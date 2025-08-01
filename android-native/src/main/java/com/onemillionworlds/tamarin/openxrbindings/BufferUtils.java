/**
 * Utility class for buffer operations.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtils {
    
    /**
     * Creates a new direct ByteBuffer with the specified capacity.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public static ByteBuffer createByteBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }
    
    /**
     * Checks if the specified size is valid for allocation.
     * 
     * @param capacity the capacity in bytes
     * @param size the size in bytes
     * @return the capacity
     * @throws IllegalArgumentException if the capacity is negative or less than the size
     */
    public static int checkBufferSize(int capacity, int size) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Negative capacity: " + capacity);
        }
        if (capacity < size) {
            throw new IllegalArgumentException("Capacity less than size: " + capacity + " < " + size);
        }
        return capacity;
    }
}