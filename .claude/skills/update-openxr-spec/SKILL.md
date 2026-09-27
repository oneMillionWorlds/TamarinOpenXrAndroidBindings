---
name: update-openxr-spec
description: Update the vendored OpenXR headers (openxr.h, openxr_platform.h, etc.) and/or the xr.xml registry to a newer OpenXR version, or bump the Khronos OpenXR loader dependency. Use when the user wants newer OpenXR functions, extensions or structs.
---

# Updating the OpenXR spec inputs

Inputs (all under `android-native/src/`):
- `native/include/openxr/openxr.h`, `openxr_platform.h`, `openxr_platform_defines.h`, `openxr_reflection.h` — currently **1.0.24**. These are the source of truth for generation (structs, functions, enums, handles, constants) and are what the C compiles against
- `openxrSpec/xr.xml` — currently **1.1.51**; only used for `parentstruct` enrichment, but every struct in the headers must exist in it
- Loader: `openxr-loader` in `gradle/libs.versions.toml` (`org.khronos.openxr:openxr_loader_for_android`, linked via prefab). Only the library is used; its bundled headers are deliberately ignored

Before starting, check the header versions (`grep XR_CURRENT_API_VERSION` in `openxr.h` and `xr.xml`) and confirm with the
user which version to target. Taking headers to 1.1.x is a larger job than a patch bump (see below).

## Steps

1. Replace the header files and/or `xr.xml` with the matching Khronos release (OpenXR-SDK / OpenXR-Docs `specification/registry/xr.xml`). Keep header and xr.xml versions at least compatible: `xr.xml` must be >= headers
2. `./gradlew :android-native:parseOpenXrFile` and fix what breaks. Typical failures:
   - `No XML information found for struct` — xr.xml older than headers
   - `Unexpected non pointer type` / parse exceptions — a new C construct the line-based parsers in `buildSrc/.../parsers/` don't handle. Extend the parser (with a unit test in `buildSrc/src/test/.../parsers/`), following the `codegen-change` skill
   - New vendor suffix on extension functions — add it to `X10CGenerator.extensionSuffixes`, otherwise the C file calls the symbol directly and fails to link (the loader doesn't export extension functions)
   - New platform/graphics `#ifdef` blocks — generation only includes what `ParseOpenXr.STANDARD_DEFS` enables (Android + OpenGL ES); new EGL/Android types may need `tamarinManualDefines.h`, `HANDLES_EXTRA` or `HAND_WRITTEN_ENUMS`
   - New functions that don't compile or use double pointers are skipped in `ParseOpenXr.parseHeaderFile` / `X10Generator.methodsToSkip` — only skip with a logged reason
3. Regenerate `android-native/src/test/resources/expectedSizes.csv` for new/changed structs. These values must come from the real C compiler (`sizeof`/`_Alignof` for arm64 Android), **not** from the generated Java — otherwise the test is circular. If you can't compile and run C for arm64, tell the user and leave existing rows untouched (missing classes are tolerated; missing rows simply aren't checked)
4. `./gradlew -p buildSrc test`, then `./gradlew build`. Reference-struct diffs in `StructsAreGeneratedCorrectlyTest` should only reflect genuine spec changes; review before `./gradlew :android-native:updateReferenceStructs`
5. Update the version mentioned in `README.md` ("entire OpenXR API version ...") and the versions noted in `CLAUDE.md`

## Moving headers to 1.1.x

OpenXR 1.1 promoted several extensions to core (functions lose their suffix, e.g. `xrLocateSpaces`) and adds
new struct shapes. Expect parser/generator work and API changes visible to Tamarin; flag this to the user as potentially breaking.
