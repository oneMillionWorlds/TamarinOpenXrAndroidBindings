package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StructParser {

    public static Pattern structStartPattern = Pattern.compile("\\s*typedef\\s+struct\\s+(?:XR_MAY_ALIAS\\s+)?(\\w+)\\s*\\{");
    static Pattern structEndPattern = Pattern.compile("\\s*\\}\\s+(\\w+);");
    static Pattern fieldPattern = Pattern.compile("\\s*((?:const\\s+)?\\w+(?:\\s*\\*(?:\\s*const)?(?:\\s*\\*)?)?(?:\\s+const)?)\\s+(\\w+)((?:\\s*\\[\\s*(?:XR_[A-Z_0-9]+|\\d+)\\s*\\])*);");
    static Pattern structAliasPattern = Pattern.compile("^\\s*typedef\\s+(Xr\\w+)\\s+(Xr\\w+)\\s*;");
    static Pattern arrayDimensionPattern = Pattern.compile("\\[\\s*(XR_[A-Z_0-9]+|\\d+)\\s*\\]");

    public static StructDefinition parseStruct(BufferedReader readerOngoing, String triggeringLine, List<String> knownEnumTypes,
                                              List<String> knownAtoms, List<String> knownTypeDefInts, List<String> knownTypeDefLongs, 
                                              List<String> knownHandles, List<String> knownFlags, List<String> knownStructs,
                                              List<String> xrStructureTypeValues) throws IOException {
        Matcher structStartMatcher = structStartPattern.matcher(triggeringLine);
        if(structStartMatcher.find()){
            String currentStructName = structStartMatcher.group(1);

            String proposedXrStructureTypeEnumValue = createXrStructureTypeEnumValueForStruct(currentStructName);

            StructDefinition structDefinition = new StructDefinition(
                    currentStructName,
                    xrStructureTypeValues.contains(proposedXrStructureTypeEnumValue)
            );

            if(xrStructureTypeValues.contains(proposedXrStructureTypeEnumValue)){
                structDefinition.setXrStructureTypeEnumValue(proposedXrStructureTypeEnumValue);
            }

            while (true) {
                String nextLine = readerOngoing.readLine().replace("XR_MAY_ALIAS", "");
                if (structEndPattern.matcher(nextLine).find()) {
                    return structDefinition;
                }
                Matcher matcher = fieldPattern.matcher(nextLine);
                if(matcher.find()){
                    String type = matcher.group(1);
                    String fieldName = matcher.group(2);
                    String arraySizeConstant = parseArraySize(matcher.group(3)); // May be null

                    // Check if it's const
                    boolean isConst = type.trim().startsWith("const") || type.contains(" const ");

                    // Check if it's a pointer or double pointer (e.g. "X**" or "const X* const*")
                    boolean isDoublePointer = type.chars().filter(c -> c == '*').count() == 2;
                    boolean isPointer = isDoublePointer || type.contains("*");

                    // Remove * from type
                    type = type.replace("*", "").replace("const", "").trim();

                    // Check type characteristics. Double pointers are exposed as raw addresses, so the pointed to
                    // type's characteristics are deliberately ignored
                    boolean isEnumType = !isDoublePointer && knownEnumTypes.contains(type);
                    boolean isAtomType = !isDoublePointer && knownAtoms.contains(type);
                    boolean isTypeDefInt = !isDoublePointer && knownTypeDefInts.contains(type);
                    boolean isTypeDefLong = !isDoublePointer && knownTypeDefLongs.contains(type);
                    boolean isHandle = !isDoublePointer && knownHandles.contains(type);
                    boolean isFlag = !isDoublePointer && knownFlags.contains(type);
                    boolean isStruct = !isDoublePointer && knownStructs.contains(type);
                    boolean isSingletonStructPointer = isStruct
                            && isPointer
                            && structDefinition.findCountParameterForPointerField(fieldName).isEmpty();
                    StructField field = new StructField(type, fieldName, arraySizeConstant, isPointer, isConst, 
                                                      isEnumType, isAtomType, isTypeDefInt, isTypeDefLong, 
                                                      isHandle, isFlag, isStruct, isDoublePointer, isSingletonStructPointer);
                    structDefinition.addField(field);
                }else{
                    throw new RuntimeException("Failed to pass line " + nextLine);
                }
            }

        }else{
            throw new RuntimeException("Unexpected not a struct: " + triggeringLine);
        }
    }

    /**
     * Parses a struct alias (e.g. "typedef XrUuid XrUuidEXT;", created when an extension is promoted to core). The alias
     * is generated as its own struct with the same fields as the struct it aliases, so the old name keeps working.
     * The alias doesn't take part in its target's parent/child relationships.
     */
    public static Optional<StructDefinition> parseStructAlias(String line, List<StructDefinition> knownStructs,
                                                              List<String> xrStructureTypeValues){
        Matcher matcher = structAliasPattern.matcher(line);
        if(!matcher.find()){
            return Optional.empty();
        }
        String targetName = matcher.group(1);
        String aliasName = matcher.group(2);
        return knownStructs.stream()
                .filter(s -> s.getName().equals(targetName))
                .findFirst()
                .map(target -> {
                    String proposedXrStructureTypeEnumValue = createXrStructureTypeEnumValueForStruct(aliasName);
                    boolean aliasHasOwnType = xrStructureTypeValues.contains(proposedXrStructureTypeEnumValue);
                    StructDefinition alias = new StructDefinition(aliasName, target.canBeItsOwnDefault());
                    target.getFields().forEach(alias::addField);
                    if(aliasHasOwnType){
                        alias.setXrStructureTypeEnumValue(proposedXrStructureTypeEnumValue);
                    }else{
                        target.getXrStructureTypeEnumValue().ifPresent(alias::setXrStructureTypeEnumValue);
                    }
                    return alias;
                });
    }

    /**
     * Converts the array dimensions of a field (e.g. "[XR_MAX_FOO]" or "[9][3]") into a single size expression. Multi
     * dimensional arrays are flattened (e.g. "9 * 3") as they have the same memory layout as a one dimensional array.
     * Returns null if the field is not an array.
     */
    static String parseArraySize(String arrayDimensions){
        List<String> dimensions = new ArrayList<>();
        Matcher matcher = arrayDimensionPattern.matcher(arrayDimensions);
        while(matcher.find()){
            dimensions.add(matcher.group(1));
        }
        return dimensions.isEmpty() ? null : String.join(" * ", dimensions);
    }

    public static String createXrStructureTypeEnumValueForStruct(String structName){
        structName = structName.replace("Xr", "");

        StringBuilder result = new StringBuilder();
        boolean inAcronym = false;

        // Process the string character by character
        for (int i = 0; i < structName.length(); i++) {
            char c = structName.charAt(i);

            // Special case for OpenGL - check if we're at the start of "OpenGL"
            if (i <= structName.length() - 6 && 
                structName.substring(i, i + 6).equals("OpenGL")) {
                // If we're not at the beginning, add an underscore
                if (i > 0) {
                    result.append('_');
                }
                // Add "OPENGL" and skip the next 5 characters (we've already processed 'O')
                result.append("OPENGL");
                i += 5; // Skip "penGL"
                continue;
            }

            // Check if we're starting a new word
            if (i > 0 && Character.isUpperCase(c)) {
                // If the previous character was lowercase or this is the start of a new acronym
                if (!inAcronym || !Character.isUpperCase(structName.charAt(i-1))) {
                    result.append('_');
                    inAcronym = Character.isUpperCase(c);
                }
                // If we're in the middle of an acronym, don't add an underscore
                else {
                    // Check if this is the end of an acronym (next char is lowercase)
                    if (i < structName.length() - 1 && Character.isLowerCase(structName.charAt(i+1))) {
                        inAcronym = false;
                    }
                }
            } else {
                inAcronym = Character.isUpperCase(c);
            }

            result.append(Character.toUpperCase(c));
        }

        return "XR_TYPE_" + result;
    }

}
