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
 * Structure specifying debug utils messenger create info e x t.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrDebugUtilsMessengerCreateInfoEXT {
 *     XrStructureType type;
 *     const void* next;
 *     XrDebugUtilsMessageSeverityFlagsEXT messageSeverities;
 *     XrDebugUtilsMessageTypeFlagsEXT messageTypes;
 *     PFN_xrDebugUtilsMessengerCallbackEXT userCallback;
 *     void* userData;
 * }</code></pre>
 * @noinspection unused
 */
public class XrDebugUtilsMessengerCreateInfoEXT extends Struct<XrDebugUtilsMessengerCreateInfoEXT> {

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
        MESSAGESEVERITIES,
        MESSAGETYPES,
        USERCALLBACK,
        USERDATA;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(8),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        MESSAGESEVERITIES = layout.offsetof(2);
        MESSAGETYPES = layout.offsetof(3);
        USERCALLBACK = layout.offsetof(4);
        USERDATA = layout.offsetof(5);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "messageSeverities", "messageTypes", "userCallback", "userData");
    }

    protected XrDebugUtilsMessengerCreateInfoEXT(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrDebugUtilsMessengerCreateInfoEXT", FIELD_BIT_MASKS);
    }

    @Override
    protected XrDebugUtilsMessengerCreateInfoEXT create(long address, ByteBuffer container) {
        return new XrDebugUtilsMessengerCreateInfoEXT(address, container);
    }

    /**
     * Creates a {@code XrDebugUtilsMessengerCreateInfoEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrDebugUtilsMessengerCreateInfoEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrDebugUtilsMessengerCreateInfoEXT", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrDebugUtilsMessengerCreateInfoEXT.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code messageSeverities} field. */
    public long messageSeverities() {
        return nmessageSeverities(address());
    }
    /** Returns the value of the {@code messageTypes} field. */
    public long messageTypes() {
        return nmessageTypes(address());
    }
    /** Returns the value of the {@code userCallback} field. */
    public long userCallback() {
        return nuserCallback(address());
    }
    /** Returns the value of the {@code userData} field. */
    public long userData() {
        return nuserData(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrDebugUtilsMessengerCreateInfoEXT type(XrStructureType value) { 
        XrDebugUtilsMessengerCreateInfoEXT.ntype(address(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrDebugUtilsMessengerCreateInfoEXT next(long value) { 
        XrDebugUtilsMessengerCreateInfoEXT.nnext(address(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code messageSeverities} field. */
    public XrDebugUtilsMessengerCreateInfoEXT messageSeverities(long value) { 
        XrDebugUtilsMessengerCreateInfoEXT.nmessageSeverities(address(), value);
        this.setterValidation.setFieldCalled("messageSeverities");
        return this;
    }
    /** Sets the specified value to the {@code messageTypes} field. */
    public XrDebugUtilsMessengerCreateInfoEXT messageTypes(long value) { 
        XrDebugUtilsMessengerCreateInfoEXT.nmessageTypes(address(), value);
        this.setterValidation.setFieldCalled("messageTypes");
        return this;
    }
    /** Sets the specified value to the {@code userCallback} field. */
    public XrDebugUtilsMessengerCreateInfoEXT userCallback(long value) { 
        XrDebugUtilsMessengerCreateInfoEXT.nuserCallback(address(), value);
        this.setterValidation.setFieldCalled("userCallback");
        return this;
    }
    /** Sets the specified value to the {@code userData} field. */
    public XrDebugUtilsMessengerCreateInfoEXT userData(long value) { 
        XrDebugUtilsMessengerCreateInfoEXT.nuserData(address(), value);
        this.setterValidation.setFieldCalled("userData");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrDebugUtilsMessengerCreateInfoEXT type$Default() { return type(XrStructureType.XR_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT); }

    /** Initializes this struct with the specified values. */
    public XrDebugUtilsMessengerCreateInfoEXT set(
        XrStructureType type,
        long next,
        long messageSeverities,
        long messageTypes,
        long userCallback,
        long userData
    ) {
        type(type);
        next(next);
        messageSeverities(messageSeverities);
        messageTypes(messageTypes);
        userCallback(userCallback);
        userData(userData);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrDebugUtilsMessengerCreateInfoEXT set(XrDebugUtilsMessengerCreateInfoEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrDebugUtilsMessengerCreateInfoEXT{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("messageSeverities=");
        sb.append(String.valueOf(messageSeverities()));
        sb.append(", ");
        sb.append("messageTypes=");
        sb.append(String.valueOf(messageTypes()));
        sb.append(", ");
        sb.append("userCallback=");
        sb.append(String.valueOf(userCallback()));
        sb.append(", ");
        sb.append("userData=");
        sb.append(String.valueOf(userData()));
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

    // -----------------------------------

    /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrDebugUtilsMessengerCreateInfoEXT malloc() {
        XrDebugUtilsMessengerCreateInfoEXT instance = new XrDebugUtilsMessengerCreateInfoEXT(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrDebugUtilsMessengerCreateInfoEXT calloc() {
        return new XrDebugUtilsMessengerCreateInfoEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link BufferUtils}. */
    public static XrDebugUtilsMessengerCreateInfoEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrDebugUtilsMessengerCreateInfoEXT(memAddress(container), container);
    }

    /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance for the specified memory address. */
    public static XrDebugUtilsMessengerCreateInfoEXT create(long address) {
        return new XrDebugUtilsMessengerCreateInfoEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrDebugUtilsMessengerCreateInfoEXT createSafe(long address) {
        return address == 0 ? null : new XrDebugUtilsMessengerCreateInfoEXT(address, null);
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
     * Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrDebugUtilsMessengerCreateInfoEXT malloc(MemoryStack stack) {
        XrDebugUtilsMessengerCreateInfoEXT instance = new XrDebugUtilsMessengerCreateInfoEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrDebugUtilsMessengerCreateInfoEXT calloc(MemoryStack stack) {
        return new XrDebugUtilsMessengerCreateInfoEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrDebugUtilsMessengerCreateInfoEXT.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrDebugUtilsMessengerCreateInfoEXT.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.NEXT, value); }
    /** Unsafe version of messageSeverities}. */
    public static long nmessageSeverities(long struct) { return memGetLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGESEVERITIES); }
    public static void nmessageSeverities(long struct, long value) { memPutLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGESEVERITIES, value); }
    /** Unsafe version of messageTypes}. */
    public static long nmessageTypes(long struct) { return memGetLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGETYPES); }
    public static void nmessageTypes(long struct, long value) { memPutLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGETYPES, value); }
    /** Unsafe version of userCallback}. */
    public static long nuserCallback(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERCALLBACK); }
    public static void nuserCallback(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERCALLBACK, value); }
    /** Unsafe version of userData}. */
    public static long nuserData(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERDATA); }
    public static void nuserData(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERDATA, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrDebugUtilsMessengerCreateInfoEXTs */
    public static class PointerBuffer extends TypedPointerBufferView<XrDebugUtilsMessengerCreateInfoEXT> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrDebugUtilsMessengerCreateInfoEXT::create);
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
    /** An array of {@link XrDebugUtilsMessengerCreateInfoEXT} structs. */
    public static class Buffer extends StructBuffer<XrDebugUtilsMessengerCreateInfoEXT, Buffer> {

        private static final Function<Long,XrDebugUtilsMessengerCreateInfoEXT> ELEMENT_FACTORY = address ->XrDebugUtilsMessengerCreateInfoEXT.create(address);

        /**
         * Creates a new {@code XrDebugUtilsMessengerCreateInfoEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrDebugUtilsMessengerCreateInfoEXT#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrDebugUtilsMessengerCreateInfoEXT> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
