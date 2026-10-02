package com.onemillionworlds.tamarin.openxrbindings;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Loads the native part of the bindings (openxrjni). Every class with native methods calls {@link #load()} from its
 * static initialiser.
 * <p>
 * On Android the library is installed with the app (it comes from the openxr-bindings-android AAR) so it is loaded by
 * name. On desktop it is inside the openxr-bindings-desktop jar (at natives/&lt;platform&gt;/) so it is extracted to a
 * directory named after its content hash under java.io.tmpdir and loaded from there.
 * </p>
 * <p>
 * The system property {@value #LIBRARY_PATH_PROPERTY} can be set to the path of an openxrjni library to use that
 * instead (e.g. when developing the native code).
 * </p>
 */
public final class NativeLibraryLoader {

    public static final String LIBRARY_NAME = "openxrjni";

    public static final String LIBRARY_PATH_PROPERTY = "tamarin.openxrbindings.libraryPath";

    private static boolean loaded = false;

    private NativeLibraryLoader() {}

    public static synchronized void load() {
        if (loaded) {
            return;
        }
        String overridePath = System.getProperty(LIBRARY_PATH_PROPERTY);
        if (overridePath != null) {
            System.load(Paths.get(overridePath).toAbsolutePath().toString());
        } else if (isAndroid()) {
            System.loadLibrary(LIBRARY_NAME);
        } else {
            System.load(extractDesktopLibrary().toString());
        }
        loaded = true;
    }

    private static boolean isAndroid() {
        // ART (and Dalvik before it) report themselves as Dalvik
        return "Dalvik".equals(System.getProperty("java.vm.name"));
    }

    /**
     * The directory (under natives/ in the openxr-bindings-desktop jar) holding the library for this platform, e.g.
     * windows-x64
     */
    static String desktopPlatform() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String os;
        if (osName.startsWith("windows")) {
            os = "windows";
        } else if (osName.startsWith("linux")) {
            os = "linux";
        } else {
            throw new UnsatisfiedLinkError("The OpenXR bindings don't support the operating system " + osName);
        }
        String archName = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        String arch;
        if (archName.equals("amd64") || archName.equals("x86_64")) {
            arch = "x64";
        } else if (archName.equals("aarch64") || archName.equals("arm64")) {
            arch = "arm64";
        } else {
            throw new UnsatisfiedLinkError("The OpenXR bindings don't support the architecture " + archName);
        }
        return os + "-" + arch;
    }

    private static Path extractDesktopLibrary() {
        String platform = desktopPlatform();
        String fileName = System.mapLibraryName(LIBRARY_NAME);
        String resource = "/natives/" + platform + "/" + fileName;

        byte[] library;
        try (InputStream in = NativeLibraryLoader.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new UnsatisfiedLinkError("Could not find " + resource + " on the classpath. On desktop the "
                        + "com.onemillionworlds.tamarin:openxr-bindings-desktop dependency is required (and on Android "
                        + "openxr-bindings-android)");
            }
            library = readAll(in);
        } catch (IOException e) {
            throw linkError("Could not read " + resource, e);
        }

        // named after the content so different versions never overwrite each other (or a library that is in use)
        Path directory = Paths.get(System.getProperty("java.io.tmpdir"), "tamarin-openxrbindings", platform + "-" + sha256(library));
        Path target = directory.resolve(fileName);
        try {
            if (!Files.exists(target) || Files.size(target) != library.length) {
                Files.createDirectories(directory);
                // write to a temporary file then move it into place, so another process never sees a partial library
                Path temporary = Files.createTempFile(directory, LIBRARY_NAME, ".tmp");
                Files.write(temporary, library);
                try {
                    Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    Files.deleteIfExists(temporary);
                    // fine if another process got there first (the content is the same, and on Windows it may be
                    // loaded so can't be replaced)
                    if (!Files.exists(target) || Files.size(target) != library.length) {
                        throw e;
                    }
                }
            }
        } catch (IOException e) {
            throw linkError("Could not extract " + resource + " to " + target, e);
        }
        return target;
    }

    private static byte[] readAll(InputStream in) throws IOException {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        byte[] buffer = new byte[64 * 1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }

    private static String sha256(byte[] bytes) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
            StringBuilder hex = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                hex.append(String.format("%02x", digest[i]));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static UnsatisfiedLinkError linkError(String message, Throwable cause) {
        UnsatisfiedLinkError error = new UnsatisfiedLinkError(message);
        error.initCause(cause);
        return error;
    }
}
