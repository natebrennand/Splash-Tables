

/*
 *  Represents a "Splash" hashtable.
 */
class Table {
    private Bucket[] buckets;       // B, number of elements per hash bucket
    private int numReinsertions;    // R, number of reinsertions before failure
    private Hash hashes;

    public Table(int bucketSize, int numReinsertions, int numTableEntries, int numHashes) {
        this.numReinsertions = numReinsertions;
        this.hashes = new Hash(numHashes);

        int numBuckets = 2^numTableEntries / bucketSize;
        this.buckets = new Bucket[numBuckets];
        for (int i=0; i<numBuckets; i++) {
            this.buckets[i] = new Bucket(bucketSize);
        }
    }

    public void insert(int key, int value) {
        // try to insert
        // dump then exit if exceeding recursion limit
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
}

