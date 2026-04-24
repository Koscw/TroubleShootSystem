package org.troubleshootsystem.bridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterParser {

    public Process process;

    public InterpreterParser(Process process) {
        this.process = process;
    }


    public String parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        String result = "";
        while ((line = reader.readLine()) != null && result.isEmpty()) {
            if (line.startsWith("RESULT:")) {
                result = line.substring(7);
            }
        }

        if (result.isEmpty()) {
            throw new RuntimeException("No response received.");
        }

        return result;
    }
}
