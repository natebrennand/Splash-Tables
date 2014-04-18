import java.lang.Integer;

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
        splashTable.BuildFromFile(inputFile);

        System.out.println(splashTable.toString());
    }
}
