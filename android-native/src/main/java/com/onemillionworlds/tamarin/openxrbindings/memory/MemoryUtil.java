/**
 * Utility class for memory operations.
 */
package com.onemillionworlds.tamarin.openxrbindings.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class MemoryUtil {

    public static final long NULL = 0L;

    static {
        System.loadLibrary("openxrjni");
    }

    /**
     * Returns the number 6
     *
     */
    public static native int test();

    /**
     * Returns the number 6
     *
     */
    public static native int testMultiply(int i, int y);


    /**
     * Returns the number 6
     *
     */
    public static native int testMultiplyLong(int i, int y);


    /**
     * Allocates native memory.
     * 
     * @param size the size in bytes
     * @return the memory address
     */
    public static native long nmemAlloc(long size);

    /**
     * Allocates native memory and checks if the allocation was successful.
     * 
     * @param size the size in bytes
     * @return the memory address
     * @throws OutOfMemoryError if the allocation failed
     */
    public static long nmemAllocChecked(long size) {
        long address = nmemAlloc(size);
        if (address == 0) {
            throw new OutOfMemoryError("Failed to allocate " + size + " bytes");
        }
        return address;
    }

    /**
     * Allocates zeroed native memory.
     * 
     * @param size the size in bytes
     * @return the memory address
     */
    public static native long nmemCalloc(long size);

    /**
     * Allocates zeroed native memory and checks if the allocation was successful.
     * 
     * @param num the number of elements
     * @param size the size of each element in bytes
     * @return the memory address
     * @throws OutOfMemoryError if the allocation failed
     */
    public static long nmemCallocChecked(long num, long size) {
        long address = nmemCalloc(num * size);
        if (address == 0) {
            throw new OutOfMemoryError("Failed to allocate " + (num * size) + " bytes");
        }
        return address;
    }

    /**
     * Frees native memory.
     * 
     * @param address the memory address
     */
    public static native void nmemFree(long address);

    /**
     * Creates a ByteBuffer that represents the specified native memory.
     * 
     * @param address the memory address
     * @param size the size in bytes
     * @return the ByteBuffer
     */
    public static ByteBuffer memByteBuffer(long address, int size) {
        if (address == 0) {
            return null;
        }
        return memSetupBuffer(memNewBuffer(address, size));
    }

    /**
     * Creates a new direct ByteBuffer for the specified memory address.
     * 
     * @param address the memory address
     * @param size the size in bytes
     * @return the ByteBuffer
     */
    private static native ByteBuffer memNewBuffer(long address, int size);

    /**
     * Sets up a ByteBuffer.
     * 
     * @param buffer the ByteBuffer
     * @return the ByteBuffer
     */
    private static ByteBuffer memSetupBuffer(ByteBuffer buffer) {
        buffer.order(ByteOrder.nativeOrder());
        return buffer;
    }

    /**
     * Returns the memory address of a direct ByteBuffer.
     * 
     * @param buffer the ByteBuffer
     * @return the memory address
     */
    public static native long memAddress(ByteBuffer buffer);

    public static native long memAddressIntBuffer(IntBuffer buffer);

    /**
     * Checks if a ByteBuffer can store the specified number of bytes.
     * 
     * @param buffer the ByteBuffer
     * @param size the size in bytes
     * @return the ByteBuffer
     * @throws IllegalArgumentException if the ByteBuffer is not direct or cannot store the specified number of bytes
     */
    public static ByteBuffer __checkContainer(ByteBuffer buffer, int size) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer is not direct");
        }
        if (buffer.remaining() < size) {
            throw new IllegalArgumentException("ByteBuffer remaining capacity is less than " + size);
        }
        return buffer;
    }

    /**
     * Creates a new direct ByteBuffer with the specified capacity.
     * 
     * @param capacity the capacity in bytes
     * @return the ByteBuffer
     */
    public static ByteBuffer __create(int capacity, int alignment) {
        return memSetupBuffer(ByteBuffer.allocateDirect(capacity));
    }

    /**
     * Checks if the specified size is valid for allocation.
     * 
     * @param size the size in bytes
     * @return the size
     * @throws IllegalArgumentException if the size is negative
     */
    public static int __checkMalloc(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative size: " + size);
        }
        return size;
    }

    /**
     * Gets an int value from the specified memory address.
     * 
     * @param address the memory address
     * @return the int value
     */
    public static native int memGetInt(long address);

    /**
     * Gets a long value from the specified memory address.
     * 
     * @param address the memory address
     * @return the long value
     */
    public static native long memGetLong(long address);

    /**
     * Gets a float value from the specified memory address.
     * 
     * @param address the memory address
     * @return the float value
     */
    public static native float memGetFloat(long address);

    /**
     * Gets an address value from the specified memory address.
     * 
     * @param address the memory address
     * @return the address value
     */
    public static native long memGetAddress(long address);

    /**
     * Puts an int value at the specified memory address.
     * 
     * @param address the memory address
     * @param value the int value
     */
    public static native void memPutInt(long address, int value);

    /**
     * Starting at the memory address, sets the specified number of bytes to zero.
     * <p>
     * Used as part of a calloc call
     * </p>
     * @param address the address to start writing zero bytes
     * @param numberOfBytes the number of bytes to write zero bytes for
     */
    public static native void memZeroBytes(long address, int numberOfBytes);

    /**
     * Puts a long value at the specified memory address.
     * 
     * @param address the memory address
     * @param value the long value
     */
    public static native void memPutLong(long address, long value);

    /**
     * Puts an address value at the specified memory address.
     * 
     * @param address the memory address
     * @param value the address value
     */
    public static native void memPutAddress(long address, long value);

    /**
     * Copies memory from one address to another.
     * 
     * @param src the source address
     * @param dst the destination address
     * @param size the size in bytes
     */
    public static native void memCopy(long src, long dst, long size);

    /**
     * Gets a UTF-8 string from the specified memory address.
     * 
     * @param address the memory address
     * @return the string
     */
    public static native String memUTF8(long address);

}
