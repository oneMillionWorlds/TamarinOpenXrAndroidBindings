package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.CreateStructs;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

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
        String firstLine = reader.readLine();

        System.out.println("[DEBUG_LOG] First line: '" + firstLine + "'");

        CreateStructs.StructDefinition expectedStruct = new CreateStructs.StructDefinition("XrVector2f");
        expectedStruct.addField(new CreateStructs.StructField("float", "x", null));
        expectedStruct.addField(new CreateStructs.StructField("float", "y", null));

        CreateStructs.StructDefinition actualStruct = StructParser.parseStruct(reader, firstLine);

        assertEquals(expectedStruct.getName(), actualStruct.getName());
        assertEquals(expectedStruct.getFields().size(), actualStruct.getFields().size());

        for (int i = 0; i < expectedStruct.getFields().size(); i++) {
            CreateStructs.StructField expectedField = expectedStruct.getFields().get(i);
            CreateStructs.StructField actualField = actualStruct.getFields().get(i);

            assertEquals(expectedField.getType(), actualField.getType());
            assertEquals(expectedField.getName(), actualField.getName());
            assertEquals(expectedField.getArraySizeConstant(), actualField.getArraySizeConstant());
        }
    }
}
