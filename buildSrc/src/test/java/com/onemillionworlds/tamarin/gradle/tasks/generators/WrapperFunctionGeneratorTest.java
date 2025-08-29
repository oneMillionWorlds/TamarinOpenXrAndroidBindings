package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WrapperFunctionGeneratorTest {

    @Test
    void generateWrapperFunction_xrEnumerateApiLayerProperties() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrEnumerateApiLayerProperties", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrEnumerateApiLayerProperties OpenXR function
                     *\s
                     * @param propertyCapacityInput (uint32_t)
                     * @param propertyCountOutput (uint32_t)
                     * @param properties (XrApiLayerProperties)
                     * @return The XrResult status code
                     */
                    public static XrResult xrEnumerateApiLayerProperties(int propertyCapacityInput, IntBufferView propertyCountOutput, XrApiLayerProperties.Buffer properties) {
                        long propertyCountOutputAddress = propertyCountOutput == null ? MemoryUtil.NULL : propertyCountOutput.address();
                        long propertiesAddress = properties == null ? MemoryUtil.NULL : properties.address();
                        return XrResult.fromValue(nxrEnumerateApiLayerProperties(propertyCapacityInput, propertyCountOutputAddress, propertiesAddress));
                    }
                    
                    public static native int nxrEnumerateApiLayerProperties(int propertyCapacityInput, long propertyCountOutput, long properties);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrGetInstanceProcAddr() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrGetInstanceProcAddr OpenXR function
                     *\s
                     * @param instance (XrInstance)
                     * @param name (char)
                     * @param function (PFN_xrVoidFunction)
                     * @return The XrResult status code
                     */
                    public static XrResult xrGetInstanceProcAddr(XrInstance instance, BufferAndAddress name, PointerBufferView function) {
                        long nameAddress = name == null ? MemoryUtil.NULL : name.address();
                        long functionAddress = function == null ? MemoryUtil.NULL : function.address();
                        return XrResult.fromValue(nxrGetInstanceProcAddr(instance.getRawHandle(), nameAddress, functionAddress));
                    }
                
                    public static native int nxrGetInstanceProcAddr(long instance, long name, long function);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrResultToString() {

        FunctionDefinition functionDefinition = new FunctionDefinition("xrResultToString", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrResult", "value", false, false, true, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "buffer", true, false, false, false, false, false, false, false, false, false).setExtraDocumentation("Required size XR_MAX_RESULT_STRING_SIZE"));

        String expectedValue = """
                    /**
                     * Wrapper for xrResultToString OpenXR function
                     *\s
                     * @param instance (XrInstance)
                     * @param value (XrResult)
                     * @param buffer (char) Required size XR_MAX_RESULT_STRING_SIZE
                     * @return The XrResult status code
                     */
                    public static XrResult xrResultToString(XrInstance instance, XrResult value, BufferAndAddress buffer) {
                        long bufferAddress = buffer == null ? MemoryUtil.NULL : buffer.address();
                        return XrResult.fromValue(nxrResultToString(instance.getRawHandle(), value.getValue(), bufferAddress));
                    }
                
                    public static native int nxrResultToString(long instance, int value, long buffer);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrGetSystem() {
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetSystem", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemGetInfo", "getInfo", true, true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", true, false, false, true, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrGetSystem OpenXR function
                     *\s
                     * @param instance (XrInstance)
                     * @param getInfo (XrSystemGetInfo)
                     * @param systemId (XrSystemId)
                     * @return The XrResult status code
                     */
                    public static XrResult xrGetSystem(XrInstance instance, XrSystemGetInfo getInfo, LongBufferView systemId) {
                        long getInfoAddress = getInfo == null ? MemoryUtil.NULL : getInfo.address();
                        long systemIdAddress = systemId == null ? MemoryUtil.NULL : systemId.address();
                        return XrResult.fromValue(nxrGetSystem(instance.getRawHandle(), getInfoAddress, systemIdAddress));
                    }
            
                    public static native int nxrGetSystem(long instance, long getInfo, long systemId);
                """;


        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(expectedFunctionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());

    }
    @Test
    void generateWrapperFunction_xrLocateSpace() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrLocateSpace", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "space", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "baseSpace", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrTime", "time", false, false, false, false, false, true, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpaceLocation", "location", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrLocateSpace OpenXR function
                     *\s
                     * @param space (XrSpace)
                     * @param baseSpace (XrSpace)
                     * @param time (XrTime)
                     * @param location (XrSpaceLocation)
                     * @return The XrResult status code
                     */
                    public static XrResult xrLocateSpace(XrSpace space, XrSpace baseSpace, long time, XrSpaceLocation location) {
                        long locationAddress = location == null ? MemoryUtil.NULL : location.address();
                        return XrResult.fromValue(nxrLocateSpace(space.getRawHandle(), baseSpace.getRawHandle(), time, locationAddress));
                    }
                
                    public static native int nxrLocateSpace(long space, long baseSpace, long time, long location);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrSetInputDeviceStateVector2fEXT() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrSetInputDeviceStateVector2fEXT", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "topLevelPath", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "inputSourcePath", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrVector2f", "state", false, false, false, false, false, false, false, false, true, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrSetInputDeviceStateVector2fEXT OpenXR function
                     *\s
                     * @param session (XrSession)
                     * @param topLevelPath (XrPath)
                     * @param inputSourcePath (XrPath)
                     * @param state (XrVector2f)
                     * @return The XrResult status code
                     */
                    public static XrResult xrSetInputDeviceStateVector2fEXT(XrSession session, long topLevelPath, long inputSourcePath, XrVector2f state) {
                        long stateAddress = state == null ? MemoryUtil.NULL : state.address();
                        return XrResult.fromValue(nxrSetInputDeviceStateVector2fEXT(session.getRawHandle(), topLevelPath, inputSourcePath, stateAddress));
                    }
                
                    public static native int nxrSetInputDeviceStateVector2fEXT(long session, long topLevelPath, long inputSourcePath, long state);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrEnumerateEnvironmentBlendModes() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrEnumerateEnvironmentBlendModes", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrViewConfigurationType", "viewConfigurationType", false, false, true, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "environmentBlendModeCapacityInput", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "environmentBlendModeCountOutput", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrEnvironmentBlendMode", "environmentBlendModes", true, false, true, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrEnumerateEnvironmentBlendModes OpenXR function
                     *\s
                     * @param instance (XrInstance)
                     * @param systemId (XrSystemId)
                     * @param viewConfigurationType (XrViewConfigurationType)
                     * @param environmentBlendModeCapacityInput (uint32_t)
                     * @param environmentBlendModeCountOutput (uint32_t)
                     * @param environmentBlendModes (XrEnvironmentBlendMode)
                     * @return The XrResult status code
                     */
                    public static XrResult xrEnumerateEnvironmentBlendModes(XrInstance instance, long systemId, XrViewConfigurationType viewConfigurationType, int environmentBlendModeCapacityInput, IntBufferView environmentBlendModeCountOutput, IntBufferView environmentBlendModes) {
                        long environmentBlendModeCountOutputAddress = environmentBlendModeCountOutput == null ? MemoryUtil.NULL : environmentBlendModeCountOutput.address();
                        long environmentBlendModesAddress = environmentBlendModes == null ? MemoryUtil.NULL : environmentBlendModes.address();
                        return XrResult.fromValue(nxrEnumerateEnvironmentBlendModes(instance.getRawHandle(), systemId, viewConfigurationType.getValue(), environmentBlendModeCapacityInput, environmentBlendModeCountOutputAddress, environmentBlendModesAddress));
                    }
                
                    public static native int nxrEnumerateEnvironmentBlendModes(long instance, long systemId, int viewConfigurationType, int environmentBlendModeCapacityInput, long environmentBlendModeCountOutput, long environmentBlendModes);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrEnumerateSwapchainFormats() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrEnumerateSwapchainFormats", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "formatCapacityInput", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "formatCountOutput", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("int64_t", "formats", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrEnumerateSwapchainFormats OpenXR function
                     *\s
                     * @param session (XrSession)
                     * @param formatCapacityInput (uint32_t)
                     * @param formatCountOutput (uint32_t)
                     * @param formats (int64_t)
                     * @return The XrResult status code
                     */
                    public static XrResult xrEnumerateSwapchainFormats(XrSession session, int formatCapacityInput, IntBufferView formatCountOutput, LongBufferView formats) {
                        long formatCountOutputAddress = formatCountOutput == null ? MemoryUtil.NULL : formatCountOutput.address();
                        long formatsAddress = formats == null ? MemoryUtil.NULL : formats.address();
                        return XrResult.fromValue(nxrEnumerateSwapchainFormats(session.getRawHandle(), formatCapacityInput, formatCountOutputAddress, formatsAddress));
                    }
                
                    public static native int nxrEnumerateSwapchainFormats(long session, int formatCapacityInput, long formatCountOutput, long formats);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrThermalGetTemperatureTrendEXT() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrThermalGetTemperatureTrendEXT", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPerfSettingsDomainEXT", "domain", false, false, true, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPerfSettingsNotificationLevelEXT", "notificationLevel", true, false, true, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("float", "tempHeadroom", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("float", "tempSlope", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrThermalGetTemperatureTrendEXT OpenXR function
                     *\s
                     * @param session (XrSession)
                     * @param domain (XrPerfSettingsDomainEXT)
                     * @param notificationLevel (XrPerfSettingsNotificationLevelEXT)
                     * @param tempHeadroom (float)
                     * @param tempSlope (float)
                     * @return The XrResult status code
                     */
                    public static XrResult xrThermalGetTemperatureTrendEXT(XrSession session, XrPerfSettingsDomainEXT domain, IntBufferView notificationLevel, FloatBufferView tempHeadroom, FloatBufferView tempSlope) {
                        long notificationLevelAddress = notificationLevel == null ? MemoryUtil.NULL : notificationLevel.address();
                        long tempHeadroomAddress = tempHeadroom == null ? MemoryUtil.NULL : tempHeadroom.address();
                        long tempSlopeAddress = tempSlope == null ? MemoryUtil.NULL : tempSlope.address();
                        return XrResult.fromValue(nxrThermalGetTemperatureTrendEXT(session.getRawHandle(), domain.getValue(), notificationLevelAddress, tempHeadroomAddress, tempSlopeAddress));
                    }
            
                    public static native int nxrThermalGetTemperatureTrendEXT(long session, int domain, long notificationLevel, long tempHeadroom, long tempSlope);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateWrapperFunction_xrLoadControllerModelMSFT() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrLoadControllerModelMSFT", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrControllerModelKeyMSFT", "modelKey", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "bufferCapacityInput", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "bufferCountOutput", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint8_t", "buffer", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                    /**
                     * Wrapper for xrLoadControllerModelMSFT OpenXR function
                     *\s
                     * @param session (XrSession)
                     * @param modelKey (XrControllerModelKeyMSFT)
                     * @param bufferCapacityInput (uint32_t)
                     * @param bufferCountOutput (uint32_t)
                     * @param buffer (uint8_t)
                     * @return The XrResult status code
                     */
                    public static XrResult xrLoadControllerModelMSFT(XrSession session, long modelKey, int bufferCapacityInput, IntBufferView bufferCountOutput, BufferAndAddress buffer) {
                        long bufferCountOutputAddress = bufferCountOutput == null ? MemoryUtil.NULL : bufferCountOutput.address();
                        long bufferAddress = buffer == null ? MemoryUtil.NULL : buffer.address();
                        return XrResult.fromValue(nxrLoadControllerModelMSFT(session.getRawHandle(), modelKey, bufferCapacityInput, bufferCountOutputAddress, bufferAddress));
                    }
            
                    public static native int nxrLoadControllerModelMSFT(long session, long modelKey, int bufferCapacityInput, long bufferCountOutput, long buffer);
                """;

        String actualValue = WrapperFunctionGenerator.generateWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }


}
