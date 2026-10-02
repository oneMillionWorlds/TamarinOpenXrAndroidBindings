package com.onemillionworlds.tamarin.openxrbindings.thickc;

import android.content.Context;
import com.onemillionworlds.tamarin.openxrbindings.NativeLibraryLoader;
import com.onemillionworlds.tamarin.openxrbindings.memory.LongBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;

/**
 * The Android only parts of {@link ThickC} (hand-written native code, not thin bindings around OpenXR calls)
 */
public class AndroidThickC {

    static {
        NativeLibraryLoader.load();
    }

    /**
     * Initializes the OpenXR loader on Android. This must be called before any other OpenXR call.
     *
     * @param activityContext the Android activity application context
     * @return the result code from xrInitializeLoaderKHR (0 == XR_SUCCESS)
     */
    public static InitialisationData initializeLoader(Context activityContext){
        try(MemoryStack stack = MemoryStack.stackGet().push()){
            LongBufferView bufferView = stack.callocLong(2);
            long outBufferAddress = bufferView.address();
            int result = initializeLoader(activityContext, outBufferAddress);
            if(result != 0){
                throw new RuntimeException("Failed to initialize OpenXR loader, result code: " + result);
            }
            return new InitialisationData(bufferView.get(0), bufferView.get(1));
        }
    }


    /**
     * Initializes the OpenXR loader on Android. This must be called before any other OpenXR call.
     *
     * @param activityContext the Android activity application context
     * @param outbufferAddress the address of a long[2] buffer to receive the javaVm and activity context addresses
     * @return the result code from xrInitializeLoaderKHR (0 == XR_SUCCESS)
     */
    public static native int initializeLoader(Context activityContext, long outbufferAddress);
}
