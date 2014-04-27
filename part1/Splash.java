
import java.lang.Integer;
import java.util.HashSet;
import java.util.Set;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Splash {
    public static void printUsuage() {
        System.out.println("USUAGE: java Splash B R S h inputfile [dumpfile]"
                           + "\n\tB: # of elements per bucket"
                           + "\n\tR: # of reinsertions allowed"
                           + "\n\tS: number of elements = 2^S"
                           + "\n\th: number of random 32-bit multiplicative hash functions");
        System.exit(0);
    }

    public static void main (String[] args) {
        // getting configuration
        if (args.length < 5) {
            printUsuage();
        }
        int B = -1, R = -1, S = -1, h = -1;
        try {
            B = Integer.parseInt(args[0]);
            R = Integer.parseInt(args[1]);
            S = Integer.parseInt(args[2]);
            h = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (B <= 0 || R <= 0 || S <= 0 || h <= 0) {
            printUsuage();
        }

        String inputFile = args[4], outputFile = null;
        if (args.length == 6) {
            outputFile = args[5];
            printUsuage();
        }

        Table splashTable = new Table(B, R, S, h, outputFile);
        // reading input file
        splashTable.BuildFromFile(inputFile);

        System.out.println(splashTable.toString());

        probe(splashTable);
    }


    public static void probe(Table t) {
        String probe;
        int key, value;
        BufferedReader probeInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            while((probe = probeInput.readLine()) != null) {
                key = Integer.parseInt(probe);
                value = t.get(key);
                if (value > -1) {
                    System.out.printf("%d %d\n", key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
