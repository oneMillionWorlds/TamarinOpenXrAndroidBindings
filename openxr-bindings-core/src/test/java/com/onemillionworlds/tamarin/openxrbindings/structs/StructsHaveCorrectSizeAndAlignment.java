package com.onemillionworlds.tamarin.openxrbindings.structs;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StructsHaveCorrectSizeAndAlignment {

    private static final String PACKAGE = "com.onemillionworlds.tamarin.openxrbindings.";

    @Test
    public void structsHaveExpectedSizeof() {
        iterateExpectedSizes((className, expectedSize, expectedAlign) -> {
            try {
                Class<?> clazz = Class.forName(PACKAGE + className);
                Field sizeofField = clazz.getField("SIZEOF");
                int actualSize = sizeofField.getInt(null);
                assertEquals(expectedSize.intValue(), actualSize,
                        () -> String.format("SIZEOF mismatch for %s. Expected=%d Actual=%d", className, expectedSize, actualSize));
            } catch (ClassNotFoundException e) {
                // Class not present is considered a pass per requirements
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fail("Reflection error for " + className + " SIZEOF: " + e.getMessage());
            }
        });
    }

    @Test
    public void structsHaveExpectedAlignof() {
        iterateExpectedSizes((className, expectedSize, expectedAlign) -> {
            try {
                Class<?> clazz = Class.forName(PACKAGE + className);
                Field alignofField = clazz.getField("ALIGNOF");
                int actualAlign = alignofField.getInt(null);
                assertEquals(expectedAlign.intValue(), actualAlign,
                        () -> String.format("ALIGNOF mismatch for %s. Expected=%d Actual=%d", className, expectedAlign, actualAlign));
            } catch (ClassNotFoundException e) {
                // Class not present is considered a pass per requirements
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fail("Reflection error for " + className + " ALIGNOF: " + e.getMessage());
            }
        });
    }

    private interface RowConsumer {
        void accept(String className, Integer expectedSize, Integer expectedAlign) throws IOException;
    }

    private static void iterateExpectedSizes(RowConsumer consumer) {
        InputStream in = StructsHaveCorrectSizeAndAlignment.class.getClassLoader().getResourceAsStream("expectedSizes.csv");
        if (in == null) {
            fail("expectedSizes.csv resource not found on test classpath");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) { // skip header
                    first = false;
                    continue;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    fail("Malformed CSV line (expected 3 columns): " + line);
                    return;
                }
                String className = parts[0].trim();
                Integer expectedSize = Integer.parseInt(parts[1].trim());
                Integer expectedAlign = Integer.parseInt(parts[2].trim());
                consumer.accept(className, expectedSize, expectedAlign);
            }
        } catch (IOException e) {
            fail("Failed reading expectedSizes.csv: " + e.getMessage());
        }
    }
}
