package libs;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode() {
    }

    public static ListNode fromValArr(int... arr) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i : arr) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }

        return dummy.next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.valueOf(this.val));

        ListNode cur = this.next;
        int limit = 10;
        while (null != cur && --limit > 0) {
            builder.append("->").append(cur.val);
            cur = cur.next;
        }

        return builder.toString();
    }
}
