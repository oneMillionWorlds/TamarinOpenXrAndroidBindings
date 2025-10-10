/*
 * Manual handle definitions for EGL types used by Tamarin OpenXR bindings.
 * This header provides minimal opaque-handle typedefs when platform EGL headers
 * are not available to the generated code. Keep this lightweight and isolated.
 */

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Define EGL handles as opaque pointers, mirroring the style used by OpenXR
 * (XR_DEFINE_HANDLE(object) => typedef struct object##_T* object;)
 * We deliberately do not include <EGL/egl.h> here to avoid NDK/SDK coupling.
 * This header should only be included in translation units that do not also
 * include real EGL headers to prevent redefinition conflicts.
 */

typedef struct EGLDisplay_T* EGLDisplay;
typedef struct EGLConfig_T*  EGLConfig;
typedef struct EGLContext_T* EGLContext;

/* Basic EGL integral enum type, as used by some OpenXR-related structs. */
typedef unsigned int EGLenum;

#ifdef __cplusplus
} /* extern "C" */
#endif

