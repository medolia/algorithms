package problems.leetcode;

import libs.ListNode;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * <p>
 * 思路：双指针同时走，走到 null 拼到另一头接着走
 */
class Leetcode160GetIntersectionNode {

    public static void main(String[] args) {

        ListNode nodeA = ListNode.fromValArr(4, 1, 8, 4, 5);
        ListNode nodeB = ListNode.fromValArr(5, 6, 1, 8, 4, 5);

        ListNode res = new Leetcode160GetIntersectionNode().getIntersectionNode(nodeA, nodeB);
        System.out.println(res);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

}
