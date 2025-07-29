/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;


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
     * @param propertyCapacityInput The maximum number of properties to retrieve
     * @param properties Buffer to store the properties
     * @return The number of properties retrieved or required
     */
    public native int xrEnumerateApiLayerProperties(int propertyCapacityInput, XrApiLayerProperties.Buffer properties);
}
