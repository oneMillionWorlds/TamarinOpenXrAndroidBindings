package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

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

    public int capacity(){
        return getBufferView().capacity();
    }

    /**
     * Creates a new LongBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the LongBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in longs
     * @return a new LongBufferView with the specified capacity.
     */
    public static LongBufferView createLongBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity * Long.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new LongBufferView(buffer, buffer.asLongBuffer(), address);
    }


}