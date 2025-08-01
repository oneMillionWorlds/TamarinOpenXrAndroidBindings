#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>

#define TAG "Library"
#define XR_USE_PLATFORM_ANDROID
#define XR_EXTENSION_PROTOTYPES

#include "../include/openxr/openxr.h"
#include "../include/openxr/openxr_platform.h"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)


JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC_initializeLoader
  (JNIEnv* env, jclass clazz, jobject activity) {

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
    XrResult resolveResult = xrInitializeLoaderKHR((XrLoaderInitInfoBaseHeaderKHR*)&loaderInitializeInfoAndroid);


    if (resolveResult != XR_SUCCESS) {
        return (jint)XR_ERROR_FUNCTION_UNSUPPORTED;
    }

    // Step 6: Optionally delete global ref if you’re not retaining it
    //(*env)->DeleteGlobalRef(env, globalActivity);
    // we're leaking globalActivity but that's probably fine as this is just called once

    return (jint)resolveResult;
}