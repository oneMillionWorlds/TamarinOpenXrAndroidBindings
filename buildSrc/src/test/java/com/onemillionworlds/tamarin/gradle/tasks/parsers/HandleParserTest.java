package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandleParserTest {

    @Test
    void parseHandle_validHandle() {
        String line = "XR_DEFINE_HANDLE(XrInstance)";
        Optional<String> result = HandleParser.parseHandle(line);
        assertEquals(Optional.of("XrInstance"), result);
    }

    @Test
    void parseHandle_validHandleWithWhitespace() {
        String line = "XR_DEFINE_HANDLE( XrSession )";
        Optional<String> result = HandleParser.parseHandle(line);
        assertEquals(Optional.of("XrSession"), result);
    }

    @Test
    void parseHandle_invalidHandle() {
        String line = "This is not a handle definition";
        Optional<String> result = HandleParser.parseHandle(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void parseHandle_emptyString() {
        String line = "";
        Optional<String> result = HandleParser.parseHandle(line);
        assertEquals(Optional.empty(), result);
    }
}