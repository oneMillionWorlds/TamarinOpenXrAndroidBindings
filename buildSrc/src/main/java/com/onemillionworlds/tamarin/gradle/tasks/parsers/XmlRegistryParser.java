package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import com.onemillionworlds.tamarin.gradle.XmlHelper;
import com.onemillionworlds.tamarin.gradle.tasks.EnumDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.FunctionDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructDefinition;
import com.onemillionworlds.tamarin.gradle.tasks.StructField;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the OpenXR registry (xr.xml) and produces the model the generators work from.
 * <p>
 * Which types/commands exist, and the order they are declared in, follows the rules of the Khronos header generator
 * (reg.py / cgenerator.py) that produces openxr.h and openxr_platform.h from the same xr.xml: core features and
 * the extensions that are supported by "openxr" are walked in sorted order, each declaring everything it requires
 * (dependencies first) that hasn't been declared already. Extensions (and a few types) with a "protect" attribute
 * (e.g. XR_USE_PLATFORM_ANDROID) are only included if that define is enabled.
 */
public class XmlRegistryParser {

    private static final String API_NAME = "openxr";

    /**
     * Declared by this feature but only written to openxr_loader_negotiation.h, which isn't bound
     */
    private static final String LOADER_FEATURE = "XR_LOADER_VERSION_1_0";

    private static final int EXT_BASE = 1000000000;
    private static final int EXT_BLOCK_SIZE = 1000;

    private static final Pattern ARRAY_DIMENSION_PATTERN = Pattern.compile("\\[\\s*([A-Za-z0-9_]+)\\s*\\]");

    /**
     * Pointer members that are arrays but that xr.xml gives no "len" for (it is only in the spec text), as
     * "Struct.member" to the member holding the count
     */
    private static final Map<String, String> MISSING_LENS = Map.of(
            "XrTriangleMeshCreateInfoFB.vertexBuffer", "vertexCount"
    );

    /**
     * Structs that hold one of a family of structs but that xr.xml doesn't make a child of that family's header (it
     * would break validation), to the header they can be viewed as
     */
    private static final Map<String, String> HEADER_VIEWS = Map.of(
            // xrPollEvent writes an event into the buffer
            "XrEventDataBuffer", "XrEventDataBaseHeader"
    );

    /**
     * Enums that are hand-written rather than generated but can appear as struct fields.
     */
    private final Collection<String> handWrittenEnums;
    private final Collection<String> enabledProtects;

    private final Map<String, Element> typeDict = new LinkedHashMap<>();
    private final Map<String, List<Element>> groupValues = new LinkedHashMap<>();
    private final Map<String, Element> groupDict = new LinkedHashMap<>();
    private final Map<String, Element> enumDict = new HashMap<>();
    private final Map<String, Element> cmdDict = new LinkedHashMap<>();

    private final Set<String> requiredTypes = new HashSet<>();
    private final Set<String> requiredEnums = new HashSet<>();
    private final Set<String> requiredCommands = new HashSet<>();

    private final Set<String> declaredTypes = new HashSet<>();
    private final Set<String> declaredEnums = new HashSet<>();
    private final Set<String> declaredCommands = new HashSet<>();

    /**
     * Bitmask value group name (e.g. XrSpaceLocationFlagBits) to the flags type that uses it (e.g. XrSpaceLocationFlags)
     */
    private final Map<String, String> bitmaskFlagTypes = new HashMap<>();

    private FeatureOutput currentFeature;

    /**
     * All the generated enums and structs, used to classify struct fields and function parameters
     */
    private final Set<String> enumNames = new HashSet<>();
    private final Set<String> structNames = new HashSet<>();

    public final Map<String, ConstParser.Const> constants = new LinkedHashMap<>();
    public final List<StructDefinition> structs = new ArrayList<>();
    public final List<EnumDefinition> enums = new ArrayList<>();
    public final List<FunctionDefinition> functions = new ArrayList<>();
    public final List<String> atoms = new ArrayList<>();
    public final List<String> intTypedefs = new ArrayList<>();
    public final List<String> longTypedefs = new ArrayList<>();
    public final List<String> handles = new ArrayList<>();
    public final List<String> flags = new ArrayList<>();

