package org.troubleshootsystem.bridge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrologPipe extends FilePipe {
    private final HashMap<String, String> arguments = new HashMap<>();

    public PrologPipe(String path) {
        super(path);
    }

    public void addArgument(String key, String value) {
        arguments.put(key, value);
    }

    public void write() throws IOException {
        FileWriter writer = new FileWriter(getPath());

        for (Map.Entry<String, String> entry : arguments.entrySet()) {
            writer
                    .append("status(")
                    .append(entry.getKey())
                    .append(", ")
                    .append(entry.getValue())
                    .append(").\n");
        }
        writer.close();
    }


    @Override
    public void cleanUp() {
        //super.cleanUp();

        try{
            new java.io.PrintWriter(getPath()).close();
        } catch (java.io.IOException e){

        }
        arguments.clear();

    }
}
