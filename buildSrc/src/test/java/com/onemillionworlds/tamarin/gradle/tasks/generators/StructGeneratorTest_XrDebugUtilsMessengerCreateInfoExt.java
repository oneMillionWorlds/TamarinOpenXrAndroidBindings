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
 * XrDebugUtilsMessengerCreateInfoEXT is interesting because it has a PFN_xrDebugUtilsMessengerCallbackEXT
 * This is a pointer to a function
 */
public class StructGeneratorTest_XrDebugUtilsMessengerCreateInfoExt {

    // Static constants for known types
    private static final List<String> KNOWN_ENUM_TYPES = List.of("XrStructureType");
    private static final List<String> KNOWN_ATOMS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_INTS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_LONGS = Collections.emptyList();
    private static final List<String> KNOWN_HANDLES = Collections.emptyList();
    private static final List<String> KNOWN_FLAGS = List.of("XrDebugUtilsMessageSeverityFlagsEXT", "XrDebugUtilsMessageTypeFlagsEXT");
    private static final List<String> KNOWN_STRUCTS = Collections.emptyList();

    private static final String expectedXrDebugUtilsMessengerCreateInfoEXT = """
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
             * Structure specifying debug utils messenger create info e x t.
             *\s
             * <h3>Layout</h3>
             *\s
             * <pre><code>
             * struct XrDebugUtilsMessengerCreateInfoEXT {
             *     XrStructureType type;
             *     const void* next;
             *     XrDebugUtilsMessageSeverityFlagsEXT messageSeverities;
             *     XrDebugUtilsMessageTypeFlagsEXT messageTypes;
             *     PFN_xrDebugUtilsMessengerCallbackEXT userCallback;
             *     void* userData;
             * }</code></pre>
             * @noinspection unused
             */
            public class XrDebugUtilsMessengerCreateInfoEXT extends Struct<XrDebugUtilsMessengerCreateInfoEXT> {
            
                /** The struct size in bytes. */
                public static final int SIZEOF;
            
                /** The struct alignment in bytes. */
                public static final int ALIGNOF;
            
                /** The struct member offsets. */
                public static final int
                    TYPE,
                    NEXT,
                    MESSAGESEVERITIES,
                    MESSAGETYPES,
                    USERCALLBACK,
                    USERDATA;
            
                static {
                    Layout layout = Layout.__struct(
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE),
                        Layout.__member(8),
                        Layout.__member(8),
                        Layout.__member(POINTER_SIZE),
                        Layout.__member(POINTER_SIZE)
                    );
            
                    SIZEOF = layout.getSize();
                    ALIGNOF = layout.getAlignment();
            
                    TYPE = layout.offsetof(0);
                    NEXT = layout.offsetof(1);
                    MESSAGESEVERITIES = layout.offsetof(2);
                    MESSAGETYPES = layout.offsetof(3);
                    USERCALLBACK = layout.offsetof(4);
                    USERDATA = layout.offsetof(5);
                }
            
                protected XrDebugUtilsMessengerCreateInfoEXT(long address, ByteBuffer container) {
                    super(address, container);
                }
            
                @Override
                protected XrDebugUtilsMessengerCreateInfoEXT create(long address, ByteBuffer container) {
                    return new XrDebugUtilsMessengerCreateInfoEXT(address, container);
                }
            
                /**
                 * Creates a {@code XrDebugUtilsMessengerCreateInfoEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
                 * visible to the struct instance and vice versa.
                 *
                 * <p>The created instance holds a strong reference to the container object.</p>
                 */
                public XrDebugUtilsMessengerCreateInfoEXT(ByteBuffer container) {
                    super(memAddress(container), __checkContainer(container, SIZEOF));
                }
            
                @Override
                public int sizeof() { return SIZEOF; }
            
                /** Returns the value of the {@code type} field. */
                public XrStructureType type() { return XrStructureType.fromValue(memGetInt(address() + TYPE)); }
                /** Returns the value of the {@code next} field. */
                public long next() { return memGetAddress(address() + NEXT); }
                /** Returns the value of the {@code messageSeverities} field. */
                public long messageSeverities() { return memGetLong(address() + MESSAGESEVERITIES); }
                /** Returns the value of the {@code messageTypes} field. */
                public long messageTypes() { return memGetLong(address() + MESSAGETYPES); }
                /** Returns the value of the {@code userCallback} field. */
                public long userCallback() { return memGetAddress(address() + USERCALLBACK); }
                /** Returns the value of the {@code userData} field. */
                public long userData() { return memGetAddress(address() + USERDATA); }
            
                /** Sets the specified value to the {@code type} field. */
                public XrDebugUtilsMessengerCreateInfoEXT type(XrStructureType value) {\s
                    memPutInt(address() + TYPE, value.getValue());
                    return this;
                }
                /** Sets the specified value to the {@code next} field. */
                public XrDebugUtilsMessengerCreateInfoEXT next(long value) {\s
                    memPutAddress(address() + NEXT, value);
                    return this;
                }
                /** Sets the specified value to the {@code messageSeverities} field. */
                public XrDebugUtilsMessengerCreateInfoEXT messageSeverities(long value) {\s
                    memPutLong(address() + MESSAGESEVERITIES, value);
                    return this;
                }
                /** Sets the specified value to the {@code messageTypes} field. */
                public XrDebugUtilsMessengerCreateInfoEXT messageTypes(long value) {\s
                    memPutLong(address() + MESSAGETYPES, value);
                    return this;
                }
                /** Sets the specified value to the {@code userCallback} field. */
                public XrDebugUtilsMessengerCreateInfoEXT userCallback(long value) {\s
                    memPutAddress(address() + USERCALLBACK, value);
                    return this;
                }
                /** Sets the specified value to the {@code userData} field. */
                public XrDebugUtilsMessengerCreateInfoEXT userData(long value) {\s
                    memPutAddress(address() + USERDATA, value);
                    return this;
                }
                /** Sets the specified value to the {@code type} field. */
                public XrDebugUtilsMessengerCreateInfoEXT type$Default() { return type(XrStructureType.XR_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT); }
            
                /** Initializes this struct with the specified values. */
                public XrDebugUtilsMessengerCreateInfoEXT set(
                    XrStructureType type,
                    long next,
                    long messageSeverities,
                    long messageTypes,
                    long userCallback,
                    long userData
                ) {
                    type(type);
                    next(next);
                    messageSeverities(messageSeverities);
                    messageTypes(messageTypes);
                    userCallback(userCallback);
                    userData(userData);
            
                    return this;
                }
            
                /**
                 * Copies the specified struct data to this struct.
                 *
                 * @param src the source struct
                 *
                 * @return this struct
                 */
                public XrDebugUtilsMessengerCreateInfoEXT set(XrDebugUtilsMessengerCreateInfoEXT src) {
                    memCopy(src.address(), address(), SIZEOF);
                    return this;
                }
            
                // -----------------------------------
            
                /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
                public static XrDebugUtilsMessengerCreateInfoEXT malloc() {
                    return new XrDebugUtilsMessengerCreateInfoEXT(nmemAllocChecked(SIZEOF), null);
                }
            
                /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
                public static XrDebugUtilsMessengerCreateInfoEXT calloc() {
                    return new XrDebugUtilsMessengerCreateInfoEXT(nmemCallocChecked(1, SIZEOF), null);
                }
            
                /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated with {@link BufferUtils}. */
                public static XrDebugUtilsMessengerCreateInfoEXT create() {
                    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
                    return new XrDebugUtilsMessengerCreateInfoEXT(memAddress(container), container);
                }
            
                /** Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance for the specified memory address. */
                public static XrDebugUtilsMessengerCreateInfoEXT create(long address) {
                    return new XrDebugUtilsMessengerCreateInfoEXT(address, null);
                }
            
                /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
                public static XrDebugUtilsMessengerCreateInfoEXT createSafe(long address) {
                    return address == 0 ? null : new XrDebugUtilsMessengerCreateInfoEXT(address, null);
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
                 * Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated on the specified {@link MemoryStack}.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrDebugUtilsMessengerCreateInfoEXT malloc(MemoryStack stack) {
                    return new XrDebugUtilsMessengerCreateInfoEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
                }
            
                /**
                 * Returns a new {@code XrDebugUtilsMessengerCreateInfoEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrDebugUtilsMessengerCreateInfoEXT calloc(MemoryStack stack) {
                    return new XrDebugUtilsMessengerCreateInfoEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
                public static int ntype(long struct) { return memGetInt(struct + XrDebugUtilsMessengerCreateInfoEXT.TYPE); }
                public static void ntype(long struct, int value ) { memPutInt(struct + XrDebugUtilsMessengerCreateInfoEXT.TYPE, value); }
                /** Unsafe version of {@link #next}. */
                public static long nnext(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.NEXT); }
                public static void nnext(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.NEXT, value); }
                /** Unsafe version of {@link #messageSeverities}. */
                public static long nmessageSeverities(long struct) { return memGetLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGESEVERITIES); }
                public static void nmessageSeverities(long struct, long value) { memPutLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGESEVERITIES, value); }
                /** Unsafe version of {@link #messageTypes}. */
                public static long nmessageTypes(long struct) { return memGetLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGETYPES); }
                public static void nmessageTypes(long struct, long value) { memPutLong(struct + XrDebugUtilsMessengerCreateInfoEXT.MESSAGETYPES, value); }
                /** Unsafe version of {@link #userCallback}. */
                public static long nuserCallback(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERCALLBACK); }
                public static void nuserCallback(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERCALLBACK, value); }
                /** Unsafe version of {@link #userData}. */
                public static long nuserData(long struct) { return memGetAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERDATA); }
                public static void nuserData(long struct, long value) { memPutAddress(struct + XrDebugUtilsMessengerCreateInfoEXT.USERDATA, value); }
            
            
                // -----------------------------------
            
                /** An array of {@link XrDebugUtilsMessengerCreateInfoEXT} structs. */
                public static class Buffer extends StructBuffer<XrDebugUtilsMessengerCreateInfoEXT, Buffer> {
            
                    private static final XrDebugUtilsMessengerCreateInfoEXT ELEMENT_FACTORY = XrDebugUtilsMessengerCreateInfoEXT.create(-1L);
            
                    /**
                     * Creates a new {@code XrDebugUtilsMessengerCreateInfoEXT.Buffer} instance backed by the specified container.
                     *
                     * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
                     * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
                     * by {@link XrDebugUtilsMessengerCreateInfoEXT#SIZEOF}, and its mark will be undefined.</p>
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
                    public XrDebugUtilsMessengerCreateInfoEXT get(int index) {
                        return XrDebugUtilsMessengerCreateInfoEXT.create(address + index * SIZEOF);
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
                    protected XrDebugUtilsMessengerCreateInfoEXT getElementFactory() {
                        return ELEMENT_FACTORY;
                    }
            
                    /** Returns the value of the {@code type} field. */
                    public XrStructureType type() { return XrStructureType.fromValue(XrDebugUtilsMessengerCreateInfoEXT.ntype(address())); }
                    /** Returns the value of the {@code next} field. */
                    public long next() { return XrDebugUtilsMessengerCreateInfoEXT.nnext(address()); }
                    /** Returns the value of the {@code messageSeverities} field. */
                    public long messageSeverities() { return XrDebugUtilsMessengerCreateInfoEXT.nmessageSeverities(address()); }
                    /** Returns the value of the {@code messageTypes} field. */
                    public long messageTypes() { return XrDebugUtilsMessengerCreateInfoEXT.nmessageTypes(address()); }
                    /** Returns the value of the {@code userCallback} field. */
                    public long userCallback() { return XrDebugUtilsMessengerCreateInfoEXT.nuserCallback(address()); }
                    /** Returns the value of the {@code userData} field. */
                    public long userData() { return XrDebugUtilsMessengerCreateInfoEXT.nuserData(address()); }
            
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type(XrStructureType value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.ntype(address(), value.getValue());
                        return this;
                    }
                    /** Sets the specified value to the {@code next} field. */
                    public Buffer next(long value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.nnext(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code messageSeverities} field. */
                    public Buffer messageSeverities(long value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.nmessageSeverities(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code messageTypes} field. */
                    public Buffer messageTypes(long value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.nmessageTypes(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code userCallback} field. */
                    public Buffer userCallback(long value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.nuserCallback(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code userData} field. */
                    public Buffer userData(long value) {\s
                        XrDebugUtilsMessengerCreateInfoEXT.nuserData(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type$Default() { return type(XrStructureType.XR_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT); }
                }
            }
            """;

    private static final String xrDebugUtilsMessengerCreateInfoEXT_cDefinition = """
            typedef struct XrDebugUtilsMessengerCreateInfoEXT {
                XrStructureType                         type;
                const void* XR_MAY_ALIAS                next;
                XrDebugUtilsMessageSeverityFlagsEXT     messageSeverities;
                XrDebugUtilsMessageTypeFlagsEXT         messageTypes;
                PFN_xrDebugUtilsMessengerCallbackEXT    userCallback;
                void* XR_MAY_ALIAS                      userData;
            } XrDebugUtilsMessengerCreateInfoEXT;
            """;

    @Test
    void testGenerateXrDebugUtilsMessengerCreateInfoEXT() throws IOException {
        // this cheats a bit and parses and generates all in one test
        BufferedReader reader = new BufferedReader(new StringReader(xrDebugUtilsMessengerCreateInfoEXT_cDefinition));
        StructDefinition structDefinition = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS);

        String result = StructGenerator.generateStruct(structDefinition);

        assertEquals(expectedXrDebugUtilsMessengerCreateInfoEXT.trim(), result.trim());
    }

}
