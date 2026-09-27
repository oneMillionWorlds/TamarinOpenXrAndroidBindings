# Process

## Java version

core-for-system-modules.jar isn't a valid module for Java 21 jlink and will cause the build 
to crash. For this reason java 17 is used instead (and is what CI uses)

## Code generation

Almost everything in the public API is generated. The `buildSrc` module contains a Gradle plugin whose `ParseOpenXr`
task (registered in android-native/build.gradle as `parseOpenXrFile`) runs before compilation and:

1. Reads the OpenXR registry, `xr.xml` (see below). What gets generated, and in what order, follows the same rules
   the Khronos generator uses to write `openxr.h`/`openxr_platform.h` from `xr.xml`, with platform/graphics API
   specific parts only included for Android + OpenGL ES (`ParseOpenXr.ENABLED_PROTECTS`). Struct fields, count fields
   (`len`), struct types (`values`), parent structs and so on all come from the registry markup
2. Uses its `<feature>` blocks to decide which functions are core (called directly) and which are extensions (called
   through a function pointer)
3. Writes the Java API (`XR10`, `XR10Constants`, the structs, `enums` and `handles` packages) to
   `android-native/src/main/generated/java` and the C JNI wrapper to
   `android-native/src/main/generated/native/src/com_onemillionworlds_tamarin_openxrbindings_XR10.c`

The generated directory is not checked in and should never be edited by hand; change the generators in
`buildSrc/src/main/java/com/onemillionworlds/tamarin/gradle/tasks/generators` instead.

## Java bindings

In the android-native module there are the java side of the native methods (both generated, e.g. `XR10`, and hand
written, e.g. `MemoryUtil` and `ThickC`). These are annotated native and during the build process (see
android-native/build.gradle) headers are created for our c code

    tasks.withType(JavaCompile) {
        options.compilerArgs += ["-h", "${project.projectDir}/src/native/headers"]
    }

## Native code

The C code is built by CMake (android-native/src/native/CMakeLists.txt) into a single library, `openxrjni`. It consists
of the generated XR10 wrapper plus the hand written `MemoryUtil` and `ThickC` C files in android-native/src/native/src.
`ThickC` is where anything that is more than a thin wrapper around an OpenXR call goes (loader initialisation, the
debug messenger callback into Java, etc.).

## OpenXR headers and xr.xml

Neither the OpenXR headers nor the registry (`xr.xml`) are checked in. The code generator only uses `xr.xml`; the
headers are only used to compile the C. Both are taken at the same version as the OpenXR
loader (`openxr-loader` in gradle/libs.versions.toml), so the generated API, the headers our C compiles against and the
loader it runs against always agree. That version (currently 1.1.63) is the version of the API this library exposes.

- The headers come from the loader AAR itself (`prefab/modules/headers/include/openxr`). CMake gets them through
  prefab (`OpenXR::openxr_loader` exports `OpenXR::headers`)
- `xr.xml` is downloaded by the `downloadXrXml` task from the matching `release-<version>` tag of
  [OpenXR-SDK-Source](https://github.com/KhronosGroup/OpenXR-SDK-Source) to `android-native/build/openxrSpec/xr.xml`.
  It is checked against `xrXmlSha256` in android-native/build.gradle, so bumping the loader means updating that
  checksum too (the build fails with the new checksum in the message). The build therefore needs network access to
  GitHub

## OpenXR loader

The OpenXR loader (libopenxr_loader.so) comes from the Khronos `org.khronos.openxr:openxr_loader_for_android` 
dependency (version in gradle/libs.versions.toml). Our CMake build links against it via prefab 
(`find_package(OpenXR)` / `OpenXR::openxr_loader`), which also provides the headers (see above).
It is an `api` dependency so consumers receive the loader (and the manifest entries it needs) transitively; we
deliberately don't bundle a copy of the .so in our own AAR.
At runtime it dynamically links to the actual OpenXR system calls and allows our java calls to flow through to 
the underlying system.
