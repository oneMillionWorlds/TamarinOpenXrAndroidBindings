package com.onemillionworlds.tamarin.openxrbindings;

public class Handle {

    long rawHandle;

    public Handle(long rawHandle) {
        this.rawHandle = rawHandle;
    }

    public long getRawHandle() {
        return rawHandle;
    }
}
