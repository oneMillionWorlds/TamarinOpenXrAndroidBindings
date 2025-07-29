/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;

/**
 * Java representation of XrResult enum
 */
public class XrResult {
    public static final int XR_SUCCESS = 0;
    public static final int XR_TIMEOUT_EXPIRED = 1;
    public static final int XR_SESSION_LOSS_PENDING = 3;
    public static final int XR_EVENT_UNAVAILABLE = 4;
    public static final int XR_SPACE_BOUNDS_UNAVAILABLE = 7;
    public static final int XR_SESSION_NOT_FOCUSED = 8;
    public static final int XR_FRAME_DISCARDED = 9;
    public static final int XR_ERROR_VALIDATION_FAILURE = -1;
    public static final int XR_ERROR_RUNTIME_FAILURE = -2;
    public static final int XR_ERROR_OUT_OF_MEMORY = -3;
    public static final int XR_ERROR_API_VERSION_UNSUPPORTED = -4;
    public static final int XR_ERROR_INITIALIZATION_FAILED = -6;
    public static final int XR_ERROR_FUNCTION_UNSUPPORTED = -7;
    public static final int XR_ERROR_FEATURE_UNSUPPORTED = -8;
    public static final int XR_ERROR_EXTENSION_NOT_PRESENT = -9;
    public static final int XR_ERROR_LIMIT_REACHED = -10;
    public static final int XR_ERROR_SIZE_INSUFFICIENT = -11;
    public static final int XR_ERROR_HANDLE_INVALID = -12;
    public static final int XR_ERROR_INSTANCE_LOST = -13;
    public static final int XR_ERROR_SESSION_RUNNING = -14;
    public static final int XR_ERROR_SESSION_NOT_RUNNING = -16;
    public static final int XR_ERROR_SESSION_LOST = -17;
    public static final int XR_ERROR_SYSTEM_INVALID = -18;
    public static final int XR_ERROR_PATH_INVALID = -19;
    public static final int XR_ERROR_PATH_COUNT_EXCEEDED = -20;
    public static final int XR_ERROR_PATH_FORMAT_INVALID = -21;
    public static final int XR_ERROR_PATH_UNSUPPORTED = -22;
    public static final int XR_ERROR_LAYER_INVALID = -23;
    public static final int XR_ERROR_LAYER_LIMIT_EXCEEDED = -24;
    public static final int XR_ERROR_SWAPCHAIN_RECT_INVALID = -25;
    public static final int XR_ERROR_SWAPCHAIN_FORMAT_UNSUPPORTED = -26;
    public static final int XR_ERROR_ACTION_TYPE_MISMATCH = -27;
    public static final int XR_ERROR_SESSION_NOT_READY = -28;
    public static final int XR_ERROR_SESSION_NOT_STOPPING = -29;
    public static final int XR_ERROR_TIME_INVALID = -30;
    public static final int XR_ERROR_REFERENCE_SPACE_UNSUPPORTED = -31;
    public static final int XR_ERROR_FILE_ACCESS_ERROR = -32;
    public static final int XR_ERROR_FILE_CONTENTS_INVALID = -33;
    public static final int XR_ERROR_FORM_FACTOR_UNSUPPORTED = -34;
    public static final int XR_ERROR_FORM_FACTOR_UNAVAILABLE = -35;
    public static final int XR_ERROR_API_LAYER_NOT_PRESENT = -36;
    public static final int XR_ERROR_CALL_ORDER_INVALID = -37;
    public static final int XR_ERROR_GRAPHICS_DEVICE_INVALID = -38;
    public static final int XR_ERROR_POSE_INVALID = -39;
    public static final int XR_ERROR_INDEX_OUT_OF_RANGE = -40;
    public static final int XR_ERROR_VIEW_CONFIGURATION_TYPE_UNSUPPORTED = -41;
    public static final int XR_ERROR_ENVIRONMENT_BLEND_MODE_UNSUPPORTED = -42;
    public static final int XR_ERROR_NAME_DUPLICATED = -44;
    public static final int XR_ERROR_NAME_INVALID = -45;
    
    /**
     * Checks if the result code indicates success
     * 
     * @param result The result code to check
     * @return true if the result indicates success, false otherwise
     */
    public static boolean XR_SUCCEEDED(int result) {
        return result >= 0;
    }
    
    /**
     * Checks if the result code indicates failure
     * 
     * @param result The result code to check
     * @return true if the result indicates failure, false otherwise
     */
    public static boolean XR_FAILED(int result) {
        return result < 0;
    }
    
