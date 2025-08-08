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

            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.JavaBufferView;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.PointerBufferView;\n");
            writer.write("import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;\n\n");

            writer.write("import java.nio.IntBuffer;\n");
            writer.write("import java.nio.LongBuffer;\n\n");

            writer.write("/**\n");
            writer.write(" * Main class for OpenXR bindings\n");
            writer.write(" */\n");
            writer.write("public class X10 {\n\n");

            writer.write("    static {\n");
            writer.write("        System.loadLibrary(\"openxrjni\");\n");
            writer.write("    }\n\n");

            logger.lifecycle("rjt " + functions.size());

            // Generate method pairs for each function
            for (FunctionDefinition function : functions) {



                generateMethodPair(writer, function);
            }

            writer.write("}\n");
        }

        logGeneration("XR10.java");
    }

    private void generateMethodPair(BufferedWriter writer, FunctionDefinition function) throws IOException {
        String functionName = function.getName();
        String returnType = mapCTypeToJavaType(function.getReturnType());

        // Generate wrapper method
        writer.write("    /**\n");
        writer.write("     * " + getJavaDocDescription(function) + "\n");
        writer.write("     * \n");

        // Generate parameter documentation
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            writer.write("     * @param " + param.getName() + " " + getParamDescription(param) + "\n");
        }

        writer.write("     * @return " + getReturnDescription(function) + "\n");
        writer.write("     */\n");

        writer.write("    public " + returnType + " " + functionName + "(");

        // Generate parameter list
        for (int i = 0; i < function.getParameters().size(); i++) {
            FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
            String javaType = mapCTypeToJavaParameterType(param);

            writer.write(javaType + " " + param.getName());

            if (i < function.getParameters().size() - 1) {
                writer.write(", ");
            }
        }

        writer.write(") {\n");

        // Generate method body
        generateWrapperMethodBody(writer, function);

        writer.write("    }\n\n");

        // Generate native method
        writer.write("    public native " + returnType + " n" + functionName + "(");

        // Generate native parameter list
        for (int i = 0; i < function.getParameters().size(); i++) {
            FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
            String javaType = mapCTypeToJavaNativeParameterType(param);
            String paramName = param.getName();

            writer.write(javaType + " " + paramName);

            if (i < function.getParameters().size() - 1) {
                writer.write(", ");
            }
        }

        writer.write(");\n\n");
    }

    private void generateWrapperMethodBody(BufferedWriter writer, FunctionDefinition function) throws IOException {
        // Convert parameters for native method call
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            String paramType = param.getType();
            String paramName = param.getName();
            boolean isPointer = param.isPointer();

            if (isPointer && !paramType.equals("char")) {
                if (paramType.equals("uint32_t")) {
                    // IntBufferView
                    writer.write("        long " + paramName + "Address = " + paramName + ".getAddress();\n");
                } else if (isHandleType(paramType) || isAtomType(paramType)) {
                    // PointerBufferView
                    writer.write("        long " + paramName + "Address = " + paramName + ".getAddress();\n");
                } else if (paramType.startsWith("Xr") && !param.isConst()) {
                    // Output struct buffer
                    writer.write("        long " + paramName + "Address = " + paramName + " == null ? MemoryUtil.NULL : " + paramName + ".address();\n");
                }
            }
        }

        // Generate native method call
        writer.write("        return n" + function.getName() + "(");

        // Generate arguments for native method call
        for (int i = 0; i < function.getParameters().size(); i++) {
            FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
            String paramType = param.getType();
            String paramName = param.getName();
            boolean isPointer = param.isPointer();

            if (paramType.equals("char") && isPointer) {
                // String parameter
                writer.write(paramName);
            } else if (paramType.equals("uint32_t") && isPointer) {
                // IntBufferView
                writer.write(paramName + "Address");
            } else if ((isHandleType(paramType) || isAtomType(paramType)) && isPointer) {
                // PointerBufferView
                writer.write(paramName + "Address");
            } else if (paramType.startsWith("Xr") && isPointer && param.isConst()) {
                // Input struct parameter
                writer.write(paramName + ".address()");
            } else if (paramType.startsWith("Xr") && isPointer && !param.isConst()) {
                // Output struct buffer
                writer.write(paramName + "Address");
            } else if (isHandleType(paramType) || isAtomType(paramType)) {
                // Handle or atom parameter
                writer.write(paramName);
            } else {
                // Other parameter
                writer.write(paramName);
            }

            if (i < function.getParameters().size() - 1) {
                writer.write(", ");
            }
        }

        writer.write(");\n");
    }

    private String mapCTypeToJavaType(String cType) {
        if (cType.equals("XrResult")) {
            return "int";
        } else if (cType.equals("void")) {
            return "void";
        } else {
            return "int"; // Default to int for other types
        }
    }

    private String mapCTypeToJavaParameterType(FunctionDefinition.FunctionParameter param) {
        String cType = param.getType();
        boolean isPointer = param.isPointer();

        if (cType.equals("char") && isPointer) {
            return "String";
        } else if (cType.equals("uint32_t") && isPointer) {
            return "IntBufferView";
        } else if (isHandleType(cType) && isPointer) {
            return "PointerBufferView";
        } else if (isAtomType(cType) && isPointer) {
            return "PointerBufferView";
        } else if (cType.equals("XrSystemId")) {
            return "long";
        } else if (isHandleType(cType)) {
            return isPointer ? "PointerBufferView" : "long";
        } else if (isAtomType(cType)) {
            return "long";
        } else if (cType.startsWith("Xr") && !isPointer) {
            return cType;
        } else if (cType.startsWith("Xr") && isPointer && param.isConst()) {
            // Input struct parameter
            return cType;
        } else if (cType.startsWith("Xr") && isPointer && !param.isConst()) {
            // Output struct parameter
            return cType + ".Buffer";
        } else if (cType.equals("uint32_t") || cType.equals("int32_t") || cType.equals("XrBool32")) {
            return "int";
        } else if (cType.equals("uint64_t") || cType.equals("int64_t") || cType.equals("XrVersion")) {
            return "long";
        } else {
            return "int"; // Default to int for other types
        }
    }

    private String mapCTypeToJavaNativeParameterType(FunctionDefinition.FunctionParameter param) {
        String cType = param.getType();
        boolean isPointer = param.isPointer();

        if (cType.equals("char") && isPointer) {
            return "String";
        } else if (cType.equals("uint32_t") && isPointer) {
            return "long"; // Changed from int to long as it's a pointer (memory address)
        } else if (cType.equals("XrSystemId")) {
            return "long";
        } else if (isHandleType(cType)) {
            return "long";
        } else if (isAtomType(cType)) {
            return "long";
        } else if (cType.startsWith("Xr") && isPointer) {
            return "long";
        } else if (cType.equals("uint32_t") || cType.equals("int32_t") || cType.equals("XrBool32")) {
            return "int";
        } else if (cType.equals("uint64_t") || cType.equals("int64_t") || cType.equals("XrVersion")) {
            return "long";
        } else {
            return "int"; // Default to int for other types
        }
    }

    private boolean isBufferType(FunctionDefinition.FunctionParameter param) {
        return param.isPointer() && !param.getType().equals("void");
    }

    private boolean isHandleType(String type) {
        return HANDLE_TYPES.contains(type);
    }

    private boolean isAtomType(String type) {
        return ATOM_TYPES.contains(type);
    }

    private String getJavaDocDescription(FunctionDefinition function) {
        return "Wrapper for " + function.getName() + " OpenXR function";
    }

    private String getParamDescription(FunctionDefinition.FunctionParameter param) {
        if (param.isPointer()) {
            if (param.getType().equals("char")) {
                return "String parameter";
            } else if (param.getType().equals("uint32_t")) {
                return "Pointer to count value";
            } else if (param.getType().startsWith("Xr")) {
                return "Buffer to store the result";
            } else {
                return "Pointer parameter";
            }
        } else if (isHandleType(param.getType())) {
            return "Handle parameter";
        } else {
            return "Value parameter";
        }
    }

    private String getReturnDescription(FunctionDefinition function) {
        if (function.getReturnType().equals("XrResult")) {
            return "The error code (if any)";
        } else {
            return "The result";
        }
    }
}
