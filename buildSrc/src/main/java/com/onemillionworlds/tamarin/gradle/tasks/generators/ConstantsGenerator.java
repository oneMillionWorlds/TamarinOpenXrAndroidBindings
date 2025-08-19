package com.onemillionworlds.tamarin.gradle.tasks.generators;

import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Generator for XR10Constants.java file.
 */
public class ConstantsGenerator extends FileGenerator {
    private final Map<String, String> constants;

    public ConstantsGenerator(Logger logger, Map<String, String> constants) {
        super(logger);
        this.constants = constants;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, "XR10Constants.java");

        try (BufferedWriter writer = createWriter(outputFile)) {
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
                if(entry.getValue().contains("\"")){
                    writer.write("    public static final String " + entry.getKey() + " = " + entry.getValue() + ";\n");
                }else{
                    writer.write("    public static final int " + entry.getKey() + " = " + entry.getValue() + ";\n");
                }
            }
            writer.write("    \n");
            writer.write("    // Pointer size\n");
            writer.write("    public static final int POINTER_SIZE = 8; // 64-bit\n");
            writer.write("}\n");
        }

        logGeneration("XR10Constants.java");
    }
}