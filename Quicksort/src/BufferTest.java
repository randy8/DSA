import student.TestCase;

/**
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class BufferTest extends TestCase {
    
    /**
     * the buffer object to test
     */
    private Buffer buf;

    /**
     * sets up private fields
     */
    public void setUp() {
        buf = new Buffer(-1, 4096);
    }

    /**
     * tests the buffer constructor
     */
    public void testBuffer() {
        assertNotNull(buf);
    }

    /**
     * tests getFileIndex()
     */
    public void testGetFileIndex() {
        assertEquals(-1, buf.getFileIndex());
    }

    /**
     * tests setFileIndex()
     */
    public void testSetFileIndex() {
        buf.setFileIndex(10);
        assertEquals(10, buf.getFileIndex());
    }

    /**
     * tests getDirty() and setDirty()
     */
    public void testDirty() {
        assertFalse(buf.getDirty());
        buf.setDirty(true);
        assertTrue(buf.getDirty());
    }

    /**
     * tests getBuffer()
     */
    public void testGetBuffer() {
        assertEquals(4096, buf.getBuffer().length);
    }
}
