/**
 * Base class for native structures.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

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
        checkValidStateForUse();
        return address;
    }

    /**
     * Returns the memory address of this structure without checking that it is valid.
     * Used internally while setting values.
     */
    public long addressUnsafe() {
        return address;
    }

    abstract void checkValidStateForUse();

    /**
     * If called this sets the object to track that the setters have all been called and if the struct is
     * used without all of the setters being called, an exception will be thrown.
     * <p>
     *     This is used when the object has been malloc created and so may contain garbage data on construction
     * </p>
     */
    abstract void setNeedsToValidateAllMethodsCalled();

    abstract void setNoLongerNeedsToValidateAllMethodsCalled();

    public ByteBuffer container() {
        return container;
    }

    /**
     * Frees the native memory associated with this structure.
     * This method should be called when the structure is no longer needed.
     * <p>Should ONLY be called if this struct owns the memory (which is almost never the case)</p>
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

    public void clear(){
        MemoryUtil.memZeroBytes(address(), sizeof());
    }
}
