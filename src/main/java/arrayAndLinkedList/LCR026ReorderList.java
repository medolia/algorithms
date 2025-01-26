package arrayAndLinkedList;

import struc.ListNode;

/**
 * <a href="https://leetcode.cn/problems/LGjMqU/solutions/1037737/zhong-pai-lian-biao-by-leetcode-solution-wm25/?envType=company&envId=bytedance&favoriteSlug=bytedance-thirty-days">LCR 026. 重排链表</a>
 * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
 * <p>
 * L0 → L1 → … → Ln-1 → Ln
 * 请将其重新排列后变为：
 * <p>
 * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
 * <p>
 * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 思路重点：找到中点 + 反转后半部分 + 合并
 * <p>
 * 时空复杂度：O(N) O(1)
 *
 * @author lilongbin
 */
class LCR026ReorderList {

    public void reorderList(ListNode head) {

        // 1. 找到中点

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = head, fast = head, prev = dummy;
        while (fast != null && fast.next != null) {
            prev = prev.next;
            slow = slow.next;
            fast = fast.next.next;
        }

        dummy.next = null;
        ListNode reverseHead;
        if (fast == null) { // 说明为偶数
            reverseHead = slow;
            prev.next = null;
        } else { // 奇数
            reverseHead = slow.next;
            slow.next = null;
        }

        // 2. 反转后半部分
        ListNode reversed = reverse(reverseHead);

        // 3. 拼接
        head = merge(head, reversed);

    }

    private ListNode merge(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        boolean flag = true;

        while (l1 != null && l2 != null) {
            if (flag) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            flag = !flag;
            curr = curr.next;
        }

        curr.next = l1 == null ? l2 : l1;

        return dummy.next;
    }

    private ListNode reverse(ListNode l) {
        if (l == null || l.next == null) {
            return l;
        }

        ListNode reversed = reverse(l.next);
        l.next.next = l;
        l.next = null;

        return reversed;
    }

}
