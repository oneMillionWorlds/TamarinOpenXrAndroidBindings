package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.Objects;

/**
 * Class representing a struct field.
 */
public class StructField {
    private final String type;
    private final String name;
    private final String arraySizeConstant;

    public StructField(String type, String name, String arraySizeConstant) {
        this.type = type;
        this.name = name;
        this.arraySizeConstant = arraySizeConstant;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getArraySizeConstant() {
        return arraySizeConstant;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructField that)) return false;
        return Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(arraySizeConstant, that.arraySizeConstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, arraySizeConstant);
    }

    @Override
    public String toString() {
        return "StructField{" +
                "type='" + type + "' " +
                ", name='" + name + "' " +
                ", arraySizeConstant='" + arraySizeConstant + "' " +
                '}';
    }
}
