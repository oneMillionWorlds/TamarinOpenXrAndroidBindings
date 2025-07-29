#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>

#define TAG "MemoryUtil"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    nmemAlloc
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_nmemAlloc
  (JNIEnv *env, jclass cls, jlong size) {
    void *ptr = malloc((size_t)size);
    return (jlong)(intptr_t)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    nmemCalloc
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_nmemCalloc
  (JNIEnv *env, jclass cls, jlong size) {
    void *ptr = calloc(1, (size_t)size);
    return (jlong)(intptr_t)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    nmemFree
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_nmemFree
  (JNIEnv *env, jclass cls, jlong address) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr != NULL) {
        free(ptr);
    }
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memNewBuffer
 * Signature: (JI)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memNewBuffer
  (JNIEnv *env, jclass cls, jlong address, jint capacity) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr == NULL) {
        return NULL;
    }
    return (*env)->NewDirectByteBuffer(env, ptr, capacity);
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memAddress
 * Signature: (Ljava/nio/ByteBuffer;)J
 */
JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memAddress
  (JNIEnv *env, jclass cls, jobject buffer) {
    if (buffer == NULL) {
        return 0;
    }
    void *ptr = (*env)->GetDirectBufferAddress(env, buffer);
    return (jlong)(intptr_t)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memGetInt
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memGetInt
  (JNIEnv *env, jclass cls, jlong address) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr == NULL) {
        return 0;
    }
    return *(jint*)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memGetLong
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memGetLong
  (JNIEnv *env, jclass cls, jlong address) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr == NULL) {
        return 0;
    }
    return *(jlong*)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memGetAddress
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memGetAddress
  (JNIEnv *env, jclass cls, jlong address) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr == NULL) {
        return 0;
    }
    return *(jlong*)ptr;
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memPutInt
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memPutInt
  (JNIEnv *env, jclass cls, jlong address, jint value) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr != NULL) {
        *(jint*)ptr = value;
    }
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memPutLong
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memPutLong
  (JNIEnv *env, jclass cls, jlong address, jlong value) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr != NULL) {
        *(jlong*)ptr = value;
    }
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memPutAddress
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memPutAddress
  (JNIEnv *env, jclass cls, jlong address, jlong value) {
    void *ptr = (void*)(intptr_t)address;
    if (ptr != NULL) {
        *(jlong*)ptr = value;
    }
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memCopy
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memCopy
  (JNIEnv *env, jclass cls, jlong src, jlong dst, jlong size) {
    void *srcPtr = (void*)(intptr_t)src;
    void *dstPtr = (void*)(intptr_t)dst;
    if (srcPtr != NULL && dstPtr != NULL && size > 0) {
        memcpy(dstPtr, srcPtr, (size_t)size);
    }
}

/*
 * Class:     com_onemillionworlds_tamarin_openxrbindings_MemoryUtil
 * Method:    memUTF8
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_MemoryUtil_memUTF8
  (JNIEnv *env, jclass cls, jlong address) {
    char *ptr = (char*)(intptr_t)address;
    if (ptr == NULL) {
        return NULL;
    }
    return (*env)->NewStringUTF(env, ptr);
}