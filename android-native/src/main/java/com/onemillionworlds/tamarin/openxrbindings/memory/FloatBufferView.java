package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

public class FloatBufferView extends JavaBufferView<java.nio.FloatBuffer>{

    public FloatBufferView(ByteBuffer rawBuffer, java.nio.FloatBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }

    public float get(int index){
        return getBufferView().get(index);
    }

    public void set(int index, float value){
        getBufferView().put(index, value);
    }

    public int capacity(){
        return getBufferView().capacity();
    }

    /**
     * Creates a new FloatBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the FloatBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in longs
     * @return a new FloatBufferView with the specified capacity.
     */
    public static FloatBufferView createIntBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity * Float.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new FloatBufferView(buffer, buffer.asFloatBuffer(), address);
    }
}
