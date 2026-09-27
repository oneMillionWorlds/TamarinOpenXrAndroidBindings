package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import java.util.Collection;

public class ConstParser {

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
            // Java has no unsigned literal suffix, so drop it (e.g. "4000u" -> "4000", "0x1ULL" -> "0x1LL")
            String constantValue = define.constantValue.replaceFirst("^(0x[0-9A-Fa-f]+|[0-9]+)[uU]", "$1");
            if(constantValue.contains("\"")){
                javaType = "String";
            } else if (constantValue.endsWith("L")){
                javaType = "long";
                constantValue = constantValue.replace("LL", "L");
            } else{
                javaType = "int";
            }

            return new Const(javaType, define.constantName, constantValue);
        }

    }

}
