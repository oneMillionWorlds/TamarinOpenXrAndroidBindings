/*
 * Platform support for the Tamarin OpenXR bindings' C.
 *
 * Include after defining the XR_USE_* platform/graphics API defines (and TAG, for logging) and before the OpenXR
 * headers. It provides:
 *  - Declarations of the external types openxr_platform.h uses for the enabled XR_USE_* defines. These are
 *    deliberately minimal opaque declarations (matching the real headers) rather than including EGL/X11/xcb/Windows
 *    headers, so the build doesn't need those platforms' development headers and nothing else is pulled in. Only
 *    include this in translation units that don't also include the real headers. Every non-pointer type here must
 *    also be in ParseOpenXr.EXTERNAL_TYPES so the Java side knows its size
 *  - LOGI/LOGE logging macros (logcat on Android, stderr for errors on desktop)
 */
#ifndef TAMARIN_PLATFORM_H
#define TAMARIN_PLATFORM_H

#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

#if defined(XR_USE_PLATFORM_EGL) || defined(XR_USE_GRAPHICS_API_OPENGL_ES)
/* EGL/egl.h */
typedef struct EGLDisplay_T* EGLDisplay;
typedef struct EGLConfig_T*  EGLConfig;
typedef struct EGLContext_T* EGLContext;
typedef unsigned int EGLenum;
#endif

#ifdef XR_USE_PLATFORM_WIN32
/* windows.h / unknwn.h. HDC and HGLRC are declared as DECLARE_HANDLE does; LARGE_INTEGER and IUnknown are only
 * used through pointers so can be incomplete */
typedef struct HDC__* HDC;
typedef struct HGLRC__* HGLRC;
typedef union _LARGE_INTEGER LARGE_INTEGER;
typedef struct IUnknown IUnknown;
#endif

#ifdef XR_USE_PLATFORM_XLIB
/* X11/Xlib.h and GL/glx.h */
typedef struct _XDisplay Display;
typedef unsigned long GLXDrawable;
typedef struct __GLXFBConfigRec* GLXFBConfig;
typedef struct __GLXcontextRec* GLXContext;
#endif

#ifdef XR_USE_PLATFORM_XCB
/* xcb/xcb.h and xcb/glx.h */
typedef struct xcb_connection_t xcb_connection_t;
typedef uint32_t xcb_visualid_t;
typedef uint32_t xcb_glx_fbconfig_t;
typedef uint32_t xcb_glx_drawable_t;
typedef uint32_t xcb_glx_context_t;
#endif

/* XR_USE_PLATFORM_WAYLAND only uses struct wl_display*, which needs no declaration */

#ifdef __cplusplus
} /* extern "C" */
#endif

#if defined(__ANDROID__)
#include <android/log.h>
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#else
#include <stdio.h>
/* info logging (e.g. which extension functions the runtime has) is only useful on a headset's logcat */
#define LOGI(...) ((void)0)
#define LOGE(...) do { fprintf(stderr, "[%s] ", TAG); fprintf(stderr, __VA_ARGS__); fprintf(stderr, "\n"); } while (0)
#endif

#endif /* TAMARIN_PLATFORM_H */
