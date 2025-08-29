package com.onemillionworlds.tamarin.gradle.tasks.generators;

import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Generator for handle classes.
 */
public class HandleGenerator extends FileGenerator {
    private final String handleName;

    public HandleGenerator(Logger logger, String handleName) {
        super(logger);
        this.handleName = handleName;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        // Create the appropriate directory for handles
        File targetDir = new File(outputDir, "handles");
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File outputFile = new File(targetDir, handleName + ".java");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write(generateHandle(handleName));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to generate handle " + handleName + " to file " + outputFile.getAbsolutePath(), e);
        }

        logGeneration("handles/" + handleName + ".java");
    }

    /**
     * Generates Java code for a handle class.
     *
     * @param handleName The name of the handle.
     * @return The generated Java code.
     */
    public static String generateHandle(String handleName) {
        StringBuilder writer = new StringBuilder();
        
        writer.append("/*\n");
        writer.append(" * OpenXR Java bindings for Android\n");
        writer.append(" * This file is auto-generated. DO NOT EDIT.\n");
        writer.append(" */\n");
        writer.append("package com.onemillionworlds.tamarin.openxrbindings.handles;\n\n");

        writer.append("import com.onemillionworlds.tamarin.openxrbindings.Handle;\n\n");

        writer.append("/**\n");
        // Add spaces between words in the handle name for better readability
        String description = handleName.substring(2); // Remove "Xr" prefix
        description = description.replaceAll("([A-Z])", " $1").toLowerCase().trim(); // Add spaces before capital letters
        writer.append(" * Handle for " + description + ".\n");
        writer.append(" */\n");
        writer.append("public class " + handleName + " extends Handle {\n\n");

        // Constructor
        writer.append("    /**\n");
        writer.append("     * Creates a new " + handleName + " instance.\n");
        writer.append("     *\n");
        writer.append("     * @param rawHandle The raw handle value.\n");
        writer.append("     */\n");
        writer.append("    public " + handleName + "(long rawHandle) {\n");
        writer.append("        super(rawHandle);\n");
        writer.append("    }\n");

        writer.append("}\n");
        
        return writer.toString();
    }
}