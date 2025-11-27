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

    /** Runtime validation bit masks for field setters. */
    private static final Map<String, Integer> FIELD_BIT_MASKS;
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
            Layout.__member(POINTER_SIZE)
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
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "vertexCapacityInput", "vertexCountOutput", "vertices", "indexCapacityInput", "indexCountOutput", "indices");
    }

    protected XrVisibilityMaskKHR(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrVisibilityMaskKHR", FIELD_BIT_MASKS);
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
        this.setterValidation = new StructSetterValidationObject("XrVisibilityMaskKHR", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrVisibilityMaskKHR.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code vertexCapacityInput} field. */
    public int vertexCapacityInput() {
        return nvertexCapacityInput(addressUnsafe());
    }
    /** Returns the value of the {@code vertexCountOutput} field. */
    public int vertexCountOutput() {
        return nvertexCountOutput(addressUnsafe());
    }
    /** Returns the value of the {@code vertices} field. */
    public XrVector2f.Buffer vertices() {
        return nvertices(addressUnsafe());
    }
    /** Returns the value of the {@code indexCapacityInput} field. */
    public int indexCapacityInput() {
        return nindexCapacityInput(addressUnsafe());
    }
    /** Returns the value of the {@code indexCountOutput} field. */
    public int indexCountOutput() {
        return nindexCountOutput(addressUnsafe());
    }
    /** Returns the value of the {@code indices} field. */
    public long indices() {
        return nindices(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrVisibilityMaskKHR type(XrStructureType value) { 
        XrVisibilityMaskKHR.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrVisibilityMaskKHR next(long value) { 
        XrVisibilityMaskKHR.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code vertexCapacityInput} field. */
    public XrVisibilityMaskKHR vertexCapacityInput(int value) { 
        XrVisibilityMaskKHR.nvertexCapacityInput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code vertexCountOutput} field. */
    public XrVisibilityMaskKHR vertexCountOutput(int value) { 
        XrVisibilityMaskKHR.nvertexCountOutput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexCountOutput");
        return this;
    }
    /** Sets the specified value to the {@code vertices} field. */
    public XrVisibilityMaskKHR vertices(XrVector2f.Buffer value) { 
        XrVisibilityMaskKHR.nvertices(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertices");
        return this;
    }
    /** Sets the specified value to the {@code indexCapacityInput} field. */
    public XrVisibilityMaskKHR indexCapacityInput(int value) { 
        XrVisibilityMaskKHR.nindexCapacityInput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indexCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code indexCountOutput} field. */
    public XrVisibilityMaskKHR indexCountOutput(int value) { 
        XrVisibilityMaskKHR.nindexCountOutput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indexCountOutput");
        return this;
    }
    /** Sets the specified value to the {@code indices} field. */
    public XrVisibilityMaskKHR indices(long value) { 
        XrVisibilityMaskKHR.nindices(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indices");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrVisibilityMaskKHR{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("vertexCapacityInput=");
        sb.append(String.valueOf(vertexCapacityInput()));
        sb.append(", ");
        sb.append("vertexCountOutput=");
        sb.append(String.valueOf(vertexCountOutput()));
        sb.append(", ");
        sb.append("vertices=");
        sb.append(String.valueOf(vertices()));
        sb.append(", ");
        sb.append("indexCapacityInput=");
        sb.append(String.valueOf(indexCapacityInput()));
        sb.append(", ");
        sb.append("indexCountOutput=");
        sb.append(String.valueOf(indexCountOutput()));
        sb.append(", ");
        sb.append("indices=");
        sb.append(String.valueOf(indices()));
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

    // -----------------------------------

    /** Returns a new {@code XrVisibilityMaskKHR} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrVisibilityMaskKHR malloc() {
        XrVisibilityMaskKHR instance = new XrVisibilityMaskKHR(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
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
     * Returns a new {@code XrVisibilityMaskKHR} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrVisibilityMaskKHR malloc(MemoryStack stack) {
        XrVisibilityMaskKHR instance = new XrVisibilityMaskKHR(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
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
    public static class PointerBuffer extends TypedPointerBufferView<XrVisibilityMaskKHR> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrVisibilityMaskKHR::create);
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
    /** An array of {@link XrVisibilityMaskKHR} structs. */
    public static class Buffer extends StructBuffer<XrVisibilityMaskKHR, Buffer> {

        private static final Function<Long,XrVisibilityMaskKHR> ELEMENT_FACTORY = address ->XrVisibilityMaskKHR.create(address);

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
        protected Function<Long,XrVisibilityMaskKHR> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