    /**
     * @param registryRoot the root (registry) element of xr.xml
     * @param enabledProtects the "protect" defines that are enabled (e.g. XR_USE_PLATFORM_ANDROID)
     * @param extraHandles handles that aren't in xr.xml (e.g. EGL types)
     * @param handWrittenEnums enums that are hand-written rather than generated but can appear as struct fields
     */
    public static XmlRegistryParser parse(Element registryRoot, Collection<String> enabledProtects, Collection<String> extraHandles, Collection<String> handWrittenEnums){
        XmlRegistryParser parser = new XmlRegistryParser(enabledProtects, handWrittenEnums);
        parser.handles.addAll(extraHandles);
        parser.parseTree(registryRoot);
        List<FeatureOutput> features = parser.generateFeatures(registryRoot);
        parser.buildModel(features);
        return parser;
    }

    private XmlRegistryParser(Collection<String> enabledProtects, Collection<String> handWrittenEnums) {
        this.enabledProtects = enabledProtects;
        this.handWrittenEnums = handWrittenEnums;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Reading the registry
    // ------------------------------------------------------------------------------------------------------------

    private void parseTree(Element registryRoot){
        for(Element types : XmlHelper.getDirectChildElements(registryRoot, "types")){
            for(Element type : XmlHelper.getDirectChildElements(types, "type")){
                String name = XmlHelper.getAttribute(type, "name")
                        .orElseGet(() -> XmlHelper.getDirectChildElements(type, "name").get(0).getTextContent());
                typeDict.putIfAbsent(name, type);
            }
        }

        for(Element enumsElement : XmlHelper.getDirectChildElements(registryRoot, "enums")){
            String groupName = enumsElement.getAttribute("name");
            groupDict.put(groupName, enumsElement);
            List<Element> values = new ArrayList<>(XmlHelper.getDirectChildElements(enumsElement, "enum"));
            groupValues.put(groupName, values);
            // values of real enum/bitmask types are always required, "API Constants" only when used
            boolean required = enumsElement.hasAttribute("type");
            for(Element value : values){
                enumDict.put(value.getAttribute("name"), value);
                if(required){
                    requiredEnums.add(value.getAttribute("name"));
                }
            }
        }

        for(Element commands : XmlHelper.getDirectChildElements(registryRoot, "commands")){
            for(Element command : XmlHelper.getDirectChildElements(commands, "command")){
                cmdDict.put(commandName(command), command);
            }
        }

        // enums added to an existing enum type by a feature or extension are appended to that type's values, in
        // document order (features first). Other enums in a <require> are constants
        for(Element feature : XmlHelper.getDirectChildElements(registryRoot, "feature")){
            for(Element require : XmlHelper.getDirectChildElements(feature, "require")){
                for(Element enumElement : XmlHelper.getDirectChildElements(require, "enum")){
                    addFeatureEnum(enumElement, null);
                }
            }
        }
        for(Element extensions : XmlHelper.getDirectChildElements(registryRoot, "extensions")){
            for(Element extension : XmlHelper.getDirectChildElements(extensions, "extension")){
                for(Element require : XmlHelper.getDirectChildElements(extension, "require")){
                    for(Element enumElement : XmlHelper.getDirectChildElements(require, "enum")){
                        addFeatureEnum(enumElement, extension);
                    }
                }
            }
        }
    }

    private void addFeatureEnum(Element enumElement, Element extension){
        String extendsGroup = enumElement.getAttribute("extends");
        if(!extendsGroup.isEmpty()){
            if(extension != null){
                if(!enumElement.hasAttribute("extnumber")){
                    enumElement.setAttribute("extnumber", extension.getAttribute("number"));
                }
                enumElement.setAttribute("extname", extension.getAttribute("name"));
                enumElement.setAttribute("supported", extension.getAttribute("supported"));
            }
            List<Element> values = groupValues.get(extendsGroup);
            if(values == null){
                throw new RuntimeException("No enum group " + extendsGroup + " for " + enumElement.getAttribute("name"));
            }
            values.add(enumElement);
            enumDict.put(enumElement.getAttribute("name"), enumElement);
        } else if(enumElement.hasAttribute("value") || enumElement.hasAttribute("bitpos") || enumElement.hasAttribute("alias")){
            enumDict.put(enumElement.getAttribute("name"), enumElement);
        }
    }

    private static String commandName(Element command){
        return XmlHelper.getAttribute(command, "name")
                .orElseGet(() -> XmlHelper.getDirectChildElements(XmlHelper.getDirectChildElements(command, "proto").get(0), "name").get(0).getTextContent());
    }

    /**
     * The element holding the prototype and parameters of a command. An alias command has the signature of the
     * command it aliases
     */
    private Element commandDefinition(String commandName){
        Element command = cmdDict.get(commandName);
        String alias = command.getAttribute("alias");
        if(!alias.isEmpty()){
            return commandDefinition(alias);
        }
        return command;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Selecting and walking features (same rules as the Khronos header generator)
    // ------------------------------------------------------------------------------------------------------------

    private static boolean apiNameMatch(String attributeValue){
        return attributeValue.isEmpty() || Arrays.asList(attributeValue.split(",")).contains(API_NAME);
    }

    private List<FeatureOutput> generateFeatures(Element registryRoot){
        List<FeatureOutput> features = new ArrayList<>();
        for(Element feature : XmlHelper.getDirectChildElements(registryRoot, "feature")){
            if(apiNameMatch(feature.getAttribute("api"))){
                features.add(new FeatureOutput(feature));
            }
        }
        List<FeatureOutput> extensions = new ArrayList<>();
        for(Element extensionsElement : XmlHelper.getDirectChildElements(registryRoot, "extensions")){
            for(Element extension : XmlHelper.getDirectChildElements(extensionsElement, "extension")){
                if(apiNameMatch(extension.getAttribute("supported")) && !extension.getAttribute("supported").isEmpty()){
                    extensions.add(new FeatureOutput(extension));
                }
            }
        }
        extensions.sort(Comparator.comparingInt(f -> f.number));
        features.addAll(extensions);
        // core versions, then Khronos extensions, then the rest; stable so ties stay in the order above
        features.sort(Comparator.<FeatureOutput>comparingInt(f -> f.categoryOrder)
                .thenComparingInt(f -> f.sortOrder)
                .thenComparingDouble(f -> f.versionNumber)
                .thenComparingInt(f -> f.number));

        for(FeatureOutput feature : features){
            for(Element require : XmlHelper.getDirectChildElements(feature.element, "require")){
                for(Element type : XmlHelper.getDirectChildElements(require, "type")){
                    markTypeRequired(type.getAttribute("name"));
                }
                for(Element enumElement : XmlHelper.getDirectChildElements(require, "enum")){
                    markEnumRequired(enumElement.getAttribute("name"));
                }
                for(Element command : XmlHelper.getDirectChildElements(require, "command")){
                    markCommandRequired(command.getAttribute("name"));
                }
            }
        }

        for(FeatureOutput feature : features){
            currentFeature = feature;
            for(Element require : XmlHelper.getDirectChildElements(feature.element, "require")){
                for(Element type : XmlHelper.getDirectChildElements(require, "type")){
                    generateType(type.getAttribute("name"));
                }
                for(Element enumElement : XmlHelper.getDirectChildElements(require, "enum")){
                    if(enumElement.getAttribute("extends").isEmpty()){
                        generateEnum(enumElement.getAttribute("name"));
                    }
                }
                for(Element command : XmlHelper.getDirectChildElements(require, "command")){
                    generateCommand(command.getAttribute("name"));
                }
            }
        }
        currentFeature = null;
        return features;
    }

    private void markTypeRequired(String typeName){
        Element type = typeDict.get(typeName);
        if(type == null || !requiredTypes.add(typeName)){
            return;
        }
        for(String attribute : List.of("requires", "alias")){
            String dependency = type.getAttribute(attribute);
            if(!dependency.isEmpty()){
                markTypeRequired(dependency);
            }
        }
        for(Element subType : XmlHelper.getElements(type, "type")){
            markTypeRequired(subType.getTextContent());
        }
        for(Element subEnum : XmlHelper.getElements(type, "enum")){
            markEnumRequired(subEnum.getTextContent());
        }
        String bitValues = type.getAttribute("bitvalues");
        if(!bitValues.isEmpty()){
            markTypeRequired(bitValues);
            bitmaskFlagTypes.put(bitValues, typeName);
        }
    }

    private void markEnumRequired(String enumName){
        Element enumElement = enumDict.get(enumName);
        if(enumElement == null){
            return;
        }
        requiredEnums.add(enumName);
        String alias = enumElement.getAttribute("alias");
        if(!alias.isEmpty()){
            markEnumRequired(alias);
        }
    }

    private void markCommandRequired(String commandName){
        if(!cmdDict.containsKey(commandName) || !requiredCommands.add(commandName)){
            return;
        }
        for(Element type : XmlHelper.getElements(commandDefinition(commandName), "type")){
            markTypeRequired(type.getTextContent());
        }
    }

    private void generateType(String typeName){
        Element type = typeDict.get(typeName);
        if(type == null || !requiredTypes.contains(typeName) || !declaredTypes.add(typeName)){
            return;
        }
        String alias = type.getAttribute("alias");
        if(!alias.isEmpty()){
            generateType(alias);
        }
        String requires = type.getAttribute("requires");
        if(!requires.isEmpty()){
            generateType(requires);
        }
        for(Element subType : XmlHelper.getElements(type, "type")){
            generateType(subType.getTextContent());
        }
        for(Element subEnum : XmlHelper.getElements(type, "enum")){
            generateEnum(subEnum.getTextContent());
        }

        String category = type.getAttribute("category");
        switch(category){
            case "define" -> currentFeature.add(Section.DEFINE, new Declaration(DeclarationKind.DEFINE, typeName, type));
            case "basetype" -> currentFeature.add(Section.BASETYPE, new Declaration(DeclarationKind.BASETYPE, typeName, type));
            case "handle" -> currentFeature.add(Section.HANDLE, new Declaration(DeclarationKind.HANDLE, typeName, type));
            case "struct" -> currentFeature.add(Section.STRUCT, new Declaration(DeclarationKind.STRUCT, typeName, type));
            case "bitmask" -> {
                currentFeature.add(Section.BITMASK, new Declaration(DeclarationKind.BITMASK, typeName, type));
                String bitValues = type.getAttribute("bitvalues");
                if(!bitValues.isEmpty()){
                    generateType(bitValues);
                }
            }
            case "enum" -> {
                Element group = groupDict.get(typeName);
                if(alias.isEmpty() && group != null){
                    boolean isBitmask = "bitmask".equals(group.getAttribute("type"));
                    currentFeature.add(isBitmask ? Section.BITMASK : Section.GROUP,
                            new Declaration(isBitmask ? DeclarationKind.BITMASK_VALUES : DeclarationKind.ENUM, typeName, group));
                }
            }
            default -> {
                // includes, function pointers and external (platform) types produce nothing that is bound
            }
        }
    }

    private void generateEnum(String enumName){
        Element enumElement = enumDict.get(enumName);
        if(enumElement == null || !requiredEnums.contains(enumName) || !declaredEnums.add(enumName)){
            return;
        }
        String alias = enumElement.getAttribute("alias");
        if(!alias.isEmpty()){
            generateEnum(alias);
        }
        currentFeature.add(Section.CONSTANT, new Declaration(DeclarationKind.CONSTANT, enumName, enumElement));
    }

    private void generateCommand(String commandName){
        if(!cmdDict.containsKey(commandName) || !requiredCommands.contains(commandName) || !declaredCommands.add(commandName)){
            return;
        }
        String alias = cmdDict.get(commandName).getAttribute("alias");
        if(!alias.isEmpty()){
            generateCommand(alias);
        }
        Element definition = commandDefinition(commandName);
        for(Element type : XmlHelper.getElements(definition, "type")){
            generateType(type.getTextContent());
        }
        currentFeature.add(Section.COMMAND, new Declaration(DeclarationKind.COMMAND, commandName, definition));
    }

    // ------------------------------------------------------------------------------------------------------------
    // Building the model from the declarations
    // ------------------------------------------------------------------------------------------------------------

    private void buildModel(List<FeatureOutput> features){
        // everything that isn't platform specific is declared before everything that is (as openxr.h is before
        // openxr_platform.h)
        List<FeatureOutput> includedFeatures = new ArrayList<>();
        features.stream().filter(f -> f.included() && f.protect.isEmpty()).forEach(includedFeatures::add);
        features.stream().filter(f -> f.included() && !f.protect.isEmpty()).forEach(includedFeatures::add);

        List<Declaration> declarations = new ArrayList<>();
        for(FeatureOutput feature : includedFeatures){
            for(Section section : Section.values()){
                for(Declaration declaration : feature.sections.get(section)){
                    String protect = declaration.element.getAttribute("protect");
                    if(protect.isEmpty() || enabledProtects.contains(protect)){
                        declarations.add(declaration);
                    }
                }
            }
        }

        // first find out what every generated type is, so struct fields and function parameters can be classified
        for(Declaration declaration : declarations){
            classify(declaration);
        }
        for(Declaration declaration : declarations){
            build(declaration);
        }
    }

    private void classify(Declaration declaration){
        Element element = declaration.element;
        String name = declaration.name;
        switch(declaration.kind){
            case BASETYPE -> {
                String baseType = XmlHelper.getDirectChildElements(element, "type").get(0).getTextContent();
                switch(baseType){
                    case "XR_DEFINE_ATOM", "XR_DEFINE_OPAQUE_64" -> atoms.add(name);
                    case "uint32_t", "int32_t" -> intTypedefs.add(name);
                    case "uint64_t", "int64_t" -> longTypedefs.add(name);
                    default -> throw new RuntimeException("Unexpected base type " + baseType + " for " + name);
                }
            }
            case HANDLE -> {
                if(!element.hasAttribute("alias")){
                    handles.add(name);
                }
            }
            case BITMASK -> {
                if(!element.hasAttribute("alias")){
                    // all flags are XrFlags64
                    flags.add(name);
                    longTypedefs.add(name);
                }
            }
            case ENUM -> enumNames.add(name);
            case STRUCT -> structNames.add(name);
            default -> {
                // not a type that fields or parameters can have
            }
        }
    }

    private void build(Declaration declaration){
        Element element = declaration.element;
        String name = declaration.name;
        switch(declaration.kind){
            case DEFINE -> buildDefine(element);
            case BITMASK_VALUES -> buildBitmaskValues(name);
            case CONSTANT -> buildConstant(name, element);
            case ENUM -> enums.add(buildEnum(name));
            case STRUCT -> buildStruct(name, element).ifPresent(structs::add);
            case COMMAND -> functions.add(buildFunction(name, element));
            default -> {
                // already handled by classify
            }
        }
    }

    /**
     * Defines are C preprocessor text. Only simple unconditional "#define XR_FOO value" lines become constants,
     * anything inside an #if (e.g. XR_NULL_HANDLE) depends on the compiler/platform so is skipped
     */
    private void buildDefine(Element element){
        String text = element.getTextContent();
        if(text.contains("#if")){
            return;
        }
        for(String line : text.split("\n")){
            addDefine(line);
        }
    }

    /**
     * An API constant or an extension's constants (e.g. XR_MAX_PATH_LENGTH, XR_FB_passthrough_SPEC_VERSION,
     * XR_FB_PASSTHROUGH_EXTENSION_NAME). These are always an integer, a string or an alias of another constant
     */
    private void buildConstant(String name, Element element){
        if(element.hasAttribute("type")){
            throw new RuntimeException("Typed constants are not supported: " + name);
        }
        // an alias refers to the constant it aliases (which is always declared first)
        String value = enumValueString(element, List.of(), false);

        Element resolved = element;
        while(resolved.hasAttribute("alias")){
            resolved = enumDict.get(resolved.getAttribute("alias"));
        }
        String resolvedValue = enumValueString(resolved, List.of(), false);
        String javaType;
        if(resolvedValue.matches("^\".*\"$")){
            javaType = "String";
        } else if(resolvedValue.matches("^-?[0-9]+$")){
            javaType = "int";
        } else{
            throw new RuntimeException("Unexpected value " + resolvedValue + " for constant " + name);
        }
        constants.put(name, new ConstParser.Const(javaType, name, value));
    }

    private void addDefine(String line){
        if(DefinePasser.definePattern.matcher(line).find()){
            DefinePasser.parseDefine(line).ifPresent(define -> constants.put(define.constantName, ConstParser.Const.fromDefine(define)));
        }
    }

    private void buildBitmaskValues(String groupName){
        String flagType = bitmaskFlagTypes.get(groupName);
        List<Element> values = groupValues.get(groupName);
        for(Element value : values){
            String valueName = value.getAttribute("name");
            constants.put(valueName, new ConstParser.Const(flagType, valueName, enumValueString(value, values, true)));
        }
    }

    private EnumDefinition buildEnum(String groupName){
        EnumDefinition enumDefinition = new EnumDefinition(groupName);
        List<Element> values = groupValues.get(groupName);

        Set<String> requiredValues = requiredGroupValues(values);

        Set<String> seenNames = new HashSet<>();
        Map<String, String> numericValues = new HashMap<>();
        List<Element> aliases = new ArrayList<>();
        for(Element value : values){
            String valueName = value.getAttribute("name");
            if(!seenNames.add(valueName) || !requiredValues.contains(valueName)){
                continue;
            }
            if(value.hasAttribute("alias")){
                aliases.add(value);
            } else{
                String numericValue = enumValueString(value, values, false);
                numericValues.put(valueName, numericValue);
                enumDefinition.addValue(new EnumDefinition.EnumValue(valueName, numericValue));
            }
        }
        // aliases come after all the real values
        for(Element alias : aliases){
            String valueName = alias.getAttribute("name");
            String numericValue = numericValues.get(alias.getAttribute("alias"));
            numericValues.put(valueName, numericValue);
            enumDefinition.addValue(new EnumDefinition.EnumValue(valueName, numericValue));
        }
        enumDefinition.addValue(new EnumDefinition.EnumValue(maxEnumName(groupName), "0x7FFFFFFF"));
        return enumDefinition;
    }

    /**
     * Values added to an enum by an extension are only included if that extension is supported by "openxr" (i.e.
     * not disabled). Aliases of included values are included too
     */
    private static Set<String> requiredGroupValues(List<Element> values){
        Set<String> required = new HashSet<>();
        List<String> aliasesOfRequired = new ArrayList<>();
        for(Element value : values){
            boolean isRequired = !value.hasAttribute("extname") || apiNameMatch(value.getAttribute("supported"));
            if(isRequired){
                required.add(value.getAttribute("name"));
                if(value.hasAttribute("alias")){
                    aliasesOfRequired.add(value.getAttribute("alias"));
                }
            }
        }
        required.addAll(aliasesOfRequired);
        return required;
    }

    /**
     * The padding value that makes every enum 32 bit, e.g. XrPerfSettingsDomainEXT -> XR_PERF_SETTINGS_DOMAIN_MAX_ENUM_EXT
     */
    static String maxEnumName(String groupName){
        String expandName = groupName.replaceAll("([0-9]+|[a-z_])([A-Z0-9])", "$1_$2").toUpperCase();
        String expandPrefix = expandName;
        String expandSuffix = "";
        Matcher suffixMatcher = Pattern.compile("[A-Z][A-Z]+$").matcher(groupName);
        if(suffixMatcher.find()){
            expandSuffix = "_" + suffixMatcher.group();
            int index = expandName.lastIndexOf(expandSuffix);
            if(index >= 0){
                expandPrefix = expandName.substring(0, index);
            }
        }
        return expandPrefix + "_MAX_ENUM" + expandSuffix;
    }

    /**
     * The C representation of the value of an enum (or constant).
     * @param siblings the other values of the enum, used to resolve an alias
     * @param resolveAlias if an alias should be resolved to the value it aliases (rather than the aliased name)
     */
    private static String enumValueString(Element value, List<Element> siblings, boolean resolveAlias){
        if(value.hasAttribute("value")){
            return value.getAttribute("value");
        }
        if(value.hasAttribute("bitpos")){
            int bitpos = Integer.parseInt(value.getAttribute("bitpos"));
            String hex = String.format("0x%08x", 1L << bitpos);
            return bitpos >= 32 ? hex + "ULL" : hex;
        }
        if(value.hasAttribute("offset")){
            long number = EXT_BASE + (Long.parseLong(value.getAttribute("extnumber")) - 1) * EXT_BLOCK_SIZE + Long.parseLong(value.getAttribute("offset"));
            if(value.hasAttribute("dir")){
                number = -number;
            }
            return Long.toString(number);
        }
        if(value.hasAttribute("alias")){
            String alias = value.getAttribute("alias");
            if(!resolveAlias){
                return alias;
            }
            for(Element sibling : siblings){
                if(sibling.getAttribute("name").equals(alias)){
                    return enumValueString(sibling, siblings, true);
                }
            }
            throw new RuntimeException("Could not find the aliased enum value " + alias);
        }
        throw new RuntimeException("Enum without a value " + value.getAttribute("name"));
    }

    private Optional<StructDefinition> buildStruct(String structName, Element element){
        String alias = element.getAttribute("alias");
        if(!alias.isEmpty()){
            return buildStructAlias(structName, alias);
        }

        List<Element> members = XmlHelper.getDirectChildElements(element, "member");
        Set<String> memberNames = new HashSet<>();
        members.forEach(member -> memberNames.add(Declarator.of(member).name));

        // the XrStructureType of a struct is the "values" of its type member. Abstract structs (e.g. base headers)
        // don't have one
        Optional<String> xrStructureType = members.stream()
                .filter(member -> Declarator.of(member).name.equals("type") && member.hasAttribute("values"))
                .map(member -> member.getAttribute("values"))
                .findFirst();

        StructDefinition structDefinition = new StructDefinition(structName);
        xrStructureType.ifPresent(structDefinition::setXrStructureTypeEnumValue);
        XmlHelper.getAttribute(element, "parentstruct").ifPresent(structDefinition::setBaseHeader);
        structDefinition.setHeaderView(HEADER_VIEWS.get(structName));

        for(Element member : members){
            Declarator declarator = Declarator.of(member);
            String type = declarator.type;

            // Double pointers are exposed as raw addresses, so the pointed to type's characteristics are deliberately
            // ignored. Except arrays of struct pointers, which become the struct's PointerBuffer
            boolean isDoublePointer = declarator.isDoublePointer();
            boolean isStruct = structNames.contains(type);
            String countField = countOf(member, memberNames, structName + "." + declarator.name);
            if(isDoublePointer && isStruct && countField == null){
                throw new RuntimeException("Array of struct pointers without a len: " + structName + "." + declarator.name);
            }
            structDefinition.addField(new StructField(type, declarator.name, declarator.arraySize(), declarator.isPointer(), declarator.isConst(),
                    !isDoublePointer && isEnum(type), !isDoublePointer && atoms.contains(type),
                    !isDoublePointer && intTypedefs.contains(type), !isDoublePointer && longTypedefs.contains(type),
                    !isDoublePointer && handles.contains(type), !isDoublePointer && flags.contains(type),
                    isStruct, isDoublePointer, countField,
                    member.getAttribute("len").contains("null-terminated")));
        }
        // the structs this one can be chained onto (via their next pointer)
        XmlHelper.getAttribute(element, "structextends")
                .map(extended -> List.of(extended.split(",")))
                .ifPresent(structDefinition::setStructExtends);
        return Optional.of(structDefinition);
    }

    /**
     * A struct alias (e.g. XrUuidEXT for XrUuid, created when an extension is promoted to core) is generated as its
     * own struct with the same fields (and XrStructureType) as the struct it aliases, so the old name keeps working.
     * The alias doesn't take part in its target's parent/child relationships.
     */
    private Optional<StructDefinition> buildStructAlias(String aliasName, String targetName){
        return structs.stream()
                .filter(s -> s.getName().equals(targetName))
                .findFirst()
                .map(target -> {
                    StructDefinition alias = new StructDefinition(aliasName);
                    target.getFields().forEach(alias::addField);
                    target.getXrStructureTypeEnumValue().ifPresent(alias::setXrStructureTypeEnumValue);
                    return alias;
                });
    }

    private FunctionDefinition buildFunction(String commandName, Element definition){
        Element proto = XmlHelper.getDirectChildElements(definition, "proto").get(0);
        String returnType = XmlHelper.getDirectChildElements(proto, "type").get(0).getTextContent();
        FunctionDefinition functionDefinition = new FunctionDefinition(commandName, returnType);

        List<Element> params = XmlHelper.getDirectChildElements(definition, "param");
        Set<String> paramNames = new HashSet<>();
        params.forEach(param -> paramNames.add(Declarator.of(param).name));

        for(Element param : params){
            Declarator declarator = Declarator.of(param);
            String type = declarator.type;
            String arraySize = declarator.arraySize();
            // Double pointers are exposed as a slot for a raw address, so the pointed to type's characteristics are deliberately ignored
            boolean isDoublePointer = declarator.isDoublePointer();
            FunctionDefinition.FunctionParameter parameter = new FunctionDefinition.FunctionParameter(
                    type, declarator.name, declarator.isPointer() || arraySize != null, declarator.isConst(),
                    !isDoublePointer && isEnum(type), !isDoublePointer && atoms.contains(type),
                    !isDoublePointer && intTypedefs.contains(type), !isDoublePointer && longTypedefs.contains(type),
                    !isDoublePointer && handles.contains(type), !isDoublePointer && flags.contains(type),
                    !isDoublePointer && structNames.contains(type), isDoublePointer);
            if(arraySize != null){
                parameter.setExtraDocumentation("Required size " + arraySize);
            }
            if(isDoublePointer){
                parameter.setExtraDocumentation("A single pointer slot the runtime writes a " + type + "* into");
            }
            parameter.setCountParameter(countOf(param, paramNames, commandName + "." + declarator.name));
            functionDefinition.addParameter(parameter);
        }
        return functionDefinition;
    }

    private boolean isEnum(String type){
        return enumNames.contains(type) || handWrittenEnums.contains(type);
    }

    /**
     * The sibling member/param that holds how many items a pointer points to, from its "len" (e.g. len="viewCount" or
     * len="enabledApiLayerCount,null-terminated"). Null if there isn't one (e.g. len="null-terminated", or no len)
     * @param qualifiedName e.g. "XrFooInfo.bar", to look up in {@link #MISSING_LENS}
     */
    private static String countOf(Element memberOrParam, Set<String> siblingNames, String qualifiedName){
        String len = memberOrParam.hasAttribute("len") ? memberOrParam.getAttribute("len") : MISSING_LENS.getOrDefault(qualifiedName, "");
        if(len.isEmpty()){
            return null;
        }
        String count = len.split(",")[0].trim();
        return siblingNames.contains(count) ? count : null;
    }


    // ------------------------------------------------------------------------------------------------------------
    // Supporting types
    // ------------------------------------------------------------------------------------------------------------

    /**
     * A struct member or command parameter, e.g. {@code <member>const <type>char</type>* const* <name>names</name></member>}
     * or {@code <param><type>char</type> <name>buffer</name>[<enum>XR_MAX_RESULT_STRING_SIZE</enum>]</param>}
     */
    static class Declarator {
        final String type;
        final String name;
        /**
         * The C type as written, excluding the name, e.g. "const char* const*"
         */
        final String declaredType;
        /**
         * Everything after the name, e.g. "[XR_MAX_RESULT_STRING_SIZE]"
         */
        final String afterName;

        private Declarator(String type, String name, String declaredType, String afterName) {
            this.type = type;
            this.name = name;
            this.declaredType = declaredType;
            this.afterName = afterName;
        }

        static Declarator of(Element memberOrParam){
            StringBuilder beforeName = new StringBuilder();
            StringBuilder afterName = new StringBuilder();
            String type = null;
            String name = null;
            for(Node child = memberOrParam.getFirstChild(); child != null; child = child.getNextSibling()){
                if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("comment")){
                    continue;
                }
                String text = child.getTextContent();
                if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("name")){
                    name = text;
                } else if(name == null){
                    if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("type")){
                        type = text;
                    }
                    beforeName.append(text);
                } else{
                    afterName.append(text);
                }
            }
            if(type == null || name == null){
                throw new RuntimeException("Could not read declaration " + memberOrParam.getTextContent());
            }
            return new Declarator(type, name, beforeName.toString().trim().replaceAll("\\s+", " "), afterName.toString().trim());
        }

