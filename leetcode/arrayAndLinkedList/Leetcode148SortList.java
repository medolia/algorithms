package arrayAndLinkedList;

import struc.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 * <a href="https://leetcode.cn/problems/sort-list/description/?envType=study-plan-v2&envId=top-100-liked">148. 排序链表</a>
 *
 *
 * @author lbli
 */
class Leetcode148SortList {

    public static void main(String[] args) {
        ListNode result = new Leetcode148SortList().sortList(ListNode.fromValArr(-1, 5, 3, 4, 0));
        System.out.println(result);
    }

    /**
     * 归并框架
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // 快慢指针找到链表中间位置
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmpMid = slow.next;
        slow.next = null;

        ListNode lSortedHead = sortList(head);
        ListNode rSortedHead = sortList(tmpMid);

        return Utils.merge2SortedLinkedList(lSortedHead, rSortedHead);
    }

    /**
     * 使用最小堆，空间复杂度 O(n)，劣等解
     */
    @SuppressWarnings("all")
    public ListNode sortListLegacy(ListNode head) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.val));
        ListNode pointer = head;
        while (pointer != null) {
            pq.add(pointer);
            pointer = pointer.next;
        }

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (!pq.isEmpty()) {
            curr.next = pq.poll();
            curr = curr.next;
        }
        curr.next = null;

        return dummy.next;
    }
}
