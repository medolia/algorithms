package problems.leetcode;

import libs.Utils;
import libs.ListNode;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 * <br>请你将所有链表合并到一个升序链表中，返回合并后的链表。，限制空间复杂度 O(1)
 * <p>
 * 思路：归并思维，类似 {@link Leetcode148SortList}，时：O(kNlogN)，k 为链表的平均长度，N 为链表个数， 空：O(1)
 * <br>使用堆为劣等解，空间复杂度为 O(n)，n为总节点数
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
        return mergeKLists(lists, 0, lists.length - 1);
    }

    private ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        } else if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        ListNode lHead = mergeKLists(lists, start, mid);
        ListNode rHead = mergeKLists(lists, mid + 1, end);

        return Utils.merge2SortedLinkedList(lHead, rHead);
    }
}
