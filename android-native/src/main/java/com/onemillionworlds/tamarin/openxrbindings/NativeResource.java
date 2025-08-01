/**
 * Interface for classes that manage native resources.
 */
package com.onemillionworlds.tamarin.openxrbindings;

public interface NativeResource {
    /**
     * Frees the native resources associated with this object.
     * This method should be called when the object is no longer needed.
     */
    void free();
    
    /**
     * Returns the memory address of the native resource.
     * 
     * @return the memory address
     */
    long address();
}