
import java.util.Arrays;

class Bucket {
    private int[] keys;
    private int[] values;
    private int occupation;

    public Bucket (int size) {
        this.keys = new int[size];
        this.values = new int[size];
    }

    /*  Has Space
     *
     *  @return: true if there is space in the bucket
     */
    public boolean hasSpace() {
        return this.occupation == this.keys.length;
    }

    /*  Get
     *
     *  @return: the value if found, -1 otherwise
     */
    public int get(int key) {
        //lol change this later plz
        int index = Arrays.binarySearch(Arrays.sort(Arrays.copyOf(this.keys, this.keys.length)), key);
        if (index > -1) {
            return this.values[index];
        }
        return -1;
    }

    /*  Insert
     *
     *  @param key: key of the key:value being stored
     *  @param value: key of the key:value being stored
     *  @return: true on clean insert, false on overwrite insert
     */
    public boolean set(int key, int value) {
        // still filling bucket
        if (this.hasSpace()) {
            this.keys[occupation] = key;
            this.values[occupation] = value;
            return true;
        }

        // if bucket is full, replace oldest value with new value
        int oldestKey = this.keys[0];
        int oldestValue = this.values[0];
        System.arraycopy(this.keys, 0, this.keys, 1, this.keys.length-1);
        System.arraycopy(this.values, 0, this.values, 1, this.values.length-1);
        this.keys[0] = key;
        this.values[0] = value;
        return false;
    }

    /*  Get oldest values
     *
     *  @return: array of oldest key & value
     */
    public int[] getOldestValues() {
        return new int[]{this.keys[0], this.values[0]};
    }
}

