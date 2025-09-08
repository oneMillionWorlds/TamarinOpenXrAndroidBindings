/**
 * A stack-based allocator for native memory.
 */
package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;

import java.nio.ByteBuffer;

import java.util.LinkedList;


/**
 * This allocates large swaths of native memory for each stack frame. During the frame
 * bits of memory can be allocated for particular purposes, and then, when the frame is
 *  finished, the memory is freed all together
 */
public class MemoryStack implements AutoCloseable {
    /**
     * Total available stack memory in bytes.
     */
    public static final int capacity = 64 * 1024;

    private static final ThreadLocal<MemoryStack> STACK = new ThreadLocal<MemoryStack>() {
        @Override
        protected MemoryStack initialValue() {
            return new MemoryStack();
        }
    };

    /**
     * Returns the thread-local memory stack.
     */
    public static MemoryStack stackGet() {
        return STACK.get();
    }

    private final ByteBuffer bulkMemory;

    private int pointer;
    private final long bulkMemoryAddress;
    private final LinkedList<Integer> framePointers = new LinkedList<>();

    public MemoryStack() {
        bulkMemory = BufferUtils.createByteBuffer(capacity);
        bulkMemoryAddress = MemoryUtil.memAddress(bulkMemory);

        pointer = capacity; // we start at the end of the memory and work backwards allocating bits of stack
    }

    /**
     * Pushes a new frame onto the stack.
     */
    public MemoryStack push() {
        // this puts the beginning of the frame at the current position
        // so we can rewind to it later
        this.framePointers.push(pointer);
        return this;
    }

    /**
     * Pops the current frame from the stack.
     */
    public void pop() {
        if(framePointers.isEmpty()) {
            throw new IllegalStateException("No frames on the stack");
        }

        // return the pointer position to the start of the frame (which will now become the continuation of the previous frame)
        pointer = this.framePointers.pop();
    }


    /**
     * Allocates memory on the stack.
     * 
     * @param alignment the alignment in bytes
     * @param size the size in bytes
     * @return the memory address
     */
    public long nmalloc(int alignment, int size) {

        long rawAddress = this.bulkMemoryAddress + pointer - size;
        long alignedAddress = alignDown(rawAddress, alignment);

        pointer = (int) (alignedAddress - this.bulkMemoryAddress);

        if(pointer<0){
            throw new IllegalStateException("Out of stack memory");
        }

        return alignedAddress;
    }

    /**
     * Allocates zeroed memory on the stack.
     * 
     * @param alignment the alignment in bytes
     * @param num the number of elements
     * @param size the size of each element in bytes
     * @return the memory address
     */
    public long ncalloc(int alignment, int num, int size) {
        int bytes = num * size;
        long address = nmalloc(alignment, bytes);
        // Zero the memory
        MemoryUtil.memZeroBytes(address, bytes);

        return address;
    }

    /**
     * Returns a ByteBuffer that represents the specified memory on the stack.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public ByteBufferView malloc(int alignment, int capacity) {
        long address = nmalloc(alignment, capacity);
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity);
        return new ByteBufferView(buffer, address);
    }

    /**
     * Returns a zeroed ByteBuffer that represents the specified memory on the stack.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public ByteBufferView calloc(int alignment, int capacity) {
        long address = ncalloc(alignment, capacity, 1);
        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, capacity);
        return new ByteBufferView(buffer, address);
    }

    /**
     * Returns an IntBuffer that represents the specified memory on the stack.
     * 
     * @param size the number of ints
     * @return the IntBuffer
     */
    public IntBufferView mallocInt(int size) {
        ByteBufferView buffer = malloc(4, size * 4);
        return new IntBufferView(buffer.buffer, buffer.buffer.asIntBuffer(), buffer.address);
    }

    /**
     * Returns an IntBuffer that represents the specified memory on the stack.
     *
     * @param size the number of ints
     * @return the IntBuffer
     */
    public IntBufferView callocInt(int size) {
        ByteBufferView buffer = calloc(4, size * 4);
        return new IntBufferView(buffer.buffer, buffer.buffer.asIntBuffer(), buffer.address);
    }

    /**
     * Returns a ByteBuffer that represents the specified string on the stack.
     */
    public ByteBufferView utf8(String string){
        return utf8(string, true);
    }

    /**
     * Returns a ByteBuffer that represents the specified string on the stack.
     */
    public ByteBufferView utf8(String string, boolean nullTerminated) {
        // Calculate byte length for UTF-8 encoded string
        byte[] bytes = string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int size = bytes.length + (nullTerminated ? 1 : 0);

        // Allocate buffer
        ByteBufferView buffer = malloc(1, size);

        // Copy string bytes
        buffer.buffer.put(bytes);

        // Add null terminator if requested
        if (nullTerminated) {
            buffer.buffer.put((byte) 0);
        }

        // Reset position to start
        buffer.buffer.flip();

        return buffer;
    }

    /**
     * Returns a LongBuffer that represents the specified memory on the stack.
     *
     * @param size the number of ints
     * @return the IntBuffer
     */
    public LongBufferView callocLong(int size) {
        ByteBufferView buffer = calloc(8, size * 8);
        return new LongBufferView(buffer.buffer, buffer.buffer.asLongBuffer(), buffer.address);
    }

    /**
     * Returns a PointerBuffer (aka LongBuffer) that represents the specified memory on the stack.
     *
     * @param size the number of ints
     * @return the IntBuffer
     */
    public PointerBufferView callocPointer(int size) {
        ByteBufferView buffer = calloc(8, size * 8);
        return new PointerBufferView(buffer.buffer, buffer.buffer.asLongBuffer(), buffer.address);
    }

    /**
     * Closes this memory stack by popping the current frame.
     */
    @Override
    public void close() {
        pop();
    }

    private static long alignDown(long address, int alignment) {
        int mask = alignment - 1;
        long maskLong = Integer.toUnsignedLong(mask);
        return address - (address & maskLong);
    }
}
