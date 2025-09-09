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
    }

    protected XrSpaceUuidFilterInfoFB(long address, ByteBuffer container) {
        super(address, container);
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
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSpaceUuidFilterInfoFB.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code uuidCount} field. */
    public int uuidCount() {
        return nuuidCount(address());
    }
    /** Returns the value of the {@code uuids} field. */
    public XrUuidEXT.Buffer uuids() {
        return nuuids(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSpaceUuidFilterInfoFB type(XrStructureType value) { 
        XrSpaceUuidFilterInfoFB.ntype(address(), value.getValue());
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSpaceUuidFilterInfoFB next(long value) { 
        XrSpaceUuidFilterInfoFB.nnext(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code uuidCount} field. */
    public XrSpaceUuidFilterInfoFB uuidCount(int value) { 
        XrSpaceUuidFilterInfoFB.nuuidCount(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code uuids} field. */
    public XrSpaceUuidFilterInfoFB uuids(XrUuidEXT.Buffer value) { 
        XrSpaceUuidFilterInfoFB.nuuids(address(), value);
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

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrSpaceFilterInfoBaseHeaderFB asParent() {
        return new XrSpaceFilterInfoBaseHeaderFB(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSpaceUuidFilterInfoFB malloc() {
        return new XrSpaceUuidFilterInfoFB(nmemAllocChecked(SIZEOF), null);
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
     * Returns a new {@code XrSpaceUuidFilterInfoFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpaceUuidFilterInfoFB malloc(MemoryStack stack) {
        return new XrSpaceUuidFilterInfoFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
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
    public static class XrSpaceUuidFilterInfoFBPointerBufferView extends TypedPointerBufferView<XrSpaceUuidFilterInfoFB> {
        public XrSpaceUuidFilterInfoFBPointerBufferView(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSpaceUuidFilterInfoFB::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static XrSpaceUuidFilterInfoFBPointerBufferView calloc(int capacity) {
            return new XrSpaceUuidFilterInfoFBPointerBufferView(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrSpaceUuidFilterInfoFBPointerBufferView calloc(int capacity, MemoryStack stack) {
            return new XrSpaceUuidFilterInfoFBPointerBufferView(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrSpaceUuidFilterInfoFBPointerBufferView malloc(int capacity, MemoryStack stack) {
            return new XrSpaceUuidFilterInfoFBPointerBufferView(stack.mallocPointer(capacity));
        }

    }
    /** An array of {@link XrSpaceUuidFilterInfoFB} structs. */
    public static class Buffer extends StructBuffer<XrSpaceUuidFilterInfoFB, Buffer> {

        private static final XrSpaceUuidFilterInfoFB ELEMENT_FACTORY = XrSpaceUuidFilterInfoFB.create(-1L);

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
            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        @Override
        public XrSpaceUuidFilterInfoFB get(int index) {
            return XrSpaceUuidFilterInfoFB.create(address + index * SIZEOF);
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
        protected XrSpaceUuidFilterInfoFB getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public XrStructureType type() { return XrStructureType.fromValue(XrSpaceUuidFilterInfoFB.ntype(address())); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrSpaceUuidFilterInfoFB.nnext(address()); }
        /** Returns the value of the {@code uuidCount} field. */
        public int uuidCount() { return XrSpaceUuidFilterInfoFB.nuuidCount(address()); }
        /** Returns the value of the {@code uuids} field. */
        public XrUuidEXT.Buffer uuids() { return XrSpaceUuidFilterInfoFB.nuuids(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(XrStructureType value) { 
            XrSpaceUuidFilterInfoFB.ntype(address(), value.getValue());
            return this;
        }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { 
            XrSpaceUuidFilterInfoFB.nnext(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code uuidCount} field. */
        public Buffer uuidCount(int value) { 
            XrSpaceUuidFilterInfoFB.nuuidCount(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code uuids} field. */
        public Buffer uuids(XrUuidEXT.Buffer value) { 
            XrSpaceUuidFilterInfoFB.nuuids(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XrStructureType.XR_TYPE_SPACE_UUID_FILTER_INFO_FB); }
    }
}
