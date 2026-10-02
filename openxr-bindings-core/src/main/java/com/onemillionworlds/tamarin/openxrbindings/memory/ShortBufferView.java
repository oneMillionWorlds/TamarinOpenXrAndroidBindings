package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

public class ShortBufferView extends JavaBufferView<java.nio.ShortBuffer>{

    public ShortBufferView(ByteBuffer rawBuffer, java.nio.ShortBuffer bufferView, long address) {
        super(rawBuffer, bufferView, address);
    }

    public short get(int index){
        return getBufferView().get(index);
    }

    public void set(int index, short value){
        getBufferView().put(index, value);
    }

    public int capacity(){
        return getBufferView().capacity();
    }

    /**
     * A view of existing native memory (e.g. an array a struct points to). The memory isn't owned by the view.
     * @param address the address of the first short
     * @param capacity the number of shorts
     * @return the view, or null if the address is NULL
     */
    public static ShortBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity * Short.BYTES);
        return buffer == null ? null : new ShortBufferView(buffer, buffer.asShortBuffer(), address);
    }

    /**
     * Creates a new ShortBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the ShortBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in shorts
     * @return a new ShortBufferView with the specified capacity.
     */
    public static ShortBufferView createShortBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity * Short.BYTES);
        long address = MemoryUtil.memAddress(buffer);
        return new ShortBufferView(buffer, buffer.asShortBuffer(), address);
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
