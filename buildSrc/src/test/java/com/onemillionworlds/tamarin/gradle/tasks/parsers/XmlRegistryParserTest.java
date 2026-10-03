package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlRegistryParserTest {

    /**
     * A cut down xr.xml with one of each interesting shape
     */
    private static final String REGISTRY = """
            <registry>
                <types>
                    <type category="define">
            #define <name>XR_NULL_PATH</name> 0</type>
                    <type category="define">
            #if !defined(XR_NULL_HANDLE)
                #define <name>XR_NULL_HANDLE</name> 0
            #endif</type>
                    <type category="basetype">typedef <type>uint32_t</type> <name>XrBool32</name>;</type>
                    <type category="basetype">typedef <type>uint64_t</type> <name>XrFlags64</name>;</type>
                    <type category="basetype"><type>XR_DEFINE_ATOM</type>(<name>XrPath</name>)</type>
                    <type category="handle"><type>XR_DEFINE_HANDLE</type>(<name>XrInstance</name>)</type>
                    <type bitvalues="XrInstanceCreateFlagBits" category="bitmask">typedef <type>XrFlags64</type> <name>XrInstanceCreateFlags</name>;</type>
                    <type name="XrInstanceCreateFlagBits" category="enum"/>
                    <type name="XrStructureType" category="enum"/>
                    <type name="XrResult" category="enum"/>
                    <type name="XrFooModeEXT" category="enum"/>
                    <type requires="EGL/egl.h" name="EGLDisplay"/>
                    <type category="struct" name="XrVector3f">
                        <member><type>float</type> <name>x</name></member>
                        <member><type>float</type> <name>y</name></member>
                        <member><type>float</type> <name>z</name></member>
                    </type>
                    <type category="struct" name="XrFooBaseHeader">
                        <member><type>XrStructureType</type> <name>type</name></member>
                        <member>const <type>void</type>* <name>next</name></member>
                    </type>
                    <type category="struct" name="XrFooChild" parentstruct="XrFooBaseHeader">
                        <member values="XR_TYPE_FOO_CHILD"><type>XrStructureType</type> <name>type</name></member>
                        <member>const <type>void</type>* <name>next</name></member>
                        <member><type>XrInstanceCreateFlags</type> <name>flags</name></member>
                        <member><type>XrPath</type> <name>path</name></member>
                        <member><type>XrBool32</type> <name>enabled</name></member>
                        <member><type>XrInstance</type> <name>instance</name></member>
                        <member><type>XrFooModeEXT</type> <name>mode</name></member>
                        <member><type>uint32_t</type> <name>pointCount</name></member>
                        <member len="pointCount">const <type>XrVector3f</type>* <name>points</name></member>
                        <member>const <type>XrVector3f</type>* <name>origin</name></member>
                        <member len="null-terminated">const <type>char</type>* <name>label</name></member>
                        <member><type>uint32_t</type> <name>enabledNameCount</name></member>
                        <member len="enabledNameCount,null-terminated">const <type>char</type>* const* <name>enabledNames</name></member>
                        <member><type>char</type> <name>name</name>[<enum>XR_MAX_FOO_NAME_SIZE</enum>]</member>
                        <member><type>float</type> <name>matrix</name>[9][3]</member>
                    </type>
                    <type category="struct" name="XrFooChildKHR" alias="XrFooChild"/>
                    <type category="struct" name="XrFooExtra" structextends="XrFooChild,XrFooFrameInfo">
                        <member><type>XrStructureType</type> <name>type</name></member>
                        <member>const <type>void</type>* <name>next</name></member>
                    </type>
                    <type category="struct" name="XrFooFrameInfo">
                        <member><type>XrStructureType</type> <name>type</name></member>
                        <member>const <type>void</type>* <name>next</name></member>
                        <member><type>uint32_t</type> <name>fooCount</name></member>
                        <member len="fooCount">const <type>XrFooBaseHeader</type>* const* <name>foos</name></member>
                    </type>
                    <type category="struct" name="XrEventDataBuffer">
                        <member><type>XrStructureType</type> <name>type</name></member>
                        <member>const <type>void</type>* <name>next</name></member>
                    </type>
                    <type category="struct" name="XrWin32Thing" protect="XR_USE_PLATFORM_WIN32">
                        <member><type>uint32_t</type> <name>value</name></member>
                    </type>
                    <type category="struct" name="XrAndroidThing">
                        <member><type>EGLDisplay</type> <name>display</name></member>
                    </type>
                </types>
                <enums name="API Constants">
                    <enum value="64" name="XR_MAX_FOO_NAME_SIZE"/>
                    <enum value="1" name="XR_TRUE"/>
                </enums>
                <enums name="XrStructureType" type="enum">
                    <enum value="0" name="XR_TYPE_UNKNOWN"/>
                </enums>
                <enums name="XrResult" type="enum">
                    <enum value="0" name="XR_SUCCESS"/>
                    <enum value="-1" name="XR_ERROR_VALIDATION_FAILURE"/>
                </enums>
                <enums name="XrFooModeEXT" type="enum">
                    <enum value="1" name="XR_FOO_MODE_ONE_EXT"/>
                </enums>
                <enums name="XrInstanceCreateFlagBits" type="bitmask">
                    <enum bitpos="0" name="XR_INSTANCE_CREATE_FIRST_BIT"/>
                </enums>
                <commands>
                    <command successcodes="XR_SUCCESS" errorcodes="XR_ERROR_VALIDATION_FAILURE">
                        <proto><type>XrResult</type> <name>xrEnumerateFoos</name></proto>
                        <param><type>XrInstance</type> <name>instance</name></param>
                        <param><type>uint32_t</type> <name>fooCapacityInput</name></param>
                        <param><type>uint32_t</type>* <name>fooCountOutput</name></param>
                        <param len="fooCapacityInput"><type>XrFooChild</type>* <name>foos</name></param>
                        <implicitexternsyncparams>
                            <param>the pname:instance parameter</param>
                        </implicitexternsyncparams>
                    </command>
                    <command>
                        <proto><type>XrResult</type> <name>xrFooToString</name></proto>
                        <param><type>char</type> <name>buffer</name>[<enum>XR_MAX_FOO_NAME_SIZE</enum>]</param>
                    </command>
                    <command name="xrEnumerateFoosKHR" alias="xrEnumerateFoos"/>
                    <command>
                        <proto><type>XrResult</type> <name>xrGetFooPoints</name></proto>
                        <param><type>XrInstance</type> <name>instance</name></param>
                        <param><type>XrVector3f</type>** <name>outPoints</name></param>
                    </command>
                    <command>
                        <proto><type>XrResult</type> <name>xrWin32Only</name></proto>
                        <param><type>XrWin32Thing</type>* <name>thing</name></param>
                    </command>
                    <command>
                        <proto><type>XrResult</type> <name>xrAndroidOnly</name></proto>
                        <param><type>XrAndroidThing</type>* <name>thing</name></param>
                    </command>
                </commands>
                <feature api="openxr" name="XR_VERSION_1_0" number="1.0">
                    <require>
                        <type name="XR_NULL_PATH"/>
                        <type name="XR_NULL_HANDLE"/>
                        <type name="XrResult"/>
                        <type name="XrStructureType"/>
                        <type name="XrInstanceCreateFlags"/>
                        <type name="XrFooBaseHeader"/>
                        <type name="XrFooFrameInfo"/>
                        <type name="XrFooExtra"/>
                        <type name="XrEventDataBuffer"/>
                        <enum name="XR_TRUE"/>
                        <command name="xrEnumerateFoos"/>
                        <command name="xrFooToString"/>
                        <command name="xrGetFooPoints"/>
                    </require>
                </feature>
                <extensions>
                    <extension name="XR_EXT_foo" number="3" supported="openxr">
                        <require>
                            <enum value="1" name="XR_EXT_foo_SPEC_VERSION"/>
                            <enum value="&quot;XR_EXT_foo&quot;" name="XR_EXT_FOO_EXTENSION_NAME"/>
                            <enum value="8" name="XR_FOO_POINT_COUNT_EXT"/>
                            <enum name="XR_EXT_FOO_POINT_COUNT" alias="XR_FOO_POINT_COUNT_EXT"/>
                            <enum offset="0" extends="XrStructureType" name="XR_TYPE_FOO_CHILD"/>
                            <enum offset="1" extends="XrResult" dir="-" name="XR_ERROR_FOO_FAILED_EXT"/>
                            <enum bitpos="1" extends="XrInstanceCreateFlagBits" name="XR_INSTANCE_CREATE_FOO_BIT_EXT"/>
                            <type name="XrFooModeEXT"/>
                        </require>
                    </extension>
                    <extension name="XR_KHR_foo" number="5" supported="openxr">
                        <require>
                            <enum value="&quot;XR_KHR_foo2&quot;" name="XR_KHR_FOO2_EXTENSION_NAME"/>
                            <enum extends="XrStructureType" name="XR_TYPE_FOO_CHILD_KHR" alias="XR_TYPE_FOO_CHILD"/>
                            <type name="XrFooChildKHR"/>
                            <command name="xrEnumerateFoosKHR"/>
                        </require>
                    </extension>
                    <extension name="XR_EXT_disabled" number="6" supported="disabled">
                        <require>
                            <enum offset="0" extends="XrStructureType" name="XR_TYPE_DISABLED_EXT"/>
                        </require>
                    </extension>
                    <extension name="XR_KHR_win32" number="7" supported="openxr" protect="XR_USE_PLATFORM_WIN32">
                        <require>
                            <command name="xrWin32Only"/>
                        </require>
                    </extension>
                    <extension name="XR_KHR_android" number="8" supported="openxr" protect="XR_USE_PLATFORM_ANDROID">
                        <require>
                            <command name="xrAndroidOnly"/>
                        </require>
                    </extension>
                </extensions>
            </registry>
            """;

    private static XmlRegistryParser parse() throws Exception {
        Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(REGISTRY)))
                .getDocumentElement();
        return XmlRegistryParser.parse(root, List.of("XR_USE_PLATFORM_ANDROID"), List.of("EGLDisplay"), List.of());
    }

    private static StructDefinition struct(XmlRegistryParser registry, String name){
        return registry.structs.stream().filter(s -> s.getName().equals(name)).findFirst().orElseThrow();
    }

    private static StructField field(StructDefinition struct, String name){
        return struct.getFields().stream().filter(f -> f.getName().equals(name)).findFirst().orElseThrow();
    }

    private static EnumDefinition enumDefinition(XmlRegistryParser registry, String name){
        return registry.enums.stream().filter(e -> e.getName().equals(name)).findFirst().orElseThrow();
    }

    private static List<String> enumValues(EnumDefinition enumDefinition){
        return enumDefinition.getValues().stream().map(v -> v.getName() + "=" + v.getValue()).toList();
    }

    @Test
    void typesAreClassified() throws Exception {
        XmlRegistryParser registry = parse();

        assertEquals(List.of("XrPath"), registry.atoms);
        assertEquals(List.of("XrBool32"), registry.intTypedefs);
        assertEquals(List.of("XrFlags64", "XrInstanceCreateFlags"), registry.longTypedefs);
        assertEquals(List.of("XrInstanceCreateFlags"), registry.flags);
        assertEquals(List.of("EGLDisplay", "XrInstance"), registry.handles);
    }

    @Test
    void structFieldsAreClassified() throws Exception {
        StructDefinition child = struct(parse(), "XrFooChild");

        assertTrue(field(child, "type").isEnumType());
        assertTrue(field(child, "flags").isFlag());
        assertTrue(field(child, "path").isAtom());
        assertTrue(field(child, "enabled").isTypeDefInt());
        assertTrue(field(child, "instance").isHandle());
        assertTrue(field(child, "mode").isEnumType());

        StructField next = field(child, "next");
        assertTrue(next.isPointer());
        assertTrue(next.isConst());
        assertFalse(next.isStruct());
    }

    @Test
    void structPointerCountsComeFromLen() throws Exception {
        StructDefinition child = struct(parse(), "XrFooChild");

        // len="pointCount"
        StructField points = field(child, "points");
        assertTrue(points.isStruct());
        assertEquals(Optional.of("pointCount"), points.getCountField());
        assertFalse(points.isSingletonStructPointer());
        assertEquals(Optional.of("pointCount"), child.findCountParameterForPointerField("points"));

        // no len, so it points to a single struct
        StructField origin = field(child, "origin");
        assertEquals(Optional.empty(), origin.getCountField());
        assertTrue(origin.isSingletonStructPointer());

        // len="null-terminated" is not a count field
        assertEquals(Optional.empty(), field(child, "label").getCountField());

        // len="enabledNameCount,null-terminated", an array of strings
        StructField enabledNames = field(child, "enabledNames");
        assertTrue(enabledNames.isDoublePointer());
        assertTrue(enabledNames.isConst());
        assertEquals(Optional.of("enabledNameCount"), enabledNames.getCountField());
    }

    @Test
    void arraysOfStructPointersAreStructPointerArrays() throws Exception {
        StructDefinition frameInfo = struct(parse(), "XrFooFrameInfo");

        // const XrFooBaseHeader* const* foos, len="fooCount"
        StructField foos = field(frameInfo, "foos");
        assertTrue(foos.isStruct());
        assertTrue(foos.isDoublePointer());
        assertTrue(foos.isStructPointerArray());
        assertFalse(foos.isSingletonStructPointer());
        assertEquals("XrFooBaseHeader.PointerBuffer", foos.getJavaType());
        assertTrue(foos.setterAlsoSetsCountField());
    }

    @Test
    void structExtendsComesFromXml() throws Exception {
        XmlRegistryParser registry = parse();

        assertEquals(List.of("XrFooChild", "XrFooFrameInfo"), struct(registry, "XrFooExtra").getStructExtends());
        assertEquals(List.of(), struct(registry, "XrFooChild").getStructExtends());
    }

    @Test
    void headerViewsAreAdded() throws Exception {
        XmlRegistryParser registry = parse();

        assertEquals(Optional.of("XrEventDataBaseHeader"), struct(registry, "XrEventDataBuffer").getHeaderView());
        assertEquals(Optional.empty(), struct(registry, "XrFooChild").getHeaderView());
    }

    @Test
    void structArraysAreRead() throws Exception {
        StructDefinition child = struct(parse(), "XrFooChild");

        assertEquals("XR_MAX_FOO_NAME_SIZE", field(child, "name").getArraySizeConstant());
        assertFalse(field(child, "name").isPointer());
        // multidimensional arrays are flattened
        assertEquals("9 * 3", field(child, "matrix").getArraySizeConstant());
    }

    @Test
    void structTypeAndParentComeFromXml() throws Exception {
        XmlRegistryParser registry = parse();

        StructDefinition child = struct(registry, "XrFooChild");
        assertEquals(Optional.of("XR_TYPE_FOO_CHILD"), child.getXrStructureTypeEnumValue());
        assertEquals(Optional.of("XrFooBaseHeader"), child.getBaseHeader());

        // no "values" on its type member, so it is abstract
        StructDefinition baseHeader = struct(registry, "XrFooBaseHeader");
        assertEquals(Optional.empty(), baseHeader.getXrStructureTypeEnumValue());

        StructDefinition vector = struct(registry, "XrVector3f");
        assertEquals(Optional.empty(), vector.getXrStructureTypeEnumValue());
    }

    @Test
    void structAliasCopiesItsTarget() throws Exception {
        XmlRegistryParser registry = parse();

        StructDefinition target = struct(registry, "XrFooChild");
        StructDefinition alias = struct(registry, "XrFooChildKHR");
        assertEquals(target.getFields(), alias.getFields());
        assertEquals(Optional.of("XR_TYPE_FOO_CHILD"), alias.getXrStructureTypeEnumValue());
        // the alias doesn't take part in its target's parent/child relationships
        assertEquals(Optional.empty(), alias.getBaseHeader());
    }

    @Test
    void protectedTypesAndCommandsAreOnlyIncludedIfEnabled() throws Exception {
        XmlRegistryParser registry = parse();

        List<String> structNames = registry.structs.stream().map(StructDefinition::getName).toList();
        assertTrue(structNames.contains("XrAndroidThing"));
        assertFalse(structNames.contains("XrWin32Thing"));

        List<String> functionNames = registry.functions.stream().map(FunctionDefinition::getName).toList();
        assertTrue(functionNames.contains("xrAndroidOnly"));
        assertFalse(functionNames.contains("xrWin32Only"));
    }

    @Test
    void commandsAreRead() throws Exception {
        XmlRegistryParser registry = parse();

        FunctionDefinition enumerate = registry.functions.stream().filter(f -> f.getName().equals("xrEnumerateFoos")).findFirst().orElseThrow();
        assertEquals("XrResult", enumerate.getReturnType());
        // the implicitexternsyncparams aren't parameters
        assertEquals(4, enumerate.getParameters().size());

        FunctionDefinition.FunctionParameter instance = enumerate.getParameters().get(0);
        assertTrue(instance.isHandle());
        assertFalse(instance.isPointer());

        FunctionDefinition.FunctionParameter foos = enumerate.getParameters().get(3);
        assertTrue(foos.isStruct());
        assertTrue(foos.isPointer());
        assertFalse(foos.isConst());
        assertEquals(Optional.of("fooCapacityInput"), enumerate.findCountParameterForPointerField("foos"));
        assertEquals(Optional.empty(), enumerate.findCountParameterForPointerField("fooCountOutput"));

        FunctionDefinition toString = registry.functions.stream().filter(f -> f.getName().equals("xrFooToString")).findFirst().orElseThrow();
        FunctionDefinition.FunctionParameter buffer = toString.getParameters().get(0);
        assertTrue(buffer.isPointer());
        assertEquals(Optional.of("Required size XR_MAX_FOO_NAME_SIZE"), buffer.getExtraDocumentation());
    }

    @Test
    void doublePointerParametersAreAPointerSlot() throws Exception {
        XmlRegistryParser registry = parse();

        FunctionDefinition getPoints = registry.functions.stream().filter(f -> f.getName().equals("xrGetFooPoints")).findFirst().orElseThrow();
        FunctionDefinition.FunctionParameter outPoints = getPoints.getParameters().get(1);
        assertTrue(outPoints.isDoublePointer());
        assertTrue(outPoints.isPointer());
        // the runtime writes an address into it, so it isn't treated as a struct (e.g. an out param to validate)
        assertFalse(outPoints.isStruct());
        assertEquals("PointerBufferView", outPoints.getHighLevelJavaType(false));
        assertEquals(Optional.of("A single pointer slot the runtime writes a XrVector3f* into"), outPoints.getExtraDocumentation());
    }

    @Test
    void aliasCommandHasTheSignatureOfWhatItAliases() throws Exception {
        XmlRegistryParser registry = parse();

        FunctionDefinition original = registry.functions.stream().filter(f -> f.getName().equals("xrEnumerateFoos")).findFirst().orElseThrow();
        FunctionDefinition alias = registry.functions.stream().filter(f -> f.getName().equals("xrEnumerateFoosKHR")).findFirst().orElseThrow();
        assertEquals(original.getParameters(), alias.getParameters());
    }

    @Test
    void enumsIncludeSupportedExtensionValues() throws Exception {
        XmlRegistryParser registry = parse();

        // extension values are calculated from the extension number, aliases go last and disabled extensions' values are excluded
        assertEquals(List.of(
                "XR_TYPE_UNKNOWN=0",
                "XR_TYPE_FOO_CHILD=1000002000",
                "XR_TYPE_FOO_CHILD_KHR=1000002000",
                "XR_STRUCTURE_TYPE_MAX_ENUM=0x7FFFFFFF"
        ), enumValues(enumDefinition(registry, "XrStructureType")));

        assertEquals(List.of(
                "XR_SUCCESS=0",
                "XR_ERROR_VALIDATION_FAILURE=-1",
                "XR_ERROR_FOO_FAILED_EXT=-1000002001",
                "XR_RESULT_MAX_ENUM=0x7FFFFFFF"
        ), enumValues(enumDefinition(registry, "XrResult")));

        assertEquals(List.of(
                "XR_FOO_MODE_ONE_EXT=1",
                "XR_FOO_MODE_MAX_ENUM_EXT=0x7FFFFFFF"
        ), enumValues(enumDefinition(registry, "XrFooModeEXT")));
    }

    @Test
    void constantsComeFromDefinesEnumsAndFlagBits() throws Exception {
        XmlRegistryParser registry = parse();

        assertEquals("0", registry.constants.get("XR_NULL_PATH").value);
        // defines inside #if blocks are compiler/platform dependent
        assertNull(registry.constants.get("XR_NULL_HANDLE"));
        assertEquals("1", registry.constants.get("XR_TRUE").value);
        // pulled in because a struct array is sized by it
        assertEquals("64", registry.constants.get("XR_MAX_FOO_NAME_SIZE").value);
        assertEquals("\"XR_EXT_foo\"", registry.constants.get("XR_EXT_FOO_EXTENSION_NAME").value);
        assertEquals("String", registry.constants.get("XR_EXT_FOO_EXTENSION_NAME").type);
        // names with lower case or digits are kept too
        assertEquals("1", registry.constants.get("XR_EXT_foo_SPEC_VERSION").value);
        assertEquals("int", registry.constants.get("XR_EXT_foo_SPEC_VERSION").type);
        assertEquals("\"XR_KHR_foo2\"", registry.constants.get("XR_KHR_FOO2_EXTENSION_NAME").value);
        // an alias refers to what it aliases (which is declared first)
        ConstParser.Const alias = registry.constants.get("XR_EXT_FOO_POINT_COUNT");
        assertEquals("int", alias.type);
        assertEquals("XR_FOO_POINT_COUNT_EXT", alias.value);
        List<String> constantNames = List.copyOf(registry.constants.keySet());
        assertTrue(constantNames.indexOf("XR_FOO_POINT_COUNT_EXT") < constantNames.indexOf("XR_EXT_FOO_POINT_COUNT"));

        ConstParser.Const firstBit = registry.constants.get("XR_INSTANCE_CREATE_FIRST_BIT");
        assertEquals("XrInstanceCreateFlags", firstBit.type);
        assertEquals("0x00000001", firstBit.value);
        assertEquals("0x00000002", registry.constants.get("XR_INSTANCE_CREATE_FOO_BIT_EXT").value);
    }

    @Test
    void khronosExtensionsAreDeclaredBeforeOtherExtensions() throws Exception {
        XmlRegistryParser registry = parse();

        // XR_KHR_foo (number 5) is declared before XR_EXT_foo (number 3), so its command comes first. Core is first of all
        List<String> functionNames = registry.functions.stream().map(FunctionDefinition::getName).toList();
        assertEquals(List.of("xrEnumerateFoos", "xrFooToString", "xrGetFooPoints", "xrEnumerateFoosKHR", "xrAndroidOnly"), functionNames);
    }

    @Test
    void maxEnumName() {
        assertEquals("XR_STRUCTURE_TYPE_MAX_ENUM", XmlRegistryParser.maxEnumName("XrStructureType"));
        assertEquals("XR_PERF_SETTINGS_DOMAIN_MAX_ENUM_EXT", XmlRegistryParser.maxEnumName("XrPerfSettingsDomainEXT"));
        // odd, but this is what the Khronos generator produces (so what is in openxr.h)
        assertEquals("XR_FACE_EXPRESSION_2FB_MAX_ENUM_FB", XmlRegistryParser.maxEnumName("XrFaceExpression2FB"));
    }
}
