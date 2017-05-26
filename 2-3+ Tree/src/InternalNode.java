/**
 * Internal node class
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */
public class InternalNode implements Node {

    private Node leftNode;
    private Node middleNode;
    private Node rightNode;
    private KVPair firstPair;
    private KVPair secondPair;

    /**
     * Empty Constructor
     */
    public InternalNode() {
        setFirstPair(null);
        setSecondPair(null);
        leftNode = null;
        middleNode = null;
        rightNode = null;
    }

    /**
     * Constructor that takes in KVPair handles.
     * 
     * @param firstPair
     *            of handles
     * @param secondPair
     *            of handles
     */
    public InternalNode(KVPair firstPair, KVPair secondPair) {
        setFirstPair(firstPair);
        setSecondPair(secondPair);
        leftNode = null;
        middleNode = null;
        rightNode = null;
    }

    /**
     * Returns next node
     * 
     * @return next node
     */
    public Node getRightChild() {
        return rightNode;
    }

    /**
     * Returns the first pair of KVPairs
     * 
     * @return first pair of handles
     */
    public KVPair getFirstPair() {
        return firstPair;
    }

    /**
     * Sets next node
     * 
     * @param newRightNode
     *            sets previous node
     */
    public void setRightChild(Node newRightNode) {
        rightNode = newRightNode;
    }

    /**
     * Gets previous node
     * 
     * @return previous node
     */
    public Node getLeftChild() {
        return leftNode;
    }

    /**
     * Sets the previous node
     * 
     * @param newLeftNode
     *            node that sets the previous node
     */
    public void setLeftChild(Node newLeftNode) {
        leftNode = newLeftNode;
    }

    /**
     * Getter for middleNode node
     * 
     * @return middleNode node
     */
    public Node getMiddleChild() {
        return middleNode;
    }

    /**
     * Sets the middleNode node
     * 
     * @param newMiddleNode
     *            node to be set
     */
    public void setMiddleChild(Node newMiddleNode) {
        middleNode = newMiddleNode;
    }

