package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongTypeDefPasserTest {

    @Test
    void parseLongTypedef_validLongTypedef() {
        String line = "typedef uint64_t XrVersion;";
        Optional<String> result = LongTypeDefPasser.parseLongTypedef(line);
        assertEquals(Optional.of("XrVersion"), result);
    }

    @Test
    void parseLongTypedef_validLongTypedefWithWhitespace() {
        String line = "typedef uint64_t  XrFlags64 ;";
        Optional<String> result = LongTypeDefPasser.parseLongTypedef(line);
        assertEquals(Optional.of("XrFlags64"), result);
    }

    @Test
    void parseLongTypedef_validInt64Typedef() {
        String line = "typedef int64_t XrTime;";
        Optional<String> result = LongTypeDefPasser.parseLongTypedef(line);
        assertEquals(Optional.of("XrTime"), result);
    }

    @Test
    void parseLongTypedef_invalidTypedef() {
        String line = "typedef uint32_t XrBool32;";
        Optional<String> result = LongTypeDefPasser.parseLongTypedef(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void parseLongTypedef_emptyString() {
        String line = "";
        Optional<String> result = LongTypeDefPasser.parseLongTypedef(line);
        assertEquals(Optional.empty(), result);
    }
}