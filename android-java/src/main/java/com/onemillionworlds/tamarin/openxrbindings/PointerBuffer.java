/**
 * A buffer of pointers (memory addresses).
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.MemoryUtil.*;

public class PointerBuffer implements NativeResource {
    private static final int POINTER_SIZE = 8; // Assuming 64-bit OS (8 bytes per pointer)
    
    protected long address;
    protected ByteBuffer container;
    protected int mark, position, limit, capacity;

    protected PointerBuffer(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {
        this.address = address;
        this.container = container;
        this.mark = mark;
        this.position = position;
        this.limit = limit;
        this.capacity = capacity;
    }

    /**
     * Creates a new {@code PointerBuffer} instance backed by the specified container.
     *
     * @param container the ByteBuffer container
     * @return the new PointerBuffer
     */
    public static PointerBuffer create(ByteBuffer container) {
        return new PointerBuffer(memAddress(container), container, -1, 0, container.remaining() / POINTER_SIZE, container.remaining() / POINTER_SIZE);
    }

    /**
     * Creates a new {@code PointerBuffer} instance with the specified capacity.
     *
     * @param capacity the buffer capacity
     * @return the new PointerBuffer
     */
    public static PointerBuffer allocate(int capacity) {
        ByteBuffer container = BufferUtils.createByteBuffer(capacity * POINTER_SIZE);
        return create(container);
    }

    /**
     * Creates a new {@code PointerBuffer} instance with the specified address and capacity.
     *
     * @param address the buffer address
     * @param capacity the buffer capacity
     * @return the new PointerBuffer
     */
    public static PointerBuffer create(long address, int capacity) {
        return new PointerBuffer(address, null, -1, 0, capacity, capacity);
    }

    /**
     * Returns the memory address of this buffer.
     */
    @Override
    public long address() {
        return address;
    }

    /**
     * Frees the native memory associated with this buffer.
     * This method should be called when the buffer is no longer needed.
     */
    @Override
    public void free() {
        if (address != 0) {
            nmemFree(address);
            address = 0;
        }
    }

    /**
     * Returns this buffer's position.
     */
    public int position() {
        return position;
    }

    /**
     * Sets this buffer's position.
     */
    public PointerBuffer position(int newPosition) {
        if (newPosition < 0 || newPosition > limit) {
            throw new IllegalArgumentException("Invalid position: " + newPosition);
        }
        position = newPosition;
        if (mark > position) {
            mark = -1;
        }
        return this;
    }

    /**
     * Returns this buffer's limit.
     */
    public int limit() {
        return limit;
    }

    /**
     * Sets this buffer's limit.
     */
    public PointerBuffer limit(int newLimit) {
        if (newLimit < 0 || newLimit > capacity) {
            throw new IllegalArgumentException("Invalid limit: " + newLimit);
        }
        limit = newLimit;
        if (position > limit) {
            position = limit;
        }
        if (mark > limit) {
            mark = -1;
        }
        return this;
    }

    /**
     * Returns this buffer's capacity.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Returns the number of elements remaining in this buffer.
     */
    public int remaining() {
        return limit - position;
    }

    /**
     * Returns the pointer at the specified index.
     */
    public long get(int index) {
        if (index < 0 || index >= limit) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return memGetAddress(address + index * POINTER_SIZE);
    }

    /**
     * Returns the pointer at the current position and increments the position.
     */
    public long get() {
        if (position >= limit) {
            throw new IndexOutOfBoundsException("Position: " + position);
        }
        return get(position++);
    }

    /**
     * Sets the pointer at the specified index.
     */
    public PointerBuffer put(int index, long value) {
        if (index < 0 || index >= limit) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        memPutAddress(address + index * POINTER_SIZE, value);
        return this;
    }

    /**
     * Sets the pointer at the current position and increments the position.
     */
    public PointerBuffer put(long value) {
        if (position >= limit) {
            throw new IndexOutOfBoundsException("Position: " + position);
        }
        put(position++, value);
        return this;
    }

    /**
     * Returns a slice of this buffer.
     */
    public PointerBuffer slice() {
        return slice(0, remaining());
    }

    /**
     * Returns a slice of this buffer with the specified position and limit.
     */
    public PointerBuffer slice(int position, int limit) {
        if (position < 0 || position > limit || limit > capacity) {
            throw new IllegalArgumentException("Invalid position/limit: " + position + "/" + limit);
        }
        return new PointerBuffer(address + position * POINTER_SIZE, container, -1, 0, limit - position, limit - position);
    }
}