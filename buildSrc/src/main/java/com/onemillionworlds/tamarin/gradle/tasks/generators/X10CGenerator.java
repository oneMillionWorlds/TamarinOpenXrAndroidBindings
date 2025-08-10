package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Generator for JNI C implementation file for OpenXR functions.
 */
public class X10CGenerator extends FileGenerator {
    private final List<FunctionDefinition> functions;


    public X10CGenerator(Logger logger, List<FunctionDefinition> functions) {
        super(logger);
        this.functions = functions;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, "com_onemillionworlds_tamarin_openxrbindings_X10.c");

        try (BufferedWriter writer = createWriter(outputFile)) {
            // Write file header
            writer.write("/*\n");
            writer.write(" * OpenXR C JNI bindings for Android\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n\n");

            // Include necessary headers
            writer.write("#include <jni.h>\n");
            writer.write("#include <string.h>\n");
            writer.write("#include <stdlib.h>\n");
            writer.write("#include <android/log.h>\n\n");

            // Define XR_EXTENSION_PROTOTYPES to enable extension function prototypes
            writer.write("// Define XR_EXTENSION_PROTOTYPES to enable extension function prototypes\n");
            writer.write("#define XR_EXTENSION_PROTOTYPES\n\n");

            // Include OpenXR headers
            writer.write("#include \"../include/openxr/openxr.h\"\n");
            writer.write("#include \"../include/openxr/openxr_platform.h\"\n\n");

            // Define logging macros
            writer.write("#define TAG \"X10\"\n");
            writer.write("#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)\n");
            writer.write("#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)\n\n");

            // Generate wrapper functions for each OpenXR function
            for (FunctionDefinition function : functions) {
                writer.write(CWrapperFunctionGenerator.generateCWrapperFunction(function));
                writer.write("\n");
            }
        }

        logGeneration("com_onemillionworlds_tamarin_openxrbindings_X10.c");
    }
}
