package com.onemillionworlds.tamarin.openxrbindings;

public class XR10Utils {


    /**
     * Packs the major, minor, and patch version numbers into a single long value (as wanted by openXR)
     */
    public static long XR_MAKE_VERSION(int major, int minor, int patch) {
        return ((major & 0xFFFFL) << 48) | ((minor & 0xFFFFL) << 32) | (patch & 0xFFFF_FFFFL);
    }

}
