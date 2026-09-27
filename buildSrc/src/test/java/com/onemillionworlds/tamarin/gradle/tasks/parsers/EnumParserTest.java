package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumParserTest {

    @Test
    void parseEnum_nameContainingDigits() throws IOException {
        String enumString = """
                typedef enum XrFaceExpressionSet2FB {
                    XR_FACE_EXPRESSION_SET2_DEFAULT_FB = 0,
                    XR_FACE_EXPRESSION_SET_2FB_MAX_ENUM_FB = 0x7FFFFFFF
                } XrFaceExpressionSet2FB;
                """;
        BufferedReader reader = new BufferedReader(new StringReader(enumString));
        String firstLine = reader.readLine();

        EnumDefinition enumDefinition = EnumParser.parseEnum(reader, firstLine);

        assertEquals("XrFaceExpressionSet2FB", enumDefinition.getName());
        assertEquals(
                List.of("XR_FACE_EXPRESSION_SET2_DEFAULT_FB", "XR_FACE_EXPRESSION_SET_2FB_MAX_ENUM_FB"),
                enumDefinition.getValues().stream().map(EnumDefinition.EnumValue::getName).toList());
    }
}
