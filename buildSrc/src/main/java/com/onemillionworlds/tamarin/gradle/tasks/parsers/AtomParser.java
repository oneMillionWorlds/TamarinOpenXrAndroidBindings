package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomParser {

    public static Pattern atomPattern = Pattern.compile("XR_DEFINE_ATOM\\(([^\\)]+)\\)");

    /**
     * Parses a line containing an atom definition in the format XR_DEFINE_ATOM(XrSystemId)
     * and returns the atom name (e.g., XrSystemId).
     *
     * @param line The line to parse
     * @return The atom name if found, null otherwise
     */
    public static Optional<String> parseAtom(String line) {
        Matcher atomMatcher = atomPattern.matcher(line);
        if (atomMatcher.find()) {
            return Optional.of(atomMatcher.group(1).trim());
        }
        return Optional.empty();
    }
}