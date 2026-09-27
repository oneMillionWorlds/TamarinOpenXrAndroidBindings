package com.onemillionworlds.tamarin.gradle.tasks.generators;

import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CWrapperFunctionGeneratorTest {

    @Test
    void generateCWrapperFunction_xrEnumerateApiLayerProperties() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrEnumerateApiLayerProperties", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCapacityInput", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint32_t", "propertyCountOutput", true, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrApiLayerProperties", "properties", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrEnumerateApiLayerProperties
                 * Signature: (IJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrEnumerateApiLayerProperties
                  (JNIEnv *env, jclass cls, jint propertyCapacityInput, jlong propertyCountOutput, jlong properties) {

                    // Convert JNI parameters to OpenXR parameters
                    uint32_t propertyCapacityInputValue = (uint32_t)propertyCapacityInput;
                    uint32_t *propertyCountOutputPtr = (uint32_t *)(intptr_t)propertyCountOutput;
                    XrApiLayerProperties *propertiesPtr = (XrApiLayerProperties *)(intptr_t)properties;

                    // Call the OpenXR function
                    XrResult result = xrEnumerateApiLayerProperties(propertyCapacityInputValue, propertyCountOutputPtr, propertiesPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_doubleParameters() {
        // doubles must be jdouble / "D" to match the Java native method's double parameters
        FunctionDefinition functionDefinition = new FunctionDefinition("xrCheckVpsAvailabilityAsyncANDROID", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("double", "latitude", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("double", "longitude", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrFutureEXT", "future", true, false, false, true, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrCheckVpsAvailabilityAsyncANDROID
                 * Signature: (JDDJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrCheckVpsAvailabilityAsyncANDROID
                  (JNIEnv *env, jclass cls, jlong session, jdouble latitude, jdouble longitude, jlong future) {

                    // Convert JNI parameters to OpenXR parameters
                    XrSession sessionHandle = (XrSession)(intptr_t)session;
                    double latitudeValue = (double)latitude;
                    double longitudeValue = (double)longitude;
                    XrFutureEXT *futurePtr = (XrFutureEXT *)(intptr_t)future;

                    // Call the OpenXR function
                    XrResult result = xrCheckVpsAvailabilityAsyncANDROID(sessionHandle, latitudeValue, longitudeValue, futurePtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrGetInstanceProcAddr() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetInstanceProcAddr", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "name", true, true, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("PFN_xrVoidFunction", "function", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrGetInstanceProcAddr
                 * Signature: (JJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrGetInstanceProcAddr
                  (JNIEnv *env, jclass cls, jlong instance, jlong name, jlong function) {

                    // Convert JNI parameters to OpenXR parameters
                    XrInstance instanceHandle = (XrInstance)(intptr_t)instance;
                    char *namePtr = (char *)(intptr_t)name;
                    PFN_xrVoidFunction *functionPtr = (PFN_xrVoidFunction *)(intptr_t)function;

                    // Call the OpenXR function
                    XrResult result = xrGetInstanceProcAddr(instanceHandle, namePtr, functionPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrResultToString() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrResultToString", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrResult", "value", false, false, true, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("char", "buffer", true, false, false, false, false, false, false, false, false, false).setExtraDocumentation("Required size XR_MAX_RESULT_STRING_SIZE"));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrResultToString
                 * Signature: (JIJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrResultToString
                  (JNIEnv *env, jclass cls, jlong instance, jint value, jlong buffer) {

                    // Convert JNI parameters to OpenXR parameters
                    XrInstance instanceHandle = (XrInstance)(intptr_t)instance;
                    XrResult valueValue = (XrResult)value;
                    char *bufferPtr = (char *)(intptr_t)buffer;

                    // Call the OpenXR function
                    XrResult result = xrResultToString(instanceHandle, valueValue, bufferPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);

        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrGetSystem() {
        FunctionDefinition expectedFunctionDefinition = new FunctionDefinition("xrGetSystem", "XrResult");
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemGetInfo", "getInfo", true, true, false, false, false, false, false, false, false, false));
        expectedFunctionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", true, false, false, true, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrGetSystem
                 * Signature: (JJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrGetSystem
                  (JNIEnv *env, jclass cls, jlong instance, jlong getInfo, jlong systemId) {

                    // Convert JNI parameters to OpenXR parameters
                    XrInstance instanceHandle = (XrInstance)(intptr_t)instance;
                    XrSystemGetInfo *getInfoPtr = (XrSystemGetInfo *)(intptr_t)getInfo;
                    XrSystemId *systemIdPtr = (XrSystemId *)(intptr_t)systemId;

                    // Call the OpenXR function
                    XrResult result = xrGetSystem(instanceHandle, getInfoPtr, systemIdPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(expectedFunctionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrLocateSpace() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrLocateSpace", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "space", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpace", "baseSpace", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrTime", "time", false, false, false, false, false, true, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSpaceLocation", "location", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrLocateSpace
                 * Signature: (JJJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrLocateSpace
                  (JNIEnv *env, jclass cls, jlong space, jlong baseSpace, jlong time, jlong location) {

                    // Convert JNI parameters to OpenXR parameters
                    XrSpace spaceHandle = (XrSpace)(intptr_t)space;
                    XrSpace baseSpaceHandle = (XrSpace)(intptr_t)baseSpace;
                    XrTime timeValue = (XrTime)time;
                    XrSpaceLocation *locationPtr = (XrSpaceLocation *)(intptr_t)location;

                    // Call the OpenXR function
                    XrResult result = xrLocateSpace(spaceHandle, baseSpaceHandle, timeValue, locationPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrSetInputDeviceStateVector2fEXT() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrSetInputDeviceStateVector2fEXT", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "topLevelPath", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrPath", "inputSourcePath", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrVector2f", "state", false, false, false, false, false, false, false, false, true, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrSetInputDeviceStateVector2fEXT
                 * Signature: (JJJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrSetInputDeviceStateVector2fEXT
                  (JNIEnv *env, jclass cls, jlong session, jlong topLevelPath, jlong inputSourcePath, jlong state) {

                    // Convert JNI parameters to OpenXR parameters
                    XrSession sessionHandle = (XrSession)(intptr_t)session;
                    XrPath topLevelPathHandle = (XrPath)(intptr_t)topLevelPath;
                    XrPath inputSourcePathHandle = (XrPath)(intptr_t)inputSourcePath;
                    XrVector2f *statePtr = (XrVector2f *)(intptr_t)state;

                    // Call the OpenXR function
                    XrResult result = xrSetInputDeviceStateVector2fEXT(sessionHandle, topLevelPathHandle, inputSourcePathHandle, *statePtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_xrGetSystemProperties() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetSystemProperties", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemProperties", "properties", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrGetSystemProperties
                 * Signature: (JJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrGetSystemProperties
                  (JNIEnv *env, jclass cls, jlong instance, jlong systemId, jlong properties) {

                    // Convert JNI parameters to OpenXR parameters
                    XrInstance instanceHandle = (XrInstance)(intptr_t)instance;
                    XrSystemId systemIdValue = (XrSystemId)systemId;
                    XrSystemProperties *propertiesPtr = (XrSystemProperties *)(intptr_t)properties;

                    // Call the OpenXR function
                    XrResult result = xrGetSystemProperties(instanceHandle, systemIdValue, propertiesPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }
    
    @Test
    void generateCWrapperFunction_xrGetOpenGLESGraphicsRequirementsKHR() {
        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetOpenGLESGraphicsRequirementsKHR", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrInstance", "instance", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSystemId", "systemId", false, false, false, true, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrGraphicsRequirementsOpenGLESKHR", "graphicsRequirements", true, false, false, false, false, false, false, false, false, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrGetOpenGLESGraphicsRequirementsKHR
                 * Signature: (JJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrGetOpenGLESGraphicsRequirementsKHR
                  (JNIEnv *env, jclass cls, jlong instance, jlong systemId, jlong graphicsRequirements) {

                    // Convert JNI parameters to OpenXR parameters
                    XrInstance instanceHandle = (XrInstance)(intptr_t)instance;
                    XrSystemId systemIdValue = (XrSystemId)systemId;
                    XrGraphicsRequirementsOpenGLESKHR *graphicsRequirementsPtr = (XrGraphicsRequirementsOpenGLESKHR *)(intptr_t)graphicsRequirements;

                    // Call the OpenXR function
                    XrResult result = xrGetOpenGLESGraphicsRequirementsKHR(instanceHandle, systemIdValue, graphicsRequirementsPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }

    @Test
    void generateCWrapperFunction_uint64Parameter() {
        // a plain uint64_t must be jlong / "J" to match the Java native method's long parameter (a jint would
        // silently truncate it)
        FunctionDefinition functionDefinition = new FunctionDefinition("xrGetMarkerSizeVARJO", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrSession", "session", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("uint64_t", "markerId", false, false, false, false, false, false, false, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrExtent2Df", "size", true, false, false, false, false, false, false, false, true, false));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrGetMarkerSizeVARJO
                 * Signature: (JJJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrGetMarkerSizeVARJO
                  (JNIEnv *env, jclass cls, jlong session, jlong markerId, jlong size) {

                    // Convert JNI parameters to OpenXR parameters
                    XrSession sessionHandle = (XrSession)(intptr_t)session;
                    XrExtent2Df *sizePtr = (XrExtent2Df *)(intptr_t)size;

                    // Call the OpenXR function
                    XrResult result = xrGetMarkerSizeVARJO(sessionHandle, markerId, sizePtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
        assertEquals("long", functionDefinition.getParameters().get(1).getLowLevelJavaType());
    }

    @Test
    void generateCWrapperFunction_unknownTypeFails() {
        // an unknown type must fail the build rather than silently becoming a jint (which is how uint64_t was once
        // passed truncated)
        FunctionDefinition functionDefinition = new FunctionDefinition("xrSomethingNew", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("int128_t", "value", false, false, false, false, false, false, false, false, false, false));

        assertThrows(RuntimeException.class, () -> CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition));
        assertThrows(RuntimeException.class, () -> functionDefinition.getParameters().get(0).getLowLevelJavaType());
        assertThrows(RuntimeException.class, () -> functionDefinition.getParameters().get(0).getHighLevelJavaType(false));
    }

    @Test
    void generateCWrapperFunction_int32Parameter() {
        FunctionDefinition.FunctionParameter parameter = new FunctionDefinition.FunctionParameter("int32_t", "value", false, false, false, false, false, false, false, false, false, false);
        assertEquals("int", parameter.getLowLevelJavaType());
    }

    @Test
    void generateCWrapperFunction_doublePointer() {
        // the runtime writes a pointer (to a buffer it owns) into the slot, so the slot must be cast to a double pointer
        FunctionDefinition functionDefinition = new FunctionDefinition("xrTriangleMeshGetVertexBufferFB", "XrResult");
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrTriangleMeshFB", "mesh", false, false, false, false, false, false, true, false, false, false));
        functionDefinition.addParameter(new FunctionDefinition.FunctionParameter("XrVector3f", "outVertexBuffer", true, false, false, false, false, false, false, false, false, true));

        String expectedValue = """
                /*
                 * Class:     com_onemillionworlds_tamarin_openxrbindings_XR10
                 * Method:    nxrTriangleMeshGetVertexBufferFB
                 * Signature: (JJ)I
                 */
                JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_nxrTriangleMeshGetVertexBufferFB
                  (JNIEnv *env, jclass cls, jlong mesh, jlong outVertexBuffer) {

                    // Convert JNI parameters to OpenXR parameters
                    XrTriangleMeshFB meshHandle = (XrTriangleMeshFB)(intptr_t)mesh;
                    XrVector3f **outVertexBufferPtr = (XrVector3f **)(intptr_t)outVertexBuffer;

                    // Call the OpenXR function
                    XrResult result = xrTriangleMeshGetVertexBufferFB(meshHandle, outVertexBufferPtr);

                    // Return the result as a jint
                    return (jint)result;
                }
                """;

        String actualValue = CWrapperFunctionGenerator.generateCWrapperFunction(functionDefinition);
        assertEquals(expectedValue.trim(), actualValue.trim());
    }
}