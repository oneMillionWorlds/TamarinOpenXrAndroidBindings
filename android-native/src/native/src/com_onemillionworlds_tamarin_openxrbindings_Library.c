#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>

// Define XR_EXTENSION_PROTOTYPES to enable extension function prototypes
#define XR_EXTENSION_PROTOTYPES

#include "../include/openxr/openxr.h"
#include "../include/openxr/openxr_platform.h"

#define TAG "Library"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_Library
 * Method:    nxrEnumerateApiLayerProperties
 * Signature: (IJJ)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_Library_nxrEnumerateApiLayerProperties
  (JNIEnv *env, jobject obj, jint propertyCapacityInput, jlong propertyCountOutput, jlong properties) {

    // Convert JNI parameters to OpenXR parameters
    uint32_t capacity = (uint32_t)propertyCapacityInput;
    uint32_t *countOutput = (uint32_t *)(intptr_t)propertyCountOutput;
    XrApiLayerProperties *props = (XrApiLayerProperties *)(intptr_t)properties;

    // Call the OpenXR function
    XrResult result = xrEnumerateApiLayerProperties(capacity, countOutput, props);

    // Return the result as a jint
    return (jint)result;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_Library
 * Method:    nxrEnumerateInstanceExtensionProperties
 * Signature: (Ljava/lang/String;IJJ)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_Library_nxrEnumerateInstanceExtensionProperties
  (JNIEnv *env, jobject obj, jstring layerName, jint propertyCapacityInput, jlong propertyCountOutput, jlong properties) {

    // Convert JNI parameters to OpenXR parameters
    uint32_t capacity = (uint32_t)propertyCapacityInput;
    uint32_t *countOutput = (uint32_t *)(intptr_t)propertyCountOutput;
    XrExtensionProperties *props = (XrExtensionProperties *)(intptr_t)properties;

    // Convert Java string to C string
    const char *layer = NULL;
    if (layerName != NULL) {
        layer = (*env)->GetStringUTFChars(env, layerName, NULL);
    }

    // Call the OpenXR function
    XrResult result = xrEnumerateInstanceExtensionProperties(layer, capacity, countOutput, props);

    // Release the string if it was allocated
    if (layer != NULL) {
        (*env)->ReleaseStringUTFChars(env, layerName, layer);
    }

    // Return the result as a jint
    return (jint)result;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_Library
 * Method:    nxrInitializeLoaderKHR
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_Library_nxrInitializeLoaderKHR
  (JNIEnv *env, jobject obj, jlong loaderInitInfo) {

    // Convert JNI parameter to native pointer
    XrLoaderInitInfoBaseHeaderKHR *initInfo = (XrLoaderInitInfoBaseHeaderKHR *)(intptr_t)loaderInitInfo;

    // Attempt to resolve xrInitializeLoaderKHR dynamically (I don't fully understand why this indirection is required,
    // it might be to do with opewnXR not being fully booted yet, or it might be because it is not part of core OpenXR)
    PFN_xrInitializeLoaderKHR pfnInitializeLoader = NULL;
    XrResult resolveResult = xrGetInstanceProcAddr(
        XR_NULL_HANDLE,
        "xrInitializeLoaderKHR",
        (PFN_xrVoidFunction*)&pfnInitializeLoader
    );

    if (resolveResult != XR_SUCCESS || pfnInitializeLoader == NULL) {
        return (jint)XR_ERROR_FUNCTION_UNSUPPORTED;
    }

    // Call the function
    XrResult result = pfnInitializeLoader(initInfo);

    // Return result as jint
    return (jint)result;
}
