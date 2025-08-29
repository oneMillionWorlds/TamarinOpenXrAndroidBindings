package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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

    public boolean hasField(String fieldName){
        for(StructField field : fields){
            if(field.getName().equals(fieldName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Often pointer fields come with a seperate parameter that lists how many items of that type
     * the field contains. This finds that count method
     */
    public Optional<String> findCountParameterForPointerField(String fieldName){
        return findCountParameterForPointerField(fieldName, this::hasField);
    }

    @Override
    public String toString() {
        return "StructDefinition{" +
                "name='" + name + "'\n" +
                ", fields=\n" + fields.stream().map(f -> "  " + f + "\n").reduce(String::concat).orElse("") +
                ", canBeItsOwnDefault=" + canBeItsOwnDefault +
                '}';
    }

    /**
     * Often pointer fields come with a seperate parameter that lists how many items of that type
     * the field contains. This finds that count method
     */
    public static Optional<String> findCountParameterForPointerField(String fieldName, Predicate<String> hasField){

        List<String> options = new ArrayList<>(List.of(
                fieldName + "Count",
                "count" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
        ));

        pluralToSingular(fieldName).ifPresent(singular -> {
            options.add(singular + "CapacityInput");
            options.add(singular + "Count");
        });

        // special cases
        if(fieldName.equals("jointLocations") || fieldName.equals("jointVelocities")){
            options.add("jointCount");
        }
        if(fieldName.equals("viewConfigurationStates")){
            options.add("viewConfigurationCount");
        }
        if(fieldName.equals("viewConfigurationLayersInfo")){
            options.add("viewConfigurationLayerCount");
            options.add("viewConfigurationCount");
        }
        if (fieldName.startsWith("node")){
            options.add("nodeCapacityInput");
        }
        if(fieldName.startsWith("joint")){
            options.add("jointCapacityInput");
        }
        if(fieldName.startsWith("vertex")){
            options.add("vertexCapacityInput");
            options.add("vertexCount");
        }


        for (String option : options) {
            if (hasField.test(option)){
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    private static Optional<String> pluralToSingular(String plural) {
        if(plural.equals("vertices")){
            return Optional.of("vertex");
        } else if (plural.equals("indices")) {
            return Optional.of("index");
        }else if (plural.equals("boxes")){
            return Optional.of("box");
        }else if (plural.equals("spheres")){
            return Optional.of("sphere");
        }else if (plural.endsWith("properties")){
            return Optional.of(plural.replace("properties", "property"));
        }else if(plural.endsWith("Meshes")){
            return Optional.of(plural.replace("Meshes", "Mesh"));
        }else if(plural.endsWith("s")) {
            return Optional.of(plural.substring(0, plural.length() - 1));
        } else if (plural.contains("Layers")) {
            return Optional.of(plural.replace("Layers", "Layer"));
        }else {
            return Optional.empty();
        }
    }
}
