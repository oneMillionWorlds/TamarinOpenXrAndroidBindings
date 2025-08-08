package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionParserTest {

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
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine());

        assertEquals(expectedFunctionDefinition, functionDefinition);
    }

    @Test
    void parseFunction_charArray() throws IOException {
        BufferedReader testFunction1Reader = new BufferedReader(new StringReader(testFunction_charPointer));

        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine());

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
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrResult", "value", false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "buffer", true, false).setExtraDocumentation("Required size XR_MAX_RESULT_STRING_SIZE"));

        FunctionDefinition functionDefinition = FunctionParser.parseFunction(testFunction1Reader, testFunction1Reader.readLine());

        assertEquals(expectedFunctionDefinition, functionDefinition);
    }
}