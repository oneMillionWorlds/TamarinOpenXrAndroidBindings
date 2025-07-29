#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>
#include "../../include/openxr/openxr.h"

#define TAG "OpenXRJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_Library
 * Method:    xrEnumerateApiLayerProperties
 * Signature: (ILcom/onemillionworlds/tamarin/openxrbindings/XrApiLayerProperties$Buffer;)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_Library_xrEnumerateApiLayerProperties
  (JNIEnv *env, jobject obj, jint propertyCapacityInput, jobject properties) {

    LOGI("Calling xrEnumerateApiLayerProperties with capacity %d", propertyCapacityInput);

    // Get the count of available properties
    uint32_t propertyCountOutput = 0;
    XrResult result = xrEnumerateApiLayerProperties(0, &propertyCountOutput, NULL);

    if (result != XR_SUCCESS) {
        LOGE("Failed to get property count: %d", result);
        return result;
    }

    LOGI("Found %d API layer properties", propertyCountOutput);

    // If the caller just wants the count, return it
    if (propertyCapacityInput == 0 || properties == NULL) {
        return propertyCountOutput;
    }

    // Get the memory address of the properties buffer
    jclass bufferClass = (*env)->GetObjectClass(env, properties);
    jmethodID addressMethod = (*env)->GetMethodID(env, bufferClass, "address", "()J");
    jlong address = (*env)->CallLongMethod(env, properties, addressMethod);

    if (address == 0) {
        LOGE("Failed to get buffer address");
        return XR_ERROR_VALIDATION_FAILURE;
    }

    // Cast the address to XrApiLayerProperties*
    XrApiLayerProperties *xrProperties = (XrApiLayerProperties*)address;

    // Initialize the properties
    for (uint32_t i = 0; i < propertyCapacityInput; i++) {
        xrProperties[i].type = XR_TYPE_API_LAYER_PROPERTIES;
        xrProperties[i].next = NULL;
    }

    // Get the properties
    result = xrEnumerateApiLayerProperties(propertyCapacityInput, &propertyCountOutput, xrProperties);

    if (result != XR_SUCCESS) {
        LOGE("Failed to get properties: %d", result);
        return result;
    }

    // Return the number of properties
    return propertyCountOutput;
}
