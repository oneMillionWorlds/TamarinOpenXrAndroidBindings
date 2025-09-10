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

    /**
     * This means this field is a pointer to a struct (which is usually an array) but in this case it is actually
     * just a single object
     */
    private final boolean isSingletonStructPointer;

    // Constants for memory sizes
    private static final String SIZE_1_BYTE = "1";
    private static final String SIZE_2_BYTES = "2";
    private static final String SIZE_4_BYTES = "4";
    private static final String SIZE_8_BYTES = "8";
    private static final String SIZE_POINTER = "POINTER_SIZE";

    public StructField(String type, String name, String arraySizeConstant, boolean isPointer, boolean isConst,
                       boolean isEnumType, boolean isAtom, boolean isTypeDefInt, boolean isTypeDefLong,
                       boolean isHandle, boolean isFlag, boolean isStruct, boolean isDoublePointer, boolean isSingletonStructPointer) {
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
        this.isSingletonStructPointer = isSingletonStructPointer;
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

    public boolean isSingletonStructPointer() {
        return isSingletonStructPointer;
    }

    /**
     * Structs by value are weird. On the java side we still treat them as pointers but then deferernce them on
     * the native side to be passed by value. This is because we can't cope with passing structs by reference on the
     * java side.
     */
    public boolean isStructByValue() {
        return isStruct && !isPointer;
    }

    /**
     * Returns the memory size of this field in bytes as a string.
     * For special sizes like pointer size, returns the constant name (e.g., "POINTER_SIZE").
     * 
     * @return The memory size as a string
     */
    public String getMemorySize() {
        if (type.equals("char")) return SIZE_1_BYTE;
        if (type.equals("int16_t")) return SIZE_2_BYTES;
        if (type.equals("float")) return SIZE_4_BYTES;
        if (type.equals("double")) return SIZE_8_BYTES;
        if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32") || isTypeDefInt) return SIZE_4_BYTES;
        if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion") || 
            isHandle || isAtom || isFlag || isTypeDefLong) return SIZE_8_BYTES;
        if (type.equals("XrStructureType")) return SIZE_4_BYTES;
        if (isPointer || type.startsWith("PFN")) return SIZE_POINTER;

        // Special struct sizes
        if (type.equals("XrVector3f")) return "12";
        if (type.equals("XrQuaternionf")) return "16";
        if (type.equals("XrPosef")) return "28";
        if (type.equals("XrExtent2Df")) return "8";
        if (type.equals("XrFovf")) return "16";
        if (type.equals("XrApplicationInfo")) return "344";
        if (type.equals("XrSystemGraphicsProperties")) return "12";
        if (type.equals("XrSystemTrackingProperties")) return "8";
        if (type.equals("XrFormFactor")) return "4";

        // Default size for other types
        return SIZE_4_BYTES;
    }

    /**
     * Returns the Java type to use for this field in getters and setters.
     * 
     * @return The Java type as a string
     */
    public String getJavaType() {
        if (arraySizeConstant != null && !isStructByValue()) {
            return "ByteBufferView";
        } else if (arraySizeConstant != null && isStructByValue()) {
            return type + ".Buffer";
        }else if (isEnumType) {
            return type;
        }else if(isSingletonStructPointer) {
            return type;
        } else if(isHandle && isPointer){
            return type + ".HandleBuffer";
        } else if(isStruct && isPointer){
            return type + ".Buffer";
        } else if (isPointer || type.startsWith("PFN")) {
            return "long";
        } else if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32") || isTypeDefInt) {
            return "int";
        } else if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion") || 
                  isAtom || isFlag || isTypeDefLong) {
            return "long";
        } else if (isHandle) {
            return type;
        } else if (type.equals("float")) {
            return "float";
        } else if (type.equals("double")) {
            return "double";
        } else if (type.equals("int16_t")) {
            return "short";
        } else if (isStructByValue()) {
            return type;
        }

        // Default to the type itself for other types (likely structs)
        return type;
    }

    /**
     * Returns the memory access method to use for this field in getters.
     * 
     * @return The memory access method as a string
     */
    public String getMemoryAccessMethod() {
        if (arraySizeConstant != null) {
            if (type.equals("char")) {
                return "memByteBuffer";
            } else {
                return "memByteBuffer";
            }
        } else if (isEnumType) {
            return "memGetInt";
        } else if (isPointer || type.startsWith("PFN")) {
            return "memGetAddress";
        } else if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32") || isTypeDefInt) {
            return "memGetInt";
        } else if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion") || 
                  isHandle || isAtom || isFlag || isTypeDefLong) {
            return "memGetLong";
        } else if (type.equals("float")) {
            return "memGetFloat";
        } else if (type.equals("double")) {
            return "memGetDouble";
        } else if (type.equals("int16_t")) {
            return "memGetShort";
        }
        throw new RuntimeException("Unexpected type: " + this);
    }

    public String getMemorySetMethod() {
        if (arraySizeConstant != null) {
            if (type.equals("char")) {
                return "memByteBuffer";
            } else {
                return "memByteBuffer";
            }
        } else if (isEnumType) {
            return "memPutInt";
        } else if (isPointer || type.startsWith("PFN")) {
            return "memPutAddress";
        } else if (type.equals("uint32_t") || type.equals("int32_t") || type.equals("XrBool32") || isTypeDefInt) {
            return "memPutInt";
        } else if (type.equals("uint64_t") || type.equals("int64_t") || type.equals("XrVersion") ||
                isHandle || isAtom || isFlag || isTypeDefLong) {
            return "memPutLong";
        } else if (type.equals("float")) {
            return "memPutFloat";
        } else if (type.equals("double")) {
            return "memPutDouble";
        } else if (type.equals("int16_t")) {
            return "memPutShort";
        }
        throw new RuntimeException("Unexpected type: " + this);
    }

    /**
     * Returns the layout member code for this field.
     * 
     * @return The layout member code as a string
     */
    public String getLayoutMember() {
        if (arraySizeConstant != null) {
            return "Layout.__array(" + SIZE_1_BYTE + ", " + arraySizeConstant + ")";
        } else {
            return "Layout.__member(" + getMemorySize() + ")";
        }
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
