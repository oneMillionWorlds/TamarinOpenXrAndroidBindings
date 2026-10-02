package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

public class ByteBufferView {
    ByteBuffer buffer;
    long address;

    public ByteBufferView(ByteBuffer buffer, long address) {
        this.buffer = buffer;
        this.address = address;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public long address() {
        return address;
    }

    public int capacity(){
        return buffer.capacity();
    }

    /**
     * A view of existing native memory (e.g. an array a struct points to). The memory isn't owned by the view.
     * @param address the address of the first byte
     * @param capacity the number of bytes
     * @return the view, or null if the address is NULL
     */
    public static ByteBufferView wrap(long address, int capacity){
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity);
        return buffer == null ? null : new ByteBufferView(buffer, address);
    }

    /**
     * A view of an existing null-terminated string in native memory. The view includes the null terminator. The
     * memory isn't owned by the view.
     * @param address the address of the first char
     * @return the view, or null if the address is NULL
     */
    public static ByteBufferView wrapNullTerminated(long address){
        if(address == 0){
            return null;
        }
        int length = 0;
        while(MemoryUtil.memGetByte(address + length) != 0){
            length++;
        }
        return wrap(address, length + 1);
    }

    /**
     * Creates a new ByteBufferView with the specified capacity.
     * <p>
     * This is managed by java and will be freed when the ByteBufferView is garbage collected.
     * </p>
     * @param capacity the capacity in bytes
     * @return a new ByteBufferView with the specified capacity.
     */
    public static ByteBufferView createLongBufferView(int capacity){
        ByteBuffer buffer = BufferUtils.createByteBuffer(capacity);
        long address = MemoryUtil.memAddress(buffer);
        return new ByteBufferView(buffer, address);
    }
}
