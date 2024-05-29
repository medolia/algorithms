package arrayAndLinkedList;

import struc.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 n 个升序链表
 *
 * @author lbli
 */
class Leetcode23MergeSortedLinkedList {

    public static void main(String[] args) {
        ListNode node1 = ListNode.fromValArr(1, 4, 5);
        ListNode node2 = ListNode.fromValArr(1, 3, 4);
        ListNode node3 = ListNode.fromValArr(1, 4, 5);

        ListNode result = new Leetcode23MergeSortedLinkedList().mergeKLists(new ListNode[]{node1, node2, node3});
        System.out.println(result);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        // 最小堆，堆顶为最小值
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparing(e -> e.val));
        for (ListNode node : lists) {
            ListNode curr = node;
            while (curr != null) {
                heap.add(curr);

                curr = curr.next;
            }
        }

        ListNode dummy = new ListNode(-1);

        ListNode curr = dummy;
        while(!heap.isEmpty()) {
            curr.next = heap.poll();
            curr = curr.next;
        }
        curr.next = null;

        return dummy.next;
    }

}
