/**
 * Example program demonstrating how to enumerate API layer properties.
 */
package com.onemillionworlds.tamarin.openxrbindings.examples;

import com.onemillionworlds.tamarin.openxrbindings.Library;
import com.onemillionworlds.tamarin.openxrbindings.XrApiLayerProperties;
import com.onemillionworlds.tamarin.openxrbindings.MemoryStack;

public class EnumerateApiLayerPropertiesExample {

    public static void main(String[] args) {
        // Create an instance of the OpenXR library
        Library openxr = new Library();

        // Use a memory stack for efficient memory management
        try (MemoryStack stack = MemoryStack.stackGet().push()) {
            // First call to get the count
            int count = openxr.xrEnumerateApiLayerProperties(0, null);
            System.out.println("Found " + count + " API layer properties");

            if (count > 0) {
                // Allocate buffer for the properties on the stack
                XrApiLayerProperties.Buffer properties = XrApiLayerProperties.calloc(count, stack);

                // Initialize each property with the default type
                for (int i = 0; i < count; i++) {
                    properties.get(i).type$Default();
                }

                // Second call to get the properties
                int result = openxr.xrEnumerateApiLayerProperties(count, properties);

                if (result >= 0) {
                    System.out.println("Successfully enumerated " + result + " API layer properties:");
                    for (int i = 0; i < result; i++) {
                        XrApiLayerProperties prop = properties.get(i);
                        System.out.println("Layer " + i + ":");
                        System.out.println("  Name: " + prop.layerNameString());
                        System.out.println("  Spec Version: " + prop.specVersion());
                        System.out.println("  Layer Version: " + prop.layerVersion());
                        System.out.println("  Description: " + prop.descriptionString());
                    }
                } else {
                    System.err.println("Failed to enumerate API layer properties: " + result);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
