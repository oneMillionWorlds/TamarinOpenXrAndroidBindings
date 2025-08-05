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
 * Method:    nxrGetInstanceProperties
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_Library_nxrGetInstanceProperties
  (JNIEnv *env, jobject obj, jlong instance, jlong instanceProperties) {

    // Convert JNI parameters to OpenXR parameters
    XrInstance xrInstance = (XrInstance)(intptr_t)instance;
    XrInstanceProperties *props = (XrInstanceProperties *)(intptr_t)instanceProperties;

    // Call the OpenXR function
    XrResult result = xrGetInstanceProperties(xrInstance, props);

    // Return the result as a jint
    return (jint)result;
}
