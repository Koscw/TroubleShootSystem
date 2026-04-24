package org.troubleshootsystem.bridge;

import java.io.File;

public abstract class FilePipe implements IFilePipe {
    private final String path;

    public FilePipe(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void cleanUp() {
        File file = new File(path);
        if (!file.delete()) {
            throw new RuntimeException("Invalid operation. Can't delete the file.");
        }
    }
}
