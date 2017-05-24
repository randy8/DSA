import student.TestCase;

/**
 * Tests the sort class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class SortTest extends TestCase {
    
    /**
     * Sort object to be tested
     */
    private Sort sort;
    
    /**
     * Sets up private fields
     */
    public void setUp() throws Exception {
        sort = new Sort(new BufferPool("test2.bin", 1));
    }

    /**
     * Tests Sort constructor
     */
    public void testSort() throws Exception {
        assertNotNull(sort);
    }

    /**
     * Tests swap()
     */
    public void testSwap() throws Exception {
        short i = sort.recordKey(10);
        short j = sort.recordKey(20);
        sort.swap(i, j);
        assertEquals(i, sort.recordKey(20));
        assertEquals(j, sort.recordKey(10));
    }

    /**
     * Tests recordKey()
     */
    public void testRecordKey() {
        try {
            assertNotNull(sort.recordKey(0));
            assertNotNull(sort.recordKey(4095));
            sort.recordKey(-1);
        }
        catch (Exception e) {
            assertNotNull(e);
        }
    }

    /**
     * Tests sorting method (recursive)
     */
    public void testMethodSort() throws Exception {
        sort.sort();
        CheckFile file = new CheckFile();
        assertTrue(file.checkFile("test2.bin"));
    }

    /**
     * Tests getRunTime()
     */
    public void testGetRunTime() throws Exception {
        sort.sort();
        assertTrue(2000 >= sort.getRunTime());
    }
}
