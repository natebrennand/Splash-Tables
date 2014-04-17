
import java.lang.Math;
import java.lang.Integer;

class Hash {

    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn


    public Hash(int numHashes) {
        hashMultipliers = new int[numHashes];
        for (int i=0; i<numHashes; i++) {
            this.hashMultipliers[i] = (int)(Math.random() * Integer.MAX_VALUE);
        }
    }

    public int[] Hash(int key) {
        // TODO: return a array of multiplicative hashes
        //       should use all of the hash multipliers
        /* hash(x) = ((M * x) mod 2^w)/2^(w-d)
         * w = bit size of integers (assumed to be 32)
         * M = hash multiplier
         * d = high order bits (chosen to be 8)
        */
        int[] hashes = new int[this.hashMultipliers.length];
        for(int i=0; i<hashes.length; i++) {
            double hashIndex = ((this.hashMultipliers[i] * key) % Math.pow(2, 32))/Math.pow(2,24);
            hashes[i] = (int) (Math.floor(hashIndex));
        }
        return hashes;
    }

}
