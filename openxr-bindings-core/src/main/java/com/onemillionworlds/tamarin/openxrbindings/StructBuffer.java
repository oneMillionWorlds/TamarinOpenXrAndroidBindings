/**
 * Base class for structure buffers.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.function.Function;

public abstract class StructBuffer<T extends Struct<T>, B extends StructBuffer<T, B>> implements NativeResource {
    protected long address;
    protected ByteBuffer container;
    protected int mark, position, limit, capacity, sizeOf;

    Object[] cachedElements;

    protected StructBuffer(long address, ByteBuffer container, int mark, int position, int limit, int capacity, int sizeOf) {
        this.address = address;
        this.container = container;
        this.mark = mark;
        this.position = position;
        this.limit = limit;
        this.capacity = capacity;
        this.sizeOf = sizeOf;
        this.cachedElements = new Object[capacity];
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

    public void markAllAsNeedsValidation(){
        for(int i = 0; i < capacity; i++){
            get(i).setNeedsToValidateAllMethodsCalled();
        }
    }

    public void setNoLongerNeedsToValidateAllMethodsCalled(){
        for(int i = 0; i < capacity; i++){
            get(i).setNoLongerNeedsToValidateAllMethodsCalled();
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
    @SuppressWarnings("unchecked")
    public final T get(int index){
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if(cachedElements[index] == null){
            cachedElements[index] = getElementFactory().apply(address + (long) index * sizeOf);
        }
        return (T)cachedElements[index];
    }

    /**
     * Returns the element at the current position and increments the position.
     */
    public T get() {
        T element = get(position);
        position++;
        return element;
    }

    /**
     * Returns a factory for creating elements of this buffer.
     */
    protected abstract Function<Long,T> getElementFactory();

    /**
     * Returns this buffer.
     */
    protected abstract B self();

    /**
     * Creates a new buffer with the specified parameters.
     */
    protected abstract B create(long address, ByteBuffer container, int mark, int position, int limit, int capacity);

    @Override
    public String toString() {
        String structName;
        T representative = get(0);

        structName = representative.getClass().getSimpleName();
        String bufferName = structName + ".Buffer";

        StringBuilder sb = new StringBuilder(bufferName).append('{');
        sb.append("address=").append("0x").append(Long.toHexString(address));
        sb.append(", mark=").append(mark);
        sb.append(", position=").append(position);
        sb.append(", limit=").append(limit);
        sb.append(", capacity=").append(capacity);
        sb.append(", elements=[");
        int count = limit;
        for (int i = 0; i < count; i++) {
            if (i > 0) sb.append(", ");
            try {
                T elem = get(i);
                sb.append(elem);
            } catch (Throwable t) {
                sb.append("<error:").append(t.getClass().getSimpleName()).append(">");
            }
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }
}
