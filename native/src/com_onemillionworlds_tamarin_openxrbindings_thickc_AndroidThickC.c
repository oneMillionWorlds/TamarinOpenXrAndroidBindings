/*
 * Hand-written native code that only makes sense on Android (only compiled into the Android library)
 */
#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>

#define TAG "Library"
#define XR_USE_PLATFORM_ANDROID
#define XR_EXTENSION_PROTOTYPES

#include "tamarinPlatform.h"
#include <openxr/openxr.h>
#include <openxr/openxr_platform.h>

JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_thickc_AndroidThickC_initializeLoader
  (JNIEnv* env, jclass clazz, jobject activity, jlong outbufferAddress) {

    // Step 1: Get JavaVM* from JNIEnv
    JavaVM* javaVm = NULL;
    if ((*env)->GetJavaVM(env, &javaVm) != 0) {
        return (jint)XR_ERROR_INITIALIZATION_FAILED;
    }

    // Step 2: Promote activity to a global reference (OpenXR may store it)
    jobject globalActivity = (*env)->NewGlobalRef(env, activity);
    if (globalActivity == NULL) {
        return (jint)XR_ERROR_INITIALIZATION_FAILED;
    }


    PFN_xrInitializeLoaderKHR xrInitializeLoaderKHR;
    xrGetInstanceProcAddr(
        XR_NULL_HANDLE, "xrInitializeLoaderKHR", (PFN_xrVoidFunction*)&xrInitializeLoaderKHR);

    XrLoaderInitInfoAndroidKHR loaderInitializeInfoAndroid;
    memset(&loaderInitializeInfoAndroid, 0, sizeof(loaderInitializeInfoAndroid));
    loaderInitializeInfoAndroid.type = XR_TYPE_LOADER_INIT_INFO_ANDROID_KHR;
    loaderInitializeInfoAndroid.next = NULL;
    loaderInitializeInfoAndroid.applicationVM = javaVm;
    loaderInitializeInfoAndroid.applicationContext = globalActivity;

    // Write back javaVm and activity pointers into the provided out buffer (as longs)
    if (outbufferAddress != 0) {
        jlong* out = (jlong*)(uintptr_t)outbufferAddress;
        out[0] = (jlong)(uintptr_t)javaVm;
        out[1] = (jlong)(uintptr_t)globalActivity;
    }

    XrResult resolveResult = xrInitializeLoaderKHR((XrLoaderInitInfoBaseHeaderKHR*)&loaderInitializeInfoAndroid);


    if (resolveResult != XR_SUCCESS) {
        return (jint)XR_ERROR_FUNCTION_UNSUPPORTED;
    }

    // Step 6: Optionally delete global ref if you’re not retaining it
    //(*env)->DeleteGlobalRef(env, globalActivity);
    // we're leaking globalActivity but that's probably fine as this is just called once

    return (jint)resolveResult;
}
