package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.CreateStructs;
import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StructParser {

    public static Pattern structStartPattern = Pattern.compile("\\s*typedef\\s+struct\\s+(?:XR_MAY_ALIAS\\s+)?(\\w+)\\s*\\{");
    static Pattern structEndPattern = Pattern.compile("\\s*\\}\\s+(\\w+);");
    static Pattern fieldPattern = Pattern.compile("\\s*((?:const\\s+)?\\w+(?:\\s*\\*(?:\\s*XR_MAY_ALIAS)?)?(?:\\s+const)?)\\s+(\\w+)(?:\\[(XR_[A-Z_]+)\\])?;");


    public static CreateStructs.StructDefinition parseStruct(BufferedReader readerOngoing, String triggeringLine) throws IOException {
        Matcher structStartMatcher = structStartPattern.matcher(triggeringLine);
        if(structStartMatcher.find()){
            String currentStructName = structStartMatcher.group(1);
            CreateStructs.StructDefinition structDefinition = new CreateStructs.StructDefinition(currentStructName);
            while (true) {
                String nextLine = readerOngoing.readLine().replace("XR_MAY_ALIAS", "");
                if (structEndPattern.matcher(nextLine).find()) {
                    return structDefinition;
                }
                Matcher matcher = fieldPattern.matcher(nextLine);
                if(matcher.find()){
                    String type = matcher.group(1);
                    String fieldName = matcher.group(2);
                    String arraySizeConstant = matcher.group(3); // May be null
                    CreateStructs.StructField field = new CreateStructs.StructField(type, fieldName, arraySizeConstant);
                    structDefinition.addField(field);
                }
            }

        }else{
            throw new RuntimeException("Unexpected not a struct: " + triggeringLine);
        }
    }
}
