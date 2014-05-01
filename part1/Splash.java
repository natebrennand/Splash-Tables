
import java.lang.Integer;
import java.util.HashSet;
import java.util.Set;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class Splash {
    public static void printUsage() {
        System.out.println("USAGE: java Splash B R S h inputfile [dumpfile]"
                           + "\n\tB: # of elements per bucket"
                           + "\n\tR: # of reinsertions allowed"
                           + "\n\tS: number of elements = 2^S"
                           + "\n\th: number of random 32-bit multiplicative hash functions");
        System.exit(0);
    }

    public static void main (String[] args) {
        // getting configuration
        if (args.length < 5) {
            printUsage();
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
            printUsage();
        }

        String inputFile = args[4], outputFile = null;
        if (args.length == 6) {
            outputFile = args[5];
        }

        // build table from input file
        Table splashTable = new Table(B, R, S, h, outputFile);

        // reading input file
        buildFromFile(splashTable, inputFile);

        // Load factor stats
        // System.out.printf("%f\n", splashTable.loadFactor());

        // Accept probe file input
        probe(splashTable);
    }

    /*  Build From File
     *
     *  Rebuilds the table from an input file of key:value pairs
     */
    public static void buildFromFile(Table t, String filename) {
        BufferedReader dumpFile = null;
        try {
            dumpFile = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Your file does not exist! Please try again.");
            System.exit(1);
        }

        try {
            while (dumpFile.ready()) {
                String data = dumpFile.readLine();
                String[] keyValue = data.split(" ");
                if (keyValue.length != 2) {
                    System.out.println("INVALID FORMAT: " + data);
                }
                if (true != t.insert(Integer.parseInt(keyValue[0]), Integer.parseInt(keyValue[1]))) {
                    break;
                }
            }
            dumpFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Your file broke! Please try again.");
            System.exit(1);
        }
    }

    /*  Probe
     *
     *  Searches the Splashtable for the key:value pair specified by the key received by stdin
     *  Prints key:value pairs to stdout in the form: "<k> <v>", the value can be 0 if the key is not found.
     */
    public static void probe(Table t) {
        String probe;
        int key, value;
        BufferedReader probeInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            while((probe = probeInput.readLine()) != null) {
                key = Integer.parseInt(probe);
                value = t.get(key);
                System.out.printf("%d %d\n", key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
