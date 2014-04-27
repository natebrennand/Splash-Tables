import java.lang.Math;
import java.lang.String;

import java.util.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 *  Represents a "Splash" hashtable.
 */
class Table {
    private Bucket[] buckets;       // B, number of elements per hash bucket
    private int numReinsertions;    // R, number of reinsertions before failure
    private int bucketSize;
    private Hash hashes;            // Hash generator that creates H hashes given an integer key
    private String dumpFile;        // Location to dump data

    public Table(int bucketSize, int numReinsertions, int numTableEntries, int numHashes, String dumpFile) {
        this.numReinsertions = numReinsertions;
        this.bucketSize = bucketSize;
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

        System.out.printf("%d:%d buckets:[%d, %d]\n", key, value, bucketIndexes[0], bucketIndexes[1]);


        // fail if key already exists
        for (int i=0; i<bucketIndexes.length; i++) {
            if (this.buckets[bucketIndexes[i]].getIndex(key) >= 0) {
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
        for (int i=0; i<bucketIndexes.length; i++) {
            spaceLeft = this.buckets[bucketIndexes[i]].spaceLeft();
            if (spaceLeft > bucketSpace) {
                bucketIndex = bucketIndexes[i];
                bucketSpace = spaceLeft;
            }
        }

        // if space is available: insert and finish
        if (this.buckets[bucketIndex].hasSpace()) {
            System.out.println("Has space!");
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
     *  @return: value (>0) if exists, -1 otherwise
     */
    public int get(int key) {
        // generate bucket indexes
        int[] bucketIndexes = this.hashes.Buckets(key);
        int valIndex = -1;
        Bucket b;

        // check for key
        for (int i=0; i<bucketIndexes.length; i++) {
            b = this.buckets[bucketIndexes[i]];

            valIndex = b.getIndex(key);
            if (valIndex >= 0) {
                return b.get(valIndex);
            }
        }

        return -1;
    }

    /*  Build From File
     *
     *  Rebuilds the table from an input file of key:value pairs
     */
    public void BuildFromFile(String filename) {
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
                this.insert(Integer.parseInt(keyValue[0]), Integer.parseInt(keyValue[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Your file broke! Please try again.");
            System.exit(1);
        }
    }

    /*  Dump
     *
     *  Creates a dump file (if specified on CLI) with the contents of the table
     */
    private void dump(String err) {
        // FAIL & DUMP FILE, to be implemented
        System.out.println(err);
        System.out.println("Failed to insert\nDumping hash table to file.");
        System.exit(1);
    }

    /*  To String
     *
     *  @return: String representation of table for debugging purposes
     */
    public String toString() {
        String contents = "";
        for (int i=0; i<this.buckets.length; i++) {
            contents += String.format("line %d: %s\n", i, this.buckets[i].toString());
        }
        return contents;
    }
}
