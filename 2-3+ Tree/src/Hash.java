/**
 * Hash table class creates tables and manipulates data in the tables.
 *
 * @author CS3114 staff
 * @version August 27, 2016
 * 
 * @author Patrick T. McKee (bludevil)
 * @author Randy Liang (randy8)
 * @version September 12, 2016
 */
public class Hash {

    private Handle[] handle;
    private MemoryManager pool;
    private int size;
    private int capacity;
    private String hashType;

    /**
     * Create a new Hash table.
     * 
     * @param hashType
     *            hashType of the hash table.
     * @param size
     *            Size of the hash table.
     * @param pool
     *            A pointer to the memory pool.
     */
    public Hash(String hashType, int size, MemoryManager pool) {
        this.handle = new Handle[size];
        this.pool = pool;
        this.size = 0;
        this.hashType = hashType;
        this.capacity = size;
    }

    /**
     * Print all strings in the hash table.
     */
    public void print() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.handle[i] != null && this.handle[i].pos() != -1) {
                System.out.println(
                        "|" + this.getString(this.handle[i]) + "| " + i);
            }
        }
        System.out.println("total " + this.hashType + "s: " + this.size);
    }

    /**
     * Insert the entry to HashTable.
     * 
     * @param h
     *            The Handle to be inserted.
     */
    public void insert(Handle h) {
        if (size + 1 > capacity / 2) {
            expand();
        }

        int home = h(new String(this.pool.get(h)), capacity);
        int pos = home;

        for (int i = 1; this.handle[pos] != null; i++) {
            if (this.handle[pos].pos() == -1) {
                break;
            }
            pos = (home + i * i) % capacity;
        }
        this.handle[pos] = h;
        size++;
    }

    /**
     * Search the given key and return it.
     * 
     * @param key
     *            The key to be searched.
     * @return Return Handle if find, else return null.
     */
    public Handle search(String key) {
        int home = h(key, this.capacity);
        int pos = home;

        for (int i = 1; this.handle[pos] != null; i++) {
            if (this.handle[pos].thePos != -1
                    && this.getString(this.handle[pos]).equals(key)) {
                return this.handle[pos];
            }
            pos = (home + i * i) % capacity;
        }
        return null;
    }

    /**
     * Remove the given String.
     * 
     * @param key
     *            The key to be removed.
     * @return Return Handle if found, else return null.
     */
    public Handle remove(String key) {
        int home = h(key, capacity);
        int pos = home;
        Handle removed = new Handle(-1);

        for (int i = 1; this.handle[pos] != null; i++) {
            if (this.handle[pos].thePos != -1
                    && this.getString(this.handle[pos]).equals(key)) {
                Handle h = this.handle[pos];
                this.handle[pos] = removed;
                size--;
                return h;
            }
            pos = (home + i * i) % capacity;
        }
        return null;
    }

    /**
     * Check if the hash table needs to expand.
     * 
     * @param key
     *            the key being searched for
     * @return true if it needs to be expanded
     */
    public boolean rehashNeeded(String key) {
        if (this.search(key) != null) {
            System.out
                    .println("|" + key + "| duplicates a record already in the "
                            + this.hashType + " database.");
            return false;
        }
        if (size + 1 > capacity / 2) {
            this.expand();
        }
        return true;
    }

    /**
     * return size
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * return capacity
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * get the string
     * 
     * @param h
     *            the given Handle
     * @return return the converted string
     */
    public String getString(Handle h) {
        return new String(this.pool.get(h));
    }
    
    /**
     * Expands the size of hash table when the size 
     * exceeds half of the capacity.
     */
    private void expand() {
        Handle[] temp = new Handle[this.capacity * 2];
        this.capacity *= 2;

        for (int i = 0; i < capacity / 2; i++) {
            int home;
            int pos;

            if (this.handle[i] != null && this.handle[i].pos() != -1) {
                home = h(this.getString(this.handle[i]), capacity);
                pos = home;

                for (int j = 1; temp[pos] != null; j++) {
                    pos = (home + j * j) % capacity;
                }
                temp[pos] = this.handle[i];
            }
        }
        this.handle = temp;
        System.out.println(hashType + " hash table size doubled.");
    }

    /**
     * Calculate the hash code through key.
     * 
     * @param key
     *            The String to be calculated.
     * @param m
     *            The capacity of the array.
     * @return The hash code.
     */
    private int h(String key, int m) {
        String s = (String) key;
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }
        return (int) (Math.abs(sum) % m);
    }
}