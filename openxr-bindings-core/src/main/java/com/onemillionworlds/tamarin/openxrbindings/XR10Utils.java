package com.onemillionworlds.tamarin.openxrbindings;

public class XR10Utils {


    /**
     * Packs the major, minor, and patch version numbers into a single long value (as wanted by openXR)
     */
    public static long xrMakeVersion(int major, int minor, int patch) {
        return ((major & 0xFFFFL) << 48) | ((minor & 0xFFFFL) << 32) | (patch & 0xFFFF_FFFFL);
    }

    public static short xrExtractMajorVersion(long version) {
        return (short)((version >> 48) & 0xFFFFL);
    }

    public static short xrExtractMinorVersion(long version) {
        return (short)((version >> 32) & 0xFFFFL);
    }

    public static int xrExtractPatchVersion(long version) {
        return (int)(version & 0xFFFF_FFFFL);
    }

}
