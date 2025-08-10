package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;

public class FloatBufferView extends JavaBufferView<java.nio.FloatBuffer>{

    public FloatBufferView(ByteBuffer rawBuffer, java.nio.FloatBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }
}
