package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.enums.XrResult;
import com.onemillionworlds.tamarin.openxrbindings.handles.XrInstance;
import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Calls into the real desktop native library (extracted from the jar resources by NativeLibraryLoader), including the
 * statically linked OpenXR loader. None of this needs an OpenXR runtime (or headset)
 */
public class DesktopNativeLibraryTest {

    @Test
    public void nativeLibraryLoads() {
        assertEquals(42, MemoryUtil.testMultiply(6, 7));
    }

    @Test
    public void loaderCanBeCalled() {
        // the loader lists the API layers from their manifests, no runtime needed
        try (MemoryStack stack = MemoryStack.stackGet().push()) {
            IntBufferView count = stack.callocInt(1);
            assertEquals(XrResult.SUCCESS, XR10.xrEnumerateApiLayerProperties(0, count, null));
        }
    }

    @Test
    public void otherPlatformsFunctionsAreUnsupported() {
        // XR_KHR_opengl_es_enable is Android only, on desktop its function is a stub
        assertEquals(XrResult.ERROR_FUNCTION_UNSUPPORTED, XR10.xrGetOpenGLESGraphicsRequirementsKHR(new XrInstance(0), 0, null));
    }
}
