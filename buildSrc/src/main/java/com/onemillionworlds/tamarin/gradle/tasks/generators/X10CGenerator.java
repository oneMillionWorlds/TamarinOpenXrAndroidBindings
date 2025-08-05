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

    // List of known handle types that should be treated as 64-bit integers
    private static final List<String> HANDLE_TYPES = Arrays.asList(
        "XrAction", "XrActionSet", "XrInstance", "XrSession", "XrSpace", "XrSwapchain",
        "XrDebugUtilsMessengerEXT", "XrSpatialAnchorMSFT", "XrSpatialGraphNodeBindingMSFT",
        "XrHandTrackerEXT", "XrSceneObserverMSFT", "XrSceneMSFT", "XrFacialTrackerHTC",
        "XrFoveationProfileFB", "XrTriangleMeshFB", "XrPassthroughFB", "XrPassthroughLayerFB",
        "XrGeometryInstanceFB", "XrSpatialAnchorStoreConnectionMSFT"
    );

    // List of known atom types that should be treated as 64-bit integers
    private static final List<String> ATOM_TYPES = Arrays.asList(
        "XrPath", "XrSystemId", "XrAsyncRequestIdFB"
    );

    public X10CGenerator(Logger logger, List<FunctionDefinition> functions) {
        super(logger);
        this.functions = functions;
    }

    @Override
    public void generate(File outputDir) throws IOException {
        File outputFile = new File(outputDir, "com_onemillionworlds_tamarin_openxrbindings_X10.c");

        try (BufferedWriter writer = createWriter(outputFile)) {
            writer.write("#include <jni.h>\n");
            writer.write("#include <string.h>\n");
            writer.write("#include <stdlib.h>\n");
            writer.write("#include <android/log.h>\n\n");

            writer.write("// Define XR_EXTENSION_PROTOTYPES to enable extension function prototypes\n");
            writer.write("#define XR_EXTENSION_PROTOTYPES\n\n");

            writer.write("#include \"../include/openxr/openxr.h\"\n");
            writer.write("#include \"../include/openxr/openxr_platform.h\"\n\n");

            writer.write("#define TAG \"X10\"\n");
            writer.write("#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)\n");
            writer.write("#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)\n\n");

            // Generate JNI implementation for each function
            for (FunctionDefinition function : functions) {
                generateJniImplementation(writer, function);
            }
        }

        logGeneration("com_onemillionworlds_tamarin_openxrbindings_X10.c");
    }

    private void generateJniImplementation(BufferedWriter writer, FunctionDefinition function) throws IOException {
        String functionName = function.getName();
        String returnType = function.getReturnType();

        writer.write("/*\n");
        writer.write(" * Class:     com_onemillionworlds_tamarin_openxrbindings_X10\n");
        writer.write(" * Method:    n" + functionName + "\n");
        writer.write(" * Signature: (");

        // Generate JNI signature
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            writer.write(getJniSignatureForParameter(param));
        }

        writer.write(")");
        writer.write(getJniSignatureForReturnType(returnType));
        writer.write("\n");
        writer.write(" */\n");

        writer.write("JNIEXPORT " + getJniReturnType(returnType) + " JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_X10_n" + functionName + "\n");
        writer.write("  (JNIEnv *env, jobject obj");

        // Generate parameter list
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            writer.write(", " + getJniParameterType(param) + " " + param.getName());
        }

        writer.write(") {\n\n");

        // Generate parameter conversion
        writer.write("    // Convert JNI parameters to OpenXR parameters\n");
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            generateParameterConversion(writer, param);
        }

        // Generate string handling for char* parameters
        boolean hasStringParam = false;
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            if (param.getType().equals("char") && param.isPointer()) {
                hasStringParam = true;
                writer.write("    // Convert Java string to C string\n");
                writer.write("    const char *" + param.getName() + "Str = NULL;\n");
                writer.write("    if (" + param.getName() + " != NULL) {\n");
                writer.write("        " + param.getName() + "Str = (*env)->GetStringUTFChars(env, " + param.getName() + ", NULL);\n");
                writer.write("    }\n\n");
            }
        }

        // Generate function call
        writer.write("    // Call the OpenXR function\n");
        writer.write("    " + returnType + " result = " + functionName + "(");

        // Generate arguments
        for (int i = 0; i < function.getParameters().size(); i++) {
            FunctionDefinition.FunctionParameter param = function.getParameters().get(i);

            if (param.getType().equals("char") && param.isPointer()) {
                writer.write(param.getName() + "Str");
            } else if (param.isPointer()) {
                writer.write(param.getName() + "Ptr");
            } else if (isHandleType(param.getType())) {
                writer.write("(" + param.getType() + ")(intptr_t)" + param.getName());
            } else {
                writer.write(param.getName());
            }

            if (i < function.getParameters().size() - 1) {
                writer.write(", ");
            }
        }

        writer.write(");\n\n");

        // Release string resources
        if (hasStringParam) {
            writer.write("    // Release string resources\n");
            for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
                if (param.getType().equals("char") && param.isPointer()) {
                    writer.write("    if (" + param.getName() + "Str != NULL) {\n");
                    writer.write("        (*env)->ReleaseStringUTFChars(env, " + param.getName() + ", " + param.getName() + "Str);\n");
                    writer.write("    }\n\n");
                }
            }
        }

        // Return result
        writer.write("    // Return the result\n");
        if (returnType.equals("void")) {
            writer.write("    return;\n");
        } else {
            writer.write("    return (" + getJniReturnType(returnType) + ")result;\n");
        }

        writer.write("}\n\n");
    }

    private void generateParameterConversion(BufferedWriter writer, FunctionDefinition.FunctionParameter param) throws IOException {
        String cType = param.getType();
        boolean isPointer = param.isPointer();

        if (cType.equals("uint32_t") && isPointer) {
            writer.write("    uint32_t *" + param.getName() + "Ptr = (uint32_t *)(intptr_t)" + param.getName() + ";\n");
        } else if ((isHandleType(cType) || isAtomType(cType)) && isPointer) {
            writer.write("    " + cType + " *" + param.getName() + "Ptr = (" + cType + " *)(intptr_t)" + param.getName() + ";\n");
        } else if (cType.startsWith("Xr") && isPointer && !cType.equals("XrBool32")) {
            writer.write("    " + cType + " *" + param.getName() + "Ptr = (" + cType + " *)(intptr_t)" + param.getName() + ";\n");
        } else if (isHandleType(cType)) {
            // No conversion needed for handle types
        } else if (cType.equals("uint32_t") || cType.equals("int32_t") || cType.equals("XrBool32")) {
            // No conversion needed for 32-bit integer types
        } else if (cType.equals("uint64_t") || cType.equals("int64_t") || cType.equals("XrVersion")) {
            // No conversion needed for 64-bit integer types
        }
    }

    private String getJniSignatureForParameter(FunctionDefinition.FunctionParameter param) {
        String cType = param.getType();
        boolean isPointer = param.isPointer();

        if (cType.equals("char") && isPointer) {
            return "Ljava/lang/String;";
        } else if (isPointer) {
            return "J"; // long (pointer address)
        } else if (cType.equals("uint32_t") || cType.equals("int32_t") || cType.equals("XrBool32")) {
            return "I"; // int
        } else if (cType.equals("uint64_t") || cType.equals("int64_t") || cType.equals("XrVersion") || 
                   isHandleType(cType) || isAtomType(cType)) {
            return "J"; // long
        } else {
            return "I"; // Default to int
        }
    }

    private String getJniSignatureForReturnType(String returnType) {
        if (returnType.equals("void")) {
            return "V";
        } else if (returnType.equals("XrResult")) {
            return "I"; // int
        } else {
            return "I"; // Default to int
        }
    }

    private String getJniReturnType(String returnType) {
        if (returnType.equals("void")) {
            return "void";
        } else if (returnType.equals("XrResult")) {
            return "jint";
        } else {
            return "jint"; // Default to jint
        }
    }

    private String getJniParameterType(FunctionDefinition.FunctionParameter param) {
        String cType = param.getType();
        boolean isPointer = param.isPointer();

        if (cType.equals("char") && isPointer) {
            return "jstring";
        } else if (isPointer) {
            return "jlong"; // long (pointer address)
        } else if (cType.equals("uint32_t") || cType.equals("int32_t") || cType.equals("XrBool32")) {
            return "jint";
        } else if (cType.equals("uint64_t") || cType.equals("int64_t") || cType.equals("XrVersion") || 
                   isHandleType(cType) || isAtomType(cType)) {
            return "jlong";
        } else {
            return "jint"; // Default to jint
        }
    }

    private boolean isHandleType(String type) {
        return HANDLE_TYPES.contains(type);
    }

    private boolean isAtomType(String type) {
        return ATOM_TYPES.contains(type);
    }
}
