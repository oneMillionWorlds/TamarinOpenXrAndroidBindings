package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.Handle;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.function.Function;

public class HandleBufferView<T extends Handle> extends LongBufferView {

    Function<Long, T> handleConstructor;

    /**
     * Adopt an existing LongBufferView as a HandleBufferView.
     * @param bufferView an existing LongBufferView.
     * @param handleConstructor a function that takes a long and returns a handle.
     */
    public HandleBufferView(LongBufferView bufferView, Function<Long, T> handleConstructor) {
        super(bufferView.getRawBuffer(), bufferView.getBufferView(), bufferView.address());
        this.handleConstructor = handleConstructor;
    }

    public T getByIndex(int index){
        return handleConstructor.apply( getBufferView().get(index));
    }

    public void setByIndex(int index, T handle){
        getBufferView().put(index, handle.getRawHandle());
    }

}
