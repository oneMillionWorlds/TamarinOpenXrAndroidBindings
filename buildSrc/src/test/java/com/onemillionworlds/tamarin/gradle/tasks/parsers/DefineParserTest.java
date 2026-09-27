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

    @Test
    void testParse_unsignedSuffixIsDropped() {
        String input = "#define XR_MAX_HAPTIC_AMPLITUDE_ENVELOPE_SAMPLES_FB 4000u";

        ConstParser.Const constant = ConstParser.Const.fromDefine(DefinePasser.parseDefine(input).orElseThrow());

        assertEquals("int", constant.type);
        assertEquals("4000", constant.value);
    }

    @Test
    void testParse_negativeNumber() {
        String input = "#define XR_MIN_HAPTIC_DURATION -1";

        ConstParser.Const constant = ConstParser.Const.fromDefine(DefinePasser.parseDefine(input).orElseThrow());

        assertEquals("int", constant.type);
        assertEquals("-1", constant.value);
    }

    @Test
    void testParse_digitsInName() {
        String input = "#define XR_BODY_JOINT_COUNT_2_BD 26";

        assertEquals("XR_BODY_JOINT_COUNT_2_BD", DefinePasser.parseDefine(input).orElseThrow().constantName);
    }


}
