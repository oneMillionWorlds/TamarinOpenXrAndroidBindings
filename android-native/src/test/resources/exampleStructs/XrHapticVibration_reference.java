/*
 * OpenXR Java bindings for Android
 * This file is auto-generated. DO NOT EDIT.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.enums.*;
import com.onemillionworlds.tamarin.openxrbindings.handles.*;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.ByteBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;

/**
 * Structure specifying haptic vibration.
 *
 * <h3>Layout</h3>
 *
 * <pre><code>
 * struct XrHapticVibration {
 *     XrStructureType type;
 *     const void* next;
 *     XrDuration duration;
 *     float frequency;
 *     float amplitude;
 * }</code></pre>
 * @noinspection unused
 */
public class XrHapticVibration extends Struct<XrHapticVibration> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        DURATION,
        FREQUENCY,
        AMPLITUDE;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(4),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        DURATION = layout.offsetof(2);
        FREQUENCY = layout.offsetof(3);
        AMPLITUDE = layout.offsetof(4);
    }

    protected XrHapticVibration(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrHapticVibration create(long address, ByteBuffer container) {
        return new XrHapticVibration(address, container);
    }

    /**
     * Creates a {@code XrHapticVibration} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrHapticVibration(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrHapticVibration.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code duration} field. */
    public long duration() {
        return nduration(address());
    }
    /** Returns the value of the {@code frequency} field. */
    public float frequency() {
        return nfrequency(address());
    }
    /** Returns the value of the {@code amplitude} field. */
    public float amplitude() {
        return namplitude(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrHapticVibration type(XrStructureType value) {
        XrHapticVibration.ntype(address(), value.getValue());
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrHapticVibration next(long value) {
        XrHapticVibration.nnext(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code duration} field. */
    public XrHapticVibration duration(long value) {
        XrHapticVibration.nduration(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code frequency} field. */
    public XrHapticVibration frequency(float value) {
        XrHapticVibration.nfrequency(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code amplitude} field. */
    public XrHapticVibration amplitude(float value) {
        XrHapticVibration.namplitude(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrHapticVibration type$Default() { return type(XrStructureType.XR_TYPE_HAPTIC_VIBRATION); }

    /** Initializes this struct with the specified values. */
    public XrHapticVibration set(
        XrStructureType type,
        long next,
        long duration,
        float frequency,
        float amplitude
    ) {
        type(type);
        next(next);
        duration(duration);
        frequency(frequency);
        amplitude(amplitude);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrHapticVibration set(XrHapticVibration src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrHapticBaseHeader asParent() {
        return new XrHapticBaseHeader(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrHapticVibration} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrHapticVibration malloc() {
        return new XrHapticVibration(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrHapticVibration} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrHapticVibration calloc() {
        return new XrHapticVibration(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrHapticVibration} instance allocated with {@link BufferUtils}. */
    public static XrHapticVibration create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrHapticVibration(memAddress(container), container);
    }

    /** Returns a new {@code XrHapticVibration} instance for the specified memory address. */
    public static XrHapticVibration create(long address) {
        return new XrHapticVibration(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrHapticVibration createSafe(long address) {
        return address == 0 ? null : new XrHapticVibration(address, null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity) {
        return new Buffer(nmemAllocChecked(__checkMalloc(capacity * SIZEOF)), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer create(int capacity) {
        ByteBuffer container = __create(capacity, SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static Buffer createSafe(long address, int capacity) {
        return address == 0 ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code XrHapticVibration} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHapticVibration malloc(MemoryStack stack) {
        return new XrHapticVibration(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrHapticVibration} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHapticVibration calloc(MemoryStack stack) {
        return new XrHapticVibration(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** Unsafe version of type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrHapticVibration.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrHapticVibration.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrHapticVibration.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrHapticVibration.NEXT, value); }
    /** Unsafe version of duration}. */
    public static long nduration(long struct) { return memGetLong(struct + XrHapticVibration.DURATION); }
    public static void nduration(long struct, long value) { memPutLong(struct + XrHapticVibration.DURATION, value); }
    /** Unsafe version of frequency}. */
    public static float nfrequency(long struct) { return memGetFloat(struct + XrHapticVibration.FREQUENCY); }
    public static void nfrequency(long struct, float value) { memPutFloat(struct + XrHapticVibration.FREQUENCY, value); }
    /** Unsafe version of amplitude}. */
    public static float namplitude(long struct) { return memGetFloat(struct + XrHapticVibration.AMPLITUDE); }
    public static void namplitude(long struct, float value) { memPutFloat(struct + XrHapticVibration.AMPLITUDE, value); }


    // -----------------------------------

    /** An array of {@link XrHapticVibration} structs. */
    public static class Buffer extends StructBuffer<XrHapticVibration, Buffer> {

        private static final XrHapticVibration ELEMENT_FACTORY = XrHapticVibration.create(-1L);

        /**
         * Creates a new {@code XrHapticVibration.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrHapticVibration#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        @Override
        public XrHapticVibration get(int index) {
            return XrHapticVibration.create(address + index * SIZEOF);
        }

        @Override
        public Buffer slice() {
            return slice(0, remaining());
        }

        Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected Buffer create(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {
            return new Buffer(address, container, mark, position, limit, capacity);
        }

        @Override
        protected XrHapticVibration getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public XrStructureType type() { return XrStructureType.fromValue(XrHapticVibration.ntype(address())); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrHapticVibration.nnext(address()); }
        /** Returns the value of the {@code duration} field. */
        public long duration() { return XrHapticVibration.nduration(address()); }
        /** Returns the value of the {@code frequency} field. */
        public float frequency() { return XrHapticVibration.nfrequency(address()); }
        /** Returns the value of the {@code amplitude} field. */
        public float amplitude() { return XrHapticVibration.namplitude(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(XrStructureType value) {
            XrHapticVibration.ntype(address(), value.getValue());
            return this;
        }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) {
            XrHapticVibration.nnext(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code duration} field. */
        public Buffer duration(long value) {
            XrHapticVibration.nduration(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code frequency} field. */
        public Buffer frequency(float value) {
            XrHapticVibration.nfrequency(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code amplitude} field. */
        public Buffer amplitude(float value) {
            XrHapticVibration.namplitude(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XrStructureType.XR_TYPE_HAPTIC_VIBRATION); }
    }
}
