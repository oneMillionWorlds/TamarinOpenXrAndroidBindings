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
  into `openxr-bindings-core/build/openxrSpec/xr.xml`, verified against `xrXmlSha256` in `openxr-bindings-core/build.gradle`
- Headers (Android): in the loader AAR (`prefab/modules/headers/include/openxr`). CMake gets them via prefab (`OpenXR::headers`);
  they are only used to compile the C, not for generation
- Desktop loader + headers: CMake downloads the `OpenXR-SDK` source release
  (`https://github.com/KhronosGroup/OpenXR-SDK/archive/refs/tags/release-<version>.tar.gz`), verified against
  `openxrSdkSha256` in `openxr-bindings-desktop/build.gradle`, and statically links the loader

Confirm the target version with the user before starting.

## Steps

1. Bump `openxr-loader` in `gradle/libs.versions.toml`. Check the `release-<version>` tag exists in OpenXR-SDK-Source
2. `./gradlew :openxr-bindings-core:downloadXrXml` fails with the new file's SHA-256 in the message. Put it in
   `xrXmlSha256` in `openxr-bindings-core/build.gradle`. Also download the OpenXR-SDK `release-<version>.tar.gz` (URL
   above) and put its SHA-256 in `openxrSdkSha256` in `openxr-bindings-desktop/build.gradle` (otherwise the desktop
   CMake configure fails with a hash mismatch)
3. Before regenerating, copy `openxr-bindings-core/src/main/generated` (and `native/generated`) to the scratchpad so you can diff old vs new output
4. `./gradlew :openxr-bindings-core:parseOpenXrFile` and fix what breaks. Typical failures:
   - `Unexpected base type` / `Could not read declaration` / `Unexpected non pointer type` / `Unknown memory size for
     type`: a new construct `XmlRegistryParser` or the type mapping in `StructField` / `FunctionDefinition` doesn't
     handle. Extend them (with a unit test in `buildSrc/src/test/`), following the `codegen-change` skill. Keep the
     Java `native` types and the C JNI types in agreement
   - `Couldn't find a count method for X`: a pointer-to-struct member has no `len` in xr.xml. If it really is an array
     (check the spec text), add it to `XmlRegistryParser.MISSING_LENS`; otherwise it is a single struct
   - New platform/graphics `protect`s: generation only includes what `ParseOpenXr.NativePlatform` enables (Android +
     OpenGL ES, Windows + OpenGL, Linux Xlib/XCB/Wayland/EGL + OpenGL); new external (EGL/Windows/X11/xcb) types need
     `native/include/tamarinPlatform.h` and `ParseOpenXr.EXTERNAL_TYPES`
   - Functions that can't be thin bindings are skipped via `ParseOpenXr.FUNCTIONS_TO_SKIP`. Only skip with a documented reason (a genuine JNI problem, not "didn't compile")
5. `./gradlew build`. Compile errors in the generated Java mean more generator gaps. A C *link* error means a function
   is called directly that the loader doesn't export (extension detection comes from `xr.xml` `<feature>` blocks via
   `XmlFeatureParser`)
6. Diff the old and new generated output. Look for removed classes/methods/constants/enum values and changed signatures
   in existing classes. These are breaking for Tamarin, so report them to the user
7. Add rows to `openxr-bindings-core/src/test/resources/expectedSizes.csv` for new structs. The values must come from the
   real C compiler for arm64 Android, **not** from the generated Java, or the test is circular. You don't need a
   device: for every generated struct class `X`, emit `char SIZE__X[sizeof(X)]; char ALIGN__X[_Alignof(X)];` into a
   C file that defines `XR_USE_PLATFORM_ANDROID`, `XR_USE_GRAPHICS_API_OPENGL_ES`, `XR_EXTENSION_PROTOTYPES` and
   includes `<jni.h>`, `tamarinPlatform.h`, `<openxr/openxr.h>` and `<openxr/openxr_platform.h>`. Compile it
   with the NDK's `clang --target=aarch64-linux-android26 -c`, including `native/include` and the
   headers from the loader AAR (unzip `prefab/modules/headers/include` from
   `openxr_loader_for_android-<version>.aar` in the Gradle cache), then read the sizes with `llvm-nm -S` (symbol
   size = value). Check that it
   reproduces the existing rows first. Only add rows, don't change existing ones, and keep the file's sort order so
   the diff is pure additions
8. `./gradlew -p buildSrc test`, then `./gradlew build`. Reference-struct diffs in `StructsAreGeneratedCorrectlyTest`
   should only reflect genuine spec changes; review them before `./gradlew :openxr-bindings-core:updateReferenceStructs`
9. Update the version in `README.md` ("entire OpenXR API version ..."), `README_DEEP.md` and `CLAUDE.md`

Tell the user that JVM tests don't exercise JNI, so the new version needs checking in Tamarin on a headset.
