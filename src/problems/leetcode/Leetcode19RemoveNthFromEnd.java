package problems.leetcode;

import libs.ListNode;

/**
 * <a href="https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/?envType=study-plan-v2&envId=top-100-liked">19. 删除链表的倒数第 N 个结点</a>
 * <br>给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * <br>未避免正好删除 head 节点的 edge case，使用一个 dummy head
 */
class Leetcode19RemoveNthFromEnd {

    public static void main(String[] args) {
//        ListNode listNode = ListNode.fromValArr(1, 2, 3, 4, 5, 6, 7);
        ListNode listNode = ListNode.fromValArr(1, 2);
        ListNode res = new Leetcode19RemoveNthFromEnd().removeNthFromEnd(listNode, 2);
        System.out.println(res);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode l = null, r = null;
        r = dummy;
        for (int i = 0; i < n; i++) {
            r = r.next;
        }
        l = dummy;

        while (r.next != null) {
            l = l.next;
            r = r.next;
        }

        l.next = l.next.next;

        return dummy.next;
    }

}
