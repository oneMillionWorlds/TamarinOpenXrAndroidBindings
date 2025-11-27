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
 * Structure specifying instance create info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrInstanceCreateInfo {
 *     XrStructureType type;
 *     const void* next;
 *     XrInstanceCreateFlags createFlags;
 *     XrApplicationInfo applicationInfo;
 *     uint32_t enabledApiLayerCount;
 *     const * enabledApiLayerNames;
 *     uint32_t enabledExtensionCount;
 *     const * enabledExtensionNames;
 * }</code></pre>
 * @noinspection unused
 */
public class XrInstanceCreateInfo extends Struct<XrInstanceCreateInfo> {

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
        CREATEFLAGS,
        APPLICATIONINFO,
        ENABLEDAPILAYERCOUNT,
        ENABLEDAPILAYERNAMES,
        ENABLEDEXTENSIONCOUNT,
        ENABLEDEXTENSIONNAMES;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(XrApplicationInfo.SIZEOF, XrApplicationInfo.ALIGNOF),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        CREATEFLAGS = layout.offsetof(2);
        APPLICATIONINFO = layout.offsetof(3);
        ENABLEDAPILAYERCOUNT = layout.offsetof(4);
        ENABLEDAPILAYERNAMES = layout.offsetof(5);
        ENABLEDEXTENSIONCOUNT = layout.offsetof(6);
        ENABLEDEXTENSIONNAMES = layout.offsetof(7);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "createFlags", "applicationInfo", "enabledApiLayerCount", "enabledApiLayerNames", "enabledExtensionCount", "enabledExtensionNames");
    }

    protected XrInstanceCreateInfo(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrInstanceCreateInfo", FIELD_BIT_MASKS);
    }

    @Override
    protected XrInstanceCreateInfo create(long address, ByteBuffer container) {
        return new XrInstanceCreateInfo(address, container);
    }

    /**
     * Creates a {@code XrInstanceCreateInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrInstanceCreateInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrInstanceCreateInfo", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrInstanceCreateInfo.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code createFlags} field. */
    public long createFlags() {
        return ncreateFlags(addressUnsafe());
    }
    /** Returns the value of the {@code applicationInfo} field. */
    public XrApplicationInfo applicationInfo() {
        return napplicationInfo(addressUnsafe());
    }
    /** Returns the value of the {@code enabledApiLayerCount} field. */
    public int enabledApiLayerCount() {
        return nenabledApiLayerCount(addressUnsafe());
    }
    /** Returns the value of the {@code enabledApiLayerNames} field. */
    public long enabledApiLayerNames() {
        return nenabledApiLayerNames(addressUnsafe());
    }
    /** Returns the value of the {@code enabledExtensionCount} field. */
    public int enabledExtensionCount() {
        return nenabledExtensionCount(addressUnsafe());
    }
    /** Returns the value of the {@code enabledExtensionNames} field. */
    public long enabledExtensionNames() {
        return nenabledExtensionNames(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrInstanceCreateInfo type(XrStructureType value) { 
        XrInstanceCreateInfo.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrInstanceCreateInfo next(long value) { 
        XrInstanceCreateInfo.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code createFlags} field. */
    public XrInstanceCreateInfo createFlags(long value) { 
        XrInstanceCreateInfo.ncreateFlags(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("createFlags");
        return this;
    }
    /** Sets the specified value to the {@code applicationInfo} field. */
    public XrInstanceCreateInfo applicationInfo(XrApplicationInfo value) { 
        XrInstanceCreateInfo.napplicationInfo(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("applicationInfo");
        return this;
    }
    /** Sets the specified value to the {@code enabledApiLayerCount} field. */
    public XrInstanceCreateInfo enabledApiLayerCount(int value) { 
        XrInstanceCreateInfo.nenabledApiLayerCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledApiLayerCount");
        return this;
    }
    /** Sets the specified value to the {@code enabledApiLayerNames} field. */
    public XrInstanceCreateInfo enabledApiLayerNames(long value) { 
        XrInstanceCreateInfo.nenabledApiLayerNames(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledApiLayerNames");
        return this;
    }
    /** Sets the specified value to the {@code enabledExtensionCount} field. */
    public XrInstanceCreateInfo enabledExtensionCount(int value) { 
        XrInstanceCreateInfo.nenabledExtensionCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledExtensionCount");
        return this;
    }
    /** Sets the specified value to the {@code enabledExtensionNames} field. */
    public XrInstanceCreateInfo enabledExtensionNames(long value) { 
        XrInstanceCreateInfo.nenabledExtensionNames(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("enabledExtensionNames");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrInstanceCreateInfo type$Default() { return type(XrStructureType.XR_TYPE_INSTANCE_CREATE_INFO); }

    /** Initializes this struct with the specified values. */
    public XrInstanceCreateInfo set(
        XrStructureType type,
        long next,
        long createFlags,
        XrApplicationInfo applicationInfo,
        int enabledApiLayerCount,
        long enabledApiLayerNames,
        int enabledExtensionCount,
        long enabledExtensionNames
    ) {
        type(type);
        next(next);
        createFlags(createFlags);
        applicationInfo(applicationInfo);
        enabledApiLayerCount(enabledApiLayerCount);
        enabledApiLayerNames(enabledApiLayerNames);
        enabledExtensionCount(enabledExtensionCount);
        enabledExtensionNames(enabledExtensionNames);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrInstanceCreateInfo set(XrInstanceCreateInfo src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrInstanceCreateInfo{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("createFlags=");
        sb.append(String.valueOf(createFlags()));
        sb.append(", ");
        sb.append("applicationInfo=");
        sb.append(String.valueOf(applicationInfo()));
        sb.append(", ");
        sb.append("enabledApiLayerCount=");
        sb.append(String.valueOf(enabledApiLayerCount()));
        sb.append(", ");
        sb.append("enabledApiLayerNames=");
        sb.append(String.valueOf(enabledApiLayerNames()));
        sb.append(", ");
        sb.append("enabledExtensionCount=");
        sb.append(String.valueOf(enabledExtensionCount()));
        sb.append(", ");
        sb.append("enabledExtensionNames=");
        sb.append(String.valueOf(enabledExtensionNames()));
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

    /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrInstanceCreateInfo malloc() {
        XrInstanceCreateInfo instance = new XrInstanceCreateInfo(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrInstanceCreateInfo calloc() {
        return new XrInstanceCreateInfo(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link BufferUtils}. */
    public static XrInstanceCreateInfo create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrInstanceCreateInfo(memAddress(container), container);
    }

    /** Returns a new {@code XrInstanceCreateInfo} instance for the specified memory address. */
    public static XrInstanceCreateInfo create(long address) {
        return new XrInstanceCreateInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrInstanceCreateInfo createSafe(long address) {
        return address == 0 ? null : new XrInstanceCreateInfo(address, null);
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
     * Returns a new {@code XrInstanceCreateInfo} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrInstanceCreateInfo malloc(MemoryStack stack) {
        XrInstanceCreateInfo instance = new XrInstanceCreateInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrInstanceCreateInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrInstanceCreateInfo calloc(MemoryStack stack) {
        return new XrInstanceCreateInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrInstanceCreateInfo.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrInstanceCreateInfo.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.NEXT, value); }
    /** Unsafe version of createFlags}. */
    public static long ncreateFlags(long struct) { return memGetLong(struct + XrInstanceCreateInfo.CREATEFLAGS); }
    public static void ncreateFlags(long struct, long value) { memPutLong(struct + XrInstanceCreateInfo.CREATEFLAGS, value); }
    /** Unsafe version of applicationInfo}. */
    public static XrApplicationInfo napplicationInfo(long struct) { return XrApplicationInfo.create(struct + XrInstanceCreateInfo.APPLICATIONINFO); }
    public static void napplicationInfo(long struct, XrApplicationInfo value) { memCopy(value.address(), struct +XrInstanceCreateInfo.APPLICATIONINFO,XrApplicationInfo.SIZEOF); }
    /** Unsafe version of enabledApiLayerCount}. */
    public static int nenabledApiLayerCount(long struct) { return memGetInt(struct + XrInstanceCreateInfo.ENABLEDAPILAYERCOUNT); }
    public static void nenabledApiLayerCount(long struct, int value) { memPutInt(struct + XrInstanceCreateInfo.ENABLEDAPILAYERCOUNT, value); }
    /** Unsafe version of enabledApiLayerNames}. */
    public static long nenabledApiLayerNames(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.ENABLEDAPILAYERNAMES); }
    public static void nenabledApiLayerNames(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.ENABLEDAPILAYERNAMES, value); }
    /** Unsafe version of enabledExtensionCount}. */
    public static int nenabledExtensionCount(long struct) { return memGetInt(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONCOUNT); }
    public static void nenabledExtensionCount(long struct, int value) { memPutInt(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONCOUNT, value); }
    /** Unsafe version of enabledExtensionNames}. */
    public static long nenabledExtensionNames(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONNAMES); }
    public static void nenabledExtensionNames(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONNAMES, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrInstanceCreateInfos */
    public static class PointerBuffer extends TypedPointerBufferView<XrInstanceCreateInfo> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrInstanceCreateInfo::create);
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
    /** An array of {@link XrInstanceCreateInfo} structs. */
    public static class Buffer extends StructBuffer<XrInstanceCreateInfo, Buffer> {

        private static final Function<Long,XrInstanceCreateInfo> ELEMENT_FACTORY = address ->XrInstanceCreateInfo.create(address);

        /**
         * Creates a new {@code XrInstanceCreateInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrInstanceCreateInfo#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrInstanceCreateInfo> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
