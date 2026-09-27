---
name: thick-c
description: Add or change hand-written native (JNI C) functionality that is not a thin generated OpenXR wrapper — e.g. loader initialisation, callbacks into Java, anything needing JNIEnv/JavaVM — or change the hand-written MemoryUtil natives. Use when a feature can't be expressed through the generated XR10 bindings.
---

# Hand-written native code ("thick C")

First check it really can't be generated. If the problem is that a whole class of functions/structs is generated wrongly,
use the `codegen-change` skill instead. Thick C is for things that need real C logic: `JNIEnv`/`JavaVM`, Java callbacks
(e.g. the debug messenger), Android `Context`, or multi-step native sequences.

## Where things live

- Java: `android-native/src/main/java/com/onemillionworlds/tamarin/openxrbindings/thickc/ThickC.java` (plus small data classes like `InitialisationData` in the same package)
- C: `android-native/src/native/src/com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC.c`
- Memory primitives (malloc/free/get/put/copy): `memory/MemoryUtil.java` ↔ `src/native/src/com_onemillionworlds_tamarin_openxrbindings_memory_MemoryUtil.c`
- CMake: `android-native/src/native/CMakeLists.txt` — a *new* `.c` file must be added to `JNI_SOURCES`
- Everything is in the single shared library `openxrjni` (`System.loadLibrary("openxrjni")`)

## Pattern

1. In `ThickC.java` declare a low-level `public static native` method taking primitives / `long` addresses / Java objects, plus (usually) a friendly Java wrapper that allocates on `MemoryStack`, checks the result code and returns typed objects (`Handle` subclasses, structs). See `initializeLoader(Context)` and `setupDebugMessenger`
2. Compile once (`./gradlew :android-native:compileDebugJavaWithJavac`) — javac's `-h` flag writes the JNI header to `android-native/src/native/headers/` (git-ignored). Copy the exact function name/signature from it
3. Implement in the C file. Conventions already used there:
   - `#define XR_USE_PLATFORM_ANDROID` / `XR_EXTENSION_PROTOTYPES` before including `../include/openxr/openxr.h` and `openxr_platform.h`
   - `LOGI`/`LOGE` via `__android_log_print`
   - Addresses cross JNI as `jlong` and are cast with `(T*)(intptr_t)addr`
   - Extension functions must be fetched with `xrGetInstanceProcAddr` (they are not linked directly); return `XR_ERROR_FUNCTION_UNSUPPORTED` if unavailable
   - Keep global refs (`NewGlobalRef`) for Java objects retained across calls, and attach/detach threads in callbacks as `debugMessengerCallback` does
4. Return `XrResult` codes as `jint`; wrap with `XrResult.fromValue(...)` on the Java side where a typed result is useful

## Verify

- `./gradlew build` (compiles the C via CMake/NDK for arm64-v8a)
- There is no on-device test harness in this repo; tell the user the behaviour needs checking in Tamarin on a headset
