package com.onemillionworlds.tamarin.gradle.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructFieldTest {

    private static StructField scalarField(String type){
        return new StructField(type, "value", null, false, false, false, false, false, false, false, false, false, false, null, false);
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

    private static StructField pointerField(String type, boolean isEnum, String countField){
        return new StructField(type, "values", null, true, false, isEnum, false, false, false, false, false, false, false, countField, false);
    }

    @Test
    void pointerToPrimitivesWithACountIsATypedView() {
        assertEquals("FloatBufferView", pointerField("float", false, "valueCount").getJavaType());
        assertEquals("IntBufferView", pointerField("uint32_t", false, "valueCount").getJavaType());
        assertEquals("ShortBufferView", pointerField("uint16_t", false, "valueCount").getJavaType());
        assertEquals("ByteBufferView", pointerField("uint8_t", false, "valueCount").getJavaType());
        // an array of enums is an array of ints, not a single enum
        assertEquals("IntBufferView", pointerField("XrSpatialComponentTypeEXT", true, "valueCount").getJavaType());
    }

    @Test
    void bufferSettersAlsoSetTheirCountField() {
        assertTrue(pointerField("uint32_t", false, "valueCount").setterAlsoSetsCountField());
        StructField structBuffer = new StructField("XrVector3f", "points", null, true, false, false, false, false, false, false, false, true, false, "pointCount", false);
        assertTrue(structBuffer.setterAlsoSetsCountField());

        // no count field
        assertFalse(pointerField("uint32_t", false, null).setterAlsoSetsCountField());
        // a double pointer that isn't an array of strings is a raw address, setting it doesn't touch the count
        StructField strings = new StructField("char", "names", null, true, true, false, false, false, false, false, false, false, true, "nameCount", false);
        assertFalse(strings.setterAlsoSetsCountField());
    }

    @Test
    void nullTerminatedStrings() {
        // const char* labelName (len="null-terminated")
        StructField string = new StructField("char", "labelName", null, true, true, false, false, false, false, false, false, false, false, null, true);
        assertTrue(string.isNullTerminatedString());
        assertEquals("ByteBufferView", string.getJavaType());

        // const char* const* enabledExtensionNames (len="enabledExtensionCount,null-terminated")
        StructField strings = new StructField("char", "enabledExtensionNames", null, true, true, false, false, false, false, false, false, false, true, "enabledExtensionCount", true);
        assertTrue(strings.isStringArray());
        assertEquals("PointerBufferView", strings.getJavaType());
        assertTrue(strings.setterAlsoSetsCountField());

        // a char* with a count (not null-terminated) is just bytes
        StructField bytes = new StructField("char", "buffer", null, true, false, false, false, false, false, false, false, false, false, "bufferCapacityInput", false);
        assertFalse(bytes.isNullTerminatedString());
        assertEquals("ByteBufferView", bytes.getJavaType());
    }

    @Test
    void pointerWithoutACountIsARawAddress() {
        StructField enumPointer = pointerField("XrSpatialComponentTypeEXT", true, null);
        assertEquals("long", enumPointer.getJavaType());
        assertEquals("memGetAddress", enumPointer.getMemoryAccessMethod());
        assertEquals("memPutAddress", enumPointer.getMemorySetMethod());
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
