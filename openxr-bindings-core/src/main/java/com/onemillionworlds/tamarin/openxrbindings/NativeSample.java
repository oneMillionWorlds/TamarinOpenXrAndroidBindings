package com.onemillionworlds.tamarin.openxrbindings;

/**
 * A sample class to verify that the Java source directory in the openxr-bindings-core module
 * is properly configured and recognized as a source root.
 */
public class NativeSample {

    public static void main(String[] args) {
        System.out.println(getGreeting());
    }

    /**
     * A simple method to demonstrate Java code in the openxr-bindings-core module.
     * 
     * @return A greeting message
     */
    public static String getGreeting() {
        return "Hello from openxr-bindings-core module!";
    }
}