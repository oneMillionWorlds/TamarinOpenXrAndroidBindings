package com.onemillionworlds.tamarin.gradle.tasks;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructDefinitionTest {

    @Test
    void findCountParameterForPointerField_iesPlural() {
        Set<String> fields = Set.of("entityCount", "entities", "propertyCountOutput", "propertyCapacityInput", "properties");

        assertEquals(Optional.of("entityCount"), StructDefinition.findCountParameterForPointerField("entities", fields::contains));
        assertEquals(Optional.of("propertyCapacityInput"), StructDefinition.findCountParameterForPointerField("properties", fields::contains));
    }
}
