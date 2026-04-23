package org.example;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {

            Map<String, String> statuses = new HashMap<>();

            statuses.put("outlet_works", "yes");
            statuses.put("cable_plugged", "yes");
            statuses.put("led_power", "on");
            statuses.put("led_internet", "green");
            statuses.put("paper_jammed", "yes");



            File tempFacts = new File("src/main/resources/prolog/temp_facts.pl");
            try (PrintWriter out = new PrintWriter(tempFacts)) {
                for (Map.Entry<String, String> entry : statuses.entrySet()) {
                    out.println("status(" + entry.getKey() + ", " + entry.getValue() + ").");
                }
            }


            ProcessBuilder pb = new ProcessBuilder(
                    "gprolog",
                    "--consult-file", "src/main/resources/prolog/analysis.pl",
                    "--consult-file", "src/main/resources/prolog/temp_facts.pl",
                    "--query-goal", "run_diagnose"
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("RESULT:")) {
                    String cause = line.substring(7);
                    System.out.println("Prolog found the cause of the problem-> " + cause);
                }
            }

            process.waitFor();
            //tempFacts.delete();

        } catch (Exception e) {
            System.out.println("Exception-> "+e);
        }
    }
}