        boolean isPointer(){
            return declaredType.contains("*");
        }

        boolean isDoublePointer(){
            return declaredType.chars().filter(c -> c == '*').count() == 2;
        }

        boolean isConst(){
            return declaredType.startsWith("const") || declaredType.contains(" const ");
        }

        /**
         * The size of a fixed array (e.g. "XR_MAX_FOO"). Multi dimensional arrays are flattened (e.g. "9 * 3") as
         * they have the same memory layout as a one dimensional array. Null if not an array
         */
        String arraySize(){
            List<String> dimensions = new ArrayList<>();
            Matcher matcher = ARRAY_DIMENSION_PATTERN.matcher(afterName);
            while(matcher.find()){
                dimensions.add(matcher.group(1));
            }
            return dimensions.isEmpty() ? null : String.join(" * ", dimensions);
        }
    }

    /**
     * The order declarations are written within a feature (by the Khronos header generator)
     */
    private enum Section {
        DEFINE, BASETYPE, HANDLE, CONSTANT, GROUP, BITMASK, STRUCT, COMMAND
    }

    private enum DeclarationKind {
        DEFINE, BASETYPE, HANDLE, CONSTANT, ENUM, BITMASK, BITMASK_VALUES, STRUCT, COMMAND
    }

    private record Declaration(DeclarationKind kind, String name, Element element) {}

    private class FeatureOutput {
        final Element element;
        final String name;
        final String protect;
        final int categoryOrder;
        final int sortOrder;
        final double versionNumber;
        final int number;
        final Map<Section, List<Declaration>> sections = new LinkedHashMap<>();

        FeatureOutput(Element element) {
            this.element = element;
            this.name = element.getAttribute("name");
            this.protect = element.getAttribute("protect");
            this.sortOrder = element.hasAttribute("sortorder") ? Integer.parseInt(element.getAttribute("sortorder")) : 0;
            boolean isFeature = element.getTagName().equals("feature");
            if(isFeature){
                categoryOrder = 0;
                versionNumber = Double.parseDouble(element.getAttribute("number"));
                number = 0;
            } else{
                String category = name.split("_", 3)[1].toUpperCase();
                categoryOrder = List.of("ARB", "KHR", "OES").contains(category) ? 1 : 2;
                versionNumber = 0;
                number = Integer.parseInt(element.getAttribute("number"));
            }
            for(Section section : Section.values()){
                sections.put(section, new ArrayList<>());
            }
        }

        void add(Section section, Declaration declaration){
            sections.get(section).add(declaration);
        }

        boolean included(){
            return !name.equals(LOADER_FEATURE) && (protect.isEmpty() || enabledProtects.contains(protect));
        }
    }
}
