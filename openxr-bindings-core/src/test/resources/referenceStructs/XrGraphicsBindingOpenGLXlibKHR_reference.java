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
 * Structure specifying graphics binding open g l xlib k h r.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrGraphicsBindingOpenGLXlibKHR {
 *     XrStructureType type;
 *     const void* next;
 *     Display* xDisplay;
 *     uint32_t visualid;
 *     GLXFBConfig glxFBConfig;
 *     GLXDrawable glxDrawable;
 *     GLXContext glxContext;
 * }</code></pre>
 * @noinspection unused
 */
public class XrGraphicsBindingOpenGLXlibKHR extends Struct<XrGraphicsBindingOpenGLXlibKHR> {

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
        XDISPLAY,
        VISUALID,
        GLXFBCONFIG,
        GLXDRAWABLE,
        GLXCONTEXT;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(8),
            Layout.__member(8),
            Layout.__member(8)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        XDISPLAY = layout.offsetof(2);
        VISUALID = layout.offsetof(3);
        GLXFBCONFIG = layout.offsetof(4);
        GLXDRAWABLE = layout.offsetof(5);
        GLXCONTEXT = layout.offsetof(6);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "xDisplay", "visualid", "glxFBConfig", "glxDrawable", "glxContext");
    }

    protected XrGraphicsBindingOpenGLXlibKHR(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrGraphicsBindingOpenGLXlibKHR", FIELD_BIT_MASKS);
    }

    @Override
    protected XrGraphicsBindingOpenGLXlibKHR create(long address, ByteBuffer container) {
        return new XrGraphicsBindingOpenGLXlibKHR(address, container);
    }

    /**
     * Creates a {@code XrGraphicsBindingOpenGLXlibKHR} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrGraphicsBindingOpenGLXlibKHR(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrGraphicsBindingOpenGLXlibKHR", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrGraphicsBindingOpenGLXlibKHR.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code xDisplay} field. */
    public long xDisplay() {
        return nxDisplay(addressUnsafe());
    }
    /** Returns the value of the {@code visualid} field. */
    public int visualid() {
        return nvisualid(addressUnsafe());
    }
    /** Returns the value of the {@code glxFBConfig} field. */
    public GLXFBConfig glxFBConfig() {
        return new GLXFBConfig(XrGraphicsBindingOpenGLXlibKHR.nglxFBConfig(addressUnsafe()));
    }
    /** Returns the value of the {@code glxDrawable} field. */
    public long glxDrawable() {
        return nglxDrawable(addressUnsafe());
    }
    /** Returns the value of the {@code glxContext} field. */
    public GLXContext glxContext() {
        return new GLXContext(XrGraphicsBindingOpenGLXlibKHR.nglxContext(addressUnsafe()));
    }

    /** Sets the specified value to the {@code type} field. */
    public XrGraphicsBindingOpenGLXlibKHR type(XrStructureType value) { 
        XrGraphicsBindingOpenGLXlibKHR.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrGraphicsBindingOpenGLXlibKHR next(long value) { 
        XrGraphicsBindingOpenGLXlibKHR.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code xDisplay} field. */
    public XrGraphicsBindingOpenGLXlibKHR xDisplay(long value) { 
        XrGraphicsBindingOpenGLXlibKHR.nxDisplay(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("xDisplay");
        return this;
    }
    /** Sets the specified value to the {@code visualid} field. */
    public XrGraphicsBindingOpenGLXlibKHR visualid(int value) { 
        XrGraphicsBindingOpenGLXlibKHR.nvisualid(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("visualid");
        return this;
    }
    /** Sets the specified value to the {@code glxFBConfig} field. */
    public XrGraphicsBindingOpenGLXlibKHR glxFBConfig(GLXFBConfig value) { 
        XrGraphicsBindingOpenGLXlibKHR.nglxFBConfig(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("glxFBConfig");
        return this;
    }
    /** Sets the specified value to the {@code glxDrawable} field. */
    public XrGraphicsBindingOpenGLXlibKHR glxDrawable(long value) { 
        XrGraphicsBindingOpenGLXlibKHR.nglxDrawable(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("glxDrawable");
        return this;
    }
    /** Sets the specified value to the {@code glxContext} field. */
    public XrGraphicsBindingOpenGLXlibKHR glxContext(GLXContext value) { 
        XrGraphicsBindingOpenGLXlibKHR.nglxContext(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("glxContext");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrGraphicsBindingOpenGLXlibKHR type$Default() { return type(XrStructureType.XR_TYPE_GRAPHICS_BINDING_OPENGL_XLIB_KHR); }

    /** Initializes this struct with the specified values. */
    public XrGraphicsBindingOpenGLXlibKHR set(
        XrStructureType type,
        long next,
        long xDisplay,
        int visualid,
        GLXFBConfig glxFBConfig,
        long glxDrawable,
        GLXContext glxContext
    ) {
        type(type);
        next(next);
        xDisplay(xDisplay);
        visualid(visualid);
        glxFBConfig(glxFBConfig);
        glxDrawable(glxDrawable);
        glxContext(glxContext);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrGraphicsBindingOpenGLXlibKHR set(XrGraphicsBindingOpenGLXlibKHR src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrGraphicsBindingOpenGLXlibKHR{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("xDisplay=");
        sb.append(String.valueOf(xDisplay()));
        sb.append(", ");
        sb.append("visualid=");
        sb.append(String.valueOf(visualid()));
        sb.append(", ");
        sb.append("glxFBConfig=");
        sb.append(String.valueOf(glxFBConfig()));
        sb.append(", ");
        sb.append("glxDrawable=");
        sb.append(String.valueOf(glxDrawable()));
        sb.append(", ");
        sb.append("glxContext=");
        sb.append(String.valueOf(glxContext()));
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

    /** Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrGraphicsBindingOpenGLXlibKHR malloc() {
        XrGraphicsBindingOpenGLXlibKHR instance = new XrGraphicsBindingOpenGLXlibKHR(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrGraphicsBindingOpenGLXlibKHR calloc() {
        return new XrGraphicsBindingOpenGLXlibKHR(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance allocated with {@link BufferUtils}. */
    public static XrGraphicsBindingOpenGLXlibKHR create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrGraphicsBindingOpenGLXlibKHR(memAddress(container), container);
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance for the specified memory address. */
    public static XrGraphicsBindingOpenGLXlibKHR create(long address) {
        return new XrGraphicsBindingOpenGLXlibKHR(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrGraphicsBindingOpenGLXlibKHR createSafe(long address) {
        return address == 0 ? null : new XrGraphicsBindingOpenGLXlibKHR(address, null);
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
     * Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrGraphicsBindingOpenGLXlibKHR malloc(MemoryStack stack) {
        XrGraphicsBindingOpenGLXlibKHR instance = new XrGraphicsBindingOpenGLXlibKHR(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrGraphicsBindingOpenGLXlibKHR} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrGraphicsBindingOpenGLXlibKHR calloc(MemoryStack stack) {
        return new XrGraphicsBindingOpenGLXlibKHR(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrGraphicsBindingOpenGLXlibKHR.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrGraphicsBindingOpenGLXlibKHR.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrGraphicsBindingOpenGLXlibKHR.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrGraphicsBindingOpenGLXlibKHR.NEXT, value); }
    /** Unsafe version of xDisplay}. */
    public static long nxDisplay(long struct) { return memGetAddress(struct + XrGraphicsBindingOpenGLXlibKHR.XDISPLAY); }
    public static void nxDisplay(long struct, long value) { memPutAddress(struct + XrGraphicsBindingOpenGLXlibKHR.XDISPLAY, value); }
    /** Unsafe version of visualid}. */
    public static int nvisualid(long struct) { return memGetInt(struct + XrGraphicsBindingOpenGLXlibKHR.VISUALID); }
    public static void nvisualid(long struct, int value) { memPutInt(struct + XrGraphicsBindingOpenGLXlibKHR.VISUALID, value); }
    /** Unsafe version of glxFBConfig}. */
    public static long nglxFBConfig(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXFBCONFIG); }
    public static void nglxFBConfig(long struct, long value ) { memPutLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXFBCONFIG, value); }
    /** Unsafe version of glxDrawable}. */
    public static long nglxDrawable(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXDRAWABLE); }
    public static void nglxDrawable(long struct, long value) { memPutLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXDRAWABLE, value); }
    /** Unsafe version of glxContext}. */
    public static long nglxContext(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXCONTEXT); }
    public static void nglxContext(long struct, long value ) { memPutLong(struct + XrGraphicsBindingOpenGLXlibKHR.GLXCONTEXT, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrGraphicsBindingOpenGLXlibKHRs */
    public static class PointerBuffer extends TypedPointerBufferView<XrGraphicsBindingOpenGLXlibKHR> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrGraphicsBindingOpenGLXlibKHR::create);
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
    /** An array of {@link XrGraphicsBindingOpenGLXlibKHR} structs. */
    public static class Buffer extends StructBuffer<XrGraphicsBindingOpenGLXlibKHR, Buffer> {

        private static final Function<Long,XrGraphicsBindingOpenGLXlibKHR> ELEMENT_FACTORY = address ->XrGraphicsBindingOpenGLXlibKHR.create(address);

        /**
         * Creates a new {@code XrGraphicsBindingOpenGLXlibKHR.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrGraphicsBindingOpenGLXlibKHR#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrGraphicsBindingOpenGLXlibKHR> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
