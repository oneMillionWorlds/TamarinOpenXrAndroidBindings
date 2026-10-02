package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;
import com.onemillionworlds.tamarin.openxrbindings.XR10Constants;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class PointerBufferView extends JavaBufferView<LongBuffer>{
    public PointerBufferView(ByteBuffer rawBuffer, LongBuffer bufferView, long address) {
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
     * A view of existing native memory (e.g. an array of pointers a struct points to). The memory isn't owned by the
     * view.
     * @param address the address of the first pointer
     * @param capacity the number of pointers
     * @return the view, or null if the address is NULL
     */
    public static PointerBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity * XR10Constants.POINTER_SIZE);
        return buffer == null ? null : new PointerBufferView(buffer, buffer.asLongBuffer(), address);
    }

    /**
     * Creates a new PointerBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the PointerBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in pointers
     * @return a new PointerBufferView with the specified capacity.
     */
    public static PointerBufferView createPointerBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity * XR10Constants.POINTER_SIZE);
        long address = MemoryUtil.memAddress(buffer);
        return new PointerBufferView(buffer, buffer.asLongBuffer(), address);
    }
}
