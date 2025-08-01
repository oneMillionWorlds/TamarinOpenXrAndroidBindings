/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.ByteBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10.*;
import static com.onemillionworlds.tamarin.openxrbindings.XrStructureType.*;

/**
 * Base structure for loader initialization info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrLoaderInitInfoBaseHeaderKHR {
 *     XrStructureType type;
 *     void * next;
 * }</code></pre>
 */
public class XrLoaderInitInfoBaseHeaderKHR extends Struct<XrLoaderInitInfoBaseHeaderKHR> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
    }

    protected XrLoaderInitInfoBaseHeaderKHR(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrLoaderInitInfoBaseHeaderKHR create(long address, ByteBuffer container) {
        return new XrLoaderInitInfoBaseHeaderKHR(address, container);
    }

    /**
     * Creates a {@code XrLoaderInitInfoBaseHeaderKHR} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrLoaderInitInfoBaseHeaderKHR(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public int type() { return memGetInt(address() + TYPE); }
    /** Returns the value of the {@code next} field. */
    public long next() { return memGetAddress(address() + NEXT); }

    /** Sets the specified value to the {@code type} field. */
    public XrLoaderInitInfoBaseHeaderKHR type(int value) { memPutInt(address() + TYPE, value); return this; }
    /** Sets the specified value to the {@code next} field. */
    public XrLoaderInitInfoBaseHeaderKHR next(long value) { memPutAddress(address() + NEXT, value); return this; }
    /** Sets the specified value to the {@code type} field. */
    public XrLoaderInitInfoBaseHeaderKHR type$Default() { return type(XR_TYPE_LOADER_INIT_INFO_ANDROID_KHR); }

    /** Initializes this struct with the specified values. */
    public XrLoaderInitInfoBaseHeaderKHR set(
        int type,
        long next
    ) {
        type(type);
        next(next);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrLoaderInitInfoBaseHeaderKHR set(XrLoaderInitInfoBaseHeaderKHR src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrLoaderInitInfoBaseHeaderKHR malloc() {
        return new XrLoaderInitInfoBaseHeaderKHR(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrLoaderInitInfoBaseHeaderKHR calloc() {
        return new XrLoaderInitInfoBaseHeaderKHR(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance allocated with {@link BufferUtils}. */
    public static XrLoaderInitInfoBaseHeaderKHR create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrLoaderInitInfoBaseHeaderKHR(memAddress(container), container);
    }

    /** Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance for the specified memory address. */
    public static XrLoaderInitInfoBaseHeaderKHR create(long address) {
        return new XrLoaderInitInfoBaseHeaderKHR(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrLoaderInitInfoBaseHeaderKHR createSafe(long address) {
        return address == 0 ? null : new XrLoaderInitInfoBaseHeaderKHR(address, null);
    }

    /**
     * Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrLoaderInitInfoBaseHeaderKHR malloc(MemoryStack stack) {
        return new XrLoaderInitInfoBaseHeaderKHR(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrLoaderInitInfoBaseHeaderKHR} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrLoaderInitInfoBaseHeaderKHR calloc(MemoryStack stack) {
        return new XrLoaderInitInfoBaseHeaderKHR(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    // -----------------------------------

    /** Unsafe version of {@link #type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrLoaderInitInfoBaseHeaderKHR.TYPE); }
    /** Unsafe version of {@link #next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrLoaderInitInfoBaseHeaderKHR.NEXT); }

    /** Unsafe version of {@link #type(int) type}. */
    public static void ntype(long struct, int value) { memPutInt(struct + XrLoaderInitInfoBaseHeaderKHR.TYPE, value); }
    /** Unsafe version of {@link #next(long) next}. */
    public static void nnext(long struct, long value) { memPutAddress(struct + XrLoaderInitInfoBaseHeaderKHR.NEXT, value); }
}