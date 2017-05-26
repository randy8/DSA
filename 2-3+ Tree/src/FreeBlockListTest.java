/**
 * Tests FBL methods.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * 
 *@version 2016.09.27
 */

public class FreeBlockListTest extends student.TestCase {

    private FreeBlockList fbl;

    /**
     * Set up.
     */
    public void setUp() throws Exception {
        this.fbl = new FreeBlockList(100);
    }

    /**
     * Test getSize.
     */
    public void testGetSize() {
        assertEquals(this.fbl.getSize(), 1);
    }

    /**
     * Test Add.
     */
    public void testAdd() {
        this.fbl.add(110, 10);
        this.fbl.add(100, 10);
        assertEquals(this.fbl.getSize(), 1);

        this.fbl.split(0, 120);
        this.fbl.add(120, 4);
        assertEquals(this.fbl.getSize(), 1);

        this.fbl.split(120, 3);
        this.fbl.add(123, 4);
        assertEquals(this.fbl.getSize(), 2);
    }

    /**
     * Test searchBlock.
     */
    public void testSearchBlock() {
        this.fbl.add(110, 10);
        this.fbl.add(130, 15);
        this.fbl.add(150, 50);
        assertEquals(this.fbl.search(10), 110);
        assertEquals(this.fbl.search(12), 130);
        assertEquals(this.fbl.search(35), 150);
    }

    /**
     * Test print.
     */
    public void testPrint() {
        this.fbl.print();
        this.fbl.add(110, 10);
        this.fbl.add(150, 50);
        this.fbl.print();
        assertEquals(this.fbl.getSize(), 3);
    }
    
    /**
     * Test splitBlock.
     */
    public void testSplitBlock() {
        this.fbl.split(0, 20);
        assertEquals(this.fbl.getSize(), 1);
        this.fbl.add(150, 10);
        this.fbl.split(150, 10);
        assertNotNull(fbl);
        assertEquals(this.fbl.getSize(), 1);
    }
}