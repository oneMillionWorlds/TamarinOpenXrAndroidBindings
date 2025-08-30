package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;

public class IntBufferView extends JavaBufferView<java.nio.IntBuffer>{

    public IntBufferView(ByteBuffer rawBuffer, java.nio.IntBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }

    public int get(int index){
        return getBufferView().get(index);
    }

    public void set(int index, int value){
        getBufferView().put(index, value);
    }
}
