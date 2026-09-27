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
     * A view of existing native memory (e.g. an array a struct points to). The memory isn't owned by the view.
     * @param address the address of the first int
     * @param capacity the number of ints
     * @return the view, or null if the address is NULL
     */
    public static IntBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity * Integer.BYTES);
        return buffer == null ? null : new IntBufferView(buffer, buffer.asIntBuffer(), address);
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

    @Override
    public String toString(){
        StringBuilder contents = new StringBuilder();
        for(int i = 0; i < capacity(); i++){
            contents.append(get(i)).append(", ");
        }
        return getClass().getSimpleName() + "{address: "  + address() + "contents: " +  contents + "}";
    }

}
