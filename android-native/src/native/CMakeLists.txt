cmake_minimum_required(VERSION 3.10)
project(OpenXRBindings LANGUAGES C CXX)


# Define JNI source files
set(JNI_SOURCES
    src/com_onemillionworlds_tamarin_openxrbindings_memory_MemoryUtil.c
    src/com_onemillionworlds_tamarin_openxrbindings_thickc_ThickC.c
    ${CMAKE_CURRENT_SOURCE_DIR}/../main/generated/native/src/com_onemillionworlds_tamarin_openxrbindings_XR10.c
)

# The OpenXR loader comes from the org.khronos.openxr:openxr_loader_for_android AAR via prefab.
# Linking OpenXR::openxr_loader also puts the AAR's OpenXR headers (OpenXR::headers) on the include path. Those are the
# same headers the Java/C code generation is based on (see extractOpenXrHeaders in build.gradle)
find_package(OpenXR REQUIRED CONFIG)


# Define JNI library
add_library(openxrjni SHARED ${JNI_SOURCES})

# Only include JNI headers and call find_package(JNI) if not Android
if (NOT ANDROID)
    find_package(JNI REQUIRED)
    include_directories(${JNI_INCLUDE_DIRS})
else()
    # On Android, the headers come from the NDK toolchain
    message(STATUS "Skipping find_package(JNI) on Android")
endif()

# Add include directories for headers
target_include_directories(openxrjni PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/headers
    ${CMAKE_CURRENT_BINARY_DIR}
)

# Link against Android system libraries
target_link_libraries(openxrjni
    android
    log
    OpenXR::openxr_loader
)
