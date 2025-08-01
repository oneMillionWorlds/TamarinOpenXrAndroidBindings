/**
 * Example program demonstrating how to enumerate instance extension properties.
 */
package com.onemillionworlds.tamarin.openxrbindings.examples;

import com.onemillionworlds.tamarin.openxrbindings.Library;
import com.onemillionworlds.tamarin.openxrbindings.Struct;
import com.onemillionworlds.tamarin.openxrbindings.StructBuffer;
import com.onemillionworlds.tamarin.openxrbindings.XR10;
import com.onemillionworlds.tamarin.openxrbindings.XrApiLayerProperties;
import com.onemillionworlds.tamarin.openxrbindings.XrExtensionProperties;
import com.onemillionworlds.tamarin.openxrbindings.memory.JavaBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;

import java.nio.IntBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.memPutInt;
import static com.onemillionworlds.tamarin.openxrbindings.XrStructureType.XR_TYPE_EXTENSION_PROPERTIES;

public class EnumerateInstanceExtensionPropertiesExample {

    public static void main(String[] args) {
        // Create an instance of the OpenXR library
        Library openxr = new Library();

        // Use a memory stack for efficient memory management
        try (MemoryStack stack = MemoryStack.stackGet().push()) {
            // First call to get the count of runtime extensions (no layer specified)
            JavaBufferView<IntBuffer> numberOfExtensionsPointer = stack.mallocInt(1);

            checkResponseCode(openxr.xrEnumerateInstanceExtensionProperties(null, numberOfExtensionsPointer, null));
            int numExtensions = numberOfExtensionsPointer.getBufferView().get(0);

            System.out.println("Found " + numExtensions + " runtime extension properties");

            if (numExtensions > 0) {
                // Allocate buffer for the properties on the stack
                XrExtensionProperties.Buffer properties = XrExtensionProperties.calloc(numExtensions, stack);

                // Initialize each property with the default type
                for (int i = 0; i < numExtensions; i++) {
                    properties.get(i).type$Default();
                }

                // Second call to get the properties
                int result = openxr.xrEnumerateInstanceExtensionProperties(null, numberOfExtensionsPointer, properties);

                if (result >= 0) {
                    System.out.println("Successfully enumerated " + numExtensions + " runtime extension properties:");
                    for (int i = 0; i < numExtensions; i++) {
                        XrExtensionProperties prop = properties.get(i);
                        System.out.println("Extension " + i + ":");
                        System.out.println("  Name: " + prop.extensionNameString());
                        System.out.println("  Version: " + prop.extensionVersion());
                    }
                } else {
                    System.err.println("Failed to enumerate runtime extension properties: " + result);
                }
            }

            // Now let's enumerate extensions for each available API layer
            // First, get the count of API layers
            JavaBufferView<IntBuffer> numberOfLayersPointer = stack.mallocInt(1);
            checkResponseCode(openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, null));
            int numLayers = numberOfLayersPointer.getBufferView().get(0);

            if (numLayers > 0) {
                // Allocate buffer for the layer properties
                XrApiLayerProperties.Buffer layerProperties = prepareApiLayerProperties(stack, numLayers);

                // Get the layer properties
                checkResponseCode(openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, layerProperties));

                // For each layer, enumerate its extensions
                for (int i = 0; i < numLayers; i++) {
                    String layerName = layerProperties.get(i).layerNameString();
                    System.out.println("\nEnumerating extensions for layer: " + layerName);

                    // Reset the extension count
                    numberOfExtensionsPointer.getBufferView().put(0, 0);

                    // Get the count of extensions for this layer
                    checkResponseCode(openxr.xrEnumerateInstanceExtensionProperties(layerName, numberOfExtensionsPointer, null));
                    numExtensions = numberOfExtensionsPointer.getBufferView().get(0);

                    System.out.println("Found " + numExtensions + " extension properties for layer " + layerName);

                    if (numExtensions > 0) {
                        // Allocate buffer for the extension properties
                        XrExtensionProperties.Buffer extensionProperties = prepareExtensionProperties(stack, numExtensions);

                        // Get the extension properties
                        int layerExtResult = openxr.xrEnumerateInstanceExtensionProperties(layerName, numberOfExtensionsPointer, extensionProperties);

                        if (layerExtResult >= 0) {
                            System.out.println("Successfully enumerated " + numExtensions + " extension properties for layer " + layerName + ":");
                            for (int j = 0; j < numExtensions; j++) {
                                XrExtensionProperties prop = extensionProperties.get(j);
                                System.out.println("Extension " + j + ":");
                                System.out.println("  Name: " + prop.extensionNameString());
                                System.out.println("  Version: " + prop.extensionVersion());
                            }
                        } else {
                            System.err.println("Failed to enumerate extension properties for layer " + layerName + ": " + layerExtResult);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static XrExtensionProperties.Buffer prepareExtensionProperties(MemoryStack stack, int numExtensions) {
        return fill(
                XrExtensionProperties.calloc(numExtensions, stack),
                XrExtensionProperties.TYPE,
                XR_TYPE_EXTENSION_PROPERTIES
        );
    }

    public static XrApiLayerProperties.Buffer prepareApiLayerProperties(MemoryStack stack, int numLayers) {
        return fill(
                XrApiLayerProperties.calloc(numLayers, stack),
                XrApiLayerProperties.TYPE,
                XR10.XR_TYPE_API_LAYER_PROPERTIES
        );
    }

    static <S extends Struct<S>, T extends StructBuffer<S, T>> T fill(T buffer, int offset, int value) {
        long ptr    = buffer.address() + offset;
        int  stride = buffer.sizeof();
        for (long i = 0; i < buffer.limit(); i++) {
            memPutInt(ptr + i * stride, value);
        }
        return buffer;
    }

    public static void checkResponseCode(int result) throws IllegalStateException {
        if(result < 0){
            throw new IllegalStateException("OpenXR error: " + result);
        }
    }
}
