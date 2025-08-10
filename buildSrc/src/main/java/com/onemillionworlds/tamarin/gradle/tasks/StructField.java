package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.Objects;

/**
 * Class representing a struct field.
 */
public class StructField {
    private final String type;
    private final String name;
    private final String arraySizeConstant;
    private final boolean isPointer;
    private final boolean isConst;
    private final boolean isEnumType;
    private final boolean isAtom;
    private final boolean isTypeDefInt;
    private final boolean isTypeDefLong;
    private final boolean isHandle;
    private final boolean isFlag;
    private final boolean isStruct;
    private final boolean isDoublePointer;

    public StructField(String type, String name, String arraySizeConstant, boolean isPointer, boolean isConst, 
                      boolean isEnumType, boolean isAtom, boolean isTypeDefInt, boolean isTypeDefLong, 
                      boolean isHandle, boolean isFlag, boolean isStruct, boolean isDoublePointer) {
        this.type = type;
        this.name = name;
        this.arraySizeConstant = arraySizeConstant;
        this.isPointer = isPointer;
        this.isConst = isConst;
        this.isEnumType = isEnumType;
        this.isAtom = isAtom;
        this.isTypeDefInt = isTypeDefInt;
        this.isTypeDefLong = isTypeDefLong;
        this.isHandle = isHandle;
        this.isFlag = isFlag;
        this.isStruct = isStruct;
        this.isDoublePointer = isDoublePointer;
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

    public boolean isPointer() {
        return isPointer;
    }

    public boolean isConst() {
        return isConst;
    }

    public boolean isEnumType() {
        return isEnumType;
    }

    public boolean isAtom() {
        return isAtom;
    }

    public boolean isTypeDefInt() {
        return isTypeDefInt;
    }

    public boolean isTypeDefLong() {
        return isTypeDefLong;
    }

    public boolean isHandle() {
        return isHandle;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public boolean isStruct() {
        return isStruct;
    }

    public boolean isDoublePointer() {
        return isDoublePointer;
    }

    /**
     * Structs by value are weird. On the java side we still treat them as pointers but then deferernce them on
     * the native side to be passed by value. This is because we can't cope with passing structs by reference on the
     * java side.
     */
    public boolean isStructByValue() {
        return isStruct && !isPointer;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructField that)) return false;
        return isPointer == that.isPointer && 
               isConst == that.isConst && 
               isEnumType == that.isEnumType && 
               isAtom == that.isAtom && 
               isTypeDefInt == that.isTypeDefInt && 
               isTypeDefLong == that.isTypeDefLong && 
               isHandle == that.isHandle && 
               isFlag == that.isFlag && 
               isStruct == that.isStruct && 
               isDoublePointer == that.isDoublePointer && 
               Objects.equals(type, that.type) && 
               Objects.equals(name, that.name) && 
               Objects.equals(arraySizeConstant, that.arraySizeConstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, arraySizeConstant, isPointer, isConst, isEnumType, isAtom, 
                           isTypeDefInt, isTypeDefLong, isHandle, isFlag, isStruct, isDoublePointer);
    }

    @Override
    public String toString() {
        return "StructField{" +
                "type='" + type + "' " +
                ", name='" + name + "' " +
                ", arraySizeConstant='" + arraySizeConstant + "' " +
                (isPointer ? ", isPointer" : "") +
                (isConst ? ", isConst" : "") +
                (isEnumType ? ", isEnumType" : "") +
                (isAtom ? ", isAtom" : "") +
                (isTypeDefInt ? ", isTypeDefInt" : "") +
                (isTypeDefLong ? ", isTypeDefLong" : "") +
                (isHandle ? ", isHandle" : "") +
                (isFlag ? ", isFlag" : "") +
                (isStruct ? ", isStruct" : "") +
                (isDoublePointer ? ", isDoublePointer" : "") +
                '}';
    }
}
