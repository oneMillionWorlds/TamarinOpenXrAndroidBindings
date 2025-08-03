package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Generator for enum classes.
 */
public class EnumGenerator extends FileGenerator {
    private final EnumDefinition enumDef;

    public EnumGenerator(Logger logger, EnumDefinition enumDef) {
        super(logger);
        this.enumDef = enumDef;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        // Create the enums directory if it doesn't exist
        File enumsDir = new File(outputDir, "enums");
        if (!enumsDir.exists()) {
            enumsDir.mkdirs();
        }

        // Generate the enum class
        String enumName = enumDef.getName();
        File outputFile = new File(enumsDir, enumName + ".java");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write("/*\n");
            writer.write(" * OpenXR Java bindings for Android\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n");
            writer.write("package com.onemillionworlds.tamarin.openxrbindings.enums;\n\n");

            writer.write("/**\n");
            writer.write(" * Enum for " + enumName + ".\n");
            writer.write(" */\n");
            writer.write("public enum " + enumName + " {\n");

            // Write enum values
            for (int i = 0; i < enumDef.getValues().size(); i++) {
                EnumDefinition.EnumValue value = enumDef.getValues().get(i);
                writer.write("    /** " + value.getName() + " */\n");
                writer.write("    " + formatEnumValueName(value.getName()) + "(" + value.getValue() + ")");
                
                if (i < enumDef.getValues().size() - 1) {
                    writer.write(",\n\n");
                } else {
                    writer.write(";\n\n");
                }
            }

            // Write value field and constructor
            writer.write("    private final int value;\n\n");
            writer.write("    " + enumName + "(int value) {\n");
            writer.write("        this.value = value;\n");
            writer.write("    }\n\n");

            // Write getValue method
            writer.write("    /**\n");
            writer.write("     * Returns the numeric value of this enum.\n");
            writer.write("     *\n");
            writer.write("     * @return the numeric value\n");
            writer.write("     */\n");
            writer.write("    public int getValue() {\n");
            writer.write("        return value;\n");
            writer.write("    }\n\n");

            // Write fromValue method
            writer.write("    /**\n");
            writer.write("     * Returns the enum value for the given numeric value.\n");
            writer.write("     *\n");
            writer.write("     * @param value the numeric value\n");
            writer.write("     * @return the enum value, or null if not found\n");
            writer.write("     */\n");
            writer.write("    public static " + enumName + " fromValue(int value) {\n");
            writer.write("        for (" + enumName + " e : values()) {\n");
            writer.write("            if (e.value == value) {\n");
            writer.write("                return e;\n");
            writer.write("            }\n");
            writer.write("        }\n");
            writer.write("        return null;\n");
            writer.write("    }\n");

            writer.write("}\n");
        }

        logGeneration("enums/" + enumName + ".java");
    }

    /**
     * Formats an enum value name to follow Java enum naming conventions.
     * For example, "XR_RESULT_MAX_ENUM" -> "MAX_ENUM"
     */
    private String formatEnumValueName(String name) {
        // Remove the prefix (e.g., "XR_RESULT_")
        String prefix = enumDef.getName().toUpperCase() + "_";
        if (name.startsWith("XR_") && name.contains(prefix.substring(3))) {
            return name.substring(name.indexOf(prefix.substring(3)) + prefix.substring(3).length());
        } else if (name.startsWith("XR_")) {
            return name.substring(3);
        }
        return name;
    }
}