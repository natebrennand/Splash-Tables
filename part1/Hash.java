
import java.lang.Math;
import java.lang.Integer;

class Hash {

    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn
    private int size;               // size of the table, all hashes returned must be in [0, size)
    private int bucketSize;


    public Hash(int numHashes, int size, int bucketSize) {
        hashMultipliers = new int[numHashes];
        for (int i=0; i<numHashes; i++) {
            this.hashMultipliers[i] = (int)(Math.random() * Integer.MAX_VALUE);
            if (this.hashMultipliers[i] < Integer.MAX_VALUE/2) {
                i -= 1; // redo hash to ensure it is a high number
            }
        }
        this.size = size;
        this.bucketSize = bucketSize;
    }

    /*  Hash
     *
     *  @return: int array of the 'h' hashes for this key
     */
    private int[] Hash(int key) {
        int[] hashes = new int[this.hashMultipliers.length];
        for(int i=0; i<hashes.length; i++) {
            hashes[i] = hashFn(key, this.hashMultipliers[i]) >>> (31-this.size);
        }
        return hashes;
    }

    /*  Buckets
     *
     *  @return: int arrray of 'h' buckets that the key is allowed to be inserted into
     */
    public int[] Buckets(int key) {
        int[] hashes = this.Hash(key);
        int[] buckets = new int[hashes.length];

        for (int i=0; i<hashes.length; i++) {
            buckets[i] = (hashes[i] / this.bucketSize);
        }

        return buckets;
    }

    /*  Hash Function
     *  Multiplicative hashing function
     *
     *  @return: hash for an integer given the specified multiplier
     */
    private int hashFn(int key, int multiplier) {
        /* floor(m * frac(k*a))
         * k: integer hash code
         * a: real number
         * frac: fn that returns the fractional part of a real number
         */
        double hashIndex = ((multiplier * key) % Math.pow(2, 32));
        return Math.abs((int)(Math.floor(hashIndex)));
    }
}

