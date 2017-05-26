import student.TestCase;

/**
 * Internal node test class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class InternalNodeTest extends TestCase {

    private InternalNode node;
    private InternalNode emptyNode;
    private KVPair first;
    private KVPair second;
    private Node child;

    /**
     * Sets up every test case
     */
    public void setUp() {
        Handle handle = new Handle(1);
        Handle handle2 = new Handle(2);
        Handle handle3 = new Handle(3);
        Handle handle4 = new Handle(4);
        first = new KVPair(handle, handle2);
        second = new KVPair(handle3, handle4);
        node = new InternalNode(first, second);
        emptyNode = new InternalNode();
    }

    /**
     * Tests all the methods in internal nodes
     */
    public void testStuff() {
        assertEquals(first, node.getFirstPair());
        assertEquals(second, node.getSecondPair());
        node.setLeftChild(child);
        assertEquals(child, node.getLeftChild());
        node.setRightChild(child);
        assertEquals(child, node.getRightChild());
        node.setMiddleChild(child);
        assertEquals(child, node.getMiddleChild());
        assertNull(emptyNode.getFirstPair());
        assertNull(emptyNode.getSecondPair());
        assertFalse(emptyNode.isFull());
        assertFalse(emptyNode.onlyFirstNode());
        emptyNode.insert(second);
        assertTrue(emptyNode.onlyFirstNode());
        emptyNode.insert(first);
        assertTrue(emptyNode.isFull());
        emptyNode.setFirstPair(null);
        emptyNode.insert(first);
        assertEquals(first, emptyNode.getFirstPair());
    }
    
    /**
     * Tests getDepth
     */
    public void testGetDepth() {
        assertEquals(0, node.getDepth(null));
    }
}