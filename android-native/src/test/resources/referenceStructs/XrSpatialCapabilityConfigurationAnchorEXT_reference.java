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
import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;

import java.nio.ByteBuffer;

import java.util.Map;
import java.util.function.Function;
import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;

/**
 * Structure specifying spatial capability configuration anchor e x t.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrSpatialCapabilityConfigurationAnchorEXT {
 *     XrStructureType type;
 *     const void* next;
 *     XrSpatialCapabilityEXT capability;
 *     uint32_t enabledComponentCount;
 *     const XrSpatialComponentTypeEXT* enabledComponents;
 * }</code></pre>
 * @noinspection unused
 */
public class XrSpatialCapabilityConfigurationAnchorEXT extends Struct<XrSpatialCapabilityConfigurationAnchorEXT> {

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
        CAPABILITY,
        ENABLEDCOMPONENTCOUNT,
        ENABLEDCOMPONENTS;

    static {
        Layout layout = Layout.__struct(
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
        CAPABILITY = layout.offsetof(2);
        ENABLEDCOMPONENTCOUNT = layout.offsetof(3);
        ENABLEDCOMPONENTS = layout.offsetof(4);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "capability", "enabledComponentCount", "enabledComponents");
    }

    protected XrSpatialCapabilityConfigurationAnchorEXT(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrSpatialCapabilityConfigurationAnchorEXT", FIELD_BIT_MASKS);
    }

    @Override
    protected XrSpatialCapabilityConfigurationAnchorEXT create(long address, ByteBuffer container) {
        return new XrSpatialCapabilityConfigurationAnchorEXT(address, container);
    }

    /** Get a view of a parent class as if it is this type. 
     * Note! It is the caller's responsibility to make sure it really is that type. To do that consult the type parameter
     */
    public static XrSpatialCapabilityConfigurationAnchorEXT cast(XrSpatialCapabilityConfigurationBaseHeaderEXT from) {
        if(from.type() != XrStructureType.XR_TYPE_SPATIAL_CAPABILITY_CONFIGURATION_ANCHOR_EXT){
            throw new IllegalArgumentException("Wrong type passed to cast method. Expected: XR_TYPE_SPATIAL_CAPABILITY_CONFIGURATION_ANCHOR_EXT actual: "+from.type() );
        }

        return new XrSpatialCapabilityConfigurationAnchorEXT(from.address(), from.container());
    }

