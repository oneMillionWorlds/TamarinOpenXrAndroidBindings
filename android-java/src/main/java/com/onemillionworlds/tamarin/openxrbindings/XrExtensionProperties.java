/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.ByteBuffer;

import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10.*;
import static com.onemillionworlds.tamarin.openxrbindings.XrStructureType.*;

/**
 * Structure specifying extension properties.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrExtensionProperties {
 *     XrStructureType type;
 *     void * next;
 *     char extensionName[XR_MAX_EXTENSION_NAME_SIZE];
 *     uint32_t extensionVersion;
 * }</code></pre>
 */
public class XrExtensionProperties extends Struct<XrExtensionProperties> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        EXTENSIONNAME,
        EXTENSIONVERSION;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__array(1, XR_MAX_EXTENSION_NAME_SIZE),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        EXTENSIONNAME = layout.offsetof(2);
        EXTENSIONVERSION = layout.offsetof(3);
    }

    protected XrExtensionProperties(long address, ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrExtensionProperties create(long address, ByteBuffer container) {
        return new XrExtensionProperties(address, container);
    }

    /**
     * Creates a {@code XrExtensionProperties} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrExtensionProperties(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public int type() { return memGetInt(address() + TYPE); }
    /** Returns the value of the {@code next} field. */
    public long next() { return memGetAddress(address() + NEXT); }
    /** Returns a {@link ByteBuffer} view of the {@code extensionName} field. */
    public ByteBuffer extensionName() { return memByteBuffer(address() + EXTENSIONNAME, XR_MAX_EXTENSION_NAME_SIZE); }
    /** Returns the null-terminated string stored in the {@code extensionName} field. */
    public String extensionNameString() { return memUTF8(address() + EXTENSIONNAME); }
    /** Returns the value of the {@code extensionVersion} field. */
    public int extensionVersion() { return memGetInt(address() + EXTENSIONVERSION); }

    /** Sets the specified value to the {@code type} field. */
    public XrExtensionProperties type(int value) { memPutInt(address() + TYPE, value); return this; }
    /** Sets the specified value to the {@code next} field. */
    public XrExtensionProperties next(long value) { memPutAddress(address() + NEXT, value); return this; }
    /** Sets the specified value to the {@code type} field. */
    public XrExtensionProperties type$Default() { return type(XR_TYPE_EXTENSION_PROPERTIES); }

    /** Initializes this struct with the specified values. */
    public XrExtensionProperties set(
        int type,
        long next
    ) {
        type(type);
        next(next);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrExtensionProperties set(XrExtensionProperties src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrExtensionProperties} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrExtensionProperties malloc() {
        return new XrExtensionProperties(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrExtensionProperties} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrExtensionProperties calloc() {
        return new XrExtensionProperties(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrExtensionProperties} instance allocated with {@link BufferUtils}. */
    public static XrExtensionProperties create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrExtensionProperties(memAddress(container), container);
    }

    /** Returns a new {@code XrExtensionProperties} instance for the specified memory address. */
    public static XrExtensionProperties create(long address) {
        return new XrExtensionProperties(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrExtensionProperties createSafe(long address) {
        return address == 0 ? null : new XrExtensionProperties(address, null);
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
     * Returns a new {@code XrExtensionProperties} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrExtensionProperties malloc(MemoryStack stack) {
        return new XrExtensionProperties(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrExtensionProperties} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrExtensionProperties calloc(MemoryStack stack) {
        return new XrExtensionProperties(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrExtensionProperties.TYPE); }
    /** Unsafe version of {@link #next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrExtensionProperties.NEXT); }
    /** Unsafe version of {@link #extensionName}. */
    public static ByteBuffer nextensionName(long struct) { return memByteBuffer(struct + XrExtensionProperties.EXTENSIONNAME, XR_MAX_EXTENSION_NAME_SIZE); }
    /** Unsafe version of {@link #extensionNameString}. */
    public static String nextensionNameString(long struct) { return memUTF8(struct + XrExtensionProperties.EXTENSIONNAME); }
    /** Unsafe version of {@link #extensionVersion}. */
    public static int nextensionVersion(long struct) { return memGetInt(struct + XrExtensionProperties.EXTENSIONVERSION); }

    /** Unsafe version of {@link #type(int) type}. */
    public static void ntype(long struct, int value) { memPutInt(struct + XrExtensionProperties.TYPE, value); }
    /** Unsafe version of {@link #next(long) next}. */
    public static void nnext(long struct, long value) { memPutAddress(struct + XrExtensionProperties.NEXT, value); }

    // -----------------------------------

    /** An array of {@link XrExtensionProperties} structs. */
    public static class Buffer extends StructBuffer<XrExtensionProperties, Buffer> {

        private static final XrExtensionProperties ELEMENT_FACTORY = XrExtensionProperties.create(-1L);

        /**
         * Creates a new {@code XrExtensionProperties.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrExtensionProperties#SIZEOF}, and its mark will be undefined.</p>
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
        public XrExtensionProperties get(int index) {
            return XrExtensionProperties.create(address + index * SIZEOF);
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
        protected XrExtensionProperties getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public int type() { return XrExtensionProperties.ntype(address()); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrExtensionProperties.nnext(address()); }
        /** Returns a {@link ByteBuffer} view of the {@code extensionName} field. */
        public ByteBuffer extensionName() { return XrExtensionProperties.nextensionName(address()); }
        /** Returns the null-terminated string stored in the {@code extensionName} field. */
        public String extensionNameString() { return XrExtensionProperties.nextensionNameString(address()); }
        /** Returns the value of the {@code extensionVersion} field. */
        public int extensionVersion() { return XrExtensionProperties.nextensionVersion(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(int value) { XrExtensionProperties.ntype(address(), value); return this; }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { XrExtensionProperties.nnext(address(), value); return this; }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XR_TYPE_EXTENSION_PROPERTIES); }
    }
}
