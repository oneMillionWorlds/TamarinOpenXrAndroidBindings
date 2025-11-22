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
 * Structure specifying interaction profile dpad binding e x t.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrInteractionProfileDpadBindingEXT {
 *     XrStructureType type;
 *     const void* next;
 *     XrPath binding;
 *     XrActionSet actionSet;
 *     float forceThreshold;
 *     float forceThresholdReleased;
 *     float centerRegion;
 *     float wedgeAngle;
 *     XrBool32 isSticky;
 *     const XrHapticBaseHeader* onHaptic;
 *     const XrHapticBaseHeader* offHaptic;
 * }</code></pre>
 * @noinspection unused
 */
public class XrInteractionProfileDpadBindingEXT extends Struct<XrInteractionProfileDpadBindingEXT> {

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
        BINDING,
        ACTIONSET,
        FORCETHRESHOLD,
        FORCETHRESHOLDRELEASED,
        CENTERREGION,
        WEDGEANGLE,
        ISSTICKY,
        ONHAPTIC,
        OFFHAPTIC;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(8),
            Layout.__member(8),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        BINDING = layout.offsetof(2);
        ACTIONSET = layout.offsetof(3);
        FORCETHRESHOLD = layout.offsetof(4);
        FORCETHRESHOLDRELEASED = layout.offsetof(5);
        CENTERREGION = layout.offsetof(6);
        WEDGEANGLE = layout.offsetof(7);
        ISSTICKY = layout.offsetof(8);
        ONHAPTIC = layout.offsetof(9);
        OFFHAPTIC = layout.offsetof(10);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "binding", "actionSet", "forceThreshold", "forceThresholdReleased", "centerRegion", "wedgeAngle", "isSticky", "onHaptic", "offHaptic");
    }

    protected XrInteractionProfileDpadBindingEXT(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrInteractionProfileDpadBindingEXT", FIELD_BIT_MASKS);
    }

    @Override
    protected XrInteractionProfileDpadBindingEXT create(long address, ByteBuffer container) {
        return new XrInteractionProfileDpadBindingEXT(address, container);
    }

    /** Get a view of a parent class as if it is this type. 
     * Note! It is the caller's responsibility to make sure it really is that type. To do that consult the type parameter
     */
    public static XrInteractionProfileDpadBindingEXT cast(XrBindingModificationBaseHeaderKHR from) {
        if(from.type() != XrStructureType.XR_TYPE_INTERACTION_PROFILE_DPAD_BINDING_EXT){
            throw new IllegalArgumentException("Wrong type passed to cast method. Expected: XR_TYPE_INTERACTION_PROFILE_DPAD_BINDING_EXT actual: "+from.type() );
        }

        return new XrInteractionProfileDpadBindingEXT(from.address(), from.container());
    }

    /**
     * Creates a {@code XrInteractionProfileDpadBindingEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrInteractionProfileDpadBindingEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrInteractionProfileDpadBindingEXT", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrInteractionProfileDpadBindingEXT.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code binding} field. */
    public long binding() {
        return nbinding(addressUnsafe());
    }
    /** Returns the value of the {@code actionSet} field. */
    public XrActionSet actionSet() {
        return new XrActionSet(XrInteractionProfileDpadBindingEXT.nactionSet(addressUnsafe()));
    }
    /** Returns the value of the {@code forceThreshold} field. */
    public float forceThreshold() {
        return nforceThreshold(addressUnsafe());
    }
    /** Returns the value of the {@code forceThresholdReleased} field. */
    public float forceThresholdReleased() {
        return nforceThresholdReleased(addressUnsafe());
    }
    /** Returns the value of the {@code centerRegion} field. */
    public float centerRegion() {
        return ncenterRegion(addressUnsafe());
    }
    /** Returns the value of the {@code wedgeAngle} field. */
    public float wedgeAngle() {
        return nwedgeAngle(addressUnsafe());
    }
    /** Returns the value of the {@code isSticky} field. */
    public int isSticky() {
        return nisSticky(addressUnsafe());
    }
    /** Returns the value of the {@code onHaptic} field. */
    public XrHapticBaseHeader onHaptic() {
        return nonHaptic(addressUnsafe());
    }
    /** Returns the value of the {@code offHaptic} field. */
    public XrHapticBaseHeader offHaptic() {
        return noffHaptic(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrInteractionProfileDpadBindingEXT type(XrStructureType value) { 
        XrInteractionProfileDpadBindingEXT.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrInteractionProfileDpadBindingEXT next(long value) { 
        XrInteractionProfileDpadBindingEXT.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code binding} field. */
    public XrInteractionProfileDpadBindingEXT binding(long value) { 
        XrInteractionProfileDpadBindingEXT.nbinding(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("binding");
        return this;
    }
    /** Sets the specified value to the {@code actionSet} field. */
    public XrInteractionProfileDpadBindingEXT actionSet(XrActionSet value) { 
        XrInteractionProfileDpadBindingEXT.nactionSet(addressUnsafe(), value.getRawHandle());
        this.setterValidation.setFieldCalled("actionSet");
        return this;
    }
    /** Sets the specified value to the {@code forceThreshold} field. */
    public XrInteractionProfileDpadBindingEXT forceThreshold(float value) { 
        XrInteractionProfileDpadBindingEXT.nforceThreshold(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("forceThreshold");
        return this;
    }
    /** Sets the specified value to the {@code forceThresholdReleased} field. */
    public XrInteractionProfileDpadBindingEXT forceThresholdReleased(float value) { 
        XrInteractionProfileDpadBindingEXT.nforceThresholdReleased(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("forceThresholdReleased");
        return this;
    }
    /** Sets the specified value to the {@code centerRegion} field. */
    public XrInteractionProfileDpadBindingEXT centerRegion(float value) { 
        XrInteractionProfileDpadBindingEXT.ncenterRegion(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("centerRegion");
        return this;
    }
    /** Sets the specified value to the {@code wedgeAngle} field. */
    public XrInteractionProfileDpadBindingEXT wedgeAngle(float value) { 
        XrInteractionProfileDpadBindingEXT.nwedgeAngle(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("wedgeAngle");
        return this;
    }
    /** Sets the specified value to the {@code isSticky} field. */
    public XrInteractionProfileDpadBindingEXT isSticky(int value) { 
        XrInteractionProfileDpadBindingEXT.nisSticky(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("isSticky");
        return this;
    }
    /** Sets the specified value to the {@code onHaptic} field. */
    public XrInteractionProfileDpadBindingEXT onHaptic(XrHapticBaseHeader value) { 
        XrInteractionProfileDpadBindingEXT.nonHaptic(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("onHaptic");
        return this;
    }
    /** Sets the specified value to the {@code offHaptic} field. */
    public XrInteractionProfileDpadBindingEXT offHaptic(XrHapticBaseHeader value) { 
        XrInteractionProfileDpadBindingEXT.noffHaptic(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("offHaptic");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrInteractionProfileDpadBindingEXT type$Default() { return type(XrStructureType.XR_TYPE_INTERACTION_PROFILE_DPAD_BINDING_EXT); }

    /** Initializes this struct with the specified values. */
    public XrInteractionProfileDpadBindingEXT set(
        XrStructureType type,
        long next,
        long binding,
        XrActionSet actionSet,
        float forceThreshold,
        float forceThresholdReleased,
        float centerRegion,
        float wedgeAngle,
        int isSticky,
        XrHapticBaseHeader onHaptic,
        XrHapticBaseHeader offHaptic
    ) {
        type(type);
        next(next);
        binding(binding);
        actionSet(actionSet);
        forceThreshold(forceThreshold);
        forceThresholdReleased(forceThresholdReleased);
        centerRegion(centerRegion);
        wedgeAngle(wedgeAngle);
        isSticky(isSticky);
        onHaptic(onHaptic);
        offHaptic(offHaptic);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrInteractionProfileDpadBindingEXT set(XrInteractionProfileDpadBindingEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrInteractionProfileDpadBindingEXT{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("binding=");
        sb.append(String.valueOf(binding()));
        sb.append(", ");
        sb.append("actionSet=");
        sb.append(String.valueOf(actionSet()));
        sb.append(", ");
        sb.append("forceThreshold=");
        sb.append(String.valueOf(forceThreshold()));
        sb.append(", ");
        sb.append("forceThresholdReleased=");
        sb.append(String.valueOf(forceThresholdReleased()));
        sb.append(", ");
        sb.append("centerRegion=");
        sb.append(String.valueOf(centerRegion()));
        sb.append(", ");
        sb.append("wedgeAngle=");
        sb.append(String.valueOf(wedgeAngle()));
        sb.append(", ");
        sb.append("isSticky=");
        sb.append(String.valueOf(isSticky()));
        sb.append(", ");
        sb.append("onHaptic=");
        sb.append(String.valueOf(onHaptic()));
        sb.append(", ");
        sb.append("offHaptic=");
        sb.append(String.valueOf(offHaptic()));
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

    /** Get a view of this struct as its parent (for use in methods that take the parent)*/
    public XrBindingModificationBaseHeaderKHR asParent() {
        return new XrBindingModificationBaseHeaderKHR(address(), container());
    }

    // -----------------------------------

    /** Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrInteractionProfileDpadBindingEXT malloc() {
        XrInteractionProfileDpadBindingEXT instance = new XrInteractionProfileDpadBindingEXT(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrInteractionProfileDpadBindingEXT calloc() {
        return new XrInteractionProfileDpadBindingEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated with {@link BufferUtils}. */
    public static XrInteractionProfileDpadBindingEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrInteractionProfileDpadBindingEXT(memAddress(container), container);
    }

    /** Returns a new {@code XrInteractionProfileDpadBindingEXT} instance for the specified memory address. */
    public static XrInteractionProfileDpadBindingEXT create(long address) {
        return new XrInteractionProfileDpadBindingEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrInteractionProfileDpadBindingEXT createSafe(long address) {
        return address == 0 ? null : new XrInteractionProfileDpadBindingEXT(address, null);
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
     * Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrInteractionProfileDpadBindingEXT malloc(MemoryStack stack) {
        XrInteractionProfileDpadBindingEXT instance = new XrInteractionProfileDpadBindingEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrInteractionProfileDpadBindingEXT calloc(MemoryStack stack) {
        return new XrInteractionProfileDpadBindingEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
    public static int ntype(long struct) { return memGetInt(struct + XrInteractionProfileDpadBindingEXT.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrInteractionProfileDpadBindingEXT.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrInteractionProfileDpadBindingEXT.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrInteractionProfileDpadBindingEXT.NEXT, value); }
    /** Unsafe version of binding}. */
    public static long nbinding(long struct) { return memGetLong(struct + XrInteractionProfileDpadBindingEXT.BINDING); }
    public static void nbinding(long struct, long value) { memPutLong(struct + XrInteractionProfileDpadBindingEXT.BINDING, value); }
    /** Unsafe version of actionSet}. */
    public static long nactionSet(long struct) { return memGetLong(struct + XrInteractionProfileDpadBindingEXT.ACTIONSET); }
    public static void nactionSet(long struct, long value ) { memPutLong(struct + XrInteractionProfileDpadBindingEXT.ACTIONSET, value); }
    /** Unsafe version of forceThreshold}. */
    public static float nforceThreshold(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLD); }
    public static void nforceThreshold(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLD, value); }
    /** Unsafe version of forceThresholdReleased}. */
    public static float nforceThresholdReleased(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLDRELEASED); }
    public static void nforceThresholdReleased(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLDRELEASED, value); }
    /** Unsafe version of centerRegion}. */
    public static float ncenterRegion(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.CENTERREGION); }
    public static void ncenterRegion(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.CENTERREGION, value); }
    /** Unsafe version of wedgeAngle}. */
    public static float nwedgeAngle(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.WEDGEANGLE); }
    public static void nwedgeAngle(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.WEDGEANGLE, value); }
    /** Unsafe version of isSticky}. */
    public static int nisSticky(long struct) { return memGetInt(struct + XrInteractionProfileDpadBindingEXT.ISSTICKY); }
    public static void nisSticky(long struct, int value) { memPutInt(struct + XrInteractionProfileDpadBindingEXT.ISSTICKY, value); }
    /** Unsafe version of onHaptic}. */
    public static XrHapticBaseHeader nonHaptic(long struct) {
        return XrHapticBaseHeader.createSafe(memGetAddress(struct + XrInteractionProfileDpadBindingEXT.ONHAPTIC));
    }
    public static void nonHaptic(long struct, XrHapticBaseHeader value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + ONHAPTIC, address);
    }
    /** Unsafe version of offHaptic}. */
    public static XrHapticBaseHeader noffHaptic(long struct) {
        return XrHapticBaseHeader.createSafe(memGetAddress(struct + XrInteractionProfileDpadBindingEXT.OFFHAPTIC));
    }
    public static void noffHaptic(long struct, XrHapticBaseHeader value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + OFFHAPTIC, address);
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrInteractionProfileDpadBindingEXTs */
    public static class PointerBuffer extends TypedPointerBufferView<XrInteractionProfileDpadBindingEXT> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrInteractionProfileDpadBindingEXT::create);
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
    /** An array of {@link XrInteractionProfileDpadBindingEXT} structs. */
    public static class Buffer extends StructBuffer<XrInteractionProfileDpadBindingEXT, Buffer> {

        private static final Function<Long,XrInteractionProfileDpadBindingEXT> ELEMENT_FACTORY = address ->XrInteractionProfileDpadBindingEXT.create(address);

        /**
         * Creates a new {@code XrInteractionProfileDpadBindingEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrInteractionProfileDpadBindingEXT#SIZEOF}, and its mark will be undefined.</p>
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
        protected Function<Long,XrInteractionProfileDpadBindingEXT> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
