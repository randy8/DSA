/**
 * Tests Hash methods.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * 
 * @version 2016.09.27
 */
public class HashTest extends student.TestCase {

    private Hash table;
    private MemoryManager pool;

    /**
     * Initialize objects.
     */
    public void setUp() throws Exception {
        this.pool = new MemoryManager(32);
        this.table = new Hash("Test", 10, this.pool);
    }

    /**
     * Test getSize method.
     */
    public void testGetSize() {
        assertEquals(table.getSize(), 0);
    }

    /**
     * Test getCapacity. method
     */
    public void testGetCapacity() {
        assertEquals(table.getCapacity(), 10);
    }

    /**
     * Test insert method.
     */
    public void testExpand() {
        Handle h = new Handle(0);
        for (int i = 0; i < 6; i++) {
            table.insert(h);
        }
        assertEquals(20, table.getCapacity());
    }

    /**
     * Test remove method.
     */
    public void testRemove() {
        Handle h = new Handle(0);
        h = pool.insert("aaaabbbb");
        table.insert(h);
        Handle hh = new Handle(0);
        hh = pool.insert("bbbbaaaa");
        table.insert(hh);

        assertEquals(hh.pos(), table.remove("bbbbaaaa").pos());
    }

    /**
     * Test search method.
     */
    public void testSearch() {
        Handle hand = new Handle(0);
        hand = pool.insert("aaaabbbb");
        table.insert(hand);
        Handle hand2 = new Handle(0);
        hand2 = pool.insert("bbbbaaaa");
        table.insert(hand2);
        assertEquals(hand2.pos(), table.search("bbbbaaaa").pos());
    }

    /**
     * Test check method.
     */
    public void testCheck() {
        Handle hand = new Handle(0);
        hand = pool.insert("aaaabbbb");
        table.insert(hand);
        Handle hand2 = new Handle(0);
        hand2 = pool.insert("bbbbaaaa");
        table.insert(hand2);
        assertFalse(table.rehashNeeded("aaaabbbb"));
        assertTrue(table.rehashNeeded("aaaacccc"));
        assertEquals(table.getCapacity(), 10);
        Handle hand3 = new Handle(0);
        for (int i = 0; i < 4; i++) {
            table.insert(hand3);
        }
        assertEquals(table.getCapacity(), 20);
    }
}