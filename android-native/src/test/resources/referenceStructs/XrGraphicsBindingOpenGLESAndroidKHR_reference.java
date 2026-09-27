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
 * Structure specifying graphics binding open g l e s android k h r.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrGraphicsBindingOpenGLESAndroidKHR {
 *     XrStructureType type;
 *     const void* next;
 *     EGLDisplay display;
 *     EGLConfig config;
 *     EGLContext context;
 * }</code></pre>
 * @noinspection unused
 */
public class XrGraphicsBindingOpenGLESAndroidKHR extends Struct<XrGraphicsBindingOpenGLESAndroidKHR> {

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
        DISPLAY,
        CONFIG,
        CONTEXT;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(8),
            Layout.__member(8)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        DISPLAY = layout.offsetof(2);
        CONFIG = layout.offsetof(3);
        CONTEXT = layout.offsetof(4);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "display", "config", "context");
    }

    protected XrGraphicsBindingOpenGLESAndroidKHR(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrGraphicsBindingOpenGLESAndroidKHR", FIELD_BIT_MASKS);
    }

    @Override
    protected XrGraphicsBindingOpenGLESAndroidKHR create(long address, ByteBuffer container) {
        return new XrGraphicsBindingOpenGLESAndroidKHR(address, container);
    }

    /**
     * Creates a {@code XrGraphicsBindingOpenGLESAndroidKHR} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrGraphicsBindingOpenGLESAndroidKHR(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrGraphicsBindingOpenGLESAndroidKHR", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrGraphicsBindingOpenGLESAndroidKHR.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code display} field. */
    public EGLDisplay display() {
        return new EGLDisplay(XrGraphicsBindingOpenGLESAndroidKHR.ndisplay(addressUnsafe()));
    }
    /** Returns the value of the {@code config} field. */
    public EGLConfig config() {
        return new EGLConfig(XrGraphicsBindingOpenGLESAndroidKHR.nconfig(addressUnsafe()));
    }
    /** Returns the value of the {@code context} field. */
    public EGLContext context() {
        return new EGLContext(XrGraphicsBindingOpenGLESAndroidKHR.ncontext(addressUnsafe()));
    }

    /** Sets the specified value to the {@code type} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR type(XrStructureType value) { 
        XrGraphicsBindingOpenGLESAndroidKHR.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR next(long value) { 
        XrGraphicsBindingOpenGLESAndroidKHR.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code display} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR display(EGLDisplay value) { 
        XrGraphicsBindingOpenGLESAndroidKHR.ndisplay(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("display");
        return this;
    }
    /** Sets the specified value to the {@code config} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR config(EGLConfig value) { 
        XrGraphicsBindingOpenGLESAndroidKHR.nconfig(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("config");
        return this;
    }
    /** Sets the specified value to the {@code context} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR context(EGLContext value) { 
        XrGraphicsBindingOpenGLESAndroidKHR.ncontext(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("context");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrGraphicsBindingOpenGLESAndroidKHR type$Default() { return type(XrStructureType.XR_TYPE_GRAPHICS_BINDING_OPENGL_ES_ANDROID_KHR); }

    /** Initializes this struct with the specified values. */
    public XrGraphicsBindingOpenGLESAndroidKHR set(
        XrStructureType type,
        long next,
        EGLDisplay display,
        EGLConfig config,
        EGLContext context
    ) {
        type(type);
        next(next);
        display(display);
        config(config);
        context(context);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrGraphicsBindingOpenGLESAndroidKHR set(XrGraphicsBindingOpenGLESAndroidKHR src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrGraphicsBindingOpenGLESAndroidKHR{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("display=");
        sb.append(String.valueOf(display()));
        sb.append(", ");
        sb.append("config=");
        sb.append(String.valueOf(config()));
        sb.append(", ");
        sb.append("context=");
        sb.append(String.valueOf(context()));
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

    /** Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrGraphicsBindingOpenGLESAndroidKHR malloc() {
        XrGraphicsBindingOpenGLESAndroidKHR instance = new XrGraphicsBindingOpenGLESAndroidKHR(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrGraphicsBindingOpenGLESAndroidKHR calloc() {
        return new XrGraphicsBindingOpenGLESAndroidKHR(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance allocated with {@link BufferUtils}. */
    public static XrGraphicsBindingOpenGLESAndroidKHR create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrGraphicsBindingOpenGLESAndroidKHR(memAddress(container), container);
    }

    /** Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance for the specified memory address. */
    public static XrGraphicsBindingOpenGLESAndroidKHR create(long address) {
        return new XrGraphicsBindingOpenGLESAndroidKHR(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrGraphicsBindingOpenGLESAndroidKHR createSafe(long address) {
        return address == 0 ? null : new XrGraphicsBindingOpenGLESAndroidKHR(address, null);
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
     * Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrGraphicsBindingOpenGLESAndroidKHR malloc(MemoryStack stack) {
        XrGraphicsBindingOpenGLESAndroidKHR instance = new XrGraphicsBindingOpenGLESAndroidKHR(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrGraphicsBindingOpenGLESAndroidKHR} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrGraphicsBindingOpenGLESAndroidKHR calloc(MemoryStack stack) {
        return new XrGraphicsBindingOpenGLESAndroidKHR(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrGraphicsBindingOpenGLESAndroidKHR.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrGraphicsBindingOpenGLESAndroidKHR.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrGraphicsBindingOpenGLESAndroidKHR.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrGraphicsBindingOpenGLESAndroidKHR.NEXT, value); }
    /** Unsafe version of display}. */
    public static long ndisplay(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.DISPLAY); }
    public static void ndisplay(long struct, long value ) { memPutLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.DISPLAY, value); }
    /** Unsafe version of config}. */
    public static long nconfig(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.CONFIG); }
    public static void nconfig(long struct, long value ) { memPutLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.CONFIG, value); }
    /** Unsafe version of context}. */
    public static long ncontext(long struct) { return memGetLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.CONTEXT); }
    public static void ncontext(long struct, long value ) { memPutLong(struct + XrGraphicsBindingOpenGLESAndroidKHR.CONTEXT, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrGraphicsBindingOpenGLESAndroidKHRs */
    public static class PointerBuffer extends TypedPointerBufferView<XrGraphicsBindingOpenGLESAndroidKHR> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrGraphicsBindingOpenGLESAndroidKHR::create);
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
    /** An array of {@link XrGraphicsBindingOpenGLESAndroidKHR} structs. */
    public static class Buffer extends StructBuffer<XrGraphicsBindingOpenGLESAndroidKHR, Buffer> {

        private static final Function<Long,XrGraphicsBindingOpenGLESAndroidKHR> ELEMENT_FACTORY = address ->XrGraphicsBindingOpenGLESAndroidKHR.create(address);

        /**
         * Creates a new {@code XrGraphicsBindingOpenGLESAndroidKHR.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrGraphicsBindingOpenGLESAndroidKHR#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrGraphicsBindingOpenGLESAndroidKHR> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
