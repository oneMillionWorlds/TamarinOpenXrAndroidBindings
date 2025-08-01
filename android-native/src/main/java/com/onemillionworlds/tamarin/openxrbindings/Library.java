/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;


import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.JavaBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.IntBuffer;

/**
 * Main class for OpenXR bindings
 */
public class Library {

    static {
        System.loadLibrary("openxrjni");
    }

    /**
     * Enumerates available API layers
     * 
     * @param propertyCapacityInput pointer to the count of properties
     * @param properties Buffer to store the properties (can be null if just trying to get the count of properties).
     *                   Will have the result written to it (if not null)
     * @return  The error code (if any)
     */
    public int xrEnumerateApiLayerProperties(IntBufferView propertyCapacityInput, XrApiLayerProperties.Buffer properties){
        int remaining = properties == null ? 0 : propertyCapacityInput.getBufferView().remaining();
        long propertyCapacityAddress = propertyCapacityInput.getAddress();
        return nxrEnumerateApiLayerProperties(remaining, propertyCapacityAddress, properties == null ? MemoryUtil.NULL : properties.address);
    }

    public native int nxrEnumerateApiLayerProperties(int propertyCapacityInput, long propertyCountOutput, long properties);

    /**
     * Enumerates instance extension properties provided by the specified layer or runtime
     * 
     * @param layerName The name of the API layer to enumerate extensions from, or null to enumerate extensions provided by the OpenXR runtime
     * @param propertyCapacityInput pointer to the count of properties
     * @param properties Buffer to store the properties (can be null if just trying to get the count of properties).
     *                   Will have the result written to it (if not null)
     * @return  The error code (if any)
     */
    public int xrEnumerateInstanceExtensionProperties(String layerName, JavaBufferView<IntBuffer> propertyCapacityInput, XrExtensionProperties.Buffer properties){
        int remaining = properties == null ? 0 :  properties.remaining();
        long propertyCapacityAddress = propertyCapacityInput.getAddress();
        return nxrEnumerateInstanceExtensionProperties(layerName, remaining, propertyCapacityAddress, properties == null ? MemoryUtil.NULL : properties.address);
    }

    public native int nxrEnumerateInstanceExtensionProperties(String layerName, int propertyCapacityInput, long propertyCountOutput, long properties);

}
