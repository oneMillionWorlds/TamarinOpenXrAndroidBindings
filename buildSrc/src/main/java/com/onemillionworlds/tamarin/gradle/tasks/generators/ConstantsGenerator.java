package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.parsers.ConstParser;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Generator for XR10Constants.java file.
 */
public class ConstantsGenerator extends FileGenerator {
    private final Map<String, ConstParser.Const> constants;
    private final Collection<String> typeDeffedInt;

    private final Collection<String> typeDeffedLong;
    public ConstantsGenerator(Logger logger, Map<String, ConstParser.Const> constants, List<String> typeDeffedInt, List<String> typeDeffedLong) {
        super(logger);
        this.constants = constants;
        this.typeDeffedInt = typeDeffedInt;
        this.typeDeffedLong = typeDeffedLong;
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
            for (Map.Entry<String, ConstParser.Const> entry : constants.entrySet()) {
                writer.write(entry.getValue().render(typeDeffedInt, typeDeffedLong));
            }
            writer.write("    \n");
            writer.write("    // Pointer size\n");
            writer.write("    public static final int POINTER_SIZE = 8; // 64-bit\n");
            writer.write("}\n");
        }

        logGeneration("XR10Constants.java");
    }
}