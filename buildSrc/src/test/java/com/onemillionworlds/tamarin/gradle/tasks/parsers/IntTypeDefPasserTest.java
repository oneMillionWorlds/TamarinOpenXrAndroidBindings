package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeDefPasserTest {

    @Test
    void parseIntTypedef_validIntTypedef() {
        String line = "typedef uint32_t XrBool32;";
        Optional<String> result = IntTypeDefPasser.parseIntTypedef(line);
        assertEquals(Optional.of("XrBool32"), result);
    }

    @Test
    void parseIntTypedef_validIntTypedefWithWhitespace() {
        String line = "typedef uint32_t  XrBool32 ;";
        Optional<String> result = IntTypeDefPasser.parseIntTypedef(line);
        assertEquals(Optional.of("XrBool32"), result);
    }

    @Test
    void parseIntTypedef_validInt32Typedef() {
        String line = "typedef int32_t XrSomeInt32;";
        Optional<String> result = IntTypeDefPasser.parseIntTypedef(line);
        assertEquals(Optional.of("XrSomeInt32"), result);
    }

    @Test
    void parseIntTypedef_invalidTypedef() {
        String line = "typedef uint64_t XrVersion;";
        Optional<String> result = IntTypeDefPasser.parseIntTypedef(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void parseIntTypedef_emptyString() {
        String line = "";
        Optional<String> result = IntTypeDefPasser.parseIntTypedef(line);
        assertEquals(Optional.empty(), result);
    }
}