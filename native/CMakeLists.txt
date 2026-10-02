cmake_minimum_required(VERSION 3.16)
project(OpenXRBindings LANGUAGES C CXX)

# Builds the openxrjni library. On Android this is run by the Android Gradle plugin (openxr-bindings-android). On
# desktop it is run by openxr-bindings-desktop's buildDesktopNatives task, which passes:
#   OPENXR_SDK_URL / OPENXR_SDK_SHA256 - the Khronos OpenXR-SDK source release the loader is built from and the
#       headers come from. The same version as the xr.xml the Java/C is generated from (see downloadXrXml)
#   TAMARIN_OUTPUT_DIR - where to put the built library

# The generated C (from openxr-bindings-core's parseOpenXrFile). Gradle builds generate it first, but an IDE Gradle
# sync configures CMake without running any tasks, so it may not exist yet (e.g. after a clean or on a fresh clone)
set(GENERATED_XR10_C ${CMAKE_CURRENT_SOURCE_DIR}/generated/com_onemillionworlds_tamarin_openxrbindings_XR10.c)
if (NOT EXISTS ${GENERATED_XR10_C})
    message(FATAL_ERROR "${GENERATED_XR10_C} does not exist. Run ./gradlew :openxr-bindings-core:parseOpenXrFile "
        "to generate it (then re-sync if this came from an IDE Gradle sync)")
endif()

# Define JNI source files
set(JNI_SOURCES
    src/com_onemillionworlds_tamarin_openxrbindings_memory_MemoryUtil.c
    src/com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC.c
    ${GENERATED_XR10_C}
)

if (ANDROID)
    list(APPEND JNI_SOURCES src/com_onemillionworlds_tamarin_openxrbindings_thickc_AndroidThickC.c)

    # The OpenXR loader comes from the org.khronos.openxr:openxr_loader_for_android AAR via prefab.
    # Linking OpenXR::openxr_loader also puts the AAR's OpenXR headers (OpenXR::headers) on the include path. Those
    # are the same version as the xr.xml the Java/C code is generated from (see downloadXrXml in
    # openxr-bindings-core/build.gradle)
    find_package(OpenXR REQUIRED CONFIG)
else()
    # On desktop the loader is built from source and statically linked into openxrjni, so it is a single library
    # with no dependency on (or conflict with) any other OpenXR loader, e.g. LWJGL's
    if (NOT OPENXR_SDK_URL OR NOT OPENXR_SDK_SHA256)
        message(FATAL_ERROR "OPENXR_SDK_URL and OPENXR_SDK_SHA256 must be set")
    endif()

    set(CMAKE_POSITION_INDEPENDENT_CODE ON)
    # Use the static C runtime with MSVC so openxrjni.dll doesn't need the Visual C++ redistributable
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded")

    # Only the loader is wanted from the SDK; it doesn't need any graphics APIs (they're for the layers/samples)
    set(BUILD_API_LAYERS OFF CACHE BOOL "" FORCE)
    set(BUILD_TESTS OFF CACHE BOOL "" FORCE)
    set(BUILD_CONFORMANCE_TESTS OFF CACHE BOOL "" FORCE)
    set(BUILD_SDK_TESTS OFF CACHE BOOL "" FORCE)
    set(DYNAMIC_LOADER OFF CACHE BOOL "" FORCE)
    set(BUILD_WITH_SYSTEM_JSONCPP OFF CACHE BOOL "" FORCE)
    set(OPENGL_INCOMPATIBLE TRUE)
    set(OPENGLES_INCOMPATIBLE TRUE)
    set(EGL_INCOMPATIBLE TRUE)
    set(VULKAN_INCOMPATIBLE TRUE)
    set(METAL_INCOMPATIBLE TRUE)

    include(FetchContent)
    FetchContent_Declare(
        OpenXRSDK
        URL ${OPENXR_SDK_URL}
        URL_HASH SHA256=${OPENXR_SDK_SHA256}
    )
    FetchContent_MakeAvailable(OpenXRSDK)
    # so only what openxrjni links (the loader) is built
    set_property(DIRECTORY ${openxrsdk_SOURCE_DIR} PROPERTY EXCLUDE_FROM_ALL TRUE)

    # The SDK sets the static loader to the DLL C runtime; match openxrjni (above)
    if (MSVC)
        set_target_properties(openxr_loader PROPERTIES MSVC_RUNTIME_LIBRARY "MultiThreaded")
    endif()

    find_package(JNI REQUIRED)
endif()

# Define JNI library
add_library(openxrjni SHARED ${JNI_SOURCES})

# Add include directories for headers
target_include_directories(openxrjni PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/include
    ${CMAKE_CURRENT_SOURCE_DIR}/headers
    ${CMAKE_CURRENT_BINARY_DIR}
)
if (TAMARIN_OUTPUT_DIR)
    # (a generator expression stops multi-config generators, e.g. Visual Studio, adding a per-config subdirectory)
    set_target_properties(openxrjni PROPERTIES
        LIBRARY_OUTPUT_DIRECTORY "$<1:${TAMARIN_OUTPUT_DIR}>"
        RUNTIME_OUTPUT_DIRECTORY "$<1:${TAMARIN_OUTPUT_DIR}>"
    )
endif()

if (ANDROID)
    target_link_libraries(openxrjni
        android
        log
        OpenXR::openxr_loader
    )
else()
    # JNI_INCLUDE_DIRS rather than JNI::JNI, which (with CMake < 3.24) also links libjvm, which a JNI library loaded
    # by a running JVM must not do
    target_include_directories(openxrjni PRIVATE ${JNI_INCLUDE_DIRS})
    target_link_libraries(openxrjni PRIVATE OpenXR::openxr_loader)

    if (CMAKE_SYSTEM_NAME STREQUAL "Linux")
        # Link the C++ runtime (the loader is C++) statically so the library works on any reasonably recent glibc
        # distribution, and only export the JNI functions (not the loader's or the C++ runtime's symbols)
        target_link_options(openxrjni PRIVATE
            -static-libstdc++ -static-libgcc
            -Wl,--version-script=${CMAKE_CURRENT_SOURCE_DIR}/exports.map
        )
        set_property(TARGET openxrjni APPEND PROPERTY LINK_DEPENDS ${CMAKE_CURRENT_SOURCE_DIR}/exports.map)
        # strip the published (Release) library
        target_link_options(openxrjni PRIVATE $<$<CONFIG:Release>:-s>)
    elseif (MINGW)
        # no dependency on the MinGW C++/pthread runtime DLLs
        target_link_options(openxrjni PRIVATE -static)
    endif()
    if (WIN32)
        # Don't name the library libopenxrjni.dll with MinGW (System.mapLibraryName gives openxrjni.dll)
        set_target_properties(openxrjni PROPERTIES PREFIX "")
    endif()
endif()
