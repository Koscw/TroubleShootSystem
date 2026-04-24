package org.troubleshootsystem;
import org.troubleshootsystem.bridge.LispSession;
import org.troubleshootsystem.bridge.PrologPipe;
import org.troubleshootsystem.bridge.PrologSession;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        PrologPipe pipe = new PrologPipe("prolog_arguments_tmp.pl");

        pipe.addArgument("outlet_works", "yes");
        pipe.addArgument("cable_plugged", "yes");
        pipe.addArgument("led_power", "on");
        pipe.addArgument("led_internet", "green");
        pipe.addArgument("paper_jammed", "yes");

        PrologSession prolog = new PrologSession("src/main/resources/analysis.pl", pipe);
        String prompt = prolog.execute();
        System.out.println(prompt);

        LispSession lisp = new LispSession("src/main/resources/reparir.lisp");

        System.out.println(lisp.execute(prompt));

    }
}
