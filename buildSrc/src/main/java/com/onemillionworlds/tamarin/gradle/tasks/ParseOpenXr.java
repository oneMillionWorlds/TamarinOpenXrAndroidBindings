package com.onemillionworlds.tamarin.gradle.tasks;

import com.onemillionworlds.tamarin.gradle.tasks.generators.ConstantsGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.EnumGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.HandleGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.StructGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10Generator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10CGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.ConstParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.XmlFeatureParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.XmlRegistryParser;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Gradle task to parse the OpenXR registry (xr.xml) and generate Java classes for constants, enums, handles and
 * structs, the XR10 bindings and their JNI C implementation.
 */
public class ParseOpenXr extends DefaultTask {

    /**
     * Platform/graphics API specific extensions (and types) are only generated if their "protect" define is one of
     * these; everything else (e.g. Vulkan, D3D, Windows) is dropped
     */
    public static final List<String> ENABLED_PROTECTS = List.of("XR_USE_PLATFORM_ANDROID", "XR_USE_GRAPHICS_API_OPENGL_ES");

    /**
     * Defined in the generated C before including the OpenXR headers, so they declare the same things that were
     * generated (including the extension function prototypes)
     */
    public static final List<String> STANDARD_DEFS = Stream.concat(Stream.of("XR_EXTENSION_PROTOTYPES"), ENABLED_PROTECTS.stream()).toList();

    /**
     * These are extra handle types not defined in xr.xml (they are external EGL types)
     */
    private static final List<String> HANDLES_EXTRA = List.of("EGLDisplay", "EGLConfig", "EGLContext");

    private static final List<String> HAND_WRITTEN_ENUMS = List.of("EGLenum");

    /**
     * Functions that can't work as a generated thin binding (neither the Java nor the C is generated). If needed they
     * should be hand-written in ThickC
     */
    private static final Set<String> FUNCTIONS_TO_SKIP = Set.of(
            /*
             * Outputs the Android Surface as a jobject* which the runtime fills with a JNI local reference. That is only
             * valid during the native call, so it would be dangling by the time Java could read it; the C would need to
             * NewGlobalRef it and return the object
             */
            "xrCreateSwapchainAndroidSurfaceKHR"
    );

    private final RegularFileProperty xrXml = getProject().getObjects().fileProperty();
    private final RegularFileProperty outputDir = getProject().getObjects().fileProperty();
    private final RegularFileProperty cOutputDir = getProject().getObjects().fileProperty();

    @InputFile
    public RegularFileProperty getXrXml() {
        return xrXml;
    }

    @OutputDirectory
    public RegularFileProperty getOutputDir() {
        return outputDir;
    }

    @OutputDirectory
    public RegularFileProperty getcOutputDir() {
        return cOutputDir;
    }

