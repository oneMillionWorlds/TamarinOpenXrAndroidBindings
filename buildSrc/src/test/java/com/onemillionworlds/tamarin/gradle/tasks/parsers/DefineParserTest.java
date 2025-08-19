package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefineParserTest {

    @Test
    void testParse_simpleNumber() {
        String input = "#define XR_GUID_SIZE_MSFT                 16";

        Optional<DefinePasser.Define> define = DefinePasser.parseDefine(input);

        assertTrue(define.isPresent());
        DefinePasser.Define defineResult = define.get();
        assertEquals("XR_GUID_SIZE_MSFT", defineResult.constantName);
        assertEquals("16", defineResult.constantValue);
    }


}
