package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WrapperFunctionGeneratorTest {

    @Test
    void generateWrapperFunction_xrEnumerateApiLayerProperties() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrEnumerateApiLayerProperties", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrEnumerateApiLayerProperties OpenXR function
                     *\s
                     * @param propertyCapacityInput
                     * @param propertyCountOutput
                     * @param properties
                     * @return The error code (if any)
                     */
                    public int xrEnumerateApiLayerProperties(int propertyCapacityInput, IntBufferView propertyCountOutput, XrApiLayerProperties.Buffer properties) {
                        long propertyCountOutputAddress = propertyCountOutput == null ? MemoryUtil.NULL : propertyCountOutput.address();
                        long propertiesAddress = properties == null ? MemoryUtil.NULL : properties.address();
                        return nxrEnumerateApiLayerProperties(propertyCapacityInput, propertyCountOutputAddress, propertiesAddress);
                    }
                
                    public native int nxrEnumerateApiLayerProperties(int propertyCapacityInput, long propertyCountOutput, long properties);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrGetInstanceProcAddr() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrGetInstanceProcAddr OpenXR function
                     *\s
                     * @param instance
                     * @param name
                     * @param function
                     * @return The error code (if any)
                     */
                    public int xrGetInstanceProcAddr(int instance, BufferAndAddress name, PointerBufferView function) {
                        long nameAddress = name == null ? MemoryUtil.NULL : name.address();
                        long functionAddress = function == null ? MemoryUtil.NULL : function.address();
                        return nxrGetInstanceProcAddr(instance, nameAddress, functionAddress);
                    }
                
                    public native int nxrGetInstanceProcAddr(int instance, long name, long function);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrResultToString() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrResultToString", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrResult", "value", false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "buffer", true, false, false, false, false, false).setExtraDocumentation("Required size XR_MAX_RESULT_STRING_SIZE"));

        String expectedValue = """
                    /**
                     * Wrapper for xrResultToString OpenXR function
                     *\s
                     * @param instance
                     * @param value
                     * @param buffer Required size XR_MAX_RESULT_STRING_SIZE
                     * @return The error code (if any)
                     */
                    public int xrResultToString(int instance, XrResult value, BufferAndAddress buffer) {
                        long bufferAddress = buffer == null ? MemoryUtil.NULL : buffer.address();
                        return nxrResultToString(instance, value.getValue(), bufferAddress);
                    }
                
                    public native int nxrResultToString(int instance, int value, long buffer);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrGetSystem() {
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetSystem", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemGetInfo", "getInfo", true, true, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", true, false, false, true, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrGetSystem OpenXR function
                     *\s
                     * @param instance
                     * @param getInfo
                     * @param systemId
                     * @return The error code (if any)
                     */
                    public int xrGetSystem(int instance, XrSystemGetInfo.Buffer getInfo, LongBufferView systemId) {
                        long getInfoAddress = getInfo == null ? MemoryUtil.NULL : getInfo.address();
                        long systemIdAddress = systemId == null ? MemoryUtil.NULL : systemId.address();
                        return nxrGetSystem(instance, getInfoAddress, systemIdAddress);
                    }
                
                    public native int nxrGetSystem(int instance, long getInfo, long systemId);
                """;


        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(expectedFunctionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());

    }
}
