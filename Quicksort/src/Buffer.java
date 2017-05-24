/**
 * the buffer class loads the next set of bytes from the file
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class Buffer {
    
    /**
     * The buffer
     */
    private byte[] bytes; 
    
    /**
     * If this buffer is dirty
     */
    private boolean dirty; 
    
    /**
     * Block number
     */
    private int fileIndex; 

    /**
     * constructor for buffer class
     * @param fileIndex is the index of the file
     * @param bufferSize is the size of the buffer
     */
    public Buffer(int fileIndex, int bufferSize) {
        this.bytes = new byte[bufferSize];
        this.dirty = false;
        this.fileIndex = fileIndex;
    }

    /**
     * gets the file index
     * @return index
     */
    public int getFileIndex() {
        return fileIndex;
    }

    /**
     * sets file index
     * @param fileIndex index to set to
     */
    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    /**
     * checks if dirty
     * @return true or false
     */
    public boolean getDirty() {
        return this.dirty;
    }

    /**

     * sets dirty
     * @param dirty true or false
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * gets the buffer byte array
     * @return the byte array
     */
    public byte[] getBuffer() {
        return bytes;
    }
}
