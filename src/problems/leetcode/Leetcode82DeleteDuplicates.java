package problems.leetcode;

import libs.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/">82. 删除排序链表中的重复元素 II</a>
 * <p>给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
 * <p>
 * 思路：滑动窗口，窗口为最长重复元素的数组
 */
class Leetcode82DeleteDuplicates {

    public static void main(String[] args) {
        ListNode res = new Leetcode82DeleteDuplicates().deleteDuplicatesV2(
                ListNode.fromValArr(1, 2, 3, 3, 4, 4, 5));
        System.out.println(res);
    }

    public ListNode deleteDuplicates(ListNode head) {
        Deque<ListNode> deque = new ArrayDeque<>();
        ListNode dummyHead = new ListNode();
        ListNode resP = dummyHead;

        ListNode p = head;
        while (p != null) {
            if (!deque.isEmpty() && deque.peek().val != p.val) {
                if (deque.size() > 1) {
                    deque.clear();
                } else {
                    resP.next = deque.pop();
                    resP = resP.next;
                }
            }

            deque.push(p);
            p = p.next;
        }

        resP.next = deque.size() == 1 ? deque.pop() : null;
        return dummyHead.next;
    }

    public ListNode deleteDuplicatesV2(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }

        return dummy.next;
    }

}
