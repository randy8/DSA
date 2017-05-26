/**
 * Tree class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class Tree {

    private Node root;
    private LeafNode head;
    private LeafNode tail;

    /**
     * Instantiates the nodes in the tree.
     */
    public Tree() {
        root = null;
        head = new LeafNode();
        tail = new LeafNode();
        head.setNext(tail);
        tail.setPrev(head);
    }

    /**
     * Insert method for 2-3+ tree
     * 
     * @param newPair
     *            to be inserted
     * @return true if item is inserted
     */
    public Node insert(KVPair newPair) {
        if (root == null) {
            root = new LeafNode(newPair, null);
            ((LeafNode) root).setNext(tail);
            ((LeafNode) root).setPrev(head);
            head.setNext((LeafNode) root);
            tail.setPrev((LeafNode) root);
            return root;
        }
        Node temp = root.add(newPair);
        if (temp == null) {
            return null;
        }
        root = temp;
        return root;
    }

    /**
     * Print method for 2-3+ tree
     */
    public void print() {
        if (root == null) {
            System.out.println("Printing 2-3 tree:");
            return;
        }
        int depth = root.getDepth(root);
        int count = depth;
        String str = "Printing 2-3 tree:\n" + root.toString(depth, count);
        System.out.println(str.substring(0, str.length() - 1));
    }

    /**
     * Getter for root node
     * 
     * @return root of tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Returns an array of handles you need for list
     * 
     * @param handle
     *            to be compared to
     * @return a handle array of desired handles
     */
    public Handle[] list(Handle handle) {
        if (root == null || handle == null) {
            return null;
        }
        LeafNode found = root.getToLeaf(handle);
        if (found == null) {
            return null;
        }
        int count = 0;
        Handle[] array = new Handle[10];
        boolean finished = false;
        while (/* found != tail && */!finished) {
            if (found.getFirstPair().key().compareTo(handle) == 0) {
                array[count] = found.getFirstPair().value();
                count++;
                array = reallocate(array, count, false);
            }
            if (found.getSecondPair() != null
                    && found.getSecondPair().key().compareTo(handle) == 0) {
                array[count] = found.getSecondPair().value();
                count++;
                array = reallocate(array, count, false);
            }
            found = found.getNext();
            if (found == tail
                    || found.getFirstPair().key().compareTo(handle) != 0) {
                finished = true;
            }
        }

        array = reallocate(array, count, true);
        return array;
    }

    /**
     * Reallocates a handle array
     * 
     * @param array
     *            needed to be reallocated
     * @param idx
     *            the size array should be
     * @param changeSize
     *            checks to see if array needs to be changed
     * @return an array thats reallocated if needed
     */
    private Handle[] reallocate(Handle[] array, int idx, boolean changeSize) {
        if (changeSize) {
            Handle[] newHandleArray = new Handle[idx];
            System.arraycopy(array, 0,
                    newHandleArray, 0, newHandleArray.length);
            return newHandleArray;
        }
        else if (idx == array.length - 1) {
            Handle[] newArray = new Handle[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        }
        else {
            return array;
        }
    }

    /**
     * Deletes one instance of a KVPair
     * 
     * @param pair
     *            to be deleted
     * @return true if pair was deleted
     */
    public boolean delete(KVPair pair) {
        if (root == null) {
            return false;
        }
        Node temp = root.delete(pair);
        if (temp != null) {
            root = temp;
            if (temp.isEmpty() && temp.getClass().equals(LeafNode.class)) {
                root = null;
            }
            else if (temp.isEmpty()) {
                root = ((InternalNode) temp).getLeftChild();
                temp = null;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the tree contains
     * the handle we are looking for.
     * 
     * @param h
     *            the handle that is being searched
     * @return true the tree contains the handle
     */
    public boolean contains(Handle h) {
        if (root == null) {
            return false;
        }
        Handle[] array = list(h);
        return array != null && array.length != 0;
    }
}