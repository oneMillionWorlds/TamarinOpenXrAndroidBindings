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
 * Structure specifying spatial component mesh2 d list e x t.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrSpatialComponentMesh2DListEXT {
 *     XrStructureType type;
 *     void* next;
 *     uint32_t meshCount;
 *     XrSpatialMeshDataEXT* meshes;
 * }</code></pre>
 * @noinspection unused
 */
public class XrSpatialComponentMesh2DListEXT extends Struct<XrSpatialComponentMesh2DListEXT> {

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
        MESHCOUNT,
        MESHES;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        MESHCOUNT = layout.offsetof(2);
        MESHES = layout.offsetof(3);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "meshCount", "meshes");
    }

    protected XrSpatialComponentMesh2DListEXT(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrSpatialComponentMesh2DListEXT", FIELD_BIT_MASKS);
    }

    @Override
    protected XrSpatialComponentMesh2DListEXT create(long address, ByteBuffer container) {
        return new XrSpatialComponentMesh2DListEXT(address, container);
    }

    /**
     * Creates a {@code XrSpatialComponentMesh2DListEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSpatialComponentMesh2DListEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrSpatialComponentMesh2DListEXT", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrSpatialComponentMesh2DListEXT.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code meshCount} field. */
    public int meshCount() {
        return nmeshCount(addressUnsafe());
    }
    /** Returns the value of the {@code meshes} field. */
    public XrSpatialMeshDataEXT.Buffer meshes() {
        return nmeshes(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrSpatialComponentMesh2DListEXT type(XrStructureType value) { 
        XrSpatialComponentMesh2DListEXT.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrSpatialComponentMesh2DListEXT next(long value) { 
        XrSpatialComponentMesh2DListEXT.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code meshCount} field. */
    public XrSpatialComponentMesh2DListEXT meshCount(int value) { 
        XrSpatialComponentMesh2DListEXT.nmeshCount(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("meshCount");
        return this;
    }
    /** Sets the specified value to the {@code meshes} field. */
    public XrSpatialComponentMesh2DListEXT meshes(XrSpatialMeshDataEXT.Buffer value) { 
        XrSpatialComponentMesh2DListEXT.nmeshes(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("meshes");
        this.setterValidation.setFieldCalled("meshCount");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrSpatialComponentMesh2DListEXT type$Default() { return type(XrStructureType.XR_TYPE_SPATIAL_COMPONENT_MESH_2D_LIST_EXT); }

    /** Initializes this struct with the specified values. */
    public XrSpatialComponentMesh2DListEXT set(
        XrStructureType type,
        long next,
        int meshCount,
        XrSpatialMeshDataEXT.Buffer meshes
    ) {
        type(type);
        next(next);
        meshCount(meshCount);
        meshes(meshes);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrSpatialComponentMesh2DListEXT set(XrSpatialComponentMesh2DListEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrSpatialComponentMesh2DListEXT{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("meshCount=");
        sb.append(String.valueOf(meshCount()));
        sb.append(", ");
        sb.append("meshes=");
        sb.append(String.valueOf(meshes()));
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

    /** Returns a new {@code XrSpatialComponentMesh2DListEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrSpatialComponentMesh2DListEXT malloc() {
        XrSpatialComponentMesh2DListEXT instance = new XrSpatialComponentMesh2DListEXT(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrSpatialComponentMesh2DListEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrSpatialComponentMesh2DListEXT calloc() {
        return new XrSpatialComponentMesh2DListEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSpatialComponentMesh2DListEXT} instance allocated with {@link BufferUtils}. */
    public static XrSpatialComponentMesh2DListEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSpatialComponentMesh2DListEXT(memAddress(container), container);
    }

    /** Returns a new {@code XrSpatialComponentMesh2DListEXT} instance for the specified memory address. */
    public static XrSpatialComponentMesh2DListEXT create(long address) {
        return new XrSpatialComponentMesh2DListEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSpatialComponentMesh2DListEXT createSafe(long address) {
        return address == 0 ? null : new XrSpatialComponentMesh2DListEXT(address, null);
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
     * Returns a new {@code XrSpatialComponentMesh2DListEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpatialComponentMesh2DListEXT malloc(MemoryStack stack) {
        XrSpatialComponentMesh2DListEXT instance = new XrSpatialComponentMesh2DListEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrSpatialComponentMesh2DListEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSpatialComponentMesh2DListEXT calloc(MemoryStack stack) {
        return new XrSpatialComponentMesh2DListEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrSpatialComponentMesh2DListEXT.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrSpatialComponentMesh2DListEXT.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSpatialComponentMesh2DListEXT.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSpatialComponentMesh2DListEXT.NEXT, value); }
    /** Unsafe version of meshCount}. */
    public static int nmeshCount(long struct) { return memGetInt(struct + XrSpatialComponentMesh2DListEXT.MESHCOUNT); }
    public static void nmeshCount(long struct, int value) { memPutInt(struct + XrSpatialComponentMesh2DListEXT.MESHCOUNT, value); }
    /** Unsafe version of meshes}. */
    public static XrSpatialMeshDataEXT.Buffer nmeshes(long struct) {
        int count = nmeshCount(struct);
        return XrSpatialMeshDataEXT.createSafe(memGetAddress(struct + XrSpatialComponentMesh2DListEXT.MESHES),count);
    }
    public static void nmeshes(long struct, XrSpatialMeshDataEXT.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + MESHES, address);
        nmeshCount(struct, value == null ? 0 : value.remaining());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrSpatialComponentMesh2DListEXTs */
    public static class PointerBuffer extends TypedPointerBufferView<XrSpatialComponentMesh2DListEXT> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrSpatialComponentMesh2DListEXT::create);
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
    /** An array of {@link XrSpatialComponentMesh2DListEXT} structs. */
    public static class Buffer extends StructBuffer<XrSpatialComponentMesh2DListEXT, Buffer> {

        private static final Function<Long,XrSpatialComponentMesh2DListEXT> ELEMENT_FACTORY = address ->XrSpatialComponentMesh2DListEXT.create(address);

        /**
         * Creates a new {@code XrSpatialComponentMesh2DListEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSpatialComponentMesh2DListEXT#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrSpatialComponentMesh2DListEXT> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
