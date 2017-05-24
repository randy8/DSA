import student.TestCase;

/**
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class BufferPoolTest extends TestCase {
    
    /**
     * Buffer pool to be tested
     */
    private BufferPool pool;
    
    /**
     * Sets up the private fields
     * 
     * @throws Exception if file error
     */
    public void setUp() throws Exception {
        Quicksort.generateFile("test.bin", "-b", "5");
        pool = new BufferPool("test.bin", 10);
    }

    /**
     * Tests constructor and default field values
     */
    public void testBufferPool() {
        assertNotNull(pool);
        assertEquals(0, pool.hits());
        assertEquals(0, pool.reads());
        assertEquals(0, pool.writes());
        assertEquals(4096 * 5, pool.length());
        assertEquals(5, pool.getMap().length);
        assertEquals(10, pool.getBuffers().length);
    }

    /**
     * Tests writeToBuffer()
     * 
     * @throws Exception if somethign doesn't work
     */
    public void testWriteToBuffer() throws Exception {
        byte[] bytes = {1, 2, 3};
        pool.writeToBuffer(0, bytes);
        assertTrue(pool.getBuffers()[0].getDirty());
    }

    /**
     * Tests flushOneBuffer()
     * 
     * @throws Exception if wrong
     */
    public void testFlushOneBuffer() throws Exception {
        byte[] bytes = {1, 2, 3};
        pool.writeToBuffer(0, bytes);
        pool.flushBuffer(0);
        assertEquals(1, pool.writes());
    }

    /**
     * Tests flush()
     * 
     * @throws Exception in the case of error
     */
    public void testFlush() throws Exception {
        byte[] bytes = {1, 2, 3};
        byte[] bytes2 = {3, 4, 5};
        pool.writeToBuffer(0, bytes);
        pool.writeToBuffer(4096, bytes2);
        pool.flush();
        assertEquals(2, pool.writes());
    }

    /**
     * tests detectHitOrMiss()
     * 
     * @throws Exception in the case of error
     */
    public void testDetectHitOrMiss() throws Exception {
        byte[] bytes = {1, 2, 3};
        pool.writeToBuffer(0, bytes);
        pool.detectHitOrMiss(1, 0);
        assertEquals(1, pool.hits());
        pool.detectHitOrMiss(-1, 0);
        assertEquals(1, pool.hits());
    }

    /**
     * Tests readFromBuffer()
     */
    public void testReadFromBuffer() throws Exception {
        byte[] bytes = {1, 2, 3};
        pool.writeToBuffer(0, bytes);
        byte[] test = new byte[3];
        pool.readFromBuffer(0, test);
        assertEquals(bytes[0], test[0]);
        assertEquals(bytes[1], test[1]);
        assertEquals(bytes[2], test[2]);
    }

    /**
     * Tests floatBuffer()
     */
    public void testFloatBuffer() throws Exception {
        for (int i = 0; i < 5; i++) {
            byte b = (byte) i;
            byte[] bytes = {b, b, b};
            pool.writeToBuffer(i * 4096, bytes);
        }
        pool.writeToBuffer(2 * 4096, new byte[]{1, 2, 3});
        assertEquals((byte) 1, pool.getBuffers()[0].getBuffer()[0]);
        assertEquals((byte) 2, pool.getBuffers()[0].getBuffer()[1]);
        assertEquals((byte) 3, pool.getBuffers()[0].getBuffer()[2]);

        //LRU
        assertEquals(-1, pool.getBuffers()[9].getFileIndex());
    }

    /**
     * Tests dirtyBits
     * 
     * @throws Exception from io
     */
    public void testDirtyBits() throws Exception {
        pool.detectHitOrMiss(-1, 0);
        assertFalse(pool.getBuffers()[0].getDirty());
        pool.writeToBuffer(0, new byte[]{1, 2, 3});
        assertTrue(pool.getBuffers()[0].getDirty());
    }
}
