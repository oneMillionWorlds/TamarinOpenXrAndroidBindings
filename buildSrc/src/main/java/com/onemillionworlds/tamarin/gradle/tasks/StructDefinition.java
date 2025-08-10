package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a struct definition.
 */
public class StructDefinition {
    private final String name;
    private final List<StructField> fields = new ArrayList<>();

    public StructDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addField(StructField field) {
        fields.add(field);
    }

    public List<StructField> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructDefinition that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fields);
    }

    @Override
    public String toString() {
        return "StructDefinition{" +
                "name='" + name + "'\n" +
                ", fields=\n" + fields.stream().map(f -> "  " + f + "\n").reduce(String::concat).orElse("") +
                '}';
    }
}
