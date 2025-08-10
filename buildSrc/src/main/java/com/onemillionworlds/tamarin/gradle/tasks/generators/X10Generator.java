package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Generator for X10.java file containing Java method pairs for OpenXR functions.
 */
public class X10Generator extends FileGenerator {
    private final List<FunctionDefinition> functions;


    public X10Generator(Logger logger, List<FunctionDefinition> functions) {
        super(logger);
        this.functions = functions;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, "X10.java");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write("/*\n");
            writer.write(" * OpenXR Java bindings for Android\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n");
            writer.write("package com.onemillionworlds.tamarin.openxrbindings;\n\n");

            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.*;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.enums.*;\n");

            writer.write("import java.nio.IntBuffer;\n");
            writer.write("import java.nio.LongBuffer;\n\n");

            writer.write("/**\n");
            writer.write(" * Main class for OpenXR bindings\n");
            writer.write(" */\n");
            writer.write("public class X10 {\n\n");

            writer.write("    static {\n");
            writer.write("        System.loadLibrary(\"openxrjni\");\n");
            writer.write("    }\n\n");

            // Generate method pairs for each function
            for (FunctionDefinition function : functions) {
                generateMethodPair(writer, function);
            }

            writer.write("}\n");
        }

        logGeneration("XR10.java");
    }

    private void generateMethodPair(BufferedWriter writer, FunctionDefinition function) throws IOException {
        // Generate wrapper method
        writer.write(WrapperFunctionGenerator.generateWrapperFunction(function));
    }

}
