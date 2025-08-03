package com.onemillionworlds.tamarin.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gradle task to parse OpenXR header files and generate Java classes for constants and structs.
 */
public class CreateStructs extends DefaultTask {

    private final RegularFileProperty headerFile = getProject().getObjects().fileProperty();
    private final RegularFileProperty outputDir = getProject().getObjects().fileProperty();

    @InputFile
    public RegularFileProperty getHeaderFile() {
        return headerFile;
    }

    @OutputDirectory
    public RegularFileProperty getOutputDir() {
        return outputDir;
    }

    @TaskAction
    public void execute() throws IOException {
        File header = headerFile.getAsFile().get();
        File output = outputDir.getAsFile().get();

        if (!output.exists()) {
            output.mkdirs();
        }

        getLogger().lifecycle("Parsing header file: {}", header.getAbsolutePath());
        getLogger().lifecycle("Output directory: {}", output.getAbsolutePath());

        // Parse the header file
        Map<String, String> constants = new LinkedHashMap<>();
        List<StructDefinition> structs = new ArrayList<>();

        parseHeaderFile(header, constants, structs);

        // Generate XR10Constants.java
        generateConstantsClass(output, constants);

        // Generate struct classes
        for (StructDefinition struct : structs) {
            generateStructClass(output, struct, constants);
        }
    }

