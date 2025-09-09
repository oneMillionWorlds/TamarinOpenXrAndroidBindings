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
 * Structure specifying session action sets attach info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrSessionActionSetsAttachInfo {
 *     XrStructureType type;
 *     const void* next;
 *     uint32_t countActionSets;
 *     const XrActionSet* actionSets;
 * }</code></pre>
 * @noinspection unused
 */
public class XrSessionActionSetsAttachInfo extends Struct<XrSessionActionSetsAttachInfo> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        COUNTACTIONSETS,
        ACTIONSETS;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(8)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        COUNTACTIONSETS = layout.offsetof(2);
        ACTIONSETS = layout.offsetof(3);
    }

    protected XrSessionActionSetsAttachInfo(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrSessionActionSetsAttachInfo create(long address, ByteBuffer container) {
        return new XrSessionActionSetsAttachInfo(address, container);
    }

    /**
     * Creates a {@code XrSessionActionSetsAttachInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSessionActionSetsAttachInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSessionActionSetsAttachInfo.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code countActionSets} field. */
    public int countActionSets() {
        return ncountActionSets(address());
    }
    /** Returns the value of the {@code actionSets} field. */
    public XrActionSet.Buffer actionSets() {
        return nactionSets(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSessionActionSetsAttachInfo type(XrStructureType value) { 
        XrSessionActionSetsAttachInfo.ntype(address(), value.getValue());
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSessionActionSetsAttachInfo next(long value) { 
        XrSessionActionSetsAttachInfo.nnext(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code countActionSets} field. */
    public XrSessionActionSetsAttachInfo countActionSets(int value) { 
        XrSessionActionSetsAttachInfo.ncountActionSets(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code actionSets} field. */
    public XrSessionActionSetsAttachInfo actionSets(XrActionSet.Buffer value) { 
        XrSessionActionSetsAttachInfo.nactionSets(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrSessionActionSetsAttachInfo type$Default() { return type(XrStructureType.XR_TYPE_SESSION_ACTION_SETS_ATTACH_INFO); }

    /** Initializes this struct with the specified values. */
    public XrSessionActionSetsAttachInfo set(
        XrStructureType type,
        long next,
        int countActionSets,
        XrActionSet.Buffer actionSets
    ) {
        type(type);
        next(next);
        countActionSets(countActionSets);
        actionSets(actionSets);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrSessionActionSetsAttachInfo set(XrSessionActionSetsAttachInfo src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrSessionActionSetsAttachInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSessionActionSetsAttachInfo malloc() {
        return new XrSessionActionSetsAttachInfo(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrSessionActionSetsAttachInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrSessionActionSetsAttachInfo calloc() {
        return new XrSessionActionSetsAttachInfo(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSessionActionSetsAttachInfo} instance allocated with {@link BufferUtils}. */
    public static XrSessionActionSetsAttachInfo create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSessionActionSetsAttachInfo(memAddress(container), container);
    }

    /** Returns a new {@code XrSessionActionSetsAttachInfo} instance for the specified memory address. */
    public static XrSessionActionSetsAttachInfo create(long address) {
        return new XrSessionActionSetsAttachInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSessionActionSetsAttachInfo createSafe(long address) {
        return address == 0 ? null : new XrSessionActionSetsAttachInfo(address, null);
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
     * Returns a new {@code XrSessionActionSetsAttachInfo} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSessionActionSetsAttachInfo malloc(MemoryStack stack) {
        return new XrSessionActionSetsAttachInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrSessionActionSetsAttachInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSessionActionSetsAttachInfo calloc(MemoryStack stack) {
        return new XrSessionActionSetsAttachInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrSessionActionSetsAttachInfo.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrSessionActionSetsAttachInfo.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSessionActionSetsAttachInfo.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSessionActionSetsAttachInfo.NEXT, value); }
    /** Unsafe version of countActionSets}. */
    public static int ncountActionSets(long struct) { return memGetInt(struct + XrSessionActionSetsAttachInfo.COUNTACTIONSETS); }
    public static void ncountActionSets(long struct, int value) { memPutInt(struct + XrSessionActionSetsAttachInfo.COUNTACTIONSETS, value); }
    /** Unsafe version of actionSets}. */
    public static XrActionSet.Buffer nactionSets(long struct) { 
        long address = memGetAddress(struct + XrSessionActionSetsAttachInfo.ACTIONSETS);
        int count = ncountActionSets(struct);
        ByteBuffer buffer = memByteBuffer(address, count * Long.BYTES);
        return XrActionSet.create(buffer, address); 
    }
    public static void nactionSets(long struct, XrActionSet.Buffer value ) {
        memPutAddress(struct + XrSessionActionSetsAttachInfo.ACTIONSETS, value.address());
        ncountActionSets(struct + XrSessionActionSetsAttachInfo.ACTIONSETS, value.capacity());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrSessionActionSetsAttachInfos */
    public static class XrSessionActionSetsAttachInfoPointerBufferView extends TypedPointerBufferView<XrSessionActionSetsAttachInfo> {
        public XrSessionActionSetsAttachInfoPointerBufferView(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSessionActionSetsAttachInfo::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static XrSessionActionSetsAttachInfoPointerBufferView calloc(int capacity) {
            return new XrSessionActionSetsAttachInfoPointerBufferView(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrSessionActionSetsAttachInfoPointerBufferView calloc(int capacity, MemoryStack stack) {
            return new XrSessionActionSetsAttachInfoPointerBufferView(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrSessionActionSetsAttachInfoPointerBufferView malloc(int capacity, MemoryStack stack) {
            return new XrSessionActionSetsAttachInfoPointerBufferView(stack.mallocPointer(capacity));
        }

    }
    /** An array of {@link XrSessionActionSetsAttachInfo} structs. */
    public static class Buffer extends StructBuffer<XrSessionActionSetsAttachInfo, Buffer> {

        private static final XrSessionActionSetsAttachInfo ELEMENT_FACTORY = XrSessionActionSetsAttachInfo.create(-1L);

        /**
         * Creates a new {@code XrSessionActionSetsAttachInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSessionActionSetsAttachInfo#SIZEOF}, and its mark will be undefined.</p>
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
        public XrSessionActionSetsAttachInfo get(int index) {
            return XrSessionActionSetsAttachInfo.create(address + index * SIZEOF);
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
        protected XrSessionActionSetsAttachInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public XrStructureType type() { return XrStructureType.fromValue(XrSessionActionSetsAttachInfo.ntype(address())); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrSessionActionSetsAttachInfo.nnext(address()); }
        /** Returns the value of the {@code countActionSets} field. */
        public int countActionSets() { return XrSessionActionSetsAttachInfo.ncountActionSets(address()); }
        /** Returns the value of the {@code actionSets} field. */
        public XrActionSet.Buffer actionSets() { return XrSessionActionSetsAttachInfo.nactionSets(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(XrStructureType value) { 
            XrSessionActionSetsAttachInfo.ntype(address(), value.getValue());
            return this;
        }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { 
            XrSessionActionSetsAttachInfo.nnext(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code countActionSets} field. */
        public Buffer countActionSets(int value) { 
            XrSessionActionSetsAttachInfo.ncountActionSets(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code actionSets} field. */
        public Buffer actionSets(XrActionSet.Buffer value) { 
            XrSessionActionSetsAttachInfo.nactionSets(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XrStructureType.XR_TYPE_SESSION_ACTION_SETS_ATTACH_INFO); }
    }
}
