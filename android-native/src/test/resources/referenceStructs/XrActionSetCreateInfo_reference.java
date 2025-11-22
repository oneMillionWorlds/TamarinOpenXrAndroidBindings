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
 * Structure specifying action set create info.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrActionSetCreateInfo {
 *     XrStructureType type;
 *     const void* next;
 *     char actionSetName[XR_MAX_ACTION_SET_NAME_SIZE];
 *     char localizedActionSetName[XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE];
 *     uint32_t priority;
 * }</code></pre>
 * @noinspection unused
 */
public class XrActionSetCreateInfo extends Struct<XrActionSetCreateInfo> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        ACTIONSETNAME,
        LOCALIZEDACTIONSETNAME,
        PRIORITY;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__array(1, XR_MAX_ACTION_SET_NAME_SIZE),
            Layout.__array(1, XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        ACTIONSETNAME = layout.offsetof(2);
        LOCALIZEDACTIONSETNAME = layout.offsetof(3);
        PRIORITY = layout.offsetof(4);
    }

    protected XrActionSetCreateInfo(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrActionSetCreateInfo create(long address, ByteBuffer container) {
        return new XrActionSetCreateInfo(address, container);
    }

    /**
     * Creates a {@code XrActionSetCreateInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrActionSetCreateInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrActionSetCreateInfo.ntype(address()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(address());
    }
    /** Returns the value of the {@code actionSetName} field. */
    public ByteBufferView actionSetName() {
        return nactionSetName(address());
    }
    /** Returns a String view of the {@code actionSetName} field. */
    public String actionSetNameString() {
        return XrActionSetCreateInfo.nactionSetNameString(address());
    }
    /** Returns the value of the {@code localizedActionSetName} field. */
    public ByteBufferView localizedActionSetName() {
        return nlocalizedActionSetName(address());
    }
    /** Returns a String view of the {@code localizedActionSetName} field. */
    public String localizedActionSetNameString() {
        return XrActionSetCreateInfo.nlocalizedActionSetNameString(address());
    }
    /** Returns the value of the {@code priority} field. */
    public int priority() {
        return npriority(address());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrActionSetCreateInfo type(XrStructureType value) { 
        XrActionSetCreateInfo.ntype(address(), value.getValue());
        this.checkSetCalled &= ~NOT_SET_TYPE_MASK;
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrActionSetCreateInfo next(long value) { 
        XrActionSetCreateInfo.nnext(address(), value);
        this.checkSetCalled &= ~NOT_SET_NEXT_MASK;
        return this;
    }
    /** Sets the specified value to the {@code actionSetName} field. */
    public XrActionSetCreateInfo actionSetName(ByteBufferView value) { 
        XrActionSetCreateInfo.nactionSetName(address(), value);
        this.checkSetCalled &= ~NOT_SET_ACTIONSETNAME_MASK;
        return this;
    }
    /** Sets the specified value to the {@code localizedActionSetName} field. */
    public XrActionSetCreateInfo localizedActionSetName(ByteBufferView value) { 
        XrActionSetCreateInfo.nlocalizedActionSetName(address(), value);
        this.checkSetCalled &= ~NOT_SET_LOCALIZEDACTIONSETNAME_MASK;
        return this;
    }
    /** Sets the specified value to the {@code priority} field. */
    public XrActionSetCreateInfo priority(int value) { 
        XrActionSetCreateInfo.npriority(address(), value);
        this.checkSetCalled &= ~NOT_SET_PRIORITY_MASK;
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrActionSetCreateInfo type$Default() { return type(XrStructureType.XR_TYPE_ACTION_SET_CREATE_INFO); }

    /** Initializes this struct with the specified values. */
    public XrActionSetCreateInfo set(
        XrStructureType type,
        long next,
        ByteBufferView actionSetName,
        ByteBufferView localizedActionSetName,
        int priority
    ) {
        type(type);
        next(next);
        actionSetName(actionSetName);
        localizedActionSetName(localizedActionSetName);
        priority(priority);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrActionSetCreateInfo set(XrActionSetCreateInfo src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrActionSetCreateInfo{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("actionSetName=");
        sb.append(actionSetNameString());
        sb.append(", ");
        sb.append("localizedActionSetName=");
        sb.append(localizedActionSetNameString());
        sb.append(", ");
        sb.append("priority=");
        sb.append(String.valueOf(priority()));
        sb.append('}');
        return sb.toString();
    }

    // Runtime initialization tracking for malloc'ed instances
    private int checkSetCalled;

    private static final int NOT_SET_TYPE_MASK = 1 << 0;
    private static final int NOT_SET_NEXT_MASK = 1 << 1;
    private static final int NOT_SET_ACTIONSETNAME_MASK = 1 << 2;
    private static final int NOT_SET_LOCALIZEDACTIONSETNAME_MASK = 1 << 3;
    private static final int NOT_SET_PRIORITY_MASK = 1 << 4;

    private static final int ALL_REQUIRED_FIELDS_MASK = NOT_SET_TYPE_MASK | NOT_SET_NEXT_MASK | NOT_SET_ACTIONSETNAME_MASK | NOT_SET_LOCALIZEDACTIONSETNAME_MASK | NOT_SET_PRIORITY_MASK;

    /**
     * Ensures that, for malloc'ed instances, all field setters have been called before use.
     * If this instance was created with calloc (or copied from another struct), this check is a no-op.
     */
    public void checkValidStateForUse() {
        if (checkSetCalled == 0) { return; }
        StringBuilder missing = new StringBuilder();
        if ((checkSetCalled & NOT_SET_TYPE_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("type");
        }
        if ((checkSetCalled & NOT_SET_NEXT_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("next");
        }
        if ((checkSetCalled & NOT_SET_ACTIONSETNAME_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("actionSetName");
        }
        if ((checkSetCalled & NOT_SET_LOCALIZEDACTIONSETNAME_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("localizedActionSetName");
        }
        if ((checkSetCalled & NOT_SET_PRIORITY_MASK) != 0) {
            if (missing.length() > 0) missing.append(", ");
            missing.append("priority");
        }
        if (missing.length() > 0) {
            throw new IllegalStateException("XrActionSetCreateInfo has unset fields: " + missing.toString());
        }
    }

    // -----------------------------------

    /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrActionSetCreateInfo malloc() {
        XrActionSetCreateInfo instance = new XrActionSetCreateInfo(nmemAllocChecked(SIZEOF), null);
        instance.checkSetCalled = ALL_REQUIRED_FIELDS_MASK;
        return instance;
    }

    /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrActionSetCreateInfo calloc() {
        return new XrActionSetCreateInfo(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link BufferUtils}. */
    public static XrActionSetCreateInfo create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrActionSetCreateInfo(memAddress(container), container);
    }

    /** Returns a new {@code XrActionSetCreateInfo} instance for the specified memory address. */
    public static XrActionSetCreateInfo create(long address) {
        return new XrActionSetCreateInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrActionSetCreateInfo createSafe(long address) {
        return address == 0 ? null : new XrActionSetCreateInfo(address, null);
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
     * Returns a new {@code XrActionSetCreateInfo} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrActionSetCreateInfo malloc(MemoryStack stack) {
        XrActionSetCreateInfo instance = new XrActionSetCreateInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.checkSetCalled = ALL_REQUIRED_FIELDS_MASK;
        return instance;
    }

    /**
     * Returns a new {@code XrActionSetCreateInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrActionSetCreateInfo calloc(MemoryStack stack) {
        return new XrActionSetCreateInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrActionSetCreateInfo.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrActionSetCreateInfo.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrActionSetCreateInfo.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrActionSetCreateInfo.NEXT, value); }
    /** Unsafe version of actionSetName}. */
    public static ByteBufferView nactionSetName(long struct) { 
        long address = struct + XrActionSetCreateInfo.ACTIONSETNAME;
        ByteBuffer rawBuffer = memByteBuffer(address, XR_MAX_ACTION_SET_NAME_SIZE);
        return new ByteBufferView(rawBuffer, address);
    }
    /** Unsafe version of actionSetName. */
    public static String nactionSetNameString(long struct) { return memUTF8(struct + XrActionSetCreateInfo.ACTIONSETNAME); }
    /** max length XR_MAX_ACTION_SET_NAME_SIZE */
    public static void nactionSetName(long struct, ByteBufferView value) {
        byteBufferLengthCheck(value.getBuffer(),XR_MAX_ACTION_SET_NAME_SIZE);
        memCopy(value.address(), struct + XrActionSetCreateInfo.ACTIONSETNAME, value.getBuffer().remaining());
    }
    /** Unsafe version of localizedActionSetName}. */
    public static ByteBufferView nlocalizedActionSetName(long struct) { 
        long address = struct + XrActionSetCreateInfo.LOCALIZEDACTIONSETNAME;
        ByteBuffer rawBuffer = memByteBuffer(address, XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE);
        return new ByteBufferView(rawBuffer, address);
    }
    /** Unsafe version of localizedActionSetName. */
    public static String nlocalizedActionSetNameString(long struct) { return memUTF8(struct + XrActionSetCreateInfo.LOCALIZEDACTIONSETNAME); }
    /** max length XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE */
    public static void nlocalizedActionSetName(long struct, ByteBufferView value) {
        byteBufferLengthCheck(value.getBuffer(),XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE);
        memCopy(value.address(), struct + XrActionSetCreateInfo.LOCALIZEDACTIONSETNAME, value.getBuffer().remaining());
    }
    /** Unsafe version of priority}. */
    public static int npriority(long struct) { return memGetInt(struct + XrActionSetCreateInfo.PRIORITY); }
    public static void npriority(long struct, int value) { memPutInt(struct + XrActionSetCreateInfo.PRIORITY, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrActionSetCreateInfos */
    public static class PointerBuffer extends TypedPointerBufferView<XrActionSetCreateInfo> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrActionSetCreateInfo::create);
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
    /** An array of {@link XrActionSetCreateInfo} structs. */
    public static class Buffer extends StructBuffer<XrActionSetCreateInfo, Buffer> {

        private static final XrActionSetCreateInfo ELEMENT_FACTORY = XrActionSetCreateInfo.create(-1L);

        /**
         * Creates a new {@code XrActionSetCreateInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrActionSetCreateInfo#SIZEOF}, and its mark will be undefined.</p>
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
        public XrActionSetCreateInfo get(int index) {
            return XrActionSetCreateInfo.create(address + index * SIZEOF);
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
        protected XrActionSetCreateInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
