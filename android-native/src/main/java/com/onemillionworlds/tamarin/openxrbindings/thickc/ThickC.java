package com.onemillionworlds.tamarin.openxrbindings.thickc;


import android.app.Activity;

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
}
