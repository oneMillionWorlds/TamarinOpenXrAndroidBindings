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

import java.util.Map;
import java.util.function.Function;
import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;

/**
 * Structure specifying event data buffer.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrEventDataBuffer {
 *     XrStructureType type;
 *     const void* next;
 *     uint8_t varying[4000];
 * }</code></pre>
 * @noinspection unused
 */
public class XrEventDataBuffer extends Struct<XrEventDataBuffer> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** Runtime validation bit masks for field setters. */
    private static final Map<String, Integer> FIELD_BIT_MASKS;
    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        VARYING;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__array(1, 4000)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        VARYING = layout.offsetof(2);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "varying");
    }

    protected XrEventDataBuffer(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrEventDataBuffer", FIELD_BIT_MASKS);
    }

    @Override
    protected XrEventDataBuffer create(long address, ByteBuffer container) {
        return new XrEventDataBuffer(address, container);
    }

    /**
     * Creates a {@code XrEventDataBuffer} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrEventDataBuffer(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrEventDataBuffer", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrEventDataBuffer.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code varying} field. */
    public ByteBufferView varying() {
        return nvarying(addressUnsafe());
    }
    /** Returns a String view of the {@code varying} field. */
    public String varyingString() {
        return XrEventDataBuffer.nvaryingString(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrEventDataBuffer type(XrStructureType value) { 
        XrEventDataBuffer.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrEventDataBuffer next(long value) { 
        XrEventDataBuffer.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code varying} field. */
    public XrEventDataBuffer varying(ByteBufferView value) { 
        XrEventDataBuffer.nvarying(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("varying");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrEventDataBuffer type$Default() { return type(XrStructureType.XR_TYPE_EVENT_DATA_BUFFER); }

    /** Initializes this struct with the specified values. */
    public XrEventDataBuffer set(
        XrStructureType type,
        long next,
        ByteBufferView varying
    ) {
        type(type);
        next(next);
        varying(varying);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrEventDataBuffer set(XrEventDataBuffer src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrEventDataBuffer{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("varying=");
        sb.append(varyingString());
        sb.append('}');
        return sb.toString();
    }

    private final StructSetterValidationObject setterValidation;

    /**
     * Ensures that, for malloc'ed instances, all field setters have been called before use.
     * If this instance was created with calloc (or copied from another struct), this check is a no-op.
     */
    public void checkValidStateForUse() {
        setterValidation.checkValidStateForUse();
    }

    /**
     * Informs this struct that it has been malloced and so must have setter validation carried out
     */
    @Override
    public void setNeedsToValidateAllMethodsCalled() {
        setterValidation.setNeedsToValidateAllMethodsCalled();
    }

    /**
     * Informs this struct that it no longer needs setter validation carried out (maybe because it is an out parameter)
     */
    @Override
    public void setNoLongerNeedsToValidateAllMethodsCalled() {
        setterValidation.setNoLongerNeedsToValidateAllMethodsCalled();
    }

    /** Get a view of the struct this holds as a {@link XrEventDataBaseHeader} (check its type() then cast it to the specific type) */
    public XrEventDataBaseHeader asXrEventDataBaseHeader() {
        return new XrEventDataBaseHeader(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrEventDataBuffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrEventDataBuffer malloc() {
        XrEventDataBuffer instance = new XrEventDataBuffer(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrEventDataBuffer} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrEventDataBuffer calloc() {
        return new XrEventDataBuffer(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrEventDataBuffer} instance allocated with {@link BufferUtils}. */
    public static XrEventDataBuffer create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrEventDataBuffer(memAddress(container), container);
    }

    /** Returns a new {@code XrEventDataBuffer} instance for the specified memory address. */
    public static XrEventDataBuffer create(long address) {
        return new XrEventDataBuffer(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrEventDataBuffer createSafe(long address) {
        return address == 0 ? null : new XrEventDataBuffer(address, null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity) {
        Buffer buf = new Buffer(nmemAllocChecked(__checkMalloc(capacity * SIZEOF)), capacity);
        buf.markAllAsNeedsValidation();
        return buf;
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
        ByteBuffer container = __create(capacity * SIZEOF);
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
     * Returns a new {@code XrEventDataBuffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrEventDataBuffer malloc(MemoryStack stack) {
        XrEventDataBuffer instance = new XrEventDataBuffer(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrEventDataBuffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrEventDataBuffer calloc(MemoryStack stack) {
        return new XrEventDataBuffer(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity, MemoryStack stack) {
        Buffer buf = new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
        buf.markAllAsNeedsValidation();
        return buf;
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
    public static int ntype(long struct) { return memGetInt(struct + XrEventDataBuffer.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrEventDataBuffer.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrEventDataBuffer.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrEventDataBuffer.NEXT, value); }
    /** Unsafe version of varying}. */
    public static ByteBufferView nvarying(long struct) { 
        long address = struct + XrEventDataBuffer.VARYING;
        ByteBuffer rawBuffer = memByteBuffer(address, 4000);
        return new ByteBufferView(rawBuffer, address);
    }
    /** Unsafe version of varying. */
    public static String nvaryingString(long struct) { return memUTF8(struct + XrEventDataBuffer.VARYING); }
    /** max length 4000 */
    public static void nvarying(long struct, ByteBufferView value) {
        byteBufferLengthCheck(value.getBuffer(),4000);
        memCopy(value.address(), struct + XrEventDataBuffer.VARYING, value.getBuffer().remaining());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrEventDataBuffers */
    public static class PointerBuffer extends TypedPointerBufferView<XrEventDataBuffer> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrEventDataBuffer::create);
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
    /** An array of {@link XrEventDataBuffer} structs. */
    public static class Buffer extends StructBuffer<XrEventDataBuffer, Buffer> {

        private static final Function<Long,XrEventDataBuffer> ELEMENT_FACTORY = address ->XrEventDataBuffer.create(address);

        /**
         * Creates a new {@code XrEventDataBuffer.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrEventDataBuffer#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF, SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap, SIZEOF);
        }

        Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap, SIZEOF);
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
        protected Function<Long,XrEventDataBuffer> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
