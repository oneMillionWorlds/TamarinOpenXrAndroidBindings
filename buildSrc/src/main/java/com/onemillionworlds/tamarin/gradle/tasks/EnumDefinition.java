package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an enum definition.
 */
public class EnumDefinition {
    private final String name;
    private final List<EnumValue> values = new ArrayList<>();

    public EnumDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addValue(EnumValue value) {
        values.add(value);
    }

    public List<EnumValue> getValues() {
        return values;
    }

    /**
     * Class representing an enum value.
     */
    public static class EnumValue {
        private final String name;
        private final String value;

        public EnumValue(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}