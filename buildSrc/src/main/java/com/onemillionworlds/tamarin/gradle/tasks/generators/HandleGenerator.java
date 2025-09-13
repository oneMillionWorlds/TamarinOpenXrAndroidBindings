package com.onemillionworlds.tamarin.gradle.tasks.generators;

import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Generator for handle classes.
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
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

        writer.append("import com.onemillionworlds.tamarin.openxrbindings.Handle;\n");
        writer.append("import com.onemillionworlds.tamarin.openxrbindings.BufferUtils;\n");
        writer.append("import com.onemillionworlds.tamarin.openxrbindings.memory.*;\n\n");
        writer.append("import java.nio.ByteBuffer;\n");

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

        writer.append("\n");

        // buffer for this handle type (basically just a long buffer view)

        writer.append("    public static class HandleBuffer extends HandleBufferView<" + handleName + "> {\n");
        writer.append("        public HandleBuffer(LongBufferView viewToAdopt){\n");
        writer.append("            super(viewToAdopt, " + handleName + "::new);\n");
        writer.append("        }\n");
        writer.append("    }\n");

        writer.append("\n");

        // create (with new memory)
        writer.append("    /**\n");
        writer.append("     * Creates a new HandleBuffer for " + handleName + " instances. NOTE must be manually freed\n");
        writer.append("     *\n");
        writer.append("     * @param capacity The number of handles of this type that can be held.\n");
        writer.append("     */\n");
        writer.append("    public static HandleBuffer create(int capacity) {\n");
        writer.append("        LongBufferView buffer = BufferUtils.createLongBufferView(capacity);\n");
        writer.append("        return new HandleBuffer(buffer);\n");
        writer.append("    }\n");

        // create (on the stack)
        writer.append("    /**\n");
        writer.append("     * Creates a new HandleBuffer for " + handleName + " instances on the stack. NOTE must NOT be manually freed\n");
        writer.append("     *\n");
        writer.append("     * @param stack The stack to allocate this buffer on.\n");
        writer.append("     * @param capacity The number of handles of this type that can be held.\n");
        writer.append("     */\n");
        writer.append("    public static HandleBuffer create(int capacity, MemoryStack stack) {\n");
        writer.append("        LongBufferView buffer = stack.callocLong(capacity);\n");
        writer.append("        return new HandleBuffer(buffer);\n");
        writer.append("    }\n");

        writer.append("    /**\n");
        writer.append("     * Adopts an existing byte buffer a new buffer and an address as a handle buffer. NOTE if it must or must not be manually freed should follow the underlying buffer\n");
        writer.append("     *\n");
        writer.append("     * @param buffer The buffer to adopt.\n");
        writer.append("     * @param address The memory address of the buffer\n");
        writer.append("     */\n");
        writer.append("    public static HandleBuffer create(ByteBuffer buffer, long address) {\n");
        writer.append("        LongBufferView bufferView = new LongBufferView(buffer, buffer.asLongBuffer(), address);\n");
        writer.append("        return new HandleBuffer(bufferView);\n");
        writer.append("    }\n");

        writer.append("}\n");
        
        return writer.toString();
    }
}