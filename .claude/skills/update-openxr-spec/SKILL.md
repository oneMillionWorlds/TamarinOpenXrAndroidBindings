---
name: update-openxr-spec
description: Move the bindings to a newer OpenXR version by bumping the Khronos OpenXR loader dependency, which also drives the xr.xml registry used for generation and the OpenXR headers (openxr.h, openxr_platform.h, etc.) the C compiles against. Use when the user wants newer OpenXR functions, extensions or structs.
---

# Updating the OpenXR version

Nothing from the spec is checked in. One version number drives everything: `openxr-loader` in
`gradle/libs.versions.toml` (`org.khronos.openxr:openxr_loader_for_android`, currently **1.1.63**).
- `xr.xml`: the only input to generation (structs, functions, enums, handles, constants, core vs extension functions).
  Downloaded by `downloadXrXml` from
  `https://raw.githubusercontent.com/KhronosGroup/OpenXR-SDK-Source/release-<version>/specification/registry/xr.xml`
  into `android-native/build/openxrSpec/xr.xml`, verified against `xrXmlSha256` in `android-native/build.gradle`
- Headers: in the loader AAR (`prefab/modules/headers/include/openxr`). CMake gets them via prefab (`OpenXR::headers`);
  they are only used to compile the C, not for generation

Confirm the target version with the user before starting.

## Steps

1. Bump `openxr-loader` in `gradle/libs.versions.toml`. Check the `release-<version>` tag exists in OpenXR-SDK-Source
2. `./gradlew :android-native:downloadXrXml` fails with the new file's SHA-256 in the message. Put it in
   `xrXmlSha256` in `android-native/build.gradle`
3. Before regenerating, copy `android-native/src/main/generated` to the scratchpad so you can diff old vs new output
4. `./gradlew :android-native:parseOpenXrFile` and fix what breaks. Typical failures:
   - `Unexpected base type` / `Could not read declaration` / `Unexpected non pointer type` / `Unknown memory size for
     type`: a new construct `XmlRegistryParser` or the type mapping in `StructField` / `FunctionDefinition` doesn't
     handle. Extend them (with a unit test in `buildSrc/src/test/`), following the `codegen-change` skill. Keep the
     Java `native` types and the C JNI types in agreement
   - `Couldn't find a count method for X`: a pointer-to-struct member has no `len` in xr.xml. If it really is an array
     (check the spec text), add it to `XmlRegistryParser.MISSING_LENS`; otherwise it is a single struct
   - New platform/graphics `protect`s: generation only includes what `ParseOpenXr.ENABLED_PROTECTS` enables
     (Android + OpenGL ES); new EGL/Android types may need `tamarinManualDefines.h`, `HANDLES_EXTRA` or
     `HAND_WRITTEN_ENUMS`
   - Functions that can't be thin bindings are skipped via `ParseOpenXr.FUNCTIONS_TO_SKIP`. Only skip with a documented reason (a genuine JNI problem, not "didn't compile")
5. `./gradlew build`. Compile errors in the generated Java mean more generator gaps. A C *link* error means a function
   is called directly that the loader doesn't export (extension detection comes from `xr.xml` `<feature>` blocks via
   `XmlFeatureParser`)
6. Diff the old and new generated output. Look for removed classes/methods/constants/enum values and changed signatures
   in existing classes. These are breaking for Tamarin, so report them to the user
7. Add rows to `android-native/src/test/resources/expectedSizes.csv` for new structs. The values must come from the
   real C compiler for arm64 Android, **not** from the generated Java, or the test is circular. You don't need a
   device: for every generated struct class `X`, emit `char SIZE__X[sizeof(X)]; char ALIGN__X[_Alignof(X)];` into a
   C file that defines `XR_USE_PLATFORM_ANDROID`, `XR_USE_GRAPHICS_API_OPENGL_ES`, `XR_EXTENSION_PROTOTYPES` and
   includes `<jni.h>`, `tamarinManualDefines.h`, `<openxr/openxr.h>` and `<openxr/openxr_platform.h>`. Compile it
   with the NDK's `clang --target=aarch64-linux-android26 -c`, including `android-native/src/native/include` and the
   headers from the loader AAR (unzip `prefab/modules/headers/include` from
   `openxr_loader_for_android-<version>.aar` in the Gradle cache), then read the sizes with `llvm-nm -S` (symbol
   size = value). Check that it
   reproduces the existing rows first. Only add rows, don't change existing ones, and keep the file's sort order so
   the diff is pure additions
8. `./gradlew -p buildSrc test`, then `./gradlew build`. Reference-struct diffs in `StructsAreGeneratedCorrectlyTest`
   should only reflect genuine spec changes; review them before `./gradlew :android-native:updateReferenceStructs`
9. Update the version in `README.md` ("entire OpenXR API version ..."), `README_DEEP.md` and `CLAUDE.md`

Tell the user that JVM tests don't exercise JNI, so the new version needs checking in Tamarin on a headset.
