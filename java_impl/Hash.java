
import java.lang.Math;
import java.lang.Integer;

import java.util.Random;

class Hash {

    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn
    private int size;               // size of the table, all hashes returned must be in [0, size)
    private int bucketSize;

    public Hash(int numHashes, int size, int bucketSize) {
        hashMultipliers = new int[numHashes];
        Random r = new Random();
		int hashValue;
        for (int i=0; i<numHashes; i++) {
            hashValue = r.nextInt();
            while(hashValue % 2 != 1) { // guarantee odd multipliers
                hashValue = r.nextInt();
            }
            this.hashMultipliers[i] = hashValue;
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
        return (long)(multiplier * key >>> (32 - this.size));
    }

    /*  Get Multipliers String
     *  @return: string of multipliers used by the hash function
     */
    public String getMultipliersStr() {
        String multiplierStr = String.format("%d", this.hashMultipliers[0]);
        for (int i=1; i<this.hashMultipliers.length; i++) {
            multiplierStr += String.format(" %d", this.hashMultipliers[i]);
        }
        return multiplierStr;
    }
}
