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

    /**
     * An abstract struct is one not listed in XrStructureType. Meaning that a struct cannot actually be set as having this type
     */
    private final boolean canBeItsOwnDefault;


    public StructDefinition(String name, boolean canBeItsOwnDefault) {
        this.name = name;
        this.canBeItsOwnDefault = canBeItsOwnDefault;
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
        return Objects.equals(name, that.name) && Objects.equals(fields, that.fields) && Objects.equals(canBeItsOwnDefault, that.canBeItsOwnDefault);
    }

    public boolean canBeItsOwnDefault() {
        return canBeItsOwnDefault;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fields, canBeItsOwnDefault);
    }

    @Override
    public String toString() {
        return "StructDefinition{" +
                "name='" + name + "'\n" +
                ", fields=\n" + fields.stream().map(f -> "  " + f + "\n").reduce(String::concat).orElse("") +
                ", canBeItsOwnDefault=" + canBeItsOwnDefault +
                '}';
    }
}
