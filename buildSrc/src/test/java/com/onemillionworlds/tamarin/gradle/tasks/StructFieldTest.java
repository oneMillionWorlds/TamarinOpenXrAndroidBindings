package com.onemillionworlds.tamarin.gradle.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StructFieldTest {

    private static StructField scalarField(String type){
        return new StructField(type, "value", null, false, false, false, false, false, false, false, false, false, false, null);
    }

    @Test
    void knownScalarTypes() {
        StructField uint64 = scalarField("uint64_t");
        assertEquals("long", uint64.getJavaType());
        assertEquals("memGetLong", uint64.getMemoryAccessMethod());
        assertEquals("memPutLong", uint64.getMemorySetMethod());

        StructField uint8 = scalarField("uint8_t");
        assertEquals("byte", uint8.getJavaType());
        assertEquals("memGetByte", uint8.getMemoryAccessMethod());
        assertEquals("memPutByte", uint8.getMemorySetMethod());
    }

    /**
     * An unknown type must fail generation rather than silently being read/written as some default size
     */
    @Test
    void unknownTypeFails() {
        StructField unknown = scalarField("int128_t");
        assertThrows(RuntimeException.class, unknown::getJavaType);
        assertThrows(RuntimeException.class, unknown::getMemoryAccessMethod);
        assertThrows(RuntimeException.class, unknown::getMemorySetMethod);
        assertThrows(RuntimeException.class, unknown::getMemorySize);
    }
}
