# TamarinOpenXrAndroidBindings

Thin Java/JNI binding for OpenXR on **Android only** (arm64-v8a, Meta Quest first), built for the Tamarin VR library
(jMonkeyEngine) but usable elsewhere. The API shape deliberately mimics LWJGL's OpenXR binding (structs with
`malloc`/`calloc`/`MemoryStack`, `Struct.Buffer`, `n`-prefixed unsafe accessors, `XR10` static calls) so code can move
between desktop LWJGL and Android. Almost all of the Java API and the JNI C glue is **generated at build time** from the
OpenXR registry, `xr.xml`.

Published as `com.onemillionworlds.tamarin:openxr-bindings-native` (version in `gradle.properties`, bumped by CI).

## Layout

- `buildSrc/` — the code generator (a Gradle plugin, plain Java 17+, has its own JUnit tests)
  - `tasks/ParseOpenXr.java` — the Gradle task and entry point. Reads `xr.xml`, then runs every generator
  - `tasks/parsers/` — `XmlRegistryParser` (builds the whole model from `xr.xml`), `XmlFeatureParser` (core vs extension commands), and `DefinePasser` (the C `#define` types, e.g. `XR_NULL_PATH`) / `ConstParser.Const` (an `XR10Constants` entry)
  - `tasks/StructDefinition`, `StructField`, `FunctionDefinition`, `EnumDefinition` — the intermediate model. Type mapping (C type → Java high-level / low-level / JNI type) lives mostly on `StructField` and `FunctionDefinition.FunctionParameter`
  - `tasks/generators/` — `StructGenerator` (largest), `X10Generator` + `WrapperFunctionGenerator` (Java `XR10`), `X10CGenerator` + `CWrapperFunctionGenerator` (JNI C), `EnumGenerator`, `HandleGenerator`, `ConstantsGenerator`
- `android-native/` — the only published module (Android library)
  - `src/main/java/.../openxrbindings/` — hand-written runtime: `Struct`, `StructBuffer`, `Layout` (C layout/alignment calc), `Handle`, `StructSetterValidationObject`, `memory/` (`MemoryStack`, `MemoryUtil`, `*BufferView`), `thickc/ThickC` (hand-written native helpers), `enums/EGLenum` (hand-written)
  - `src/main/generated/` — **generated, git-ignored, never edit**. `java/.../openxrbindings/` (`XR10`, `XR10Constants`, one class per struct, `enums/`, `handles/`) and `native/src/com_onemillionworlds_tamarin_openxrbindings_XR10.c`
  - `src/native/` — CMake project: `src/*.c` hand-written JNI (`MemoryUtil`, `ThickC`), `include/tamarinManualDefines.h` (opaque EGL typedefs), `headers/` (javac `-h` output, git-ignored)
  - `build/openxrSpec/` — **not checked in**: `xr.xml` downloaded from GitHub (`downloadXrXml`, SHA-256 pinned in `android-native/build.gradle`). The C headers aren't used by the generator; CMake gets them from the loader AAR via prefab
  - `src/test/` — JVM unit tests (see Testing)

## Generation pipeline

`:android-native:parseOpenXrFile` (type `ParseOpenXr`) runs automatically before Java compile, CMake and javadoc/sources
tasks. It:

1. `XmlRegistryParser` selects what exists the same way the Khronos header generator (reg.py) does when it writes
   `openxr.h`/`openxr_platform.h`: `<feature>`s plus extensions with `supported="openxr"`, sorted (core, then KHR,
   then the rest by number), each declaring what it requires (dependencies first). Extensions/types with a `protect`
   are only included if it is in `ParseOpenXr.ENABLED_PROTECTS` (`XR_USE_PLATFORM_ANDROID`,
   `XR_USE_GRAPHICS_API_OPENGL_ES`); other platforms/graphics APIs are dropped. The order matters (constants, enum
   values, `XR10` methods and child-struct lists come out in declaration order)
2. Everything comes from `xr.xml` markup, not name guessing: type categories (enum/bitmask/handle/atom/basetype/struct),
   member/param `len` (the count field of a pointer; `MISSING_LENS` covers the rare pointer arrays xr.xml has no `len`
   for), the `values` of a struct's `type` member (its `XrStructureType`, used for `type$Default()`/`cast()`; absent on
   abstract base headers) and `parentstruct` (drives `asParent()`/`asXxx()`/`cast()`)
