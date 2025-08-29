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
                import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;
                import com.onemillionworlds.tamarin.openxrbindings.memory.*;
                
                import java.nio.ByteBuffer;
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
                
                    public static class Buffer extends HandleBufferView<XrActionSet> {
                        public Buffer(LongBufferView viewToAdopt){
                            super(viewToAdopt, XrActionSet::new);
                        }
                    }
                
                    /**
                     * Creates a new buffer for XrActionSet instances. NOTE must be manually freed
                     *
                     * @param capacity The number of handles of this type that can be held.
                     */
                    public static Buffer create(int capacity) {
                        LongBufferView buffer = BufferUtils.createLongBufferView(capacity);
                        return new Buffer(buffer);
                    }
                    /**
                     * Creates a new buffer for XrActionSet instances on the stack. NOTE must NOT be manually freed
                     *
                     * @param stack The stack to allocate this buffer on.
                     * @param capacity The number of handles of this type that can be held.
                     */
                    public static Buffer create(MemoryStack stack, int capacity) {
                        LongBufferView buffer = stack.callocLong(capacity);
                        return new Buffer(buffer);
                    }
                    /**
                     * Adopts an existing byte buffer a new buffer and an address as a handle buffer. NOTE if it must or must not be manually freed should follow the underlying buffer
                     *
                     * @param buffer The buffer to adopt.
                     * @param address The memory address of the buffer
                     */
                    public static Buffer create(ByteBuffer buffer, long address) {
                        LongBufferView bufferView = new LongBufferView(buffer, buffer.asLongBuffer(), address);
                        return new Buffer(bufferView);
                    }
                }
                """;

        String actualValue = HandleGenerator.generateHandle("XrActionSet");

        assertEquals(expectedValue.trim(), actualValue.trim());
    }
}