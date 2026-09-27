package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * If this struct has a base header that it adds on to this records it
     */
    private Optional<String> baseHeader = Optional.empty();

    /**
     * If this struct has a type in the XrStructureType enum then this is the value of that enum.
     * I.e. when a base header has type() called on it it will return this value if it can be cast to this type.
     */
    private Optional<String> xrStructureTypeEnumValue = Optional.empty();

    /**
     * If this struct is a base struct then it can have child structs. This lists those
     */
    private List<String> childTypes = new ArrayList<>();

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

    public boolean hasField(String fieldName){
        for(StructField field : fields){
            if(field.getName().equals(fieldName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Pointer fields usually come with a separate field that holds how many items the pointer points to (the "len"
     * of the member in xr.xml). This finds that count field
     */
    public Optional<String> findCountParameterForPointerField(String fieldName){
        return fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .flatMap(StructField::getCountField);
    }

    public Optional<String> getBaseHeader() {
        return baseHeader;
    }

    public void setBaseHeader(String baseHeader) {
        this.baseHeader = Optional.ofNullable(baseHeader);
    }

    public List<String> getChildTypes() {
        return childTypes;
    }

    public void setChildren(List<String> childTypes) {
        this.childTypes = childTypes;
    }

    public Optional<String> getXrStructureTypeEnumValue() {
        return xrStructureTypeEnumValue;
    }

    public void setXrStructureTypeEnumValue(String xrStructureTypeEnumValue) {
        this.xrStructureTypeEnumValue = Optional.ofNullable(xrStructureTypeEnumValue);
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
