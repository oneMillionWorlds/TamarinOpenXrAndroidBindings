package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generator for struct classes.
 */
public class StructGenerator extends FileGenerator {
    private final StructDefinition struct;

    public StructGenerator(Logger logger, StructDefinition struct) {
        super(logger);
        this.struct = struct;
    }

    /**
     * Gets the import statement for an enum type.
     * 
     * @param type The enum type
     * @return The import statement for the enum type
     */
    private String getEnumImport(String type) {
        return "import com.onemillionworlds.tamarin.openxrbindings.enums." + type + ";\n";
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, struct.getName() + ".java");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write("/*\n");
            writer.write(" * OpenXR Java bindings for Android\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n");
            writer.write("package com.onemillionworlds.tamarin.openxrbindings;\n\n");

            writer.write("import com.onemillionworlds.tamarin.openxrbindings.enums.*;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;\n");


            writer.write("\nimport java.nio.ByteBuffer;\n\n");
            writer.write("import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;\n");
            writer.write("import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;\n");

            writer.write("\n");
            writer.write("/**\n");
            // Add spaces between words in the struct name for better readability
            String description = struct.getName().substring(2); // Remove "Xr" prefix
            description = description.replaceAll("([A-Z])", " $1").toLowerCase().trim(); // Add spaces before capital letters
            writer.write(" * Structure specifying " + description + ".\n");
            writer.write(" * \n");
            writer.write(" * <h3>Layout</h3>\n");
            writer.write(" * \n");
            writer.write(" * <pre><code>\n");
            writer.write(" * struct " + struct.getName() + " {\n");

            // Write struct layout in comment
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();
                String arraySizeConstant = field.getArraySizeConstant();

