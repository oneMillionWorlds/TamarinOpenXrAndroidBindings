# TamarinOpenXrAndroidBindings

Thin Java/JNI binding for OpenXR on **Android only** (arm64-v8a, Meta Quest first), built for the Tamarin VR library
(jMonkeyEngine) but usable elsewhere. The API shape deliberately mimics LWJGL's OpenXR binding (structs with
`malloc`/`calloc`/`MemoryStack`, `Struct.Buffer`, `n`-prefixed unsafe accessors, `XR10` static calls) so code can move
between desktop LWJGL and Android. Almost all of the Java API and the JNI C glue is **generated at build time** from the
OpenXR headers and `xr.xml`.

Published as `com.onemillionworlds.tamarin:openxr-bindings-native` (version in `gradle.properties`, bumped by CI).

## Layout

- `buildSrc/` — the code generator (a Gradle plugin, plain Java 17+, has its own JUnit tests)
  - `tasks/ParseOpenXr.java` — the Gradle task and entry point. Reads headers line by line, then `xr.xml`, then runs every generator
  - `tasks/parsers/` — line-based parsers for the C headers (`StructParser`, `FunctionParser`, `EnumParser`, `IfDefParser`, ...) and `XmlStructParser` for `xr.xml`
  - `tasks/StructDefinition`, `StructField`, `FunctionDefinition`, `EnumDefinition` — the intermediate model. Type mapping (C type → Java high-level / low-level / JNI type) lives mostly on `StructField` and `FunctionDefinition.FunctionParameter`
  - `tasks/generators/` — `StructGenerator` (largest), `X10Generator` + `WrapperFunctionGenerator` (Java `XR10`), `X10CGenerator` + `CWrapperFunctionGenerator` (JNI C), `EnumGenerator`, `HandleGenerator`, `ConstantsGenerator`
- `android-native/` — the only published module (Android library)
  - `src/main/java/.../openxrbindings/` — hand-written runtime: `Struct`, `StructBuffer`, `Layout` (C layout/alignment calc), `Handle`, `StructSetterValidationObject`, `memory/` (`MemoryStack`, `MemoryUtil`, `*BufferView`), `thickc/ThickC` (hand-written native helpers), `enums/EGLenum` (hand-written)
  - `src/main/generated/` — **generated, git-ignored, never edit**. `java/.../openxrbindings/` (`XR10`, `XR10Constants`, one class per struct, `enums/`, `handles/`) and `native/src/com_onemillionworlds_tamarin_openxrbindings_XR10.c`
  - `src/native/` — CMake project: `src/*.c` hand-written JNI (`MemoryUtil`, `ThickC`), `include/openxr/*.h` vendored OpenXR headers, `include/tamarinManualDefines.h` (opaque EGL typedefs), `headers/` (javac `-h` output, git-ignored)
  - `src/openxrSpec/xr.xml` — Khronos registry
  - `src/test/` — JVM unit tests (see Testing)

## Generation pipeline

`:android-native:parseOpenXrFile` (type `ParseOpenXr`) runs automatically before Java compile, CMake and javadoc/sources
tasks. It:

1. Preprocesses `openxr.h` and `openxr_platform.h` with `IfDefParser` using `ParseOpenXr.STANDARD_DEFS`
   (`XR_EXTENSION_PROTOTYPES`, `XR_USE_PLATFORM_ANDROID`, `XR_USE_GRAPHICS_API_OPENGL_ES`) — other platforms/graphics APIs are dropped
2. Parses structs, enums, functions, handles, atoms, flags, int/long typedefs and `#define`s **from the headers**
3. Enriches structs from `xr.xml` (currently only `parentstruct`, which drives `asParent()`/`asXxx()`/`cast()`); every header struct must exist in `xr.xml` or generation throws
4. Emits Java + one C file. Functions with double pointers and `*META` functions are skipped; `xrCreateSwapchainAndroidSurfaceKHR` is skipped in `X10Generator`

