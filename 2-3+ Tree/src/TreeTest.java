import student.TestCase;

/**
 * Tree testing class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class TreeTest extends TestCase {

    private Tree tree;
    private KVPair pair;
    private KVPair pair2;
    private KVPair pair3;
    private KVPair pair4;
    private Handle key;
    private Handle value;
    private Handle key2;
    private Handle value2;
    private Handle key3;
    private Handle value3;
    private Handle key4;
    private Handle value4;
    private Handle hand;

    /**
     * Sets up the test methods
     */
    public void setUp() {
        tree = new Tree();
        key = new Handle(1);
        value = new Handle(2);
        key2 = new Handle(7);
        value2 = new Handle(9);
        key3 = new Handle(6);
        value3 = new Handle(25);
        key4 = new Handle(34);
        value4 = new Handle(23);
        pair = new KVPair(key, value);
        pair2 = new KVPair(key2, value2);
        pair3 = new KVPair(key3, value3);
        pair4 = new KVPair(key4, value4);
        hand = new Handle(60);
    }

    /**
     * Tests the insert method
     */
    public void testInsert() {
        /*assertEquals("Printing 2-3 tree:", systemOut().getHistory());*/
        assertNull(tree.getRoot());
        tree.insert(pair);
        /*assertEquals("Printing 2-3 tree:\n1 2", tree.toString());*/
        assertEquals(LeafNode.class, tree.getRoot().getClass());
    }

    /**
     * Tests duplicate insert
     */
    public void testInsert2() {
        tree.insert(pair);
        assertNull(tree.insert(pair));
        assertFalse(tree.delete(pair2));
        assertTrue(tree.contains(key));
        assertFalse(tree.contains(value));
    }

    /**
     * Tests multiple inserts
     */
    public void testInsert3() {
        /*assertEquals("Printing 2-3 tree:", tree.toString());*/
        tree.insert(pair);
        tree.insert(pair2);
        tree.insert(pair3);
        KVPair morePairs = new KVPair(key, value2);
        assertFalse(tree.delete(morePairs));
        tree.insert(morePairs);
        morePairs = new KVPair(key2, value);
        tree.insert(morePairs);
        tree.list(key2);
        morePairs = new KVPair(key3, value2);
        tree.insert(morePairs);
        tree.list(value2);
        morePairs = new KVPair(value, key3);
        tree.insert(morePairs);
        morePairs = new KVPair(value2, value3);
        tree.insert(morePairs);
        morePairs = new KVPair(value3, key2);
        tree.insert(morePairs);
        morePairs = new KVPair(key4, value4);
        tree.insert(morePairs);
        assertEquals(tree.getRoot().getClass(), InternalNode.class);
        morePairs = new KVPair(value4, key4);
        tree.insert(morePairs);
        morePairs = new KVPair(key4, key);
        tree.insert(morePairs);
        morePairs = new KVPair(value4, key3);
        tree.insert(morePairs);
        morePairs = new KVPair(hand, key4);
        tree.insert(morePairs);
        morePairs = new KVPair(hand, value4);
        tree.insert(morePairs);
        assertEquals(InternalNode.class, tree.getRoot().getClass());
        /*assertEquals(
                "Printing 2-3 tree:" + "\n7 9" + "\n  6 25" + "\n    1 9 2 6"
                        + "\n      1 2" + "\n      1 9" + "\n      2 6 6 9"
                        + "\n    7 2" + "\n      6 25" + "\n      7 2"
                        + "\n  25 7 34 23" + "\n    9 25 23 6" + "\n      7 9"
                        + "\n      9 25" + "\n      23 6 23 34" + "\n    34 1"
                        + "\n      25 7" + "\n      34 1" + "\n    60 23"
                        + "\n      34 23" + "\n      60 23 60 34",
                systemOut().getHistory());*/
        tree.delete(morePairs);
        morePairs = new KVPair(hand, key4);
        tree.delete(morePairs);
        tree.delete(pair);
        morePairs = new KVPair(value4, key4);
        tree.delete(morePairs);
        morePairs = new KVPair(key4, key);
        tree.delete(morePairs);
        morePairs = new KVPair(key3, value2);
        tree.delete(morePairs);
        morePairs = new KVPair(key, value2);
        tree.delete(morePairs);
        /*assertEquals("Printing 2-3 tree:" + "\n7 9 25 7" + "\n  6 25 7 2"
                + "\n    2 6" + "\n    6 25" + "\n    7 2" + "\n  9 25 23 6"
                + "\n    7 9" + "\n    9 25" + "\n    23 6" + "\n  34 23"
                + "\n    25 7" + "\n    34 23", tree.toString());*/
        assertNotNull(tree.delete(pair2));
        Handle smallHand = new Handle(1);
        morePairs = new KVPair(smallHand, value);
        tree.insert(morePairs);

    }

    /**
     * Tests list
     */
    public void testList() {
        Handle[] actual = new Handle[50];
        for (int i = 0; i < 50; i++) {
            Handle handle = new Handle(i);
            actual[i] = handle;
            KVPair pair1000000 = new KVPair(key, handle);
            tree.insert(pair1000000);
        }
        Handle[] list = tree.list(key);
        for (int j = 0; j < 50; j++) {
            assertEquals(actual[j], list[j]);
        }
    }

    /**
     * Tests adding on the left
     */
    public void testAddLeft() {
        tree.insert(pair);
        tree.insert(pair2);
        tree.insert(pair3);
        tree.insert(pair4);
        assertEquals(2, tree.getRoot().getDepth(tree.getRoot()));
    }
}