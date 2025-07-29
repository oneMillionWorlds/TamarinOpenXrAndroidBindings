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

## OpenXR loader so files

The file at android-native/src/main/jnilibs/arm64-v8a/libopenxr_loader.so is the actual openXR loader.
At runtime it dynamically links to the actual OpenXR system calls and allows our java calls to flow through to 
the underlying system.