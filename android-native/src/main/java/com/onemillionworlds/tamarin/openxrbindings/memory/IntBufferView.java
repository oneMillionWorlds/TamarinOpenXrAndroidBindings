package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

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

    public int capacity(){
        return getBufferView().capacity();
    }

    /**
     * Creates a new IntBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the PointerBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in longs
     * @return a new IntBufferView with the specified capacity.
     */
    public static IntBufferView createIntBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity * Integer.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new IntBufferView(buffer, buffer.asIntBuffer(), address);
    }

}
