package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.XR_STRUCTURE_TYPE_ENUM_VALUES;
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

        StructDefinition expectedStruct = new StructDefinition("XrVector2f", false);
        expectedStruct.addField(new StructField("float", "x", null, false, false, false, false,false,false,false,false,false,false, false));
        expectedStruct.addField(new StructField("float", "y", null, false, false, false, false,false,false,false,false,false,false, false));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine(), 
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS, 
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS, XR_STRUCTURE_TYPE_ENUM_VALUES);

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

        StructDefinition expectedStruct = new StructDefinition("XrSwapchainImageBaseHeader", false);
        expectedStruct.addField(new StructField("XrStructureType", "type", null, false, false, true, false,false,false,false,false,false,false, false));
        expectedStruct.addField(new StructField("void", "next", null, true, false, false, false,false,false,false,false,false,false, false));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS, XR_STRUCTURE_TYPE_ENUM_VALUES);

        assertEquals(expectedStruct, actualStruct);
    }

    @Test
    void passStruct_canBeItsOwnDefault() throws IOException {
        String structString = """
                typedef struct XrEventDataBuffer {
                    XrStructureType             type;
                    const void* XR_MAY_ALIAS    next;
                    uint8_t                     varying[4000];
                } XrEventDataBuffer;
                """;
        BufferedReader reader = new BufferedReader(new StringReader(structString));

        StructDefinition expectedStruct = new StructDefinition("XrEventDataBuffer", true);
        expectedStruct.addField(new StructField("XrStructureType", "type", null, false, false, true, false,false,false,false,false,false,false, false));
        expectedStruct.addField(new StructField("void", "next", null, true, true, false, false,false,false,false,false,false,false, false));
        expectedStruct.addField(new StructField("uint8_t", "varying", "4000", false, false, false, false,false,false,false,false,false,false, false));

        StructDefinition actualStruct = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS, XR_STRUCTURE_TYPE_ENUM_VALUES);

        assertEquals(expectedStruct, actualStruct);
    }


    @Test
    void createXrStructureTypeEnumValueForStruct(){
        assertEquals("XR_TYPE_SWAPCHAIN_IMAGE_OPENGL_KHR", StructParser.createXrStructureTypeEnumValueForStruct("XrSwapchainImageOpenGLKHR"));
        assertEquals("XR_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT", StructParser.createXrStructureTypeEnumValueForStruct("XrDebugUtilsMessengerCreateInfoEXT"));
        assertEquals("XR_TYPE_INPUT_SOURCE_LOCALIZED_NAME_GET_INFO", StructParser.createXrStructureTypeEnumValueForStruct("XrInputSourceLocalizedNameGetInfo"));
        assertEquals("XR_TYPE_COMPOSITION_LAYER_CYLINDER_KHR", StructParser.createXrStructureTypeEnumValueForStruct("XrCompositionLayerCylinderKHR"));
        assertEquals("XR_TYPE_GRAPHICS_BINDING_OPENGL_WIN32_KHR", StructParser.createXrStructureTypeEnumValueForStruct("XrGraphicsBindingOpenGLWin32KHR"));
        assertEquals("XR_TYPE_VISIBILITY_MASK_KHR", StructParser.createXrStructureTypeEnumValueForStruct("XrVisibilityMaskKHR"));
    }

}
