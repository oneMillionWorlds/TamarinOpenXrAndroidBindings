package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;

public class LongBufferView extends JavaBufferView<java.nio.LongBuffer>{

    public LongBufferView(ByteBuffer rawBuffer, java.nio.LongBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }
}