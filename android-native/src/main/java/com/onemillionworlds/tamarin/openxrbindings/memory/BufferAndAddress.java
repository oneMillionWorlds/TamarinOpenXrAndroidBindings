package com.onemillionworlds.tamarin.openxrbindings.memory;

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

    public long getAddress() {
        return address;
    }
}