3. Emits Java + one C file. Functions in `ParseOpenXr.FUNCTIONS_TO_SKIP` (can't be thin bindings, e.g. `xrCreateSwapchainAndroidSurfaceKHR` outputs a JNI local ref) are skipped for both Java and C. `T**` params (the runtime writing out a pointer to a buffer it owns) become a `PointerBufferView` slot

Key conventions in the generated code:
- Every `XR10.xrFoo(...)` is a high-level wrapper returning `XrResult` that unwraps structs/buffers to addresses and calls `public static native int nxrFoo(...)`
- In C, core functions (required by a `<feature>` in `xr.xml`, see `XmlFeatureParser`) are called directly; **extension functions** (everything else) go through `PFN_` pointers loaded by `initializeExtensionFunctions`, which is called from a special hand-emitted `nxrCreateInstance`. A missing extension returns `XR_ERROR_FUNCTION_UNSUPPORTED`
- Pointer params become `*BufferView` / `Struct.Buffer` / `Handle.HandleBuffer`; structs passed by value are passed as addresses and dereferenced in C
- Pointer struct fields with a `len` (a count field) become `Struct.Buffer`, `Handle.HandleBuffer` or, for plain values (numbers, enums, atoms), `IntBufferView`/`FloatBufferView`/... (`StructField.getPrimitiveBufferViewType`); setting one also sets the count field. A struct pointer without a `len` is a single struct; any other pointer without one is a raw `long` address
- Null-terminated string fields (`len="null-terminated"`): a `const char*` is a `ByteBufferView` (setter checks it is null-terminated, e.g. from `MemoryStack.utf8`) plus `xxxString()`; a counted `const char* const*` is a `PointerBufferView` of string addresses (e.g. from `MemoryStack.utf8Pointers`, sets the count) plus `List<String> xxxStrings()`
- Structs: `malloc()` variants turn on setter validation (`StructSetterValidationObject` throws on `address()` if any setter wasn't called); `calloc()`/`create()` don't. Setting a buffer field also counts as setting its count field (the setter writes it). Fields xr.xml marks `optional` are still required after `malloc()`: optional means 0/NULL is allowed, and unset `malloc` memory is garbage (use `calloc()` to leave fields unset). Non-const struct params in `XR10` wrappers are treated as out-params and have validation disabled. `type$Default()` sets the matching `XrStructureType` (not generated for abstract base headers)

Note: `xr.xml` (the only input to generation) and the headers the C compiles against are always the same version as
the runtime loader (`openxr-loader` in `gradle/libs.versions.toml`, currently **1.1.63**;
`org.khronos.openxr:openxr_loader_for_android`, via prefab, see `README_DEEP.md`). The generated C defines
`ParseOpenXr.STANDARD_DEFS` (`XR_EXTENSION_PROTOTYPES` + the enabled protects) before including the headers so they
declare exactly what was generated. Building needs network access to GitHub for `xr.xml`.

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

- `buildSrc/src/test` — unit tests for the parsers (`XmlRegistryParserTest` uses a small inline registry with one of each shape) and for `WrapperFunctionGenerator` / `CWrapperFunctionGenerator` / `HandleGenerator`, asserting exact generated strings (text blocks)
- `StructsAreGeneratedCorrectlyTest` — byte-for-byte comparison of selected generated structs against `referenceStructs/*_reference.java`. Each test has a comment saying *why* that struct is interesting (pointer-to-struct with count, function pointer, fixed array of structs, base header, odd non-const input, ...). Add a new reference + test when adding generator behaviour for a new shape
- `StructsHaveCorrectSizeAndAlignment` — checks every generated struct's `SIZEOF`/`ALIGNOF` against `expectedSizes.csv` (real C `sizeof`/`_Alignof` values). A missing class is a pass
- `NativeSignaturesMatchTest` — checks every generated `XR10` native method against its generated C JNI function (parameter types and the JNI signature comment). JNI binds by name only, so a mismatch otherwise links fine and passes garbage
- There are no on-device tests here; real runtime verification happens in Tamarin on a headset. Say so rather than claiming a change "works" from a JVM test alone

## Release

Run the "Java release with Gradle" workflow (`workflow_dispatch`) on GitHub: it builds, runs
`:android-native:prepareCentralBundle`, uploads via `.github/scripts/upload_central.sh` (Central Portal, USER_MANAGED — must be
published manually in the portal), tags `v<version>` and bumps `gradle.properties`. Details in `README_PUBLISHING.md`.
Don't bump the version by hand.

## Skills

- `/codegen-change` — changing what the generator produces (structs, XR10 wrappers, JNI C)
- `/thick-c` — adding hand-written native functionality
- `/update-openxr-spec` — moving to a newer OpenXR version (bumping the loader, which drives `xr.xml` and the headers)