                if (arraySizeConstant != null) {
                    writer.write(" *     " + fieldType + " " + fieldName + "[" + arraySizeConstant + "];\n");
                } else {
                    writer.write(" *     " + fieldType + " " + fieldName + ";\n");
                }
            }

            writer.write(" * }</code></pre>\n");
            writer.write(" * @noinspection unused\n");
            writer.write(" */\n");
            writer.write("public class " + struct.getName() + " extends Struct<" + struct.getName() + "> {\n\n");

            // Write struct size and alignment
            writer.write("    /** The struct size in bytes. */\n");
            writer.write("    public static final int SIZEOF;\n\n");
            writer.write("    /** The struct alignment in bytes. */\n");
            writer.write("    public static final int ALIGNOF;\n\n");

            // Write field offsets
            writer.write("    /** The struct member offsets. */\n");

            if (struct.getFields().isEmpty()) {
                // If there are no fields, just declare an empty int array
                writer.write("    public static final int[] EMPTY_OFFSETS = {};\n\n");
            } else {
                writer.write("    public static final int\n");

                StringBuilder offsetsBuilder = new StringBuilder();
                for (int i = 0; i < struct.getFields().size(); i++) {
                    StructField field = struct.getFields().get(i);
                    String fieldName = field.getName().toUpperCase();

                    offsetsBuilder.append("        ").append(fieldName);
                    if (i < struct.getFields().size() - 1) {
                        offsetsBuilder.append(",\n");
                    } else {
                        offsetsBuilder.append(";\n");
                    }
                }
                writer.write(offsetsBuilder.toString());
                writer.write("\n");
            }

            // Write static initializer
            writer.write("    static {\n");
            writer.write("        Layout layout = Layout.__struct(\n");

            StringBuilder layoutBuilder = new StringBuilder();
            for (int i = 0; i < struct.getFields().size(); i++) {
                StructField field = struct.getFields().get(i);
                String fieldType = field.getType();

                layoutBuilder.append("            ");
                layoutBuilder.append(field.getLayoutMember());

                if (field.isTypeDefLong()) {
                    logger.lifecycle("  Treating '{}' as a 64-bit integer (8 bytes) because it's a typedef of int64_t or uint64_t", fieldType);
                }

                if (i < struct.getFields().size() - 1) {
                    layoutBuilder.append(",\n");
                } else {
                    layoutBuilder.append("\n");
                }
            }
            writer.write(layoutBuilder.toString());
            writer.write("        );\n\n");

            writer.write("        SIZEOF = layout.getSize();\n");
            writer.write("        ALIGNOF = layout.getAlignment();\n\n");

            // Write field offsets
            for (int i = 0; i < struct.getFields().size(); i++) {
                StructField field = struct.getFields().get(i);
                String fieldName = field.getName().toUpperCase();
                writer.write("        " + fieldName + " = layout.offsetof(" + i + ");\n");
            }
            writer.write("    }\n\n");

            // Write constructors and methods
            writer.write("    protected " + struct.getName() + "(long address, ByteBuffer container) {\n");
            writer.write("        super(address, container);\n");
            writer.write("    }\n\n");

            writer.write("    @Override\n");
            writer.write("    protected " + struct.getName() + " create(long address, ByteBuffer container) {\n");
            writer.write("        return new " + struct.getName() + "(address, container);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Creates a {@code " + struct.getName() + "} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be\n");
            writer.write("     * visible to the struct instance and vice versa.\n");
            writer.write("     *\n");
            writer.write("     * <p>The created instance holds a strong reference to the container object.</p>\n");
            writer.write("     */\n");
            writer.write("    public " + struct.getName() + "(ByteBuffer container) {\n");
            writer.write("        super(memAddress(container), __checkContainer(container, SIZEOF));\n");
            writer.write("    }\n\n");

            writer.write("    @Override\n");
            writer.write("    public int sizeof() { return SIZEOF; }\n\n");

            // Generate getters
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();
                String fieldNameUpper = fieldName.toUpperCase();
                String arraySizeConstant = field.getArraySizeConstant();

                writer.write("    /** Returns the value of the {@code " + fieldName + "} field. */\n");

                if (arraySizeConstant != null) {
                    if (fieldType.equals("char")) {
                        writer.write("    public ByteBuffer " + fieldName + "() { return memByteBuffer(address() + " + fieldNameUpper + ", " + arraySizeConstant + "); }\n");
                        writer.write("    /** Returns the null-terminated string stored in the {@code " + fieldName + "} field. */\n");
                        writer.write("    public String " + fieldName + "String() { return memUTF8(address() + " + fieldNameUpper + "); }\n");
                    } else {
                        writer.write("    public ByteBuffer " + fieldName + "() { return memByteBuffer(address() + " + fieldNameUpper + ", " + arraySizeConstant + " * " + field.getMemorySize() + "); }\n");
                    }
                } else if (field.isEnumType()) {
                    writer.write("    public " + field.getJavaType() + " " + fieldName + "() { return " + fieldType + ".fromValue(memGetInt(address() + " + fieldNameUpper + ")); }\n");
                } else if (fieldType.equals("int16_t")) {
                    writer.write("    public short " + fieldName + "() { return memGetShort(address() + " + fieldNameUpper + "); }\n");
                } else {
                    String javaType = field.getJavaType();
                    String accessMethod = field.getMemoryAccessMethod();

                    if (field.isStructByValue()) {
                        writer.write("    public " + javaType + " " + fieldName + "() { return " + javaType + ".create(address() + " + fieldNameUpper + "); }\n");
                    } else {
                        writer.write("    public " + javaType + " " + fieldName + "() { return " + accessMethod + "(address() + " + fieldNameUpper + "); }\n");
                    }
                }
            }
            writer.write("\n");

            // Generate setters for type, next fields, and handle/atom types
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();
                String fieldNameUpper = fieldName.toUpperCase();

                if (fieldName.equals("type")) {
                    writer.write("    /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("    public " + struct.getName() + " " + fieldName + "(int value) { memPutInt(address() + " + fieldNameUpper + ", value); return this; }\n");
                } else if (fieldName.equals("next")) {
                    writer.write("    /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("    public " + struct.getName() + " " + fieldName + "(long value) { memPutAddress(address() + " + fieldNameUpper + ", value); return this; }\n");
                } else if (field.isHandle() || field.isAtom() || field.isFlag() || field.isTypeDefLong()) {
                    writer.write("    /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("    public " + struct.getName() + " " + fieldName + "(long value) { memPutLong(address() + " + fieldNameUpper + ", value); return this; }\n");
                } else if (field.isEnumType()) {
                    writer.write("    /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("    public " + struct.getName() + " " + fieldName + "(" + fieldType + " value) { memPutInt(address() + " + fieldNameUpper + ", value.getValue()); return this; }\n");
                }
            }

            // Add type$Default method if the struct has a type field
            boolean hasTypeField = struct.getFields().stream()
                    .anyMatch(field -> field.getName().equals("type"));
            if (hasTypeField) {
                String typeConstant = getTypeConstantForStruct(struct.getName());
                writer.write("    /** Sets the specified value to the {@code type} field. */\n");
                writer.write("    public " + struct.getName() + " type$Default() { return type(XrStructureType." + typeConstant + ".getValue()); }\n");
            }

            writer.write("\n");

            // Generate set method
            writer.write("    /** Initializes this struct with the specified values. */\n");
            writer.write("    public " + struct.getName() + " set(\n");

            StringBuilder setParamsBuilder = new StringBuilder();
            StringBuilder setBodyBuilder = new StringBuilder();

            List<StructField> settableFields = new ArrayList<>();
            for (StructField field : struct.getFields()) {
                if (field.getName().equals("type") || field.getName().equals("next")) {
                    settableFields.add(field);
                }
            }

            for (int i = 0; i < settableFields.size(); i++) {
                StructField field = settableFields.get(i);
                String fieldType = field.getType();
                String fieldName = field.getName();

                if (fieldType.equals("XrStructureType")) {
                    setParamsBuilder.append("        int ").append(fieldName);
                } else if (fieldType.contains("*")) {
                    setParamsBuilder.append("        long ").append(fieldName);
                } else {
                    setParamsBuilder.append("        ").append(fieldType).append(" ").append(fieldName);
                }

                if (i < settableFields.size() - 1) {
                    setParamsBuilder.append(",\n");
                } else {
                    setParamsBuilder.append("\n");
                }

                setBodyBuilder.append("        ").append(fieldName).append("(").append(fieldName).append(");\n");
            }

            writer.write(setParamsBuilder.toString());
            writer.write("    ) {\n");
            writer.write(setBodyBuilder.toString());
            writer.write("\n");
            writer.write("        return this;\n");
            writer.write("    }\n\n");

            // Generate copy method
            writer.write("    /**\n");
            writer.write("     * Copies the specified struct data to this struct.\n");
            writer.write("     *\n");
            writer.write("     * @param src the source struct\n");
            writer.write("     *\n");
            writer.write("     * @return this struct\n");
            writer.write("     */\n");
            writer.write("    public " + struct.getName() + " set(" + struct.getName() + " src) {\n");
            writer.write("        memCopy(src.address(), address(), SIZEOF);\n");
            writer.write("        return this;\n");
            writer.write("    }\n\n");

            // Add standard factory methods
            writer.write("    // -----------------------------------\n\n");

            writer.write("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */\n");
            writer.write("    public static " + struct.getName() + " malloc() {\n");
            writer.write("        return new " + struct.getName() + "(nmemAllocChecked(SIZEOF), null);\n");
            writer.write("    }\n\n");

            writer.write("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */\n");
            writer.write("    public static " + struct.getName() + " calloc() {\n");
            writer.write("        return new " + struct.getName() + "(nmemCallocChecked(1, SIZEOF), null);\n");
            writer.write("    }\n\n");

            writer.write("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link BufferUtils}. */\n");
            writer.write("    public static " + struct.getName() + " create() {\n");
            writer.write("        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);\n");
            writer.write("        return new " + struct.getName() + "(memAddress(container), container);\n");
            writer.write("    }\n\n");

            writer.write("    /** Returns a new {@code " + struct.getName() + "} instance for the specified memory address. */\n");
            writer.write("    public static " + struct.getName() + " create(long address) {\n");
            writer.write("        return new " + struct.getName() + "(address, null);\n");
            writer.write("    }\n\n");

            writer.write("    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */\n");
            writer.write("    public static " + struct.getName() + " createSafe(long address) {\n");
            writer.write("        return address == 0 ? null : new " + struct.getName() + "(address, null);\n");
            writer.write("    }\n\n");

            // Add Buffer methods
            writer.write("    /**\n");
            writer.write("     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed.\n");
            writer.write("     *\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer malloc(int capacity) {\n");
            writer.write("        return new Buffer(nmemAllocChecked(__checkMalloc(capacity * SIZEOF)), capacity);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed.\n");
            writer.write("     *\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer calloc(int capacity) {\n");
            writer.write("        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Returns a new {@link Buffer} instance allocated with {@link BufferUtils}.\n");
            writer.write("     *\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer create(int capacity) {\n");
            writer.write("        ByteBuffer container = __create(capacity, SIZEOF);\n");
            writer.write("        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Create a {@link Buffer} instance at the specified memory.\n");
            writer.write("     *\n");
            writer.write("     * @param address  the memory address\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer create(long address, int capacity) {\n");
            writer.write("        return new Buffer(address, capacity);\n");
            writer.write("    }\n\n");

            writer.write("    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */\n");
            writer.write("    public static Buffer createSafe(long address, int capacity) {\n");
            writer.write("        return address == 0 ? null : new Buffer(address, capacity);\n");
            writer.write("    }\n\n");

            // Add MemoryStack methods
            writer.write("    /**\n");
            writer.write("     * Returns a new {@code " + struct.getName() + "} instance allocated on the specified {@link MemoryStack}.\n");
            writer.write("     *\n");
            writer.write("     * @param stack the stack from which to allocate\n");
            writer.write("     */\n");
            writer.write("    public static " + struct.getName() + " malloc(MemoryStack stack) {\n");
            writer.write("        return new " + struct.getName() + "(stack.nmalloc(ALIGNOF, SIZEOF), null);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Returns a new {@code " + struct.getName() + "} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.\n");
            writer.write("     *\n");
            writer.write("     * @param stack the stack from which to allocate\n");
            writer.write("     */\n");
            writer.write("    public static " + struct.getName() + " calloc(MemoryStack stack) {\n");
            writer.write("        return new " + struct.getName() + "(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.\n");
            writer.write("     *\n");
            writer.write("     * @param stack    the stack from which to allocate\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer malloc(int capacity, MemoryStack stack) {\n");
            writer.write("        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);\n");
            writer.write("    }\n\n");

            writer.write("    /**\n");
            writer.write("     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.\n");
            writer.write("     *\n");
            writer.write("     * @param stack    the stack from which to allocate\n");
            writer.write("     * @param capacity the buffer capacity\n");
            writer.write("     */\n");
            writer.write("    public static Buffer calloc(int capacity, MemoryStack stack) {\n");
            writer.write("        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);\n");
            writer.write("    }\n\n");

            // Add unsafe methods
            writer.write("    // -----------------------------------\n\n");

            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();
                String fieldNameUpper = fieldName.toUpperCase();
                String arraySizeConstant = field.getArraySizeConstant();

                writer.write("    /** Unsafe version of {@link #" + fieldName + "}. */\n");

                if (arraySizeConstant != null) {
                    if (fieldType.equals("char")) {
                        writer.write("    public static ByteBuffer n" + fieldName + "(long struct) { return memByteBuffer(struct + " + struct.getName() + "." + fieldNameUpper + ", " + arraySizeConstant + "); }\n");
                        writer.write("    /** Unsafe version of {@link #" + fieldName + "String}. */\n");
                        writer.write("    public static String n" + fieldName + "String(long struct) { return memUTF8(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                    } else {
                        writer.write("    public static ByteBuffer n" + fieldName + "(long struct) { return memByteBuffer(struct + " + struct.getName() + "." + fieldNameUpper + ", " + arraySizeConstant + " * " + field.getMemorySize() + "); }\n");
                    }
                } else if (field.isEnumType()) {
                    writer.write("    public static int n" + fieldName + "(long struct) { return memGetInt(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("int16_t")) {
                    // Special case for int16_t
                    writer.write("    public static short n" + fieldName + "(long struct) { return memGetShort(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else {
                    String javaType = field.getJavaType();
                    String accessMethod = field.getMemoryAccessMethod();

                    if (field.isStructByValue()) {
                        writer.write("    public static " + javaType + " n" + fieldName + "(long struct) { return " + javaType + ".create(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                    } else {
                        // For unsafe methods, we need to use the primitive return type for enums
                        if (field.isEnumType()) {
                            writer.write("    public static int n" + fieldName + "(long struct) { return " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                        } else {
                            writer.write("    public static " + javaType + " n" + fieldName + "(long struct) { return " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                        }
                    }
                }
            }

            writer.write("\n");

            // Generate unsafe setters for type, next fields, and handle/atom types
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();

                if (fieldName.equals("type")) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(int) " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, int value) { memPutInt(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
                } else if (fieldName.equals("next")) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(long) " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, long value) { memPutAddress(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
                } else if (field.isHandle() || field.isAtom() || field.isFlag() || field.isTypeDefLong()) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(long) " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, long value) { memPutLong(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
                } else if (field.isEnumType()) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(" + fieldType + ") " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, int value) { memPutInt(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
                }
            }

            writer.write("\n");
            writer.write("    // -----------------------------------\n\n");

            // Generate Buffer inner class
            writer.write("    /** An array of {@link " + struct.getName() + "} structs. */\n");
            writer.write("    public static class Buffer extends StructBuffer<" + struct.getName() + ", Buffer> {\n\n");

            writer.write("        private static final " + struct.getName() + " ELEMENT_FACTORY = " + struct.getName() + ".create(-1L);\n\n");

            writer.write("        /**\n");
            writer.write("         * Creates a new {@code " + struct.getName() + ".Buffer} instance backed by the specified container.\n");
            writer.write("         *\n");
            writer.write("         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values\n");
            writer.write("         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided\n");
            writer.write("         * by {@link " + struct.getName() + "#SIZEOF}, and its mark will be undefined.</p>\n");
            writer.write("         *\n");
            writer.write("         * <p>The created buffer instance holds a strong reference to the container object.</p>\n");
            writer.write("         */\n");
            writer.write("        public Buffer(ByteBuffer container) {\n");
            writer.write("            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);\n");
            writer.write("        }\n\n");

            writer.write("        public Buffer(long address, int cap) {\n");
            writer.write("            super(address, null, -1, 0, cap, cap);\n");
            writer.write("        }\n\n");

            writer.write("        @Override\n");
            writer.write("        public " + struct.getName() + " get(int index) {\n");
            writer.write("            return " + struct.getName() + ".create(address + index * SIZEOF);\n");
            writer.write("        }\n\n");

            writer.write("        @Override\n");
            writer.write("        public Buffer slice() {\n");
            writer.write("            return slice(0, remaining());\n");
            writer.write("        }\n\n");

            writer.write("        Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {\n");
            writer.write("            super(address, container, mark, pos, lim, cap);\n");
            writer.write("        }\n\n");

            writer.write("        @Override\n");
            writer.write("        protected Buffer self() {\n");
            writer.write("            return this;\n");
            writer.write("        }\n\n");

            writer.write("        @Override\n");
            writer.write("        protected Buffer create(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {\n");
            writer.write("            return new Buffer(address, container, mark, position, limit, capacity);\n");
            writer.write("        }\n\n");

            writer.write("        @Override\n");
            writer.write("        protected " + struct.getName() + " getElementFactory() {\n");
            writer.write("            return ELEMENT_FACTORY;\n");
            writer.write("        }\n\n");

            // Generate getters for Buffer
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();
                String arraySizeConstant = field.getArraySizeConstant();

                writer.write("        /** Returns the value of the {@code " + fieldName + "} field. */\n");

                if (arraySizeConstant != null) {
                    if (fieldType.equals("char")) {
                        writer.write("        public ByteBuffer " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                        writer.write("        /** Returns the null-terminated string stored in the {@code " + fieldName + "} field. */\n");
                        writer.write("        public String " + fieldName + "String() { return " + struct.getName() + ".n" + fieldName + "String(address()); }\n");
                    } else {
                        writer.write("        public ByteBuffer " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                    }
                } else if (field.isEnumType()) {
                    writer.write("        public " + field.getJavaType() + " " + fieldName + "() { return " + fieldType + ".fromValue(" + struct.getName() + ".n" + fieldName + "(address())); }\n");
                } else {
                    String javaType = field.getJavaType();

                    if (field.isStructByValue()) {
                        writer.write("        public " + javaType + " " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                    } else {
                        writer.write("        public " + javaType + " " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                    }
                }
            }

            writer.write("\n");

            // Generate setters for Buffer
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();

                if (fieldName.equals("type")) {
                    writer.write("        /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("        public Buffer " + fieldName + "(int value) { " + struct.getName() + ".n" + fieldName + "(address(), value); return this; }\n");
                } else if (fieldName.equals("next")) {
                    writer.write("        /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("        public Buffer " + fieldName + "(long value) { " + struct.getName() + ".n" + fieldName + "(address(), value); return this; }\n");
                } else if (field.isHandle() || field.isAtom() || field.isFlag() || field.isTypeDefLong()) {
                    writer.write("        /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("        public Buffer " + fieldName + "(long value) { " + struct.getName() + ".n" + fieldName + "(address(), value); return this; }\n");
                } else if (field.isEnumType()) {
                    writer.write("        /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
                    writer.write("        public Buffer " + fieldName + "(" + fieldType + " value) { " + struct.getName() + ".n" + fieldName + "(address(), value.getValue()); return this; }\n");
                }
            }

            // Add type$Default method for Buffer if the struct has a type field
            if (hasTypeField) {
                String typeConstant = getTypeConstantForStruct(struct.getName());
                writer.write("        /** Sets the specified value to the {@code type} field. */\n");
                writer.write("        public Buffer type$Default() { return type(XrStructureType." + typeConstant + ".getValue()); }\n");
            }

            writer.write("    }\n");
            writer.write("}\n");
        }

        logGeneration(struct.getName() + ".java");
    }

    /**
     * Converts a struct name to its corresponding type constant.
     * For example, "XrExtensionProperties" -> "XR_TYPE_EXTENSION_PROPERTIES"
     * Handles acronyms correctly, e.g., "XrBindingModificationsKHR" -> "XR_TYPE_BINDING_MODIFICATIONS_KHR"
     */
    private String getTypeConstantForStruct(String structName) {
        // Convert camel case to underscore format for the type constant
        String name = structName.substring(2); // Remove "Xr" prefix
        StringBuilder typeConstantBuilder = new StringBuilder("XR_TYPE_");
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            // Only add underscore when transitioning from lowercase to uppercase
            // This ensures acronyms like "KHR" stay together without underscores
            if (i > 0 && Character.isUpperCase(c) && i < name.length() - 1 && 
                (Character.isLowerCase(name.charAt(i - 1)) || 
                 (Character.isUpperCase(name.charAt(i - 1)) && Character.isLowerCase(name.charAt(i + 1))))) {
                typeConstantBuilder.append('_');
            }
            typeConstantBuilder.append(Character.toUpperCase(c));
        }
        return typeConstantBuilder.toString();
    }

    // These methods have been replaced by StructField.getMemorySize()
}