    /**
     * Creates a {@code XrSpatialCapabilityConfigurationAnchorEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSpatialCapabilityConfigurationAnchorEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrSpatialCapabilityConfigurationAnchorEXT", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSpatialCapabilityConfigurationAnchorEXT.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code capability} field. */
    public XrSpatialCapabilityEXT capability() {
        return XrSpatialCapabilityEXT.fromValue(XrSpatialCapabilityConfigurationAnchorEXT.ncapability(addressUnsafe()));
    }
    /** Returns the value of the {@code enabledComponentCount} field. */
    public int enabledComponentCount() {
        return nenabledComponentCount(addressUnsafe());
    }
    /** Returns the value of the {@code enabledComponents} field. */
    public IntBufferView enabledComponents() {
        return nenabledComponents(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT type(XrStructureType value) { 
        XrSpatialCapabilityConfigurationAnchorEXT.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT next(long value) { 
        XrSpatialCapabilityConfigurationAnchorEXT.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code capability} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT capability(XrSpatialCapabilityEXT value) { 
        XrSpatialCapabilityConfigurationAnchorEXT.ncapability(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("capability");
        return this;
    }
    /** Sets the specified value to the {@code enabledComponentCount} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT enabledComponentCount(int value) { 
        XrSpatialCapabilityConfigurationAnchorEXT.nenabledComponentCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledComponentCount");
        return this;
    }
    /** Sets the specified value to the {@code enabledComponents} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT enabledComponents(IntBufferView value) { 
        XrSpatialCapabilityConfigurationAnchorEXT.nenabledComponents(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledComponents");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrSpatialCapabilityConfigurationAnchorEXT type$Default() { return type(XrStructureType.XR_TYPE_SPATIAL_CAPABILITY_CONFIGURATION_ANCHOR_EXT); }

    /** Initializes this struct with the specified values. */
    public XrSpatialCapabilityConfigurationAnchorEXT set(
        XrStructureType type,
        long next,
        XrSpatialCapabilityEXT capability,
        int enabledComponentCount,
        IntBufferView enabledComponents
    ) {
        type(type);
        next(next);
        capability(capability);
        enabledComponentCount(enabledComponentCount);
        enabledComponents(enabledComponents);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrSpatialCapabilityConfigurationAnchorEXT set(XrSpatialCapabilityConfigurationAnchorEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrSpatialCapabilityConfigurationAnchorEXT{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("capability=");
        sb.append(String.valueOf(capability()));
        sb.append(", ");
        sb.append("enabledComponentCount=");
        sb.append(String.valueOf(enabledComponentCount()));
        sb.append(", ");
        sb.append("enabledComponents=");
        sb.append(String.valueOf(enabledComponents()));
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

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrSpatialCapabilityConfigurationBaseHeaderEXT asParent() {
        return new XrSpatialCapabilityConfigurationBaseHeaderEXT(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSpatialCapabilityConfigurationAnchorEXT malloc() {
        XrSpatialCapabilityConfigurationAnchorEXT instance = new XrSpatialCapabilityConfigurationAnchorEXT(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrSpatialCapabilityConfigurationAnchorEXT calloc() {
        return new XrSpatialCapabilityConfigurationAnchorEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance allocated with {@link BufferUtils}. */
    public static XrSpatialCapabilityConfigurationAnchorEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSpatialCapabilityConfigurationAnchorEXT(memAddress(container), container);
    }

    /** Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance for the specified memory address. */
    public static XrSpatialCapabilityConfigurationAnchorEXT create(long address) {
        return new XrSpatialCapabilityConfigurationAnchorEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSpatialCapabilityConfigurationAnchorEXT createSafe(long address) {
        return address == 0 ? null : new XrSpatialCapabilityConfigurationAnchorEXT(address, null);
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
     * Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpatialCapabilityConfigurationAnchorEXT malloc(MemoryStack stack) {
        XrSpatialCapabilityConfigurationAnchorEXT instance = new XrSpatialCapabilityConfigurationAnchorEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrSpatialCapabilityConfigurationAnchorEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpatialCapabilityConfigurationAnchorEXT calloc(MemoryStack stack) {
        return new XrSpatialCapabilityConfigurationAnchorEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSpatialCapabilityConfigurationAnchorEXT.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSpatialCapabilityConfigurationAnchorEXT.NEXT, value); }
    /** Unsafe version of capability}. */
    public static int ncapability(long struct) { return memGetInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.CAPABILITY); }
    public static void ncapability(long struct, int value ) { memPutInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.CAPABILITY, value); }
    /** Unsafe version of enabledComponentCount}. */
    public static int nenabledComponentCount(long struct) { return memGetInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.ENABLEDCOMPONENTCOUNT); }
    public static void nenabledComponentCount(long struct, int value) { memPutInt(struct + XrSpatialCapabilityConfigurationAnchorEXT.ENABLEDCOMPONENTCOUNT, value); }
    /** Unsafe version of enabledComponents}. */
    public static IntBufferView nenabledComponents(long struct) {
        int count = (int)nenabledComponentCount(struct);
        return IntBufferView.wrap(memGetAddress(struct + XrSpatialCapabilityConfigurationAnchorEXT.ENABLEDCOMPONENTS), count);
    }
    public static void nenabledComponents(long struct, IntBufferView value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + ENABLEDCOMPONENTS, address);
        if(value!=null){
            nenabledComponentCount(struct, value.capacity());
        }
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrSpatialCapabilityConfigurationAnchorEXTs */
    public static class PointerBuffer extends TypedPointerBufferView<XrSpatialCapabilityConfigurationAnchorEXT> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSpatialCapabilityConfigurationAnchorEXT::create);
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
    /** An array of {@link XrSpatialCapabilityConfigurationAnchorEXT} structs. */
    public static class Buffer extends StructBuffer<XrSpatialCapabilityConfigurationAnchorEXT, Buffer> {

        private static final Function<Long,XrSpatialCapabilityConfigurationAnchorEXT> ELEMENT_FACTORY = address ->XrSpatialCapabilityConfigurationAnchorEXT.create(address);

        /**
         * Creates a new {@code XrSpatialCapabilityConfigurationAnchorEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSpatialCapabilityConfigurationAnchorEXT#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrSpatialCapabilityConfigurationAnchorEXT> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
