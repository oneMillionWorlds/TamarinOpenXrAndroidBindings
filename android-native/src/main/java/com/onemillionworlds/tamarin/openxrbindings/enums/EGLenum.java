package com.onemillionworlds.tamarin.openxrbindings.enums;

/**
 * Enumeration of EGL constants used in OpenGL ES and related API calls.
 * These constants are organized by functional groups to help with API usage.
 */
public enum EGLenum {
    // ── Bind API (for eglBindAPI / eglQueryAPI) ───────────────────────────────
    /**
     * OpenGL ES API binding value.
     * Used with eglBindAPI to select OpenGL ES as the rendering API.
     * Group: BindAPI
     */
    EGL_OPENGL_ES_API(0x30A0, "BindAPI"),
    
    /**
     * OpenVG API binding value.
     * Used with eglBindAPI to select OpenVG as the rendering API.
     * Group: BindAPI
     */
    EGL_OPENVG_API(0x30A1, "BindAPI"),
    
    /**
     * OpenGL API binding value.
     * Used with eglBindAPI to select desktop OpenGL as the rendering API.
     * Group: BindAPI
     */
    EGL_OPENGL_API(0x30A2, "BindAPI"),

    // ── GL colorspace (attributes for images/contexts that take EGLenum values)
    /**
     * Specifies the color space used by OpenGL and OpenGL ES when rendering to the surface. If its value is EGL_GL_COLORSPACE_SRGB, then a non-linear, perceptually uniform color space is assumed, with a corresponding GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING value of GL_SRGB. If its value is EGL_GL_COLORSPACE_LINEAR, then a linear color space is assumed, with a corresponding GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING value of GL_LINEAR. The default value of EGL_GL_COLORSPACE is EGL_GL_COLORSPACE_LINEAR.
     * <p>
     * Note that the EGL_GL_COLORSPACE attribute is used only by OpenGL and OpenGL ES contexts supporting sRGB framebuffers. EGL itself does not distinguish multiple colorspace models. Refer to the ``sRGB Conversion'' sections of the OpenGL 4.6 and OpenGL ES 3.2 Specifications for more information.
     * </p>
     */
    EGL_GL_COLORSPACE(0x309D, "GLColorSpace"),
    
    /**
     * sRGB colorspace value.
     * Specifies that colors should be interpreted in sRGB color space.
     * Group: GLColorSpace
     */
    EGL_GL_COLORSPACE_SRGB(0x3089, "GLColorSpace"),
    
    /**
     * Linear colorspace value.
     * Specifies that colors should be interpreted in linear color space.
     * Group: GLColorSpace
     */
    EGL_GL_COLORSPACE_LINEAR(0x308A, "GLColorSpace"),

    // ── Image targets (eglCreateImage targets) ────────────────────────────────
    /**
     * 2D texture image target.
     * Used with eglCreateImage to specify a 2D texture as the source.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_2D(0x30B1, "ImageTarget"),
    
    /**
     * 3D texture image target.
     * Used with eglCreateImage to specify a 3D texture as the source.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_3D(0x30B2, "ImageTarget"),
    
    /**
     * Positive X face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_X(0x30B3, "ImageTarget"),
    
    /**
     * Negative X face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_X(0x30B4, "ImageTarget"),
    
    /**
     * Positive Y face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Y(0x30B5, "ImageTarget"),
    
    /**
     * Negative Y face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Y(0x30B6, "ImageTarget"),
    
    /**
     * Positive Z face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Z(0x30B7, "ImageTarget"),
    
    /**
     * Negative Z face of a cubemap texture.
     * Used with eglCreateImage for cubemap creation.
     * Group: ImageTarget
     */
    EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Z(0x30B8, "ImageTarget"),
    
    /**
     * Renderbuffer image target.
     * Used with eglCreateImage to specify a renderbuffer as the source.
     * Group: ImageTarget
     */
    EGL_GL_RENDERBUFFER(0x30B9, "ImageTarget"),
    
    /**
     * Texture mipmap level attribute.
     * Specifies which mipmap level to use for eglCreateImage.
     * Group: ImageTargetAttr
     */
    EGL_GL_TEXTURE_LEVEL(0x30BC, "ImageTargetAttr"),
    
    /**
     * Z-offset attribute for 3D textures.
     * Specifies the z-offset within a 3D texture for eglCreateImage.
     * Group: ImageTargetAttr
     */
    EGL_GL_TEXTURE_ZOFFSET(0x30BD, "ImageTargetAttr"),

    // ── Sync objects (eglCreateSync type/condition/status enums) ──────────────
    /**
     * Fence sync object type.
     * Used with eglCreateSync to create a fence sync object.
     * Group: SyncType
     */
    EGL_SYNC_FENCE(0x30F9, "SyncType"),
    
    /**
     * Sync condition specifying that all prior commands must complete.
     * Used when creating a sync object with eglCreateSync.
     * Group: SyncCondition
     */
    EGL_SYNC_PRIOR_COMMANDS_COMPLETE(0x30F0, "SyncCondition"),
    
    /**
     * Sync object signaled status.
     * Indicates a sync object that has been signaled.
     * Group: SyncStatus
     */
    EGL_SIGNALED(0x30F2, "SyncStatus"),
    
    /**
     * Sync object unsignaled status.
     * Indicates a sync object that has not yet been signaled.
     * Group: SyncStatus
     */
    EGL_UNSIGNALED(0x30F3, "SyncStatus"),

    // ── Platforms for eglGetPlatformDisplay (common ones) ─────────────────────
    /**
     * Android platform for eglGetPlatformDisplay.
     * Used to specify the Android platform when getting an EGL display.
     * Group: PlatformKHR
     */
    EGL_PLATFORM_ANDROID_KHR(0x3141, "PlatformKHR"),
    
    /**
     * X11 platform for eglGetPlatformDisplay.
     * Used to specify the X11 platform when getting an EGL display.
     * Group: PlatformKHR
     */
    EGL_PLATFORM_X11_KHR(0x31D5, "PlatformKHR"),
    
    /**
     * X11 screen attribute for eglGetPlatformDisplay.
     * Used to specify which X11 screen to use when getting an EGL display.
     * Group: PlatformKHR
     */
    EGL_PLATFORM_X11_SCREEN_KHR(0x31D6, "PlatformKHR"),
    
    /**
     * GBM (Generic Buffer Manager) platform for eglGetPlatformDisplay.
     * Used on Linux systems with DRM/KMS support.
     * Group: PlatformKHR
     */
    EGL_PLATFORM_GBM_KHR(0x31D7, "PlatformKHR"),
    
    /**
     * Wayland platform for eglGetPlatformDisplay.
     * Used to specify the Wayland platform when getting an EGL display.
     * Group: PlatformKHR
     */
    EGL_PLATFORM_WAYLAND_KHR(0x31D8, "PlatformKHR"),
    
    /**
     * Device platform extension for eglGetPlatformDisplay.
     * Used to create an EGL display from an EGLDeviceEXT object.
     * Group: PlatformEXT
     */
    EGL_PLATFORM_DEVICE_EXT(0x313F, "PlatformEXT");

    private final int value;
    private final String group;

    EGLenum(int value, String group) {
        this.value = value;
        this.group = group;
    }

    /**
     * Returns the numeric value of this enum.
     *
     * @return the numeric value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the group this enum belongs to.
     *
     * @return the group name
     */
    public String getGroup() {
        return group;
    }

    /**
     * Returns the enum value for the given numeric value.
     *
     * @param value the numeric value
     * @return the enum value, or null if not found
     */
    public static EGLenum fromValue(int value) {
        for (EGLenum e : values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;
    }
}
