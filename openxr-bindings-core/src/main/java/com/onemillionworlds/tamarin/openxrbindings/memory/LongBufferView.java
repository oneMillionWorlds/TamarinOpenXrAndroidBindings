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
     * A view of existing native memory (e.g. an array a struct points to). The memory isn't owned by the view.
     * @param address the address of the first long
     * @param capacity the number of longs
     * @return the view, or null if the address is NULL
     */
    public static LongBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity * Long.BYTES);
        return buffer == null ? null : new LongBufferView(buffer, buffer.asLongBuffer(), address);
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

    @Override
    public String toString(){
        StringBuilder contents = new StringBuilder();
        for(int i = 0; i < capacity(); i++){
            contents.append(get(i)).append(", ");
        }
        return getClass().getSimpleName() + "{address: "  + address() + "contents: " +  contents + "}";
    }

}