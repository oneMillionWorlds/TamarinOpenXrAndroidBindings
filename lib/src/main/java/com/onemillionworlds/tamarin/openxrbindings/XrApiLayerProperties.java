/*
 * OpenXR Java bindings for Android
 */
package com.onemillionworlds.tamarin.openxrbindings;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;

import static com.onemillionworlds.tamarin.openxrbindings.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10.*;

/**
 * Structure specifying layer properties.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrApiLayerProperties {
 *     XrStructureType type;
 *     void * next;
 *     char layerName[XR_MAX_API_LAYER_NAME_SIZE];
 *     XrVersion specVersion;
 *     uint32_t layerVersion;
 *     char description[XR_MAX_API_LAYER_DESCRIPTION_SIZE];
 * }</code></pre>
 */
public class XrApiLayerProperties extends Struct<XrApiLayerProperties> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        LAYERNAME,
        SPECVERSION,
        LAYERVERSION,
        DESCRIPTION;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__array(1, XR_MAX_API_LAYER_NAME_SIZE),
            Layout.__member(8),
            Layout.__member(4),
            Layout.__array(1, XR_MAX_API_LAYER_DESCRIPTION_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        LAYERNAME = layout.offsetof(2);
        SPECVERSION = layout.offsetof(3);
        LAYERVERSION = layout.offsetof(4);
        DESCRIPTION = layout.offsetof(5);
    }

    protected XrApiLayerProperties(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrApiLayerProperties create(long address, @Nullable ByteBuffer container) {
        return new XrApiLayerProperties(address, container);
    }

    /**
     * Creates a {@code XrApiLayerProperties} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrApiLayerProperties(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public int type() { return memGetInt(address() + TYPE); }
    /** Returns the value of the {@code next} field. */
    public long next() { return memGetAddress(address() + NEXT); }
    /** Returns a {@link ByteBuffer} view of the {@code layerName} field. */
    public ByteBuffer layerName() { return memByteBuffer(address() + LAYERNAME, XR_MAX_API_LAYER_NAME_SIZE); }
    /** Returns the null-terminated string stored in the {@code layerName} field. */
    public String layerNameString() { return memUTF8(address() + LAYERNAME); }
    /** Returns the value of the {@code specVersion} field. */
    public long specVersion() { return memGetLong(address() + SPECVERSION); }
    /** Returns the value of the {@code layerVersion} field. */
    public int layerVersion() { return memGetInt(address() + LAYERVERSION); }
    /** Returns a {@link ByteBuffer} view of the {@code description} field. */
    public ByteBuffer description() { return memByteBuffer(address() + DESCRIPTION, XR_MAX_API_LAYER_DESCRIPTION_SIZE); }
    /** Returns the null-terminated string stored in the {@code description} field. */
    public String descriptionString() { return memUTF8(address() + DESCRIPTION); }

    /** Sets the specified value to the {@code type} field. */
    public XrApiLayerProperties type(int value) { memPutInt(address() + TYPE, value); return this; }
    /** Sets the specified value to the {@code next} field. */
    public XrApiLayerProperties next(long value) { memPutAddress(address() + NEXT, value); return this; }
    /** Sets the specified value to the {@code type} field. */
    public XrApiLayerProperties type$Default() { return type(XR_TYPE_API_LAYER_PROPERTIES); }

    /** Initializes this struct with the specified values. */
    public XrApiLayerProperties set(
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
    public XrApiLayerProperties set(XrApiLayerProperties src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrApiLayerProperties} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrApiLayerProperties malloc() {
        return new XrApiLayerProperties(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrApiLayerProperties} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrApiLayerProperties calloc() {
        return new XrApiLayerProperties(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrApiLayerProperties} instance allocated with {@link BufferUtils}. */
    public static XrApiLayerProperties create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrApiLayerProperties(memAddress(container), container);
    }

    /** Returns a new {@code XrApiLayerProperties} instance for the specified memory address. */
    public static XrApiLayerProperties create(long address) {
        return new XrApiLayerProperties(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    @Nullable
    public static XrApiLayerProperties createSafe(long address) {
        return address == 0 ? null : new XrApiLayerProperties(address, null);
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
    @Nullable
    public static Buffer createSafe(long address, int capacity) {
        return address == 0 ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code XrApiLayerProperties} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrApiLayerProperties malloc(MemoryStack stack) {
        return new XrApiLayerProperties(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrApiLayerProperties} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrApiLayerProperties calloc(MemoryStack stack) {
        return new XrApiLayerProperties(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrApiLayerProperties.TYPE); }
    /** Unsafe version of {@link #next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrApiLayerProperties.NEXT); }
    /** Unsafe version of {@link #layerName}. */
    public static ByteBuffer nlayerName(long struct) { return memByteBuffer(struct + XrApiLayerProperties.LAYERNAME, XR_MAX_API_LAYER_NAME_SIZE); }
    /** Unsafe version of {@link #layerNameString}. */
    public static String nlayerNameString(long struct) { return memUTF8(struct + XrApiLayerProperties.LAYERNAME); }
    /** Unsafe version of {@link #specVersion}. */
    public static long nspecVersion(long struct) { return memGetLong(struct + XrApiLayerProperties.SPECVERSION); }
    /** Unsafe version of {@link #layerVersion}. */
    public static int nlayerVersion(long struct) { return memGetInt(struct + XrApiLayerProperties.LAYERVERSION); }
    /** Unsafe version of {@link #description}. */
    public static ByteBuffer ndescription(long struct) { return memByteBuffer(struct + XrApiLayerProperties.DESCRIPTION, XR_MAX_API_LAYER_DESCRIPTION_SIZE); }
    /** Unsafe version of {@link #descriptionString}. */
    public static String ndescriptionString(long struct) { return memUTF8(struct + XrApiLayerProperties.DESCRIPTION); }

    /** Unsafe version of {@link #type(int) type}. */
    public static void ntype(long struct, int value) { memPutInt(struct + XrApiLayerProperties.TYPE, value); }
    /** Unsafe version of {@link #next(long) next}. */
    public static void nnext(long struct, long value) { memPutAddress(struct + XrApiLayerProperties.NEXT, value); }

    // -----------------------------------

    /** An array of {@link XrApiLayerProperties} structs. */
    public static class Buffer extends StructBuffer<XrApiLayerProperties, Buffer> {

        private static final XrApiLayerProperties ELEMENT_FACTORY = XrApiLayerProperties.create(-1L);

        /**
         * Creates a new {@code XrApiLayerProperties.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrApiLayerProperties#SIZEOF}, and its mark will be undefined.</p>
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
        public XrApiLayerProperties get(int index) {
            return XrApiLayerProperties.create(address + index * SIZEOF);
        }
        
        @Override
        public Buffer slice() {
            return slice(0, remaining());
        }

        Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected Buffer create(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
            return new Buffer(address, container, mark, position, limit, capacity);
        }

        @Override
        protected XrApiLayerProperties getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code type} field. */
        public int type() { return XrApiLayerProperties.ntype(address()); }
        /** Returns the value of the {@code next} field. */
        public long next() { return XrApiLayerProperties.nnext(address()); }
        /** Returns a {@link ByteBuffer} view of the {@code layerName} field. */
        public ByteBuffer layerName() { return XrApiLayerProperties.nlayerName(address()); }
        /** Returns the null-terminated string stored in the {@code layerName} field. */
        public String layerNameString() { return XrApiLayerProperties.nlayerNameString(address()); }
        /** Returns the value of the {@code specVersion} field. */
        public long specVersion() { return XrApiLayerProperties.nspecVersion(address()); }
        /** Returns the value of the {@code layerVersion} field. */
        public int layerVersion() { return XrApiLayerProperties.nlayerVersion(address()); }
        /** Returns a {@link ByteBuffer} view of the {@code description} field. */
        public ByteBuffer description() { return XrApiLayerProperties.ndescription(address()); }
        /** Returns the null-terminated string stored in the {@code description} field. */
        public String descriptionString() { return XrApiLayerProperties.ndescriptionString(address()); }

        /** Sets the specified value to the {@code type} field. */
        public Buffer type(int value) { XrApiLayerProperties.ntype(address(), value); return this; }
        /** Sets the specified value to the {@code next} field. */
        public Buffer next(long value) { XrApiLayerProperties.nnext(address(), value); return this; }
        /** Sets the specified value to the {@code type} field. */
        public Buffer type$Default() { return type(XR_TYPE_API_LAYER_PROPERTIES); }
    }
}