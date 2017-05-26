/**
 * Deals with the byte array, manipulates the records in tandem with memory pool
 * and updates the freeblock list.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version 2016.09.14
 *
 */
public class MemoryManager {

    private byte[] pool;
    private FreeBlockList fbl;
    private int newSize;
    private String expanded;

    /**
     * Create a new Memory manager.
     * 
     * @param size
     *            Size of memory pool.
     */
    public MemoryManager(int size) {
        this.pool = new byte[size];
        this.fbl = new FreeBlockList(size);
        this.newSize = size;
        this.expanded = "";
    }

    /**
     * Insert a String to memory pool.
     * 
     * @param str
     *            String
     * @return Return a Handle of the String.
     */
    public Handle insert(String str) {
        int pos = fbl.search(str.length() + 2);

        while (pos == -1) {
            int newPos = pool.length;
            expand();
            expanded += "Memory pool expanded to be " 
                + pool.length + " bytes.\n";
            fbl.add(newPos, newSize);
            pos = fbl.search(str.length() + 2);
        }
        fbl.split(pos, str.length() + 2);
        byte[] length = new byte[] { (byte) (str.length() >> 8),
            (byte) str.length() };
        byte[] string = str.getBytes();
        pool[pos] = length[0];
        pool[pos + 1] = length[1];

        for (int i = 0; i < str.length(); i++) {
            pool[pos + i + 2] = string[i];
        }
        return new Handle(pos);
    }

    /**
     *
     * if pool has expanded, print expanded statement
     * 
     */
    public void expanded() {
        if (!expanded.equals("")) {
            System.out.print(expanded);
            expanded = "";
        }
    }

    /**
     * Expand the memory pool when it is full.
     */
    private void expand() {
        byte[] temp = new byte[pool.length + newSize];

        for (int i = 0; i < this.pool.length; i++) {
            temp[i] = pool[i];
        }
        pool = temp;
    }

    /**
     * The get method to get the byte array according to the given handle
     * 
     * @param hand
     *            the given handle
     * @return the byte array which the handle pointing to
     */
    public byte[] get(Handle hand) {
        if (hand.pos() < 0) {
            return null;
        }
        int length = (pool[hand.pos()] << 8) | (pool[hand.pos() + 1]);
        byte[] target = new byte[length];
        for (int i = 0; i < length; i++) {
            target[i] = pool[hand.pos() + 2 + i];
        }
        return target;
    }

    /**
     * Remove a String from memory pool.
     * 
     * @param hand
     *            Handle
     */
    public void remove(Handle hand) {
        int len = (pool[hand.pos()] << 8) | (pool[hand.pos() + 1]);

        for (int i = 0; i < len + 2; i++) {
            pool[hand.pos() + i] = 0;
        }
        fbl.add(hand.pos(), len + 2);
    }

    /**
     * Print free blocks list.
     */
    public void dump() {
        fbl.print();
    }
}