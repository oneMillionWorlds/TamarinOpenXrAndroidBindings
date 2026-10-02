package com.onemillionworlds.tamarin.openxrbindings.thickc;

import java.util.logging.Logger;

import com.onemillionworlds.tamarin.openxrbindings.NativeLibraryLoader;
import com.onemillionworlds.tamarin.openxrbindings.XR10;
import com.onemillionworlds.tamarin.openxrbindings.enums.XrResult;
import com.onemillionworlds.tamarin.openxrbindings.handles.XrDebugUtilsMessengerEXT;
import com.onemillionworlds.tamarin.openxrbindings.handles.XrInstance;

import java.util.function.Consumer;

/**
 * This class contains calls to native code where we have written actual proper c, not just a thin binding around
 * an openXR call.
 *
 * <p>
 * Basically the weird stuff goes in here. Things that only make sense on Android (e.g. initialising the loader with
 * the activity) are in AndroidThickC in openxr-bindings-android
 * </p>
 */
public class ThickC {
    private static final Logger LOGGER = Logger.getLogger(ThickC.class.getName());


    static {
        NativeLibraryLoader.load();
    }

    /**
     * Sets up the OpenXR debug messenger with the specified message consumer.
     * This allows receiving debug messages from the OpenXR runtime.
     *
     * @param instance the OpenXR instance
     * @param messageConsumer a consumer that will receive debug messages as strings
     * @param resultCode an array of length 1 that will be filled with the result code (0 == XR_SUCCESS)
     * @return the handle to the created debug messenger, or 0 if creation failed
     */
    public static native long setupDebug(XrInstance instance, Consumer<String> messageConsumer, int[] resultCode);

    /**
     * Sets up the OpenXR debug messenger with the specified message consumer.
     * This allows receiving debug messages from the OpenXR runtime.
     * 
     * This is a convenience method that returns the XrDebugUtilsMessengerEXT object
     * instead of the raw handle.
     *
     * @param instance the OpenXR instance
     * @param messageConsumer a consumer that will receive debug messages as strings
     * @return the created debug messenger, or null if creation failed
     */
    public static XrDebugUtilsMessengerEXT setupDebugMessenger(XrInstance instance, Consumer<String> messageConsumer) {
        int[] resultCode = new int[1];
        long messengerHandle = setupDebug(instance, messageConsumer, resultCode);

        if (resultCode[0] != 0 || messengerHandle == 0) { // 0 == XR_SUCCESS
            LOGGER.warning("Failed to setup debug messenger, result code: " + resultCode[0] + ", handle: " + messengerHandle);
            return null;
        }

        return new XrDebugUtilsMessengerEXT(messengerHandle);
    }

    /**
     * Destroys the OpenXR debug messenger.
     * 
     * @param messenger the debug messenger to destroy
     * @return the result of the operation
     */
    public static XrResult destroyDebugMessenger(XrDebugUtilsMessengerEXT messenger) {
        if (messenger == null) {
            return XrResult.ERROR_HANDLE_INVALID;
        }

        return XR10.xrDestroyDebugUtilsMessengerEXT(messenger);
    }
}
