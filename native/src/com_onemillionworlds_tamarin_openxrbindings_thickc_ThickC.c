#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>

#define TAG "Library"
#define XR_EXTENSION_PROTOTYPES

#include "tamarinPlatform.h"
#include <openxr/openxr.h>

// Global variables for debug messenger
static JavaVM* g_javaVM = NULL;
static jobject g_messageConsumer = NULL;
static jmethodID g_acceptMethod = NULL;

// Debug messenger callback function
static XrBool32 XRAPI_PTR debugMessengerCallback(
    XrDebugUtilsMessageSeverityFlagsEXT messageSeverity,
    XrDebugUtilsMessageTypeFlagsEXT messageTypes,
    const XrDebugUtilsMessengerCallbackDataEXT* callbackData,
    void* userData) {

    // Get the message string
    const char* message = callbackData->message;

    // Log the message locally
    LOGI("XR Debug: %s", message);

    // Forward to Java consumer if available
    if (g_javaVM != NULL && g_messageConsumer != NULL && g_acceptMethod != NULL) {
        JNIEnv* env;
        jint result = (*g_javaVM)->GetEnv(g_javaVM, (void**)&env, JNI_VERSION_1_6);

        if (result == JNI_OK) {
            // Create a Java string from the message
            jstring jMessage = (*env)->NewStringUTF(env, message);

            // Call the accept method on the consumer
            (*env)->CallVoidMethod(env, g_messageConsumer, g_acceptMethod, jMessage);

            // Clean up the local reference
            (*env)->DeleteLocalRef(env, jMessage);
        } else if (result == JNI_EDETACHED) {
            // Thread is detached, attach it. (void*) as Android's jni.h takes a JNIEnv** but the JDK's a void**
            if ((*g_javaVM)->AttachCurrentThread(g_javaVM, (void*)&env, NULL) == JNI_OK) {
                // Create a Java string from the message
                jstring jMessage = (*env)->NewStringUTF(env, message);

                // Call the accept method on the consumer
                (*env)->CallVoidMethod(env, g_messageConsumer, g_acceptMethod, jMessage);

                // Clean up the local reference
                (*env)->DeleteLocalRef(env, jMessage);

                // Detach the thread when done
                (*g_javaVM)->DetachCurrentThread(g_javaVM);
            }
        }
    }

    // Return XR_FALSE to indicate the message was handled
    return XR_FALSE;
}


JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC_setupDebug
  (JNIEnv* env, jclass clazz, jobject instance, jobject messageConsumer, jintArray resultCodeArray) {

    // The debug callback may come in on any thread, it needs the JavaVM to get a JNIEnv there
    if (g_javaVM == NULL && (*env)->GetJavaVM(env, &g_javaVM) != 0) {
        g_javaVM = NULL;
        LOGE("Failed to get the JavaVM");
        if (resultCodeArray != NULL) {
            jint resultCode = (jint)XR_ERROR_INITIALIZATION_FAILED;
            (*env)->SetIntArrayRegion(env, resultCodeArray, 0, 1, &resultCode);
        }
        return 0;
    }

    // Clean up any existing consumer
    if (g_messageConsumer != NULL) {
        (*env)->DeleteGlobalRef(env, g_messageConsumer);
        g_messageConsumer = NULL;
    }

    // Store the message consumer as a global reference
    if (messageConsumer != NULL) {
        g_messageConsumer = (*env)->NewGlobalRef(env, messageConsumer);

        // Get the Consumer.accept method ID
        jclass consumerClass = (*env)->GetObjectClass(env, g_messageConsumer);
        g_acceptMethod = (*env)->GetMethodID(env, consumerClass, "accept", "(Ljava/lang/Object;)V");

        if (g_acceptMethod == NULL) {
            LOGE("Failed to get Consumer.accept method ID");
            // Set result code
            if (resultCodeArray != NULL) {
                jint resultCode = (jint)XR_ERROR_INITIALIZATION_FAILED;
                (*env)->SetIntArrayRegion(env, resultCodeArray, 0, 1, &resultCode);
            }
            return 0; // Return 0 handle on failure
        }

        // Clean up local reference
        (*env)->DeleteLocalRef(env, consumerClass);
    }

    // Get the instance handle
    jclass instanceClass = (*env)->GetObjectClass(env, instance);
    jmethodID getRawHandleMethod = (*env)->GetMethodID(env, instanceClass, "getRawHandle", "()J");

    if (getRawHandleMethod == NULL) {
        LOGE("Failed to get XrInstance.getRawHandle method ID");
        // Set result code
        if (resultCodeArray != NULL) {
            jint resultCode = (jint)XR_ERROR_INITIALIZATION_FAILED;
            (*env)->SetIntArrayRegion(env, resultCodeArray, 0, 1, &resultCode);
        }
        return 0; // Return 0 handle on failure
    }

    jlong instanceHandle = (*env)->CallLongMethod(env, instance, getRawHandleMethod);

    // Clean up local reference
    (*env)->DeleteLocalRef(env, instanceClass);

    // Get the xrCreateDebugUtilsMessengerEXT function pointer
    PFN_xrCreateDebugUtilsMessengerEXT xrCreateDebugUtilsMessengerEXT;
    XrResult result = xrGetInstanceProcAddr(
        (XrInstance)instanceHandle,
        "xrCreateDebugUtilsMessengerEXT",
        (PFN_xrVoidFunction*)&xrCreateDebugUtilsMessengerEXT);

    if (result != XR_SUCCESS || xrCreateDebugUtilsMessengerEXT == NULL) {
        LOGE("Failed to get xrCreateDebugUtilsMessengerEXT function pointer: %d", result);
        // Set result code
        if (resultCodeArray != NULL) {
            jint resultCode = (jint)result;
            (*env)->SetIntArrayRegion(env, resultCodeArray, 0, 1, &resultCode);
        }
        return 0; // Return 0 handle on failure
    }

    // Create the debug utils messenger
    XrDebugUtilsMessengerCreateInfoEXT createInfo = {
        .type = XR_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT,
        .next = NULL,
        .messageSeverities = 
            XR_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT |
            XR_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT |
            XR_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT,
        .messageTypes = 
            XR_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT |
            XR_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT |
            XR_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT |
            XR_DEBUG_UTILS_MESSAGE_TYPE_CONFORMANCE_BIT_EXT,
        .userCallback = debugMessengerCallback,
        .userData = NULL
    };

    XrDebugUtilsMessengerEXT messenger;
    result = xrCreateDebugUtilsMessengerEXT((XrInstance)instanceHandle, &createInfo, &messenger);

    // Set result code
    if (resultCodeArray != NULL) {
        jint resultCode = (jint)result;
        (*env)->SetIntArrayRegion(env, resultCodeArray, 0, 1, &resultCode);
    }

    if (result != XR_SUCCESS) {
        LOGE("Failed to create debug utils messenger: %d", result);
        return 0; // Return 0 handle on failure
    } else {
        LOGI("Debug utils messenger created successfully");
        return (jlong)(uintptr_t)messenger; // Return the messenger handle
    }
}
