/**
 * Example program demonstrating how to enumerate API layer properties.
 */
package com.onemillionworlds.tamarin.openxrbindings.examples;

import com.onemillionworlds.tamarin.openxrbindings.Library;
import com.onemillionworlds.tamarin.openxrbindings.Struct;
import com.onemillionworlds.tamarin.openxrbindings.StructBuffer;
import com.onemillionworlds.tamarin.openxrbindings.XR10;
import com.onemillionworlds.tamarin.openxrbindings.XrApiLayerProperties;
import com.onemillionworlds.tamarin.openxrbindings.MemoryStack;

import java.nio.IntBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.MemoryUtil.memPutInt;

public class EnumerateApiLayerPropertiesExample {

    public static void main(String[] args) {
        // Create an instance of the OpenXR library
        Library openxr = new Library();

        // Use a memory stack for efficient memory management
        try (MemoryStack stack = MemoryStack.stackGet().push()) {
            // First call to get the count
            IntBuffer numberOfLayersPointer = stack.mallocInt(1);

            checkResponseCode(openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, null));
            int numLayers = numberOfLayersPointer.get(0);

            System.out.println("Found " + numLayers + " API layer properties");

            if (numLayers > 0) {
                // Allocate buffer for the properties on the stack
                XrApiLayerProperties.Buffer properties = XrApiLayerProperties.calloc(numLayers, stack);

                // Initialize each property with the default type
                for (int i = 0; i < numLayers; i++) {
                    properties.get(i).type$Default();
                }

                // Second call to get the properties
                int result = openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, properties);

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

    private static LayerCheckResult makeLayersCheck(MemoryStack stack){
        Library openxr = new Library();

        IntBuffer numberOfLayersPointer = stack.mallocInt(1);

        boolean hasCoreValidationLayer = false;
        checkResponseCode(openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, null));
        int numLayers = numberOfLayersPointer.get(0);

        XrApiLayerProperties.Buffer pLayers = prepareApiLayerProperties(stack, numLayers);
        checkResponseCode(openxr.xrEnumerateApiLayerProperties(numberOfLayersPointer, pLayers));
        System.out.println("No. of XR layers available: " + numLayers);
        for (int index = 0; index < numLayers; index++) {
            XrApiLayerProperties layer = pLayers.get(index);

            String layerName = layer.layerNameString();
            System.out.println("Layer: " + layerName);
            if (layerName.equals("XR_APILAYER_LUNARG_core_validation")) {
                hasCoreValidationLayer = true;
            }
        };

        PointerBuffer wantedLayers;
        if (hasCoreValidationLayer) {
            wantedLayers = stack.callocPointer(1);
            // wantedLayers.put(0, stack.UTF8("XR_APILAYER_LUNARG_core_validation"));
            System.out.println("Enabling XR core validation");
        } else {
            System.out.println("XR core validation not available");
            wantedLayers = null;
        }
        return new LayerCheckResult(wantedLayers, hasCoreValidationLayer);
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


    public static class LayerCheckResult{
        PointerBuffer wantedLayers;
        boolean hasCoreValidationLayer;

        public LayerCheckResult(PointerBuffer wantedLayers, boolean hasCoreValidationLayer) {
            this.wantedLayers = wantedLayers;
            this.hasCoreValidationLayer = hasCoreValidationLayer;
        }
    }

}
