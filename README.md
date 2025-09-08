# TamarinOpenXrAndroidBindings

Java bindings for OpenXR on Android, initially targeting the Meta Quest headset.

## Overview

This library provides Java bindings for the OpenXR API, allowing Android applications to interact with OpenXR-compatible VR/AR devices. The bindings use JNI (Java Native Interface) to call the native OpenXR functions from Java code.

## Features

Currently, the library supports:

- Enumerating API layer properties (`xrEnumerateApiLayerProperties`)

More functions will be added in future releases.

## Requirements

- Android SDK 24+
- Android NDK
- CMake
- Gradle 8.0+
- Java 11+

## Installation

Add the library to your project's dependencies:

```gradle
dependencies {
    implementation 'com.onemillionworlds.tamarin:openxrbindings:1.0.0'
}
```

## Usage

// todo

## Building from Source

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/TamarinOpenXrAndroidBindings.git
   ```

2. Build the library:
   ```
   ./gradlew build
   ```

3. The library will be generated in `lib/build/outputs/aar/`.

### Development Tasks

#### Updating Reference Struct Files

The project includes tests that compare generated struct files with reference versions to ensure consistency. When you make changes to the struct generation code and want to accept the new generated files as the reference:

```
./gradlew updateReferenceStructs
```

This task will update only existing reference struct files with their corresponding generated versions. It will not create new reference files. Only run this task when you've verified that the changes to the generated files are correct and should become the new reference standard.

Note: Only a small subset of generated files are used as references in tests. This task preserves that subset and only updates those files that already exist as references.

## Implementation Details

The library uses JNI to bridge between Java and the native OpenXR API. The implementation follows these steps:

1. Java classes define the API and data structures
2. JNI headers are generated from the Java classes
3. C implementation forwards calls to the OpenXR API
4. Results are converted back to Java objects

## License

This project is licensed under the Apache 2.0 License - see the LICENSE file for details.

## Acknowledgments

- The OpenXR Working Group for the OpenXR API
- jMonkeyEngine for inspiration on the JNI implementation approach
