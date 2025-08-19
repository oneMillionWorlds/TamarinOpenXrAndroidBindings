package com.onemillionworlds.tamarin.gradle.tasks;

import com.onemillionworlds.tamarin.gradle.tasks.generators.ConstantsGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.EnumGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.StructGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10Generator;
import com.onemillionworlds.tamarin.gradle.tasks.generators.X10CGenerator;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.AtomParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.DefinePasser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.EnumParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.FlagsParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.FunctionParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.HandleParser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.IntTypeDefPasser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.LongTypeDefPasser;
import com.onemillionworlds.tamarin.gradle.tasks.parsers.StructParser;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Gradle task to parse OpenXR header files and generate Java classes for constants and structs.
 */
public class CreateStructs extends DefaultTask {

    private final RegularFileProperty headerFile = getProject().getObjects().fileProperty();
    private final RegularFileProperty outputDir = getProject().getObjects().fileProperty();
    private final RegularFileProperty cOutputDir = getProject().getObjects().fileProperty();

    @InputFile
    public RegularFileProperty getHeaderFile() {
        return headerFile;
    }

    @OutputDirectory
    public RegularFileProperty getOutputDir() {
        return outputDir;
    }

    @OutputDirectory
    public RegularFileProperty getcOutputDir() {
        return cOutputDir;
    }

    @TaskAction
    public void execute() throws IOException {
        File header = headerFile.getAsFile().get();
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

        getLogger().lifecycle("Parsing header file: {}", header.getAbsolutePath());
        getLogger().lifecycle("Output directory: {}", output.getAbsolutePath());
        if (cOutput != null) {
            getLogger().lifecycle("Generated C output directory: {}", cOutput.getAbsolutePath());
        }

        // Parse the header file
        Map<String, String> constants = new LinkedHashMap<>();
        List<StructDefinition> structs = new ArrayList<>();
        List<EnumDefinition> enums = new ArrayList<>();
        List<FunctionDefinition> functions = new ArrayList<>();
        List<String> atoms = new ArrayList<>();
        List<String> intTypedefs = new ArrayList<>();
        List<String> longTypedefs = new ArrayList<>();
        List<String> handles = new ArrayList<>();
        List<String> flags = new ArrayList<>();

        parseHeaderFile(header, constants, structs, enums, functions, atoms, intTypedefs, longTypedefs, handles, flags);

        // Log the parsed int and long typedefs
        getLogger().lifecycle("Found {} int typedefs:", intTypedefs.size());
        for (String intTypedef : intTypedefs) {
            getLogger().lifecycle("  {}", intTypedef);
        }

        getLogger().lifecycle("Found {} long typedefs:", longTypedefs.size());
        for (String longTypedef : longTypedefs) {
            getLogger().lifecycle("  {}", longTypedef);
        }

        // Log the parsed handles
        getLogger().lifecycle("Found {} handles:", handles.size());
        for (String handle : handles) {
            getLogger().lifecycle("  {}", handle);
        }

        // Log the parsed flags
        getLogger().lifecycle("Found {} flags:", flags.size());
        for (String flag : flags) {
            getLogger().lifecycle("  {}", flag);
        }

        // Generate XR10Constants.java
        new ConstantsGenerator(getLogger(), constants).generate(output);

        // Generate enum classes
        for (EnumDefinition enumDef : enums) {
            new EnumGenerator(getLogger(), enumDef).generate(output);
        }

        // Generate struct classes
        for (StructDefinition struct : structs) {
            new StructGenerator(getLogger(), struct).generate(output);
        }

        // Generate X10.java with method pairs
        new X10Generator(getLogger(), functions).generate(output);

        // Generate C JNI implementation file
        if (cOutput != null) {
            new X10CGenerator(getLogger(), functions).generate(cOutput);
        }
    }

    private void parseHeaderFile(File headerFile, Map<String, String> constants, List<StructDefinition> structs, List<EnumDefinition> enums, List<FunctionDefinition> functions, List<String> atoms, List<String> intTypedefs, List<String> longTypedefs, List<String> handles, List<String> flags) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(headerFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if we're entering an enum

                if(EnumParser.enumStartPattern.matcher(line).find()) {
                    enums.add(EnumParser.parseEnum(reader, line));
                }
                // Check for function declarations
                Matcher functionMatcher = FunctionParser.functionStartPattern.matcher(line);
                if (functionMatcher.find()) {

                    List<String> knownEnums = enums.stream().map(EnumDefinition::getName).toList();
                    List<String> knownStructs = structs.stream().map(StructDefinition::getName).toList();

                    FunctionDefinition functionDefinition = FunctionParser.parseFunction(reader, line, knownEnums, atoms, intTypedefs, longTypedefs, handles, flags, knownStructs);

                    if(functionDefinition.hasADoublePointer()){
                        // these double pointers are a pain to generate for and we don't plan to use them anyway
                        getLogger().lifecycle("Function {} has a double pointer, skipping", functionDefinition.getName());
                    } else if(functionDefinition.getName().endsWith("META") || functionDefinition.getName().endsWith("METAFunc")){
                        // these meta methods seem to fail to compile and we aren't going to use them anyway
                        getLogger().lifecycle("Skipping META function {}", functionDefinition.getName());
                    } else {
                        functions.add(functionDefinition);
                    }

                }

                // Parse #define constants
                Matcher defineMatcher = DefinePasser.definePattern.matcher(line);
                if (defineMatcher.find()) {
                    DefinePasser.parseDefine(line).ifPresent(define -> {
                        String name = define.constantName;
                        String value = define.constantValue;
                        constants.put(name, value);
                    });
                }

                // Parse atom definitions
                AtomParser.parseAtom(line).ifPresent(atoms::add);

                // Parse int typedefs
                IntTypeDefPasser.parseIntTypedef(line).ifPresent(intTypedefs::add);

                // Parse long typedefs
                LongTypeDefPasser.parseLongTypedef(line).ifPresent(longTypedefs::add);

                // Parse handle definitions
                HandleParser.parseHandle(line).ifPresent(handles::add);

                // Parse flag definitions
                FlagsParser.parseFlags(line).ifPresent(flags::add);

                if(StructParser.structStartPattern.matcher(line).find()) {
                    List<String> knownEnums = enums.stream().map(EnumDefinition::getName).toList();
                    List<String> knownStructs = structs.stream().map(StructDefinition::getName).toList();

                    List<String> xrStructureTypeValues = enums.stream()
                            .filter(e -> e.getName().equals("XrStructureType"))
                            .flatMap(e -> e.getValues().stream())
                            .map(EnumDefinition.EnumValue::getName)
                            .toList();

                    structs.add(StructParser.parseStruct(reader, line, knownEnums, atoms, intTypedefs, longTypedefs, handles, flags, knownStructs, xrStructureTypeValues));
                }
            }
        }
    }

}
