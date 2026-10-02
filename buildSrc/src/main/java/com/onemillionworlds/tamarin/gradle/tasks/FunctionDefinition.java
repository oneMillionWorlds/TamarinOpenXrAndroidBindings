package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Class representing a function definition.
 */
public class FunctionDefinition {
    private final String name;
    private final String returnType;
    private final List<FunctionParameter> parameters = new ArrayList<>();

    /**
     * The platform/graphics API define (e.g. XR_USE_PLATFORM_WIN32) the function is only available with (the
     * "protect" of the extension that declares it in xr.xml). Null if it is available everywhere
     */
    private String protect;

    public FunctionDefinition(String name, String returnType) {
        this.name = name;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getProtect() {
        return Optional.ofNullable(protect);
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }

    public String getReturnType() {
        return returnType;
    }

    public void addParameter(FunctionParameter parameter) {
        parameters.add(parameter);
    }

    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FunctionDefinition that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(returnType, that.returnType) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, returnType, parameters);
    }

    @Override
    public String toString() {
        return "FunctionDefinition{" +
                "name='" + name + "'\n" +
                ", returnType='" + returnType + "'\n"  +
                ", parameters=\n" + parameters.stream().map(p -> "  " + p + "\n").reduce(String::concat).orElse("") +
                '}';
    }

    /**
     * Pointer parameters usually come with a separate parameter that holds how many items the pointer points to (the
     * "len" of the param in xr.xml). This finds that count parameter
     */
    public Optional<String> findCountParameterForPointerField(String parameterName){
        return parameters.stream()
                .filter(p -> p.getName().equals(parameterName))
                .findFirst()
                .flatMap(FunctionParameter::getCountParameter);
    }

    /**
     * The JNI primitive a native parameter is passed as, in all the places it has to be written
     */
    public enum JniType {
        INT("int", "jint", "I"),
        LONG("long", "jlong", "J"),
        FLOAT("float", "jfloat", "F"),
        DOUBLE("double", "jdouble", "D");

        /**
         * The type on the Java native method
         */
        public final String javaType;
        /**
         * The type on the C JNI function
         */
        public final String cType;
        /**
         * The type in a JNI method signature (e.g. "(IJ)I")
         */
        public final String signature;

        JniType(String javaType, String cType, String signature) {
            this.javaType = javaType;
            this.cType = cType;
            this.signature = signature;
        }
    }

    /**
     * Class representing a function parameter.
     */
    public static class FunctionParameter {
        /**
         * External (platform) types that parameters point to as opaque objects, e.g. a Windows COM IUnknown*. There is
         * nothing in the pointed to memory for Java to read or write so the parameter is just the (long) address, as
         * in LWJGL
         */
        private static final Set<String> OPAQUE_OBJECT_TYPES = Set.of("IUnknown");

        private final String type;
        private final String name;
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

        private String extraDocumentation;

        /**
         * The parameter holding how many items this pointer parameter points to (the "len" of the param in xr.xml)
         */
        private String countParameter;

        public FunctionParameter(String type, String name, boolean isPointer, boolean isConst, boolean isEnumType, boolean isAtom, boolean isTypeDefInt, boolean isTypeDefLong, boolean isHandle, boolean isFlag, boolean isStructType, boolean isDoublePointer) {
            this.type = type;
            this.name = name;
            this.isPointer = isPointer;
            this.isConst = isConst;
            this.isEnumType = isEnumType;
            this.isAtom = isAtom;
            this.isTypeDefInt = isTypeDefInt;
            this.isTypeDefLong = isTypeDefLong;
            this.isHandle = isHandle;
            this.isFlag = isFlag;
            this.isStruct = isStructType;
            this.isDoublePointer = isDoublePointer;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
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
         * A plain uint64_t/int64_t, a Java long (and a jlong in JNI)
         */
        public boolean is64BitInteger() {
            return type.equals("uint64_t") || type.equals("int64_t");
        }

        /**
         * Structs by value are weird. On the java side we still treat them as pointers but then deferernce them on
         * the native side to be passed by value. This is because we can't cope with passing structs by reference on the
         * java side.
         */
        public boolean isStructByValue(){
            return isStruct && !isPointer;
        }

        /**
         * A pointer to an opaque object (see OPAQUE_OBJECT_TYPES), passed from Java as the raw address (a long)
         */
        public boolean isOpaqueObjectPointer(){
            return isPointer && !isDoublePointer && OPAQUE_OBJECT_TYPES.contains(type);
        }

        public String getHighLevelJavaType(boolean hasAnAssociatedCountParameter) {
            if (isPointer || isStructByValue()) {
                if(isDoublePointer){
                    // an out parameter the runtime writes a pointer into (e.g. to a buffer it owns)
                    return "PointerBufferView";
                }
                if(isOpaqueObjectPointer()){
                    return "long";
                }
                if(type.equals("LARGE_INTEGER")){
                    // a Windows 64 bit signed integer (a union of it and its two halves)
                    return "LongBufferView";
                }
                if(type.equals("wchar_t")){
                    // only used by Windows (XR_USE_PLATFORM_WIN32) functions, where it is a UTF-16 code unit
                    return "ByteBufferView";
                }
                if(type.equals("PFN_xrVoidFunction")){
                    return "PointerBufferView";
                }
                if(type.equals("char")){
                    return "ByteBufferView";
                }
                if(isHandle){
                    return type + ".HandleBuffer";
                }
                if(type.equals("uint32_t") || type.equals("int32_t") || isTypeDefInt || isEnumType){
                    return "IntBufferView";
                }
                if(type.equals("uint64_t") || type.equals("int64_t") || isAtom || isTypeDefLong || isFlag){
                    return "LongBufferView";
                }
                if(type.equals("uint8_t")){
                    return "ByteBufferView";
                }
                if(type.equals("uint16_t")){
                    return "ShortBufferView";
                }
                if(type.equals("float")){
                    return "FloatBufferView";
                }
                if(!isStruct){
                    throw new RuntimeException("Unexpected pointer type: " + this);
                }
                if(hasAnAssociatedCountParameter) {
                    return getType() + ".Buffer";
                }
                return getType();
            }else{
                if(type.equals("uint32_t") || isTypeDefInt){
                    return "int";
                }
                if(isHandle){
                    return type;
                }
                if(isEnumType){
                    return type;
                }
                if(isAtom || isTypeDefLong || isFlag || is64BitInteger()){
                    return "long";
                }
                if(type.equals("float")){
                    return "float";
                }
                if(type.equals("double")){
                    return "double";
                }
                throw new RuntimeException("Unexpected non pointer type: " + this);
            }
        }

        /**
         * The type of this parameter on the java method that has the native keyword
         */
        public String getLowLevelJavaType() {
            return getJniType().javaType;
        }

        /**
         * How this parameter crosses JNI. Both the Java native method and the C JNI function are generated from this,
         * so they can't disagree
         */
        public JniType getJniType() {
            if (isPointer || isStructByValue() || isAtom) {
                // pointers and structs are passed as addresses (by value structs are dereferenced in C)
                return JniType.LONG;
            } else if (type.equals("uint32_t") || type.equals("int32_t") || isTypeDefInt) {
                return JniType.INT;
            } else if (isHandle) {
                return JniType.LONG;
            } else if (isEnumType) {
                return JniType.INT;
            } else if (isTypeDefLong || isFlag || is64BitInteger()) {
                return JniType.LONG;
            } else if (type.equals("float")) {
                return JniType.FLOAT;
            } else if (type.equals("double")) {
                return JniType.DOUBLE;
            } else {
                throw new RuntimeException("Unexpected parameter type: " + this);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof FunctionParameter that)) return false;
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
                   Objects.equals(extraDocumentation, that.extraDocumentation) &&
                   Objects.equals(countParameter, that.countParameter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name, isPointer, isConst, isEnumType, isAtom, isTypeDefInt, isTypeDefLong, isHandle, isFlag, isStruct, isDoublePointer, extraDocumentation, countParameter);
        }

        @Override
        public String toString() {
            return "[" + type + " " + name + 
                (isPointer ? " isPointer" : "") + 
                (isConst ? " isConst" : "") + 
                (isAtom ? " isAtom" : "") + 
                (isTypeDefInt ? " isTypeDefInt" : "") + 
                (isTypeDefLong ? " isTypeDefLong" : "") + 
                (isHandle ? " isHandle" : "") + 
                (isFlag ? " isFlag" : "") + 
                (isStruct ? " isStruct" : "") +
                (isEnumType ? " isEnumType" : "") +
                (isDoublePointer ? " isDoublePointer" : "") +
                (countParameter != null ? " countParameter=" + countParameter : "") +
                "]" + (extraDocumentation != null ? " " + extraDocumentation : "") + " ;";
        }

        public Optional<String> getExtraDocumentation() {
            return Optional.ofNullable(extraDocumentation);
        }

        public FunctionParameter setExtraDocumentation(String extraDocumentation) {
            this.extraDocumentation = extraDocumentation;
            return this;
        }

        public Optional<String> getCountParameter() {
            return Optional.ofNullable(countParameter);
        }

        public FunctionParameter setCountParameter(String countParameter) {
            this.countParameter = countParameter;
            return this;
        }
    }
}
