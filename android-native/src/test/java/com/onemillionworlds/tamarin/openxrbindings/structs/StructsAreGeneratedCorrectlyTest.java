package com.onemillionworlds.tamarin.openxrbindings.structs;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructsAreGeneratedCorrectlyTest {

    private static final File moduleRoot = findModuleRoot();

    @Test
    public void xrActionSetCreateInfoTest(){
        test("XrActionSetCreateInfo");
    }


    public static void test(String structName){
        try {
            String referenceContent = readResourceFile(structName);
            String generatedContent = readGeneratedFile(structName);

            // Compare the files
            assertEquals(referenceContent, generatedContent, 
                    "Generated file " + structName + ".java does not match reference file");

        } catch (IOException e) {

            throw new RuntimeException("Failed to read files", e);
        }
    }

    private static String readResourceFile(String structName) throws IOException {
        File file = new File(moduleRoot, "src/test/resources/exampleStructs/" + structName + "_reference.java");
        return readSanitisedFile(file);
    }

    private static String readGeneratedFile(String structName) throws IOException {
        File file = new File(moduleRoot, "src/main/generated/java/com/onemillionworlds/tamarin/openxrbindings/" + structName + ".java");

        return readSanitisedFile(file);
    }

    private static String readSanitisedFile(File file) throws IOException {
        return Files.readAllLines(file.toPath())
                .stream()
                .map(String::stripTrailing)
                .collect(Collectors.joining("\n"));
    }

    public static File findModuleRoot(){
        File file = new File(StructsAreGeneratedCorrectlyTest.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        while(!file.getName().equals("android-native")){
            file = file.getParentFile();
        }
        return file;
    }
}