Key conventions in the generated code:
- Every `XR10.xrFoo(...)` is a high-level wrapper returning `XrResult` that unwraps structs/buffers to addresses and calls `public static native int nxrFoo(...)`
- In C, core functions are called directly; **extension functions** (name contains a vendor suffix in `X10CGenerator.extensionSuffixes`) go through `PFN_` pointers loaded by `initializeExtensionFunctions`, which is called from a special hand-emitted `nxrCreateInstance`. A missing extension returns `XR_ERROR_FUNCTION_UNSUPPORTED`
- Pointer params become `*BufferView` / `Struct.Buffer` / `Handle.HandleBuffer`; structs passed by value are passed as addresses and dereferenced in C
- Structs: `malloc()` variants turn on setter validation (`StructSetterValidationObject` throws on `address()` if any setter wasn't called); `calloc()`/`create()` don't. Non-const struct params in `XR10` wrappers are treated as out-params and have validation disabled. `type$Default()` sets the matching `XrStructureType` (not generated for abstract base headers)

Note: the vendored headers are OpenXR **1.0.24** while `xr.xml` is **1.1.51** and the runtime loader
(`org.khronos.openxr:openxr_loader_for_android`, via prefab, see `README_DEEP.md`) is newer still. The headers are the
source of truth for what gets generated.

## Rules

- **Never edit anything under `android-native/src/main/generated/`.** Change the generator in `buildSrc/.../generators/` (or the model/parsers) and rebuild
- **Update, don't overload.** When asked to change a method, change that method rather than adding an overload with extra parameters, unless explicitly asked
- Keep it a *thin* binding. Genuinely hand-written native logic goes in `thickc/ThickC` + `src/native/src/..._ThickC.c`, not in the generators
- Any generator change that alters struct output must be reflected in the reference structs (see Testing) — review the diff, don't blindly accept
- Java source level is 11 for `android-native` (it runs on Android), but `buildSrc` uses modern Java (pattern-matching `instanceof`, text blocks, `Stream.toList()`). CI builds with JDK 17 (`README_DEEP.md` notes Java 21 jlink once broke on `core-for-system-modules.jar`); Gradle is 9.x
- Match surrounding style: 4-space indent, British spellings appear in names (`sanitise`, `Initialisation`) — keep existing names as they are

## Build and test

Use the Gradle wrapper (`./gradlew` in bash, `.\gradlew.bat` in PowerShell). Requires Android SDK + NDK + CMake (`local.properties` points at the SDK).

- `./gradlew build` — full build: generation, CMake, AAR, `android-native` tests (this is what CI runs)
- `./gradlew :android-native:parseOpenXrFile` — just regenerate sources (fast way to inspect generator output)
- `./gradlew -p buildSrc test` — generator/parser unit tests. The root `build` only compiles buildSrc (Gradle 8+ doesn't run buildSrc tests), so this is a separate step (CI runs it before `build`); run it yourself after touching `buildSrc`
- `./gradlew :android-native:testDebugUnitTest` — JVM tests for the library (`--tests '*StructsAreGeneratedCorrectlyTest*'` to filter)
- `./gradlew :android-native:updateReferenceStructs` — overwrite the existing `src/test/resources/referenceStructs/*_reference.java` with current generated output (never creates new ones)
- `./gradlew clean` also deletes `src/main/generated`

## Testing

- `buildSrc/src/test` — unit tests for parsers and for `WrapperFunctionGenerator` / `CWrapperFunctionGenerator` / `HandleGenerator`, asserting exact generated strings (text blocks). `CommonData` holds shared fixtures
- `StructsAreGeneratedCorrectlyTest` — byte-for-byte comparison of selected generated structs against `referenceStructs/*_reference.java`. Each test has a comment saying *why* that struct is interesting (pointer-to-struct with count, function pointer, fixed array of structs, base header, odd non-const input, ...). Add a new reference + test when adding generator behaviour for a new shape
- `StructsHaveCorrectSizeAndAlignment` — checks every generated struct's `SIZEOF`/`ALIGNOF` against `expectedSizes.csv` (real C `sizeof`/`_Alignof` values). A missing class is a pass
- There are no on-device tests here; real runtime verification happens in Tamarin on a headset. Say so rather than claiming a change "works" from a JVM test alone

## Release

Run the "Java release with Gradle" workflow (`workflow_dispatch`) on GitHub: it builds, runs
`:android-native:prepareCentralBundle`, uploads via `.github/scripts/upload_central.sh` (Central Portal, USER_MANAGED — must be
published manually in the portal), tags `v<version>` and bumps `gradle.properties`. Details in `README_PUBLISHING.md`.
Don't bump the version by hand.

## Skills

- `/codegen-change` — changing what the generator produces (structs, XR10 wrappers, JNI C)
- `/thick-c` — adding hand-written native functionality
- `/update-openxr-spec` — updating the vendored headers / `xr.xml`
