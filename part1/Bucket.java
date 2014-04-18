import java.util.Arrays;

import java.lang.String;

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
        return !(this.occupation == this.keys.length);
    }

    /*  Space left
     *
     *  @return: slots in the bucket left
     */
    public int spaceLeft() {
        return this.keys.length - this.occupation;
    }

    /*  Get
     *
     *  @return: the value if found, -1 otherwise
     */
    public int get(int key) {
        int index = -1;
        for (int i=0; i<this.keys.length; i++) {
            if (this.keys[i] == key) {
                index = i;
            }
        }
        return index;
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
            this.occupation += 1;
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

    public String toString() {
        String contents = "";
        for (int i = 0; i < this.keys.length; i++) {
            contents += String.format("%d:%d ", this.keys[i], this.values[i]);
        }
        return contents;
    }
}

