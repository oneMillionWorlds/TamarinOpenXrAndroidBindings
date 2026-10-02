# Process

## Java version

core-for-system-modules.jar isn't a valid module for Java 21 jlink and will cause the build 
to crash. For this reason java 17 is used instead (and is what CI uses)

## Modules

- `openxr-bindings-core` (plain Java library): the Java API, generated and hand-written. Platform independent
- `openxr-bindings-android` (Android library): builds `native/` for Android and adds the Android only Java
  (`AndroidThickC`)
- `openxr-bindings-desktop` (plain Java library): builds `native/` for desktop and packages the libraries in the jar
- `native/` (not a Gradle project): the CMake project for the `openxrjni` library, shared by Android and desktop

## Code generation

Almost everything in the public API is generated. The `buildSrc` module contains a Gradle plugin whose `ParseOpenXr`
task (registered in openxr-bindings-core/build.gradle as `parseOpenXrFile`) runs before compilation and:

1. Reads the OpenXR registry, `xr.xml` (see below). What gets generated, and in what order, follows the same rules
   the Khronos generator uses to write `openxr.h`/`openxr_platform.h` from `xr.xml`, with platform/graphics API
   specific parts only included for the platforms the library is built for (`ParseOpenXr.NativePlatform`: Android +
   OpenGL ES, Windows + OpenGL, Linux (Xlib, XCB, Wayland, EGL) + OpenGL). Struct fields, count fields (`len`), struct
   types (`values`), parent structs and so on all come from the registry markup
2. Uses its `<feature>` blocks to decide which functions are core (called directly) and which are extensions (called
   through a function pointer)
3. Writes the Java API (`XR10`, `XR10Constants`, the structs, `enums` and `handles` packages) to
   `openxr-bindings-core/src/main/generated/java` and the C JNI wrapper to
   `native/generated/com_onemillionworlds_tamarin_openxrbindings_XR10.c`

The Java API is the same on every platform, so it has every platform's structs and functions. The C file is also the
same everywhere: it defines the `XR_USE_*` protects of the platform it is compiled for (picked by `__ANDROID__`,
`_WIN32` or `__linux__`) and the functions of other platforms' extensions compile to stubs returning
`XR_ERROR_FUNCTION_UNSUPPORTED` (so the Java native methods always link).

The generated directories are not checked in and should never be edited by hand; change the generators in
`buildSrc/src/main/java/com/onemillionworlds/tamarin/gradle/tasks/generators` instead.

## Java bindings

In openxr-bindings-core there are the java side of the native methods (both generated, e.g. `XR10`, and hand
written, e.g. `MemoryUtil` and `ThickC`). These are annotated native and during the build process (see
openxr-bindings-core/build.gradle) headers are created for our c code in `native/headers`.

Every class with native methods loads the library through `NativeLibraryLoader.load()`: on Android by name (it is
installed from the AAR), on desktop by extracting `natives/<platform>/` from the openxr-bindings-desktop jar to a
content-hashed directory under `java.io.tmpdir`. The system property `tamarin.openxrbindings.libraryPath` loads a
specific file instead.

## Native code

The C code is built by CMake (native/CMakeLists.txt) into a single library, `openxrjni`. It consists
of the generated XR10 wrapper plus the hand written `MemoryUtil` and `ThickC` C files in native/src (and on Android
`AndroidThickC`). `ThickC` is where anything that is more than a thin wrapper around an OpenXR call goes (loader
initialisation, the debug messenger callback into Java, etc.). `native/include/tamarinPlatform.h` declares the external
(EGL, Windows, X11, xcb) types the OpenXR platform header needs, as opaque types, so no platform development headers are
needed, and the logging macros (logcat on Android, stderr on desktop).

On Android the Android Gradle plugin runs CMake. On desktop openxr-bindings-desktop's `configureDesktopNatives` /
`buildDesktopNatives` tasks run it (for the machine Gradle is running on) and the library is added to the jar. Other
platforms' libraries are built on those platforms (CI has a Windows job) and passed in with `-PdesktopNativesDir`;
publishing fails unless the jar has every platform (`verifyAllDesktopNatives`).

## OpenXR headers and xr.xml

Neither the OpenXR headers nor the registry (`xr.xml`) are checked in. The code generator only uses `xr.xml`; the
headers are only used to compile the C. Both are taken at the same version as the OpenXR
loader (`openxr-loader` in gradle/libs.versions.toml), so the generated API, the headers our C compiles against and the
loader it runs against always agree. That version (currently 1.1.63) is the version of the API this library exposes.

- On Android the headers come from the loader AAR itself (`prefab/modules/headers/include/openxr`). CMake gets them
  through prefab (`OpenXR::openxr_loader` exports `OpenXR::headers`)
- On desktop they come from the Khronos [OpenXR-SDK](https://github.com/KhronosGroup/OpenXR-SDK) source release of the
  same version, which CMake downloads (FetchContent) along with the loader source. It is checked against
  `openxrSdkSha256` in openxr-bindings-desktop/build.gradle
- `xr.xml` is downloaded by the `downloadXrXml` task from the matching `release-<version>` tag of
  [OpenXR-SDK-Source](https://github.com/KhronosGroup/OpenXR-SDK-Source) to `openxr-bindings-core/build/openxrSpec/xr.xml`.
  It is checked against `xrXmlSha256` in openxr-bindings-core/build.gradle, so bumping the loader means updating that
  checksum (and `openxrSdkSha256`) too (the build fails with the new checksum in the message). The build therefore
  needs network access to GitHub

## OpenXR loader

On Android the OpenXR loader (libopenxr_loader.so) comes from the Khronos `org.khronos.openxr:openxr_loader_for_android` 
dependency (version in gradle/libs.versions.toml). Our CMake build links against it via prefab 
(`find_package(OpenXR)` / `OpenXR::openxr_loader`), which also provides the headers (see above).
It is an `api` dependency so consumers receive the loader (and the manifest entries it needs) transitively; we
deliberately don't bundle a copy of the .so in our own AAR.

On desktop the loader is built from the OpenXR-SDK source and statically linked into `openxrjni` (with the C/C++ runtime
also static, so the library only needs system libraries). On Linux a version script (`native/exports.map`) exports only
the JNI functions, so the loader's `xr*` symbols can't clash with another loader (e.g. LWJGL's) in the same process.

At runtime the loader finds the active OpenXR runtime and allows our java calls to flow through to it.
