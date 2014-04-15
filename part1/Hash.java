
import java.lang.Math;
import java.lang.Integer;

class Hash {

    private int[] hashMultipliers;  // h, number of random integers for the multiplicative hash fn

    public Hash(int numHashes) {
        for (int i=0; i<numHashes; i++) {
            this.hashMultipliers[i] = (int)(Math.random() * Integer.MAX_VALUE);
        }
    }

    public int[] Hash(int key) {
        // TODO: return a array of multiplicative hashes
        //       should use all of the hash multipliers
        return new int[]{0};
    }

}
