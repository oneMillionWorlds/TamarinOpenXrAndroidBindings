package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.onemillionworlds.tamarin.gradle.tasks.generators.X10Generator.HANDLE_TYPES;

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
                "name='" + name + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameters=" + parameters +
                '}';
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

        private String extraDocumentation;

        public FunctionParameter(String type, String name, boolean isPointer, boolean isConst, boolean isEnumType, boolean isAtom, boolean isTypeDefInt, boolean isTypeDefLong, boolean isHandle) {
            this.type = type;
            this.name = name;
            this.isPointer = isPointer;
            this.isConst = isConst;
            this.isEnumType = isEnumType;
            this.isAtom = isAtom;
            this.isTypeDefInt = isTypeDefInt;
            this.isTypeDefLong = isTypeDefLong;
            this.isHandle = isHandle;
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

        public String getHighLevelJavaType() {
            if (isPointer) {
                if(type.equals("PFN_xrVoidFunction")){
                    return "PointerBufferView";
                }
                if(type.equals("char")){
                    return "BufferAndAddress";
                }
                if(HANDLE_TYPES.contains(type)){
                    return "PointerBufferView";
                }
                if(type.equals("uint32_t") || isTypeDefInt){
                    return "IntBufferView";
                }
                if(isAtom || isTypeDefLong){
                    return "LongBufferView";
                }
                return getType() + ".Buffer";
            }else{
                if(type.equals("uint32_t") || isTypeDefInt){
                    return "int";
                }
                if(isHandle){
                    return "long";
                }
                if(isEnumType){
                    return type;
                }
                if(isAtom || isTypeDefLong){
                    return "long";
                }
                throw new RuntimeException("Unexpected non pointer type: " + this);
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
                   Objects.equals(type, that.type) && 
                   Objects.equals(name, that.name) && 
                   Objects.equals(extraDocumentation, that.extraDocumentation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name, isPointer, isConst, isEnumType, isAtom, isTypeDefInt, isTypeDefLong, isHandle, extraDocumentation);
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
