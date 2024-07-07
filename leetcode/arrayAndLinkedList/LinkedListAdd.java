package arrayAndLinkedList;

import struc.ListNode;

/**
 * <a href="https://leetcode.cn/problems/sum-lists-lcci/description/">面试题 02.05. 链表求和</a>
 * 给定两个用链表表示的整数，每个节点包含一个数位。
 * <p>
 * 这些数位是反向存放的，也就是个位排在链表首部。
 * <p>
 * 编写函数对这两个整数求和，并用链表形式返回结果。
 * <p>
 * 思路：双指针，用 0 补齐位数差（17+234 -> 017+234），考虑遍历后的溢出(5+5=10)
 * <p>扩展：如果正向存放，使用两个栈(0补齐)作为辅助
 */
class LinkedListAdd {

    public static void main(String[] args) {
        ListNode l1 = ListNode.fromValArr(7, 1, 6, 3);
        ListNode l2 = ListNode.fromValArr(5, 9, 4, 7);

//        ListNode l1 = ListNode.fromValArr(5);
//        ListNode l2 = ListNode.fromValArr(5);
        ListNode res = new LinkedListAdd().addTwoNumbers(l1, l2);
        System.out.println(res);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;

        ListNode resP = new ListNode();
        ListNode headP = resP;

        int leftOver = 0;
        while (null != p1 || null != p2) {
            if (p1 == null) {
                p1 = new ListNode(0);
            }
            if (p2 == null) {
                p2 = new ListNode(0);
            }

            int resV = (p1.val + p2.val + leftOver) % 10;
            leftOver = (p1.val + p2.val + leftOver) / 10;

            resP.next = new ListNode(resV);
            resP = resP.next;

            p1 = p1.next;
            p2 = p2.next;
        }

        if (leftOver > 0) {
            resP.next = new ListNode(leftOver);
        }

        return headP.next;
    }

}
