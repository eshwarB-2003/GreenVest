package com.greenvest.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileUtil {

    private final Path dataDirectory;

    public JsonFileUtil(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public Path resolve(String fileName) {
        return dataDirectory.resolve(fileName);
    }

    public void ensureDataDirectoryExists() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create data directory: " + dataDirectory, e);
        }
    }
}
