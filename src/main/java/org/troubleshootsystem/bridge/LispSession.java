package org.troubleshootsystem.bridge;

import java.io.IOException;

public class LispSession {
    private final String executablePath;

    public LispSession(String executablePath) {
        this.executablePath = executablePath;
    }

    public String execute(String argument) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "swipl",
                "-q",
                "-s",
                executablePath,
                "-g",
                "on_java_request('" + argument + "'),halt."
        );

        Process process = builder.start();
        InterpreterParser parser = new InterpreterParser(process);
        String result = parser.parse();
        process.waitFor();
        return result;
    }
}
