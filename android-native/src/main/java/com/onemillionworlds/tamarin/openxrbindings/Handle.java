package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;

import java.util.Objects;

public class Handle {

    long rawHandle;

    public Handle(long rawHandle) {
        this.rawHandle = rawHandle;
    }

    public long getRawHandle() {
        return rawHandle;
    }

    /**
     * Returns true if this handle is null. I.e. it is not a valid handle.
     */
    public boolean isNullHandle(){
        return rawHandle == MemoryUtil.NULL;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        Handle handle = (Handle) o;
        return rawHandle == handle.rawHandle;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rawHandle);
    }
}
