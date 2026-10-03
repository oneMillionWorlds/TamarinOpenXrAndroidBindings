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
 * Structure specifying frame end info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrFrameEndInfo {
 *     XrStructureType type;
 *     const void* next;
 *     XrTime displayTime;
 *     XrEnvironmentBlendMode environmentBlendMode;
 *     uint32_t layerCount;
 *     const XrCompositionLayerBaseHeader* const* layers;
 * }</code></pre>
 * @noinspection unused
 */
public class XrFrameEndInfo extends Struct<XrFrameEndInfo> {

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
        DISPLAYTIME,
        ENVIRONMENTBLENDMODE,
        LAYERCOUNT,
        LAYERS;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        DISPLAYTIME = layout.offsetof(2);
        ENVIRONMENTBLENDMODE = layout.offsetof(3);
        LAYERCOUNT = layout.offsetof(4);
        LAYERS = layout.offsetof(5);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "displayTime", "environmentBlendMode", "layerCount", "layers");
    }

    protected XrFrameEndInfo(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrFrameEndInfo", FIELD_BIT_MASKS);
    }

    @Override
    protected XrFrameEndInfo create(long address, ByteBuffer container) {
        return new XrFrameEndInfo(address, container);
    }

    /**
     * Creates a {@code XrFrameEndInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrFrameEndInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrFrameEndInfo", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrFrameEndInfo.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code displayTime} field. */
    public long displayTime() {
        return ndisplayTime(addressUnsafe());
    }
    /** Returns the value of the {@code environmentBlendMode} field. */
    public XrEnvironmentBlendMode environmentBlendMode() {
        return XrEnvironmentBlendMode.fromValue(XrFrameEndInfo.nenvironmentBlendMode(addressUnsafe()));
    }
    /** Returns the value of the {@code layerCount} field. */
    public int layerCount() {
        return nlayerCount(addressUnsafe());
    }
    /** Returns the value of the {@code layers} field. */
    public XrCompositionLayerBaseHeader.PointerBuffer layers() {
        return nlayers(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrFrameEndInfo type(XrStructureType value) { 
        XrFrameEndInfo.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrFrameEndInfo next(long value) { 
        XrFrameEndInfo.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code displayTime} field. */
    public XrFrameEndInfo displayTime(long value) { 
        XrFrameEndInfo.ndisplayTime(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("displayTime");
        return this;
    }
    /** Sets the specified value to the {@code environmentBlendMode} field. */
    public XrFrameEndInfo environmentBlendMode(XrEnvironmentBlendMode value) { 
        XrFrameEndInfo.nenvironmentBlendMode(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("environmentBlendMode");
        return this;
    }
    /** Sets the specified value to the {@code layerCount} field. */
    public XrFrameEndInfo layerCount(int value) { 
        XrFrameEndInfo.nlayerCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("layerCount");
        return this;
    }
    /** Sets the specified value to the {@code layers} field. */
    public XrFrameEndInfo layers(XrCompositionLayerBaseHeader.PointerBuffer value) { 
        XrFrameEndInfo.nlayers(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("layers");
        this.setterValidation.setFieldCalled("layerCount");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrFrameEndInfo type$Default() { return type(XrStructureType.XR_TYPE_FRAME_END_INFO); }
    /**
     * Prepends the specified {@link XrSecondaryViewConfigurationFrameEndInfoMSFT} to the {@code next} chain (it is pointed at whatever
     * this struct's {@code next} pointed to, or NULL if it hasn't been set yet).
     */
    public XrFrameEndInfo next(XrSecondaryViewConfigurationFrameEndInfoMSFT value) {
        long currentNext = this.setterValidation.isFieldAwaitingSet("next") ? NULL : next();
        return this.next(value.next(currentNext).address());
    }
    /**
     * Prepends the specified {@link XrFrameEndInfoML} to the {@code next} chain (it is pointed at whatever
     * this struct's {@code next} pointed to, or NULL if it hasn't been set yet).
     */
    public XrFrameEndInfo next(XrFrameEndInfoML value) {
        long currentNext = this.setterValidation.isFieldAwaitingSet("next") ? NULL : next();
        return this.next(value.next(currentNext).address());
    }
    /**
     * Prepends the specified {@link XrGlobalDimmerFrameEndInfoML} to the {@code next} chain (it is pointed at whatever
     * this struct's {@code next} pointed to, or NULL if it hasn't been set yet).
     */
    public XrFrameEndInfo next(XrGlobalDimmerFrameEndInfoML value) {
        long currentNext = this.setterValidation.isFieldAwaitingSet("next") ? NULL : next();
        return this.next(value.next(currentNext).address());
    }
    /**
     * Prepends the specified {@link XrLocalDimmingFrameEndInfoMETA} to the {@code next} chain (it is pointed at whatever
     * this struct's {@code next} pointed to, or NULL if it hasn't been set yet).
     */
    public XrFrameEndInfo next(XrLocalDimmingFrameEndInfoMETA value) {
        long currentNext = this.setterValidation.isFieldAwaitingSet("next") ? NULL : next();
        return this.next(value.next(currentNext).address());
    }
    /**
     * Prepends the specified {@link XrSpatialContainerLayerFrameEndInfoEXT} to the {@code next} chain (it is pointed at whatever
     * this struct's {@code next} pointed to, or NULL if it hasn't been set yet).
     */
    public XrFrameEndInfo next(XrSpatialContainerLayerFrameEndInfoEXT value) {
        long currentNext = this.setterValidation.isFieldAwaitingSet("next") ? NULL : next();
        return this.next(value.next(currentNext).address());
    }

    /** Initializes this struct with the specified values. */
    public XrFrameEndInfo set(
        XrStructureType type,
        long next,
        long displayTime,
        XrEnvironmentBlendMode environmentBlendMode,
        int layerCount,
        XrCompositionLayerBaseHeader.PointerBuffer layers
    ) {
        type(type);
        next(next);
        displayTime(displayTime);
        environmentBlendMode(environmentBlendMode);
        layerCount(layerCount);
        layers(layers);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrFrameEndInfo set(XrFrameEndInfo src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrFrameEndInfo{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("displayTime=");
        sb.append(String.valueOf(displayTime()));
        sb.append(", ");
        sb.append("environmentBlendMode=");
        sb.append(String.valueOf(environmentBlendMode()));
        sb.append(", ");
        sb.append("layerCount=");
        sb.append(String.valueOf(layerCount()));
        sb.append(", ");
        sb.append("layers=");
        sb.append(String.valueOf(layers()));
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

    /** Returns a new {@code XrFrameEndInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrFrameEndInfo malloc() {
        XrFrameEndInfo instance = new XrFrameEndInfo(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrFrameEndInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrFrameEndInfo calloc() {
        return new XrFrameEndInfo(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrFrameEndInfo} instance allocated with {@link BufferUtils}. */
    public static XrFrameEndInfo create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrFrameEndInfo(memAddress(container), container);
    }

    /** Returns a new {@code XrFrameEndInfo} instance for the specified memory address. */
    public static XrFrameEndInfo create(long address) {
        return new XrFrameEndInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrFrameEndInfo createSafe(long address) {
        return address == 0 ? null : new XrFrameEndInfo(address, null);
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
     * Returns a new {@code XrFrameEndInfo} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrFrameEndInfo malloc(MemoryStack stack) {
        XrFrameEndInfo instance = new XrFrameEndInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrFrameEndInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrFrameEndInfo calloc(MemoryStack stack) {
        return new XrFrameEndInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrFrameEndInfo.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrFrameEndInfo.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrFrameEndInfo.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrFrameEndInfo.NEXT, value); }
    /** Unsafe version of displayTime}. */
    public static long ndisplayTime(long struct) { return memGetLong(struct + XrFrameEndInfo.DISPLAYTIME); }
    public static void ndisplayTime(long struct, long value) { memPutLong(struct + XrFrameEndInfo.DISPLAYTIME, value); }
    /** Unsafe version of environmentBlendMode}. */
    public static int nenvironmentBlendMode(long struct) { return memGetInt(struct + XrFrameEndInfo.ENVIRONMENTBLENDMODE); }
    public static void nenvironmentBlendMode(long struct, int value ) { memPutInt(struct + XrFrameEndInfo.ENVIRONMENTBLENDMODE, value); }
    /** Unsafe version of layerCount}. */
    public static int nlayerCount(long struct) { return memGetInt(struct + XrFrameEndInfo.LAYERCOUNT); }
    public static void nlayerCount(long struct, int value) { memPutInt(struct + XrFrameEndInfo.LAYERCOUNT, value); }
    /** Unsafe version of layers}. */
    public static XrCompositionLayerBaseHeader.PointerBuffer nlayers(long struct) {
        int count = (int)nlayerCount(struct);
        PointerBufferView pointers = PointerBufferView.wrap(memGetAddress(struct + XrFrameEndInfo.LAYERS), count);
        return pointers == null ? null : new XrCompositionLayerBaseHeader.PointerBuffer(pointers);
    }
    public static void nlayers(long struct, XrCompositionLayerBaseHeader.PointerBuffer value){
        memPutAddress(struct + XrFrameEndInfo.LAYERS, value == null ? NULL : value.address());
        nlayerCount(struct, value == null ? 0 : value.capacity());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrFrameEndInfos */
    public static class PointerBuffer extends TypedPointerBufferView<XrFrameEndInfo> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrFrameEndInfo::create);
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
    /** An array of {@link XrFrameEndInfo} structs. */
    public static class Buffer extends StructBuffer<XrFrameEndInfo, Buffer> {

        private static final Function<Long,XrFrameEndInfo> ELEMENT_FACTORY = address ->XrFrameEndInfo.create(address);

        /**
         * Creates a new {@code XrFrameEndInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrFrameEndInfo#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrFrameEndInfo> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
