# Process

## Java bindings

In the module android-java there are the java side of the native methods. These are annotated 
native and during the buid process (see android-java/build.gradle) headers are created for our c code

    compileJava {
        options.compilerArgs += ["-h", "${project.rootDir}/android-native/src/native/headers"]
    }

## OpenXR headers

The header files for the openXR calls (calls into the loader, not the system calls) are at 
`lib/src/main/native/include/openxr`. These are third party 