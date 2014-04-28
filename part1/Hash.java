
import java.lang.Math;
import java.lang.Integer;

class Hash {

    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn
    private int size;               // size of the table, all hashes returned must be in [0, size)
    private int bucketSize;

    private int UNSIGNED_MASK = (int)(Math.pow(2, 32) - 1);


    public Hash(int numHashes, int size, int bucketSize) {
        hashMultipliers = new int[numHashes];
        for (int i=0; i<numHashes; i++) {
            this.hashMultipliers[i] = (int)(Math.random() * Integer.MAX_VALUE/2) + Integer.MAX_VALUE/2;
        }
        this.size = size;
        this.bucketSize = bucketSize;
    }

    /*  Buckets
     *
     *  @return: int arrray of 'h' buckets that the key is allowed to be inserted into
     */
    public int[] Buckets(int key) {
        int[] buckets = new int[this.hashMultipliers.length];
        long hash;

        for (int i=0; i<buckets.length; i++) {
            hash = hashFn(key, this.hashMultipliers[i]);
            buckets[i] = (int)(hash / this.bucketSize);
        }

        return buckets;
    }

    /*  Hash Function
     *  Multiplicative hashing function
     *
     *  @return: hash for an integer given the specified multiplier
     */
    private long hashFn(int key, int multiplier) {
        // floor(m * frac(k*a))
        return (long)(multiplier * key >>> (32 - this.size));
    }

    /*  Get Multipliers
     *  @return: string of multipliers used by the hash function
     */
    public int[] getMultipliersStr() {
        String multiplierStr = String.format("%d", this.hashMultipliers[0]);
        for (int i=1; i<this.hashMultipliers.length; i++) {
            multiplierStr += String.format(" %d", this.hashMultipliers[i]);
        }
        return this.hashMultipliers;
    }
}

