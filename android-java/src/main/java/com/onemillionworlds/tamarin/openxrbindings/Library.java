/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;


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
    public native int xrEnumerateApiLayerProperties(IntBuffer propertyCapacityInput, XrApiLayerProperties.Buffer properties);
}