    /**
     * Add method for internal nodes
     * 
     * @param pair
     *            to be inserted
     * @return current node where pair was inserted
     */
    public Node add(KVPair pair) {
        Node check = new InternalNode();
        // KVPair testPair;
        if (!isFull()) {
            if (pair.compareTo(getFirstPair()) == 0) {
                return null;
            }
            if (pair.compareTo(getFirstPair()) < 0) {
                check = this.leftNode.add(pair);
                if (check == null) {
                    return null;
                }
                if (check.isFull() || (this.leftNode.getFirstPair()
                        .compareTo(check.getFirstPair()) == 0)) {
                    return this;
                }
                if (!check.getClass().equals(LeafNode.class)) {
                    this.insert(check.getFirstPair());
                    this.setRightChild(middleNode);
                    this.setMiddleChild(
                            ((InternalNode) check).getMiddleChild());
                    this.setLeftChild(((InternalNode) check).getLeftChild());
                    check = null;
                    return this;
                }
                return this;
            }
            else {
                check = this.middleNode.add(pair);
                if (check == null) {
                    return null;
                }
                if (check.isFull() || (this.middleNode.getFirstPair()
                        .compareTo(check.getFirstPair()) == 0)) {
                    return this;
                }
                if (!check.getClass().equals(LeafNode.class)) {
                    this.insert(check.getFirstPair());
                    this.setMiddleChild(((InternalNode) check).getLeftChild());
                    this.setRightChild(((InternalNode) check).getMiddleChild());
                    check = null;
                    return this;
                }
                return this;
            }
        }
        else {
            if (pair.compareTo(getFirstPair()) == 0
                    || pair.compareTo(getSecondPair()) == 0) {
                return null;
            }
            if (pair.compareTo(getFirstPair()) < 0) {
                check = this.leftNode.add(pair);
                if (check == null) {
                    return null;
                }
                if (check.isFull() || (this.leftNode.getFirstPair()
                        .compareTo(check.getFirstPair()) == 0)) {
                    return this;
                }
                if (!check.getClass().equals(LeafNode.class)) {
                    InternalNode intNode = new InternalNode(this.getFirstPair(),
                            null);
                    this.setFirstPair(this.getSecondPair());
                    this.setSecondPair(null);
                    this.setLeftChild(this.middleNode);
                    this.setMiddleChild(this.rightNode);
                    intNode.setLeftChild(check);
                    intNode.setMiddleChild(this);
                    this.setRightChild(null);
                    return intNode;
                }
                return this;
            }
            else if (pair.compareTo(getSecondPair()) < 0) {
                check = this.middleNode.add(pair);
                if (check == null) {
                    return null;
                }
                if (check.isFull() || (this.middleNode.getFirstPair()
                        .compareTo(check.getFirstPair()) == 0)) {
                    return this;
                }
                if (!check.getClass().equals(LeafNode.class)) {
                    InternalNode intNode = new InternalNode(
                            this.getSecondPair(), null);
                    this.setSecondPair(null);
                    intNode.setMiddleChild(this.rightNode);
                    this.setRightChild(null);
                    intNode.setLeftChild(
                            ((InternalNode) check).getMiddleChild());
                    this.setMiddleChild(((InternalNode) check).getLeftChild());
                    ((InternalNode) check).setLeftChild(this);
                    ((InternalNode) check).setMiddleChild(intNode);
                    return check;
                }
                return this;
            }
            else {
                check = this.rightNode.add(pair);
                if (check == null) {
                    return null;
                }
                if (check.isFull() || (this.rightNode.getFirstPair()
                        .compareTo(check.getFirstPair()) == 0)) {
                    return this;
                }
                if (!check.getClass().equals(LeafNode.class)) {
                    InternalNode intNode = new InternalNode(
                            this.getSecondPair(), null);
                    this.setSecondPair(null);
                    intNode.setLeftChild(this);
                    intNode.setMiddleChild(check);
                    this.setRightChild(null);
                    return intNode;
                }
                return this;
            }
        }
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
     * Strings together the internal nodes
     * 
     * @param depth
     *            of 2-3 tree
     * @param count
     *            of the current depth
     * @return string representation of internal node
     */
    public String toString(int depth, int count) {
        int i = (depth - count) * 2;
        String indentSpace = indent(i);
        String res = indentSpace + this.getFirstPair().toString();
        if (this.getSecondPair() != null) {
            res += " " + this.getSecondPair().toString();
        }

        res += "\n" + this.getLeftChild().toString(depth, count - 1)
                + this.getMiddleChild().toString(depth, count - 1);
        if (this.getRightChild() != null) {
            res += this.getRightChild().toString(depth, count - 1);
        }
        return res;
    }

    /**
     * Gets depth of node
     * 
     * @param node
     *            to get the depth from
     * @return the depth of tree
     */
    public int getDepth(Node node) {
        if (node == null) {
            return 0;
        }
        if (!node.getClass().equals(LeafNode.class)) {
            return 1 + getDepth(((InternalNode) node).getLeftChild());
        }
        else {
            return 1;
        }
    }

    /**
     * Sets the first pair to newleftNode
     * 
     * @param newleftNode
     *            pair that sets original
     */
    public void setFirstPair(KVPair newleftNode) {
        firstPair = newleftNode;
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
     * @param newrightNode
     *            for the second pair
     */
    public void setSecondPair(KVPair newrightNode) {
        secondPair = newrightNode;
    }

    /**
     * Checks if node is full or not
     * 
     * @return true if node is full
     */
    public boolean isFull() {
        return firstPair != null && secondPair != null;
    }

    /**
     * True if only first pair is occupied
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
        if (firstPair == null && secondPair == null) {
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
     * Swap method for full nodes.
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
     * Gets the first leafNode that contains handle as its key
     * 
     * @param handle
     *            the key that we are looking for
     * @return the first LaafNode that contains the handle otherwise returns
     *         null
     */
    public LeafNode getToLeaf(Handle handle) {
        LeafNode check;
        if (!isFull()) {
            if (handle.compareTo(getFirstPair().key()) <= 0) {
                check = leftNode.getToLeaf(handle);
                if (check == null) {
                    return null;
                }
                return check;
            }
            else {
                check = middleNode.getToLeaf(handle);
                if (check == null) {
                    return null;
                }
                return check;
            }
        }
        else {
            if (handle.compareTo(getFirstPair().key()) <= 0) {
                check = leftNode.getToLeaf(handle);
                if (check == null) {
                    return null;
                }
                return check;
            }
            else if (handle.compareTo(getSecondPair().key()) <= 0) {
                check = middleNode.getToLeaf(handle);
                if (check == null) {
                    return null;
                }
                return check;
            }
            else {
                check = rightNode.getToLeaf(handle);
                if (check == null) {
                    return null;
                }
                return check;
            }
        }
    }

    /**
     * Delete method for internal node
     * 
     * @param pair
     *            to be deleted
     * @return updated tree after deletion
     */
    public Node delete(KVPair pair) {
        Node check;
        // if internal node isn't full
        if (onlyFirstNode()) {
            if (pair.compareTo(getFirstPair()) < 0) {
                check = this.leftNode.delete(pair);
                // if pair wasn't in the tree
                if (check == null) {
                    return null;
                }
                // if check is a leaf node
                if (check.getClass().equals(LeafNode.class)) {
                    if (check.isEmpty() && (this.getMiddleChild().isFull())) {
                        check.setFirstPair(this.middleNode.getFirstPair());
                        this.middleNode
                                .setFirstPair(this.middleNode.getSecondPair());
                        this.middleNode.setSecondPair(null);
                        this.setFirstPair(middleNode.getLeast());
                        ((LeafNode) middleNode).getPrev()
                                .setNext((LeafNode) check);
                        ((LeafNode) check)
                                .setPrev(((LeafNode) middleNode).getPrev());
                        ((LeafNode) middleNode).setPrev((LeafNode) check);
                        ((LeafNode) check).setNext((LeafNode) middleNode);
                        return this;
                    }
                    // if leaf node is empty
                    else if (check.isEmpty()) {
                        this.setLeftChild(middleNode);
                        this.setMiddleChild(null);
                        this.setFirstPair(null);
                        return this;
                    }
                    return this;
                }
                else {
                    // if internal node and is not full
                    if (check.isEmpty()) { 
                        if (!middleNode.isFull()) {
                            middleNode.setSecondPair(middleNode.getFirstPair());
                            middleNode.setFirstPair(this.getFirstPair());
                            this.setFirstPair(null);
                            ((InternalNode) middleNode)
                                    .setRightChild(((InternalNode) middleNode)
                                            .getMiddleChild());
                            ((InternalNode) middleNode).setMiddleChild(
                                    ((InternalNode) middleNode).getLeftChild());
                            ((InternalNode) middleNode).setLeftChild(
                                    ((InternalNode) check).getLeftChild());
                            this.setLeftChild(middleNode);
                            this.setMiddleChild(null);
                            check = null;
                            return this;
                        }
                        else {

                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) middleNode).getLeftChild());
                            ((InternalNode) middleNode)
                                    .setLeftChild(((InternalNode) middleNode)
                                            .getMiddleChild());
                            ((InternalNode) middleNode)
                                    .setMiddleChild(((InternalNode) middleNode)
                                            .getRightChild());
                            ((InternalNode) middleNode).setRightChild(null);
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            middleNode.setFirstPair(middleNode.getSecondPair());
                            middleNode.setSecondPair(null);
                            this.setFirstPair(middleNode.getLeast());

                            return this;
                        }
                    }
                    else {
                        this.setFirstPair(middleNode.getLeast());
                        return this;
                    }
                }
            }
            else if (pair.compareTo(getFirstPair()) >= 0) {
                check = this.middleNode.delete(pair);
                // if pair not in the tree
                if (check == null) {
                    return null;
                }
                // if check is a leaf node
                if (check.getClass().equals(LeafNode.class)) {
                    if (check.isEmpty() && (this.getLeftChild().isFull())) {
                        check.setFirstPair(this.getLeftChild().getSecondPair());
                        this.getLeftChild().setSecondPair(null);
                        ((LeafNode) leftNode).getNext()
                                .setPrev((LeafNode) check);
                        ((LeafNode) check)
                                .setNext(((LeafNode) leftNode).getNext());

                        ((LeafNode) leftNode).setNext((LeafNode) check);
                        ((LeafNode) check).setPrev((LeafNode) leftNode);
                        this.setFirstPair(middleNode.getLeast());
                        return this;
                    }
                    // if leaf node is empty
                    else if (check.isEmpty()) {
                        this.setMiddleChild(null);
                        this.setFirstPair(null);
                        return this;
                    }
                    this.setFirstPair(middleNode.getLeast());
                    return this;
                }
                else {
                    // if internal node and is not full
                    if (check.isEmpty()) {
                        if (!this.getLeftChild().isFull()) {
                            ((InternalNode) getLeftChild()).setRightChild(
                                    ((InternalNode) check).getLeftChild());
                            this.getLeftChild()
                                    .setSecondPair(((InternalNode) leftNode)
                                            .getRightChild().getLeast());
                            this.setMiddleChild(null);
                            this.setFirstPair(null);
                            check = null;
                            return this;
                        }
                        else {
                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) check).getLeftChild());
                            ((InternalNode) check).setLeftChild(
                                    ((InternalNode) leftNode).getRightChild());
                            ((InternalNode) leftNode).setRightChild(null);
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            leftNode.setSecondPair(null);
                            this.setFirstPair(middleNode.getLeast());
                        }
                    }
                    else {
                        this.setFirstPair(middleNode.getLeast());
                        return this;
                    }
                }
            }
        }
        // if internal node is full
        else {
            // leftNode case if this is full
            if (pair.compareTo(getFirstPair()) < 0) {
                check = this.leftNode.delete(pair);
                // if pair wasn't in the tree
                if (check == null) {
                    return null;
                }
                // if check is a leaf node
                if (check.getClass().equals(LeafNode.class)) {
                    if (check.isEmpty() && (this.getMiddleChild().isFull())) {
                        check.setFirstPair(this.middleNode.getFirstPair());
                        this.middleNode
                                .setFirstPair(this.middleNode.getSecondPair());
                        this.middleNode.setSecondPair(null);
                        this.setFirstPair(middleNode.getLeast());
                        ((LeafNode) middleNode).getPrev()
                                .setNext((LeafNode) check);
                        ((LeafNode) check)
                                .setPrev(((LeafNode) middleNode).getPrev());
                        ((LeafNode) middleNode).setPrev((LeafNode) check);
                        ((LeafNode) check).setNext((LeafNode) middleNode);
                        return this;
                    }
                    // if leaf node is empty
                    else if (check.isEmpty()) {
                        this.setFirstPair(secondPair);
                        this.setSecondPair(null);
                        this.setLeftChild(middleNode);
                        this.setMiddleChild(rightNode);
                        this.setRightChild(null);
                        return this;
                    }
                    this.setFirstPair(middleNode.getLeast());
                    return this;
                }
                else {
                    // if internal node and full
                    if (check.isEmpty()) {
                        // middleNode child not full
                        if (!getMiddleChild().isFull()) {
                            middleNode.setSecondPair(middleNode.getFirstPair());
                            middleNode.setFirstPair(this.getFirstPair());
                            this.setSecondPair(null);
                            ((InternalNode) middleNode)
                                    .setRightChild(((InternalNode) middleNode)
                                            .getMiddleChild());
                            ((InternalNode) middleNode).setMiddleChild(
                                    ((InternalNode) middleNode).getLeftChild());
                            ((InternalNode) middleNode).setLeftChild(
                                    ((InternalNode) check).getLeftChild());
                            this.setLeftChild(middleNode);
                            this.setMiddleChild(rightNode);
                            this.setRightChild(null);
                            this.setFirstPair(middleNode.getLeast());
                            return this;
                        }
                        else {
                            // middleNode child full
                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) this.getMiddleChild())
                                            .getLeftChild());
                            middleNode.setFirstPair(middleNode.getSecondPair());
                            middleNode.setSecondPair(null);
                            ((InternalNode) middleNode)
                                    .setLeftChild(((InternalNode) middleNode)
                                            .getMiddleChild());
                            ((InternalNode) middleNode)
                                    .setMiddleChild(((InternalNode) middleNode)
                                            .getRightChild());
                            ((InternalNode) middleNode).setRightChild(null);
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            this.setFirstPair(middleNode.getLeast());
                            return this;
                        }
                    }
                }
                this.setFirstPair(middleNode.getLeast());
                return this;
            }
            // middleNode case if this is full
            if (pair.compareTo(getSecondPair()) < 0
                    && pair.compareTo(getFirstPair()) >= 0) {
                check = this.middleNode.delete(pair);
                if (check == null) {
                    return null;
                }
                // if check is a leaf node
                if (check.getClass().equals(LeafNode.class)) {
                    // if the previous leaf node is full
                    if (check.isEmpty() && (this.getLeftChild().isFull())) {
                        check.setFirstPair(this.getLeftChild().getSecondPair());
                        this.getLeftChild().setSecondPair(null);
                        ((LeafNode) leftNode).getNext()
                                .setPrev((LeafNode) check);
                        ((LeafNode) check)
                                .setNext(((LeafNode) leftNode).getNext());
                        ((LeafNode) leftNode).setNext((LeafNode) check);
                        ((LeafNode) check).setPrev((LeafNode) leftNode);
                        this.setFirstPair(middleNode.getLeast());
                        return this;
                    }
                    // if previous leaf node isn't full and check is empty
                    else if (check.isEmpty()) {
                        this.setSecondPair(null);
                        this.setMiddleChild(rightNode);
                        this.setRightChild(null);
                        this.setFirstPair(middleNode.getLeast());
                        return this;
                    }
                    this.setFirstPair(middleNode.getLeast());
                    this.setSecondPair(rightNode.getLeast());

                    return this;
                }
                else {
                    // if check is internal node and this full
                    if (check.isEmpty()) {
                        if (this.getLeftChild().isFull()) {
                            getLeftChild().setSecondPair(null);
                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) check).getLeftChild());
                            ((InternalNode) check).setLeftChild(
                                    ((InternalNode) getLeftChild())
                                            .getRightChild());
                            ((InternalNode) getLeftChild()).setRightChild(null);
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            this.setFirstPair(middleNode.getLeast());
                            return this;
                        }
                        // check if rightNode one is full
                        else if (this.getRightChild().isFull()) {
                            getRightChild().setSecondPair(null);
                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) rightNode).getLeftChild());
                            ((InternalNode) rightNode)
                                    .setLeftChild(((InternalNode) rightNode)
                                            .getMiddleChild());
                            ((InternalNode) rightNode).setMiddleChild(
                                    ((InternalNode) rightNode).getRightChild());
                            ((InternalNode) rightNode).setRightChild(null);
                            rightNode.setFirstPair(((InternalNode) rightNode)
                                    .getMiddleChild().getLeast());
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            this.setFirstPair(middleNode.getLeast());
                            this.setSecondPair(rightNode.getLeast());
                            return this;
                        }
                        // leftNode child isn't full
                        else {
                            this.setSecondPair(null);
                            ((InternalNode) getLeftChild()).setRightChild(
                                    ((InternalNode) check).getLeftChild());
                            this.setMiddleChild(this.getRightChild());
                            this.setRightChild(null);
                            leftNode.setFirstPair(((InternalNode) leftNode)
                                    .getMiddleChild().getLeast());

                            leftNode.setSecondPair(((InternalNode) leftNode)
                                    .getRightChild().getLeast());

                            this.setFirstPair(middleNode.getLeast());
                            return this;
                        }
                    }
                    this.setFirstPair(middleNode.getLeast());
                    this.setSecondPair(rightNode.getLeast());
                    return this;
                }
            }
            else {
                check = this.rightNode.delete(pair);
                if (check == null) {
                    return null;
                }
                // check if is a leaf node
                if (check.getClass().equals(LeafNode.class)) {
                    // if the previous leaf node is full
                    if (check.isEmpty() && (this.middleNode.isFull())) {
                        check.setFirstPair(middleNode.getSecondPair());
                        middleNode.setSecondPair(null);
                        ((LeafNode) middleNode).getNext()
                                .setPrev((LeafNode) check);
                        ((LeafNode) check)
                                .setNext(((LeafNode) middleNode).getNext());

                        ((LeafNode) middleNode).setNext((LeafNode) check);
                        ((LeafNode) check).setPrev((LeafNode) middleNode);
                        this.setFirstPair(middleNode.getLeast());
                        this.setSecondPair(rightNode.getLeast());
                        return this;
                    }
                    // if the previous leaf node is not full
                    else if (check.isEmpty()) {
                        this.setSecondPair(null);
                        this.setRightChild(null);
                        return this;
                    }
                    this.setFirstPair(middleNode.getLeast());
                    this.setSecondPair(rightNode.getLeast());
                    return this;
                }
                else {
                    // check if is internal node and full
                    if (check.isEmpty()) {
                        if (middleNode.isFull()) {
                            ((InternalNode) check).setMiddleChild(
                                    ((InternalNode) check).getLeftChild());
                            ((InternalNode) check)
                                    .setLeftChild(((InternalNode) middleNode)
                                            .getRightChild());
                            ((InternalNode) middleNode).setRightChild(null);
                            check.setFirstPair(((InternalNode) check)
                                    .getMiddleChild().getLeast());
                            middleNode.setSecondPair(null);
                            this.setFirstPair(middleNode.getLeast());
                            this.setSecondPair(rightNode.getLeast());
                            return this;
                        }
                        else {
                            this.setSecondPair(null);
                            ((InternalNode) middleNode).setRightChild(
                                    ((InternalNode) check).getLeftChild());

                            middleNode.setSecondPair(((InternalNode) middleNode)
                                    .getRightChild().getLeast());
                            this.setRightChild(null);
                            check = null;
                            return this;
                        }
                    }
                }
                this.setFirstPair(middleNode.getLeast());
                this.setSecondPair(rightNode.getLeast());
                return this;
            }
        }
        return this;
    }

    /**
     * Checks to see if the node is empty.
     * 
     * @return true when both pairs are null
     */
    public boolean isEmpty() {
        return firstPair == null && secondPair == null;
    }

    /**
     * Update KVPair method for nodes
     * 
     * @return the KVPair needed to be updated
     */
    public KVPair getLeast() {
        KVPair least = leftNode.getLeast();
        return least;
    }
}