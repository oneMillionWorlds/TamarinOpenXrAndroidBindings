package com.onemillionworlds.tamarin.gradle.tasks;

import com.onemillionworlds.tamarin.gradle.tasks.generators.ConstantsGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.EnumGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.StructGenerator;
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
        List<EnumDefinition> enums = new ArrayList<>();
        Map<String, String> typedefs = new HashMap<>();

        parseHeaderFile(header, constants, structs, enums, typedefs);

        // Generate XR10Constants.java
        new ConstantsGenerator(getLogger(), constants).generate(output);

        // Generate struct classes
        for (StructDefinition struct : structs) {
            new StructGenerator(getLogger(), struct, constants, enums, typedefs).generate(output);
        }

        // Generate enum classes
        for (EnumDefinition enumDef : enums) {
            new EnumGenerator(getLogger(), enumDef).generate(output);
        }
    }

    private void parseHeaderFile(File headerFile, Map<String, String> constants, List<StructDefinition> structs, List<EnumDefinition> enums, Map<String, String> typedefs) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(headerFile))) {
            String line;
            StringBuilder structBuilder = null;
            StringBuilder enumBuilder = null;
            String currentStructName = null;
            String currentEnumName = null;
            boolean inEnum = false;

            // Regex patterns
            Pattern definePattern = Pattern.compile("#define\\s+(XR_[A-Z_]+)\\s+(.+)");
            Pattern structStartPattern = Pattern.compile("typedef\\s+struct\\s+(Xr[A-Za-z]+)\\s+\\{");
            Pattern structEndPattern = Pattern.compile("\\}\\s+(Xr[A-Za-z]+);");
            Pattern structFieldPattern = Pattern.compile("\\s*(\\w+(?:\\*\\s*XR_MAY_ALIAS)?)\\s+(\\w+)(?:\\[(XR_[A-Z_]+)\\])?;");

            // Pattern for simple typedefs like "typedef int64_t XrTime;"
            Pattern simpleTypedefPattern = Pattern.compile("typedef\\s+(\\w+)\\s+(Xr[A-Za-z]+);");

            // Patterns for all enum parsing (including XrStructureType)
            // This pattern handles both named enums (typedef enum XrName {) and unnamed enums (typedef enum {)
            Pattern enumStartPattern = Pattern.compile("typedef\\s+enum\\s+(?:(Xr[A-Za-z]+)\\s+)?\\{");
            Pattern enumEndPattern = Pattern.compile("\\}\\s+(Xr[A-Za-z]+);");
            Pattern enumValuePattern = Pattern.compile("\\s*(XR_[A-Z0-9_]+)\\s*=\\s*([^,]+),?");

            while ((line = reader.readLine()) != null) {
                // Check for simple typedefs like "typedef int64_t XrTime;"
                Matcher simpleTypedefMatcher = simpleTypedefPattern.matcher(line);
                if (simpleTypedefMatcher.find()) {
                    String baseType = simpleTypedefMatcher.group(1);
                    String typedefName = simpleTypedefMatcher.group(2);
                    typedefs.put(typedefName, baseType);
                    getLogger().lifecycle("Found typedef: {} -> {}", typedefName, baseType);
                    continue;
                }

                // Check if we're entering an enum
                if (!inEnum) {
                    Matcher enumStartMatcher = enumStartPattern.matcher(line);
                    if (enumStartMatcher.find()) {
                        inEnum = true;
                        // Group 1 might be null if this is an unnamed enum like "typedef enum {"
                        // In that case, we'll get the name from the end pattern (e.g., "} XrStructureType;")
                        currentEnumName = enumStartMatcher.group(1);
                        enumBuilder = new StringBuilder();
                        enumBuilder.append(line).append("\n");

                        // Create a new enum definition
                        // If currentEnumName is null, we'll update it when we find the end of the enum
                        if (currentEnumName != null) {
                            EnumDefinition enumDef = new EnumDefinition(currentEnumName);
                            enums.add(enumDef);
                        }
                        continue;
                    }
                }

                // If we're inside an enum, extract the values
                if (inEnum) {
                    enumBuilder.append(line).append("\n");

                    Matcher enumEndMatcher = enumEndPattern.matcher(line);
                    if (enumEndMatcher.find()) {
                        // If we didn't get the enum name from the start pattern, get it from the end pattern
                        if (currentEnumName == null) {
                            currentEnumName = enumEndMatcher.group(1);
                            // Create the enum definition now that we have the name
                            EnumDefinition enumDef = new EnumDefinition(currentEnumName);
                            enums.add(enumDef);
                        }

                        inEnum = false;
                        currentEnumName = null;
                        enumBuilder = null;
                        continue;
                    }

                    Matcher enumValueMatcher = enumValuePattern.matcher(line);
                    if (enumValueMatcher.find()) {
                        String name = enumValueMatcher.group(1);
                        String value = enumValueMatcher.group(2).trim();

                        // Find the enum definition (if it exists)
                        if (!enums.isEmpty()) {
                            EnumDefinition enumDef = enums.get(enums.size() - 1);

                            // Check if the value is a reference to another enum value
                            if (value.startsWith("XR_") && !value.contains("(") && !value.contains("+") && !value.contains("-") && !value.contains("*") && !value.contains("/")) {
                                // Look up the referenced value in the current enum
                                String resolvedValue = null;
                                for (EnumDefinition.EnumValue existingValue : enumDef.getValues()) {
                                    if (existingValue.getName().equals(value)) {
                                        resolvedValue = existingValue.getValue();
                                        break;
                                    }
                                }

                                // If we found the referenced value, use it
                                if (resolvedValue != null) {
                                    value = resolvedValue;
                                }
                            }

                            // Add the enum value
                            enumDef.addValue(new EnumDefinition.EnumValue(name, value));
                        }
                    }
                }

                // Parse #define constants
                Matcher defineMatcher = definePattern.matcher(line);
                if (defineMatcher.find()) {
                    String name = defineMatcher.group(1);
                    String value = defineMatcher.group(2).trim();

                    // Only process constants we're interested in (size constants, etc.)
                    if (name.startsWith("XR_MAX_") || name.endsWith("_SIZE_EXT")) {
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

    /**
     * Gets the size in bytes for a given type.
     */
    private String getSizeForType(String type) {
        if (type.equals("char")) return "1";
        if (type.equals("float")) return "4";
        if (type.equals("double")) return "8";
        if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32")) return "4";
        if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion")) return "8";
        return "4"; // Default size
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


    /**
     * Class representing a struct definition.
     */
    public static class StructDefinition {
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
    public static class StructField {
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
