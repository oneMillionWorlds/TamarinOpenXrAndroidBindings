package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.XmlHelper;
import org.w3c.dom.Element;

import java.util.Set;
import java.util.TreeSet;

public class XmlFeatureParser {

    /**
     * Returns the names of all commands that are part of a core OpenXR version (i.e. required by a {@code <feature>}
     * such as XR_VERSION_1_0 or XR_VERSION_1_1). Every other command belongs to an extension, which the loader does not
     * export, so must be called through a function pointer obtained from xrGetInstanceProcAddr.
     */
    public static Set<String> parseCoreCommands(Element registryRoot){
        Set<String> coreCommands = new TreeSet<>();
        for(Element feature : XmlHelper.getElements(registryRoot, "feature")){
            for(Element command : XmlHelper.getElements(feature, "command")){
                XmlHelper.getAttribute(command, "name").ifPresent(coreCommands::add);
            }
        }
        return coreCommands;
    }
}
