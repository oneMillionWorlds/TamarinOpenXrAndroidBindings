package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionParserTest {

    private static final List<String> knownEnums = List.of(
            "XrResult",
            "XrActionType",
            "XrEnvironmentBlendMode",
            "XrViewConfigurationType",
            "XrPerfSettingsDomainEXT",
            "XrPerfSettingsNotificationLevelEXT"
    );

    private static final List<String> knownAtoms = List.of(
            "XrSystemId",
            "XrControllerModelKeyMSFT"
    );

    private static final List<String> knownTypeDefInts = List.of(
            "XrBool32"
    );

    private static final List<String> knownTypeDefLongs = List.of(
            "XrTime",
            "XrDuration"
    );

    private static final List<String> knownHandles = List.of(
            "XrInstance",
            "XrSession",
            "XrSpace",
            "XrAction",
            "XrSwapchain",
            "XrActionSet"
    );

    private static final List<String> knownFlags = List.of(
            "XrDebugUtilsMessageSeverityFlagsEXT",
            "XrDebugUtilsMessageTypeFlagsEXT"
    );

    private static final List<String> knownStructs = List.of(
            "XrApiLayerProperties",
            "XrSystemGetInfo",
            "XrSpaceLocation",
            "XrVector2f"
    );

    private static final String testFunction_pointers = """
            XRAPI_ATTR XrResult XRAPI_CALL xrEnumerateApiLayerProperties(
                uint32_t                                    propertyCapacityInput,
                uint32_t*                                   propertyCountOutput,
                XrApiLayerProperties*                       properties);
            random other garbage
            """;

    private static final String testFunction_charPointer = """
            XRAPI_ATTR XrResult XRAPI_CALL xrGetInstanceProcAddr(
                XrInstance                                  instance,
                const char*                                 name,
                PFN_xrVoidFunction*                         function);
                random other garbage
            """;


    /**
     * This test tests pointers parsing for the xrEnumerateApiLayerProperties function.
     */
    @Test
    void parseFunction_xrEnumerateApiLayerProperties() throws IOException {
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(testFunction_pointers));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrEnumerateApiLayerProperties", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false, false, false, false, false, false, false, true));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
    
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    /**
     * This test tests char array parsing for the xrGetInstanceProcAddr function.
     */
    @Test
    void parseFunction_xrGetInstanceProcAddr() throws IOException {
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(testFunction_charPointer));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false, false, false, false, false, false, false, false));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
    
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    /**
     * This test tests weird char buffer parsing for the xrResultToString function.
     */
    @Test
    void parseFunction_xrResultToString() throws IOException {
    
        String xrResultToString = """
            XRAPI_ATTR XrResult XRAPI_CALL xrResultToString(
                XrInstance                                  instance,
                XrResult                                    value,
                char                                        buffer[XR_MAX_RESULT_STRING_SIZE]);
            random other garbage
            """;
    
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(xrResultToString));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrResultToString", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrResult", "value", false, false, true, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "buffer", true, false, false, false, false, false, false, false, false).setExtraDocumentation("Required size XR_MAX_RESULT_STRING_SIZE"));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
    
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    /**
     * This test tests atom type parsing for the xrGetSystem function.
     */
    @Test
    void parseFunction_xrGetSystem() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrGetSystem(
                    XrInstance                                  instance,
                    const XrSystemGetInfo*                      getInfo,
                    XrSystemId*                                 systemId);
                random other garbage
                """;
    
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(functionString));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetSystem", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemGetInfo", "getInfo", true, true, false, false, false, false, false, false, true));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", true, false, false, true, false, false, false, false, false));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    
    /**
     * This test tests typedef long parsing for the xrLocateSpace function.
     */
    @Test
    void parseFunction_xrLocateSpace() throws IOException {
    
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrLocateSpace(
                    XrSpace                                     space,
                    XrSpace                                     baseSpace,
                    XrTime                                      time,
                    XrSpaceLocation*                            location);
                """;
    
        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrLocateSpace", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "space", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "baseSpace", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrTime", "time", false, false, false, false, false, true, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpaceLocation", "location", true, false, false, false, false, false, false, false, true));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    /**
     * This test tests struct by value parsing for the xrSetInputDeviceStateVector2fEXT function.
     */
    @Test
    void parseFunction_xrSetInputDeviceStateVector2fEXT() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrSetInputDeviceStateVector2fEXT(
                    XrSession                                   session,
                    XrPath                                      topLevelPath,
                    XrPath                                      inputSourcePath,
                    XrVector2f                                  state);
                random other garbage
                """;
    
        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));
    
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrSetInputDeviceStateVector2fEXT", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "topLevelPath", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "inputSourcePath", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrVector2f", "state", false, false, false, false, false, false, false, false, true));
    
        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
    
    /**
     * This test tests pointer to enum parsing for the xrEnumerateEnvironmentBlendModes function.
     */
    @Test
    void parseFunction_xrEnumerateEnvironmentBlendModes() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrEnumerateEnvironmentBlendModes(
                    XrInstance                                  instance,
                    XrSystemId                                  systemId,
                    XrViewConfigurationType                     viewConfigurationType,
                    uint32_t                                    environmentBlendModeCapacityInput,
                    uint32_t*                                   environmentBlendModeCountOutput,
                    XrEnvironmentBlendMode*                     environmentBlendModes);
                """;

        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrEnumerateEnvironmentBlendModes", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", false, false, false, true, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrViewConfigurationType", "viewConfigurationType", false, false, true, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "environmentBlendModeCapacityInput", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "environmentBlendModeCountOutput", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrEnvironmentBlendMode", "environmentBlendModes", true, false, true, false, false, false, false, false, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    /**
     * This test tests pointer to long (int64_t*) parsing for the xrEnumerateSwapchainFormats function.
     */
    @Test
    void parseFunction_xrEnumerateSwapchainFormats() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrEnumerateSwapchainFormats(
                    XrSession                                   session,
                    uint32_t                                    formatCapacityInput,
                    uint32_t*                                   formatCountOutput,
                    int64_t*                                    formats);
                """;

        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrEnumerateSwapchainFormats", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "formatCapacityInput", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "formatCountOutput", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("int64_t", "formats", true, false, false, false, false, false, false, false, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    /**
     * This test tests float pointer parsing for the xrThermalGetTemperatureTrendEXT function.
     */
    @Test
    void parseFunction_xrThermalGetTemperatureTrendEXT() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrThermalGetTemperatureTrendEXT(
                    XrSession                                   session,
                    XrPerfSettingsDomainEXT                     domain,
                    XrPerfSettingsNotificationLevelEXT*         notificationLevel,
                    float*                                      tempHeadroom,
                    float*                                      tempSlope);
                """;

        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrThermalGetTemperatureTrendEXT", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPerfSettingsDomainEXT", "domain", false, false, true, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPerfSettingsNotificationLevelEXT", "notificationLevel", true, false, true, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("float", "tempHeadroom", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("float", "tempSlope", true, false, false, false, false, false, false, false, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    /**
     * This test tests byte pointer (uint8_t*) parsing for the xrLoadControllerModelMSFT function.
     */
    @Test
    void parseFunction_xrLoadControllerModelMSFT() throws IOException {
        String functionString = """
                XRAPI_ATTR XrResult XRAPI_CALL xrLoadControllerModelMSFT(
                    XrSession                                   session,
                    XrControllerModelKeyMSFT                    modelKey,
                    uint32_t                                    bufferCapacityInput,
                    uint32_t*                                   bufferCountOutput,
                    uint8_t*                                    buffer);
                """;

        BufferedReader testFunctionReader = new BufferedReader(new StringReader(functionString));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrLoadControllerModelMSFT", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrControllerModelKeyMSFT", "modelKey", false, false, false, true, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "bufferCapacityInput", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "bufferCountOutput", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint8_t", "buffer", true, false, false, false, false, false, false, false, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunctionReader, testFunctionReader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);
        assertEquals(expectedFunctionDefinition, functionDefinition);
    }


}
