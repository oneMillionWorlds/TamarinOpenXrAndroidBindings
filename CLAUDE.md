# TamarinOpenXrAndroidBindings

Thin Java/JNI binding for OpenXR on **Android** (arm64-v8a, Meta Quest first) **and desktop** (Windows x64, Linux x64),
built for the Tamarin VR library (jMonkeyEngine) but usable elsewhere. The API shape deliberately mimics LWJGL's OpenXR
binding (structs with `malloc`/`calloc`/`MemoryStack`, `Struct.Buffer`, `n`-prefixed unsafe accessors, `XR10` static
calls). The Java API is identical on every platform. Almost all of the Java API and the JNI C glue is **generated at
build time** from the OpenXR registry, `xr.xml`.

Published as `com.onemillionworlds.tamarin:openxr-bindings-core` (the Java API), `openxr-bindings-android` (AAR) and
`openxr-bindings-desktop` (jar with the natives), all at the same version (in `gradle.properties`, bumped by CI).

## Layout

- `buildSrc/` — the code generator (a Gradle plugin, plain Java 17+, has its own JUnit tests)
  - `tasks/ParseOpenXr.java` — the Gradle task and entry point. Reads `xr.xml`, then runs every generator. Holds `NativePlatform` (each platform's protects) and `EXTERNAL_TYPES`
  - `tasks/parsers/` — `XmlRegistryParser` (builds the whole model from `xr.xml`), `XmlFeatureParser` (core vs extension commands), and `DefinePasser` (the C `#define` types, e.g. `XR_NULL_PATH`) / `ConstParser.Const` (an `XR10Constants` entry)
  - `tasks/StructDefinition`, `StructField`, `FunctionDefinition`, `EnumDefinition` — the intermediate model. Type mapping (C type → Java high-level / low-level / JNI type) lives mostly on `StructField` and `FunctionDefinition.FunctionParameter`
  - `tasks/generators/` — `StructGenerator` (largest), `X10Generator` + `WrapperFunctionGenerator` (Java `XR10`), `X10CGenerator` + `CWrapperFunctionGenerator` (JNI C), `EnumGenerator`, `HandleGenerator`, `ConstantsGenerator`
- `openxr-bindings-core/` — plain Java library (source level 11): the Java API, platform independent
  - `src/main/java/.../openxrbindings/` — hand-written runtime: `Struct`, `StructBuffer`, `Layout` (C layout/alignment calc), `Handle`, `NativeLibraryLoader` (loads `openxrjni`: by name on Android, extracted from the desktop jar's `natives/<platform>/` on desktop), `StructSetterValidationObject`, `memory/` (`MemoryStack`, `MemoryUtil`, `*BufferView`), `thickc/ThickC` (hand-written native helpers), `enums/EGLenum` (hand-written)
  - `src/main/generated/` — **generated, git-ignored, never edit**: `java/.../openxrbindings/` (`XR10`, `XR10Constants`, one class per struct, `enums/`, `handles/`)
  - `build/openxrSpec/` — **not checked in**: `xr.xml` downloaded from GitHub (`downloadXrXml`, SHA-256 pinned in `openxr-bindings-core/build.gradle`)
  - `src/test/` — JVM unit tests (see Testing)
- `openxr-bindings-android/` — Android library: runs `native/CMakeLists.txt` for arm64-v8a, plus `thickc/AndroidThickC` (`initializeLoader(Context)`) and `InitialisationData`
- `openxr-bindings-desktop/` — plain Java library: runs `native/CMakeLists.txt` for this machine (`configureDesktopNatives`/`buildDesktopNatives`) and packages the library at `natives/<platform>/` in the jar; `src/test` calls the real native library
- `native/` — the CMake project for `openxrjni`, shared by Android and desktop
  - `src/*.c` hand-written JNI (`MemoryUtil`, `ThickC`, Android only `AndroidThickC`), `include/tamarinPlatform.h` (opaque declarations of the external EGL/Windows/X11/xcb types, logging macros), `exports.map` (Linux: only export `Java_*`)
  - `generated/` — **generated, git-ignored, never edit**: `com_onemillionworlds_tamarin_openxrbindings_XR10.c`
  - `headers/` (javac `-h` output, git-ignored)
  - Android gets the loader + headers from the loader AAR via prefab; desktop downloads the OpenXR-SDK source release (SHA-256 pinned as `openxrSdkSha256` in `openxr-bindings-desktop/build.gradle`) and statically links the loader
- `gradle/publishing.gradle` — POM/repositories/signing shared by the three modules; the root `build.gradle` zips them into one Central bundle

## Generation pipeline

`:openxr-bindings-core:parseOpenXrFile` (type `ParseOpenXr`) runs automatically before Java compile and before both
native builds. It:

1. `XmlRegistryParser` selects what exists the same way the Khronos header generator (reg.py) does when it writes
   `openxr.h`/`openxr_platform.h`: `<feature>`s plus extensions with `supported="openxr"`, sorted (core, then KHR,
   then the rest by number), each declaring what it requires (dependencies first). Extensions/types with a `protect`
   are only included if it is in `ParseOpenXr.ENABLED_PROTECTS`, the union of every `ParseOpenXr.NativePlatform`'s
   protects (Android: `XR_USE_PLATFORM_ANDROID` + `XR_USE_GRAPHICS_API_OPENGL_ES`; Windows: `XR_USE_PLATFORM_WIN32` +
   `XR_USE_GRAPHICS_API_OPENGL`; Linux: Xlib, XCB, Wayland, EGL + `XR_USE_GRAPHICS_API_OPENGL`). Vulkan, D3D etc. are
   dropped. The order matters (constants, enum values, `XR10` methods and child-struct lists come out in declaration
   order)
2. Everything comes from `xr.xml` markup, not name guessing: type categories (enum/bitmask/handle/atom/basetype/struct),
   member/param `len` (the count field of a pointer; `MISSING_LENS` covers the rare pointer arrays xr.xml has no `len`
   for), the `values` of a struct's `type` member (its `XrStructureType`, used for `type$Default()`/`cast()`; absent on
   abstract base headers), `parentstruct` (drives `asParent()`/`asXxx()`/`cast()`) and each command's `protect` (that of
   the extension declaring it, `FunctionDefinition.getProtect()`). Types from outside OpenXR (EGL, Windows, X11, xcb)
   that aren't just pointed to are listed in `ParseOpenXr.EXTERNAL_TYPES` (as handles, int or long typedefs); generation
   fails on an unknown type
