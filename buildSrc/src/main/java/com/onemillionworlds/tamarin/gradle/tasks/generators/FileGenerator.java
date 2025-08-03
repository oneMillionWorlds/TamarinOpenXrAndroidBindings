package com.onemillionworlds.tamarin.gradle.tasks.generators;

import org.gradle.api.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Base class for file generators.
 */
public abstract class FileGenerator {
    protected final Logger logger;

    public FileGenerator(Logger logger) {
        this.logger = logger;
    }

    /**
     * Generate a file.
     *
     * @param outputDir The directory where the file should be generated.
     * @throws IOException If an I/O error occurs.
     */
    public abstract void generate(File outputDir) throws IOException;

    /**
     * Create a writer for the specified file.
     *
     * @param file The file to write to.
     * @return A BufferedWriter for the file.
     * @throws IOException If an I/O error occurs.
     */
    protected BufferedWriter createWriter(File file) throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }

    /**
     * Log a message about file generation.
     *
     * @param fileName The name of the generated file.
     */
    protected void logGeneration(String fileName) {
        logger.lifecycle("Generated {}", fileName);
    }
}