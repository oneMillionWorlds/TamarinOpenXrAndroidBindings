#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>
#include "../include/openxr/openxr.h"

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