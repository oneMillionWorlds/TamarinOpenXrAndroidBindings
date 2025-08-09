package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for flag definitions in the format "typedef XrFlags64 XrDebugUtilsMessageSeverityFlagsEXT;"
 */
public class FlagsParser {

    public static Pattern flagsPattern = Pattern.compile("typedef\\s+XrFlags64\\s+(Xr[A-Za-z0-9]+)\\s*;");

    /**
     * Parses a line containing a flag definition in the format "typedef XrFlags64 XrDebugUtilsMessageSeverityFlagsEXT;"
     * and returns the flag name (e.g., XrDebugUtilsMessageSeverityFlagsEXT).
     *
     * @param line The line to parse
     * @return The flag name if found, empty otherwise
     */
    public static Optional<String> parseFlags(String line) {
        Matcher flagsMatcher = flagsPattern.matcher(line);
        if (flagsMatcher.find()) {
            return Optional.of(flagsMatcher.group(1).trim());
        }
        return Optional.empty();
    }
}