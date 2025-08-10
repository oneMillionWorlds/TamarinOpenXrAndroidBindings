package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;


@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class WrapperFunctionGenerator {

    /**
     * This is the non-native function that unwraps buffers to addresses etc
     */
    public static String generateWrapperFunction(FunctionDefinition function){
        try {

            StringBuilder functionString = new StringBuilder();

            String functionName = function.getName();
            String returnType = "XrResult"; // all the methods just return a success/failure code

            // Generate wrapper method
            functionString.append("    /**\n");
            functionString.append("     * " + getJavaDocDescription(function) + "\n");
            functionString.append("     * \n");

            // Generate parameter documentation
            for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
                String cType = param.getType();
                functionString.append("     * @param " + param.getName() + " (" + cType + ")" + param.getExtraDocumentation().map(ed -> " " + ed).orElse("") + "\n");
            }

            functionString.append("     * @return " + "The XrResult status code" + "\n");
            functionString.append("     */\n");

            functionString.append("    public " + returnType + " " + functionName + "(");

            // Generate parameter list
            for (int i = 0; i < function.getParameters().size(); i++) {
                FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
                String javaType = param.getHighLevelJavaType();

                functionString.append(javaType + " " + param.getName());

                if (i < function.getParameters().size() - 1) {
                    functionString.append(", ");
                }
            }

            functionString.append(") {\n");

            // Generate method body
            generateWrapperMethodBody(functionString, function);

            functionString.append("    }\n\n");

            // Generate native method declaration
            functionString.append("    public native int n" + functionName + "(");

            // Generate parameter list for native method
            for (int i = 0; i < function.getParameters().size(); i++) {
                FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
                String paramType = param.getLowLevelJavaType();
                String paramName = param.getName();

                functionString.append(paramType +  " " + paramName);

                if (i < function.getParameters().size() - 1) {
                    functionString.append(", ");
                }
            }

            functionString.append(");\n");

            return functionString.toString();
        }catch (Exception e){
            throw new RuntimeException("Error generating wrapper function for " + function.getName() + ": " + e.getMessage(), e);
        }
    }

    private static String getJavaDocDescription(FunctionDefinition function) {
        return "Wrapper for " + function.getName() + " OpenXR function";
    }

    private static void generateWrapperMethodBody(StringBuilder writer, FunctionDefinition function) {
        // Convert parameters for native method call
        for (FunctionDefinition.FunctionParameter param : function.getParameters()) {
            String paramName = param.getName();
            boolean isPointer = param.isPointer();
            boolean isStructByValue = param.isStructByValue();
            if(isPointer || isStructByValue){
                writer.append("        long " + paramName + "Address = " + paramName + " == null ? MemoryUtil.NULL : " + paramName + ".address();\n");
            }

        }

        // Generate native method call
        writer.append("        return XrResult.fromValue(n" + function.getName() + "(");

        // Generate arguments for native method call
        for (int i = 0; i < function.getParameters().size(); i++) {
            FunctionDefinition.FunctionParameter param = function.getParameters().get(i);
            String paramType = param.getType();
            String paramName = param.getName();
            boolean isPointer = param.isPointer();
            boolean isStructByValue = param.isStructByValue();
            boolean isEnum = param.isEnumType();
            if(isPointer || isStructByValue){
                writer.append(paramName + "Address");
            }else{
                writer.append(paramName);
                if(isEnum){
                    writer.append(".getValue()");
                }
            }
            if (i < function.getParameters().size() - 1) {
                writer.append(", ");
            }
        }

        writer.append("));\n");
    }

}
