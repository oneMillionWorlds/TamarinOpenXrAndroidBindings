package com.onemillionworlds.tamarin.gradle.tasks.generators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the HandleGenerator class.
 */
public class HandleGeneratorTest {

    @Test
    void testGenerateHandle_XrInstance() {
        String expectedValue = """
                /*
                 * OpenXR Java bindings for Android
                 * This file is auto-generated. DO NOT EDIT.
                 */
                package com.onemillionworlds.tamarin.openxrbindings.handles;

                import com.onemillionworlds.tamarin.openxrbindings.Handle;

                /**
                 * Handle for action set.
                 */
                public class XrActionSet extends Handle {

                    /**
                     * Creates a new XrActionSet instance.
                     *
                     * @param rawHandle The raw handle value.
                     */
                    public XrActionSet(long rawHandle) {
                        super(rawHandle);
                    }
                }
                """;

        String actualValue = HandleGenerator.generateHandle("XrActionSet");

        assertEquals(expectedValue.trim(), actualValue.trim());
    }
}