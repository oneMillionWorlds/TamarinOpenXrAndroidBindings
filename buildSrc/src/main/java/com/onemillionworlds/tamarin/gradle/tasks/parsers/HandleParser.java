package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for handle definitions in the format XR_DEFINE_HANDLE(XrInstance).
 */
public class HandleParser {

    public static Pattern handlePattern = Pattern.compile("XR_DEFINE_HANDLE\\(([^\\)]+)\\)");

    /**
     * Parses a line containing a handle definition in the format XR_DEFINE_HANDLE(XrInstance)
     * and returns the handle name (e.g., XrInstance).
     *
     * @param line The line to parse
     * @return The handle name if found, empty otherwise
     */
    public static Optional<String> parseHandle(String line) {
        Matcher handleMatcher = handlePattern.matcher(line);
        if (handleMatcher.find()) {
            return Optional.of(handleMatcher.group(1).trim());
        }
        return Optional.empty();
    }
}