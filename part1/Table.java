
import java.lang.Math;
import java.lang.Integer;

/*
 *  Represents a "Splash" hashtable.
 */
class Table {
    private int bucketSize;         // B, number of elements per hash bucket
    private int numReinsertions;    // R, number of reinsertions before failure
    private int numTableEntries;    // S, logarithmic size of table(N log 2)
    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn

    public Table(int bucketSize, int numReinsertions, int numTableEntries, int numHashes){
        this.bucketSize = bucketSize;
        this.numReinsertions = numReinsertions;
        this.numTableEntries = numTableEntries;

        this.hashMultipliers = new int[numHashes];
        for (int i=0; i<numHashes; i++) {
            this.hashMultipliers[i] = (int)(Math.random() * Integer.MAX_VALUE);
        }
    }
}
