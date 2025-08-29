package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class representing a function definition.
 */
public class FunctionDefinition {
    private final String name;
    private final String returnType;
    private final List<FunctionParameter> parameters = new ArrayList<>();

    public FunctionDefinition(String name, String returnType) {
        this.name = name;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
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

    public boolean hasADoublePointer() {
        return parameters.stream().anyMatch(FunctionParameter::isDoublePointer);
    }

    /**
     * Class representing a function parameter.
     */
    public static class FunctionParameter {
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
         * Structs by value are weird. On the java side we still treat them as pointers but then deferernce them on
         * the native side to be passed by value. This is because we can't cope with passing structs by reference on the
         * java side.
         */
        public boolean isStructByValue(){
            return isStruct && !isPointer;
        }

        public String getHighLevelJavaType() {
            if (isPointer || isStructByValue()) {
                if(type.equals("PFN_xrVoidFunction")){
                    return "PointerBufferView";
                }
                if(type.equals("char")){
                    return "BufferAndAddress";
                }
                if(isHandle){
                    return type + ".Buffer";
                }
                if(type.equals("uint32_t") || type.equals("int32_t") || isTypeDefInt || isEnumType){
                    return "IntBufferView";
                }
                if(type.equals("uint64_t") || type.equals("int64_t") || isAtom || isTypeDefLong || isFlag){
                    return "LongBufferView";
                }
                if(type.equals("uint8_t")){
                    return "BufferAndAddress";
                }
                if(type.equals("float")){
                    return "FloatBufferView";
                }
                return getType() + ".Buffer";
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
                if(isAtom || isTypeDefLong || isFlag || type.equals("uint64_t") ){
                    return "long";
                }
                if(type.equals("float")){
                    return "float";
                }
                throw new RuntimeException("Unexpected non pointer type: " + this);
            }
        }

        /**
         * This is the java method that has the native keyword
         */
        public String getLowLevelJavaType() {
            String paramType = getType();
            String paramName = getName();
            boolean isPointer = isPointer();
            boolean isAtom = isAtom();
            boolean isStructByValue = isStructByValue();
            boolean isEnum = isEnumType();

            if (isPointer || isStructByValue ||isAtom) {
                return "long";
            } else if (paramType.equals("uint32_t") || isTypeDefInt()) {
                return "int";
            } else if (isHandle()) {
                return "long";
            } else if (isEnum) {
                return "int";
            } else if (isTypeDefLong() || isFlag() || paramType.equals("uint64_t")) {
                return "long";
            } else if (paramType.equals("float")) {
                return "float";
            } else {
                return "int";
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
                   Objects.equals(extraDocumentation, that.extraDocumentation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name, isPointer, isConst, isEnumType, isAtom, isTypeDefInt, isTypeDefLong, isHandle, isFlag, isStruct, isDoublePointer, extraDocumentation);
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
                "]" + (extraDocumentation != null ? " " + extraDocumentation : "") + " ;";
        }

        public Optional<String> getExtraDocumentation() {
            return Optional.ofNullable(extraDocumentation);
        }

        public FunctionParameter setExtraDocumentation(String extraDocumentation) {
            this.extraDocumentation = extraDocumentation;
            return this;
        }

    }
}
