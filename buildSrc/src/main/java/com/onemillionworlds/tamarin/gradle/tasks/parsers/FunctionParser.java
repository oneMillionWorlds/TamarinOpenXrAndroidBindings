package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParser {
    public static Pattern functionStartPattern = Pattern.compile("XRAPI_ATTR\\s+(\\w+)\\s+XRAPI_CALL\\s+(xr\\w+)\\s*.*");
    static Pattern parameterPattern = Pattern.compile("\\s*((?:const\\s+)?\\w+(?:\\s+const)?)\\s+(\\w+)\\s*,?\\s*");

    public static FunctionDefinition parseFunction(BufferedReader readerOngoing, String triggeringLine, List<String> knownEnumTypes, List<String> knownAtoms, List<String> knownTypeDefInts, List<String> knownTypeDefLongs, List<String> knownHandles, List<String> knownFlags, List<String> knownStructs){
        Matcher startMatcher = functionStartPattern.matcher(triggeringLine);
        if(startMatcher.find()){
            String returnType = startMatcher.group(1);
            String functionName = startMatcher.group(2);

            FunctionDefinition functionDef = new FunctionDefinition(functionName, returnType);

            // Read lines until we find the end of the function (line ending with ");")
            try {
                StringBuilder parameterBuffer = new StringBuilder();
                String line;
                while ((line = readerOngoing.readLine()) != null) {
                    parameterBuffer.append(line.trim()).append(" ");

                    if (line.trim().endsWith(");")) {
                        // Found the end of the function
                        break;
                    }
                }

                // Extract parameters from the buffer
                String parameterString = parameterBuffer.toString();
                // Remove everything after the closing parenthesis
                int endIndex = parameterString.indexOf(");");
                if (endIndex != -1) {
                    parameterString = parameterString.substring(0, endIndex);
                }

                // Split by commas to get individual parameters
                String[] parameters = parameterString.split(",");
                for (String param : parameters) {
                    param = param.trim();
                    if (param.isEmpty()) continue;

                    // Extract type and name
                    String[] parts = param.split("\\s+");
                    if (parts.length >= 2) {
                        // The last part is the parameter name
                        String name = parts[parts.length - 1];
                        // Everything before the name is the type
                        StringBuilder typeBuilder = new StringBuilder();
                        for (int i = 0; i < parts.length - 1; i++) {
                            typeBuilder.append(parts[i]).append(" ");
                        }
                        String type = typeBuilder.toString().trim();

                        // Check if it's a pointer or double pointer
                        boolean isDoublePointer = name.startsWith("**") || type.endsWith("**");
                        boolean isPointer = isDoublePointer || name.startsWith("*") || type.endsWith("*");
                        name = name.replace("*", "");
                        type = type.replace("*", "");

                        // Check if it's a buffer with size specification (e.g., buffer[SIZE])
                        String extraDocumentation = null;
                        if (name.contains("[") && name.contains("]")) {
                            int startBracket = name.indexOf("[");
                            int endBracket = name.indexOf("]");
                            if (startBracket > 0 && endBracket > startBracket) {
                                String bufferSize = name.substring(startBracket + 1, endBracket);
                                extraDocumentation = "Required size " + bufferSize;
                                name = name.substring(0, startBracket);
                                isPointer = true;
                            }
                        }

                        // Check if it's const
                        boolean isConst = param.trim().startsWith("const") || param.contains(" const ");

                        // Remove const from type if present
                        type = type.replace("const", "").trim();
                        boolean isEnumType = knownEnumTypes.contains(type);
                        boolean isAtomType = knownAtoms.contains(type);
                        boolean isTypeDefInt = knownTypeDefInts.contains(type);
                        boolean isTypeDefLong = knownTypeDefLongs.contains(type);
                        boolean isHandle = knownHandles.contains(type);
                        boolean isFlag = knownFlags.contains(type);
                        boolean isStruct = knownStructs.contains(type);
                        FunctionDefinition.FunctionParameter parameter = new FunctionDefinition.FunctionParameter(type, name, isPointer, isConst, isEnumType, isAtomType, isTypeDefInt, isTypeDefLong, isHandle, isFlag, isStruct, isDoublePointer);
                        if (extraDocumentation != null) {
                            parameter.setExtraDocumentation(extraDocumentation);
                        }
                        functionDef.addParameter(parameter);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading function parameters", e);
            }

            return functionDef;
        }else{
            throw new RuntimeException("Unexpected not a function: " + triggeringLine);
        }
    }
}
