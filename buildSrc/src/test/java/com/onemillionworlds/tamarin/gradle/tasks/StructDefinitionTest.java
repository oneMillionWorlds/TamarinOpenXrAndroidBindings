package com.onemillionworlds.tamarin.gradle.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructDefinitionTest {

    private static StructField countField(String name) {
        return new StructField("uint32_t", name, null, false, false, false, false, false, false, false, false, false, false, null, false);
    }

    private static StructField floatBuffer(String name, String countField) {
        return new StructField("float", name, null, true, false, false, false, false, false, false, false, false, false, countField, false);
    }

    @Test
    void nullOnlyZeroesACountFieldThatIsntShared() {
        StructDefinition struct = new StructDefinition("XrFoo");
        struct.addField(countField("ownCount"));
        StructField own = floatBuffer("own", "ownCount");
        struct.addField(own);
        struct.addField(countField("sharedCount"));
        StructField sharedA = floatBuffer("sharedA", "sharedCount");
        StructField sharedB = floatBuffer("sharedB", "sharedCount");
        struct.addField(sharedA);
        struct.addField(sharedB);

        assertFalse(struct.isCountFieldShared("ownCount"));
        assertTrue(struct.isCountFieldShared("sharedCount"));

        assertTrue(struct.nullSetterZeroesCountField(own));
        // setting sharedA to null mustn't zero the count sharedB still uses
        assertFalse(struct.nullSetterZeroesCountField(sharedA));
        // not a buffer
        assertFalse(struct.nullSetterZeroesCountField(countField("other")));
    }
}
