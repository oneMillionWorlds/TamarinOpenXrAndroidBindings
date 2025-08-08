package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumParser {
    public static Pattern enumStartPattern = Pattern.compile("typedef\\s+enum\\s+(?:(Xr[A-Za-z]+)\\s+)?\\{");
    static Pattern enumEndPattern = Pattern.compile("\\}\\s+(Xr[A-Za-z]+);");
    static Pattern enumValuePattern = Pattern.compile("\\s*(XR_[A-Z0-9_]+)\\s*=\\s*([^,]+),?");

    public static EnumDefinition parseEnum(BufferedReader readerOngoing, String triggeringLine) throws IOException {
        Matcher enumStartMatcher = enumStartPattern.matcher(triggeringLine);
        if (enumStartMatcher.find()) {
            // Group 1 might be null if this is an unnamed enum like "typedef enum {"
            // In that case, we'll get the name from the end pattern (e.g., "} XrStructureType;")
            String enumName = enumStartMatcher.group(1);

            EnumDefinition enumDef = new EnumDefinition(enumName);

            Map<String, String> enumValues = new HashMap<>();

            while (true) {
                String nextLine = readerOngoing.readLine();
                if (enumEndPattern.matcher(nextLine).find()) {
                    return enumDef;
                }
                Matcher enumValueMatcher = enumValuePattern.matcher(nextLine);
                if (enumValueMatcher.find()) {
                    String name = enumValueMatcher.group(1);
                    String value = enumValueMatcher.group(2).trim();

                    // Check if the value is a reference to another enum value
                    if (enumValues.containsKey(value)) {
                        value = enumValues.get(value);
                    }

                    enumValues.put(name, value);
                    enumDef.addValue(new EnumDefinition.EnumValue(name, value));
                } else{
                    throw new RuntimeException("Unexpected not an enum value: " + nextLine);
                }
            }
        } else {
            throw new RuntimeException("Unexpected not an enum: " + triggeringLine);
        }


    }

}