3. Emits Java + one C file. Functions in `ParseOpenXr.FUNCTIONS_TO_SKIP` (can't be thin bindings, e.g. `xrCreateSwapchainAndroidSurfaceKHR` outputs a JNI local ref) are skipped for both Java and C. `T**` params (the runtime writing out a pointer to a buffer it owns) become a `PointerBufferView` slot

Key conventions in the generated code:
- Every `XR10.xrFoo(...)` is a high-level wrapper returning `XrResult` that unwraps structs/buffers to addresses and calls `public static native int nxrFoo(...)`
- The one C file compiles on every platform: it `#define`s the protects of the platform being compiled for (`#if defined(__ANDROID__)` / `_WIN32` / `__linux__`, from `NativePlatform`). Functions with a protect are wrapped in `#ifdef <protect>`, with an `#else` stub (same JNI signature) returning `XR_ERROR_FUNCTION_UNSUPPORTED`, so every Java native links everywhere
- In C, core functions (required by a `<feature>` in `xr.xml`, see `XmlFeatureParser`) are called directly; **extension functions** (everything else) go through `static` `PFN_` pointers loaded by `initializeExtensionFunctions`, which is called from a special hand-emitted `nxrCreateInstance`. A missing extension returns `XR_ERROR_FUNCTION_UNSUPPORTED`
- Pointer params become `*BufferView` / `Struct.Buffer` / `Handle.HandleBuffer`; pointers to opaque external objects (`FunctionParameter.OPAQUE_OBJECT_TYPES`, e.g. `IUnknown*`) are a raw `long`; structs passed by value are passed as addresses and dereferenced in C
- Structs: `malloc()` variants turn on setter validation (`StructSetterValidationObject` throws on `address()` if any setter wasn't called); `calloc()`/`create()` don't. Non-const struct params in `XR10` wrappers are treated as out-params and have validation disabled. `type$Default()` sets the matching `XrStructureType` (not generated for abstract base headers)

Note: `xr.xml` (the only input to generation) and the headers the C compiles against are always the same version as
the runtime loader (`openxr-loader` in `gradle/libs.versions.toml`, currently **1.1.63**;
`org.khronos.openxr:openxr_loader_for_android` via prefab on Android, the `OpenXR-SDK` source release of the same version
on desktop, see `README_DEEP.md`). Building needs network access to GitHub for `xr.xml` (and the SDK, on desktop).

## Rules

- **Never edit anything under `openxr-bindings-core/src/main/generated/` or `native/generated/`.** Change the generator in `buildSrc/.../generators/` (or the model/parsers) and rebuild
- **Update, don't overload.** When asked to change a method, change that method rather than adding an overload with extra parameters, unless explicitly asked
- Keep it a *thin* binding. Genuinely hand-written native logic goes in `thickc/ThickC` + `native/src/..._ThickC.c` (Android only things in `AndroidThickC`), not in the generators
- The Java API must stay platform independent: nothing in `openxr-bindings-core` may use `android.*`. Android only Java goes in `openxr-bindings-android`
- Every class with `native` methods loads the library with `NativeLibraryLoader.load()` in its static initialiser (never `System.loadLibrary`)
- Enabling another platform/graphics API protect means adding it to `ParseOpenXr.NativePlatform`, its external types to `ParseOpenXr.EXTERNAL_TYPES` and `native/include/tamarinPlatform.h`, and checking `expectedSizes.csv` covers its structs
- Any generator change that alters struct output must be reflected in the reference structs (see Testing) — review the diff, don't blindly accept
- Java source level is 11 for `openxr-bindings-*` (it runs on Android), but `buildSrc` uses modern Java (pattern-matching `instanceof`, text blocks, `Stream.toList()`). CI builds with JDK 17 (`README_DEEP.md` notes Java 21 jlink once broke on `core-for-system-modules.jar`); Gradle is 9.x
- Match surrounding style: 4-space indent, British spellings appear in names (`sanitise`, `Initialisation`) — keep existing names as they are

## Build and test

Use the Gradle wrapper (`./gradlew` in bash, `.\gradlew.bat` in PowerShell). Requires Android SDK + NDK + CMake (`local.properties` points at the SDK). The desktop library also needs CMake and a C/C++ compiler on the PATH (MSVC on Windows; gcc/clang + `libx11-dev` on Linux). Without one, pass `-PskipDesktopNatives` (the desktop tests are then skipped). Other desktop build properties are documented at the top of `openxr-bindings-desktop/build.gradle`.

- `./gradlew build` — full build: generation, core tests, Android CMake + AAR, desktop natives for this machine + desktop tests (this is what CI runs)
- `./gradlew :openxr-bindings-core:parseOpenXrFile` — just regenerate sources (fast way to inspect generator output)
- `./gradlew -p buildSrc test` — generator/parser unit tests. The root `build` only compiles buildSrc (Gradle 8+ doesn't run buildSrc tests), so this is a separate step (CI runs it before `build`); run it yourself after touching `buildSrc`
- `./gradlew :openxr-bindings-core:test` — JVM tests for the Java API (`--tests '*StructsAreGeneratedCorrectlyTest*'` to filter)
- `./gradlew :openxr-bindings-desktop:test` — builds the desktop library for this machine and calls into it
- `./gradlew :openxr-bindings-core:updateReferenceStructs` — overwrite the existing `src/test/resources/referenceStructs/*_reference.java` with current generated output (never creates new ones)
- `./gradlew clean` also deletes the generated Java, `native/generated` and `native/headers`

## Testing

- `buildSrc/src/test` — unit tests for the parsers (`XmlRegistryParserTest` uses a small inline registry with one of each shape) and for `WrapperFunctionGenerator` / `CWrapperFunctionGenerator` / `HandleGenerator`, asserting exact generated strings (text blocks)
- `StructsAreGeneratedCorrectlyTest` — byte-for-byte comparison of selected generated structs against `referenceStructs/*_reference.java`. Each test has a comment saying *why* that struct is interesting (pointer-to-struct with count, function pointer, fixed array of structs, base header, odd non-const input, external platform types, ...). Add a new reference + test when adding generator behaviour for a new shape
- `StructsHaveCorrectSizeAndAlignment` — checks every generated struct's `SIZEOF`/`ALIGNOF` against `expectedSizes.csv` (real C `sizeof`/`_Alignof` values, the same on every 64 bit platform). A missing class is a pass
- `NativeSignaturesMatchTest` — checks every generated `XR10` native method against its generated C JNI function (parameter types and the JNI signature comment). JNI binds by name only, so a mismatch otherwise links fine and passes garbage
- `DesktopNativeLibraryTest` (openxr-bindings-desktop) — loads the real desktop library and calls the loader (no OpenXR runtime needed). CI runs it on Linux and Windows
- There are no on-device/headset tests here; real runtime verification happens in Tamarin on a headset (or PCVR). Say so rather than claiming a change "works" from a JVM test alone

## Release

Run the "Java release with Gradle" workflow (`workflow_dispatch`) on GitHub: a Windows job builds the Windows library,
then the release job builds everything else (including the Linux library), runs `prepareCentralBundle` (all three
modules in `build/central-bundle.zip`, with `-PdesktopNativesDir` for the Windows library), uploads via
`.github/scripts/upload_central.sh` (Central Portal, USER_MANAGED — must be published manually in the portal), tags
`v<version>` and bumps `gradle.properties`. Details in `README_PUBLISHING.md`. Don't bump the version by hand.

## Skills

- `/codegen-change` — changing what the generator produces (structs, XR10 wrappers, JNI C)
- `/thick-c` — adding hand-written native functionality
- `/update-openxr-spec` — moving to a newer OpenXR version (bumping the loader, which drives `xr.xml` and the headers)
