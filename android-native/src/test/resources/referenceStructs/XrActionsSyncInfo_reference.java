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
 * Structure specifying actions sync info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrActionsSyncInfo {
 *     XrStructureType type;
 *     const void* next;
 *     uint32_t countActiveActionSets;
 *     const XrActiveActionSet* activeActionSets;
 * }</code></pre>
 * @noinspection unused
 */
public class XrActionsSyncInfo extends Struct<XrActionsSyncInfo> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        COUNTACTIVEACTIONSETS,
        ACTIVEACTIONSETS;

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
        COUNTACTIVEACTIONSETS = layout.offsetof(2);
        ACTIVEACTIONSETS = layout.offsetof(3);
    }

    protected XrActionsSyncInfo(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrActionsSyncInfo create(long address, ByteBuffer container) {
        return new XrActionsSyncInfo(address, container);
    }

    /**
     * Creates a {@code XrActionsSyncInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrActionsSyncInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrActionsSyncInfo.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code countActiveActionSets} field. */
    public int countActiveActionSets() {
        return ncountActiveActionSets(address());
    }
    /** Returns the value of the {@code activeActionSets} field. */
    public XrActiveActionSet.Buffer activeActionSets() {
        return nactiveActionSets(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrActionsSyncInfo type(XrStructureType value) { 
        XrActionsSyncInfo.ntype(address(), value.getValue());
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrActionsSyncInfo next(long value) { 
        XrActionsSyncInfo.nnext(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code countActiveActionSets} field. */
    public XrActionsSyncInfo countActiveActionSets(int value) { 
        XrActionsSyncInfo.ncountActiveActionSets(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code activeActionSets} field. */
    public XrActionsSyncInfo activeActionSets(XrActiveActionSet.Buffer value) { 
        XrActionsSyncInfo.nactiveActionSets(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrActionsSyncInfo type$Default() { return type(XrStructureType.XR_TYPE_ACTIONS_SYNC_INFO); }

    /** Initializes this struct with the specified values. */
    public XrActionsSyncInfo set(
        XrStructureType type,
        long next,
        int countActiveActionSets,
        XrActiveActionSet.Buffer activeActionSets
    ) {
        type(type);
        next(next);
        countActiveActionSets(countActiveActionSets);
        activeActionSets(activeActionSets);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrActionsSyncInfo set(XrActionsSyncInfo src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrActionsSyncInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrActionsSyncInfo malloc() {
        return new XrActionsSyncInfo(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrActionsSyncInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrActionsSyncInfo calloc() {
        return new XrActionsSyncInfo(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrActionsSyncInfo} instance allocated with {@link BufferUtils}. */
    public static XrActionsSyncInfo create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrActionsSyncInfo(memAddress(container), container);
    }

    /** Returns a new {@code XrActionsSyncInfo} instance for the specified memory address. */
    public static XrActionsSyncInfo create(long address) {
        return new XrActionsSyncInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrActionsSyncInfo createSafe(long address) {
        return address == 0 ? null : new XrActionsSyncInfo(address, null);
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
     * Returns a new {@code XrActionsSyncInfo} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrActionsSyncInfo malloc(MemoryStack stack) {
        return new XrActionsSyncInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrActionsSyncInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrActionsSyncInfo calloc(MemoryStack stack) {
        return new XrActionsSyncInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrActionsSyncInfo.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrActionsSyncInfo.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrActionsSyncInfo.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrActionsSyncInfo.NEXT, value); }
    /** Unsafe version of countActiveActionSets}. */
    public static int ncountActiveActionSets(long struct) { return memGetInt(struct + XrActionsSyncInfo.COUNTACTIVEACTIONSETS); }
    public static void ncountActiveActionSets(long struct, int value) { memPutInt(struct + XrActionsSyncInfo.COUNTACTIVEACTIONSETS, value); }
    /** Unsafe version of activeActionSets}. */
    public static XrActiveActionSet.Buffer nactiveActionSets(long struct) {
        int count = ncountActiveActionSets(struct);
        return XrActiveActionSet.createSafe(memGetAddress(struct + XrActionsSyncInfo.ACTIVEACTIONSETS),count);
    }
    public static void nactiveActionSets(long struct, XrActiveActionSet.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + ACTIVEACTIONSETS, address);
        if(value!=null){
            ncountActiveActionSets(struct, value.remaining());
        }
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrActionsSyncInfos */
    public static class XrActionsSyncInfoPointerBufferView extends TypedPointerBufferView<XrActionsSyncInfo> {
        public XrActionsSyncInfoPointerBufferView(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrActionsSyncInfo::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static XrActionsSyncInfoPointerBufferView calloc(int capacity) {
            return new XrActionsSyncInfoPointerBufferView(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrActionsSyncInfoPointerBufferView calloc(int capacity, MemoryStack stack) {
            return new XrActionsSyncInfoPointerBufferView(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrActionsSyncInfoPointerBufferView malloc(int capacity, MemoryStack stack) {
            return new XrActionsSyncInfoPointerBufferView(stack.mallocPointer(capacity));
        }

    }
    /** An array of {@link XrActionsSyncInfo} structs. */
    public static class Buffer extends StructBuffer<XrActionsSyncInfo, Buffer> {

        private static final XrActionsSyncInfo ELEMENT_FACTORY = XrActionsSyncInfo.create(-1L);

        /**
         * Creates a new {@code XrActionsSyncInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrActionsSyncInfo#SIZEOF}, and its mark will be undefined.</p>
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
        public XrActionsSyncInfo get(int index) {
            return XrActionsSyncInfo.create(address + index * SIZEOF);
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
        protected XrActionsSyncInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public XrStructureType type() { return XrStructureType.fromValue(XrActionsSyncInfo.ntype(address())); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrActionsSyncInfo.nnext(address()); }
        /** Returns the value of the {@code countActiveActionSets} field. */
        public int countActiveActionSets() { return XrActionsSyncInfo.ncountActiveActionSets(address()); }
        /** Returns the value of the {@code activeActionSets} field. */
        public XrActiveActionSet.Buffer activeActionSets() { return XrActionsSyncInfo.nactiveActionSets(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(XrStructureType value) { 
            XrActionsSyncInfo.ntype(address(), value.getValue());
            return this;
        }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { 
            XrActionsSyncInfo.nnext(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code countActiveActionSets} field. */
        public Buffer countActiveActionSets(int value) { 
            XrActionsSyncInfo.ncountActiveActionSets(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code activeActionSets} field. */
        public Buffer activeActionSets(XrActiveActionSet.Buffer value) { 
            XrActionsSyncInfo.nactiveActionSets(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XrStructureType.XR_TYPE_ACTIONS_SYNC_INFO); }
    }
}
