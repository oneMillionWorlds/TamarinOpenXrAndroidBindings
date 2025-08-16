package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.StructParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * XrInstanceCreateInfo is interesting because it is a struct that itself contains structs
 */
public class StructGeneratorTest_XrInstanceCreateInfo {

    // Static constants for known types
    private static final List<String> KNOWN_ENUM_TYPES = List.of("XrStructureType");
    private static final List<String> KNOWN_ATOMS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_INTS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_LONGS = Collections.emptyList();
    private static final List<String> KNOWN_HANDLES = Collections.emptyList();
    private static final List<String> KNOWN_FLAGS = List.of("XrInstanceCreateFlags");
    private static final List<String> KNOWN_STRUCTS = List.of("XrApplicationInfo");

    private static final String expectedXrInstanceCreateInfo = """
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
            import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;

            /**
             * Structure specifying instance create info.
             * 
             * <h3>Layout</h3>
             * 
             * <pre><code>
             * struct XrInstanceCreateInfo {
             *     XrStructureType type;
             *     const void* next;
             *     XrInstanceCreateFlags createFlags;
             *     XrApplicationInfo applicationInfo;
             *     uint32_t enabledApiLayerCount;
             *     const * enabledApiLayerNames;
             *     uint32_t enabledExtensionCount;
             *     const * enabledExtensionNames;
             * }</code></pre>
             * @noinspection unused
             */
            public class XrInstanceCreateInfo extends Struct<XrInstanceCreateInfo> {

                /** The struct size in bytes. */
                public static final int SIZEOF;

                /** The struct alignment in bytes. */
                public static final int ALIGNOF;

                /** The struct member offsets. */
                public static final int
                    TYPE,
                    NEXT,
                    CREATEFLAGS,
                    APPLICATIONINFO,
                    ENABLEDAPILAYERCOUNT,
                    ENABLEDAPILAYERNAMES,
                    ENABLEDEXTENSIONCOUNT,
                    ENABLEDEXTENSIONNAMES;

                static {
                    Layout layout = Layout.__struct(
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE),
                        Layout.__member(8),
                        Layout.__member(344),
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE),
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE)
                    );

                    SIZEOF = layout.getSize();
                    ALIGNOF = layout.getAlignment();

                    TYPE = layout.offsetof(0);
                    NEXT = layout.offsetof(1);
                    CREATEFLAGS = layout.offsetof(2);
                    APPLICATIONINFO = layout.offsetof(3);
                    ENABLEDAPILAYERCOUNT = layout.offsetof(4);
                    ENABLEDAPILAYERNAMES = layout.offsetof(5);
                    ENABLEDEXTENSIONCOUNT = layout.offsetof(6);
                    ENABLEDEXTENSIONNAMES = layout.offsetof(7);
                }

                protected XrInstanceCreateInfo(long address, ByteBuffer container) {
                    super(address, container);
                }

                @Override
                protected XrInstanceCreateInfo create(long address, ByteBuffer container) {
                    return new XrInstanceCreateInfo(address, container);
                }

                /**
                 * Creates a {@code XrInstanceCreateInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
                 * visible to the struct instance and vice versa.
                 *
                 * <p>The created instance holds a strong reference to the container object.</p>
                 */
                public XrInstanceCreateInfo(ByteBuffer container) {
                    super(memAddress(container), __checkContainer(container, SIZEOF));
                }

                @Override
                public int sizeof() { return SIZEOF; }

                /** Returns the value of the {@code type} field. */
                public XrStructureType type() { return XrStructureType.fromValue(memGetInt(address() + TYPE)); }
                /** Returns the value of the {@code next} field. */
                public long next() { return memGetAddress(address() + NEXT); }
                /** Returns the value of the {@code createFlags} field. */
                public long createFlags() { return memGetLong(address() + CREATEFLAGS); }
                /** Returns the value of the {@code applicationInfo} field. */
                public XrApplicationInfo applicationInfo() { return XrApplicationInfo.create(address() + APPLICATIONINFO); }
                /** Returns the value of the {@code enabledApiLayerCount} field. */
                public int enabledApiLayerCount() { return memGetInt(address() + ENABLEDAPILAYERCOUNT); }
                /** Returns the value of the {@code enabledApiLayerNames} field. */
                public long enabledApiLayerNames() { return memGetAddress(address() + ENABLEDAPILAYERNAMES); }
                /** Returns the value of the {@code enabledExtensionCount} field. */
                public int enabledExtensionCount() { return memGetInt(address() + ENABLEDEXTENSIONCOUNT); }
                /** Returns the value of the {@code enabledExtensionNames} field. */
                public long enabledExtensionNames() { return memGetAddress(address() + ENABLEDEXTENSIONNAMES); }

                /** Sets the specified value to the {@code type} field. */
                public XrInstanceCreateInfo type(XrStructureType value) { 
                    memPutInt(address() + TYPE, value.getValue());
                    return this;
                }
                /** Sets the specified value to the {@code next} field. */
                public XrInstanceCreateInfo next(long value) { 
                    memPutAddress(address() + NEXT, value);
                    return this;
                }
                /** Sets the specified value to the {@code createFlags} field. */
                public XrInstanceCreateInfo createFlags(long value) { 
                    memPutLong(address() + CREATEFLAGS, value);
                    return this;
                }
                /** Sets the specified value to the {@code applicationInfo} field. */
                public XrInstanceCreateInfo applicationInfo(XrApplicationInfo value) { 
                    memCopy(address() + APPLICATIONINFO, value.address(), XrApplicationInfo.SIZEOF);
                    return this;
                }
                /** Sets the specified value to the {@code enabledApiLayerCount} field. */
                public XrInstanceCreateInfo enabledApiLayerCount(int value) { 
                    memPutInt(address() + ENABLEDAPILAYERCOUNT, value);
                    return this;
                }
                /** Sets the specified value to the {@code enabledApiLayerNames} field. */
                public XrInstanceCreateInfo enabledApiLayerNames(long value) { 
                    memPutAddress(address() + ENABLEDAPILAYERNAMES, value);
                    return this;
                }
                /** Sets the specified value to the {@code enabledExtensionCount} field. */
                public XrInstanceCreateInfo enabledExtensionCount(int value) { 
                    memPutInt(address() + ENABLEDEXTENSIONCOUNT, value);
                    return this;
                }
                /** Sets the specified value to the {@code enabledExtensionNames} field. */
                public XrInstanceCreateInfo enabledExtensionNames(long value) { 
                    memPutAddress(address() + ENABLEDEXTENSIONNAMES, value);
                    return this;
                }
                /** Sets the specified value to the {@code type} field. */
                public XrInstanceCreateInfo type$Default() { return type(XrStructureType.XR_TYPE_INSTANCE_CREATE_INFO); }

                /** Initializes this struct with the specified values. */
                public XrInstanceCreateInfo set(
                    XrStructureType type,
                    long next,
                    long createFlags,
                    XrApplicationInfo applicationInfo,
                    int enabledApiLayerCount,
                    long enabledApiLayerNames,
                    int enabledExtensionCount,
                    long enabledExtensionNames
                ) {
                    type(type);
                    next(next);
                    createFlags(createFlags);
                    applicationInfo(applicationInfo);
                    enabledApiLayerCount(enabledApiLayerCount);
                    enabledApiLayerNames(enabledApiLayerNames);
                    enabledExtensionCount(enabledExtensionCount);
                    enabledExtensionNames(enabledExtensionNames);

                    return this;
                }

                /**
                 * Copies the specified struct data to this struct.
                 *
                 * @param src the source struct
                 *
                 * @return this struct
                 */
                public XrInstanceCreateInfo set(XrInstanceCreateInfo src) {
                    memCopy(src.address(), address(), SIZEOF);
                    return this;
                }

                // -----------------------------------

                /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
                public static XrInstanceCreateInfo malloc() {
                    return new XrInstanceCreateInfo(nmemAllocChecked(SIZEOF), null);
                }

                /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
                public static XrInstanceCreateInfo calloc() {
                    return new XrInstanceCreateInfo(nmemCallocChecked(1, SIZEOF), null);
                }

                /** Returns a new {@code XrInstanceCreateInfo} instance allocated with {@link BufferUtils}. */
                public static XrInstanceCreateInfo create() {
                    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
                    return new XrInstanceCreateInfo(memAddress(container), container);
                }

                /** Returns a new {@code XrInstanceCreateInfo} instance for the specified memory address. */
                public static XrInstanceCreateInfo create(long address) {
                    return new XrInstanceCreateInfo(address, null);
                }

                /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
                public static XrInstanceCreateInfo createSafe(long address) {
                    return address == 0 ? null : new XrInstanceCreateInfo(address, null);
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
                 * Returns a new {@code XrInstanceCreateInfo} instance allocated on the specified {@link MemoryStack}.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrInstanceCreateInfo malloc(MemoryStack stack) {
                    return new XrInstanceCreateInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
                }

                /**
                 * Returns a new {@code XrInstanceCreateInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrInstanceCreateInfo calloc(MemoryStack stack) {
                    return new XrInstanceCreateInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
                public static int ntype(long struct) { return memGetInt(struct + XrInstanceCreateInfo.TYPE); }
                public static void ntype(long struct, int value ) { memPutInt(struct + XrInstanceCreateInfo.TYPE, value); }
                /** Unsafe version of {@link #next}. */
                public static long nnext(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.NEXT); }
                public static void nnext(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.NEXT, value); }
                /** Unsafe version of {@link #createFlags}. */
                public static long ncreateFlags(long struct) { return memGetLong(struct + XrInstanceCreateInfo.CREATEFLAGS); }
                public static void ncreateFlags(long struct, long value) { memPutLong(struct + XrInstanceCreateInfo.CREATEFLAGS, value); }
                /** Unsafe version of {@link #applicationInfo}. */
                public static XrApplicationInfo napplicationInfo(long struct) { return XrApplicationInfo.create(struct + XrInstanceCreateInfo.APPLICATIONINFO); }
                public static void napplicationInfo(long struct, XrApplicationInfo value) { memCopy(value.address(), struct +XrInstanceCreateInfo.APPLICATIONINFO,XrApplicationInfo.SIZEOF); }
                /** Unsafe version of {@link #enabledApiLayerCount}. */
                public static int nenabledApiLayerCount(long struct) { return memGetInt(struct + XrInstanceCreateInfo.ENABLEDAPILAYERCOUNT); }
                public static void nenabledApiLayerCount(long struct, int value) { memPutInt(struct + XrInstanceCreateInfo.ENABLEDAPILAYERCOUNT, value); }
                /** Unsafe version of {@link #enabledApiLayerNames}. */
                public static long nenabledApiLayerNames(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.ENABLEDAPILAYERNAMES); }
                public static void nenabledApiLayerNames(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.ENABLEDAPILAYERNAMES, value); }
                /** Unsafe version of {@link #enabledExtensionCount}. */
                public static int nenabledExtensionCount(long struct) { return memGetInt(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONCOUNT); }
                public static void nenabledExtensionCount(long struct, int value) { memPutInt(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONCOUNT, value); }
                /** Unsafe version of {@link #enabledExtensionNames}. */
                public static long nenabledExtensionNames(long struct) { return memGetAddress(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONNAMES); }
                public static void nenabledExtensionNames(long struct, long value) { memPutAddress(struct + XrInstanceCreateInfo.ENABLEDEXTENSIONNAMES, value); }


                // -----------------------------------

                /** An array of {@link XrInstanceCreateInfo} structs. */
                public static class Buffer extends StructBuffer<XrInstanceCreateInfo, Buffer> {

                    private static final XrInstanceCreateInfo ELEMENT_FACTORY = XrInstanceCreateInfo.create(-1L);

                    /**
                     * Creates a new {@code XrInstanceCreateInfo.Buffer} instance backed by the specified container.
                     *
                     * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
                     * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
                     * by {@link XrInstanceCreateInfo#SIZEOF}, and its mark will be undefined.</p>
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
                    public XrInstanceCreateInfo get(int index) {
                        return XrInstanceCreateInfo.create(address + index * SIZEOF);
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
                    protected XrInstanceCreateInfo getElementFactory() {
                        return ELEMENT_FACTORY;
                    }

                    /** Returns the value of the {@code type} field. */
                    public XrStructureType type() { return XrStructureType.fromValue(XrInstanceCreateInfo.ntype(address())); }
                    /** Returns the value of the {@code next} field. */
                    public long next() { return XrInstanceCreateInfo.nnext(address()); }
                    /** Returns the value of the {@code createFlags} field. */
                    public long createFlags() { return XrInstanceCreateInfo.ncreateFlags(address()); }
                    /** Returns the value of the {@code applicationInfo} field. */
                    public XrApplicationInfo applicationInfo() { return XrInstanceCreateInfo.napplicationInfo(address()); }
                    /** Returns the value of the {@code enabledApiLayerCount} field. */
                    public int enabledApiLayerCount() { return XrInstanceCreateInfo.nenabledApiLayerCount(address()); }
                    /** Returns the value of the {@code enabledApiLayerNames} field. */
                    public long enabledApiLayerNames() { return XrInstanceCreateInfo.nenabledApiLayerNames(address()); }
                    /** Returns the value of the {@code enabledExtensionCount} field. */
                    public int enabledExtensionCount() { return XrInstanceCreateInfo.nenabledExtensionCount(address()); }
                    /** Returns the value of the {@code enabledExtensionNames} field. */
                    public long enabledExtensionNames() { return XrInstanceCreateInfo.nenabledExtensionNames(address()); }

                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type(XrStructureType value) { 
                        XrInstanceCreateInfo.ntype(address(), value.getValue());
                        return this;
                    }
                    /** Sets the specified value to the {@code next} field. */
                    public Buffer next(long value) { 
                        XrInstanceCreateInfo.nnext(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code createFlags} field. */
                    public Buffer createFlags(long value) { 
                        XrInstanceCreateInfo.ncreateFlags(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code applicationInfo} field. */
                    public Buffer applicationInfo(XrApplicationInfo value) { 
                        XrInstanceCreateInfo.napplicationInfo(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code enabledApiLayerCount} field. */
                    public Buffer enabledApiLayerCount(int value) { 
                        XrInstanceCreateInfo.nenabledApiLayerCount(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code enabledApiLayerNames} field. */
                    public Buffer enabledApiLayerNames(long value) { 
                        XrInstanceCreateInfo.nenabledApiLayerNames(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code enabledExtensionCount} field. */
                    public Buffer enabledExtensionCount(int value) { 
                        XrInstanceCreateInfo.nenabledExtensionCount(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code enabledExtensionNames} field. */
                    public Buffer enabledExtensionNames(long value) { 
                        XrInstanceCreateInfo.nenabledExtensionNames(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type$Default() { return type(XrStructureType.XR_TYPE_INSTANCE_CREATE_INFO); }
                }
            }
            """;

