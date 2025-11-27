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
 * Structure specifying swapchain image base header.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrSwapchainImageBaseHeader {
 *     XrStructureType type;
 *     void* next;
 * }</code></pre>
 * @noinspection unused
 */
public class XrSwapchainImageBaseHeader extends Struct<XrSwapchainImageBaseHeader> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** Runtime validation bit masks for field setters. */
    private static final Map<String, Integer> FIELD_BIT_MASKS;
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
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next");
    }

    protected XrSwapchainImageBaseHeader(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrSwapchainImageBaseHeader", FIELD_BIT_MASKS);
    }

    @Override
    protected XrSwapchainImageBaseHeader create(long address, ByteBuffer container) {
        return new XrSwapchainImageBaseHeader(address, container);
    }

    /**
     * Creates a {@code XrSwapchainImageBaseHeader} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSwapchainImageBaseHeader(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrSwapchainImageBaseHeader", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSwapchainImageBaseHeader.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSwapchainImageBaseHeader type(XrStructureType value) { 
        XrSwapchainImageBaseHeader.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSwapchainImageBaseHeader next(long value) { 
        XrSwapchainImageBaseHeader.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }

    /** Initializes this struct with the specified values. */
    public XrSwapchainImageBaseHeader set(
        XrStructureType type,
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
    public XrSwapchainImageBaseHeader set(XrSwapchainImageBaseHeader src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrSwapchainImageBaseHeader{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
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

    /** Casts the specified {@link XrSwapchainImageBaseHeader} instance to the {@code XrSwapchainImageOpenGLESKHR} class. 
     * Note it is the callers responsibility to make sure it really is that type (check the type() method) 
     */
    public XrSwapchainImageOpenGLESKHR asXrSwapchainImageOpenGLESKHR() {
        return XrSwapchainImageOpenGLESKHR.cast(this);
    }

    // -----------------------------------

    /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSwapchainImageBaseHeader malloc() {
        XrSwapchainImageBaseHeader instance = new XrSwapchainImageBaseHeader(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrSwapchainImageBaseHeader calloc() {
        return new XrSwapchainImageBaseHeader(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link BufferUtils}. */
    public static XrSwapchainImageBaseHeader create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSwapchainImageBaseHeader(memAddress(container), container);
    }

    /** Returns a new {@code XrSwapchainImageBaseHeader} instance for the specified memory address. */
    public static XrSwapchainImageBaseHeader create(long address) {
        return new XrSwapchainImageBaseHeader(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSwapchainImageBaseHeader createSafe(long address) {
        return address == 0 ? null : new XrSwapchainImageBaseHeader(address, null);
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
     * Returns a new {@code XrSwapchainImageBaseHeader} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSwapchainImageBaseHeader malloc(MemoryStack stack) {
        XrSwapchainImageBaseHeader instance = new XrSwapchainImageBaseHeader(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrSwapchainImageBaseHeader} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSwapchainImageBaseHeader calloc(MemoryStack stack) {
        return new XrSwapchainImageBaseHeader(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrSwapchainImageBaseHeader.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrSwapchainImageBaseHeader.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSwapchainImageBaseHeader.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSwapchainImageBaseHeader.NEXT, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrSwapchainImageBaseHeaders */
    public static class PointerBuffer extends TypedPointerBufferView<XrSwapchainImageBaseHeader> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSwapchainImageBaseHeader::create);
        }

        /** Adds a pointer to a XrSwapchainImageOpenGLESKHR to this buffer. 
         * (This is allowed as XrSwapchainImageOpenGLESKHR extends XrSwapchainImageBaseHeader
         * <p>Note there is no getter for this type. To get by type first get the base type then cast in to the child class (using the cast method in the child)  </p>
         */
        public void setByIndex(int index, XrSwapchainImageOpenGLESKHR struct){
            getBufferView().put(index, struct.address());
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
    /** An array of {@link XrSwapchainImageBaseHeader} structs. */
    public static class Buffer extends StructBuffer<XrSwapchainImageBaseHeader, Buffer> {

        private static final Function<Long,XrSwapchainImageBaseHeader> ELEMENT_FACTORY = address ->XrSwapchainImageBaseHeader.create(address);

        /**
         * Creates a new {@code XrSwapchainImageBaseHeader.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSwapchainImageBaseHeader#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrSwapchainImageBaseHeader> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
