package arrayAndLinkedList;

import struc.ListNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 n 个升序链表
 *
 * @author lbli
 */
public class Leetcode23MergeSortedLinkedList {

    public ListNode mergeKLists(ListNode[] lists) {
        // 最小堆，堆顶为最小值
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparing(e -> e.val));
        priorityQueue.addAll(Arrays.asList(lists));

        ListNode dummy = new ListNode(-1);

        ListNode curr = dummy;
        while(!priorityQueue.isEmpty()) {
            curr.next = priorityQueue.poll();
            curr = curr.next;
        }

        return dummy.next;
    }

}
