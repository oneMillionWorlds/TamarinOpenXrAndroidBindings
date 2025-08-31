package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.StructParser;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Generator for struct classes.
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class StructGenerator extends FileGenerator {
    private final StructDefinition struct;
    public StructGenerator(Logger logger, StructDefinition struct) {
        super(logger);
        this.struct = struct;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, struct.getName() + ".java");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write(generateStruct(struct));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to generate struct " + struct.getName() + " to file " + outputFile.getAbsolutePath(), e);
        }

        logGeneration(struct.getName() + ".java");
    }

    public static String generateStruct(StructDefinition struct){

        StringBuilder writer = new StringBuilder();

        writer.append("/*\n");
        writer.append(" * OpenXR Java bindings for Android\n");
        writer.append(" * This file is auto-generated. DO NOT EDIT.\n");
        writer.append(" */\n");
        writer.append("package com.onemillionworlds.tamarin.openxrbindings;\n\n");

        writer.append("import com.onemillionworlds.tamarin.openxrbindings.enums.*;\n");
        writer.append("import com.onemillionworlds.tamarin.openxrbindings.handles.*;\n");
        writer.append("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;\n");
        writer.append("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;\n");


        writer.append("\nimport java.nio.ByteBuffer;\n\n");
        writer.append("import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;\n");
        writer.append("import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;\n");
        writer.append("import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;\n");

        writer.append("\n");
        writer.append("/**\n");
        // Add spaces between words in the struct name for better readability
        String description = struct.getName().substring(2); // Remove "Xr" prefix
        description = description.replaceAll("([A-Z])", " $1").toLowerCase().trim(); // Add spaces before capital letters
        writer.append(" * Structure specifying " + description + ".\n");
        writer.append(" * \n");
        writer.append(" * <h3>Layout</h3>\n");
        writer.append(" * \n");
        writer.append(" * <pre><code>\n");
        writer.append(" * struct " + struct.getName() + " {\n");

        // Write struct layout in comment
        for (StructField field : struct.getFields()) {
            String fieldType = field.getType();
            String fieldName = field.getName();
            String arraySizeConstant = field.getArraySizeConstant();

            writer.append(" *     ");

            if(field.isConst()){
                writer.append("const ");
            }
            writer.append(fieldType);
            if(field.isPointer()){
                writer.append("*");
            }
            writer.append(" ");
            writer.append(fieldName);

            if(arraySizeConstant != null){
                writer.append("[").append(arraySizeConstant).append("]");
            }
            writer.append(";\n");
        }

        writer.append(" * }</code></pre>\n");
        writer.append(" * @noinspection unused\n");
        writer.append(" */\n");
        writer.append("public class " + struct.getName() + " extends Struct<" + struct.getName() + "> {\n\n");

        // Write struct size and alignment
        writer.append("    /** The struct size in bytes. */\n");
        writer.append("    public static final int SIZEOF;\n\n");
        writer.append("    /** The struct alignment in bytes. */\n");
        writer.append("    public static final int ALIGNOF;\n\n");

        // Write field offsets
        writer.append("    /** The struct member offsets. */\n");

        if (struct.getFields().isEmpty()) {
            // If there are no fields, just declare an empty int array
            writer.append("    public static final int[] EMPTY_OFFSETS = {};\n\n");
        } else {
            writer.append("    public static final int\n");

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
            writer.append(offsetsBuilder);
            writer.append("\n");
        }

        // Write static initializer
        writer.append("    static {\n");
        writer.append("        Layout layout = Layout.__struct(\n");

        StringBuilder layoutBuilder = new StringBuilder();
        for (int i = 0; i < struct.getFields().size(); i++) {
            StructField field = struct.getFields().get(i);
            String fieldType = field.getType();

            layoutBuilder.append("            ");
            layoutBuilder.append(field.getLayoutMember());

            if (i < struct.getFields().size() - 1) {
                layoutBuilder.append(",\n");
            } else {
                layoutBuilder.append("\n");
            }
        }
        writer.append(layoutBuilder.toString());
        writer.append("        );\n\n");

        writer.append("        SIZEOF = layout.getSize();\n");
        writer.append("        ALIGNOF = layout.getAlignment();\n\n");

        // Write field offsets
        for (int i = 0; i < struct.getFields().size(); i++) {
            StructField field = struct.getFields().get(i);
            String fieldName = field.getName().toUpperCase();
            writer.append("        " + fieldName + " = layout.offsetof(" + i + ");\n");
        }
        writer.append("    }\n\n");

        // Write constructors and methods
        writer.append("    protected " + struct.getName() + "(long address, ByteBuffer container) {\n");
        writer.append("        super(address, container);\n");
        writer.append("    }\n\n");

        writer.append("    @Override\n");
        writer.append("    protected " + struct.getName() + " create(long address, ByteBuffer container) {\n");
        writer.append("        return new " + struct.getName() + "(address, container);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Creates a {@code " + struct.getName() + "} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be\n");
        writer.append("     * visible to the struct instance and vice versa.\n");
        writer.append("     *\n");
        writer.append("     * <p>The created instance holds a strong reference to the container object.</p>\n");
        writer.append("     */\n");
        writer.append("    public " + struct.getName() + "(ByteBuffer container) {\n");
        writer.append("        super(memAddress(container), __checkContainer(container, SIZEOF));\n");
        writer.append("    }\n\n");

        writer.append("    @Override\n");
        writer.append("    public int sizeof() { return SIZEOF; }\n\n");

        // Generate getters
        for (StructField field : struct.getFields()) {
            try{
                generateFieldGetter(struct, field, writer);
            }catch (Exception e) {
                throw new RuntimeException("Failed to generate getter for field " + field.getName() + " in struct " + struct.getName(), e);
            }

        }
        writer.append("\n");

        // Generate setters for type, next fields, and handle/atom types
        for (StructField field : struct.getFields()) {
            writer.append(generateFieldSetter(struct, field));
        }

        // Add type$Default method if the struct has a type field
        boolean hasTypeField = struct.getFields().stream()
                .anyMatch(field -> field.getName().equals("type"));
        if (hasTypeField && struct.canBeItsOwnDefault()) {
            String typeConstant = StructParser.createXrStructureTypeEnumValueForStruct(struct.getName());
            writer.append("    /** Sets the specified value to the {@code type} field. */\n");
            writer.append("    public " + struct.getName() + " type$Default() { return type(XrStructureType." + typeConstant + "); }\n");
        }

        writer.append("\n");

        // Generate set method
        boolean anyFieldIsAStructArray = struct.getFields().stream()
                .anyMatch(field -> field.isStruct() && field.getArraySizeConstant() !=null && !field.isPointer());
        if(!anyFieldIsAStructArray) {
            writer.append(createSetMethod(struct));
        }

        // Generate copy method
        writer.append("    /**\n");
        writer.append("     * Copies the specified struct data to this struct.\n");
        writer.append("     *\n");
        writer.append("     * @param src the source struct\n");
        writer.append("     *\n");
        writer.append("     * @return this struct\n");
        writer.append("     */\n");
        writer.append("    public " + struct.getName() + " set(" + struct.getName() + " src) {\n");
        writer.append("        memCopy(src.address(), address(), SIZEOF);\n");
        writer.append("        return this;\n");
        writer.append("    }\n\n");

       struct.getBaseHeader().ifPresent(parent -> {
           writer.append("    /** Get a view of this struct as its parent (for use in methods that take the parent)*/\n");
           writer.append("    public " + parent + " asParent() {\n");
           writer.append("        return new "+parent+"(address(), container());\n");
           writer.append("    }\n\n");
       });


        // Add standard factory methods
        writer.append("    // -----------------------------------\n\n");

        writer.append("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */\n");
        writer.append("    public static " + struct.getName() + " malloc() {\n");
        writer.append("        return new " + struct.getName() + "(nmemAllocChecked(SIZEOF), null);\n");
        writer.append("    }\n\n");

        writer.append("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */\n");
        writer.append("    public static " + struct.getName() + " calloc() {\n");
        writer.append("        return new " + struct.getName() + "(nmemCallocChecked(1, SIZEOF), null);\n");
        writer.append("    }\n\n");

        writer.append("    /** Returns a new {@code " + struct.getName() + "} instance allocated with {@link BufferUtils}. */\n");
        writer.append("    public static " + struct.getName() + " create() {\n");
        writer.append("        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);\n");
        writer.append("        return new " + struct.getName() + "(memAddress(container), container);\n");
        writer.append("    }\n\n");

        writer.append("    /** Returns a new {@code " + struct.getName() + "} instance for the specified memory address. */\n");
        writer.append("    public static " + struct.getName() + " create(long address) {\n");
        writer.append("        return new " + struct.getName() + "(address, null);\n");
        writer.append("    }\n\n");

        writer.append("    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */\n");
        writer.append("    public static " + struct.getName() + " createSafe(long address) {\n");
        writer.append("        return address == 0 ? null : new " + struct.getName() + "(address, null);\n");
        writer.append("    }\n\n");

        // Add Buffer methods
        writer.append("    /**\n");
        writer.append("     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed.\n");
        writer.append("     *\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer malloc(int capacity) {\n");
        writer.append("        return new Buffer(nmemAllocChecked(__checkMalloc(capacity * SIZEOF)), capacity);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed.\n");
        writer.append("     *\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer calloc(int capacity) {\n");
        writer.append("        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Returns a new {@link Buffer} instance allocated with {@link BufferUtils}.\n");
        writer.append("     *\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer create(int capacity) {\n");
        writer.append("        ByteBuffer container = __create(capacity, SIZEOF);\n");
        writer.append("        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Create a {@link Buffer} instance at the specified memory.\n");
        writer.append("     *\n");
        writer.append("     * @param address  the memory address\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer create(long address, int capacity) {\n");
        writer.append("        return new Buffer(address, capacity);\n");
        writer.append("    }\n\n");

        writer.append("    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */\n");
        writer.append("    public static Buffer createSafe(long address, int capacity) {\n");
        writer.append("        return address == 0 ? null : new Buffer(address, capacity);\n");
        writer.append("    }\n\n");

        // Add MemoryStack methods
        writer.append("    /**\n");
        writer.append("     * Returns a new {@code " + struct.getName() + "} instance allocated on the specified {@link MemoryStack}.\n");
        writer.append("     *\n");
        writer.append("     * @param stack the stack from which to allocate\n");
        writer.append("     */\n");
        writer.append("    public static " + struct.getName() + " malloc(MemoryStack stack) {\n");
        writer.append("        return new " + struct.getName() + "(stack.nmalloc(ALIGNOF, SIZEOF), null);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Returns a new {@code " + struct.getName() + "} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.\n");
        writer.append("     *\n");
        writer.append("     * @param stack the stack from which to allocate\n");
        writer.append("     */\n");
        writer.append("    public static " + struct.getName() + " calloc(MemoryStack stack) {\n");
        writer.append("        return new " + struct.getName() + "(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.\n");
        writer.append("     *\n");
        writer.append("     * @param stack    the stack from which to allocate\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer malloc(int capacity, MemoryStack stack) {\n");
        writer.append("        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);\n");
        writer.append("    }\n\n");

        writer.append("    /**\n");
        writer.append("     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.\n");
        writer.append("     *\n");
        writer.append("     * @param stack    the stack from which to allocate\n");
        writer.append("     * @param capacity the buffer capacity\n");
        writer.append("     */\n");
        writer.append("    public static Buffer calloc(int capacity, MemoryStack stack) {\n");
        writer.append("        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);\n");
        writer.append("    }\n\n");

        // Add unsafe methods
        writer.append("    // -----------------------------------\n\n");

        for (StructField field : struct.getFields()) {
            writer.append(generateUnsafeGetterAndSetter(struct, field));
        }

        writer.append("\n");

        writer.append("\n");
        writer.append("    // -----------------------------------\n\n");

        // Generate Buffer inner class
        writer.append("    /** An array of {@link " + struct.getName() + "} structs. */\n");
        writer.append("    public static class Buffer extends StructBuffer<" + struct.getName() + ", Buffer> {\n\n");

        writer.append("        private static final " + struct.getName() + " ELEMENT_FACTORY = " + struct.getName() + ".create(-1L);\n\n");

        writer.append("        /**\n");
        writer.append("         * Creates a new {@code " + struct.getName() + ".Buffer} instance backed by the specified container.\n");
        writer.append("         *\n");
        writer.append("         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values\n");
        writer.append("         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided\n");
        writer.append("         * by {@link " + struct.getName() + "#SIZEOF}, and its mark will be undefined.</p>\n");
        writer.append("         *\n");
        writer.append("         * <p>The created buffer instance holds a strong reference to the container object.</p>\n");
        writer.append("         */\n");
        writer.append("        public Buffer(ByteBuffer container) {\n");
        writer.append("            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF);\n");
        writer.append("        }\n\n");

        writer.append("        public Buffer(long address, int cap) {\n");
        writer.append("            super(address, null, -1, 0, cap, cap);\n");
        writer.append("        }\n\n");

        writer.append("        @Override\n");
        writer.append("        public " + struct.getName() + " get(int index) {\n");
        writer.append("            return " + struct.getName() + ".create(address + index * SIZEOF);\n");
        writer.append("        }\n\n");

        writer.append("        @Override\n");
        writer.append("        public Buffer slice() {\n");
        writer.append("            return slice(0, remaining());\n");
        writer.append("        }\n\n");

        writer.append("        Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {\n");
        writer.append("            super(address, container, mark, pos, lim, cap);\n");
        writer.append("        }\n\n");

        writer.append("        @Override\n");
        writer.append("        protected Buffer self() {\n");
        writer.append("            return this;\n");
        writer.append("        }\n\n");

        writer.append("        @Override\n");
        writer.append("        protected Buffer create(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {\n");
        writer.append("            return new Buffer(address, container, mark, position, limit, capacity);\n");
        writer.append("        }\n\n");

        writer.append("        @Override\n");
        writer.append("        protected " + struct.getName() + " getElementFactory() {\n");
        writer.append("            return ELEMENT_FACTORY;\n");
        writer.append("        }\n\n");

        // Generate getters for Buffer
        for (StructField field : struct.getFields()) {
            String fieldType = field.getType();
            String fieldName = field.getName();
            String fieldNameSanitised = sanitiseFieldName(fieldName);
            String javaType = field.getJavaType();

            writer.append("        /** Returns the value of the {@code " + fieldName + "} field. */\n");
            if (field.isEnumType()) {
                writer.append("        public " + field.getJavaType() + " " + fieldNameSanitised + "() { return " + fieldType + ".fromValue(" + struct.getName() + ".n" + fieldName + "(address())); }\n");
            } else if (field.isHandle() && !field.isPointer()) {
                writer.append("        public " + javaType + " " + fieldNameSanitised + "() { return new " + javaType + "(" + struct.getName() + ".n" + fieldName + "(address())); }\n");
            } else {
                writer.append("        public " + javaType + " " + fieldNameSanitised + "() { return " + struct.getName() + ".n" + fieldNameSanitised + "(address()); }\n");
            }

            if(field.getArraySizeConstant() != null && field.isStruct() && !field.isPointer()) {
                // bonus getter by index if an array of structs
                writer.append("        /** Returns the value of the index-th item in the {@code " + fieldName + "} field. Note to mutate the value get by index then mutate in place*/\n");
                writer.append("        public " + fieldType + " " + fieldNameSanitised + "(int index) { return " + struct.getName() + ".n" + fieldNameSanitised + "(address(), index); }\n");
            }

        }

        writer.append("\n");

        // Generate setters for Buffer
        for (StructField field : struct.getFields()) {
            String fieldType = field.getJavaType();
            String fieldName = field.getName();

            if(field.getArraySizeConstant() != null && field.isStruct() && !field.isPointer()) {
                // no setter for arrays of structs
                continue;
            }

            String fieldNameSanitised = sanitiseFieldName(fieldName);

            writer.append("        /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
            writer.append("        public Buffer " + fieldNameSanitised + "(" + fieldType + " value) { \n");
            if(field.isEnumType()){
                writer.append("            " + struct.getName() + ".n" + fieldNameSanitised + "(address(), value.getValue());\n");
            } else if(field.isHandle() && !field.isPointer()){
                writer.append("            " + struct.getName() + ".n" + fieldNameSanitised + "(address(), value.getRawHandle());\n");
            } else{
                writer.append("            " + struct.getName() + ".n" + fieldNameSanitised + "(address(), value);\n");
            }
            writer.append("            return this;\n");
            writer.append("        }\n");
        }

        // Add type$Default method for Buffer if the struct has a type field
        if (hasTypeField && struct.canBeItsOwnDefault()) {
            String typeConstant = StructParser.createXrStructureTypeEnumValueForStruct(struct.getName());
            writer.append("        /** Sets the specified value to the {@code type} field. */\n");
            writer.append("        public Buffer type$Default() { return type(XrStructureType." + typeConstant + "); }\n");
        }

        writer.append("    }\n");
        writer.append("}\n");

        return writer.toString();
    }

    private static String createSetMethod(StructDefinition struct) {
        StringBuilder writer = new StringBuilder();

        writer.append("    /** Initializes this struct with the specified values. */\n");
        writer.append("    public " + struct.getName() + " set(\n");

        StringBuilder setParamsBuilder = new StringBuilder();
        StringBuilder setBodyBuilder = new StringBuilder();


        for (int i = 0; i< struct.getFields().size(); i++ ) {
            StructField field = struct.getFields().get(i);
            String fieldJavaType = field.getJavaType();
            String fieldName = field.getName();

            String fieldNameSanitised = sanitiseFieldName(fieldName);

            setParamsBuilder.append("        " + fieldJavaType).append(" ").append(fieldName);

            if (i < struct.getFields().size() - 1) {
                setParamsBuilder.append(",\n");
            } else {
                setParamsBuilder.append("\n");
            }

            setBodyBuilder.append("        ").append(fieldNameSanitised).append("(").append(fieldName).append(");\n");
        }

        writer.append(setParamsBuilder);
        writer.append("    ) {\n");
        writer.append(setBodyBuilder);
        writer.append("\n");
        writer.append("        return this;\n");
        writer.append("    }\n\n");

        return writer.toString();
    }

    private static void generateFieldGetter(StructDefinition struct, StructField field, StringBuilder writer) {
        String fieldType = field.getType();

        String javaType = field.getJavaType();
        String fieldName = field.getName();
        String fieldNameSanitised = sanitiseFieldName(fieldName);

        writer.append("    /** Returns the value of the {@code " + fieldName + "} field. */\n");

        writer.append("    public " + javaType + " " + fieldNameSanitised + "() {\n");

        if(field.isEnumType()){
            writer.append("        return " + fieldType + ".fromValue(" + struct.getName() + ".n" + fieldName + "(address()));\n");
        } else if(field.isHandle() && !field.isPointer()) {
            writer.append("        return new " + javaType + "(" + struct.getName() + ".n" + fieldName + "(address()));\n");
        } else {
            writer.append("        return n" + fieldNameSanitised + "(address());\n");
        }
        writer.append("    }\n");

        if(field.getArraySizeConstant() != null && field.isStruct() && !field.isPointer()) {
            // bonus getter by index if an array of structs
            writer.append("    /** Returns the value of the index-th item in the {@code " + fieldName + "} field. Note to mutate the value get by index then mutate in place*/\n");
            writer.append("    public " + fieldType + " " + fieldNameSanitised + "(int index) { return " + struct.getName() + ".n" + fieldNameSanitised + "(address(), index); }\n");
        }
    }

    private static String generateUnsafeGetterAndSetter(StructDefinition struct, StructField field) {
        StringBuilder writer = new StringBuilder();
        String fieldName = field.getName();
        // if the method name clashes with a struct method add a $ to the end
        String fieldNameSanitised = sanitiseFieldName(fieldName);
        String fieldType = field.getType();
        String javaType = field.getJavaType();
        String fieldNameUpper = fieldName.toUpperCase();

        writer.append("    /** Unsafe version of " + fieldName + "}. */\n");

        if(field.isStruct()) {
            if(field.isPointer()){
                if(field.isSingletonStructPointer()){
                    writer.append("    public static " + fieldType + " n" + fieldNameSanitised + "(long struct) {\n");
                    writer.append("        return " + fieldType + ".createSafe(memGetAddress(struct + " + struct.getName() + "." + fieldNameUpper + "));\n");
                    writer.append("    }\n");

                    writer.append("    public static void n" + fieldNameSanitised + "(long struct, " + javaType + " value){\n");
                    writer.append("        long address = value == null ? NULL : value.address();\n");
                    writer.append("        memPutAddress(struct + " + fieldNameUpper + ", address);\n");
                    writer.append("    }\n");
                }else {
                    String countMethodName = struct.findCountParameterForPointerField(field.getName()).map(f -> "n" + f).orElse(null);
                    if (countMethodName == null) {
                         throw new RuntimeException("Couldn't find a count method for " + fieldName + " in " + struct.getName());
                    } else {
                        writer.append("    public static " + javaType + " n" + fieldNameSanitised + "(long struct) {\n");
                        writer.append("        int count = " + countMethodName + "(struct);\n");
                        writer.append("        return " + fieldType + ".createSafe(memGetAddress(struct + " + struct.getName() + "." + fieldNameUpper + "),count);\n");
                        writer.append("    }\n");

                        writer.append("    public static void n" + fieldNameSanitised + "(long struct, " + javaType + " value){\n");
                        writer.append("        long address = value == null ? NULL : value.address();\n");
                        writer.append("        memPutAddress(struct + " + fieldNameUpper + ", address);\n");
                        writer.append("        if(value!=null){\n");
                        writer.append("            " + countMethodName + "(struct, value.remaining());\n");
                        writer.append("        }\n");
                        writer.append("    }\n");
                    }
                }
            }else {
                if(field.getArraySizeConstant() != null) {
                    // Arrays of structs don't get setters. Setters are done by getting then mutating

                    writer.append("    public static " + javaType + " n" + fieldNameSanitised + "(long struct) { return " + fieldType + ".create(struct + " + struct.getName() + "." + fieldNameUpper + ", " + field.getArraySizeConstant() + "); }\n");

                    // add a by index access
                    writer.append("    public static " + fieldType + " n" + fieldNameSanitised + "(long struct, int index) {\n");
                    writer.append("        if(index > " + field.getArraySizeConstant() + ") throw new IllegalArgumentException(\"index out of bounds: \" + index);\n");
                    writer.append("        return " + fieldType + ".create(struct + " + struct.getName() + "." + fieldNameUpper + " + (index * " + fieldType + ".SIZEOF));\n");
                    writer.append("    }\n");
                }else {
                    writer.append("    public static " + javaType + " n" + fieldNameSanitised + "(long struct) { return " + fieldType + ".create(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                    writer.append("    public static void n" + fieldNameSanitised + "(long struct, " + javaType + " value) { memCopy(value.address(), struct +" + struct.getName() + "." + fieldNameUpper + "," + javaType + ".SIZEOF); }\n");
                }
            }
        } else if (javaType.equals("ByteBuffer")) {
            writer.append("    public static ByteBuffer n" + fieldNameSanitised + "(long struct) { return memByteBuffer(struct + " + struct.getName() + "." + fieldNameUpper + ", " + field.getArraySizeConstant() + "); }\n");
            writer.append("    /** Unsafe version of " + fieldNameSanitised + ". */\n");
            writer.append("    public static String n" + fieldNameSanitised + "String(long struct) { return memUTF8(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");

            if(field.getArraySizeConstant() != null) {
                writer.append("    /** max length " + field.getArraySizeConstant() + " */\n");
            }

            writer.append("    public static void n" + fieldNameSanitised + "(long struct, ByteBuffer value) {\n");
            if(field.getArraySizeConstant() != null) {
                writer.append("        byteBufferLengthCheck(value," + field.getArraySizeConstant() + ");\n");
            }

            writer.append("        memCopy(memAddress(value), struct + " + struct.getName() + "." + fieldNameUpper + ", value.remaining());\n");
            writer.append("    }\n");
        }else {
            String accessMethod = field.getMemoryAccessMethod();
            String setMethod = field.getMemorySetMethod();

            if (field.isEnumType()) {
                writer.append("    public static int n" + fieldNameSanitised + "(long struct) { return " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                writer.append("    public static void n" + fieldNameSanitised + "(long struct, int value ) { " + setMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + ", value); }\n");
            } else if (field.isHandle()) {
                if(field.isPointer()){

                    String countMethodName = struct.findCountParameterForPointerField(field.getName()).map(f -> "n" + f).orElse(null);
                    if (countMethodName == null) {
                        throw new RuntimeException("Couldn't find a count method for " + fieldName + " in " + struct.getName());
                    }

                    writer.append("    public static " + javaType + " n" + fieldNameSanitised + "(long struct) { \n");
                    writer.append("        long address = " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + ");\n");
                    writer.append("        int count = " + countMethodName + "(struct);\n");
                    writer.append("        ByteBuffer buffer = memByteBuffer(address, count * Long.BYTES);\n");
                    writer.append("        return " + fieldType + ".create(buffer, address); \n");
                    writer.append("    }\n");

                    writer.append("    public static void n" + fieldNameSanitised + "(long struct, " + javaType + " value ) {\n");
                    writer.append("        " + setMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + ", value.address());\n");
                    writer.append("        " + countMethodName + "(struct + " + struct.getName() + "." + fieldNameUpper + ", value.capacity());\n");
                    writer.append("    }\n");
                }else {
                    writer.append("    public static long n" + fieldNameSanitised + "(long struct) { return " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                    writer.append("    public static void n" + fieldNameSanitised + "(long struct, long value ) { " + setMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + ", value); }\n");
                }
            }  else {
                writer.append("    public static " + javaType + " n" + fieldNameSanitised + "(long struct) { return " + accessMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                writer.append("    public static void n" + fieldNameSanitised + "(long struct, " + javaType  + " value) { " + setMethod + "(struct + " + struct.getName() + "." + fieldNameUpper + ", value); }\n");
            }
        }

        return writer.toString();
    }

    private static String sanitiseFieldName(String fieldName) {
        if(fieldName.equals("position")){
            return fieldName + "$";
        }else{
            return fieldName;
        }
    }


    private static String generateFieldSetter(StructDefinition struct, StructField field) {
        StringBuilder writer = new StringBuilder();
        String fieldName = field.getName();
        String fieldNameUpper = fieldName.toUpperCase();
        String fieldType = field.getJavaType();
        String fieldNameSanitised = sanitiseFieldName(fieldName);

        if(field.getArraySizeConstant() != null && field.isStruct() && !field.isPointer()) {
            // no setter for arrays of structs
            return "";
        }

        writer.append("    /** Sets the specified value to the {@code " + fieldName + "} field. */\n");
        writer.append("    public " + struct.getName() + " " + fieldNameSanitised + "(" + fieldType + " value) { \n");

        if(field.isEnumType()){
            writer.append("        " + struct.getName() + ".n"+fieldNameSanitised+"(address(), value.getValue());\n");
        } else if(field.isHandle() && !field.isPointer()){
            writer.append("        " + struct.getName() + ".n"+fieldNameSanitised+"(address(), value.getRawHandle());\n");
        } else{
            writer.append("        " + struct.getName() + ".n"+fieldNameSanitised+"(address(), value);\n");
        }

        writer.append("        return this;\n");
        writer.append("    }\n");

        return writer.toString();
    }


}
