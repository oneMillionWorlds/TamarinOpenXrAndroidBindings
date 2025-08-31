/*
 * OpenXR Java bindings for Android
 * This file is auto-generated. DO NOT EDIT.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.enums.*;
import com.onemillionworlds.tamarin.openxrbindings.handles.*;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.nio.ByteBuffer;

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

    /** The struct member offsets. */
    public static final int
        POINTS,
        RADIUS,
        JOINT;

    static {
        Layout layout = Layout.__struct(
            Layout.__array(1, XR_HAND_TRACKING_CAPSULE_POINT_COUNT_FB),
            Layout.__member(4),
            Layout.__member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        POINTS = layout.offsetof(0);
        RADIUS = layout.offsetof(1);
        JOINT = layout.offsetof(2);
    }

    protected XrHandCapsuleFB(long address, ByteBuffer container) {
        super(address, container);
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
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code points} field. */
    public XrVector3f.Buffer points() {
        return npoints(address());
    }
    /** Returns the value of the index-th item in the {@code points} field. Note to mutate the value get by index then mutate in place*/
    public XrVector3f points(int index) { return XrHandCapsuleFB.npoints(address(), index); }
    /** Returns the value of the {@code radius} field. */
    public float radius() {
        return nradius(address());
    }
    /** Returns the value of the {@code joint} field. */
    public XrHandJointEXT joint() {
        return XrHandJointEXT.fromValue(XrHandCapsuleFB.njoint(address()));
    }

    /** Sets the specified value to the {@code radius} field. */
    public XrHandCapsuleFB radius(float value) { 
        XrHandCapsuleFB.nradius(address(), value);
        return this;
    }
    /** Sets the specified value to the {@code joint} field. */
    public XrHandCapsuleFB joint(XrHandJointEXT value) { 
        XrHandCapsuleFB.njoint(address(), value.getValue());
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

    // -----------------------------------

    /** Returns a new {@code XrHandCapsuleFB} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrHandCapsuleFB malloc() {
        return new XrHandCapsuleFB(nmemAllocChecked(SIZEOF), null);
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
     * Returns a new {@code XrHandCapsuleFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHandCapsuleFB malloc(MemoryStack stack) {
        return new XrHandCapsuleFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
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

    /** An array of {@link XrHandCapsuleFB} structs. */
    public static class Buffer extends StructBuffer<XrHandCapsuleFB, Buffer> {

        private static final XrHandCapsuleFB ELEMENT_FACTORY = XrHandCapsuleFB.create(-1L);

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
            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        @Override
        public XrHandCapsuleFB get(int index) {
            return XrHandCapsuleFB.create(address + index * SIZEOF);
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
        protected XrHandCapsuleFB getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code points} field. */
        public XrVector3f.Buffer points() { return XrHandCapsuleFB.npoints(address()); }
        /** Returns the value of the index-th item in the {@code points} field. Note to mutate the value get by index then mutate in place*/
        public XrVector3f points(int index) { return XrHandCapsuleFB.npoints(address(), index); }
        /** Returns the value of the {@code radius} field. */
        public float radius() { return XrHandCapsuleFB.nradius(address()); }
        /** Returns the value of the {@code joint} field. */
        public XrHandJointEXT joint() { return XrHandJointEXT.fromValue(XrHandCapsuleFB.njoint(address())); }

        /** Sets the specified value to the {@code radius} field. */
        public Buffer radius(float value) { 
            XrHandCapsuleFB.nradius(address(), value);
            return this;
        }
        /** Sets the specified value to the {@code joint} field. */
        public Buffer joint(XrHandJointEXT value) { 
            XrHandCapsuleFB.njoint(address(), value.getValue());
            return this;
        }
    }
}
