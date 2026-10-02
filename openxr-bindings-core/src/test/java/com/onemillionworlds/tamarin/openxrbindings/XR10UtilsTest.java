package com.onemillionworlds.tamarin.openxrbindings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XR10UtilsTest {

    @Test
    public void testXrMakeVersionRoundTrip() {
        // Test round trip: make version -> extract components -> verify same values
        int major = 7;
        int minor = 8;
        int patch = 9;

        long version = XR10Utils.xrMakeVersion(major, minor, patch);

        int extractedMajor = XR10Utils.xrExtractMajorVersion(version);
        int extractedMinor = XR10Utils.xrExtractMinorVersion(version);
        int extractedPatch = XR10Utils.xrExtractPatchVersion(version);

        assertEquals(major, extractedMajor, "Major version should be preserved in round trip");
        assertEquals(minor, extractedMinor, "Minor version should be preserved in round trip");
        assertEquals(patch, extractedPatch, "Patch version should be preserved in round trip");
    }

}
