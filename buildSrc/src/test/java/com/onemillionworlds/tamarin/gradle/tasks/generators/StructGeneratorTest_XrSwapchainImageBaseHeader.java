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
 * XrSwapchainImageBaseHeader is interesting because it is a base structure type that other types extend.
 * As such, it should not have a type$Default() method.
 */
public class StructGeneratorTest_XrSwapchainImageBaseHeader {

    public static final String expected = """
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
             * Structure specifying swapchain image base header.
             *\s
             * <h3>Layout</h3>
             *\s
             * <pre><code>
             * struct XrSwapchainImageBaseHeader {
             *     XrStructureType type;
             *     void* next;
             * }</code></pre>
             * @noinspection unused
             */
            public class XrSwapchainImageBaseHeader extends Struct<XrSwapchainImageBaseHeader> {
            
                /** The struct size in bytes. */
                public static final int SIZEOF;
            
                /** The struct alignment in bytes. */
                public static final int ALIGNOF;
            
                /** The struct member offsets. */
                public static final int
                    TYPE,
                    NEXT;
            
                static {
                    Layout layout = Layout.__struct(
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE)
                    );
            
                    SIZEOF = layout.getSize();
                    ALIGNOF = layout.getAlignment();
            
                    TYPE = layout.offsetof(0);
                    NEXT = layout.offsetof(1);
                }
            
                protected XrSwapchainImageBaseHeader(long address, ByteBuffer container) {
                    super(address, container);
                }
            
                @Override
                protected XrSwapchainImageBaseHeader create(long address, ByteBuffer container) {
                    return new XrSwapchainImageBaseHeader(address, container);
                }
            
                /**
                 * Creates a {@code XrSwapchainImageBaseHeader} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
                 * visible to the struct instance and vice versa.
                 *
                 * <p>The created instance holds a strong reference to the container object.</p>
                 */
                public XrSwapchainImageBaseHeader(ByteBuffer container) {
                    super(memAddress(container), __checkContainer(container, SIZEOF));
                }
            
                @Override
                public int sizeof() { return SIZEOF; }
            
                /** Returns the value of the {@code type} field. */
                public XrStructureType type() {
                    return XrStructureType.fromValue(XrSwapchainImageBaseHeader.ntype(address()));
                }
                /** Returns the value of the {@code next} field. */
                public long next() {
                    return nnext(address());
                }
            
                /** Sets the specified value to the {@code type} field. */
                public XrSwapchainImageBaseHeader type(XrStructureType value) {\s
                    XrSwapchainImageBaseHeader.ntype(address(), value.getValue());
                    return this;
                }
                /** Sets the specified value to the {@code next} field. */
                public XrSwapchainImageBaseHeader next(long value) {\s
                    XrSwapchainImageBaseHeader.nnext(address(), value);
                    return this;
                }
            
                /** Initializes this struct with the specified values. */
                public XrSwapchainImageBaseHeader set(
                    XrStructureType type,
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
                public XrSwapchainImageBaseHeader set(XrSwapchainImageBaseHeader src) {
                    memCopy(src.address(), address(), SIZEOF);
                    return this;
                }
            
                // -----------------------------------
            
                /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
                public static XrSwapchainImageBaseHeader malloc() {
                    return new XrSwapchainImageBaseHeader(nmemAllocChecked(SIZEOF), null);
                }
            
                /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
                public static XrSwapchainImageBaseHeader calloc() {
                    return new XrSwapchainImageBaseHeader(nmemCallocChecked(1, SIZEOF), null);
                }
            
                /** Returns a new {@code XrSwapchainImageBaseHeader} instance allocated with {@link BufferUtils}. */
                public static XrSwapchainImageBaseHeader create() {
                    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
                    return new XrSwapchainImageBaseHeader(memAddress(container), container);
                }
            
                /** Returns a new {@code XrSwapchainImageBaseHeader} instance for the specified memory address. */
                public static XrSwapchainImageBaseHeader create(long address) {
                    return new XrSwapchainImageBaseHeader(address, null);
                }
            
                /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
                public static XrSwapchainImageBaseHeader createSafe(long address) {
                    return address == 0 ? null : new XrSwapchainImageBaseHeader(address, null);
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
                 * Returns a new {@code XrSwapchainImageBaseHeader} instance allocated on the specified {@link MemoryStack}.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrSwapchainImageBaseHeader malloc(MemoryStack stack) {
                    return new XrSwapchainImageBaseHeader(stack.nmalloc(ALIGNOF, SIZEOF), null);
                }
            
                /**
                 * Returns a new {@code XrSwapchainImageBaseHeader} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrSwapchainImageBaseHeader calloc(MemoryStack stack) {
                    return new XrSwapchainImageBaseHeader(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
                public static int ntype(long struct) { return memGetInt(struct + XrSwapchainImageBaseHeader.TYPE); }
                public static void ntype(long struct, int value ) { memPutInt(struct + XrSwapchainImageBaseHeader.TYPE, value); }
                /** Unsafe version of next}. */
                public static long nnext(long struct) { return memGetAddress(struct + XrSwapchainImageBaseHeader.NEXT); }
                public static void nnext(long struct, long value) { memPutAddress(struct + XrSwapchainImageBaseHeader.NEXT, value); }
            
            
                // -----------------------------------
            
                /** An array of {@link XrSwapchainImageBaseHeader} structs. */
                public static class Buffer extends StructBuffer<XrSwapchainImageBaseHeader, Buffer> {
            
                    private static final XrSwapchainImageBaseHeader ELEMENT_FACTORY = XrSwapchainImageBaseHeader.create(-1L);
            
                    /**
                     * Creates a new {@code XrSwapchainImageBaseHeader.Buffer} instance backed by the specified container.
                     *
                     * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
                     * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
                     * by {@link XrSwapchainImageBaseHeader#SIZEOF}, and its mark will be undefined.</p>
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
                    public XrSwapchainImageBaseHeader get(int index) {
                        return XrSwapchainImageBaseHeader.create(address + index * SIZEOF);
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
                    protected XrSwapchainImageBaseHeader getElementFactory() {
                        return ELEMENT_FACTORY;
                    }
            
                    /** Returns the value of the {@code type} field. */
                    public XrStructureType type() { return XrStructureType.fromValue(XrSwapchainImageBaseHeader.ntype(address())); }
                    /** Returns the value of the {@code next} field. */
                    public long next() { return XrSwapchainImageBaseHeader.nnext(address()); }
            
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type(XrStructureType value) {\s
                        XrSwapchainImageBaseHeader.ntype(address(), value.getValue());
                        return this;
                    }
                    /** Sets the specified value to the {@code next} field. */
                    public Buffer next(long value) {\s
                        XrSwapchainImageBaseHeader.nnext(address(), value);
                        return this;
                    }
                }
            }
            
            """;

    private static final String xrSwapchainImageBaseHeader_cDefiniton = """
            typedef struct XR_MAY_ALIAS XrSwapchainImageBaseHeader {
                XrStructureType       type;
                void* XR_MAY_ALIAS    next;
            } XrSwapchainImageBaseHeader;
            """;

    @Test
    void testGenerateXrSwapchainImageBaseHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(xrSwapchainImageBaseHeader_cDefiniton));
        StructDefinition structDefinition = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS, XR_STRUCTURE_TYPE_ENUM_VALUES);

        String result = StructGenerator.generateStruct(structDefinition);

        assertEquals(expected.trim(), result.trim());
    }

}
