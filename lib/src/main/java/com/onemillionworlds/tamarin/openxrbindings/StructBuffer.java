/**
 * Base class for structure buffers.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;

public abstract class StructBuffer<T extends Struct<T>, B extends StructBuffer<T, B>> implements NativeResource {
    protected long address;
    protected ByteBuffer container;
    protected int mark, position, limit, capacity;

    protected StructBuffer(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {
        this.address = address;
        this.container = container;
        this.mark = mark;
        this.position = position;
        this.limit = limit;
        this.capacity = capacity;
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
            MemoryUtil.nmemFree(address);
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
    public B position(int newPosition) {
        if (newPosition < 0 || newPosition > limit) {
            throw new IllegalArgumentException("Invalid position: " + newPosition);
        }
        position = newPosition;
        if (mark > position) {
            mark = -1;
        }
        return self();
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
    public B limit(int newLimit) {
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
        return self();
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
     * Returns the element at the specified index.
     */
    public abstract T get(int index);

    /**
     * Returns the element at the current position and increments the position.
     */
    public T get() {
        T element = get(position);
        position++;
        return element;
    }

    /**
     * Returns a slice of this buffer.
     */
    public abstract B slice();

    /**
     * Returns a slice of this buffer with the specified position and limit.
     */
    public B slice(int position, int limit) {
        if (position < 0 || position > limit || limit > capacity) {
            throw new IllegalArgumentException("Invalid position/limit: " + position + "/" + limit);
        }
        return create(address + position * getElementFactory().sizeof(), container, -1, 0, limit - position, limit - position);
    }

    /**
     * Returns a factory for creating elements of this buffer.
     */
    protected abstract T getElementFactory();

    /**
     * Returns this buffer.
     */
    protected abstract B self();

    /**
     * Creates a new buffer with the specified parameters.
     */
    protected abstract B create(long address, ByteBuffer container, int mark, int position, int limit, int capacity);
}
