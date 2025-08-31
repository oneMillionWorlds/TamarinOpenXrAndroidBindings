package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for typedefs that are considered as int types (uint32_t and int32_t).
 */
public class IntTypeDefPasser {

    public static Pattern intTypedefPattern = Pattern.compile("typedef\\s+(uint32_t|int32_t)\\s+(Xr[A-Za-z0-9]+)\\s*;");

    private static final Pattern nestedPattern = Pattern.compile("typedef\\s+([A-Za-z0-9]+)\\s+([A-Za-z0-9]+)\\s*;");

    /**
     * Parses a line containing an int typedef in the format "typedef uint32_t XrBool32;"
     * and returns the typedef name (e.g., XrBool32).
     *
     * @param line The line to parse
     * @return The typedef name if found, empty otherwise
     */
    public static Optional<String> parseIntTypedef(String line, Collection<String> existingIntTypedefs) {
        Matcher intTypedefMatcher = intTypedefPattern.matcher(line);
        if (intTypedefMatcher.find()) {
            return Optional.of(intTypedefMatcher.group(2).trim());
        }
        Matcher nestedMatcher = nestedPattern.matcher(line);
        if (nestedMatcher.find() && existingIntTypedefs.contains(nestedMatcher.group(1))) {
            return Optional.of(nestedMatcher.group(2).trim());
        }

        return Optional.empty();
    }
}

