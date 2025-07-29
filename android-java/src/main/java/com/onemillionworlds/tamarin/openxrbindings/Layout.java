/**
 * Class for defining the layout of native structures.
 */
package com.onemillionworlds.tamarin.openxrbindings;

public class Layout {
    private final int size;
    private final int alignment;
    private final int[] offsets;
    
    private Layout(int size, int alignment, int[] offsets) {
        this.size = size;
        this.alignment = alignment;
        this.offsets = offsets;
    }
    
    /**
     * Returns the size of the structure in bytes.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Returns the alignment of the structure in bytes.
     */
    public int getAlignment() {
        return alignment;
    }
    
    /**
     * Returns the offset of the field at the specified index.
     */
    public int offsetof(int index) {
        return offsets[index];
    }
    
    /**
     * Creates a new structure layout.
     */
    public static Layout __struct(MemberInfo... members) {
        int size = 0;
        int alignment = 1;
        int[] offsets = new int[members.length];
        
        for (int i = 0; i < members.length; i++) {
            MemberInfo member = members[i];
            int memberAlignment = member.getAlignment();
            
            // Align the current size to the member's alignment
            size = align(size, memberAlignment);
            
            // Store the offset of this member
            offsets[i] = size;
            
            // Add the member's size to the total size
            size += member.getSize();
            
            // Update the structure's alignment if necessary
            if (memberAlignment > alignment) {
                alignment = memberAlignment;
            }
        }
        
        // Align the final size to the structure's alignment
        size = align(size, alignment);
        
        return new Layout(size, alignment, offsets);
    }
    
    /**
     * Aligns the specified value to the specified alignment.
     */
    private static int align(int value, int alignment) {
        return (value + alignment - 1) & ~(alignment - 1);
    }
    
    /**
     * Creates a new member info for a primitive type.
     */
    public static MemberInfo __member(int size) {
        return new MemberInfo(size, size);
    }
    
    /**
     * Creates a new member info for a pointer.
     */
    public static MemberInfo __member(int size, int alignment) {
        return new MemberInfo(size, alignment);
    }
    
    /**
     * Creates a new member info for an array.
     */
    public static MemberInfo __array(int elementSize, int length) {
        return new MemberInfo(elementSize * length, elementSize);
    }
    
    /**
     * Class for storing information about a structure member.
     */
    public static class MemberInfo {
        private final int size;
        private final int alignment;
        
        private MemberInfo(int size, int alignment) {
            this.size = size;
            this.alignment = alignment;
        }
        
        /**
         * Returns the size of the member in bytes.
         */
        public int getSize() {
            return size;
        }
        
        /**
         * Returns the alignment of the member in bytes.
         */
        public int getAlignment() {
            return alignment;
        }
    }
}