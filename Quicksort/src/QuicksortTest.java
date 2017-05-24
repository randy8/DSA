import student.TestCase;

/**
 * Test class for Quicksort (main method class)
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 24, 2016
 */
public class QuicksortTest extends TestCase {
	
    /**
     * Get code coverage of the class declaration.
     */
    public void testQInit() throws Exception {
    	// TODO: Old copy of this test case is broken
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.main(null);
        // assertEquals("Wrong args\n", systemOut().getHistory());
        Quicksort.main(new String[]{"blah", "blah"});
        // assertEquals("Wrong args\nWrong args\n", systemOut().getHistory());
    }

    /**
     * Tests sorting one 4096 byte block
     */
    public void testSortOneBlock() throws Exception {
        Quicksort.generateFile("test2.bin", "-a", "1");
        Quicksort.main(new String[]{"test2.bin", "1", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }

    /**
     * Tests using ten bins with ten buffers
     */
    public void testTenBinaryTenBuffers() throws Exception {
        Quicksort.generateFile("test2.bin", "-b", "10");
        Quicksort.main(new String[]{"test2.bin", "10", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }

    /**
     * Tests using ten bins with four buffers
     */
    public void testTenBinaryFourBuffers() throws Exception {
        Quicksort.generateFile("test2.bin", "-b", "10");
        Quicksort.main(new String[]{"test2.bin", "4", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }

    /**
     * Tests using ten blocks with one buffer
     */
    public void testTenBlocksOneBuffer() throws Exception {
        Quicksort.generateFile("test2.bin", "-b", "10");
        Quicksort.main(new String[]{"test2.bin", "1", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }

    /**
     * Tests using one hundred blocks with ten buffers
     */
    public void testOneHundredBlocksTenBuffers() throws Exception {
        Quicksort.generateFile("test2.bin", "-b", "100");
        Quicksort.main(new String[]{"test2.bin", "10", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }

    /**
     * Tests using one thousand blocks with ten buffers
     */
    public void testThousandBlocksTenBuffers() throws Exception {
        Quicksort.generateFile("test2.bin", "-b", "1000");
        Quicksort.main(new String[]{"test2.bin", "10", "file.txt"});
        CheckFile checkFile = new CheckFile();
        assertTrue(checkFile.checkFile("test2.bin"));
    }
}