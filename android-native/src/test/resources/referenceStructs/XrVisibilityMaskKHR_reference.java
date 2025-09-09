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
 * Structure specifying visibility mask k h r.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrVisibilityMaskKHR {
 *     XrStructureType type;
 *     void* next;
 *     uint32_t vertexCapacityInput;
 *     uint32_t vertexCountOutput;
 *     XrVector2f* vertices;
 *     uint32_t indexCapacityInput;
 *     uint32_t indexCountOutput;
 *     uint32_t* indices;
 * }</code></pre>
 * @noinspection unused
 */
public class XrVisibilityMaskKHR extends Struct<XrVisibilityMaskKHR> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        VERTEXCAPACITYINPUT,
        VERTEXCOUNTOUTPUT,
        VERTICES,
        INDEXCAPACITYINPUT,
        INDEXCOUNTOUTPUT,
        INDICES;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        VERTEXCAPACITYINPUT = layout.offsetof(2);
        VERTEXCOUNTOUTPUT = layout.offsetof(3);
        VERTICES = layout.offsetof(4);
        INDEXCAPACITYINPUT = layout.offsetof(5);
        INDEXCOUNTOUTPUT = layout.offsetof(6);
        INDICES = layout.offsetof(7);
    }

    protected XrVisibilityMaskKHR(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrVisibilityMaskKHR create(long address, ByteBuffer container) {
        return new XrVisibilityMaskKHR(address, container);
    }

    /**
     * Creates a {@code XrVisibilityMaskKHR} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrVisibilityMaskKHR(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrVisibilityMaskKHR.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code vertexCapacityInput} field. */
    public int vertexCapacityInput() {
        return nvertexCapacityInput(address());
    }
    /** Returns the value of the {@code vertexCountOutput} field. */
    public int vertexCountOutput() {
        return nvertexCountOutput(address());
    }
    /** Returns the value of the {@code vertices} field. */
    public XrVector2f.Buffer vertices() {
        return nvertices(address());
    }
    /** Returns the value of the {@code indexCapacityInput} field. */
    public int indexCapacityInput() {
        return nindexCapacityInput(address());
    }
    /** Returns the value of the {@code indexCountOutput} field. */
    public int indexCountOutput() {
        return nindexCountOutput(address());
    }
    /** Returns the value of the {@code indices} field. */
    public long indices() {
        return nindices(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrVisibilityMaskKHR type(XrStructureType value) { 
        XrVisibilityMaskKHR.ntype(address(), value.getValue());
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrVisibilityMaskKHR next(long value) { 
        XrVisibilityMaskKHR.nnext(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code vertexCapacityInput} field. */
    public XrVisibilityMaskKHR vertexCapacityInput(int value) { 
        XrVisibilityMaskKHR.nvertexCapacityInput(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code vertexCountOutput} field. */
    public XrVisibilityMaskKHR vertexCountOutput(int value) { 
        XrVisibilityMaskKHR.nvertexCountOutput(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code vertices} field. */
    public XrVisibilityMaskKHR vertices(XrVector2f.Buffer value) { 
        XrVisibilityMaskKHR.nvertices(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code indexCapacityInput} field. */
    public XrVisibilityMaskKHR indexCapacityInput(int value) { 
        XrVisibilityMaskKHR.nindexCapacityInput(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code indexCountOutput} field. */
    public XrVisibilityMaskKHR indexCountOutput(int value) { 
        XrVisibilityMaskKHR.nindexCountOutput(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code indices} field. */
    public XrVisibilityMaskKHR indices(long value) { 
        XrVisibilityMaskKHR.nindices(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrVisibilityMaskKHR type$Default() { return type(XrStructureType.XR_TYPE_VISIBILITY_MASK_KHR); }

    /** Initializes this struct with the specified values. */
    public XrVisibilityMaskKHR set(
        XrStructureType type,
        long next,
        int vertexCapacityInput,
        int vertexCountOutput,
        XrVector2f.Buffer vertices,
        int indexCapacityInput,
        int indexCountOutput,
        long indices
    ) {
        type(type);
        next(next);
        vertexCapacityInput(vertexCapacityInput);
        vertexCountOutput(vertexCountOutput);
        vertices(vertices);
        indexCapacityInput(indexCapacityInput);
        indexCountOutput(indexCountOutput);
        indices(indices);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrVisibilityMaskKHR set(XrVisibilityMaskKHR src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrVisibilityMaskKHR} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrVisibilityMaskKHR malloc() {
        return new XrVisibilityMaskKHR(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrVisibilityMaskKHR} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrVisibilityMaskKHR calloc() {
        return new XrVisibilityMaskKHR(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrVisibilityMaskKHR} instance allocated with {@link BufferUtils}. */
    public static XrVisibilityMaskKHR create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrVisibilityMaskKHR(memAddress(container), container);
    }

    /** Returns a new {@code XrVisibilityMaskKHR} instance for the specified memory address. */
    public static XrVisibilityMaskKHR create(long address) {
        return new XrVisibilityMaskKHR(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrVisibilityMaskKHR createSafe(long address) {
        return address == 0 ? null : new XrVisibilityMaskKHR(address, null);
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
     * Returns a new {@code XrVisibilityMaskKHR} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrVisibilityMaskKHR malloc(MemoryStack stack) {
        return new XrVisibilityMaskKHR(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrVisibilityMaskKHR} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrVisibilityMaskKHR calloc(MemoryStack stack) {
        return new XrVisibilityMaskKHR(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrVisibilityMaskKHR.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrVisibilityMaskKHR.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrVisibilityMaskKHR.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrVisibilityMaskKHR.NEXT, value); }
    /** Unsafe version of vertexCapacityInput}. */
    public static int nvertexCapacityInput(long struct) { return memGetInt(struct + XrVisibilityMaskKHR.VERTEXCAPACITYINPUT); }
    public static void nvertexCapacityInput(long struct, int value) { memPutInt(struct + XrVisibilityMaskKHR.VERTEXCAPACITYINPUT, value); }
    /** Unsafe version of vertexCountOutput}. */
    public static int nvertexCountOutput(long struct) { return memGetInt(struct + XrVisibilityMaskKHR.VERTEXCOUNTOUTPUT); }
    public static void nvertexCountOutput(long struct, int value) { memPutInt(struct + XrVisibilityMaskKHR.VERTEXCOUNTOUTPUT, value); }
    /** Unsafe version of vertices}. */
    public static XrVector2f.Buffer nvertices(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector2f.createSafe(memGetAddress(struct + XrVisibilityMaskKHR.VERTICES),count);
    }
    public static void nvertices(long struct, XrVector2f.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTICES, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of indexCapacityInput}. */
    public static int nindexCapacityInput(long struct) { return memGetInt(struct + XrVisibilityMaskKHR.INDEXCAPACITYINPUT); }
    public static void nindexCapacityInput(long struct, int value) { memPutInt(struct + XrVisibilityMaskKHR.INDEXCAPACITYINPUT, value); }
    /** Unsafe version of indexCountOutput}. */
    public static int nindexCountOutput(long struct) { return memGetInt(struct + XrVisibilityMaskKHR.INDEXCOUNTOUTPUT); }
    public static void nindexCountOutput(long struct, int value) { memPutInt(struct + XrVisibilityMaskKHR.INDEXCOUNTOUTPUT, value); }
    /** Unsafe version of indices}. */
    public static long nindices(long struct) { return memGetAddress(struct + XrVisibilityMaskKHR.INDICES); }
    public static void nindices(long struct, long value) { memPutAddress(struct + XrVisibilityMaskKHR.INDICES, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrVisibilityMaskKHRs */
    public static class XrVisibilityMaskKHRPointerBufferView extends TypedPointerBufferView<XrVisibilityMaskKHR> {
        public XrVisibilityMaskKHRPointerBufferView(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrVisibilityMaskKHR::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static XrVisibilityMaskKHRPointerBufferView calloc(int capacity) {
            return new XrVisibilityMaskKHRPointerBufferView(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrVisibilityMaskKHRPointerBufferView calloc(int capacity, MemoryStack stack) {
            return new XrVisibilityMaskKHRPointerBufferView(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static XrVisibilityMaskKHRPointerBufferView malloc(int capacity, MemoryStack stack) {
            return new XrVisibilityMaskKHRPointerBufferView(stack.mallocPointer(capacity));
        }

    }
    /** An array of {@link XrVisibilityMaskKHR} structs. */
    public static class Buffer extends StructBuffer<XrVisibilityMaskKHR, Buffer> {

        private static final XrVisibilityMaskKHR ELEMENT_FACTORY = XrVisibilityMaskKHR.create(-1L);

        /**
         * Creates a new {@code XrVisibilityMaskKHR.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrVisibilityMaskKHR#SIZEOF}, and its mark will be undefined.</p>
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
        public XrVisibilityMaskKHR get(int index) {
            return XrVisibilityMaskKHR.create(address + index * SIZEOF);
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
        protected XrVisibilityMaskKHR getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public XrStructureType type() { return XrStructureType.fromValue(XrVisibilityMaskKHR.ntype(address())); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrVisibilityMaskKHR.nnext(address()); }
        /** Returns the value of the {@code vertexCapacityInput} field. */
        public int vertexCapacityInput() { return XrVisibilityMaskKHR.nvertexCapacityInput(address()); }
        /** Returns the value of the {@code vertexCountOutput} field. */
        public int vertexCountOutput() { return XrVisibilityMaskKHR.nvertexCountOutput(address()); }
        /** Returns the value of the {@code vertices} field. */
        public XrVector2f.Buffer vertices() { return XrVisibilityMaskKHR.nvertices(address()); }
        /** Returns the value of the {@code indexCapacityInput} field. */
        public int indexCapacityInput() { return XrVisibilityMaskKHR.nindexCapacityInput(address()); }
        /** Returns the value of the {@code indexCountOutput} field. */
        public int indexCountOutput() { return XrVisibilityMaskKHR.nindexCountOutput(address()); }
        /** Returns the value of the {@code indices} field. */
        public long indices() { return XrVisibilityMaskKHR.nindices(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(XrStructureType value) { 
            XrVisibilityMaskKHR.ntype(address(), value.getValue());
            return this;
        }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { 
            XrVisibilityMaskKHR.nnext(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code vertexCapacityInput} field. */
        public Buffer vertexCapacityInput(int value) { 
            XrVisibilityMaskKHR.nvertexCapacityInput(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code vertexCountOutput} field. */
        public Buffer vertexCountOutput(int value) { 
            XrVisibilityMaskKHR.nvertexCountOutput(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code vertices} field. */
        public Buffer vertices(XrVector2f.Buffer value) { 
            XrVisibilityMaskKHR.nvertices(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code indexCapacityInput} field. */
        public Buffer indexCapacityInput(int value) { 
            XrVisibilityMaskKHR.nindexCapacityInput(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code indexCountOutput} field. */
        public Buffer indexCountOutput(int value) { 
            XrVisibilityMaskKHR.nindexCountOutput(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code indices} field. */
        public Buffer indices(long value) { 
            XrVisibilityMaskKHR.nindices(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XrStructureType.XR_TYPE_VISIBILITY_MASK_KHR); }
    }
}
