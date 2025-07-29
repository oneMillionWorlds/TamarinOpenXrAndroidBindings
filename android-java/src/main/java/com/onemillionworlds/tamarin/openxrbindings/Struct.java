/**
 * Base class for native structures.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;

public abstract class Struct<T extends Struct<T>> implements NativeResource {
    protected long address;
    protected ByteBuffer container;

    protected Struct(long address, ByteBuffer container) {
        this.address = address;
        this.container = container;
    }

    /**
     * Returns the memory address of this structure.
     */
    @Override
    public long address() {
        return address;
    }

    /**
     * Frees the native memory associated with this structure.
     * This method should be called when the structure is no longer needed.
     */
    @Override
    public void free() {
        if (address != 0) {
            MemoryUtil.nmemFree(address);
            address = 0;
        }
    }

    /**
     * Returns the size of this structure in bytes.
     */
    public abstract int sizeof();

    /**
     * Creates a new instance of this structure type at the specified memory address.
     */
    protected abstract T create(long address, ByteBuffer container);

    /**
     * Sets the values of this structure to the values of the specified structure.
     */
    public T set(T src) {
        MemoryUtil.memCopy(src.address(), address(), sizeof());
        return (T)this;
    }
}
