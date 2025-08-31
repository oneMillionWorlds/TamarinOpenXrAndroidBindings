package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.XmlHelper;
import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class XmlStructParser {

    public static Map<String, XmlStruct> parseStructs(List<Element> types){
        Map<String, XmlStruct> structsData = new HashMap<>();

        types.stream()
            .filter(type -> XmlHelper.getAttribute(type, "category").filter(c -> c.equals("struct")).isPresent())
            .map(t -> {
                XmlStruct struct = new XmlStruct();
                struct.name = XmlHelper.getAttribute(t, "name").orElseThrow();
                struct.parentstruct = XmlHelper.getAttribute(t, "parentstruct");
                return struct;
            }).forEach(s -> structsData.put(s.name, s));

        return structsData;
    }

    public static class XmlStruct{
        public String name;
        public Optional<String> parentstruct;

        public void enrich(StructDefinition structDefinition){
            parentstruct.ifPresent(structDefinition::setBaseHeader);
        }
    }
}
