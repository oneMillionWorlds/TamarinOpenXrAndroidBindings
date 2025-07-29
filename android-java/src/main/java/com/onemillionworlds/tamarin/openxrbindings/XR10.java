/**
 * Constants for OpenXR 1.0.
 */
package com.onemillionworlds.tamarin.openxrbindings;

public class XR10 {
    // Structure types
    public static final int XR_TYPE_API_LAYER_PROPERTIES = 1;
    
    // Constants
    public static final int XR_MAX_API_LAYER_NAME_SIZE = 256;
    public static final int XR_MAX_API_LAYER_DESCRIPTION_SIZE = 256;
    public static final int XR_MAX_EXTENSION_NAME_SIZE = 128;
    public static final int XR_MAX_APPLICATION_NAME_SIZE = 128;
    public static final int XR_MAX_ENGINE_NAME_SIZE = 128;
    public static final int XR_MAX_RUNTIME_NAME_SIZE = 128;
    public static final int XR_MAX_PATH_LENGTH = 256;
    public static final int XR_MAX_STRUCTURE_NAME_SIZE = 64;
    public static final int XR_MAX_RESULT_STRING_SIZE = 64;
    
    // Pointer size
    public static final int POINTER_SIZE = 8; // 64-bit
}