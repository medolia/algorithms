package design;

import java.util.Objects;

class LinkedList<T> {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        linkedList.add(1);
        linkedList.removeAll(1);
        System.out.println(linkedList);
        linkedList.add(1);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.add(1);
        linkedList.add(4);
        linkedList.add(1);

        linkedList.removeAll(1);

        System.out.println(linkedList);
    }

    Node head, tail;

    LinkedList() {
    }

    void add(T value) {
        Node newNode = new Node();
        newNode.value = value;

        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    void removeAll(T value) {
        Node p = head;
        while (p != null) {
            if (Objects.equals(p.value, value)) {
                if (head == tail) {
                    head = null;
                    tail = null;
                    return;
                }

                Node currPrev = p.prev, currNext = p.next;
                if (currPrev == null) { // head
                    head = currNext;
                    currNext.prev = null;
                    p = currNext;
                    continue;
                }
                if (currNext == null) { // tail
                    tail = currPrev;
                    currPrev.next = null;
                    p = null;
                    continue;
                }

                currPrev.next = currNext;
                currNext.prev = currPrev;

                p.prev = null;
                p.next = null;
                p = currNext;
            } else {
                p = p.next;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        Node p = this.head;
        int i = 15;
        boolean first = true;
        while (p != null && i-- > 0) {
            if (first) {
                first = false;
            } else {
                res.append("->");
            }
            res.append(p);
            p = p.next;
        }

        StringBuilder prevChain = new StringBuilder();
        p = this.tail;
        i = 15;
        first = true;
        while (p != null && i-- > 0) {
            if (first) {
                first = false;
            } else {
                prevChain.insert(0, "<-");
            }

            prevChain.insert(0, p);
            p = p.prev;
        }
        res.append("\n").append(prevChain);

        return res.toString();
    }

    static class Node<T> {
        T value;
        Node prev, next;

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

}
