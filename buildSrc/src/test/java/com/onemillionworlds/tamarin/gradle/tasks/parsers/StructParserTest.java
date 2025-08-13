package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructParserTest {

    // Static constants for known types
    private static final List<String> KNOWN_ENUM_TYPES = Collections.singletonList("XrStructureType");
    private static final List<String> KNOWN_ATOMS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_INTS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_LONGS = Collections.emptyList();
    private static final List<String> KNOWN_HANDLES = Collections.emptyList();
    private static final List<String> KNOWN_FLAGS = Collections.emptyList();
    private static final List<String> KNOWN_STRUCTS = Collections.emptyList();
    
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
        expectedStruct.addField(new StructField("float", "x", null, false, false, false, false,false,false,false,false,false,false));
        expectedStruct.addField(new StructField("float", "y", null, false, false, false, false,false,false,false,false,false,false));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine(), 
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS, 
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS);

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
        expectedStruct.addField(new StructField("XrStructureType", "type", null, false, false, true, false,false,false,false,false,false,false ));
        expectedStruct.addField(new StructField("void", "next", null, true, false, false, false,false,false,false,false,false,false ));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS);

        assertEquals(expectedStruct, actualStruct);
    }


}
