
import java.util.Arrays;
import java.lang.String;

class Bucket {
    private int[] keyValue;
    private int occupation;
    private int size;

    private int[] MASKS = new int[]{0b00000000, 0b11111111};

    public Bucket (int size) {
        this.keyValue = new int[2 * size];
        this.occupation = 0;
        this.size = size;
    }

    /*  Has Space
     *  @return: true if there is space in the bucket
     */
    public boolean hasSpace() {
        return !(this.occupation == this.size);
    }

    /*  Space left
     *  @return: slots in the bucket left
     */
    public int spaceLeft() {
        return this.size - this.occupation;
    }

    /*  Get
     *  @return: value associated with the key, otherwise 0
     */
    public int get(int key) {
        int value = 0,
            iterKey = 0,
            iterValue = 0,
            mask;

        for (int i=0; i<this.size; i++) {
            iterKey = this.keyValue[i];
            iterValue = this.keyValue[i + this.size];

            mask = MASKS[(key == iterKey)? 1 : 0];
            value = value | (mask & iterValue);
        }

        return value;
    }

    /*  Insert
     *  @param key: key of the key:value being stored
     *  @param value: key of the key:value being stored
     *  @return: true on clean insert, false on overwrite insert
     */
    public boolean set(int key, int value) {
        // still filling bucket
        if (this.hasSpace()) {
            int keyIndex = occupation,
                valIndex = occupation + this.size;
            this.keyValue[keyIndex] = key;
            this.keyValue[valIndex] = value;
            this.occupation += 1;
            return true;
        }

        // if bucket is full, replace oldest value with new value
        System.arraycopy(this.keyValue, 0, this.keyValue, 1, this.keyValue.length-1); // shifts left by one
        this.keyValue[0] = key;
        this.keyValue[this.size] = value;
        return false;
    }

    /*  Get oldest values
     *  @return: array of oldest key & value
     */
    public int[] getOldestValues() {
        return new int[]{this.keyValue[0], this.keyValue[this.size]};
    }

    /* To String
     * @return: String representation of bucket for debugging purposes
     */
    public String toString() {
        String contents = "";
        for (int i = 0; i < this.size; i++) {
            contents += String.format("%d:%d ", this.keyValue[i], this.keyValue[this.size + i]);
        }
        return contents;
    }
}

