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

    public static void byteBufferLengthCheck(ByteBuffer directByteBuffer, int maximumPermittedLength){
        if(directByteBuffer.remaining() > maximumPermittedLength){
            throw new IllegalArgumentException("Buffer length exceeds maximum permitted length of " + maximumPermittedLength);
        }
    }
}