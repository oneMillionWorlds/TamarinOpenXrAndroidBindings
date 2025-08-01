package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class PointerBufferView extends JavaBufferView<LongBuffer>{
    public PointerBufferView(ByteBuffer rawBuffer, LongBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }
}
