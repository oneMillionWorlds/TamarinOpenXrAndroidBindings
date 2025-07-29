cmake_minimum_required(VERSION 3.10)
project(OpenXRBindings LANGUAGES C CXX)


# Define JNI source files
set(JNI_SOURCES
    jni/src/com_onemillionworlds_tamarin_openxrbindings_Library.c
    jni/src/com_onemillionworlds_tamarin_openxrbindings_MemoryUtil.c
)

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
    ${CMAKE_CURRENT_SOURCE_DIR}/include
    ${CMAKE_CURRENT_SOURCE_DIR}/jni/include
    ${CMAKE_CURRENT_BINARY_DIR}
)

# Link against Android system libraries
target_link_libraries(openxrjni
    android
    log
)

# Add the OpenXR header generation step
set(HEADERS openxr.h openxr_platform.h openxr_reflection.h)
set(GENERATED_HEADERS)

foreach(header ${HEADERS})
    list(APPEND GENERATED_HEADERS ${CMAKE_CURRENT_BINARY_DIR}/${header})

    add_custom_command(
        OUTPUT ${CMAKE_CURRENT_BINARY_DIR}/${header}
        COMMAND ${PYTHON_EXECUTABLE} ${CMAKE_CURRENT_SOURCE_DIR}/../../specification/scripts/genxr.py
            -registry ${CMAKE_CURRENT_SOURCE_DIR}/../../specification/registry/xr.xml
            -o ${CMAKE_CURRENT_BINARY_DIR} ${header}
        DEPENDS
            ${CMAKE_CURRENT_SOURCE_DIR}/../../specification/scripts/genxr.py
            ${CMAKE_CURRENT_SOURCE_DIR}/../../specification/scripts/generator.py
            ${CMAKE_CURRENT_SOURCE_DIR}/../../specification/registry/xr.xml
        COMMENT "Generating OpenXR header: ${header}"
    )
endforeach()

add_custom_target(generate_openxr_header DEPENDS ${GENERATED_HEADERS})
add_dependencies(openxrjni generate_openxr_header)