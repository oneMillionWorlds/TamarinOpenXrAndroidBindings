package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.CreateStructs;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StructParserTest {

    @Test
    void parseStruct() throws IOException {
        String structString = """
                typedef struct XrVector2f {
                    float    x;
                    float    y;
                } XrVector2f;
                """;

        BufferedReader reader = new BufferedReader(new StringReader(structString));

        CreateStructs.StructDefinition expectedStruct = new CreateStructs.StructDefinition("XrVector2f");
        expectedStruct.addField(new CreateStructs.StructField("float", "x", null));
        expectedStruct.addField(new CreateStructs.StructField("float", "y", null));

        CreateStructs.StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine());

        assertEquals(expectedStruct, actualStruct);
    }

    @Test
    void passStruct_mayAlias() throws IOException {
        String structString = """
                typedef struct XR_MAY_ALIAS XrSwapchainImageBaseHeader {
                    XrStructureType       type;
                    void* XR_MAY_ALIAS    next;
                } XrSwapchainImageBaseHeader;
                """;

        BufferedReader reader = new BufferedReader(new StringReader(structString));

        CreateStructs.StructDefinition expectedStruct = new CreateStructs.StructDefinition("XrSwapchainImageBaseHeader");
        expectedStruct.addField(new CreateStructs.StructField("XrStructureType", "type", null));
        expectedStruct.addField(new CreateStructs.StructField("void*", "next", null));

        CreateStructs.StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine());

        assertEquals(expectedStruct, actualStruct);
    }


}
