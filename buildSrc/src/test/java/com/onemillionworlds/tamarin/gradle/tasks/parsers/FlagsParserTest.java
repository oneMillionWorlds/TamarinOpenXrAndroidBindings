package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlagsParserTest {

    @Test
    void parseFlags_validFlags() {
        String line = "typedef XrFlags64 XrDebugUtilsMessageSeverityFlagsEXT;";
        Optional<String> result = FlagsParser.parseFlags(line);
        assertEquals(Optional.of("XrDebugUtilsMessageSeverityFlagsEXT"), result);
    }

    @Test
    void parseFlags_validFlagsWithWhitespace() {
        String line = "typedef XrFlags64  XrDebugUtilsMessageTypeFlagsEXT ;";
        Optional<String> result = FlagsParser.parseFlags(line);
        assertEquals(Optional.of("XrDebugUtilsMessageTypeFlagsEXT"), result);
    }

    @Test
    void parseFlags_invalidFlags() {
        String line = "This is not a flags definition";
        Optional<String> result = FlagsParser.parseFlags(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void parseFlags_emptyString() {
        String line = "";
        Optional<String> result = FlagsParser.parseFlags(line);
        assertEquals(Optional.empty(), result);
    }
}