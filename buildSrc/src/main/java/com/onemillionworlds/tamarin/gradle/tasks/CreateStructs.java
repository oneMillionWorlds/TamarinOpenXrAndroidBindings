package com.onemillionworlds.tamarin.gradle.tasks;

import com.onemillionworlds.tamarin.gradle.tasks.generators.ConstantsGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.EnumGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.StructGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10Generator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10CGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.AtomParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.DefinePasser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.EnumParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.FunctionParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.StructParser;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private final RegularFileProperty cOutputDir = getProject().getObjects().fileProperty();

    @InputFile
    public RegularFileProperty getHeaderFile() {
        return headerFile;
    }

    @OutputDirectory
    public RegularFileProperty getOutputDir() {
        return outputDir;
    }

    @OutputDirectory
    public RegularFileProperty getcOutputDir() {
        return cOutputDir;
    }

    @TaskAction
    public void execute() throws IOException {
        File header = headerFile.getAsFile().get();
        File output = outputDir.getAsFile().get();
        File cOutput = null;
        if (cOutputDir.isPresent()) {
            cOutput = cOutputDir.getAsFile().get();
            if (!cOutput.exists()) {
                cOutput.mkdirs();
            }
        }

        if (!output.exists()) {
            output.mkdirs();
        }

        getLogger().lifecycle("Parsing header file: {}", header.getAbsolutePath());
        getLogger().lifecycle("Output directory: {}", output.getAbsolutePath());
        if (cOutput != null) {
            getLogger().lifecycle("Generated C output directory: {}", cOutput.getAbsolutePath());
        }

        // Parse the header file
        Map<String, String> constants = new LinkedHashMap<>();
        List<StructDefinition> structs = new ArrayList<>();
        List<EnumDefinition> enums = new ArrayList<>();
        List<FunctionDefinition> functions = new ArrayList<>();
        Map<String, String> typedefs = new HashMap<>();
        List<String> atoms = new ArrayList<>();

        parseHeaderFile(header, constants, structs, enums, functions, typedefs, atoms);

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

        // Generate X10.java with method pairs
        new X10Generator(getLogger(), functions).generate(output);

        // Generate C JNI implementation file
        if (cOutput != null) {
            new X10CGenerator(getLogger(), functions).generate(cOutput);
        }
    }

    private void parseHeaderFile(File headerFile, Map<String, String> constants, List<StructDefinition> structs, List<EnumDefinition> enums, List<FunctionDefinition> functions, Map<String, String> typedefs, List<String> atoms) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(headerFile))) {
            String line;

            // Pattern for simple typedefs like "typedef int64_t XrTime;"
            Pattern simpleTypedefPattern = Pattern.compile("typedef\\s+(\\w+)\\s+(Xr[A-Za-z]+);");

            // Pattern for function declarations like "XRAPI_ATTR XrResult XRAPI_CALL xrFunctionName(parameters...);"
            Pattern functionPattern = Pattern.compile("XRAPI_ATTR\\s+(\\w+)\\s+XRAPI_CALL\\s+(xr\\w+)\\s*\\(([^\\)]*)\\);");

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

                if(EnumParser.enumStartPattern.matcher(line).find()) {
                    enums.add(EnumParser.parseEnum(reader, line));
                }
                // Check for function declarations
                Matcher functionMatcher = FunctionParser.functionStartPattern.matcher(line);
                if (functionMatcher.find()) {

                    List<String> knownEnums = enums.stream().map(EnumDefinition::getName).toList();

                    FunctionDefinition functionDefinition = FunctionParser.parseFunction(reader, line, knownEnums, atoms);

                    functions.add(functionDefinition);
                    getLogger().lifecycle("Found function: {}", functionDefinition.getName());
                }

                // Parse #define constants
                Matcher defineMatcher = DefinePasser.definePattern.matcher(line);
                if (defineMatcher.find()) {
                    DefinePasser.parseDefine(line).ifPresent(define -> {
                        String name = define.constantName;
                        String value = define.constantValue;
                        constants.put(name, value);
                    });
                }

                // Parse atom definitions
                AtomParser.parseAtom(line).ifPresent(atoms::add);

                if(StructParser.structStartPattern.matcher(line).find()) {
                    structs.add(StructParser.parseStruct(reader, line));
                }
            }
        }
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
