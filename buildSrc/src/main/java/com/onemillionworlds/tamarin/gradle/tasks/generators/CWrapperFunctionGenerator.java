package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;

/**
 * Generator for C wrapper functions for JNI implementation of OpenXR functions.
 * This class generates the C code that bridges between Java and the OpenXR library.
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class CWrapperFunctionGenerator {

    /**
     * Generates a C wrapper function for a given OpenXR function.
     * The wrapper converts JNI parameters to OpenXR parameters, calls the OpenXR function,
     * and returns the result back to Java.
     *
     * @param function The function definition
     * @return The C wrapper function code
     */
    public static String generateCWrapperFunction(FunctionDefinition function) {
        try {
            StringBuilder functionString = new StringBuilder();
            String functionName = function.getName();
            
            // Generate JNI function signature
            functionString.append("/*\n");
            functionString.append(" * Class:     com_onemillionworlds_tamarin_openxrbindings_X10\n");
            functionString.append(" * Method:    n" + functionName + "\n");
            functionString.append(" * Signature: (");
            
            // Generate JNI signature
            for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
                if (param.isPointer() || param.isStructByValue()) {
                    functionString.append("J"); // long
                } else if (param.getType().equals("uint32_t") || param.isTypeDefInt()) {
                    functionString.append("I"); // int
                } else if (param.isHandle()) {
                    functionString.append("J"); // long
                } else if (param.isEnumType()) {
                    functionString.append("I"); // int
                } else if (param.isTypeDefLong() || param.isFlag()) {
                    functionString.append("J"); // long
                } else if (param.getType().equals("float")) {
                    functionString.append("F"); // float
                } else {
                    functionString.append("I"); // default to int
                }
            }
            functionString.append(")I\n"); // Return type is always int (XrResult)
            functionString.append(" */\n");
            
            // Generate function declaration
            functionString.append("JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_X10_n" + functionName + "\n");
            functionString.append("  (JNIEnv *env");
            
            // Generate parameter list
            for (int i = 0; i < function.getParameters().size(); i++) {
                FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
                String paramName = param.getName();
                
                if (param.isPointer() || param.isStructByValue()) {
                    functionString.append(", jlong " + paramName);
                } else if (param.getType().equals("uint32_t") || param.isTypeDefInt()) {
                    functionString.append(", jint " + paramName);
                } else if (param.isHandle()) {
                    functionString.append(", jlong " + paramName);
                } else if (param.isEnumType()) {
                    functionString.append(", jint " + paramName);
                } else if (param.isTypeDefLong() || param.isFlag()) {
                    functionString.append(", jlong " + paramName);
                } else if (param.getType().equals("float")) {
                    functionString.append(", jfloat " + paramName);
                } else {
                    functionString.append(", jint " + paramName); // Default to int
                }
            }
            functionString.append(") {\n\n");
            
            // Generate parameter conversion
            functionString.append("    // Convert JNI parameters to OpenXR parameters\n");
            for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
                String paramName = param.getName();
                String paramType = param.getType();
                
                if (param.isPointer()) {
                    functionString.append("    " + paramType + (paramType.endsWith("*") ? "" : " *") + 
                                         paramName + "Ptr = (" + paramType + (paramType.endsWith("*") ? "" : " *") + 
                                         ")(intptr_t)" + paramName + ";\n");
                } else if (param.isStructByValue()) {
                    // For structs passed by value, we need to dereference the pointer
                    functionString.append("    " + paramType + " *" + paramName + "Ptr = (" + 
                                         paramType + " *)(intptr_t)" + paramName + ";\n");
                } else if (param.getType().equals("uint32_t")) {
                    functionString.append("    uint32_t " + paramName + "Value = (uint32_t)" + paramName + ";\n");
                } else if (param.isHandle()) {
                    functionString.append("    " + paramType + " " + paramName + "Handle = (" + 
                                         paramType + ")(intptr_t)" + paramName + ";\n");
                } else if (param.isEnumType()) {
                    functionString.append("    " + paramType + " " + paramName + "Value = (" + 
                                         paramType + ")" + paramName + ";\n");
                } else if (param.isTypeDefLong() || param.isFlag()) {
                    functionString.append("    " + paramType + " " + paramName + "Value = (" + 
                                         paramType + ")" + paramName + ";\n");
                } else if (param.getType().equals("float")) {
                    functionString.append("    float " + paramName + "Value = (float)" + paramName + ";\n");
                } else if (param.isTypeDefInt()) {
                    functionString.append("    " + paramType + " " + paramName + "Value = (" + 
                                         paramType + ")" + paramName + ";\n");
                }
            }
            
            functionString.append("\n    // Call the OpenXR function\n");
            functionString.append("    XrResult result = " + functionName + "(");
            
            // Generate arguments for OpenXR function call
            for (int i = 0; i < function.getParameters().size(); i++) {
                FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
                String paramName = param.getName();
                
                if (param.isPointer()) {
                    functionString.append(paramName + "Ptr");
                } else if (param.isStructByValue()) {
                    // For structs passed by value, we need to dereference the pointer
                    functionString.append("*" + paramName + "Ptr");
                } else if (param.getType().equals("uint32_t") || param.isTypeDefInt() || 
                          param.isEnumType() || param.isTypeDefLong() || param.isFlag() || 
                          param.getType().equals("float")) {
                    functionString.append(paramName + "Value");
                } else if (param.isHandle()) {
                    functionString.append(paramName + "Handle");
                } else {
                    functionString.append(paramName);
                }
                
                if (i < function.getParameters().size() - 1) {
                    functionString.append(", ");
                }

            }
            functionString.append(");\n\n");
            
            // Return the result
            functionString.append("    // Return the result as a jint\n");
            functionString.append("    return (jint)result;\n");
            functionString.append("}\n");
            
            return functionString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating C wrapper function for " + function.getName() + ": " + e.getMessage(), e);
        }
    }
}