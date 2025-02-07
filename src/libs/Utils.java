package libs;

/**
 * @author lbli
 */
public class Utils {

    /**
     * 合并排序链表
     */
    public static ListNode merge2SortedLinkedList(ListNode lSortedHead, ListNode rSortedHead) {
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

    /**
     * 求最大公因数
     * <p>
     * 约数：假如整数 x 可以整除 y，那么 y 为 x 的一个约数，所有正整数都是 0 的约数，1 的约数只有 1
     * <p>
     * 定理：x = ay + b，那么 x 与 y 的最大公因数等于 b 与 y 的最大公因数
     *
     * @param x 较大值
     * @param y 较小值
     */
    public static int gcd(int x, int y) {
        // x % y 一定比 y 小
        return y == 0 ? x : gcd(y, x % y);
    }
}
