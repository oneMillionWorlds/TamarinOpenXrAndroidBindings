/*
 * OpenXR Java bindings for Android
 * This file is auto-generated. DO NOT EDIT.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.enums.*;
import com.onemillionworlds.tamarin.openxrbindings.handles.*;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;
import com.onemillionworlds.tamarin.openxrbindings.memory.ByteBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.PointerBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.TypedPointerBufferView;

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

    /** Get a view of a parent class as if it is this type. 
     * Note! It is the caller's responsibility to make sure it really is that type. To do that consult the type parameter
     */
    public static XrHapticVibration cast(XrHapticBaseHeader from) {
        if(from.type() != XrStructureType.XR_TYPE_HAPTIC_VIBRATION){
            throw new IllegalArgumentException("Wrong type passed to cast method. Expected: XR_TYPE_HAPTIC_VIBRATION actual: "+from.type() );
        }

        return new XrHapticVibration(from.address(), from.container());
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
        this.checkSetCalled &= ~NOT_SET_TYPE_MASK;
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrHapticVibration next(long value) { 
        XrHapticVibration.nnext(address(), value);
        this.checkSetCalled &= ~NOT_SET_NEXT_MASK;
        return this;
    }
    /** Sets the specified value to the {@code duration} field. */
    public XrHapticVibration duration(long value) { 
        XrHapticVibration.nduration(address(), value);
        this.checkSetCalled &= ~NOT_SET_DURATION_MASK;
        return this;
    }
    /** Sets the specified value to the {@code frequency} field. */
    public XrHapticVibration frequency(float value) { 
        XrHapticVibration.nfrequency(address(), value);
        this.checkSetCalled &= ~NOT_SET_FREQUENCY_MASK;
        return this;
    }
    /** Sets the specified value to the {@code amplitude} field. */
    public XrHapticVibration amplitude(float value) { 
        XrHapticVibration.namplitude(address(), value);
        this.checkSetCalled &= ~NOT_SET_AMPLITUDE_MASK;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrHapticVibration{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("duration=");
        sb.append(String.valueOf(duration()));
        sb.append(", ");
        sb.append("frequency=");
        sb.append(String.valueOf(frequency()));
        sb.append(", ");
        sb.append("amplitude=");
        sb.append(String.valueOf(amplitude()));
        sb.append('}');
        return sb.toString();
    }

    // Runtime initialization tracking for malloc'ed instances
    private int checkSetCalled;

    private static final int NOT_SET_TYPE_MASK = 1 << 0;
    private static final int NOT_SET_NEXT_MASK = 1 << 1;
    private static final int NOT_SET_DURATION_MASK = 1 << 2;
    private static final int NOT_SET_FREQUENCY_MASK = 1 << 3;
    private static final int NOT_SET_AMPLITUDE_MASK = 1 << 4;

    private static final int ALL_REQUIRED_FIELDS_MASK = NOT_SET_TYPE_MASK | NOT_SET_NEXT_MASK | NOT_SET_DURATION_MASK | NOT_SET_FREQUENCY_MASK | NOT_SET_AMPLITUDE_MASK;

    /**
     * Ensures that, for malloc'ed instances, all field setters have been called before use.
     * If this instance was created with calloc (or copied from another struct), this check is a no-op.
     */
    public void checkValidStateForUse() {
        if (checkSetCalled == 0) { return; }
        StringBuilder missing = new StringBuilder();
        if ((checkSetCalled & NOT_SET_TYPE_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("type");
        }
        if ((checkSetCalled & NOT_SET_NEXT_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("next");
        }
        if ((checkSetCalled & NOT_SET_DURATION_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("duration");
        }
        if ((checkSetCalled & NOT_SET_FREQUENCY_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("frequency");
        }
        if ((checkSetCalled & NOT_SET_AMPLITUDE_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("amplitude");
        }
        if (missing.length() > 0) {
            throw new IllegalStateException("XrHapticVibration has unset fields: " + missing.toString());
        }
    }

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrHapticBaseHeader asParent() {
        return new XrHapticBaseHeader(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrHapticVibration} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrHapticVibration malloc() {
        XrHapticVibration instance = new XrHapticVibration(nmemAllocChecked(SIZEOF), null);
        instance.checkSetCalled = ALL_REQUIRED_FIELDS_MASK;
        return instance;
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
        XrHapticVibration instance = new XrHapticVibration(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.checkSetCalled = ALL_REQUIRED_FIELDS_MASK;
        return instance;
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

    /** A pointer buffer that holds pointers (aka memory addresses) to XrHapticVibrations */
    public static class PointerBuffer extends TypedPointerBufferView<XrHapticVibration> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrHapticVibration::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static PointerBuffer calloc(int capacity) {
            return new PointerBuffer(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static PointerBuffer calloc(int capacity, MemoryStack stack) {
            return new PointerBuffer(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static PointerBuffer malloc(int capacity, MemoryStack stack) {
            return new PointerBuffer(stack.mallocPointer(capacity));
        }

    }
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

    }
}
