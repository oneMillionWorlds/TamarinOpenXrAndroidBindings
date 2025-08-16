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

public class StructGeneratorTest_XrActionSetCreateInfo {

    // Static constants for known types
    private static final List<String> KNOWN_ENUM_TYPES =List.of("XrStructureType", "XrActionType");
    private static final List<String> KNOWN_ATOMS =  List.of("XrPath");
    private static final List<String> KNOWN_TYPEDEF_INTS = Collections.emptyList();
    private static final List<String> KNOWN_TYPEDEF_LONGS = Collections.emptyList();
    private static final List<String> KNOWN_HANDLES = Collections.emptyList();
    private static final List<String> KNOWN_FLAGS = Collections.emptyList();
    private static final List<String> KNOWN_STRUCTS = Collections.emptyList();

    private static final String expectedXrActionCreateInfo = """
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
             * Structure specifying action set create info.
             *\s
             * <h3>Layout</h3>
             *\s
             * <pre><code>
             * struct XrActionSetCreateInfo {
             *     XrStructureType type;
             *     const void* next;
             *     char actionSetName[XR_MAX_ACTION_SET_NAME_SIZE];
             *     char localizedActionSetName[XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE];
             *     uint32_t priority;
             * }</code></pre>
             * @noinspection unused
             */
            public class XrActionSetCreateInfo extends Struct<XrActionSetCreateInfo> {
            
                /** The struct size in bytes. */
                public static final int SIZEOF;
            
                /** The struct alignment in bytes. */
                public static final int ALIGNOF;
            
                /** The struct member offsets. */
                public static final int
                    TYPE,
                    NEXT,
                    ACTIONSETNAME,
                    LOCALIZEDACTIONSETNAME,
                    PRIORITY;
            
                static {
                    Layout layout = Layout.__struct(
                        Layout.__member(4),
                        Layout.__member(POINTER_SIZE),
                        Layout.__array(1, XR_MAX_ACTION_SET_NAME_SIZE),
                        Layout.__array(1, XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE),
                        Layout.__member(4)
                    );
            
                    SIZEOF = layout.getSize();
                    ALIGNOF = layout.getAlignment();
            
                    TYPE = layout.offsetof(0);
                    NEXT = layout.offsetof(1);
                    ACTIONSETNAME = layout.offsetof(2);
                    LOCALIZEDACTIONSETNAME = layout.offsetof(3);
                    PRIORITY = layout.offsetof(4);
                }
            
                protected XrActionSetCreateInfo(long address, ByteBuffer container) {
                    super(address, container);
                }
            
                @Override
                protected XrActionSetCreateInfo create(long address, ByteBuffer container) {
                    return new XrActionSetCreateInfo(address, container);
                }
            
                /**
                 * Creates a {@code XrActionSetCreateInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
                 * visible to the struct instance and vice versa.
                 *
                 * <p>The created instance holds a strong reference to the container object.</p>
                 */
                public XrActionSetCreateInfo(ByteBuffer container) {
                    super(memAddress(container), __checkContainer(container, SIZEOF));
                }
            
                @Override
                public int sizeof() { return SIZEOF; }
            
                /** Returns the value of the {@code type} field. */
                public XrStructureType type() { return XrStructureType.fromValue(memGetInt(address() + TYPE)); }
                /** Returns the value of the {@code next} field. */
                public long next() { return memGetAddress(address() + NEXT); }
                /** Returns the value of the {@code actionSetName} field. */
                public ByteBuffer actionSetName() { return memByteBuffer(address() + ACTIONSETNAME, XR_MAX_ACTION_SET_NAME_SIZE); }
                /** Returns the null-terminated string stored in the {@code actionSetName} field. */
                public String actionSetNameString() { return memUTF8(address() + ACTIONSETNAME); }
                /** Returns the value of the {@code localizedActionSetName} field. */
                public ByteBuffer localizedActionSetName() { return memByteBuffer(address() + LOCALIZEDACTIONSETNAME, XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE); }
                /** Returns the null-terminated string stored in the {@code localizedActionSetName} field. */
                public String localizedActionSetNameString() { return memUTF8(address() + LOCALIZEDACTIONSETNAME); }
                /** Returns the value of the {@code priority} field. */
                public int priority() { return memGetInt(address() + PRIORITY); }
            
                /** Sets the specified value to the {@code type} field. */
                public XrActionSetCreateInfo type(XrStructureType value) {\s
                    memPutInt(address() + TYPE, value.getValue());
                    return this;
                }
                /** Sets the specified value to the {@code next} field. */
                public XrActionSetCreateInfo next(long value) {\s
                    memPutAddress(address() + NEXT, value);
                    return this;
                }
                /** Sets the specified value to the {@code priority} field. */
                public XrActionSetCreateInfo priority(int value) {\s
                    memPutInt(address() + PRIORITY, value);
                    return this;
                }
                /** Sets the specified value to the {@code type} field. */
                public XrActionSetCreateInfo type$Default() { return type(XrStructureType.XR_TYPE_ACTION_SET_CREATE_INFO); }
            
                /** Initializes this struct with the specified values. */
                public XrActionSetCreateInfo set(
                    XrStructureType type,
                    long next,
                    ByteBuffer actionSetName,
                    ByteBuffer localizedActionSetName,
                    int priority
                ) {
                    type(type);
                    next(next);
                    actionSetName(actionSetName);
                    localizedActionSetName(localizedActionSetName);
                    priority(priority);
            
                    return this;
                }
            
                /**
                 * Copies the specified struct data to this struct.
                 *
                 * @param src the source struct
                 *
                 * @return this struct
                 */
                public XrActionSetCreateInfo set(XrActionSetCreateInfo src) {
                    memCopy(src.address(), address(), SIZEOF);
                    return this;
                }
            
                // -----------------------------------
            
                /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
                public static XrActionSetCreateInfo malloc() {
                    return new XrActionSetCreateInfo(nmemAllocChecked(SIZEOF), null);
                }
            
                /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
                public static XrActionSetCreateInfo calloc() {
                    return new XrActionSetCreateInfo(nmemCallocChecked(1, SIZEOF), null);
                }
            
                /** Returns a new {@code XrActionSetCreateInfo} instance allocated with {@link BufferUtils}. */
                public static XrActionSetCreateInfo create() {
                    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
                    return new XrActionSetCreateInfo(memAddress(container), container);
                }
            
                /** Returns a new {@code XrActionSetCreateInfo} instance for the specified memory address. */
                public static XrActionSetCreateInfo create(long address) {
                    return new XrActionSetCreateInfo(address, null);
                }
            
                /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
                public static XrActionSetCreateInfo createSafe(long address) {
                    return address == 0 ? null : new XrActionSetCreateInfo(address, null);
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
                 * Returns a new {@code XrActionSetCreateInfo} instance allocated on the specified {@link MemoryStack}.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrActionSetCreateInfo malloc(MemoryStack stack) {
                    return new XrActionSetCreateInfo(stack.nmalloc(ALIGNOF, SIZEOF), null);
                }
            
                /**
                 * Returns a new {@code XrActionSetCreateInfo} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
                 *
                 * @param stack the stack from which to allocate
                 */
                public static XrActionSetCreateInfo calloc(MemoryStack stack) {
                    return new XrActionSetCreateInfo(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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
                public static int ntype(long struct) { return memGetInt(struct + XrActionSetCreateInfo.TYPE); }
                public static void ntype(long struct, int value ) { memPutInt(struct + XrActionSetCreateInfo.TYPE, value); }
                /** Unsafe version of {@link #next}. */
                public static long nnext(long struct) { return memGetAddress(struct + XrActionSetCreateInfo.NEXT); }
                public static void nnext(long struct, long value) { memPutAddress(struct + XrActionSetCreateInfo.NEXT, value); }
                /** Unsafe version of {@link #actionSetName}. */
                public static ByteBuffer nactionSetName(long struct) { return memByteBuffer(struct + XrActionSetCreateInfo.ACTIONSETNAME, XR_MAX_ACTION_SET_NAME_SIZE); }
                /** Unsafe version of {@link #actionSetNameString}. */
                public static String nactionSetNameString(long struct) { return memUTF8(struct + XrActionSetCreateInfo.ACTIONSETNAME); }
                /** Unsafe version of {@link #localizedActionSetName}. */
                public static ByteBuffer nlocalizedActionSetName(long struct) { return memByteBuffer(struct + XrActionSetCreateInfo.LOCALIZEDACTIONSETNAME, XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE); }
                /** Unsafe version of {@link #localizedActionSetNameString}. */
                public static String nlocalizedActionSetNameString(long struct) { return memUTF8(struct + XrActionSetCreateInfo.LOCALIZEDACTIONSETNAME); }
                /** Unsafe version of {@link #priority}. */
                public static int npriority(long struct) { return memGetInt(struct + XrActionSetCreateInfo.PRIORITY); }
                public static void npriority(long struct, int value) { memPutInt(struct + XrActionSetCreateInfo.PRIORITY, value); }
            
            
                // -----------------------------------
            
                /** An array of {@link XrActionSetCreateInfo} structs. */
                public static class Buffer extends StructBuffer<XrActionSetCreateInfo, Buffer> {
            
                    private static final XrActionSetCreateInfo ELEMENT_FACTORY = XrActionSetCreateInfo.create(-1L);
            
                    /**
                     * Creates a new {@code XrActionSetCreateInfo.Buffer} instance backed by the specified container.
                     *
                     * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
                     * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
                     * by {@link XrActionSetCreateInfo#SIZEOF}, and its mark will be undefined.</p>
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
                    public XrActionSetCreateInfo get(int index) {
                        return XrActionSetCreateInfo.create(address + index * SIZEOF);
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
                    protected XrActionSetCreateInfo getElementFactory() {
                        return ELEMENT_FACTORY;
                    }
            
                    /** Returns the value of the {@code type} field. */
                    public XrStructureType type() { return XrStructureType.fromValue(XrActionSetCreateInfo.ntype(address())); }
                    /** Returns the value of the {@code next} field. */
                    public long next() { return XrActionSetCreateInfo.nnext(address()); }
                    /** Returns the value of the {@code actionSetName} field. */
                    public ByteBuffer actionSetName() { return XrActionSetCreateInfo.nactionSetName(address()); }
                    /** Returns the null-terminated string stored in the {@code actionSetName} field. */
                    public String actionSetNameString() { return XrActionSetCreateInfo.nactionSetNameString(address()); }
                    /** Returns the value of the {@code localizedActionSetName} field. */
                    public ByteBuffer localizedActionSetName() { return XrActionSetCreateInfo.nlocalizedActionSetName(address()); }
                    /** Returns the null-terminated string stored in the {@code localizedActionSetName} field. */
                    public String localizedActionSetNameString() { return XrActionSetCreateInfo.nlocalizedActionSetNameString(address()); }
                    /** Returns the value of the {@code priority} field. */
                    public int priority() { return XrActionSetCreateInfo.npriority(address()); }
            
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type(XrStructureType value) {\s
                        XrActionSetCreateInfo.ntype(address(), value.getValue());
                        return this;
                    }
                    /** Sets the specified value to the {@code next} field. */
                    public Buffer next(long value) {\s
                        XrActionSetCreateInfo.nnext(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code actionSetName} field. */
                    public Buffer actionSetName(ByteBuffer value) {\s
                        XrActionSetCreateInfo.nactionSetName(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code localizedActionSetName} field. */
                    public Buffer localizedActionSetName(ByteBuffer value) {\s
                        XrActionSetCreateInfo.nlocalizedActionSetName(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code priority} field. */
                    public Buffer priority(int value) {\s
                        XrActionSetCreateInfo.npriority(address(), value);
                        return this;
                    }
                    /** Sets the specified value to the {@code type} field. */
                    public Buffer type$Default() { return type(XrStructureType.XR_TYPE_ACTION_SET_CREATE_INFO); }
                }
            }
            """;

    private static final String xrActionCreateInfo_cDefinition = """
            typedef struct XrActionSetCreateInfo {
                XrStructureType             type;
                const void* XR_MAY_ALIAS    next;
                char                        actionSetName[XR_MAX_ACTION_SET_NAME_SIZE];
                char                        localizedActionSetName[XR_MAX_LOCALIZED_ACTION_SET_NAME_SIZE];
                uint32_t                    priority;
            } XrActionSetCreateInfo;
            """;

    @Test
    void testGenerateXrActionCreateInfo() throws IOException {
        // this cheats a bit and parses and generates all in one test
        BufferedReader reader = new BufferedReader(new StringReader(xrActionCreateInfo_cDefinition));
        StructDefinition structDefinition = StructParser.parseStruct(reader, reader.readLine(),
                KNOWN_ENUM_TYPES, KNOWN_ATOMS, KNOWN_TYPEDEF_INTS, KNOWN_TYPEDEF_LONGS,
                KNOWN_HANDLES, KNOWN_FLAGS, KNOWN_STRUCTS);

        String result = StructGenerator.generateStruct(structDefinition);

        assertEquals(expectedXrActionCreateInfo.trim(), result.trim());
    }

}
