import student.TestCase;

/**
 * Leaf node test class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class LeafNodeTest extends TestCase {

    private LeafNode node;
    private KVPair pair;
    private KVPair pair2;
    private KVPair pair3;

    /**
     * Sets up every test case
     */
    public void setUp() {
        Handle hand = new Handle(1);
        Handle key = new Handle(3);
        Handle value = new Handle(4);
        pair = new KVPair(key, value);
        pair2 = new KVPair(value, key);
        node = new LeafNode();
        pair3 = new KVPair(hand, value);
    }

    /**
     * Tests is empty
     */
    public void testIsEmpty() {
        assertTrue(node.isEmpty());
        node.insert(pair);
        assertFalse(node.isEmpty());
    }

    /**
     * Tests methods in LeafNode
     */
    public void testOtherMethods() {
        assertFalse(node.onlyFirstNode());
        assertFalse(node.isFull());
        node.insert(pair2);
        assertTrue(node.onlyFirstNode());
        node.insert(pair);
        assertTrue(node.isFull());
        node.setFirstPair(null);
        node.insert(pair);
        assertEquals(pair, node.getFirstPair());
        node.insert(pair3);
        assertEquals(pair, node.getFirstPair());
    }
}