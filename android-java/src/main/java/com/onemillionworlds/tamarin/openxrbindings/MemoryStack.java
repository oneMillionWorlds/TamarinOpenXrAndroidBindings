/**
 * A stack-based allocator for native memory.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class MemoryStack implements AutoCloseable {

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

    private final List<ByteBuffer> buffers = new ArrayList<>();
    private int currentBuffer = -1;

    /**
     * Pushes a new frame onto the stack.
     */
    public MemoryStack push() {
        currentBuffer++;
        if (currentBuffer >= buffers.size()) {
            buffers.add(ByteBuffer.allocateDirect(1024 * 1024)); // 1MB
        }
        ByteBuffer buffer = buffers.get(currentBuffer);
        buffer.clear();
        return this;
    }

    /**
     * Pops the current frame from the stack.
     */
    public void pop() {
        if (currentBuffer < 0) {
            throw new IllegalStateException("No frames on the stack");
        }
        currentBuffer--;
    }

    /**
     * Allocates memory on the stack.
     * 
     * @param alignment the alignment in bytes
     * @param size the size in bytes
     * @return the memory address
     */
    public long nmalloc(int alignment, int size) {
        if (currentBuffer < 0) {
            throw new IllegalStateException("No frames on the stack");
        }
        ByteBuffer buffer = buffers.get(currentBuffer);
        int position = buffer.position();
        int alignedPosition = (position + alignment - 1) & ~(alignment - 1);
        buffer.position(alignedPosition + size);
        return MemoryUtil.memAddress(buffer) + alignedPosition;
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
        long address = nmalloc(alignment, num * size);
        // Zero the memory
        for (int i = 0; i < num * size; i++) {
            MemoryUtil.memPutInt(address + i, 0);
        }
        return address;
    }

    /**
     * Returns a ByteBuffer that represents the specified memory on the stack.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public ByteBuffer malloc(int capacity) {
        return MemoryUtil.memByteBuffer(nmalloc(1, capacity), capacity);
    }

    /**
     * Returns a zeroed ByteBuffer that represents the specified memory on the stack.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public ByteBuffer calloc(int capacity) {
        return MemoryUtil.memByteBuffer(ncalloc(1, capacity, 1), capacity);
    }

    /**
     * Returns an IntBuffer that represents the specified memory on the stack.
     * 
     * @param size the number of ints
     * @return the IntBuffer
     */
    public IntBuffer mallocInt(int size) {
        ByteBuffer buffer = malloc(size * 4);
        return buffer.asIntBuffer();
    }

    /**
     * Closes this memory stack by popping the current frame.
     */
    @Override
    public void close() {
        pop();
    }
}
