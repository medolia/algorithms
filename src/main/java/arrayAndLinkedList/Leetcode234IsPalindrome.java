package arrayAndLinkedList;

import struc.ListNode;


/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为
 * 回文链表
 * 。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 思路：快慢指针找中点，反转后半部分双指针比对，返回结果前再反转一次还原结果
 */
class Leetcode234IsPalindrome {

    public static void main(String[] args) {
        boolean palindrome = new Leetcode234IsPalindrome()
                .isPalindrome(ListNode.fromValArr(1, 2, 2, 1));
        System.out.println(palindrome);
    }

    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }

        ListNode reversedPart = reverse(slow);

        ListNode p1 = head, p2 = reversedPart;

        boolean result = true;
        while (p1 != null && p2 != null) {
            if (p1.val != p2.val) {
                result = false;
                break;
            }

            p1 = p1.next;
            p2 = p2.next;
        }

        reverse(reversedPart);

        return result;
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }


        ListNode reverse = reverse(head.next);
        head.next.next = head;
        head.next = null;

        return reverse;
    }


}
