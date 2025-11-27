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
 * Structure specifying hand capsule f b.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrHandCapsuleFB {
 *     XrVector3f points[XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB];
 *     float radius;
 *     XrHandJointEXT joint;
 * }</code></pre>
 * @noinspection unused
 */
public class XrHandCapsuleFB extends Struct<XrHandCapsuleFB> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** Runtime validation bit masks for field setters. */
    private static final Map<String, Integer> FIELD_BIT_MASKS;
    /** The struct member offsets. */
    public static final int
        POINTS,
        RADIUS,
        JOINT;

    static {
        Layout layout = Layout.__struct(
            Layout.__array(XrVector3f.SIZEOF, XrVector3f.ALIGNOF, XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB),
            Layout.__member(4),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        POINTS = layout.offsetof(0);
        RADIUS = layout.offsetof(1);
        JOINT = layout.offsetof(2);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("radius", "joint");
    }

    protected XrHandCapsuleFB(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrHandCapsuleFB", FIELD_BIT_MASKS);
    }

    @Override
    protected XrHandCapsuleFB create(long address, ByteBuffer container) {
        return new XrHandCapsuleFB(address, container);
    }

    /**
     * Creates a {@code XrHandCapsuleFB} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrHandCapsuleFB(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrHandCapsuleFB", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code points} field. */
    public XrVector3f.Buffer points() {
        return npoints(addressUnsafe());
    }
    /** Returns the value of the index-th item in the {@code points} field. Note to mutate the value get by index then mutate in place*/
    public XrVector3f points(int index) { return XrHandCapsuleFB.npoints(addressUnsafe(), index); }
    /** Returns the value of the {@code radius} field. */
    public float radius() {
        return nradius(addressUnsafe());
    }
    /** Returns the value of the {@code joint} field. */
    public XrHandJointEXT joint() {
        return XrHandJointEXT.fromValue(XrHandCapsuleFB.njoint(addressUnsafe()));
    }

    /** Sets the specified value to the {@code radius} field. */
    public XrHandCapsuleFB radius(float value) { 
        XrHandCapsuleFB.nradius(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("radius");
        return this;
    }
    /** Sets the specified value to the {@code joint} field. */
    public XrHandCapsuleFB joint(XrHandJointEXT value) { 
        XrHandCapsuleFB.njoint(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("joint");
        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrHandCapsuleFB set(XrHandCapsuleFB src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrHandCapsuleFB{");
        sb.append("points=");
        sb.append('[');
        for(int iArr=0;iArr<XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB;iArr++){
            if(iArr>0) sb.append(", ");
            sb.append(points(iArr));
        }
        sb.append(']');
        sb.append(", ");
        sb.append("radius=");
        sb.append(String.valueOf(radius()));
        sb.append(", ");
        sb.append("joint=");
        sb.append(String.valueOf(joint()));
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

    /** Returns a new {@code XrHandCapsuleFB} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrHandCapsuleFB malloc() {
        XrHandCapsuleFB instance = new XrHandCapsuleFB(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrHandCapsuleFB} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrHandCapsuleFB calloc() {
        return new XrHandCapsuleFB(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrHandCapsuleFB} instance allocated with {@link BufferUtils}. */
    public static XrHandCapsuleFB create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrHandCapsuleFB(memAddress(container), container);
    }

    /** Returns a new {@code XrHandCapsuleFB} instance for the specified memory address. */
    public static XrHandCapsuleFB create(long address) {
        return new XrHandCapsuleFB(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrHandCapsuleFB createSafe(long address) {
        return address == 0 ? null : new XrHandCapsuleFB(address, null);
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
     * Returns a new {@code XrHandCapsuleFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHandCapsuleFB malloc(MemoryStack stack) {
        XrHandCapsuleFB instance = new XrHandCapsuleFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrHandCapsuleFB} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHandCapsuleFB calloc(MemoryStack stack) {
        return new XrHandCapsuleFB(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of points}. */
    public static XrVector3f.Buffer npoints(long struct) { return XrVector3f.create(struct + XrHandCapsuleFB.POINTS, XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB); }
    public static XrVector3f npoints(long struct, int index) {
        if(index > XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB) throw new IllegalArgumentException("index out of bounds: " + index);
        return XrVector3f.create(struct + XrHandCapsuleFB.POINTS + (index * XrVector3f.SIZEOF));
    }
    /** Unsafe version of radius}. */
    public static float nradius(long struct) { return memGetFloat(struct + XrHandCapsuleFB.RADIUS); }
    public static void nradius(long struct, float value) { memPutFloat(struct + XrHandCapsuleFB.RADIUS, value); }
    /** Unsafe version of joint}. */
    public static int njoint(long struct) { return memGetInt(struct + XrHandCapsuleFB.JOINT); }
    public static void njoint(long struct, int value ) { memPutInt(struct + XrHandCapsuleFB.JOINT, value); }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrHandCapsuleFBs */
    public static class PointerBuffer extends TypedPointerBufferView<XrHandCapsuleFB> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrHandCapsuleFB::create);
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
    /** An array of {@link XrHandCapsuleFB} structs. */
    public static class Buffer extends StructBuffer<XrHandCapsuleFB, Buffer> {

        private static final Function<Long,XrHandCapsuleFB> ELEMENT_FACTORY = address ->XrHandCapsuleFB.create(address);

        /**
         * Creates a new {@code XrHandCapsuleFB.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrHandCapsuleFB#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrHandCapsuleFB> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