    private static final String xrInstanceCreateInfo_cDefinition = """
            typedef struct XrInstanceCreateInfo {
                XrStructureType             type;
                const void* XR_MAY_ALIAS    next;
                XrInstanceCreateFlags       createFlags;
                XrApplicationInfo           applicationInfo;
                uint32_t                    enabledApiLayerCount;
                const char* const*          enabledApiLayerNames;
                uint32_t                    enabledExtensionCount;
                const char* const*          enabledExtensionNames;
            } XrInstanceCreateInfo;
            """;

    @Test
    void testGenerateXrInstanceCreateInfo() throws IOException {
        // this cheats a bit and parses and generates all in one test
        BufferedReader reader = new BufferedReader(new StringReader(xrInstanceCreateInfo_cDefinition));
        StructDefinition structDefinition = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS);

        String result = StructGenerator.generateStruct(structDefinition);

        // Normalize whitespace for comparison
        String normalizedExpected = normalizeWhitespace(expectedXrInstanceCreateInfo);
        String normalizedResult = normalizeWhitespace(result);

        assertEquals(normalizedExpected, normalizedResult);
    }

    // Helper method to normalize whitespace for comparison
    private String normalizeWhitespace(String input) {
        // Remove all whitespace and normalize line endings
        return input.replaceAll("\\s+", " ").trim();
    }
}
