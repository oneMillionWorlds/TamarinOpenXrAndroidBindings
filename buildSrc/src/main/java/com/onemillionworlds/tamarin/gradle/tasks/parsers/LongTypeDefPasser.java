package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for typedefs that are considered as long types (uint64_t and int64_t).
 */
public class LongTypeDefPasser {

    public static Pattern longTypedefPattern = Pattern.compile("typedef\\s+(uint64_t|int64_t)\\s+(Xr[A-Za-z0-9]+)\\s*;");

    /**
     * Parses a line containing a long typedef in the format "typedef uint64_t XrVersion;"
     * and returns the typedef name (e.g., XrVersion).
     *
     * @param line The line to parse
     * @return The typedef name if found, empty otherwise
     */
    public static Optional<String> parseLongTypedef(String line) {
        Matcher longTypedefMatcher = longTypedefPattern.matcher(line);
        if (longTypedefMatcher.find()) {
            return Optional.of(longTypedefMatcher.group(2).trim());
        }
        return Optional.empty();
    }
}
