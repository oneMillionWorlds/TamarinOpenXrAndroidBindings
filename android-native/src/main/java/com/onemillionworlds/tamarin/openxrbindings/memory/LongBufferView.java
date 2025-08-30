package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;

public class LongBufferView extends JavaBufferView<java.nio.LongBuffer>{

    public LongBufferView(ByteBuffer rawBuffer, java.nio.LongBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }

    public long get(int index){
        return getBufferView().get(index);
    }

    public void set(int index, long value){
        getBufferView().put(index, value);
    }
}