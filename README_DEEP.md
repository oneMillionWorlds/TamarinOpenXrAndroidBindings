# Process

## Java version

core-for-system-modules.jar isn't a valid module for Java 21 jlink and will cause the build 
to crash. For this reason java 17 is used instead (and is what CI uses)

## Code generation

Almost everything in the public API is generated. The `buildSrc` module contains a Gradle plugin whose `ParseOpenXr`
task (registered in android-native/build.gradle as `parseOpenXrFile`) runs before compilation and:

1. Parses the vendored OpenXR headers (see below) line by line, with `#ifdef`s resolved for Android + OpenGL ES
   (`ParseOpenXr.STANDARD_DEFS`)
2. Enriches the parsed structs with information from `android-native/src/openxrSpec/xr.xml` (parent structs)
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

## OpenXR headers

The header files for the openXR calls (calls into the loader, not the system calls) are at 
`android-native/src/native/include/openxr`. These are third party headers that allow our c to call 
the openXR loader. They are also the main input to the code generation, so the OpenXR version they define (currently
1.0.24) is the version of the API this library exposes.

## OpenXR loader

The OpenXR loader (libopenxr_loader.so) comes from the Khronos `org.khronos.openxr:openxr_loader_for_android` 
dependency (version in gradle/libs.versions.toml). Our CMake build links against it via prefab 
(`find_package(OpenXR)` / `OpenXR::openxr_loader`), but only the library - our code still uses the vendored headers 
above. It is an `api` dependency so consumers receive the loader (and the manifest entries it needs) transitively; we
deliberately don't bundle a copy of the .so in our own AAR.
At runtime it dynamically links to the actual OpenXR system calls and allows our java calls to flow through to 
the underlying system.