    /**
     * Gets a string representation of the result code
     * 
     * @param result The result code
     * @return A string representation of the result code
     */
    public static String toString(int result) {
        switch (result) {
            case XR_SUCCESS: return "XR_SUCCESS";
            case XR_TIMEOUT_EXPIRED: return "XR_TIMEOUT_EXPIRED";
            case XR_SESSION_LOSS_PENDING: return "XR_SESSION_LOSS_PENDING";
            case XR_EVENT_UNAVAILABLE: return "XR_EVENT_UNAVAILABLE";
            case XR_SPACE_BOUNDS_UNAVAILABLE: return "XR_SPACE_BOUNDS_UNAVAILABLE";
            case XR_SESSION_NOT_FOCUSED: return "XR_SESSION_NOT_FOCUSED";
            case XR_FRAME_DISCARDED: return "XR_FRAME_DISCARDED";
            case XR_ERROR_VALIDATION_FAILURE: return "XR_ERROR_VALIDATION_FAILURE";
            case XR_ERROR_RUNTIME_FAILURE: return "XR_ERROR_RUNTIME_FAILURE";
            case XR_ERROR_OUT_OF_MEMORY: return "XR_ERROR_OUT_OF_MEMORY";
            case XR_ERROR_API_VERSION_UNSUPPORTED: return "XR_ERROR_API_VERSION_UNSUPPORTED";
            case XR_ERROR_INITIALIZATION_FAILED: return "XR_ERROR_INITIALIZATION_FAILED";
            case XR_ERROR_FUNCTION_UNSUPPORTED: return "XR_ERROR_FUNCTION_UNSUPPORTED";
            case XR_ERROR_FEATURE_UNSUPPORTED: return "XR_ERROR_FEATURE_UNSUPPORTED";
            case XR_ERROR_EXTENSION_NOT_PRESENT: return "XR_ERROR_EXTENSION_NOT_PRESENT";
            case XR_ERROR_LIMIT_REACHED: return "XR_ERROR_LIMIT_REACHED";
            case XR_ERROR_SIZE_INSUFFICIENT: return "XR_ERROR_SIZE_INSUFFICIENT";
            case XR_ERROR_HANDLE_INVALID: return "XR_ERROR_HANDLE_INVALID";
            case XR_ERROR_INSTANCE_LOST: return "XR_ERROR_INSTANCE_LOST";
            case XR_ERROR_SESSION_RUNNING: return "XR_ERROR_SESSION_RUNNING";
            case XR_ERROR_SESSION_NOT_RUNNING: return "XR_ERROR_SESSION_NOT_RUNNING";
            case XR_ERROR_SESSION_LOST: return "XR_ERROR_SESSION_LOST";
            case XR_ERROR_SYSTEM_INVALID: return "XR_ERROR_SYSTEM_INVALID";
            case XR_ERROR_PATH_INVALID: return "XR_ERROR_PATH_INVALID";
            case XR_ERROR_PATH_COUNT_EXCEEDED: return "XR_ERROR_PATH_COUNT_EXCEEDED";
            case XR_ERROR_PATH_FORMAT_INVALID: return "XR_ERROR_PATH_FORMAT_INVALID";
            case XR_ERROR_PATH_UNSUPPORTED: return "XR_ERROR_PATH_UNSUPPORTED";
            case XR_ERROR_LAYER_INVALID: return "XR_ERROR_LAYER_INVALID";
            case XR_ERROR_LAYER_LIMIT_EXCEEDED: return "XR_ERROR_LAYER_LIMIT_EXCEEDED";
            case XR_ERROR_SWAPCHAIN_RECT_INVALID: return "XR_ERROR_SWAPCHAIN_RECT_INVALID";
            case XR_ERROR_SWAPCHAIN_FORMAT_UNSUPPORTED: return "XR_ERROR_SWAPCHAIN_FORMAT_UNSUPPORTED";
            case XR_ERROR_ACTION_TYPE_MISMATCH: return "XR_ERROR_ACTION_TYPE_MISMATCH";
            case XR_ERROR_SESSION_NOT_READY: return "XR_ERROR_SESSION_NOT_READY";
            case XR_ERROR_SESSION_NOT_STOPPING: return "XR_ERROR_SESSION_NOT_STOPPING";
            case XR_ERROR_TIME_INVALID: return "XR_ERROR_TIME_INVALID";
            case XR_ERROR_REFERENCE_SPACE_UNSUPPORTED: return "XR_ERROR_REFERENCE_SPACE_UNSUPPORTED";
            case XR_ERROR_FILE_ACCESS_ERROR: return "XR_ERROR_FILE_ACCESS_ERROR";
            case XR_ERROR_FILE_CONTENTS_INVALID: return "XR_ERROR_FILE_CONTENTS_INVALID";
            case XR_ERROR_FORM_FACTOR_UNSUPPORTED: return "XR_ERROR_FORM_FACTOR_UNSUPPORTED";
            case XR_ERROR_FORM_FACTOR_UNAVAILABLE: return "XR_ERROR_FORM_FACTOR_UNAVAILABLE";
            case XR_ERROR_API_LAYER_NOT_PRESENT: return "XR_ERROR_API_LAYER_NOT_PRESENT";
            case XR_ERROR_CALL_ORDER_INVALID: return "XR_ERROR_CALL_ORDER_INVALID";
            case XR_ERROR_GRAPHICS_DEVICE_INVALID: return "XR_ERROR_GRAPHICS_DEVICE_INVALID";
            case XR_ERROR_POSE_INVALID: return "XR_ERROR_POSE_INVALID";
            case XR_ERROR_INDEX_OUT_OF_RANGE: return "XR_ERROR_INDEX_OUT_OF_RANGE";
            case XR_ERROR_VIEW_CONFIGURATION_TYPE_UNSUPPORTED: return "XR_ERROR_VIEW_CONFIGURATION_TYPE_UNSUPPORTED";
            case XR_ERROR_ENVIRONMENT_BLEND_MODE_UNSUPPORTED: return "XR_ERROR_ENVIRONMENT_BLEND_MODE_UNSUPPORTED";
            case XR_ERROR_NAME_DUPLICATED: return "XR_ERROR_NAME_DUPLICATED";
            case XR_ERROR_NAME_INVALID: return "XR_ERROR_NAME_INVALID";
            default: return "UNKNOWN_RESULT_CODE(" + result + ")";
        }
    }
}