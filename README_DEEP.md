# Process

## Java version

core-for-system-modules.jar isn't a valid module for Java 21 jlink and will cause the build 
to crash. For this reason java 17 is used instead

## Java bindings

In the module android-java there are the java side of the native methods. These are annotated 
native and during the buid process (see android-java/build.gradle) headers are created for our c code

    compileJava {
        options.compilerArgs += ["-h", "${project.rootDir}/android-native/src/native/headers"]
    }

## OpenXR headers

The header files for the openXR calls (calls into the loader, not the system calls) are at 
`lib/src/main/native/include/openxr`. These are third party headers that allow our c to call 
the openXR loaded

## OpenXR loader

The OpenXR loader (libopenxr_loader.so) comes from the Khronos `org.khronos.openxr:openxr_loader_for_android` 
dependency (version in gradle/libs.versions.toml). Our CMake build links against it via prefab 
(`find_package(OpenXR)` / `OpenXR::openxr_loader`), but only the library - our code still uses the vendored headers 
above. It is an `api` dependency so consumers receive the loader (and the manifest entries it needs) transitively; we
deliberately don't bundle a copy of the .so in our own AAR.
At runtime it dynamically links to the actual OpenXR system calls and allows our java calls to flow through to 
the underlying system.