/*
 * OpenXR Java bindings for Android
 * This file is auto-generated. DO NOT EDIT.
 */
package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.enums.*;
import com.onemillionworlds.tamarin.openxrbindings.handles.*;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryStack;
import com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil;
import com.onemillionworlds.tamarin.openxrbindings.memory.ByteBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.PointerBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.TypedPointerBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.FloatBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.IntBufferView;
import com.onemillionworlds.tamarin.openxrbindings.memory.ShortBufferView;

import java.nio.ByteBuffer;

import java.util.Map;
import java.util.function.Function;
import static com.onemillionworlds.tamarin.openxrbindings.memory.MemoryUtil.*;
import static com.onemillionworlds.tamarin.openxrbindings.BufferUtils.*;
import static com.onemillionworlds.tamarin.openxrbindings.XR10Constants.*;

/**
 * Structure specifying hand tracking mesh f b.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct XrHandTrackingMeshFB {
 *     XrStructureType type;
 *     void* next;
 *     uint32_t jointCapacityInput;
 *     uint32_t jointCountOutput;
 *     XrPosef* jointBindPoses;
 *     float* jointRadii;
 *     XrHandJointEXT* jointParents;
 *     uint32_t vertexCapacityInput;
 *     uint32_t vertexCountOutput;
 *     XrVector3f* vertexPositions;
 *     XrVector3f* vertexNormals;
 *     XrVector2f* vertexUVs;
 *     XrVector4sFB* vertexBlendIndices;
 *     XrVector4f* vertexBlendWeights;
 *     uint32_t indexCapacityInput;
 *     uint32_t indexCountOutput;
 *     int16_t* indices;
 * }</code></pre>
 * @noinspection unused
 */
