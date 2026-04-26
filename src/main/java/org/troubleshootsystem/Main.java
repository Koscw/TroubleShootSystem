package org.troubleshootsystem;
import org.troubleshootsystem.bridge.LispSession;
import org.troubleshootsystem.bridge.PrologPipe;
import org.troubleshootsystem.bridge.PrologSession;

import java.io.*;

public class Main {
    /** No longer required, Troubleshooter.java is utilized as primary file to run the ui and send/receive from lisp and prolog
    public static void main(String[] args) throws IOException, InterruptedException {

        PrologPipe pipe = new PrologPipe("src/main/resources/prolog/prolog_arguments_tmp.pl");

        pipe.addArgument("outlet_works", "yes");
        pipe.addArgument("cable_plugged", "yes");
        pipe.addArgument("led_power", "on");
        pipe.addArgument("led_internet", "green");
        pipe.addArgument("paper_jammed", "yes");

        PrologSession prolog = new PrologSession("src/main/resources/prolog/analysis.pl", pipe);
        String prompt = prolog.execute();
        System.out.println(prompt);

        LispSession lisp = new LispSession("src/main/resources/Lisp/RecursiveTree.lisp");
        String lispDecision = lisp.lispExecute(prompt);
        System.out.println(lispDecision);

    }
     **/
}
