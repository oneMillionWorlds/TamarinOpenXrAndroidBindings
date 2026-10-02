package com.onemillionworlds.tamarin.openxrbindings;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StructSetterValidationObjectTest {

    @Test
    public void testStructSetterValidationObject(){
        Map<String, Integer> fieldBitMasks = StructSetterValidationObject.createBitFieldMasks("Alpha", "Beta", "Gamma");

        StructSetterValidationObject validator = new StructSetterValidationObject("TestStruct", fieldBitMasks);

        assertDoesNotThrow(validator::checkValidStateForUse);
        validator.setNeedsToValidateAllMethodsCalled();

        assertThrows(IllegalStateException.class, validator::checkValidStateForUse);

        validator.setFieldCalled("Alpha");
        assertThrows(IllegalStateException.class, validator::checkValidStateForUse);

        validator.setFieldCalled("Beta");
        assertThrows(IllegalStateException.class, validator::checkValidStateForUse);

        validator.setFieldCalled("Gamma");

        assertDoesNotThrow(validator::checkValidStateForUse);

        // calling the method twice is fine
        validator.setFieldCalled("Gamma");
        assertDoesNotThrow(validator::checkValidStateForUse);
    }

}