package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StructParser {

    public static Pattern structStartPattern = Pattern.compile("\\s*typedef\\s+struct\\s+(?:XR_MAY_ALIAS\\s+)?(\\w+)\\s*\\{");
    static Pattern structEndPattern = Pattern.compile("\\s*\\}\\s+(\\w+);");
    static Pattern fieldPattern = Pattern.compile("\\s*((?:const\\s+)?\\w+(?:\\s*\\*(?:\\s*XR_MAY_ALIAS)?)?(?:\\s+const)?)\\s+(\\w+)(?:\\[(XR_[A-Z_]+)\\])?;");

    public static StructDefinition parseStruct(BufferedReader readerOngoing, String triggeringLine, List<String> knownEnumTypes,
                                              List<String> knownAtoms, List<String> knownTypeDefInts, List<String> knownTypeDefLongs, 
                                              List<String> knownHandles, List<String> knownFlags, List<String> knownStructs) throws IOException {
        Matcher structStartMatcher = structStartPattern.matcher(triggeringLine);
        if(structStartMatcher.find()){
            String currentStructName = structStartMatcher.group(1);
            StructDefinition structDefinition = new StructDefinition(currentStructName);
            while (true) {
                String nextLine = readerOngoing.readLine().replace("XR_MAY_ALIAS", "");
                if (structEndPattern.matcher(nextLine).find()) {
                    return structDefinition;
                }
                Matcher matcher = fieldPattern.matcher(nextLine);
                if(matcher.find()){
                    String type = matcher.group(1);
                    String fieldName = matcher.group(2);
                    String arraySizeConstant = matcher.group(3); // May be null

                    // Check if it's const
                    boolean isConst = type.trim().startsWith("const") || type.contains(" const ");

                    // Check if it's a pointer or double pointer
                    boolean isDoublePointer = type.contains("**");
                    boolean isPointer = isDoublePointer || type.contains("*");

                    // Remove * from type
                    type = type.replace("*", "").replace("const", "").trim();

                    // Check type characteristics
                    boolean isEnumType = knownEnumTypes.contains(type);
                    boolean isAtomType = knownAtoms.contains(type);
                    boolean isTypeDefInt = knownTypeDefInts.contains(type);
                    boolean isTypeDefLong = knownTypeDefLongs.contains(type);
                    boolean isHandle = knownHandles.contains(type);
                    boolean isFlag = knownFlags.contains(type);
                    boolean isStruct = knownStructs.contains(type);

                    StructField field = new StructField(type, fieldName, arraySizeConstant, isPointer, isConst, 
                                                      isEnumType, isAtomType, isTypeDefInt, isTypeDefLong, 
                                                      isHandle, isFlag, isStruct, isDoublePointer);
                    structDefinition.addField(field);
                }
            }

        }else{
            throw new RuntimeException("Unexpected not a struct: " + triggeringLine);
        }
    }
    
}
