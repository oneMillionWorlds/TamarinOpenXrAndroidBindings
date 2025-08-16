package com.onemillionworlds.tamarin.gradle.tasks.generators;

import java.util.Collections;
import java.util.List;

public class StructGeneratorCommonData {

    public static final List<String> KNOWN_ENUM_TYPES =List.of("XrStructureType", "XrActionType");
    public static final List<String> KNOWN_ATOMS =  List.of("XrPath");
    public static final List<String> KNOWN_TYPEDEF_INTS = Collections.emptyList();
    public static final List<String> KNOWN_TYPEDEF_LONGS = Collections.emptyList();
    public static final List<String> KNOWN_HANDLES = Collections.emptyList();
    public static final List<String> KNOWN_FLAGS = List.of("XrDebugUtilsMessageSeverityFlagsEXT", "XrDebugUtilsMessageTypeFlagsEXT", "XrInstanceCreateFlags");
    public static final List<String> KNOWN_STRUCTS = List.of("XrActiveActionSet", "XrApplicationInfo");

}
