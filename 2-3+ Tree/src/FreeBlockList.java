/**
 * Free block list.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * 
 * @version 2016.09.27
 */
public class FreeBlockList {

    /**
     * Head of the list.
     */
    private Node head;
    private Node tail;
    private int size;

    /**
     * Initialize a new, empty FBL.
     * 
     * @param size
     *            Size of the memory pool.
     */
    public FreeBlockList(int size) {
        Node node = new Node(0, size);
        head = new Node(0, 0, null, node);
        tail = new Node(0, 0, node, null);
        node.prev = this.head;
        node.next = this.tail;
        this.size = 1;
    }

    /**
     * Search for space in free blocks.
     * 
     * @param length
     *            Needed size.
     * @return Return the position of the free block.
     */
    public int search(int length) {
        int res = Integer.MAX_VALUE;
        int pos = -1; //error handle
        Node curr = this.head.next;

        while (curr != this.tail) {
            if (res == 0) {
                break;
            }
            if (curr.len >= length && curr.len - length < res) {
                res = curr.len - length;
                pos = curr.pos;
            }
            curr = curr.next;
        }
        return pos;
    }

    /**
     * Add a new free block to the list.
     * 
     * @param pos
     *            Position of the free block.
     * @param len
     *            Length of the free block.
     */
    public void add(int pos, int len) {
        Node curr = this.head.next;
        Node newNode = new Node(pos, len);

        while (curr != this.tail) {
            if (pos <= curr.pos) {
                break;
            }
            else {
                curr = curr.next;
            }
        }
        if (curr.pos == pos && curr.len == 0) {
            curr.len = len;
        }
        else {
            newNode.next = curr;
            newNode.prev = curr.prev;
            newNode.prev.next = newNode;
            curr.prev = newNode;
            this.size++;
            merge(newNode);
        }
    }

    /**
     * Merge adjacent free blocks.
     * 
     * @param node
     *            The node to be checked.
     */
    private void merge(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (prev != this.head && prev.pos + prev.len == node.pos) {
            node.pos = prev.pos;
            node.len += prev.len;
            node.prev = prev.prev;
            node.prev.next = node;
            this.size--;
        }
        if (next != this.tail && node.pos + node.len == next.pos) {
            node.len += next.len;
            node.next = next.next;
            next.next.prev = node;
            this.size--;
        }
    }

    /**
     * Get size.
     * 
     * @return Size.
     */
    public int getSize() {
        return this.size;
    }
    
    /**
     * Print blocks.
     */
    public void print() {
        Node curr = head.next;

        while (curr.next != tail) {
            System.out.printf("(%d,%d) -> ", curr.pos, curr.len);
            curr = curr.next;
        }
        System.out.printf("(%d,%d)\n", curr.pos, curr.len);
    }

    /**
     * Split space from a free block.
     * 
     * @param pos
     *            Position of the free block.
     * @param len
     *            Length of the free block.
     */
    public void split(int pos, int len) {
        Node curr = this.head.next;

        while (!curr.equals(this.tail)) {
            if (curr.pos == pos) {
                curr.len -= len;
                curr.pos += len;

                if (curr.len == 0 && this.size > 1) {
                    curr.prev.next = curr.next;
                    curr.next.prev = curr.prev;
                    this.size--;
                }
                else {
                    merge(curr);
                }
                break;
            }
            curr = curr.next;
        }
    }

    /**
     * Private node class.
     * 
     * @author Randy Liang (randy8)
     * @version September 2, 2016
     */
    private class Node {

        private int pos;
        private int len;
        private Node prev;
        private Node next;

        /**
         * Create a new Node.
         * 
         * @param pos
         *            Position.
         * @param len
         *            Length.
         * @param prev
         *            Previous node.
         * @param next
         *            Next node.
         */
        public Node(int pos, int len, Node prev, Node next) {
            this.pos = pos;
            this.len = len;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Create a new Node.
         * 
         * @param pos
         *            Position.
         * @param len
         *            Length.
         */
        public Node(int pos, int len) {
            this.pos = pos;
            this.len = len;
        }
    }
}