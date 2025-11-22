package com.onemillionworlds.tamarin.openxrbindings;

import java.util.Map;

public class StructSetterValidationObject {
    String structName;
    int checkSetCalled;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StructSetterValidationObject{checkSetCalled=");
        for (int i = 31; i >= 0; i--) {
            sb.append((checkSetCalled & (1 << i)) == 0 ? "0" : "1");
        }
        sb.append("}");
        return sb.toString();
    }
    Map<String, Integer> fieldBitMasks;

    public StructSetterValidationObject(String structName, Map<String, Integer> fieldBitMasks) {
        this.fieldBitMasks = fieldBitMasks;
        this.structName = structName;
        this.checkSetCalled = 0; // starts with assumption nothing needs checking (case for calloc and create). For malloc setNeedsToBeCalled() will be called.

    }

    public void setNeedsToValidateAllMethodsCalled(){
        int numberOfFields = fieldBitMasks.size();
        this.checkSetCalled |= (1 << numberOfFields) - 1;
    }

    public void setFieldCalled(String fieldName) {
        @SuppressWarnings("DataFlowIssue") 
        int mask = fieldBitMasks.get(fieldName);
        this.checkSetCalled &= ~mask;
    }

    /**
     * Ensures that, for malloc'ed instances, all field setters have been called before use.
     * If this instance was created with calloc (or copied from another struct), this check is a no-op.
     */
    public void checkValidStateForUse() {
        if (checkSetCalled == 0) { return; }
        StringBuilder missing = new StringBuilder();
        for (Map.Entry<String, Integer> entry : fieldBitMasks.entrySet()) {
            int bitmask = entry.getValue();
            if ((checkSetCalled & bitmask) != 0) {
                if (missing.length() > 0) missing.append(", ");
                missing.append(entry.getKey());
            }
        }
        if (missing.length() > 0) {
            throw new IllegalStateException("Some fields of " + structName + " were not set: " + missing);
        }
    }

    public static Map<String, Integer> createBitFieldMasks(String... fieldNames){
        Map<String, Integer> bitFieldMasks = new java.util.HashMap<>();
        int index = 0;
        for(String fieldName : fieldNames){
            bitFieldMasks.put(fieldName, 1 << index);
            index++;
        }
        return bitFieldMasks;
    }

    
    
}
