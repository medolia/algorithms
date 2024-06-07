package arrayAndLinkedList;

import struc.ListNode;

/**
 * @author lbli
 */
class Common {

    static ListNode merge2SortedList(ListNode lSortedHead, ListNode rSortedHead) {
        ListNode dummy = new ListNode(0);
        ListNode mergeP = dummy;
        ListNode lP = lSortedHead, rP = rSortedHead;
        while (lP != null && rP != null) {
            if (lP.val < rP.val) {
                mergeP.next = lP;
                lP = lP.next;
            } else {
                mergeP.next = rP;
                rP = rP.next;
            }
            mergeP = mergeP.next;
        }
        mergeP.next = lP == null ? rP : lP;

        return dummy.next;
    }
}
