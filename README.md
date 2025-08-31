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

### Enumerating API Layer Properties

```java
import com.onemillionworlds.tamarin.openxrbindings.Library;
import com.onemillionworlds.tamarin.openxrbindings.XrResult;

// Create an instance of the OpenXR library
Library openxr = new Library();

// First call to get the count
int count = openxr.xrEnumerateApiLayerProperties(0, null);
System.out.println("Found " + count + " API layer properties");

if (count > 0) {
    // Allocate array for the properties
    Library.XrApiLayerProperties[] properties = new Library.XrApiLayerProperties[count];
    for (int i = 0; i < count; i++) {
        properties[i] = new Library.XrApiLayerProperties();
    }

    // Second call to get the properties
    int result = openxr.xrEnumerateApiLayerProperties(count, properties);

    if (XrResult.XR_SUCCEEDED(result)) {
        System.out.println("Successfully enumerated " + result + " API layer properties:");
        for (int i = 0; i < result; i++) {
            System.out.println(properties[i].toString());
        }
    } else {
        System.err.println("Failed to enumerate API layer properties: " + XrResult.toString(result));
    }
}
```

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

This task will copy all generated struct files to the reference directory, updating the test expectations. Only run this task when you've verified that the changes to the generated files are correct and should become the new reference standard.

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
