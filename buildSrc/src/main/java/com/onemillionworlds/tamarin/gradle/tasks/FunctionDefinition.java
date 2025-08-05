package com.onemillionworlds.tamarin.gradle.tasks;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Class representing a function parameter.
     */
    public static class FunctionParameter {
        private final String type;
        private final String name;
        private final boolean isPointer;
        private final boolean isConst;

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
    }
}