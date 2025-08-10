package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

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

        StructDefinition expectedStruct = new StructDefinition("XrVector2f");
        expectedStruct.addField(new StructField("float", "x", null));
        expectedStruct.addField(new StructField("float", "y", null));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine());

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

        StructDefinition expectedStruct = new StructDefinition("XrSwapchainImageBaseHeader");
        expectedStruct.addField(new StructField("XrStructureType", "type", null));
        expectedStruct.addField(new StructField("void*", "next", null));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine());

        assertEquals(expectedStruct, actualStruct);
    }


}
