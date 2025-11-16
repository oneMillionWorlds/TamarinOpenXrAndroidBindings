package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.ParseOpenXr;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generator for JNI C implementation file for OpenXR functions.
 */
public class X10CGenerator extends FileGenerator {
    private final List<FunctionDefinition> functions;
    private final List<String> extensionSuffixes = Arrays.asList("KHR", "EXT", "MSFT", "FB", "OCULUS", "VALVE", "HTC", "EPIC", "VARJO", "ML", "ALMALENCE", "QUALCOMM", "MND", "HUAWEI", "SAMSUNG");

    public X10CGenerator(Logger logger, List<FunctionDefinition> functions) {
        super(logger);
        this.functions = functions;
    }

    /**
     * Checks if a function is an extension function by looking for extension suffixes in the function name.
     * 
     * @param functionName The name of the function to check
     * @return true if the function is an extension function, false otherwise
     */
    private boolean isExtensionFunction(String functionName) {
        for (String suffix : extensionSuffixes) {
            if (functionName.contains(suffix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, "com_onemillionworlds_tamarin_openxrbindings_XR10.c");

        // Separate functions into core and extension functions
        List<FunctionDefinition> coreFunctions = new ArrayList<>();
        List<FunctionDefinition> extensionFunctions = new ArrayList<>();

        for (FunctionDefinition function : functions) {
            if (isExtensionFunction(function.getName())) {
                extensionFunctions.add(function);
            } else {
                coreFunctions.add(function);
            }
        }

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
            for(String def : ParseOpenXr.STANDARD_DEFS){
                writer.write("#define " + def + "\n");
            }

            // Include manual EGL handle definitions needed by some platform structs
            writer.write("#include \"../../../../native/include/tamarinManualDefines.h\"\n\n");

            // Include OpenXR headers
            writer.write("#include \"../../../../native/include/openxr/openxr.h\"\n");
            writer.write("#include \"../../../../native/include/openxr/openxr_platform.h\"\n");
            // Define logging macros
            writer.write("#define TAG \"XR10\"\n");
            writer.write("#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)\n");
            writer.write("#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)\n\n");

            // Declare function pointers for extension functions
            if (!extensionFunctions.isEmpty()) {
                writer.write("// Function pointers for extension functions\n");
                for (FunctionDefinition function : extensionFunctions) {
                    String functionName = function.getName();
                    writer.write("PFN_" + functionName + " " + functionName + "Func = NULL;\n");
                }
                writer.write("\n");

                // Add initialization function for extension functions
                writer.write("// Initialize extension function pointers\n");
                writer.write("static void initializeExtensionFunctions(XrInstance instance) {\n");
                writer.write("    if (instance == XR_NULL_HANDLE) {\n");
                writer.write("        LOGE(\"Cannot initialize extension functions with null instance\");\n");
                writer.write("        return;\n");
                writer.write("    }\n\n");

                for (FunctionDefinition function : extensionFunctions) {
                    String functionName = function.getName();
                    writer.write("    xrGetInstanceProcAddr(instance, \"" + functionName + "\", (PFN_xrVoidFunction*)&" + functionName + "Func);\n");
                    writer.write("    if (" + functionName + "Func == NULL) {\n");
                    writer.write("        LOGI(\"Extension function " + functionName + " not available\");\n");
                    writer.write("    }\n");
                }

                writer.write("}\n\n");

                // Add a special wrapper for xrCreateInstance that initializes extension functions
                writer.write("/*\n");
                writer.write(" * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10\n");
                writer.write(" * Method:    nxrCreateInstance\n");
                writer.write(" * Signature: (JJ)I\n");
                writer.write(" */\n");
                writer.write("JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrCreateInstance\n");
                writer.write("  (JNIEnv *env, jclass cls, jlong createInfo, jlong instance) {\n\n");
                writer.write("    // Convert JNI parameters to OpenXR parameters\n");
                writer.write("    XrInstanceCreateInfo *createInfoPtr = (XrInstanceCreateInfo *)(intptr_t)createInfo;\n");
                writer.write("    XrInstance *instancePtr = (XrInstance *)(intptr_t)instance;\n\n");
                writer.write("    // Call the OpenXR function\n");
                writer.write("    XrResult result = xrCreateInstance(createInfoPtr, instancePtr);\n\n");
                writer.write("    // Initialize extension functions if instance creation was successful\n");
                writer.write("    if (result == XR_SUCCESS && instancePtr != NULL) {\n");
                writer.write("        initializeExtensionFunctions(*instancePtr);\n");
                writer.write("    }\n\n");
                writer.write("    // Return the result as a jint\n");
                writer.write("    return (jint)result;\n");
                writer.write("}\n\n");
            }

            // Generate wrapper functions for core OpenXR functions (except xrCreateInstance which is handled specially)
            for (FunctionDefinition function : coreFunctions) {
                if (!function.getName().equals("xrCreateInstance")) {
                    writer.write(CWrapperFunctionGenerator.generateCWrapperFunction(function));
                    writer.write("\n");
                }
            }

            // Generate wrapper functions for extension OpenXR functions
            for (FunctionDefinition function : extensionFunctions) {
                writer.write(CWrapperFunctionGenerator.generateCWrapperFunction(function, true));
                writer.write("\n");
            }
        }

        logGeneration("com_onemillionworlds_tamarin_openxrbindings_XR10.c");
    }


}