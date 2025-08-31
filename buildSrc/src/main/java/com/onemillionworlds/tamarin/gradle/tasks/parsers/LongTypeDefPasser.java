package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for typedefs that are considered as long types (uint64_t and int64_t).
 */
public class LongTypeDefPasser {

    public static final Pattern longTypedefPattern = Pattern.compile("typedef\\s+(uint64_t|int64_t)\\s+(Xr[A-Za-z0-9]+)\\s*;");

    private static final Pattern nestedPattern = Pattern.compile("typedef\\s+([A-Za-z0-9]+)\\s+([A-Za-z0-9]+)\\s*;");

    /**
     * Parses a line containing a long typedef in the format "typedef uint64_t XrVersion;"
     * and returns the typedef name (e.g., XrVersion).
     *
     * @param line The line to parse
     * @return The typedef name if found, empty otherwise
     */
    public static Optional<String> parseLongTypedef(String line, Collection<String> existingLongTypedefs) {
        Matcher longTypedefMatcher = longTypedefPattern.matcher(line);
        if (longTypedefMatcher.find()) {
            return Optional.of(longTypedefMatcher.group(2).trim());
        }

        Matcher nestedMatcher = nestedPattern.matcher(line);
        if (nestedMatcher.find() && existingLongTypedefs.contains(nestedMatcher.group(1))) {
            return Optional.of(nestedMatcher.group(2).trim());
        }
        return Optional.empty();
    }
}
