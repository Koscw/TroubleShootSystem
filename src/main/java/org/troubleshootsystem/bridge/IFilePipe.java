package org.troubleshootsystem.bridge;

import java.io.IOException;

public interface IFilePipe {
    String getPath();

    void write() throws IOException;

    void cleanUp();
}
