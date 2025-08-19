package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.StructParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_ATOMS;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_ENUM_TYPES;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_FLAGS;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_HANDLES;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_STRUCTS;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_TYPEDEF_INTS;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.KNOWN_TYPEDEF_LONGS;
import static com.onemillionworlds.tamarin.gradle.tasks.generators.CommonData.XR_STRUCTURE_TYPE_ENUM_VALUES;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * XrInteractionProfileDpadBindingEXT is interesting onHaptic is a singular pointer
 * (with a base class XrHapticBaseHeader but it might be another type)
 */
public class StructGeneratorTest_XrInteractionProfileDpadBindingEXT {
    private static final String expectedXrInteractionProfileDpadBindingEXT = """
            /*
             * OpenXR Java bindings for Android
             * This file is auto-generated. DO NOT EDIT.
             */
            package com.onemillionworlds.tamarin.openxrbindings;
            
            import com.onemillionworlds.tamarin.openxrbindings.enums.*;
            import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
            import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;
            
            import java.nio.ByteBuffer;
            
            import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
            import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;
            import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;
            
            /**
             * Structure specifying interaction profile dpad binding e x t.
             *\s
             * <h3>Layout</h3>
             *\s
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
                }
            
                protected XrInteractionProfileDpadBindingEXT(long address, ByteBuffer container) {
                    super(address, container);
                }
            
                @Override
                protected XrInteractionProfileDpadBindingEXT create(long address, ByteBuffer container) {
                    return new XrInteractionProfileDpadBindingEXT(address, container);
                }
            
                /**
                 * Creates a {@code XrInteractionProfileDpadBindingEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
                 * visible to the struct instance and vice versa.
                 *
                 * <p>The created instance holds a strong reference to the container object.</p>
                 */
                public XrInteractionProfileDpadBindingEXT(ByteBuffer container) {
                    super(memAddress(container), __checkContainer(container, SIZEOF));
                }
            
                @Override
                public int sizeof() { return SIZEOF; }
            
                /** Returns the value of the {@code type} field. */
                public XrStructureType type() { return XrStructureType.fromValue(memGetInt(address() + TYPE)); }
                /** Returns the value of the {@code next} field. */
                public long next() { return memGetAddress(address() + NEXT); }
                /** Returns the value of the {@code binding} field. */
                public long binding() { return memGetLong(address() + BINDING); }
                /** Returns the value of the {@code actionSet} field. */
                public long actionSet() { return memGetLong(address() + ACTIONSET); }
                /** Returns the value of the {@code forceThreshold} field. */
                public float forceThreshold() { return memGetFloat(address() + FORCETHRESHOLD); }
                /** Returns the value of the {@code forceThresholdReleased} field. */
                public float forceThresholdReleased() { return memGetFloat(address() + FORCETHRESHOLDRELEASED); }
                /** Returns the value of the {@code centerRegion} field. */
                public float centerRegion() { return memGetFloat(address() + CENTERREGION); }
                /** Returns the value of the {@code wedgeAngle} field. */
                public float wedgeAngle() { return memGetFloat(address() + WEDGEANGLE); }
                /** Returns the value of the {@code isSticky} field. */
                public int isSticky() { return memGetInt(address() + ISSTICKY); }
                /** Returns the value of the {@code onHaptic} field. */
                public XrHapticBaseHeader onHaptic() {
                    return nonHaptic(address());
                }
                /** Returns the value of the {@code offHaptic} field. */
                public XrHapticBaseHeader offHaptic() {
                    return noffHaptic(address());
                }
            
                /** Sets the specified value to the {@code type} field. */
                public XrInteractionProfileDpadBindingEXT type(XrStructureType value) {\s
                    memPutInt(address() + TYPE, value.getValue());
                    return this;
                }
                /** Sets the specified value to the {@code next} field. */
                public XrInteractionProfileDpadBindingEXT next(long value) {\s
                    memPutAddress(address() + NEXT, value);
                    return this;
                }
                /** Sets the specified value to the {@code binding} field. */
                public XrInteractionProfileDpadBindingEXT binding(long value) {\s
                    memPutLong(address() + BINDING, value);
                    return this;
                }
                /** Sets the specified value to the {@code actionSet} field. */
                public XrInteractionProfileDpadBindingEXT actionSet(long value) {\s
                    memPutLong(address() + ACTIONSET, value);
                    return this;
                }
                /** Sets the specified value to the {@code forceThreshold} field. */
                public XrInteractionProfileDpadBindingEXT forceThreshold(float value) {\s
                    memPutFloat(address() + FORCETHRESHOLD, value);
                    return this;
                }
                /** Sets the specified value to the {@code forceThresholdReleased} field. */
                public XrInteractionProfileDpadBindingEXT forceThresholdReleased(float value) {\s
                    memPutFloat(address() + FORCETHRESHOLDRELEASED, value);
                    return this;
                }
                /** Sets the specified value to the {@code centerRegion} field. */
                public XrInteractionProfileDpadBindingEXT centerRegion(float value) {\s
                    memPutFloat(address() + CENTERREGION, value);
                    return this;
                }
                /** Sets the specified value to the {@code wedgeAngle} field. */
                public XrInteractionProfileDpadBindingEXT wedgeAngle(float value) {\s
                    memPutFloat(address() + WEDGEANGLE, value);
                    return this;
                }
                /** Sets the specified value to the {@code isSticky} field. */
                public XrInteractionProfileDpadBindingEXT isSticky(int value) {\s
                    memPutInt(address() + ISSTICKY, value);
                    return this;
                }
                /** Sets the specified value to the {@code onHaptic} field. */
                public XrInteractionProfileDpadBindingEXT onHaptic(XrHapticBaseHeader value) {\s
                    nonHaptic(address(), value);
                    return this;
                }
                /** Sets the specified value to the {@code offHaptic} field. */
                public XrInteractionProfileDpadBindingEXT offHaptic(XrHapticBaseHeader value) {\s
                    noffHaptic(address(), value);
                    return this;
                }
                /** Sets the specified value to the {@code type} field. */
                public XrInteractionProfileDpadBindingEXT type$Default() { return type(XrStructureType.XR_TYPE_INTERACTION_PROFILE_DPAD_BINDING_EXT); }
            
                /** Initializes this struct with the specified values. */
                public XrInteractionProfileDpadBindingEXT set(
                    XrStructureType type,
                    long next,
                    long binding,
                    long actionSet,
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
            
                // -----------------------------------
            
                /** Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
                public static XrInteractionProfileDpadBindingEXT malloc() {
                    return new XrInteractionProfileDpadBindingEXT(nmemAllocChecked(SIZEOF), null);
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
                 * Returns a new {@code XrInteractionProfileDpadBindingEXT} instance allocated on the specified {@link MemoryStack}.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrInteractionProfileDpadBindingEXT malloc(MemoryStack stack) {
                    return new XrInteractionProfileDpadBindingEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
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
                public static int ntype(long struct) { return memGetInt(struct + XrInteractionProfileDpadBindingEXT.TYPE); }
                public static void ntype(long struct, int value ) { memPutInt(struct + XrInteractionProfileDpadBindingEXT.TYPE, value); }
                /** Unsafe version of {@link #next}. */
                public static long nnext(long struct) { return memGetAddress(struct + XrInteractionProfileDpadBindingEXT.NEXT); }
                public static void nnext(long struct, long value) { memPutAddress(struct + XrInteractionProfileDpadBindingEXT.NEXT, value); }
                /** Unsafe version of {@link #binding}. */
                public static long nbinding(long struct) { return memGetLong(struct + XrInteractionProfileDpadBindingEXT.BINDING); }
                public static void nbinding(long struct, long value) { memPutLong(struct + XrInteractionProfileDpadBindingEXT.BINDING, value); }
                /** Unsafe version of {@link #actionSet}. */
                public static long nactionSet(long struct) { return memGetLong(struct + XrInteractionProfileDpadBindingEXT.ACTIONSET); }
                public static void nactionSet(long struct, long value) { memPutLong(struct + XrInteractionProfileDpadBindingEXT.ACTIONSET, value); }
                /** Unsafe version of {@link #forceThreshold}. */
                public static float nforceThreshold(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLD); }
                public static void nforceThreshold(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLD, value); }
                /** Unsafe version of {@link #forceThresholdReleased}. */
                public static float nforceThresholdReleased(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLDRELEASED); }
                public static void nforceThresholdReleased(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.FORCETHRESHOLDRELEASED, value); }
                /** Unsafe version of {@link #centerRegion}. */
                public static float ncenterRegion(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.CENTERREGION); }
                public static void ncenterRegion(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.CENTERREGION, value); }
                /** Unsafe version of {@link #wedgeAngle}. */
                public static float nwedgeAngle(long struct) { return memGetFloat(struct + XrInteractionProfileDpadBindingEXT.WEDGEANGLE); }
                public static void nwedgeAngle(long struct, float value) { memPutFloat(struct + XrInteractionProfileDpadBindingEXT.WEDGEANGLE, value); }
                /** Unsafe version of {@link #isSticky}. */
                public static int nisSticky(long struct) { return memGetInt(struct + XrInteractionProfileDpadBindingEXT.ISSTICKY); }
                public static void nisSticky(long struct, int value) { memPutInt(struct + XrInteractionProfileDpadBindingEXT.ISSTICKY, value); }
                /** Unsafe version of {@link #onHaptic}. */
                public static XrHapticBaseHeader nonHaptic(long struct) {
                    return XrHapticBaseHeader.createSafe(memGetAddress(struct + XrInteractionProfileDpadBindingEXT.ONHAPTIC));
                }
                public static void nonHaptic(long struct, XrHapticBaseHeader value){
                    long address = value == null ? NULL : value.address();
                    memPutAddress(struct + ONHAPTIC, address);
                }
                /** Unsafe version of {@link #offHaptic}. */
                public static XrHapticBaseHeader noffHaptic(long struct) {
                    return XrHapticBaseHeader.createSafe(memGetAddress(struct + XrInteractionProfileDpadBindingEXT.OFFHAPTIC));
                }
                public static void noffHaptic(long struct, XrHapticBaseHeader value){
                    long address = value == null ? NULL : value.address();
                    memPutAddress(struct + OFFHAPTIC, address);
                }
            
            
                // -----------------------------------
            
                /** An array of {@link XrInteractionProfileDpadBindingEXT} structs. */
                public static class Buffer extends StructBuffer<XrInteractionProfileDpadBindingEXT, Buffer> {
            
                    private static final XrInteractionProfileDpadBindingEXT ELEMENT_FACTORY = XrInteractionProfileDpadBindingEXT.create(-1L);
            
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
                        super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);
                    }
            
                    public Buffer(long address, int cap) {
                        super(address, null, -1, 0, cap, cap);
                    }
            
                    @Override
                    public XrInteractionProfileDpadBindingEXT get(int index) {
                        return XrInteractionProfileDpadBindingEXT.create(address + index * SIZEOF);
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
                    protected XrInteractionProfileDpadBindingEXT getElementFactory() {
                        return ELEMENT_FACTORY;
                    }
            
                    /** Returns the value of the {@code type} field. */
                    public XrStructureType type() { return XrStructureType.fromValue(XrInteractionProfileDpadBindingEXT.ntype(address())); }
                    /** Returns the value of the {@code next} field. */
                    public long next() { return XrInteractionProfileDpadBindingEXT.nnext(address()); }
                    /** Returns the value of the {@code binding} field. */
                    public long binding() { return XrInteractionProfileDpadBindingEXT.nbinding(address()); }
                    /** Returns the value of the {@code actionSet} field. */
                    public long actionSet() { return XrInteractionProfileDpadBindingEXT.nactionSet(address()); }
                    /** Returns the value of the {@code forceThreshold} field. */
                    public float forceThreshold() { return XrInteractionProfileDpadBindingEXT.nforceThreshold(address()); }
                    /** Returns the value of the {@code forceThresholdReleased} field. */
                    public float forceThresholdReleased() { return XrInteractionProfileDpadBindingEXT.nforceThresholdReleased(address()); }
                    /** Returns the value of the {@code centerRegion} field. */
                    public float centerRegion() { return XrInteractionProfileDpadBindingEXT.ncenterRegion(address()); }
                    /** Returns the value of the {@code wedgeAngle} field. */
                    public float wedgeAngle() { return XrInteractionProfileDpadBindingEXT.nwedgeAngle(address()); }
                    /** Returns the value of the {@code isSticky} field. */
                    public int isSticky() { return XrInteractionProfileDpadBindingEXT.nisSticky(address()); }
                    /** Returns the value of the {@code onHaptic} field. */
                    public XrHapticBaseHeader onHaptic() { return XrInteractionProfileDpadBindingEXT.nonHaptic(address()); }
                    /** Returns the value of the {@code offHaptic} field. */
                    public XrHapticBaseHeader offHaptic() { return XrInteractionProfileDpadBindingEXT.noffHaptic(address()); }
            
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type(XrStructureType value) {\s
                        XrInteractionProfileDpadBindingEXT.ntype(address(), value.getValue());
                        return this;
                    }
                    /** Sets the specified value to the {@code next} field. */
                    public Buffer next(long value) {\s
                        XrInteractionProfileDpadBindingEXT.nnext(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code binding} field. */
                    public Buffer binding(long value) {\s
                        XrInteractionProfileDpadBindingEXT.nbinding(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code actionSet} field. */
                    public Buffer actionSet(long value) {\s
                        XrInteractionProfileDpadBindingEXT.nactionSet(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code forceThreshold} field. */
                    public Buffer forceThreshold(float value) {\s
                        XrInteractionProfileDpadBindingEXT.nforceThreshold(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code forceThresholdReleased} field. */
                    public Buffer forceThresholdReleased(float value) {\s
                        XrInteractionProfileDpadBindingEXT.nforceThresholdReleased(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code centerRegion} field. */
                    public Buffer centerRegion(float value) {\s
                        XrInteractionProfileDpadBindingEXT.ncenterRegion(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code wedgeAngle} field. */
                    public Buffer wedgeAngle(float value) {\s
                        XrInteractionProfileDpadBindingEXT.nwedgeAngle(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code isSticky} field. */
                    public Buffer isSticky(int value) {\s
                        XrInteractionProfileDpadBindingEXT.nisSticky(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code onHaptic} field. */
                    public Buffer onHaptic(XrHapticBaseHeader value) {\s
                        XrInteractionProfileDpadBindingEXT.nonHaptic(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code offHaptic} field. */
                    public Buffer offHaptic(XrHapticBaseHeader value) {\s
                        XrInteractionProfileDpadBindingEXT.noffHaptic(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type$Default() { return type(XrStructureType.XR_TYPE_INTERACTION_PROFILE_DPAD_BINDING_EXT); }
                }
            }
            
            """;
    private static final String xrInteractionProfileDpadBindingEXT_cDefinition = """
            typedef struct XrInteractionProfileDpadBindingEXT {
                XrStructureType              type;
                const void* XR_MAY_ALIAS     next;
                XrPath                       binding;
                XrActionSet                  actionSet;
                float                        forceThreshold;
                float                        forceThresholdReleased;
                float                        centerRegion;
                float                        wedgeAngle;
                XrBool32                     isSticky;
                const XrHapticBaseHeader*    onHaptic;
                const XrHapticBaseHeader*    offHaptic;
            } XrInteractionProfileDpadBindingEXT;
            """;

    @Test
    void testGenerateXrInteractionProfileDpadBindingEXT() throws IOException {
        // this cheats a bit and parses and generates all in one test
        BufferedReader reader = new BufferedReader(new StringReader(xrInteractionProfileDpadBindingEXT_cDefinition));
        StructDefinition structDefinition = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS, XR_STRUCTURE_TYPE_ENUM_VALUES);

        String result = StructGenerator.generateStruct(structDefinition);

        assertEquals(expectedXrInteractionProfileDpadBindingEXT.trim(), result.trim());
    }
}
