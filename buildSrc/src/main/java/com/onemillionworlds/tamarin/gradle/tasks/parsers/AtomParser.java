package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomParser {

    public static Pattern atomPattern = Pattern.compile("XR_DEFINE_(?:ATOM|OPAQUE_64)\\(([^\\)]+)\\)");

    /**
     * Parses a line containing an atom definition in the format XR_DEFINE_ATOM(XrSystemId)
     * and returns the atom name (e.g., XrSystemId).
     * <p>
     * XR_DEFINE_OPAQUE_64(XrFutureEXT) is also treated as an atom; it is an opaque 64 bit value passed by value (a
     * pointer sized typedef on 64 bit platforms, uint64_t elsewhere), so on arm64 it has exactly an atom's shape.
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