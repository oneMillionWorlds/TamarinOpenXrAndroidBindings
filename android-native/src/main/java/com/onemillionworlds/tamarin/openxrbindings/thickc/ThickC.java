package com.onemillionworlds.tamarin.openxrbindings.thickc;


import android.app.Activity;

import com.onemillionworlds.tamarin.openxrbindings.handles.XrInstance;

import java.util.function.Consumer;

/**
 * This class contains calls to native code where we have written actual proper c, not just a thin binding around
 * an openXR call.
 *
 * <p>
 * Basically the weird stuff goes in here
 * </p>
 */
public class ThickC {

    static {
        System.loadLibrary("openxrjni"); // or whatever your .so is named
    }

    /**
     * Initializes the OpenXR loader on Android. This must be called before any other OpenXR call.
     *
     * @param activity the Android activity (used internally to get application context)
     * @return the result code from xrInitializeLoaderKHR (0 == XR_SUCCESS)
     */
    public static native int initializeLoader(Activity activity);

    /**
     * Sets up the OpenXR debug messenger with the specified message consumer.
     * This allows receiving debug messages from the OpenXR runtime.
     *
     * @param instance the OpenXR instance
     * @param messageConsumer a consumer that will receive debug messages as strings
     * @return the result code (0 == XR_SUCCESS)
     */
    public static native int setupDebug(XrInstance instance, Consumer<String> messageConsumer);
}
