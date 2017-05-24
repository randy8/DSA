/**
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class Sort {
    
    /**
     * Used for sorting
     */
    private byte[] buf;
    
    /**
     * Pool to be sorted
     */
    private BufferPool pool;
    
    /**
     * Number of sets in the pool ([total bytes]/4)
     */
    private int recLen;
    
    /**
     * Start time of sort
     */
    private long startTime;
    
    /**
     * Finish time of sort
     */
    private long finishTime;

    /**
     * Constructor for sort
     * @param pool the bufferpool
     */
    public Sort(BufferPool pool) {
        this.buf = new byte[4];
        this.pool = pool;
        this.recLen = this.pool.length() / 4;
    }

    /**
     * Swaps index i and j
     * @param i the first index
     * @param j the second index
     * @throws Exception when IOException
     */
    public void swap(int i, int j) throws Exception {
        byte[] swap = new byte[4];
        pool.readFromBuffer(i * 4, buf);
        pool.readFromBuffer(j * 4, swap);
        pool.writeToBuffer(i * 4, swap);
        pool.writeToBuffer(j * 4, buf);
    }


    /**
     * Gets a records key
     * @param i the index
     * @return the key as a short
     * @throws Exception IO Exception
     */
    public short recordKey(int i) throws Exception {
        pool.readFromBuffer(i * 4, buf);
        return (short) (((short) (buf[0] & 0xFF) << 8)
                + ((short) buf[1] & 0xFF));
    }

    /**
     * The recursive sort method
     * @param low the low index
     * @param high the high index
     * @throws Exception IO Exception
     */
    private void sort(int low, int high) throws Exception {
        short pivot = this.recordKey(low + (high - low) / 2);

        int i = low;
        int j = high;

        while (i <= j) {
            while (this.recordKey(i) < pivot) {
                i++;
            }
            while (this.recordKey(j) > pivot) {
                j--;
            }
            if (i <= j) {
                this.swap(i, j);
                i++;
                j--;
            }
        }

        if (low < j) {
            this.sort(low, j);
        }

        if (i < high) {
            this.sort(i, high);
        }
    }

    /**
     * Gets the runtime of just the sort
     * @return the runtime
     */
    public long getRunTime() {
        return this.finishTime - this.startTime;
    }

    /**
     * The external sorting method
     * @throws Exception IO Exception
     */
    public void sort() throws Exception {
        this.startTime = System.currentTimeMillis();
        this.sort(0, recLen - 1);
        this.finishTime = System.currentTimeMillis();
    }
}
