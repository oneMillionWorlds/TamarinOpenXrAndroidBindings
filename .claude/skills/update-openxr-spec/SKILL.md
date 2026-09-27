---
name: update-openxr-spec
description: Move the bindings to a newer OpenXR version by bumping the Khronos OpenXR loader dependency, which also drives the OpenXR headers (openxr.h, openxr_platform.h, etc.) and the xr.xml registry used for generation. Use when the user wants newer OpenXR functions, extensions or structs.
---

# Updating the OpenXR version

Nothing from the spec is checked in. One version number drives everything: `openxr-loader` in
`gradle/libs.versions.toml` (`org.khronos.openxr:openxr_loader_for_android`, currently **1.1.63**).
- Headers: extracted from that loader AAR (`prefab/modules/headers/include/openxr`) by `extractOpenXrHeaders` into
  `android-native/build/openxrSpec/include`. CMake gets the same headers via prefab (`OpenXR::headers`). They are the
  source of truth for generation (structs, functions, enums, handles, constants) and what the C compiles against
- `xr.xml`: downloaded by `downloadXrXml` from
  `https://raw.githubusercontent.com/KhronosGroup/OpenXR-SDK-Source/release-<version>/specification/registry/xr.xml`
  into `android-native/build/openxrSpec/xr.xml`, verified against `xrXmlSha256` in `android-native/build.gradle`. Used
  for `parentstruct` enrichment (every header struct must exist in it) and for deciding core vs extension functions

Confirm the target version with the user before starting.

## Steps

1. Bump `openxr-loader` in `gradle/libs.versions.toml`. Check the `release-<version>` tag exists in OpenXR-SDK-Source
2. `./gradlew :android-native:downloadXrXml` fails with the new file's SHA-256 in the message. Put it in
   `xrXmlSha256` in `android-native/build.gradle`
3. Before regenerating, copy `android-native/src/main/generated` to the scratchpad so you can diff old vs new output
4. `./gradlew :android-native:parseOpenXrFile` and fix what breaks. Typical failures:
   - `No XML information found for struct`: a struct in the headers is missing from `xr.xml` (shouldn't happen when
     the versions match; check the download)
   - `Failed to pass line` / `Unexpected non pointer type` / `Unknown memory size for type`: a new C construct the
     line-based parsers in `buildSrc/.../parsers/` or the type mapping in `StructField` / `FunctionDefinition` doesn't
     handle. Extend them (with a unit test in `buildSrc/src/test/`), following the `codegen-change` skill. Keep the
     Java `native` types and the C JNI types in agreement
   - `Couldn't find a count method for X`: the count-field name guessing in
     `StructDefinition.findCountParameterForPointerField` doesn't cover a new naming pattern
   - New platform/graphics `#ifdef` blocks: generation only includes what `ParseOpenXr.STANDARD_DEFS` enables
     (Android + OpenGL ES); new EGL/Android types may need `tamarinManualDefines.h`, `HANDLES_EXTRA` or
     `HAND_WRITTEN_ENUMS`
   - Functions with double pointers are skipped in `ParseOpenXr.parseHeaderFile`, others in
     `X10Generator.methodsToSkip`. Only skip with a logged reason
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
   with the NDK's `clang --target=aarch64-linux-android26 -c` (include `android-native/build/openxrSpec/include` and
   `android-native/src/native/include`), then read the sizes with `llvm-nm -S` (symbol size = value). Check that it
   reproduces the existing rows first. Only add rows, don't change existing ones, and keep the file's sort order so
   the diff is pure additions
8. `./gradlew -p buildSrc test`, then `./gradlew build`. Reference-struct diffs in `StructsAreGeneratedCorrectlyTest`
   should only reflect genuine spec changes; review them before `./gradlew :android-native:updateReferenceStructs`
9. Update the version in `README.md` ("entire OpenXR API version ..."), `README_DEEP.md` and `CLAUDE.md`

Tell the user that JVM tests don't exercise JNI, so the new version needs checking in Tamarin on a headset.
