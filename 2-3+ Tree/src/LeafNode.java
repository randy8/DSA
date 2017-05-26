/**
 * Leaf node class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class LeafNode implements Node {

    private LeafNode next;
    private LeafNode prev;
    private KVPair firstPair;
    private KVPair secondPair;

    /**
     * Default constructor initializes empty nodes.
     */
    public LeafNode() {
        setFirstPair(null);
        setSecondPair(null);
        setNext(null);
        setPrev(null);
    }

    /**
     * Constructor that takes handles as parameters
     * 
     * @param firstPair
     *            of handles
     * @param secondPair
     *            of handles
     */
    public LeafNode(KVPair firstPair, KVPair secondPair) {
        setFirstPair(firstPair);
        setSecondPair(secondPair);
        setNext(null);
        setPrev(null);
    }

    /**
     * Getter for next node
     * 
     * @return gets next node
     */
    public LeafNode getNext() {
        return next;
    }

    /**
     * Sets next node
     * 
     * @param next
     *            node to set
     */
    public void setNext(LeafNode next) {
        this.next = next;
    }

    /**
     * Gets previous leaf node
     * 
     * @return previous leaf node
     */
    public LeafNode getPrev() {
        return prev;
    }

    /**
     * Sets previous node to parameter
     * 
     * @param prev
     *            node that changes prev
     */
    public void setPrev(LeafNode prev) {
        this.prev = prev;
    }

    /**
     * Gets the first leafNode that contains handle as its key
     * 
     * @param handle
     *            the key that we are looking for
     * @return the first LEafNode that contains the handle otherwise returns
     *         null
     */
    public LeafNode getToLeaf(Handle handle) {
        if (!isFull()) {
            if (handle.compareTo(getFirstPair().key()) == 0) {
                return this;
            }
        }
        else {
            if (handle.compareTo(getFirstPair().key()) == 0
                    || handle.compareTo(getSecondPair().key()) == 0) {
                return this;
            }
        }
        return getToHelp(next, handle);
    }

    /**
     * Gets the next instance of the node needed to be found
     * 
     * @param node
     *            current node in tree
     * @param handle
     *            to be found
     * @return true if node is found
     */
    private LeafNode getToHelp(LeafNode node, Handle handle) {
        if (node == null || node.isEmpty() || handle == null) {
            return null;
        }
        else {
            if (node.getFirstPair().key().compareTo(handle) == 0
                    || (node.getSecondPair() != null && node.getSecondPair()
                            .key().compareTo(handle) == 0)) {
                return node;
            }
        }
        return node.getToHelp(node.getNext(), handle);
    }

    /**
     * Gets depth of node
     * 
     * @param node
     *            to get the depth from
     * @return the depth of tree
     */
    public int getDepth(Node node) {
        return 1;
    }

    /**
     * Return first pair o KVPairs
     * 
     * @return first pair of handles
     */
    public KVPair getFirstPair() {
        return firstPair;
    }

    /**
     * Sets the first pair to newLeft
     * 
     * @param newLeft
     *            pair that sets original
     */
    public void setFirstPair(KVPair newLeft) {
        firstPair = newLeft;
    }

    /**
     * Returns the second KVPair
     * 
     * @return second pair
     */
    public KVPair getSecondPair() {
        return secondPair;
    }

    /**
     * Sets the second KVPair in node
     * 
     * @param newRight
     *            for the second pair
     */
    public void setSecondPair(KVPair newRight) {
        secondPair = newRight;
    }

    /**
     * Get least method for nodes
     * 
     * @return the KVPair of the least node needed for the updata
     */
    public KVPair getLeast() {
        return getFirstPair();
    }

    /**
     * Add method for leaf node
     * 
     * @param pair
     *            to be added
     * @return the current node that you inserted pair into
     */
    public Node add(KVPair pair) {
        if (!this.isFull()) {
            if (pair.compareTo(getFirstPair()) == 0) {
                return null;
            }
            this.insert(pair);
            return this;
        }
        else {
            InternalNode newNode = new InternalNode();
            LeafNode sibling = new LeafNode();
            if (pair.compareTo(getFirstPair()) == 0
                    || pair.compareTo(getSecondPair()) == 0) {
                return null;
            }
            else if (pair.compareTo(getFirstPair()) < 0) {
                sibling.setFirstPair(pair);
                sibling.setPrev(this.prev);
                sibling.setNext(this);
                this.getPrev().setNext(sibling);
                this.setPrev(sibling);
                newNode.setFirstPair(this.getFirstPair());
                newNode.setLeftChild(sibling);
                newNode.setMiddleChild(this);
                return (Node) newNode;
            }
            else if (pair.compareTo(getSecondPair()) < 0) {
                sibling.setFirstPair(pair);
                sibling.setSecondPair(this.getSecondPair());
                this.setSecondPair(null);
                sibling.setPrev(this);
                sibling.setNext(this.next);
                this.next.setPrev(sibling);
                this.setNext(sibling);
                newNode.setFirstPair(sibling.getFirstPair());
                newNode.setLeftChild(this);
                newNode.setMiddleChild(sibling);
                return (Node) newNode;
            }
            else {
                sibling.setSecondPair(pair);
                sibling.setFirstPair(this.getSecondPair());
                this.setSecondPair(null);
                sibling.setPrev(this);
                sibling.setNext(this.getNext());
                this.getNext().setPrev(sibling);
                this.setNext(sibling);
                newNode.setFirstPair(sibling.getFirstPair());
                newNode.setLeftChild(this);
                newNode.setMiddleChild(sibling);
                return (Node) newNode;
            }
        }
    }

    /**
     * Strings together the leaf nodes
     * 
     * @param depth
     *            of 2-3 tree
     * @param curr
     *            of the current depth
     * @return string representation of leaf node
     */
    public String toString(int depth, int curr) {
        int ind = (depth - curr) * 2;
        String space = indent(ind);
        String res = space + this.getFirstPair().toString();
        if (this.getSecondPair() != null) {
            res += " " + this.getSecondPair().toString();
        }
        res += "\n";
        return res;
    }

    /**
     * Helper method for toString
     * 
     * @param numIndent
     *            number of times indentation is needed
     * @return an array of spaces that allow for indents
     */
    private String indent(int numIndent) {
        char[] space = new char[numIndent];
        for (int i = 0; i < numIndent; i++) {
            space[i] = ' ';
        }
        String ind = new String(space);
        return ind;
    }

    /**
     * Determines if the node is full.
     * 
     * @return true if it is a full node
     */
    public boolean isFull() {
        return firstPair != null && secondPair != null;
    }

    /**
     * Only the first node is occupied.
     * 
     * @return true if only first pair is used
     */
    public boolean onlyFirstNode() {
        return firstPair != null && secondPair == null;
    }

    /**
     * Check first and second pairs to see where to insert to see where you can
     * insert
     * 
     * @param pair
     *            to be inserted
     */
    public void insert(KVPair pair) {
        if (isEmpty()) {
            setFirstPair(pair);
        }
        else if (onlyFirstNode()) {
            setSecondPair(pair);
        }
        else if (firstPair == null && secondPair != null) {
            setFirstPair(pair);
        }
        if (isFull()) {
            swap();
        }
    }

    /**
     * Shifts full nodes accordingly.
     */
    public void swap() {
        KVPair temp = secondPair;
        if (secondPair.compareTo(firstPair) < 0) {
            temp = secondPair;
            secondPair = firstPair;
            firstPair = temp;
        }
    }

    /**
     * Delete method for LeafNode
     * 
     * @param pair
     *            to be deleted
     * @return the leaf node that should be deleted
     */
    public Node delete(KVPair pair) {
        if (pair.key() == null || pair.value() == null) {
            return null;
        }
        // leaf node is full
        if (isFull()) {
            if (getFirstPair().compareTo(pair) == 0) {
                this.setFirstPair(null);
                this.setFirstPair(this.getSecondPair());
                this.setSecondPair(null);
                return this;
            }
            else if (getSecondPair().compareTo(pair) == 0) {
                this.setSecondPair(null);
                return this;
            }
        }
        // leaf node not full
        else {
            if (getFirstPair().compareTo(pair) == 0) {
                this.setFirstPair(null);
                this.getPrev().setNext(next);
                this.getNext().setPrev(prev);
                this.setNext(null);
                this.setPrev(null);
                return this;
            }
        }
        return null;
    }

    /**
     * Checks to see if both pairs in node is empty
     * 
     * @return true if node is empty
     */
    public boolean isEmpty() {
        return firstPair == null && secondPair == null;
    }
}