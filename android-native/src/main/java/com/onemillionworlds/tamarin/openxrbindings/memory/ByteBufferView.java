package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

public class ByteBufferView {
    ByteBuffer buffer;
    long address;

    public ByteBufferView(ByteBuffer buffer, long address) {
        this.buffer = buffer;
        this.address = address;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public long address() {
        return address;
    }

    public int capacity(){
        return buffer.capacity();
    }

    /**
     * Creates a new ByteBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the ByteBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in bytes
     * @return a new ByteBufferView with the specified capacity.
     */
    public static ByteBufferView createLongBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity);
        long address = MemoryUtil.memAddress(buffer);
        return new ByteBufferView(buffer, address);
    }
}
