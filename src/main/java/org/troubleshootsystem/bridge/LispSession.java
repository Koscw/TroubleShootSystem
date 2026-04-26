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

/*  i dont know if this works or not. i barely understand it.
    would be helpful to check and use this because i dont think lisp works with swipl
    
package org.troubleshootsystem.bridge;
import java.io.IOException;

public class LispSession {
    private final String executablePath;

    public LispSession(String executablePath) {
        // This expects the path to your troubleshoot.lisp file
        this.executablePath = executablePath;
    }

    public String execute(String argument) throws IOException, InterruptedException {
        // The SBCL Execution Bridge
        ProcessBuilder builder = new ProcessBuilder(
                "sbcl",
                "--script",
                executablePath, 
                argument        
        );

        // Armor: Merges error output with standard output so Java doesn't hang on a Lisp crash
        builder.redirectErrorStream(true);

        Process process = builder.start();
        
        // We hand the OS process over to the custom parser to read the text
        InterpreterParser parser = new InterpreterParser(process);
        String result = parser.parse();
        
        process.waitFor();
        return result;
    }
}
*/
