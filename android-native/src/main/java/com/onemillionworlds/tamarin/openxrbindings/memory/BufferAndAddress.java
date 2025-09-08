package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

public class BufferAndAddress {
    ByteBuffer buffer;
    long address;

    public BufferAndAddress(ByteBuffer buffer, long address) {
        this.buffer = buffer;
        this.address = address;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public long address() {
        return address;
    }

    /**
     * Creates a new BufferAndAddress with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the BufferAndAddress is garbage collected.
     * </p>
     * @param capacity the capacity in bytes
     * @return a new BufferAndAddress with the specified capacity.
     */
    public static BufferAndAddress createLongBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity);
        long address = MemoryUtil.memAddress(buffer);
        return new BufferAndAddress(buffer, address);
    }
}
