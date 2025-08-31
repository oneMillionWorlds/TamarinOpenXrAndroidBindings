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

    /**
     * XrActionsSyncInfo is interesting because it has a pointer to struct (and a count of that object)
     */
    @Test
    public void xrActionsSyncInfoTest() {
        test("XrActionsSyncInfo");
    }

    /**
     * XrDebugUtilsMessengerCreateInfoEXT is interesting because it has a PFN_xrDebugUtilsMessengerCallbackEXT
     * This is a pointer to a function
     */
    @Test
    public void xrDebugUtilsMessengerCreateInfoExtTest() {
        test("XrDebugUtilsMessengerCreateInfoExt");
    }

    /**
     * XrInstanceCreateInfo is interesting because it is a struct that itself contains structs.
     */
    @Test
    public void xrInstanceCreateInfoTest() {
        test("XrInstanceCreateInfo");
    }

    /**
     * XrInteractionProfileDpadBindingEXT is interesting onHaptic is a singular pointer
     * (with a base class XrHapticBaseHeader but it might be another type)
     */
    @Test
    public void xrInteractionProfileDpadBindingEXTTest() {
        test("XrInteractionProfileDpadBindingEXT");
    }

    /**
     * This one is interesting because actionSets is a pointer to a buffer of handles
     */
    @Test
    public void xrSessionActionSetsAttachInfoTest() {
        test("XrSessionActionSetsAttachInfo");
    }

    /**
     * This one is interesting because uuids is not marked as const so it *looks* like an output for
     * openXr to read into. But the number is held in uuidCount (implying its an input). I believe
     * this extension is done incorrectly and it *should* have been marked const but wasn't.
     * This test makes sure these oddballs generate correctly
     */
    @Test
    public void xrSpaceUuidFilterInfoFBTest() {
        test("XrSpaceUuidFilterInfoFB");
    }

    /**
     * XrSwapchainImageBaseHeader is interesting because it is a base structure type that other types extend.
     * As such, it should not have a type$Default() method.
     */
    @Test
    public void xrSwapchainImageBaseHeaderTest() {
        test("XrSwapchainImageBaseHeader");
    }

    /**
     * XrVisibilityMaskKHR is interesting because the vertices parameter is written INTO by openXR
     */
    @Test
    public void xrVisibilityMaskKHRTest() {
        test("XrVisibilityMaskKHR");
    }

    /**
     * XrHandCapsuleFB is interesting because it has a constant number of XrVector3fs in it
     * (and isn't a pointer)
     */
    @Test
    public void xrHandCapsuleFBTest() {
        test("XrHandCapsuleFB");
    }

    /**
     * xrHapticVibration is interesting because it has a parent class so gets an asParent() method
     */
    @Test
    public void xrHapticVibrationTest(){
        test("XrHapticVibration");
    }

    public static void test(String structName){
        try {
            String referenceContent = readResourceFile(structName);
            String generatedContent = readGeneratedFile(structName);

            // Compare the files
            assertEquals(referenceContent, generatedContent, 
                    "Generated file " + structName + ".java does not match reference file. If the update is intentional run `./gradlew updateReferenceStructs`");

        } catch (IOException e) {

            throw new RuntimeException("Failed to read files", e);
        }
    }

    private static String readResourceFile(String structName) throws IOException {
        File file = new File(moduleRoot, "src/test/resources/referenceStructs/" + structName + "_reference.java");
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
