import java.lang.Math;
import java.util.Random;

/*
 *  Represents a "Splash" hashtable.
 */
class Table {
    private Bucket[] buckets;       // B, number of elements per hash bucket
    private int numReinsertions;    // R, number of reinsertions before failure
    private Hash hashes;            // Hash generator that creates H hashes given an integer key

    public Table(int bucketSize, int numReinsertions, int numTableEntries, int numHashes) {
        this.numReinsertions = numReinsertions;
        this.hashes = new Hash(numHashes);

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
            dump();
        }

        // generate hashes for key
        int[] hashes = this.hashes.Hash(key);

        // generate bucket indexes
        int[] bucketIndexes = new int[hashes.length];
        for (int i=0; i<hashes.length; i++) {
            bucketIndexes[i] = (hashes[i] / hashes.length) % hashes.length;
        }

        // fail if key already exists
        for (int i=0; i<bucketIndexes.length; i++) {
            if (this.buckets[bucketIndexes[i]].get(key) >= 0) {
                dump();
            }
        }

        // try to insert key, dump table & exit if it fails
        if (!reinsert(key, value, this.numReinsertions)) {
            dump();
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

        // generate hashes for key
        int[] hashes = this.hashes.Hash(key);

        // generate bucket indexes
        int[] bucketIndexes = new int[hashes.length];
        for (int i=0; i<hashes.length; i++) {
            bucketIndexes[i] = (hashes[i] / hashes.length) % hashes.length;
        }

        // find emptiest bucket, default to a random bucket
        Random r = new Random();
        int bucketIndex = r.nextInt(bucketIndexes.length), bucketSpace = 0, spaceLeft;
        for (int i=0; i<bucketIndexes.length; i++) {
            spaceLeft = this.buckets[bucketIndexes[i]].spaceLeft();
            if (spaceLeft > bucketSpace) {
                bucketIndex = i;
                bucketSpace = spaceLeft;
            }
        }

        // finish if space is available
        if (this.buckets[bucketIndex].hasSpace()) {
            this.buckets[bucketIndex].set(key, value);
            return true;
        }

        // recall reinsert if there are tries left
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
        int bucketIndex, value;
        int[] hashes = this.hashes.Hash(key);
        for (int i=0; i<hashes.length; i++) {
            bucketIndex = (hashes[0] / hashes.length) % hashes.length;
            value = this.buckets[bucketIndex].get(key);
            if (value > 0) {
                return value;
            }
        }
        return -1;
    }

    private void dump() {
        // FAIL & DUMP FILE, to be implemented
    }
}

