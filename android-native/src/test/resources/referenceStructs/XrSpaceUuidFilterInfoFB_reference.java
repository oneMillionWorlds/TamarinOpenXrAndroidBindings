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
 * Structure specifying space uuid filter info f b.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrSpaceUuidFilterInfoFB {
 *     XrStructureType type;
 *     const void* next;
 *     uint32_t uuidCount;
 *     XrUuidEXT* uuids;
 * }</code></pre>
 * @noinspection unused
 */
public class XrSpaceUuidFilterInfoFB extends Struct<XrSpaceUuidFilterInfoFB> {

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
        UUIDCOUNT,
        UUIDS;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        UUIDCOUNT = layout.offsetof(2);
        UUIDS = layout.offsetof(3);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "uuidCount", "uuids");
    }

    protected XrSpaceUuidFilterInfoFB(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrSpaceUuidFilterInfoFB", FIELD_BIT_MASKS);
    }

    @Override
    protected XrSpaceUuidFilterInfoFB create(long address, ByteBuffer container) {
        return new XrSpaceUuidFilterInfoFB(address, container);
    }

    /** Get a view of a parent class as if it is this type. 
     * Note! It is the caller's responsibility to make sure it really is that type. To do that consult the type parameter
     */
    public static XrSpaceUuidFilterInfoFB cast(XrSpaceFilterInfoBaseHeaderFB from) {
        if(from.type() != XrStructureType.XR_TYPE_SPACE_UUID_FILTER_INFO_FB){
            throw new IllegalArgumentException("Wrong type passed to cast method. Expected: XR_TYPE_SPACE_UUID_FILTER_INFO_FB actual: "+from.type() );
        }

        return new XrSpaceUuidFilterInfoFB(from.address(), from.container());
    }

    /**
     * Creates a {@code XrSpaceUuidFilterInfoFB} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSpaceUuidFilterInfoFB(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrSpaceUuidFilterInfoFB", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSpaceUuidFilterInfoFB.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code uuidCount} field. */
    public int uuidCount() {
        return nuuidCount(addressUnsafe());
    }
    /** Returns the value of the {@code uuids} field. */
    public XrUuidEXT.Buffer uuids() {
        return nuuids(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSpaceUuidFilterInfoFB type(XrStructureType value) { 
        XrSpaceUuidFilterInfoFB.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSpaceUuidFilterInfoFB next(long value) { 
        XrSpaceUuidFilterInfoFB.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code uuidCount} field. */
    public XrSpaceUuidFilterInfoFB uuidCount(int value) { 
        XrSpaceUuidFilterInfoFB.nuuidCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("uuidCount");
        return this;
    }
    /** Sets the specified value to the {@code uuids} field. */
    public XrSpaceUuidFilterInfoFB uuids(XrUuidEXT.Buffer value) { 
        XrSpaceUuidFilterInfoFB.nuuids(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("uuids");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrSpaceUuidFilterInfoFB type$Default() { return type(XrStructureType.XR_TYPE_SPACE_UUID_FILTER_INFO_FB); }

    /** Initializes this struct with the specified values. */
    public XrSpaceUuidFilterInfoFB set(
        XrStructureType type,
        long next,
        int uuidCount,
        XrUuidEXT.Buffer uuids
    ) {
        type(type);
        next(next);
        uuidCount(uuidCount);
        uuids(uuids);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrSpaceUuidFilterInfoFB set(XrSpaceUuidFilterInfoFB src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrSpaceUuidFilterInfoFB{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("uuidCount=");
        sb.append(String.valueOf(uuidCount()));
        sb.append(", ");
        sb.append("uuids=");
        sb.append(String.valueOf(uuids()));
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
    public void setNeedsToValidateAllMethodsCalled() {
        setterValidation.setNeedsToValidateAllMethodsCalled();
    }

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrSpaceFilterInfoBaseHeaderFB asParent() {
        return new XrSpaceFilterInfoBaseHeaderFB(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSpaceUuidFilterInfoFB malloc() {
        XrSpaceUuidFilterInfoFB instance = new XrSpaceUuidFilterInfoFB(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrSpaceUuidFilterInfoFB calloc() {
        return new XrSpaceUuidFilterInfoFB(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated with {@link BufferUtils}. */
    public static XrSpaceUuidFilterInfoFB create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSpaceUuidFilterInfoFB(memAddress(container), container);
    }

    /** Returns a new {@code XrSpaceUuidFilterInfoFB} instance for the specified memory address. */
    public static XrSpaceUuidFilterInfoFB create(long address) {
        return new XrSpaceUuidFilterInfoFB(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSpaceUuidFilterInfoFB createSafe(long address) {
        return address == 0 ? null : new XrSpaceUuidFilterInfoFB(address, null);
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
     * Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpaceUuidFilterInfoFB malloc(MemoryStack stack) {
        XrSpaceUuidFilterInfoFB instance = new XrSpaceUuidFilterInfoFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpaceUuidFilterInfoFB calloc(MemoryStack stack) {
        return new XrSpaceUuidFilterInfoFB(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrSpaceUuidFilterInfoFB.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrSpaceUuidFilterInfoFB.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSpaceUuidFilterInfoFB.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSpaceUuidFilterInfoFB.NEXT, value); }
    /** Unsafe version of uuidCount}. */
    public static int nuuidCount(long struct) { return memGetInt(struct + XrSpaceUuidFilterInfoFB.UUIDCOUNT); }
    public static void nuuidCount(long struct, int value) { memPutInt(struct + XrSpaceUuidFilterInfoFB.UUIDCOUNT, value); }
    /** Unsafe version of uuids}. */
    public static XrUuidEXT.Buffer nuuids(long struct) {
        int count = nuuidCount(struct);
        return XrUuidEXT.createSafe(memGetAddress(struct + XrSpaceUuidFilterInfoFB.UUIDS),count);
    }
    public static void nuuids(long struct, XrUuidEXT.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + UUIDS, address);
        if(value!=null){
            nuuidCount(struct, value.remaining());
        }
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrSpaceUuidFilterInfoFBs */
    public static class PointerBuffer extends TypedPointerBufferView<XrSpaceUuidFilterInfoFB> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSpaceUuidFilterInfoFB::create);
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
    /** An array of {@link XrSpaceUuidFilterInfoFB} structs. */
    public static class Buffer extends StructBuffer<XrSpaceUuidFilterInfoFB, Buffer> {

        private static final Function<Long,XrSpaceUuidFilterInfoFB> ELEMENT_FACTORY = address ->XrSpaceUuidFilterInfoFB.create(address);

        /**
         * Creates a new {@code XrSpaceUuidFilterInfoFB.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSpaceUuidFilterInfoFB#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrSpaceUuidFilterInfoFB> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
