package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstParser {

    public static Pattern constPattern = Pattern.compile(
            "static\\s+const\\s+" +     // Match "static const" with possible whitespace
                    "([A-Za-z][A-Za-z0-9_]*)\\s+" +  // Type name (starts with letter, followed by letters/numbers/underscore)
                    "([A-Za-z][A-Za-z0-9_]*)\\s*" +  // Variable name
                    "=\\s*" +                    // Equals sign with optional whitespace
                    "(.+);"                      // Value up to semicolon
    );

    public static Optional<Const> parseConst(String line){
        Matcher constMatcher = constPattern.matcher(line);

        if (constMatcher.find()) {
            String type = constMatcher.group(1);
            String name = constMatcher.group(2);
            String value = constMatcher.group(3).trim();
            return Optional.of(new Const(type, name, value));
        }
        return Optional.empty();
    }

    public static class Const{
        public final String type;
        public final String name;
        public final String value;

        public Const(String type, String name, String value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Const{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        public boolean isSimpleType(){
            return type.equals("int") || type.equals("String") || type.equals("long");
        }

        public String render(Collection<String> variablesTypeDeffedInt, Collection<String> variablesTypeDeffedLong){
            if(isSimpleType()){
                return "    public static final " + type + " " + name + " = " + value + ";\n\n";
            } else{

                boolean isInt = variablesTypeDeffedInt.contains(type);
                boolean isLong = variablesTypeDeffedLong.contains(type);

                if(isInt || isLong){
                    String javaType = isInt ? "int" : "long";
                    String value = this.value;
                    if(!value.endsWith("L")){
                        value = value + "L";
                    }

                    return "    /**\n" +
                            "    * " + name + " (" + type + ")\n" +
                            "    */\n" +
                            "    public static final " + javaType + " " + name + " = " + value + ";\n\n";
                }else{
                    System.out.println("INTS " + variablesTypeDeffedInt);
                    System.out.println("LONGS " + variablesTypeDeffedLong);
                    throw new RuntimeException("Unexpected const type: " + type);
                }
            }
        }

        public String getJavaType(){
            if(isSimpleType()){
                return type;
            }else{
                return "Xr" + type;
            }
        }

        public static Const fromDefine(DefinePasser.Define define){

            String javaType;
            String constantValue = define.constantValue;
            if(define.constantValue.contains("\"")){
                javaType = "String";
            } else if (define.constantValue.endsWith("L")){
                javaType = "long";
                constantValue = constantValue.replace("LL", "L");
            } else{
                javaType = "int";
            }

            return new Const(javaType, define.constantName, constantValue);
        }

    }

}
