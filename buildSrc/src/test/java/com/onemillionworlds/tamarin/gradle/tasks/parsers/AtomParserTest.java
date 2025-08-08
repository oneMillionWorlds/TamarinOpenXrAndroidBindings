package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AtomParserTest {

    @Test
    void parseAtom_validAtom() {
        String line = "XR_DEFINE_ATOM(XrSystemId)";
        Optional<String> result = AtomParser.parseAtom(line);
        assertEquals(Optional.of("XrSystemId"), result);
    }

    @Test
    void parseAtom_validAtomWithWhitespace() {
        String line = "XR_DEFINE_ATOM( XrSystemId )";
        Optional<String> result = AtomParser.parseAtom(line);
        assertEquals(Optional.of("XrSystemId"), result);
    }

    @Test
    void parseAtom_invalidAtom() {
        String line = "This is not an atom definition";
        Optional<String> result = AtomParser.parseAtom(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void parseAtom_emptyString() {
        String line = "";
        Optional<String> result = AtomParser.parseAtom(line);
        assertEquals(Optional.empty(), result);
    }
}