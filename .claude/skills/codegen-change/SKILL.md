---
name: codegen-change
description: Change what the OpenXR code generator emits — generated struct classes, XR10 wrapper methods, the JNI C file, enums, handles or constants. Use for any bug or feature in generated code under android-native/src/main/generated, or any edit to buildSrc parsers/generators.
---

# Changing generated code

Generated files (`android-native/src/main/generated/**`) are git-ignored and rebuilt on every build. Never edit them;
find the generator that writes the line you want to change.

## 1. Locate the source of the output

| Generated output | Written by |
| --- | --- |
| `Xr*.java` struct classes (layout, getters/setters, `malloc`/`calloc`, `Buffer`, `PointerBuffer`, `toString`, validation) | `buildSrc/.../generators/StructGenerator.java`; field type mapping in `tasks/StructField.java`; parent/child + count-field lookup in `tasks/StructDefinition.java`; the fields themselves (types, `len` count fields, `values` struct type, `parentstruct`) are read from xr.xml by `parsers/XmlRegistryParser` |
| `XR10.java` wrappers + `native` decls | `WrapperFunctionGenerator` (per function), `X10Generator` (file shell, skip list); Java types from `FunctionDefinition.FunctionParameter.getHighLevelJavaType/getLowLevelJavaType` |
| `com_onemillionworlds_tamarin_openxrbindings_XR10.c` | `CWrapperFunctionGenerator` (per function, JNI signature + casts), `X10CGenerator` (includes, extension PFN table, special `nxrCreateInstance`) |
| `enums/*.java` | `EnumGenerator` (values, incl. extension values and `MAX_ENUM`, from `parsers/XmlRegistryParser.buildEnum`) |
| `handles/*.java` | `HandleGenerator` (handles from `parsers/XmlRegistryParser` + `ParseOpenXr.HANDLES_EXTRA`) |
| `XR10Constants.java` | `ConstantsGenerator` (from `XmlRegistryParser`: `#define` types via `DefinePasser`, enum constants, flag bits) |
| Whether something is generated at all | `XmlRegistryParser` (which features/extensions/types are included, mirroring the Khronos header generator), `ParseOpenXr.ENABLED_PROTECTS` (platform/graphics API set), `ParseOpenXr.execute` (skips double-pointer functions and `ParseOpenXr.FUNCTIONS_TO_SKIP`, for both Java and C) |

Grep the generated file for the exact text, then grep `buildSrc` for a distinctive literal from it.

Keep the Java wrapper (`WrapperFunctionGenerator`/`FunctionParameter`) and the C side (`CWrapperFunctionGenerator`) in
agreement: the Java `native` parameter types, the JNI signature comment, the C parameter types and the casts must all
match, or the app crashes at runtime with `UnsatisfiedLinkError` or silently passes garbage. Nothing in the JVM tests
catches a Java/C mismatch.

## 2. Make the change

- Keep changes minimal and in the generator's existing string-building style (`StringBuilder.append` / `writer.write`)
- Prefer information from xr.xml markup (`len`, `values`, `optional`, `category`, `parentstruct`, ...) over guessing from names. If xr.xml genuinely lacks something, add an explicit, commented table entry (like `XmlRegistryParser.MISSING_LENS`) rather than a heuristic
- If the change is about a specific C shape (pointer + count, fixed array, function pointer, base header, by-value struct), check the existing special cases in `StructField`/`StructGenerator` first — there usually is one
- "Update, don't overload" applies to generated APIs too: change the generated method rather than emitting an extra overload unless asked

## 3. Regenerate and inspect

```
./gradlew :android-native:parseOpenXrFile
```

Look at a few affected generated files (and the C file if relevant). Generated files aren't in git, so to see a
before/after diff copy the relevant files to the scratchpad before regenerating.

## 4. Tests

1. Update or add string-exact tests in `buildSrc/src/test/.../generators/` (and `parsers/` if parsing changed). Run:
   `./gradlew -p buildSrc test` (the root build does not run these)
2. Run `./gradlew :android-native:testDebugUnitTest`
   - `StructsAreGeneratedCorrectlyTest` fails if struct output changed. Read the failure diff, confirm every difference is intended, then `./gradlew :android-native:updateReferenceStructs` and review `git diff android-native/src/test/resources/referenceStructs`
   - If the change introduces behaviour for a struct shape not already covered, copy that generated struct to `src/test/resources/referenceStructs/<Name>_reference.java` and add a test method with a comment explaining why the struct is interesting (follow the existing ones)
   - `StructsHaveCorrectSizeAndAlignment` failing means `Layout`/field layout generation is wrong — fix the generator, never the CSV
3. `./gradlew build` for the full thing including the native CMake build (catches C compile errors in the generated C)

Tell the user that JVM tests don't exercise JNI; on-device verification happens in Tamarin.
