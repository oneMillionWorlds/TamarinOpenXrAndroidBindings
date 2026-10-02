---
name: thick-c
description: Add or change hand-written native (JNI C) functionality that is not a thin generated OpenXR wrapper — e.g. loader initialisation, callbacks into Java, anything needing JNIEnv/JavaVM — or change the hand-written MemoryUtil natives. Use when a feature can't be expressed through the generated XR10 bindings.
---

# Hand-written native code ("thick C")

First check it really can't be generated. If the problem is that a whole class of functions/structs is generated wrongly,
use the `codegen-change` skill instead. Thick C is for things that need real C logic: `JNIEnv`/`JavaVM`, Java callbacks
(e.g. the debug messenger), Android `Context`, or multi-step native sequences.

## Where things live

The same C is built for Android (arm64) and desktop (Windows x64, Linux x64), so hand-written code must compile on all of
them unless it is Android only.

- Java (every platform): `openxr-bindings-core/src/main/java/com/onemillionworlds/tamarin/openxrbindings/thickc/ThickC.java`. No `android.*` here
- Java (Android only, e.g. anything taking a `Context`): `openxr-bindings-android/src/main/java/.../thickc/AndroidThickC.java` (plus small data classes like `InitialisationData`)
- C: `native/src/com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC.c` (every platform) and `..._thickc_AndroidThickC.c` (only compiled for Android)
- Memory primitives (malloc/free/get/put/copy): `memory/MemoryUtil.java` ↔ `native/src/com_onemillionworlds_tamarin_openxrbindings_memory_MemoryUtil.c`
- CMake: `native/CMakeLists.txt` — a *new* `.c` file must be added to `JNI_SOURCES` (or, if Android only, the `if (ANDROID)` list)
- Everything is in the single shared library `openxrjni`. A new class with native methods loads it with `NativeLibraryLoader.load()` in a static initialiser (not `System.loadLibrary`, which doesn't work on desktop)

## Pattern

1. In `ThickC.java` (or `AndroidThickC.java`) declare a low-level `public static native` method taking primitives / `long` addresses / Java objects, plus (usually) a friendly Java wrapper that allocates on `MemoryStack`, checks the result code and returns typed objects (`Handle` subclasses, structs). See `AndroidThickC.initializeLoader(Context)` and `ThickC.setupDebugMessenger`
2. Compile once (`./gradlew :openxr-bindings-core:compileJava`) — javac's `-h` flag writes the JNI header to `native/headers/` (git-ignored). Copy the exact function name/signature from it
3. Implement in the C file. Conventions already used there:
   - `#define TAG "..."` (and any `XR_USE_*` protects, e.g. `XR_USE_PLATFORM_ANDROID` in `AndroidThickC.c`) and `XR_EXTENSION_PROTOTYPES`, then `#include "tamarinPlatform.h"` before `<openxr/openxr.h>` / `<openxr/openxr_platform.h>` (from the loader AAR via prefab on Android, the OpenXR SDK on desktop)
   - `LOGI`/`LOGE` from `tamarinPlatform.h` (logcat on Android; on desktop `LOGE` goes to stderr and `LOGI` is dropped). Never use `__android_log_print` or other platform APIs directly outside `#ifdef __ANDROID__`
   - Addresses cross JNI as `jlong` and are cast with `(T*)(intptr_t)addr`
   - Extension functions must be fetched with `xrGetInstanceProcAddr` (they are not linked directly); return `XR_ERROR_FUNCTION_UNSUPPORTED` if unavailable
   - Keep global refs (`NewGlobalRef`) for Java objects retained across calls, and attach/detach threads in callbacks as `debugMessengerCallback` does. Android's and the JDK's `jni.h` differ slightly (e.g. `AttachCurrentThread` takes `JNIEnv**` vs `void**`); pass `(void*)` where they disagree
4. Return `XrResult` codes as `jint`; wrap with `XrResult.fromValue(...)` on the Java side where a typed result is useful

## Verify

- `./gradlew build` (compiles the C via CMake/NDK for arm64-v8a and, with a C compiler installed, for this desktop machine; `:openxr-bindings-desktop:test` calls the desktop library). CI also builds and tests on Windows and Linux
- There is no on-device test harness in this repo; tell the user the behaviour needs checking in Tamarin on a headset (or PCVR)
