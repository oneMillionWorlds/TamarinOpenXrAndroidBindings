package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefinePasser {

    public static Pattern definePattern = Pattern.compile("#define\\s+(XR_[A-Z_]+)\\s+(.+)");

    public static Optional<Define> parseDefine(String line){
        Matcher defineMatcher = definePattern.matcher(line);

        if (defineMatcher.find()) {
            String name = defineMatcher.group(1);
            String value = defineMatcher.group(2).trim();

            boolean isNumber = value.matches("^[0-9]+$");
            boolean isQuotedString = value.matches("^\".*\"$");
            boolean isHex = value.startsWith("0x");

            // Only process constants we're interested in (size constants, etc.) or if they are really simple
            if (name.startsWith("XR_MAX_") || name.endsWith("_SIZE_EXT") || isNumber || isQuotedString || isHex) {
                // Special case for XR_MAX_EVENT_DATA_SIZE
                if (name.equals("XR_MAX_EVENT_DATA_SIZE")) {
                    // Use a fixed value for this constant
                    return Optional.of(new Define(name, "XrEventDataBuffer.SIZEOF"));
                } else {
                    return Optional.of(new Define(name, value));
                }
            }
            return Optional.empty();
        } else{
            throw new RuntimeException("Unexpected not a define: " + line);
        }


    }

    public static class Define{
        public final String constantName;
        public final String constantValue;

        public Define(String constantName, String constantValue) {
            this.constantName = constantName;
            this.constantValue = constantValue;
        }
    }



}
