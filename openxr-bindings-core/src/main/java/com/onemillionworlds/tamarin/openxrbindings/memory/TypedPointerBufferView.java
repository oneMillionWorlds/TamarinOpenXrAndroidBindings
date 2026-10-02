package com.onemillionworlds.tamarin.openxrbindings.memory;

import com.onemillionworlds.tamarin.openxrbindings.Struct;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.function.Function;

public class TypedPointerBufferView <T extends Struct<T>> extends PointerBufferView {

    private final Function<Long, T> structConstructor;

    public TypedPointerBufferView(PointerBufferView pointerToAdopt, Function<Long, T> structConstructor) {
        super(pointerToAdopt.getRawBuffer(), pointerToAdopt.getBufferView(), pointerToAdopt.address());
        this.structConstructor = structConstructor;
    }

    public TypedPointerBufferView(ByteBuffer rawBuffer, LongBuffer bufferView, long address, Function<Long, T> structConstructor) {
        super(rawBuffer, bufferView, address);
        this.structConstructor = structConstructor;
    }

    public T getByIndex(int index){
        return structConstructor.apply( getBufferView().get(index));
    }

    public void setByIndex(int index, T struct){
        getBufferView().put(index, struct.address());
    }


}