public class XrHandTrackingMeshFB extends Struct<XrHandTrackingMeshFB> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** Runtime validation bit masks for field setters. */
    private static final Map<String, Integer> FIELD_BIT_MASKS;
    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        JOINTCAPACITYINPUT,
        JOINTCOUNTOUTPUT,
        JOINTBINDPOSES,
        JOINTRADII,
        JOINTPARENTS,
        VERTEXCAPACITYINPUT,
        VERTEXCOUNTOUTPUT,
        VERTEXPOSITIONS,
        VERTEXNORMALS,
        VERTEXUVS,
        VERTEXBLENDINDICES,
        VERTEXBLENDWEIGHTS,
        INDEXCAPACITYINPUT,
        INDEXCOUNTOUTPUT,
        INDICES;

    static {
        Layout layout = Layout.__struct(
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(POINTER_SIZE),
            Layout.__member(4),
            Layout.__member(4),
            Layout.__member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        JOINTCAPACITYINPUT = layout.offsetof(2);
        JOINTCOUNTOUTPUT = layout.offsetof(3);
        JOINTBINDPOSES = layout.offsetof(4);
        JOINTRADII = layout.offsetof(5);
        JOINTPARENTS = layout.offsetof(6);
        VERTEXCAPACITYINPUT = layout.offsetof(7);
        VERTEXCOUNTOUTPUT = layout.offsetof(8);
        VERTEXPOSITIONS = layout.offsetof(9);
        VERTEXNORMALS = layout.offsetof(10);
        VERTEXUVS = layout.offsetof(11);
        VERTEXBLENDINDICES = layout.offsetof(12);
        VERTEXBLENDWEIGHTS = layout.offsetof(13);
        INDEXCAPACITYINPUT = layout.offsetof(14);
        INDEXCOUNTOUTPUT = layout.offsetof(15);
        INDICES = layout.offsetof(16);
        FIELD_BIT_MASKS = StructSetterValidationObject.createBitFieldMasks("type", "next", "jointCapacityInput", "jointCountOutput", "jointBindPoses", "jointRadii", "jointParents", "vertexCapacityInput", "vertexCountOutput", "vertexPositions", "vertexNormals", "vertexUVs", "vertexBlendIndices", "vertexBlendWeights", "indexCapacityInput", "indexCountOutput", "indices");
    }

    protected XrHandTrackingMeshFB(long address, ByteBuffer container) {
        super(address, container);
        this.setterValidation = new StructSetterValidationObject("XrHandTrackingMeshFB", FIELD_BIT_MASKS);
    }

    @Override
    protected XrHandTrackingMeshFB create(long address, ByteBuffer container) {
        return new XrHandTrackingMeshFB(address, container);
    }

    /**
     * Creates a {@code XrHandTrackingMeshFB} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrHandTrackingMeshFB(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
        this.setterValidation = new StructSetterValidationObject("XrHandTrackingMeshFB", FIELD_BIT_MASKS);
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code type} field. */
    public XrStructureType type() {
        return XrStructureType.fromValue(XrHandTrackingMeshFB.ntype(addressUnsafe()));
    }
    /** Returns the value of the {@code next} field. */
    public long next() {
        return nnext(addressUnsafe());
    }
    /** Returns the value of the {@code jointCapacityInput} field. */
    public int jointCapacityInput() {
        return njointCapacityInput(addressUnsafe());
    }
    /** Returns the value of the {@code jointCountOutput} field. */
    public int jointCountOutput() {
        return njointCountOutput(addressUnsafe());
    }
    /** Returns the value of the {@code jointBindPoses} field. */
    public XrPosef.Buffer jointBindPoses() {
        return njointBindPoses(addressUnsafe());
    }
    /** Returns the value of the {@code jointRadii} field. */
    public FloatBufferView jointRadii() {
        return njointRadii(addressUnsafe());
    }
    /** Returns the value of the {@code jointParents} field. */
    public IntBufferView jointParents() {
        return njointParents(addressUnsafe());
    }
    /** Returns the value of the {@code vertexCapacityInput} field. */
    public int vertexCapacityInput() {
        return nvertexCapacityInput(addressUnsafe());
    }
    /** Returns the value of the {@code vertexCountOutput} field. */
    public int vertexCountOutput() {
        return nvertexCountOutput(addressUnsafe());
    }
    /** Returns the value of the {@code vertexPositions} field. */
    public XrVector3f.Buffer vertexPositions() {
        return nvertexPositions(addressUnsafe());
    }
    /** Returns the value of the {@code vertexNormals} field. */
    public XrVector3f.Buffer vertexNormals() {
        return nvertexNormals(addressUnsafe());
    }
    /** Returns the value of the {@code vertexUVs} field. */
    public XrVector2f.Buffer vertexUVs() {
        return nvertexUVs(addressUnsafe());
    }
    /** Returns the value of the {@code vertexBlendIndices} field. */
    public XrVector4sFB.Buffer vertexBlendIndices() {
        return nvertexBlendIndices(addressUnsafe());
    }
    /** Returns the value of the {@code vertexBlendWeights} field. */
    public XrVector4f.Buffer vertexBlendWeights() {
        return nvertexBlendWeights(addressUnsafe());
    }
    /** Returns the value of the {@code indexCapacityInput} field. */
    public int indexCapacityInput() {
        return nindexCapacityInput(addressUnsafe());
    }
    /** Returns the value of the {@code indexCountOutput} field. */
    public int indexCountOutput() {
        return nindexCountOutput(addressUnsafe());
    }
    /** Returns the value of the {@code indices} field. */
    public ShortBufferView indices() {
        return nindices(addressUnsafe());
    }

    /** Sets the specified value to the {@code type} field. */
    public XrHandTrackingMeshFB type(XrStructureType value) { 
        XrHandTrackingMeshFB.ntype(addressUnsafe(), value.getValue());
        this.setterValidation.setFieldCalled("type");
        return this;
    }
    /** Sets the specified value to the {@code next} field. */
    public XrHandTrackingMeshFB next(long value) { 
        XrHandTrackingMeshFB.nnext(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("next");
        return this;
    }
    /** Sets the specified value to the {@code jointCapacityInput} field. */
    public XrHandTrackingMeshFB jointCapacityInput(int value) { 
        XrHandTrackingMeshFB.njointCapacityInput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("jointCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code jointCountOutput} field. */
    public XrHandTrackingMeshFB jointCountOutput(int value) { 
        XrHandTrackingMeshFB.njointCountOutput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("jointCountOutput");
        return this;
    }
    /** Sets the specified value to the {@code jointBindPoses} field. */
    public XrHandTrackingMeshFB jointBindPoses(XrPosef.Buffer value) { 
        XrHandTrackingMeshFB.njointBindPoses(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("jointBindPoses");
        if(value != null){ this.setterValidation.setFieldCalled("jointCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code jointRadii} field. */
    public XrHandTrackingMeshFB jointRadii(FloatBufferView value) { 
        XrHandTrackingMeshFB.njointRadii(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("jointRadii");
        if(value != null){ this.setterValidation.setFieldCalled("jointCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code jointParents} field. */
    public XrHandTrackingMeshFB jointParents(IntBufferView value) { 
        XrHandTrackingMeshFB.njointParents(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("jointParents");
        if(value != null){ this.setterValidation.setFieldCalled("jointCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code vertexCapacityInput} field. */
    public XrHandTrackingMeshFB vertexCapacityInput(int value) { 
        XrHandTrackingMeshFB.nvertexCapacityInput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code vertexCountOutput} field. */
    public XrHandTrackingMeshFB vertexCountOutput(int value) { 
        XrHandTrackingMeshFB.nvertexCountOutput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexCountOutput");
        return this;
    }
    /** Sets the specified value to the {@code vertexPositions} field. */
    public XrHandTrackingMeshFB vertexPositions(XrVector3f.Buffer value) { 
        XrHandTrackingMeshFB.nvertexPositions(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexPositions");
        if(value != null){ this.setterValidation.setFieldCalled("vertexCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code vertexNormals} field. */
    public XrHandTrackingMeshFB vertexNormals(XrVector3f.Buffer value) { 
        XrHandTrackingMeshFB.nvertexNormals(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexNormals");
        if(value != null){ this.setterValidation.setFieldCalled("vertexCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code vertexUVs} field. */
    public XrHandTrackingMeshFB vertexUVs(XrVector2f.Buffer value) { 
        XrHandTrackingMeshFB.nvertexUVs(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexUVs");
        if(value != null){ this.setterValidation.setFieldCalled("vertexCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code vertexBlendIndices} field. */
    public XrHandTrackingMeshFB vertexBlendIndices(XrVector4sFB.Buffer value) { 
        XrHandTrackingMeshFB.nvertexBlendIndices(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexBlendIndices");
        if(value != null){ this.setterValidation.setFieldCalled("vertexCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code vertexBlendWeights} field. */
    public XrHandTrackingMeshFB vertexBlendWeights(XrVector4f.Buffer value) { 
        XrHandTrackingMeshFB.nvertexBlendWeights(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("vertexBlendWeights");
        if(value != null){ this.setterValidation.setFieldCalled("vertexCapacityInput"); }
        return this;
    }
    /** Sets the specified value to the {@code indexCapacityInput} field. */
    public XrHandTrackingMeshFB indexCapacityInput(int value) { 
        XrHandTrackingMeshFB.nindexCapacityInput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indexCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code indexCountOutput} field. */
    public XrHandTrackingMeshFB indexCountOutput(int value) { 
        XrHandTrackingMeshFB.nindexCountOutput(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indexCountOutput");
        return this;
    }
    /** Sets the specified value to the {@code indices} field. */
    public XrHandTrackingMeshFB indices(ShortBufferView value) { 
        XrHandTrackingMeshFB.nindices(addressUnsafe(), value);
        this.setterValidation.setFieldCalled("indices");
        this.setterValidation.setFieldCalled("indexCapacityInput");
        return this;
    }
    /** Sets the specified value to the {@code type} field. */
    public XrHandTrackingMeshFB type$Default() { return type(XrStructureType.XR_TYPE_HAND_TRACKING_MESH_FB); }

    /** Initializes this struct with the specified values. */
    public XrHandTrackingMeshFB set(
        XrStructureType type,
        long next,
        int jointCapacityInput,
        int jointCountOutput,
        XrPosef.Buffer jointBindPoses,
        FloatBufferView jointRadii,
        IntBufferView jointParents,
        int vertexCapacityInput,
        int vertexCountOutput,
        XrVector3f.Buffer vertexPositions,
        XrVector3f.Buffer vertexNormals,
        XrVector2f.Buffer vertexUVs,
        XrVector4sFB.Buffer vertexBlendIndices,
        XrVector4f.Buffer vertexBlendWeights,
        int indexCapacityInput,
        int indexCountOutput,
        ShortBufferView indices
    ) {
        type(type);
        next(next);
        jointCapacityInput(jointCapacityInput);
        jointCountOutput(jointCountOutput);
        jointBindPoses(jointBindPoses);
        jointRadii(jointRadii);
        jointParents(jointParents);
        vertexCapacityInput(vertexCapacityInput);
        vertexCountOutput(vertexCountOutput);
        vertexPositions(vertexPositions);
        vertexNormals(vertexNormals);
        vertexUVs(vertexUVs);
        vertexBlendIndices(vertexBlendIndices);
        vertexBlendWeights(vertexBlendWeights);
        indexCapacityInput(indexCapacityInput);
        indexCountOutput(indexCountOutput);
        indices(indices);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrHandTrackingMeshFB set(XrHandTrackingMeshFB src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("XrHandTrackingMeshFB{");
        sb.append("type=");
        sb.append(String.valueOf(type()));
        sb.append(", ");
        sb.append("next=");
        sb.append(String.valueOf(next()));
        sb.append(", ");
        sb.append("jointCapacityInput=");
        sb.append(String.valueOf(jointCapacityInput()));
        sb.append(", ");
        sb.append("jointCountOutput=");
        sb.append(String.valueOf(jointCountOutput()));
        sb.append(", ");
        sb.append("jointBindPoses=");
        sb.append(String.valueOf(jointBindPoses()));
        sb.append(", ");
        sb.append("jointRadii=");
        sb.append(String.valueOf(jointRadii()));
        sb.append(", ");
        sb.append("jointParents=");
        sb.append(String.valueOf(jointParents()));
        sb.append(", ");
        sb.append("vertexCapacityInput=");
        sb.append(String.valueOf(vertexCapacityInput()));
        sb.append(", ");
        sb.append("vertexCountOutput=");
        sb.append(String.valueOf(vertexCountOutput()));
        sb.append(", ");
        sb.append("vertexPositions=");
        sb.append(String.valueOf(vertexPositions()));
        sb.append(", ");
        sb.append("vertexNormals=");
        sb.append(String.valueOf(vertexNormals()));
        sb.append(", ");
        sb.append("vertexUVs=");
        sb.append(String.valueOf(vertexUVs()));
        sb.append(", ");
        sb.append("vertexBlendIndices=");
        sb.append(String.valueOf(vertexBlendIndices()));
        sb.append(", ");
        sb.append("vertexBlendWeights=");
        sb.append(String.valueOf(vertexBlendWeights()));
        sb.append(", ");
        sb.append("indexCapacityInput=");
        sb.append(String.valueOf(indexCapacityInput()));
        sb.append(", ");
        sb.append("indexCountOutput=");
        sb.append(String.valueOf(indexCountOutput()));
        sb.append(", ");
        sb.append("indices=");
        sb.append(String.valueOf(indices()));
        sb.append('}');
        return sb.toString();
    }

    private final StructSetterValidationObject setterValidation;

    /**
     * Ensures that, for malloc'ed instances, all field setters have been called before use.
     * If this instance was created with calloc (or copied from another struct), this check is a no-op.
     */
    public void checkValidStateForUse() {
        setterValidation.checkValidStateForUse();
    }

    /**
     * Informs this struct that it has been malloced and so must have setter validation carried out
     */
    @Override
    public void setNeedsToValidateAllMethodsCalled() {
        setterValidation.setNeedsToValidateAllMethodsCalled();
    }

    /**
     * Informs this struct that it no longer needs setter validation carried out (maybe because it is an out parameter)
     */
    @Override
    public void setNoLongerNeedsToValidateAllMethodsCalled() {
        setterValidation.setNoLongerNeedsToValidateAllMethodsCalled();
    }

    // -----------------------------------

    /** Returns a new {@code XrHandTrackingMeshFB} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed. */
    public static XrHandTrackingMeshFB malloc() {
        XrHandTrackingMeshFB instance = new XrHandTrackingMeshFB(nmemAllocChecked(SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /** Returns a new {@code XrHandTrackingMeshFB} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed. */
    public static XrHandTrackingMeshFB calloc() {
        return new XrHandTrackingMeshFB(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrHandTrackingMeshFB} instance allocated with {@link BufferUtils}. */
    public static XrHandTrackingMeshFB create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrHandTrackingMeshFB(memAddress(container), container);
    }

    /** Returns a new {@code XrHandTrackingMeshFB} instance for the specified memory address. */
    public static XrHandTrackingMeshFB create(long address) {
        return new XrHandTrackingMeshFB(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrHandTrackingMeshFB createSafe(long address) {
        return address == 0 ? null : new XrHandTrackingMeshFB(address, null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemAlloc nmemAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity) {
        Buffer buf = new Buffer(nmemAllocChecked(__checkMalloc(capacity * SIZEOF)), capacity);
        buf.markAllAsNeedsValidation();
        return buf;
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#nmemCalloc nmemCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer create(int capacity) {
        ByteBuffer container = __create(capacity * SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static Buffer createSafe(long address, int capacity) {
        return address == 0 ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code XrHandTrackingMeshFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHandTrackingMeshFB malloc(MemoryStack stack) {
        XrHandTrackingMeshFB instance = new XrHandTrackingMeshFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
        instance.setterValidation.setNeedsToValidateAllMethodsCalled();
        return instance;
    }

    /**
     * Returns a new {@code XrHandTrackingMeshFB} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrHandTrackingMeshFB calloc(MemoryStack stack) {
        return new XrHandTrackingMeshFB(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity, MemoryStack stack) {
        Buffer buf = new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
        buf.markAllAsNeedsValidation();
        return buf;
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** Unsafe version of type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.TYPE); }
    public static void ntype(long struct, int value ) { memPutInt(struct + XrHandTrackingMeshFB.TYPE, value); }
    /** Unsafe version of next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrHandTrackingMeshFB.NEXT); }
    public static void nnext(long struct, long value) { memPutAddress(struct + XrHandTrackingMeshFB.NEXT, value); }
    /** Unsafe version of jointCapacityInput}. */
    public static int njointCapacityInput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.JOINTCAPACITYINPUT); }
    public static void njointCapacityInput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.JOINTCAPACITYINPUT, value); }
    /** Unsafe version of jointCountOutput}. */
    public static int njointCountOutput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.JOINTCOUNTOUTPUT); }
    public static void njointCountOutput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.JOINTCOUNTOUTPUT, value); }
    /** Unsafe version of jointBindPoses}. */
    public static XrPosef.Buffer njointBindPoses(long struct) {
        int count = njointCapacityInput(struct);
        return XrPosef.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.JOINTBINDPOSES),count);
    }
    public static void njointBindPoses(long struct, XrPosef.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + JOINTBINDPOSES, address);
        if(value!=null){
            njointCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of jointRadii}. */
    public static FloatBufferView njointRadii(long struct) {
        int count = (int)njointCapacityInput(struct);
        return FloatBufferView.wrap(memGetAddress(struct + XrHandTrackingMeshFB.JOINTRADII), count);
    }
    public static void njointRadii(long struct, FloatBufferView value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + JOINTRADII, address);
        if(value!=null){
            njointCapacityInput(struct, value.capacity());
        }
    }
    /** Unsafe version of jointParents}. */
    public static IntBufferView njointParents(long struct) {
        int count = (int)njointCapacityInput(struct);
        return IntBufferView.wrap(memGetAddress(struct + XrHandTrackingMeshFB.JOINTPARENTS), count);
    }
    public static void njointParents(long struct, IntBufferView value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + JOINTPARENTS, address);
        if(value!=null){
            njointCapacityInput(struct, value.capacity());
        }
    }
    /** Unsafe version of vertexCapacityInput}. */
    public static int nvertexCapacityInput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.VERTEXCAPACITYINPUT); }
    public static void nvertexCapacityInput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.VERTEXCAPACITYINPUT, value); }
    /** Unsafe version of vertexCountOutput}. */
    public static int nvertexCountOutput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.VERTEXCOUNTOUTPUT); }
    public static void nvertexCountOutput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.VERTEXCOUNTOUTPUT, value); }
    /** Unsafe version of vertexPositions}. */
    public static XrVector3f.Buffer nvertexPositions(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector3f.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.VERTEXPOSITIONS),count);
    }
    public static void nvertexPositions(long struct, XrVector3f.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTEXPOSITIONS, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of vertexNormals}. */
    public static XrVector3f.Buffer nvertexNormals(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector3f.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.VERTEXNORMALS),count);
    }
    public static void nvertexNormals(long struct, XrVector3f.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTEXNORMALS, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of vertexUVs}. */
    public static XrVector2f.Buffer nvertexUVs(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector2f.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.VERTEXUVS),count);
    }
    public static void nvertexUVs(long struct, XrVector2f.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTEXUVS, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of vertexBlendIndices}. */
    public static XrVector4sFB.Buffer nvertexBlendIndices(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector4sFB.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.VERTEXBLENDINDICES),count);
    }
    public static void nvertexBlendIndices(long struct, XrVector4sFB.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTEXBLENDINDICES, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of vertexBlendWeights}. */
    public static XrVector4f.Buffer nvertexBlendWeights(long struct) {
        int count = nvertexCapacityInput(struct);
        return XrVector4f.createSafe(memGetAddress(struct + XrHandTrackingMeshFB.VERTEXBLENDWEIGHTS),count);
    }
    public static void nvertexBlendWeights(long struct, XrVector4f.Buffer value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + VERTEXBLENDWEIGHTS, address);
        if(value!=null){
            nvertexCapacityInput(struct, value.remaining());
        }
    }
    /** Unsafe version of indexCapacityInput}. */
    public static int nindexCapacityInput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.INDEXCAPACITYINPUT); }
    public static void nindexCapacityInput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.INDEXCAPACITYINPUT, value); }
    /** Unsafe version of indexCountOutput}. */
    public static int nindexCountOutput(long struct) { return memGetInt(struct + XrHandTrackingMeshFB.INDEXCOUNTOUTPUT); }
    public static void nindexCountOutput(long struct, int value) { memPutInt(struct + XrHandTrackingMeshFB.INDEXCOUNTOUTPUT, value); }
    /** Unsafe version of indices}. */
    public static ShortBufferView nindices(long struct) {
        int count = (int)nindexCapacityInput(struct);
        return ShortBufferView.wrap(memGetAddress(struct + XrHandTrackingMeshFB.INDICES), count);
    }
    public static void nindices(long struct, ShortBufferView value){
        long address = value == null ? NULL : value.address();
        memPutAddress(struct + INDICES, address);
        nindexCapacityInput(struct, value == null ? 0 : value.capacity());
    }


    // -----------------------------------

    /** A pointer buffer that holds pointers (aka memory addresses) to XrHandTrackingMeshFBs */
    public static class PointerBuffer extends TypedPointerBufferView<XrHandTrackingMeshFB> {
        public PointerBuffer(PointerBufferView underlyingPointerBuffer) {
            super(underlyingPointerBuffer, XrHandTrackingMeshFB::create);
        }
        /** Creates a new TypedPointerBufferView with the specified capacity. (Will be garbage collected do no manually free)*/
        public static PointerBuffer calloc(int capacity) {
            return new PointerBuffer(PointerBufferView.createPointerBufferView(capacity));
        }

        /** Callocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static PointerBuffer calloc(int capacity, MemoryStack stack) {
            return new PointerBuffer(stack.callocPointer(capacity));
        }

        /** Mallocs a new TypedPointerBufferView with the specified capacity. (Will be created on the stack do no manually free)*/
        public static PointerBuffer malloc(int capacity, MemoryStack stack) {
            return new PointerBuffer(stack.mallocPointer(capacity));
        }

    }
    /** An array of {@link XrHandTrackingMeshFB} structs. */
    public static class Buffer extends StructBuffer<XrHandTrackingMeshFB, Buffer> {

        private static final Function<Long,XrHandTrackingMeshFB> ELEMENT_FACTORY = address ->XrHandTrackingMeshFB.create(address);

        /**
         * Creates a new {@code XrHandTrackingMeshFB.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrHandTrackingMeshFB#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(memAddress(container), container, -1, 0, container.remaining() / SIZEOF, container.remaining() / SIZEOF, SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap, SIZEOF);
        }

        Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap, SIZEOF);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected Buffer create(long address, ByteBuffer container, int mark, int position, int limit, int capacity) {
            return new Buffer(address, container, mark, position, limit, capacity);
        }

        @Override
        protected Function<Long,XrHandTrackingMeshFB> getElementFactory() {
            return ELEMENT_FACTORY;
        }

    }
}
