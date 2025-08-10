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
            "XrActionType"
    );

    private static final List<String> knownAtoms = List.of(
            "XrSystemId"
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


    @Test
    void parseFunction_pointers() throws IOException {
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(testFunction_pointers));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrEnumerateApiLayerProperties", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false, false, false, false, false, false, false, true));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);

        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    @Test
    void parseFunction_charArray() throws IOException {
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(testFunction_charPointer));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false, false, false, false, false, false, false, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine(), knownEnums, knownAtoms, knownTypeDefInts, knownTypeDefLongs, knownHandles, knownFlags, knownStructs);

        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    @Test
    void parseFunction_weirdCharBuffer() throws IOException {

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

    @Test
    void parseFunction_atomType() throws IOException {
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


    @Test
    void parseFunction_typeDefLong() throws IOException {

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

    @Test
    void parseFunction_structByValue() throws IOException {
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


}
