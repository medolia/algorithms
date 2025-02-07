package problems.other;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1, 90);
        lruCache.put(2, 290);
        lruCache.put(3, 909);


        lruCache.put(2, 290);
        lruCache.put(7, 34);
    }

    Map<Integer, ListNode> cache;
    int capacity;
    ListNode head, tail;

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        this.capacity = capacity;

        head = new ListNode("HEAD");
        tail = new ListNode("TAIL");
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }

        ListNode result = cache.get(key);
        refresh(result);
        return result.value;
    }

    public void put(int key, int value) {
        // refresh if exist
        if (cache.containsKey(key)) {
            ListNode curr = cache.get(key);
            curr.value = value;

            refresh(curr);
            return;
        }

        // remove oldest
        if (capacity <= 0) {
            ListNode last = tail.prev;
            last.prev.next = tail;
            tail.prev = last.prev;
            last.prev = null;
            last.next = null;

            ++capacity;
        }

        // add new node
        ListNode newNode = new ListNode(key, value);
        refresh(newNode);
        cache.put(key, newNode);
        --capacity;
    }

    void refresh(ListNode curr) {
        ListNode currNext = curr.next, currPrev = curr.prev;

        ListNode headNext = head.next;
        head.next = curr;
        curr.prev = head;
        curr.next = headNext;
        headNext.prev = curr;

        if (null != currNext && null != currPrev) {
            currPrev.next = currNext;
            currNext.prev = currPrev;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        ListNode p = this.head;
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

    static class ListNode {
        ListNode prev, next;
        int key, value;
        private String name;

        public ListNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public ListNode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name == null ? String.valueOf(value) : name;
        }
    }
}
