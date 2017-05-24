import java.io.RandomAccessFile;
import static java.lang.Math.toIntExact;

/**
 * The buffer pool class manages the binary file and its contents
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class BufferPool {

	/**
	 * The max size of a buffer
	 */
	private static final int BLOCK_SIZE = 4096;

	/**
	 * The file
	 */
	private RandomAccessFile file;

	/**
	 * The pool of buffer of size numBuffers
	 */
	private Buffer[] buffers;

	/**
	 * Used for mapping between file address and array address
	 */
	private int[] map;

	/**
	 * The number of buffers
	 */
	private int numBuffers;

	/**
	 * The length of the file in bytes
	 */
	private int length;

	/**
	 * How many times the cache was read
	 */
	private int hits;

	/**
	 * Reads from file
	 */
	private int reads;

	/**
	 * Writes to file
	 */
	private int writes;

	/**
	 * Constructor for BufferPool
	 * 
	 * @param file
	 *            is the file to open
	 * @param numBuffers
	 *            is the number of buffers
	 * @throws Exception
	 *             if file doesn't work
	 */
	public BufferPool(String file, int numBuffers) throws Exception {
		this.file = new RandomAccessFile(file, "rw");
		this.length = toIntExact(this.file.length());
		this.map = new int[toIntExact(length) / BLOCK_SIZE];
		for (int i = 0; i < map.length; i++) {
			map[i] = -1;
		}
		this.numBuffers = numBuffers;
		buffers = new Buffer[this.numBuffers];
		for (int i = 0; i < this.numBuffers; i++) {
			buffers[i] = new Buffer(-1, BLOCK_SIZE);
		}
		hits = 0;
		reads = 0;
		writes = 0;
	}

	/**
	 * Detects hit or miss
	 * 
	 * @param cacheIndex
	 *            the index in the buffer pool to check
	 * @param fileIndex
	 *            the index in the file
	 * @throws Exception
	 *             if i/o error
	 */
	public void detectHitOrMiss(int cacheIndex, int fileIndex) throws Exception {
		if (cacheIndex == -1) {
			loadIntoPool(fileIndex);
		} 
		else {
			hits++;
			if (cacheIndex != 0) {
				floatBuffer(fileIndex);
			}
		}
	}

	/**
	 * Buffer to float, used for LRU
	 * 
	 * @param index
	 *            the index being checked
	 */
	private void floatBuffer(int index) {
		int memoryIndex = map[index];
		Buffer buf = buffers[memoryIndex];
		for (int i = memoryIndex; i > 0; i--) {
			buffers[i] = buffers[i - 1];
			map[buffers[i].getFileIndex()] = i;
		}
		buffers[0] = buf;
		map[index] = 0;
	}

	/**
	 * Flushes buffer pool and closes the file
	 * 
	 * @throws Exception
	 *             if i/o error
	 */
	public void flush() throws Exception {
		for (int i = 0; i < numBuffers; i++) {
			this.flushBuffer(i);
		}
		file.close();
	}

	/**
	 * Used for flushing a single buffer
	 * 
	 * @param index
	 *            the index to flush
	 * @throws Exception
	 *             for i/o error
	 */
	public void flushBuffer(int index) throws Exception {
		if (buffers[index].getFileIndex() >= 0) {
			file.seek(buffers[index].getFileIndex() * BLOCK_SIZE);
			file.write(buffers[index].getBuffer());
			writes++;
		}
	}

	/**
	 * Loads buffer into pool
	 * 
	 * @param fileIndex
	 *            the index
	 * @throws Exception
	 *             if i/o error
	 */
	private void loadIntoPool(int fileIndex) throws Exception {
		if (buffers[numBuffers - 1].getDirty()) {
			this.flushBuffer(numBuffers - 1);
		}
		if (buffers[numBuffers - 1].getFileIndex() != -1) {
			map[buffers[numBuffers - 1].getFileIndex()] = -1;
		}

		Buffer buf = buffers[numBuffers - 1];
		for (int i = numBuffers - 1; i > 0; i--) {
			buffers[i] = buffers[i - 1];
			if (buffers[i].getFileIndex() != -1) {
				map[buffers[i].getFileIndex()] = i;
			}
		}

		buffers[0] = buf;
		map[fileIndex] = 0;
		buf.setDirty(false);
		buf.setFileIndex(fileIndex);
		file.seek(fileIndex * BLOCK_SIZE);
		file.read(buffers[0].getBuffer());
		map[fileIndex] = 0;
		reads++;
	}

	/**
	 * Populates array into buffer from sort
	 * 
	 * @param index
	 *            the index
	 * @param array
	 *            the array to populate
	 * @throws Exception
	 *             if i/o error
	 */
	public void readFromBuffer(int index, byte[] array) throws Exception {
		int fileIndex = (index / BLOCK_SIZE);
		int poolIndex = map[fileIndex];

		this.detectHitOrMiss(poolIndex, fileIndex);

		byte[] buf = buffers[0].getBuffer();
		int start = index % BLOCK_SIZE;
		System.arraycopy(buf, start, array, 0, array.length);
	}

	/**
	 * writes to buffer
	 * 
	 * @param index
	 *            the index
	 * @param val
	 *            values to write
	 * @throws Exception
	 *             if i/o error
	 */
	public void writeToBuffer(int index, byte[] val) throws Exception {
		int fileIndex = (index / BLOCK_SIZE);
		int poolIndex = map[fileIndex];

		this.detectHitOrMiss(poolIndex, fileIndex);

		byte[] buf = buffers[0].getBuffer();
		int start = index % BLOCK_SIZE;
		System.arraycopy(val, 0, buf, start, val.length);
		buffers[0].setDirty(true);
	}

	/**
	 * Gets the length
	 * 
	 * @return the length
	 */
	public int length() {
		return length;
	}

	/**
	 * Gets hits
	 * 
	 * @return hits
	 */
	public int hits() {
		return hits;
	}

	/**
	 * Gets the number of reads
	 * 
	 * @return reads
	 */
	public int reads() {
		return reads;
	}

	/**
	 * Gets number of writes
	 * 
	 * @return writes
	 */
	public int writes() {
		return writes;
	}

	/**
	 * Returns the map
	 * 
	 * @return the int[]
	 */
	public int[] getMap() {
		return map;
	}

	/**
	 * Returns the buffers
	 * 
	 * @return the buffers
	 */
	public Buffer[] getBuffers() {
		return buffers;
	}
}