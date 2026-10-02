package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;

public class JavaBufferView<T> {
    private final ByteBuffer rawBuffer;
    private final T bufferView;
    private final long address;

    public JavaBufferView(ByteBuffer rawBuffer, T bufferView, long address) {
        this.rawBuffer = rawBuffer;
        this.bufferView = bufferView;
        this.address = address;
    }

    /**
     * A buffer of the raw bytes (probably not useful)
     */
    public ByteBuffer getRawBuffer() {
        return rawBuffer;
    }

    /**
     * A java view of the raw bytes (probably useful). E.g. IntBuffer, FloatBuffer, etc.
     */
    public T getBufferView() {
        return bufferView;
    }

    /**
     * A native address of the raw bytes, used for passing to native functions.
     */
    public long address() {
        return address;
    }
}
