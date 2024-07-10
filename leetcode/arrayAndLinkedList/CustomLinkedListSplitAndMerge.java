package arrayAndLinkedList;

import struc.ListNode;

/**
 * 将链表 {1->2->3->4->5} 处理为 {1->5->2->4->3}
 * 将链表 {1->2->3->4->5->6} 处理为 {1->6->2->5->3->4}
 * <p>
 * 思路：快慢指针找到中点并断开 - 反转后半段 - 错位拼接
 */
class CustomLinkedListSplitAndMerge {

    public static void main(String[] args) {
        ListNode customRes = ListNode.fromValArr(1, 2, 3, 4, 5, 6);
        ListNode custom = new CustomLinkedListSplitAndMerge().custom(customRes);
        System.out.println(custom);
    }

    ListNode custom(ListNode head) {
        ListNode slow = head, fast = head;

        ListNode prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // 从中点处断开链表
        if (fast == null) { // 节点数为偶数时
            if (prev != null) {
                prev.next = null;
            }
        } else { // 节点数为奇数时
            ListNode slowNext = slow.next;
            slow.next = null;
            slow = slowNext;
        }

        ListNode reversedP2 = reverse(slow);

        return merge(head, reversedP2);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode resultP = new ListNode();
        ListNode resultH = resultP;

        boolean flag = true;
        while (l1 != null && l2 != null) {
            if (flag) {
                resultP.next = l1;
                l1 = l1.next;
            } else {
                resultP.next = l2;
                l2 = l2.next;
            }

            flag = !flag;
            resultP = resultP.next;
        }

        resultP.next = l1 == null ? l2 : l1;

        return resultH.next;
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode rest = reverse(head.next);
        head.next.next = head;
        head.next = null;

        return rest;
    }
}
