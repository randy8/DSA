import java.util.Arrays;

/**
 * Test MemoryManager
 * 
 * @author Randy Liang (randy8)
 * @version October 3, 2016
 */
public class MemoryManagerTest extends student.TestCase {

    private MemoryManager pool;

    /**
     * Set up.
     * 
     * @throws Exception
     */
    public void setUp() throws Exception {
        this.pool = new MemoryManager(32);
    }

    /**
     * Tests insert.
     */
    public void testInsert() {
        assertEquals(this.pool.insert("a").pos(), 0);
        for (int i = 0; i < 100; i++) {
            this.pool.insert("" + i);
        }
    }

    /**
     * Tests get.
     */
    public void testGet() {
        String name = "a";
        assertEquals(this.pool.insert(name).pos(), 0);
        Handle h = new Handle(0);
        byte[] target = name.getBytes();
        assertTrue(Arrays.equals(this.pool.get(h), target));
        Handle hand = new Handle(-1);
        assertNull(this.pool.get(hand));
    }

    /**
     * Tests remove.
     */
    public void testRemove() {
        assertEquals(pool.insert("a").pos(), 0);
        pool.remove(new Handle(0));
    }
}