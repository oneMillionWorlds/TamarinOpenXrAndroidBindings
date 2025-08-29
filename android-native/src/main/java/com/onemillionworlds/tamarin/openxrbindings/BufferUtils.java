/**
 * Utility class for buffer operations.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.LongBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

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

    public static LongBufferView createLongBufferView(int capacity){
        ByteBuffer buffer = createByteBuffer(capacity * Long.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new LongBufferView(buffer, buffer.asLongBuffer(), address);
    }

    public static IntBufferView createIntBufferView(int capacity){
        ByteBuffer buffer = createByteBuffer(capacity * Integer.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new IntBufferView(buffer, buffer.asIntBuffer(), address);
    }

    public static void byteBufferLengthCheck(ByteBuffer directByteBuffer, int maximumPermittedLength){
        if(directByteBuffer.remaining() > maximumPermittedLength){
            throw new IllegalArgumentException("Buffer length exceeds maximum permitted length of " + maximumPermittedLength);
        }
    }
}