    /**
     * Parses an XML file and returns a Document object that can be traversed.
     *
     * @param xmlFile The XML file to parse
     * @return A Document object representing the parsed XML
     * @throws IOException If there is an error reading the file
     */
    private Document parseXmlFile(File xmlFile) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            return document;
        } catch (ParserConfigurationException | SAXException e) {
            getLogger().error("Error parsing XML file: {}", e.getMessage());
            throw new IOException("Error parsing XML file", e);
        }
    }

    @TaskAction
    public void execute() throws IOException {
        File xrXmlFile = xrXml.getAsFile().get();

        Document xrXmlDocument = parseXmlFile(xrXmlFile);
        getLogger().lifecycle("Successfully parsed XML file: {}", xrXmlFile.getAbsolutePath());

        Element registryRoot = xrXmlDocument.getDocumentElement();
        Set<String> coreCommands = XmlFeatureParser.parseCoreCommands(registryRoot);

        File output = outputDir.getAsFile().get();
        File cOutput = null;
        if (cOutputDir.isPresent()) {
            cOutput = cOutputDir.getAsFile().get();
            if (!cOutput.exists()) {
                cOutput.mkdirs();
            }
        }

        if (!output.exists()) {
            output.mkdirs();
        }

        getLogger().lifecycle("Output directory: {}", output.getAbsolutePath());
        if (cOutput != null) {
            getLogger().lifecycle("Generated C output directory: {}", cOutput.getAbsolutePath());
        }

        XmlRegistryParser registry = XmlRegistryParser.parse(registryRoot, ENABLED_PROTECTS, HANDLES_EXTRA, HAND_WRITTEN_ENUMS);

        Map<String, ConstParser.Const> constants = registry.constants;
        List<StructDefinition> structs = registry.structs;
        List<EnumDefinition> enums = registry.enums;

        List<FunctionDefinition> functions = new ArrayList<>();
        for (FunctionDefinition functionDefinition : registry.functions) {
            if(FUNCTIONS_TO_SKIP.contains(functionDefinition.getName())) {
                getLogger().lifecycle("Function {} can't be a thin binding, skipping", functionDefinition.getName());
            } else {
                functions.add(functionDefinition);
            }
        }

        addEnumValuesUsedAsArraySizes(structs, enums, constants);

        Map<String, List<String>> parentToChildren = new LinkedHashMap<>();
        for (StructDefinition struct : structs) {
            struct.getBaseHeader().ifPresent(baseHeader -> parentToChildren.computeIfAbsent(baseHeader, k -> new ArrayList<>())
                    .add(struct.getName()));
        }
        for (StructDefinition struct : structs) {
            if(parentToChildren.containsKey(struct.getName())) {
                struct.setChildren(parentToChildren.get(struct.getName()));
            }
        }

        getLogger().lifecycle("Found {} structs, {} enums, {} functions, {} handles, {} flags, {} int typedefs, {} long typedefs",
                structs.size(), enums.size(), functions.size(), registry.handles.size(), registry.flags.size(),
                registry.intTypedefs.size(), registry.longTypedefs.size());

        // Generate XR10Constants.java
        new ConstantsGenerator(getLogger(), constants, registry.intTypedefs, registry.longTypedefs).generate(output);

        // Generate enum classes
        for (EnumDefinition enumDef : enums) {
            new EnumGenerator(getLogger(), enumDef).generate(output);
        }

        // Generate struct classes
        for (StructDefinition struct : structs) {
            new StructGenerator(getLogger(), struct).generate(output);
        }

        // Generate handle classes
        for (String handle : registry.handles) {
            new HandleGenerator(getLogger(), handle).generate(output);
        }

        // Generate X10.java with method pairs
        new X10Generator(getLogger(), functions).generate(output);

        // Generate C JNI implementation file
        if (cOutput != null) {
            new X10CGenerator(getLogger(), functions, coreCommands).generate(cOutput);
        }
    }

    /**
     * Some fixed size arrays are sized by an enum value rather than a #define (e.g. gaze[XR_EYE_POSITION_COUNT_FB]).
     * The generated code refers to array sizes as XR10Constants, so those enum values are added as int constants.
     */
    private static void addEnumValuesUsedAsArraySizes(List<StructDefinition> structs, List<EnumDefinition> enums, Map<String, ConstParser.Const> constants){
        Map<String, String> enumValues = new LinkedHashMap<>();
        enums.forEach(e -> e.getValues().forEach(v -> enumValues.put(v.getName(), v.getValue())));

        for(StructDefinition struct : structs){
            for(StructField field : struct.getFields()){
                if(field.getArraySizeConstant() == null){
                    continue;
                }
                for(String sizePart : field.getArraySizeConstant().split("\\s*\\*\\s*")){
                    if(sizePart.startsWith("XR_") && !constants.containsKey(sizePart)){
                        String value = enumValues.get(sizePart);
                        if(value == null){
                            throw new RuntimeException("Unknown array size " + sizePart + " for " + struct.getName() + "." + field.getName());
                        }
                        constants.put(sizePart, new ConstParser.Const("int", sizePart, value));
                    }
                }
            }
        }
    }

}