    private void parseHeaderFile(File headerFile, Map<String, String> constants, List<StructDefinition> structs) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(headerFile))) {
            String line;
            StringBuilder structBuilder = null;
            String currentStructName = null;
            boolean inStructureTypeEnum = false;

            // Regex patterns
            Pattern definePattern = Pattern.compile("#define\\s+(XR_[A-Z_]+)\\s+(.+)");
            Pattern structStartPattern = Pattern.compile("typedef\\s+struct\\s+(Xr[A-Za-z]+)\\s+\\{");
            Pattern structEndPattern = Pattern.compile("\\}\\s+(Xr[A-Za-z]+);");
            Pattern structFieldPattern = Pattern.compile("\\s*(\\w+(?:\\*\\s*XR_MAY_ALIAS)?)\\s+(\\w+)(?:\\[(XR_[A-Z_]+)\\])?;");
            Pattern enumStartPattern = Pattern.compile("typedef\\s+enum\\s+XrStructureType\\s+\\{");
            Pattern enumEndPattern = Pattern.compile("\\}\\s+XrStructureType;");
            Pattern enumValuePattern = Pattern.compile("\\s*(XR_TYPE_[A-Z0-9_]+)\\s*=\\s*([^,]+),?");

            while ((line = reader.readLine()) != null) {
                // Check if we're entering the XrStructureType enum
                if (!inStructureTypeEnum) {
                    Matcher enumStartMatcher = enumStartPattern.matcher(line);
                    if (enumStartMatcher.find()) {
                        inStructureTypeEnum = true;
                        continue;
                    }
                }

                // If we're inside the XrStructureType enum, extract the values
                if (inStructureTypeEnum) {
                    Matcher enumEndMatcher = enumEndPattern.matcher(line);
                    if (enumEndMatcher.find()) {
                        inStructureTypeEnum = false;
                        continue;
                    }

                    Matcher enumValueMatcher = enumValuePattern.matcher(line);
                    if (enumValueMatcher.find()) {
                        String name = enumValueMatcher.group(1);
                        String value = enumValueMatcher.group(2).trim();

                        // Try to convert the value to an integer if possible
                        try {
                            // Handle hexadecimal values
                            if (value.startsWith("0x")) {
                                int intValue = Integer.parseInt(value.substring(2), 16);
                                constants.put(name, String.valueOf(intValue));
                            } else {
                                int intValue = Integer.parseInt(value);
                                constants.put(name, String.valueOf(intValue));
                            }
                        } catch (NumberFormatException e) {
                            // If it's not a simple integer, store the raw value
                            constants.put(name, value);
                        }
                    }
                }

                // Parse #define constants
                Matcher defineMatcher = definePattern.matcher(line);
                if (defineMatcher.find()) {
                    String name = defineMatcher.group(1);
                    String value = defineMatcher.group(2).trim();

                    // Only process constants we're interested in (size constants, etc.)
                    if (name.startsWith("XR_MAX_")) {
                        // Special case for XR_MAX_EVENT_DATA_SIZE
                        if (name.equals("XR_MAX_EVENT_DATA_SIZE")) {
                            // Use a fixed value for this constant
                            constants.put(name, "4000"); // Size of XrEventDataBuffer.varying
                        } else {
                            // Try to convert the value to an integer if possible
                            try {
                                // Handle hexadecimal values
                                if (value.startsWith("0x")) {
                                    int intValue = Integer.parseInt(value.substring(2), 16);
                                    constants.put(name, String.valueOf(intValue));
                                } else {
                                    int intValue = Integer.parseInt(value);
                                    constants.put(name, String.valueOf(intValue));
                                }
                            } catch (NumberFormatException e) {
                                // If it's not a simple integer, store the raw value
                                constants.put(name, value);
                            }
                        }
                    }
                }

                // Start of struct definition
                Matcher structStartMatcher = structStartPattern.matcher(line);
                if (structStartMatcher.find()) {
                    currentStructName = structStartMatcher.group(1);
                    structBuilder = new StringBuilder();
                    structBuilder.append(line).append("\n");
                    continue;
                }

                // Inside struct definition
                if (structBuilder != null) {
                    structBuilder.append(line).append("\n");

                    // End of struct definition
                    Matcher structEndMatcher = structEndPattern.matcher(line);
                    if (structEndMatcher.find()) {
                        String structContent = structBuilder.toString();
                        StructDefinition structDef = parseStructDefinition(currentStructName, structContent);
                        structs.add(structDef);

                        structBuilder = null;
                        currentStructName = null;
                    }
                }
            }
        }
    }

    private StructDefinition parseStructDefinition(String name, String content) {
        StructDefinition structDef = new StructDefinition(name);

        // Parse fields
        Pattern fieldPattern = Pattern.compile("\\s*((?:const\\s+)?\\w+(?:\\s*\\*(?:\\s*XR_MAY_ALIAS)?)?(?:\\s+const)?)\\s+(\\w+)(?:\\[(XR_[A-Z_]+)\\])?;");
        Matcher matcher = fieldPattern.matcher(content);

        while (matcher.find()) {
            String type = matcher.group(1);
            String fieldName = matcher.group(2);
            String arraySizeConstant = matcher.group(3); // May be null

            StructField field = new StructField(type, fieldName, arraySizeConstant);
            structDef.addField(field);
        }

        return structDef;
    }

    private void generateConstantsClass(File outputDir, Map<String, String> constants) throws IOException {
        File outputFile = new File(outputDir, "XR10Constants.java");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("/**\n");
            writer.write(" * Constants for OpenXR 1.0.\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n");
            writer.write("package com.onemillionworlds.tamarin.openxrbindings;\n\n");
            writer.write("/** @noinspection unused*/\n");
            writer.write("public class XR10Constants {\n");

            // Write structure types
            writer.write("    // Structure types\n");
            for (Map.Entry<String, String> entry : constants.entrySet()) {
                if (entry.getKey().startsWith("XR_TYPE_")) {
                    writer.write("    public static final int " + entry.getKey() + " = " + entry.getValue() + ";\n");
                }
            }

            writer.write("    \n");
            writer.write("    // Constants\n");
            for (Map.Entry<String, String> entry : constants.entrySet()) {
                if (entry.getKey().startsWith("XR_MAX_")) {
                    writer.write("    public static final int " + entry.getKey() + " = " + entry.getValue() + ";\n");
                }
            }

            writer.write("    \n");
            writer.write("    // Pointer size\n");
            writer.write("    public static final int POINTER_SIZE = 8; // 64-bit\n");
            writer.write("}\n");
        }

        getLogger().lifecycle("Generated XR10Constants.java");
    }

    private void generateStructClass(File outputDir, StructDefinition struct, Map<String, String> constants) throws IOException {
        File outputFile = new File(outputDir, struct.getName() + ".java");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("/*\n");
            writer.write(" * OpenXR Java bindings for Android\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n");
            writer.write("package com.onemillionworlds.tamarin.openxrbindings;\n\n");

            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;\n\n");
            writer.write("import java.nio.ByteBuffer;\n\n");
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
            writer.write(" */\n");
            writer.write("public class " + struct.getName() + " extends Struct<" + struct.getName() + "> {\n\n");

            // Write struct size and alignment
            writer.write("    /** The struct size in bytes. */\n");
            writer.write("    public static final int SIZEOF;\n\n");
            writer.write("    /** The struct alignment in bytes. */\n");
            writer.write("    public static final int ALIGNOF;\n\n");

            // Write field offsets
            writer.write("    /** The struct member offsets. */\n");
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

            // Write static initializer
            writer.write("    static {\n");
            writer.write("        Layout layout = Layout.__struct(\n");

            StringBuilder layoutBuilder = new StringBuilder();
            for (int i = 0; i < struct.getFields().size(); i++) {
                StructField field = struct.getFields().get(i);
                String fieldType = field.getType();
                String arraySizeConstant = field.getArraySizeConstant();

                layoutBuilder.append("            ");

                if (arraySizeConstant != null) {
                    layoutBuilder.append("Layout.__array(1, ").append(arraySizeConstant).append(")");
                } else if (fieldType.equals("XrStructureType")) {
                    layoutBuilder.append("Layout.__member(4)");
                } else if (fieldType.contains("*")) {
                    layoutBuilder.append("Layout.__member(POINTER_SIZE)");
                } else if (fieldType.equals("uint32_t")) {
                    layoutBuilder.append("Layout.__member(4)");
                } else if (fieldType.equals("int32_t")) {
                    layoutBuilder.append("Layout.__member(4)");
                } else if (fieldType.equals("uint64_t")) {
                    layoutBuilder.append("Layout.__member(8)");
                } else if (fieldType.equals("int64_t")) {
                    layoutBuilder.append("Layout.__member(8)");
                } else if (fieldType.equals("float")) {
                    layoutBuilder.append("Layout.__member(4)");
                } else if (fieldType.equals("double")) {
                    layoutBuilder.append("Layout.__member(8)");
                } else if (fieldType.equals("XrBool32")) {
                    layoutBuilder.append("Layout.__member(4)");
                } else if (fieldType.equals("XrVersion")) {
                    layoutBuilder.append("Layout.__member(8)");
                } else if (fieldType.equals("char")) {
                    layoutBuilder.append("Layout.__member(1)");
                } else {
                    // For other types, assume it's a struct and use its size
                    layoutBuilder.append("Layout.__member(4)"); // Default to 4 bytes
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
                        writer.write("    public ByteBuffer " + fieldName + "() { return memByteBuffer(address() + " + fieldNameUpper + ", " + arraySizeConstant + " * " + getSizeForType(fieldType) + "); }\n");
                    }
                } else if (fieldType.equals("XrStructureType") || fieldType.equals("XrActionType")) {
                    writer.write("    public int " + fieldName + "() { return memGetInt(address() + " + fieldNameUpper + "); }\n");
                } else if (fieldType.contains("*")) {
                    writer.write("    public long " + fieldName + "() { return memGetAddress(address() + " + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("uint32_t") || fieldType.equals("int32_t") || fieldType.equals("XrBool32")) {
                    writer.write("    public int " + fieldName + "() { return memGetInt(address() + " + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("uint64_t") || fieldType.equals("int64_t") || fieldType.equals("XrVersion")) {
                    writer.write("    public long " + fieldName + "() { return memGetLong(address() + " + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("float")) {
                    writer.write("    public float " + fieldName + "() { return memGetFloat(address() + " + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("double")) {
                    writer.write("    public double " + fieldName + "() { return memGetDouble(address() + " + fieldNameUpper + "); }\n");
                } else {
                    // For other types, assume it's a struct
                    writer.write("    public " + fieldType + " " + fieldName + "() { return " + fieldType + ".create(address() + " + fieldNameUpper + "); }\n");
                }
            }
            writer.write("\n");

            // Generate setters for type and next fields
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
                }
            }

            // Add type$Default method if the struct has a type field
            boolean hasTypeField = struct.getFields().stream()
                    .anyMatch(field -> field.getName().equals("type"));
            if (hasTypeField) {
                String typeConstant = getTypeConstantForStruct(struct.getName());
                writer.write("    /** Sets the specified value to the {@code type} field. */\n");
                writer.write("    public " + struct.getName() + " type$Default() { return type(XR10Constants." + typeConstant + "); }\n");
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
                        writer.write("    public static ByteBuffer n" + fieldName + "(long struct) { return memByteBuffer(struct + " + struct.getName() + "." + fieldNameUpper + ", " + arraySizeConstant + " * " + getSizeForType(fieldType) + "); }\n");
                    }
                } else if (fieldType.equals("XrStructureType") || fieldType.equals("XrActionType")) {
                    writer.write("    public static int n" + fieldName + "(long struct) { return memGetInt(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.contains("*")) {
                    writer.write("    public static long n" + fieldName + "(long struct) { return memGetAddress(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("uint32_t") || fieldType.equals("int32_t") || fieldType.equals("XrBool32")) {
                    writer.write("    public static int n" + fieldName + "(long struct) { return memGetInt(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("uint64_t") || fieldType.equals("int64_t") || fieldType.equals("XrVersion")) {
                    writer.write("    public static long n" + fieldName + "(long struct) { return memGetLong(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("float")) {
                    writer.write("    public static float n" + fieldName + "(long struct) { return memGetFloat(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else if (fieldType.equals("double")) {
                    writer.write("    public static double n" + fieldName + "(long struct) { return memGetDouble(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                } else {
                    // For other types, assume it's a struct
                    writer.write("    public static " + fieldType + " n" + fieldName + "(long struct) { return " + fieldType + ".create(struct + " + struct.getName() + "." + fieldNameUpper + "); }\n");
                }
            }

            writer.write("\n");

            // Generate unsafe setters for type and next fields
            for (StructField field : struct.getFields()) {
                String fieldType = field.getType();
                String fieldName = field.getName();

                if (fieldName.equals("type")) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(int) " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, int value) { memPutInt(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
                } else if (fieldName.equals("next")) {
                    writer.write("    /** Unsafe version of {@link #" + fieldName + "(long) " + fieldName + "}. */\n");
                    writer.write("    public static void n" + fieldName + "(long struct, long value) { memPutAddress(struct + " + struct.getName() + "." + fieldName.toUpperCase() + ", value); }\n");
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
                } else if (fieldType.equals("XrStructureType") || fieldType.equals("XrActionType")) {
                    writer.write("        public int " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else if (fieldType.contains("*")) {
                    writer.write("        public long " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else if (fieldType.equals("uint32_t") || fieldType.equals("int32_t") || fieldType.equals("XrBool32")) {
                    writer.write("        public int " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else if (fieldType.equals("uint64_t") || fieldType.equals("int64_t") || fieldType.equals("XrVersion")) {
                    writer.write("        public long " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else if (fieldType.equals("float")) {
                    writer.write("        public float " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else if (fieldType.equals("double")) {
                    writer.write("        public double " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
                } else {
                    // For other types, assume it's a struct
                    writer.write("        public " + fieldType + " " + fieldName + "() { return " + struct.getName() + ".n" + fieldName + "(address()); }\n");
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
                }
            }

            // Add type$Default method for Buffer if the struct has a type field
            if (hasTypeField) {
                String typeConstant = getTypeConstantForStruct(struct.getName());
                writer.write("        /** Sets the specified value to the {@code type} field. */\n");
                writer.write("        public Buffer type$Default() { return type(XR10Constants." + typeConstant + "); }\n");
            }

            writer.write("    }\n");
            writer.write("}\n");
        }

        getLogger().lifecycle("Generated " + struct.getName() + ".java");
    }

    /**
     * Converts a struct name to its corresponding type constant.
     * For example, "XrExtensionProperties" -> "XR_TYPE_EXTENSION_PROPERTIES"
     */
    private String getTypeConstantForStruct(String structName) {
        // Convert camel case to underscore format for the type constant
        String name = structName.substring(2); // Remove "Xr" prefix
        StringBuilder typeConstantBuilder = new StringBuilder("XR_TYPE_");
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                typeConstantBuilder.append('_');
            }
            typeConstantBuilder.append(Character.toUpperCase(c));
        }
        return typeConstantBuilder.toString();
    }

    private String getSizeForType(String type) {
        if (type.equals("char")) return "1";
        if (type.equals("float")) return "4";
        if (type.equals("double")) return "8";
        if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32")) return "4";
        if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion")) return "8";
        return "4"; // Default size
    }

    /**
     * Class representing a struct definition.
     */
    private static class StructDefinition {
        private final String name;
        private final List<StructField> fields = new ArrayList<>();

        public StructDefinition(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addField(StructField field) {
            fields.add(field);
        }

        public List<StructField> getFields() {
            return fields;
        }
    }

    /**
     * Class representing a struct field.
     */
    private static class StructField {
        private final String type;
        private final String name;
        private final String arraySizeConstant;

        public StructField(String type, String name, String arraySizeConstant) {
            this.type = type;
            this.name = name;
            this.arraySizeConstant = arraySizeConstant;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getArraySizeConstant() {
            return arraySizeConstant;
        }
    }
}
