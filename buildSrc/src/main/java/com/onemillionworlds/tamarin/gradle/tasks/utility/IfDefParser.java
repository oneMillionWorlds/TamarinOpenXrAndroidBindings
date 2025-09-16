package com.onemillionworlds.tamarin.gradle.tasks.utility;

import java.util.Collection;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for processing C/C++ header files with ifdef and ifndef directives.
 * This class can process a string containing C/C++ code and remove parts that don't
 * conform to the specified definition flags.
 */
public class IfDefParser {

    // Regex patterns for matching ifdef, ifndef, else, endif
    private static final Pattern IFDEF_PATTERN = Pattern.compile("^\\s*#\\s*ifdef\\s+(\\w+).*$");
    private static final Pattern IFNDEF_PATTERN = Pattern.compile("^\\s*#\\s*ifndef\\s+(\\w+).*$");
    private static final Pattern ELSE_PATTERN = Pattern.compile("^\\s*#\\s*else.*$");
    private static final Pattern ENDIF_PATTERN = Pattern.compile("^\\s*#\\s*endif.*$");

    /**
     * Process a string containing C/C++ code with ifdef and ifndef directives.
     * Parts of the code that don't conform to the specified definition flags will be removed.
     *
     * @param input The input string containing C/C++ code with ifdef and ifndef directives
     * @param defFlags A collection of definition flags that are considered defined
     * @return A processed string with parts that don't conform to the definition flags removed
     */
    public static String processIfDefs(String input, Collection<String> defFlags) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] lines = input.split("\\r?\\n");
        
        // Stack to keep track of nested if conditions
        Stack<Boolean> conditionStack = new Stack<>();
        // Stack to keep track of whether we're in an else block
        Stack<Boolean> elseStack = new Stack<>();
        
        // Initially, we're including code (not inside any conditional)
        boolean includeCode = true;
        
        for (String line : lines) {
            Matcher ifdefMatcher = IFDEF_PATTERN.matcher(line);
            Matcher ifndefMatcher = IFNDEF_PATTERN.matcher(line);
            Matcher elseMatcher = ELSE_PATTERN.matcher(line);
            Matcher endifMatcher = ENDIF_PATTERN.matcher(line);
            
            if (ifdefMatcher.matches()) {
                // #ifdef directive
                String flag = ifdefMatcher.group(1);
                boolean condition = defFlags.contains(flag);
                conditionStack.push(condition);
                elseStack.push(false); // Not in an else block yet
                includeCode = includeCode && condition;
            } else if (ifndefMatcher.matches()) {
                // #ifndef directive
                String flag = ifndefMatcher.group(1);
                boolean condition = !defFlags.contains(flag);
                conditionStack.push(condition);
                elseStack.push(false); // Not in an else block yet
                includeCode = includeCode && condition;
            } else if (elseMatcher.matches()) {
                // #else directive
                if (!conditionStack.isEmpty()) {
                    boolean condition = !conditionStack.pop();
                    conditionStack.push(condition);
                    elseStack.pop();
                    elseStack.push(true); // Now in an else block
                    
                    // Recalculate includeCode based on the updated condition stack
                    includeCode = true;
                    for (int i = 0; i < conditionStack.size(); i++) {
                        includeCode = includeCode && conditionStack.get(i);
                    }
                }
            } else if (endifMatcher.matches()) {
                // #endif directive
                if (!conditionStack.isEmpty()) {
                    conditionStack.pop();
                    elseStack.pop();
                    
                    // Recalculate includeCode based on the updated condition stack
                    includeCode = true;
                    for (int i = 0; i < conditionStack.size(); i++) {
                        includeCode = includeCode && conditionStack.get(i);
                    }
                }
            } else if (includeCode) {
                // If we're including code, add the line to the result
                result.append(line).append("\n");
            }
        }
        
        return result.toString();
    }
}