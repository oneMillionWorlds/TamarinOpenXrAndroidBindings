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
     * A view of existing native memory (e.g. an array a struct points to). The memory isn't owned by the view.
     * @param address the address of the first float
     * @param capacity the number of floats
     * @return the view, or null if the address is NULL
     */
    public static FloatBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity * Float.BYTES);
        return buffer == null ? null : new FloatBufferView(buffer, buffer.asFloatBuffer(), address);
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

    @Override
    public String toString(){
        StringBuilder contents = new StringBuilder();
        for(int i = 0; i < capacity(); i++){
            contents.append(get(i)).append(", ");
        }
        return getClass().getSimpleName() + "{address: "  + address() + "contents: " +  contents + "}";
    }
}
