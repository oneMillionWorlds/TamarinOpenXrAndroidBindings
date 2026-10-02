package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.ParseOpenXr;
import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Generator for JNI C implementation file for OpenXR functions.
 */
public class X10CGenerator extends FileGenerator {
    private final List<FunctionDefinition> functions;

    /**
     * Functions that are part of a core OpenXR version (from xr.xml), these are exported by the loader and can be
     * called directly
     */
    private final Set<String> coreFunctions;

    public X10CGenerator(Logger logger, List<FunctionDefinition> functions, Set<String> coreFunctions) {
        super(logger);
        this.functions = functions;
        this.coreFunctions = coreFunctions;
    }

    /**
     * Checks if a function is an extension function (i.e. not part of any core OpenXR version).
     *
     * @param functionName The name of the function to check
     * @return true if the function is an extension function, false otherwise
     */
    private boolean isExtensionFunction(String functionName) {
        return !coreFunctions.contains(functionName);
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
            writer.write(" * OpenXR C JNI bindings\n");
            writer.write(" * This file is auto-generated. DO NOT EDIT.\n");
            writer.write(" */\n\n");

            // Include necessary headers
            writer.write("#include <jni.h>\n");
            writer.write("#include <string.h>\n");
            writer.write("#include <stdlib.h>\n");
            writer.write("#include <stdint.h>\n\n");

            // Define XR_EXTENSION_PROTOTYPES to enable extension function prototypes
            writer.write("#define XR_EXTENSION_PROTOTYPES\n\n");

            // The platform/graphics API parts of the OpenXR headers available on the platform being compiled for
            writer.write("// The platform specific parts of OpenXR available on the platform being compiled for\n");
            ParseOpenXr.NativePlatform[] platforms = ParseOpenXr.NativePlatform.values();
            for (int i = 0; i < platforms.length; i++) {
                writer.write((i == 0 ? "#if" : "#elif") + " defined(" + platforms[i].compilerMacro + ")\n");
                for (String protect : platforms[i].protects) {
                    writer.write("#define " + protect + "\n");
                }
            }
            writer.write("#else\n");
            writer.write("#error \"Unsupported platform\"\n");
            writer.write("#endif\n\n");

            // Declares the external (platform) types the OpenXR platform header needs and the logging macros
            writer.write("#define TAG \"XR10\"\n");
            writer.write("#include \"tamarinPlatform.h\"\n\n");

            // Include OpenXR headers (on Android from the loader AAR via prefab, on desktop from the OpenXR SDK)
            writer.write("#include <openxr/openxr.h>\n");
            writer.write("#include <openxr/openxr_platform.h>\n\n");

            // Declare function pointers for extension functions (static, so they aren't exported from the library)
            if (!extensionFunctions.isEmpty()) {
                writer.write("// Function pointers for extension functions\n");
                for (FunctionDefinition function : extensionFunctions) {
                    String functionName = function.getName();
                    writeIfProtected(writer, function);
                    writer.write("static PFN_" + functionName + " " + functionName + "Func = NULL;\n");
                    writeEndIfProtected(writer, function);
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
                    writeIfProtected(writer, function);
                    writer.write("    xrGetInstanceProcAddr(instance, \"" + functionName + "\", (PFN_xrVoidFunction*)&" + functionName + "Func);\n");
                    writer.write("    if (" + functionName + "Func == NULL) {\n");
                    writer.write("        LOGI(\"Extension function " + functionName + " not available\");\n");
                    writer.write("    }\n");
                    writeEndIfProtected(writer, function);
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

            // Generate wrapper functions for extension OpenXR functions. Ones only available on some platforms are
            // stubs returning XR_ERROR_FUNCTION_UNSUPPORTED elsewhere (so the Java native method still links)
            for (FunctionDefinition function : extensionFunctions) {
                writeIfProtected(writer, function);
                writer.write(CWrapperFunctionGenerator.generateCWrapperFunction(function, true));
                if (function.getProtect().isPresent()) {
                    writer.write("#else\n");
                    writer.write(CWrapperFunctionGenerator.generateUnsupportedCWrapperFunction(function));
                }
                writeEndIfProtected(writer, function);
                writer.write("\n");
            }
        }

        logGeneration("com_onemillionworlds_tamarin_openxrbindings_XR10.c");
    }

    private static void writeIfProtected(BufferedWriter writer, FunctionDefinition function) throws IOException {
        if (function.getProtect().isPresent()) {
            writer.write("#ifdef " + function.getProtect().get() + "\n");
        }
    }

    private static void writeEndIfProtected(BufferedWriter writer, FunctionDefinition function) throws IOException {
        if (function.getProtect().isPresent()) {
            writer.write("#endif\n");
        }
    }


}