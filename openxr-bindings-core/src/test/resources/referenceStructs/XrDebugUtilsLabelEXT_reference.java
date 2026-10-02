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
 * Structure specifying debug utils label e x t.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrDebugUtilsLabelEXT {
 *     XrStructureType type;
 *     const void* next;
 *     const char* labelName;
 * }</code></pre>
 * @noinspection unused
 */
public class XrDebugUtilsLabelEXT extends Struct<XrDebugUtilsLabelEXT> {

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
        LABELNAME;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        LABELNAME = layout.offsetof(2);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "labelName");
    }

    protected XrDebugUtilsLabelEXT(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrDebugUtilsLabelEXT", FIELD_BIT_MASKS);
    }

    @Override
    protected XrDebugUtilsLabelEXT create(long address, ByteBuffer container) {
        return new XrDebugUtilsLabelEXT(address, container);
    }

    /**
     * Creates a {@code XrDebugUtilsLabelEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrDebugUtilsLabelEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrDebugUtilsLabelEXT", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrDebugUtilsLabelEXT.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code labelName} field. */
    public ByteBufferView labelName() {
        return nlabelName(addressUnsafe());
    }
    /** Returns a String view of the {@code labelName} field. */
    public String labelNameString() {
        return XrDebugUtilsLabelEXT.nlabelNameString(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrDebugUtilsLabelEXT type(XrStructureType value) { 
        XrDebugUtilsLabelEXT.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrDebugUtilsLabelEXT next(long value) { 
        XrDebugUtilsLabelEXT.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code labelName} field. */
    public XrDebugUtilsLabelEXT labelName(ByteBufferView value) { 
        XrDebugUtilsLabelEXT.nlabelName(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("labelName");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrDebugUtilsLabelEXT type$Default() { return type(XrStructureType.XR_TYPE_DEBUG_UTILS_LABEL_EXT); }

    /** Initializes this struct with the specified values. */
    public XrDebugUtilsLabelEXT set(
        XrStructureType type,
        long next,
        ByteBufferView labelName
    ) {
        type(type);
        next(next);
        labelName(labelName);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrDebugUtilsLabelEXT set(XrDebugUtilsLabelEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrDebugUtilsLabelEXT{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("labelName=");
        sb.append(labelNameString());
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

    /** Returns a new {@code XrDebugUtilsLabelEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrDebugUtilsLabelEXT malloc() {
        XrDebugUtilsLabelEXT instance = new XrDebugUtilsLabelEXT(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrDebugUtilsLabelEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrDebugUtilsLabelEXT calloc() {
        return new XrDebugUtilsLabelEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrDebugUtilsLabelEXT} instance allocated with {@link BufferUtils}. */
    public static XrDebugUtilsLabelEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrDebugUtilsLabelEXT(memAddress(container), container);
    }

    /** Returns a new {@code XrDebugUtilsLabelEXT} instance for the specified memory address. */
    public static XrDebugUtilsLabelEXT create(long address) {
        return new XrDebugUtilsLabelEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrDebugUtilsLabelEXT createSafe(long address) {
        return address == 0 ? null : new XrDebugUtilsLabelEXT(address, null);
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
     * Returns a new {@code XrDebugUtilsLabelEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrDebugUtilsLabelEXT malloc(MemoryStack stack) {
        XrDebugUtilsLabelEXT instance = new XrDebugUtilsLabelEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrDebugUtilsLabelEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrDebugUtilsLabelEXT calloc(MemoryStack stack) {
        return new XrDebugUtilsLabelEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrDebugUtilsLabelEXT.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrDebugUtilsLabelEXT.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrDebugUtilsLabelEXT.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrDebugUtilsLabelEXT.NEXT, value); }
    /** Unsafe version of labelName}. */
    public static ByteBufferView nlabelName(long struct) {
        return ByteBufferView.wrapNullTerminated(memGetAddress(struct + XrDebugUtilsLabelEXT.LABELNAME));
    }
    /** Unsafe version of labelNameString. */
    public static String nlabelNameString(long struct) {
        long address = memGetAddress(struct + XrDebugUtilsLabelEXT.LABELNAME);
        return address == NULL ? null : memUTF8(address);
    }
    public static void nlabelName(long struct, ByteBufferView value){
        if(value != null){
            checkNullTerminated(value.getBuffer());
        }
        memPutAddress(struct + XrDebugUtilsLabelEXT.LABELNAME, value == null ? NULL : value.address());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrDebugUtilsLabelEXTs */
    public static class PointerBuffer extends TypedPointerBufferView<XrDebugUtilsLabelEXT> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrDebugUtilsLabelEXT::create);
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
    /** An array of {@link XrDebugUtilsLabelEXT} structs. */
    public static class Buffer extends StructBuffer<XrDebugUtilsLabelEXT, Buffer> {

        private static final Function<Long,XrDebugUtilsLabelEXT> ELEMENT_FACTORY = address ->XrDebugUtilsLabelEXT.create(address);

        /**
         * Creates a new {@code XrDebugUtilsLabelEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrDebugUtilsLabelEXT#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrDebugUtilsLabelEXT> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
