package org.troubleshootsystem.bridge;

import java.io.IOException;

public class PrologSession {
    private final ProcessBuilder builder;
    IFilePipe filePipe;

    public PrologSession(String executable_path, IFilePipe filePipe) {
        builder = new ProcessBuilder(
                "gprolog",
                "--consult-file", executable_path,
                "--consult-file", filePipe.getPath(),
                "--query-goal", "run_diagnose"
                //"--entry-goal", "halt(0)"
        );

        builder.redirectErrorStream(true);
        this.filePipe = filePipe;
    }

    public String execute() throws IOException, InterruptedException {
        filePipe.write();
        Process process = builder.start();
        InterpreterParser parser = new InterpreterParser(process);
        String result = parser.parse();

        process.waitFor();
        filePipe.cleanUp();
        return result;
    }
}
