
import java.lang.Math;
import java.lang.String;

import java.util.Random;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

/*
 *  Represents a "Splash" hashtable.
 */
class Table {
    private Bucket[] buckets;       // B, number of elements per hash bucket
    private int numReinsertions;    // R, number of reinsertions before failure
    private int bucketSize;
    private int numTableEntries;
    private int numHashes;
    private Hash hashes;            // Hash generator that creates H hashes given an integer key
    private String dumpFile;        // Location to dump data

    public Table(int bucketSize, int numReinsertions, int numTableEntries, int numHashes, String dumpFile) {
        this.bucketSize = bucketSize;
        this.numReinsertions = numReinsertions;
        this.numTableEntries = numTableEntries;
        this.numHashes = numHashes;

        this.hashes = new Hash(numHashes, numTableEntries, bucketSize);
        this.dumpFile = dumpFile;

        int numBuckets = (int)(Math.pow(2, numTableEntries) / bucketSize);
        this.buckets = new Bucket[numBuckets];
        for (int i=0; i<numBuckets; i++) {
            this.buckets[i] = new Bucket(bucketSize);
        }
    }

    /*  Set
     *  Tries to insert the key value pair into the splash table
     *
     *  @param key: key of the key-value pair being inserted
     *  @param value: value of the key-value pair being inserted
     */
    public void insert(int key, int value) {
        // fail if using invalid key
        if (key <= 0) {
            dump("Invalid key");
        }
        // generate buckets from hashes for key
        int[] bucketIndexes = this.hashes.Buckets(key);
        // fail if key already exists
        for (int index: bucketIndexes) {
            if (this.buckets[index].get(key) > 0) {
                dump("The key already existed");
            }
        }
        // try to insert key, if it fails: dump table & exit
        if (!reinsert(key, value, this.numReinsertions)) { // called recursively
            dump(String.format("All %d reinsertions were used", this.numReinsertions));
        }
    }

    /*  Reinsert
     *  Tries to find a bucket to insert the key:value pair into. Will displace other pairs
     *  and attempt to find an available spot for them.  
     *
     *  @param key: key of the key-value pair being inserted
     *  @param value: value of the key-value pair being inserted
     *  @param triesLeft: number of times a pair can be evicted while trying to find space
     */
    private boolean reinsert(int key, int value, int triesLeft) {
        triesLeft -= 1; // decrement for using a try
        Random r = new Random();
        // generate bucket indexes
        int[] bucketIndexes = this.hashes.Buckets(key);
        // find emptiest bucket, default to a random bucket
        int bucketIndex = bucketIndexes[r.nextInt(bucketIndexes.length)],
            bucketSpace = 0,
            spaceLeft;
        for (int index: bucketIndexes) {
            spaceLeft = this.buckets[index].spaceLeft();
            if (spaceLeft > bucketSpace) {
                bucketIndex = index;
                bucketSpace = spaceLeft;
            }
        }
        // if space is available: insert and finish
        if (this.buckets[bucketIndex].hasSpace()) {
            this.buckets[bucketIndex].set(key, value);
            return true;
        }
        // call reinsert if there are tries left
        if (triesLeft > 0) {
            int[] evictedPair = this.buckets[bucketIndex].getOldestValues();
            this.buckets[bucketIndex].set(key, value);
            return reinsert(evictedPair[0], evictedPair[1], triesLeft);
        }
        return false;
    }

    /*  Get
     *
     *  @param: integer key
     *  @return: value (>0) if exists, 0 otherwise
     */
    public int get(int key) {
        // generate bucket indexes
        int[] bucketIndexes = this.hashes.Buckets(key);
        int valIndex = -1,
            value = 0,
            index;

        // check for key
        for (int i=0; i<bucketIndexes.length; i++) {
            index = bucketIndexes[i];
            value |= this.buckets[index].get(key);
        }

        return value;
    }

    private void abortDump(String err) {
        System.err.println(err);
        dump(err);
        System.exit(1);
    }

    /*  Dump
     *
     *  Creates a dump file (if specified on CLI) with the contents of the table
     */
    public void dump(String err) {

        if (this.dumpFile != null) {
            try {
                PrintWriter dumpFile = new PrintWriter(this.dumpFile);

                dumpFile.println(String.format("%d %d %d %d", this.bucketSize, this.numReinsertions, this.numTableEntries, this.numHashes));
                dumpFile.println(this.hashes.getMultipliersStr());

                for (Bucket b: this.buckets) {
                    dumpFile.print(b.toDumpFileString());
                }

                dumpFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*  To String
     *
     *  @return: String representation of table for debugging purposes
     */
    public String toString() {
        String contents = "";
        Bucket b;
        for (int i=0; i<this.buckets.length; i++) {
            b = this.buckets[i];
            contents += String.format("line %d: %s\n", i, b.toString());
        }
        return contents;
    }
}
