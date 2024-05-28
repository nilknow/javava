package com.nilknow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Entry {
    byte[] readClass(String className) throws IOException;

    String getPath();
}

class DirEntry implements Entry {

    private final String absPath;

    public DirEntry(String path) throws IOException {
        this.absPath = new File(path).getAbsolutePath().replace("\\.","");
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        String filePath = absPath + File.separator + className;
        return Files.readAllBytes(Paths.get(filePath));
    }

    @Override
    public String getPath() {
        return absPath;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
