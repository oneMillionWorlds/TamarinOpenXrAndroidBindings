# JME3 Android Native Module Documentation

## Overview

The `jme3-android-native` module in jMonkeyEngine provides a bridge between Java code and native C/C++ code on Android devices. This document explains how the module works and how you can implement a similar approach for OpenXR on Quest.

## Architecture

The architecture follows a standard JNI (Java Native Interface) pattern:

1. **Java Interface Layer**: Defined in the `jme3-android` module
   - Declares native methods with the `native` keyword
   - Loads the native library using `System.loadLibrary()`
   - Implements Java interfaces that the rest of the engine uses

2. **JNI Header Files**: Generated from Java classes
   - Located in `src/native/headers`
   - Define the C function signatures that correspond to the Java native methods

3. **Native Implementation Layer**: Implemented in the `jme3-android-native` module
   - C/C++ code that implements the functions defined in the JNI headers
   - Typically wraps existing C/C++ libraries (like OpenAL)
   - Handles conversion between Java and C data types

4. **Build System**: Gradle scripts that handle the native build process
   - Downloads external dependencies if needed
   - Copies source files to build directories
   - Invokes the Android NDK to compile native code
   - Packages the compiled libraries into the JAR file

## Examples in the Module

The module contains several examples of JNI implementations:

### 1. OpenAL Soft (Audio)

- **Java Interface**: `com.jme3.audio.android.AndroidAL` in `jme3-android`
- **Native Implementation**: `com_jme3_audio_android_AndroidAL.c` in `jme3-android-native`
- **Build Configuration**: `openalsoft.gradle`

This implementation wraps the OpenAL audio library, providing Java methods that call into the native OpenAL functions.

### 2. Buffer Allocator

- **Java Interface**: `com.jme3.util.AndroidNativeBufferAllocator` in `jme3-android`
- **Native Implementation**: `com_jme3_util_AndroidNativeBufferAllocator.c` in `jme3-android-native`
- **Build Configuration**: `bufferallocator.gradle`

This implementation provides native methods for allocating and releasing direct byte buffers in Java.

## How It Works

1. **Java Side**:
   - Define a Java class with native methods
   - Load the native library in a static initializer block
   - Call the native methods from Java code

2. **C/C++ Side**:
   - Include the JNI header file
   - Implement the functions with the JNI naming convention
   - Convert Java parameters to C types
   - Call the underlying C/C++ library
   - Convert results back to Java types

3. **Build Process**:
   - Copy native source files to build directory
   - Copy JNI headers to build directory
   - Invoke the Android NDK to compile the native code
   - Package the compiled libraries into the JAR file

## Implementing OpenXR on Quest

To implement OpenXR on Quest using a similar approach, you would:

1. **Create a Java Interface**:
   - Define a Java class with native methods for OpenXR functions
   - Load the native library in a static initializer block
   - Implement any necessary Java interfaces

2. **Generate JNI Headers**:
   - Use `javac -h` or a Gradle task to generate JNI headers from your Java class

3. **Implement Native Code**:
   - Create C/C++ files that implement the functions defined in the JNI headers
   - Include the OpenXR headers
   - Call OpenXR functions from your JNI functions
   - Handle conversion between Java and C data types

4. **Configure the Build**:
   - Create a Gradle script similar to `openalsoft.gradle` or `bufferallocator.gradle`
   - Configure it to copy your source files to the build directory
   - Invoke the Android NDK to compile your native code
   - Package the compiled libraries into your JAR file

## Step-by-Step Guide for OpenXR on Quest

1. **Create a Java Interface for OpenXR**:
   ```java
   package com.jme3.vr.openxr;

   public class OpenXRInterface {
       static {
           System.loadLibrary("openxrjme");
       }

       // Native methods for OpenXR functions
       public native void initialize();
       public native void createSession();
       public native void pollEvents();
       // ... other OpenXR functions
   }
   ```

2. **Generate JNI Headers**:
   - Add a task to your Gradle build to generate JNI headers
   - Or use `javac -h` command manually

3. **Implement Native Code**:
   - Create a C/C++ file that implements the JNI functions
   - Include the OpenXR headers
   - Call OpenXR functions from your JNI functions
   ```c
   #include "com_jme3_vr_openxr_OpenXRInterface.h"
   #include <openxr/openxr.h>

   JNIEXPORT void JNICALL Java_com_jme3_vr_openxr_OpenXRInterface_initialize
     (JNIEnv *env, jobject obj)
   {
       // Initialize OpenXR
       XrInstance instance;
       XrInstanceCreateInfo createInfo = {XR_TYPE_INSTANCE_CREATE_INFO};
       // ... set up createInfo
       xrCreateInstance(&createInfo, &instance);
       // ... store instance for later use
   }
   ```

4. **Configure the Build**:
   - Create a Gradle script for your OpenXR implementation
   - Configure it to copy your source files to the build directory
   - Invoke the Android NDK to compile your native code
   - Package the compiled libraries into your JAR file

5. **Use Your OpenXR Interface in Your Application**:
   ```java
   public class MyApplication {
       public void initializeOpenXR() {
           OpenXRInterface openXR = new OpenXRInterface();
           openXR.initialize();
           openXR.createSession();
           // ... use other OpenXR functions
       }
   }
   ```

## Conclusion

The `jme3-android-native` module provides a good example of how to use JNI to bridge Java and native code on Android. By following a similar approach, you can implement OpenXR support for Quest in your jMonkeyEngine application.

Remember that working with JNI requires careful memory management and error handling, especially when dealing with complex APIs like OpenXR. Make sure to properly handle resources and check for errors in both your Java and native code.
