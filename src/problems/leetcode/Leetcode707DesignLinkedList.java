package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/design-linked-list/description/?envType=company&envId=bytedance&favoriteSlug=bytedance-thirty-days">707. 设计链表</a>
 *
 * @author lilongbin
 */
class Leetcode707DesignLinkedList {

    private final Node head, tail;

    public Leetcode707DesignLinkedList() {

        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        tail.prev = head;

    }

    public int get(int index) {
        Node cur = head;
        while (cur != tail && index-- >= 0) {
            cur = cur.next;
        }

        return cur.val;
    }

    public void addAtHead(int val) {
        Node newNode = new Node(val);
        insertInternal(head, head.next, newNode);
    }

    public void addAtTail(int val) {
        Node newNode = new Node(val);
        insertInternal(tail.prev, tail, newNode);
    }

    public void addAtIndex(int index, int val) {
        Node cur = head;
        while (cur != tail && index-- >= 0) {
            cur = cur.next;
        }

        if (index >= 0) {
            return;
        }

        Node newNode = new Node(val);
        insertInternal(cur.prev, cur, newNode);

    }

    public void deleteAtIndex(int index) {
        Node cur = head;
        while (cur != tail && index-- >= 0) {
            cur = cur.next;
        }

        if (cur == tail) {
            return;
        }

        Node prev = cur.prev;
        Node next = cur.next;
        prev.next = next;
        next.prev = prev;
        cur.prev = null;
        cur.next = null;
    }

    private void insertInternal(Node before, Node after, Node newNode) {
        before.next = newNode;
        newNode.prev = before;
        newNode.next = after;
        after.prev = newNode;
    }

    static class Node {
        int val;
        Node prev, next;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
