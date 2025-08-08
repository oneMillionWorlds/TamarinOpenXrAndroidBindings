package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        private String extraDocumentation;

        public FunctionParameter(String type, String name, boolean isPointer, boolean isConst) {
            this.type = type;
            this.name = name;
            this.isPointer = isPointer;
            this.isConst = isConst;
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
                if(type.equals("uint32_t")){
                    return "IntBufferView";
                }
                return getType() + ".Buffer";
            }else{
                if(type.equals("uint32_t")){
                    return "int";
                }
                if(HANDLE_TYPES.contains(type)){
                    return "int";
                }
            }
            throw new RuntimeException("Unexpected pointer type: " + this);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof FunctionParameter that)) return false;
            return isPointer == that.isPointer && isConst == that.isConst && Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(extraDocumentation, that.extraDocumentation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name, isPointer, isConst, extraDocumentation);
        }

        @Override
        public String toString() {
            return "["+type + " " + name + (isPointer ? " isPointer" : "") + (isConst ? " isConst" : "") + "]" + (extraDocumentation != null ? " " + extraDocumentation : "") + " ;";
        }

        public String getExtraDocumentation() {
            return extraDocumentation;
        }

        public FunctionParameter setExtraDocumentation(String extraDocumentation) {
            this.extraDocumentation = extraDocumentation;
            return this;
        }

    }
}