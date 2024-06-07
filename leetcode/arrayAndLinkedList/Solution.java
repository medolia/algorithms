package arrayAndLinkedList;

import struc.ListNode;
import struc.Node;

import java.util.*;

public class Solution {
    /**
     * 面试题 10.01. 合并排序的数组
     * 双指针，从尾部开始比较，较大数入坑
     * 考虑 B 数组有元素剩余时，补充填充剩余坑位
     */
    public void merge(int[] A, int m, int[] B, int n) {
        int ptrA = m - 1;
        int ptrB = n - 1;
        int ptr = m + n - 1;

        while (ptrA >= 0 || ptrB >= 0) {
            if (A[ptrA] >= B[ptrB])
                A[ptr--] = A[ptrA--];
            else
                A[ptr--] = B[ptrB--];
        }

        if (ptrB >= 0) {
            while (ptrB >= 0)
                A[ptr--] = B[ptrB--];
        }
    }

    /**
     * 剑指 Offer 06. 从尾到头打印链表
     * 先进后出 -> 栈
     */
    public int[] reversePrint(ListNode head) {
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        int size = stack.size();
        int[] print = new int[size];
        for (int i = 0; i < size; i++) {
            print[i] = stack.pop().val;
        }
        return print;
    }

    /**
     * 剑指 Offer 11. 旋转数组的最小数字
     * 允许存在重复数字，当判定比较相等时应仅谨慎地将区间缩短1
     */
    public int minArray(int[] numbers) {
        int low = 0;
        int high = numbers.length - 1;

        while (low < high) {
            int pivot = (low + high) >> 1;
            if (numbers[pivot] > numbers[high])
                low = pivot + 1;
            else if (numbers[pivot] < numbers[high])
                high = pivot;
            else
                high -= 1;
        }

        return numbers[low];
    }

    /**
     * 剑指 Offer 18. 删除链表的节点
     * <p>
     * 创建一个假非 null 的节点，其 next 为head
     */
    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummy = new ListNode(0), curr = head, prev = dummy;
        dummy.next = head;

        while (curr.val != val) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = curr.next;

        return dummy.next;
    }

    /**
     * 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
     * <p>
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使
     * 得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
     * <p>
     * 思路：双指针，奇数在从 0，偶数从 len-1 开始
     */
    public int[] exchange(int[] nums) {
        int[] res = nums;

        int oddPtr = 0;
        int evenPtr = res.length - 1;

        while (true) {
            while (oddPtr < evenPtr && res[oddPtr] % 2 == 1)
                oddPtr++;
            while (oddPtr < evenPtr && res[evenPtr] % 2 == 0)
                evenPtr--;

            if (oddPtr >= evenPtr) break;

            int tmp = res[oddPtr];
            res[oddPtr++] = res[evenPtr];
            res[evenPtr--] = tmp;
        }

        return res;
    }

    /**
     * 剑指 Offer 22. 链表中倒数第k个节点
     * <p>
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开
     * 始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开
     * 始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
     * <p>
     * 思路：要仅遍历一次链表，可以使用两个间隔为 k-1 的两个指针，当靠近尾端的指针遍历
     * 完毕时，靠近头端的指针为倒数第 k 个结点
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode ptr1 = head, ptr2 = head;
        while (k > 1) {
            --k;
            ptr2 = ptr2.next;
        }

        while (ptr2.next != null) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return ptr1;
    }

    /**
     * 剑指 Offer 24. 反转链表
     * <p>
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode tmp = curr;
            curr = curr.next;
            tmp.next = prev;
            prev = tmp;
        }

        return prev;
    }

    /**
     * 剑指 Offer 29. 顺时针打印矩阵
     * <p>
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     * <p>
     * 思路：考虑上、下、左、右四个边界，分四个方向讨论
     * 1. l-r, ++t, t>b?
     * 2. t-b, --r, r<l?
     * 3. r-l, --b, b<t?
     * 4. b-t, ++l, l>r?
     * <p>
     * 启发：涉及矩阵方向遍历，考虑位移矩阵、边界收缩。
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        int[] res = new int[(r + 1) * (b + 1)];
        while (true) {
            for (int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if (++t > b) break;
            for (int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if (l > --r) break;
            for (int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if (t > --b) break;
            for (int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if (++l > r) break;
        }
        return res;
    }

    /**
     * 剑指 Offer 35. 复杂链表的复制
     * <p>
     * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针
     * 指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
     * <p>
     * 思路：分三步，浅复制拼接 -> 复制 random -> 拆分主链与复制链
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node curr = head, clone = null;

        // 浅复制，拼接
        while (curr != null) {
            // a - 2 -> a' - 1 -> b
            clone = new Node(curr.val);
            clone.next = curr.next;
            curr.next = clone;
            curr = clone.next;
        }

        // 复制 random
        curr = head;
        while (curr != null) {
            clone = curr.next;
            if (curr.random != null)
                clone.random = curr.random.next;
            curr = clone.next;
        }

        // 拆分
        curr = head;
        Node cloneHead = clone = head.next;
        while (curr != null) {
            curr.next = clone.next;
            curr = curr.next;
            if (curr != null) clone.next = curr.next;
            clone = clone.next;
        }

        return cloneHead;
    }

    /**
     * 剑指 Offer 39. 数组中出现次数超过一半的数字
     * <p>
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * <p>
     * 思路：1.快速排序返回中间索引 2.摩尔投票法/众数
     */
    public int majorityElement(int[] nums) {
        /*Arrays.sort(nums);
        return nums[nums.length / 2];*/

        int x = 0, vote = 0;
        for (int num : nums) {
            if (vote == 0) x = num;
            vote += num == x ? 1 : -1;
        }

        return x;
    }

    /**
     * 剑指 Offer 40. 最小的k个数
     * <p>
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、
     * 6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     * <p>
     * 思路：1. 使用大顶堆，时间 nlogk（k<=n）
     * 2. 快排（当 pivot 与 k 符合时提前结束），会修改原数组，时间 n
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] res = new int[k];
        if (k == 0) return res;

        Queue<Integer> queue = new PriorityQueue<>((num1, num2) -> num2 - num1);
        for (int i = 0; i < k; i++)
            queue.offer(arr[i]);

        for (int i = k; i < arr.length; i++) {
            if (arr[i] < queue.peek()) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }

        for (int i = 0; i < k; i++)
            res[i] = queue.poll();

        return res;
    }
    /*int k;
    int[] res;
    public int[] getLeastNumbers2(int[] arr, int k) {
        if (k == arr.length) return arr;
        this.k = k;
        res = new int[k];
        quickSort(arr, 0, arr.length - 1);
        return res;
    }

    //递归分区
    public void quickSort(int[] a, int start, int end) {
        if (start < end) {
            int p = partition(a, start, end);
            //特殊之处，中途判断p和k，可以提前结束
            if (p == k || p == k - 1) {
                for (int i = 0; i < k; i++) {
                    res[i] = a[i];
                }
            } else if (p < k) quickSort(a, p + 1, end);
            else if (p > k) quickSort(a, start, p - 1);
        }
    }

    //核心算法
    public int partition(int[] a, int start, int end) {

        //快速排序在分区时运用了巧妙的方法因此不用申请额外空间
        //取最后一个点mid为中心值
        int mid = a[end];
        int i = start;
        //遇到比mid小的，将其与i之后的第一个数据进行交换
        //i之前的都是小于mid的，i之后的未知

        //从头遍历到尾
        for (int j = start; j < end; j++) {
            //遇到小的就和i交换
            if (a[j] < mid) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;//i往后移一位
            }
        }
        //遍历完后，可以保证，i之前的都是小于mid,i之后(包括i)都是大于mid
        //再将分区点插在i处(交换)
        a[end] = a[i];
        a[i] = mid;
        return i; //返回i位置
    }*/

    /**
     * 剑指 Offer 52. 两个链表的第一个公共节点
     * <p>
     * 输入两个链表，找出它们的第一个公共节点。(两链表自第一相交后一直相交，即成 Y 型)
     * <p>
     * 思路：两者同时向前走，一端走到 null 时接向另一端的头部，则循环结果要么两结点同
     * 时为 null 要么在公共节点相遇。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode currA = headA, currB = headB;
        while (currA != currB) {
            currA = currA == null ? headB : currA.next;
            currB = currB == null ? headA : currB.next;
        }
        return currA;
    }

    /**
     * 剑指 Offer 53 - I. 在排序数组中查找数字 I
     * <p>
     * 统计一个数字在排序数组中出现的次数，要求时间复杂度为 logN。
     * <p>
     * 思路：两次二分查找确定右边界和左边界，辅助函数 helper 帮助确定 nums 中
     * 大于 tgt 的最小索引值，引入 tgt - 1 得到大于等于 tgt 的最左边界。
     */
    public int counterInSortedArray(int[] nums, int target) {
        return helper(nums, target) - helper(nums, target - 1);
    }

    int helper(int[] nums, int tgt) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= tgt) left = mid + 1;
            else right = mid - 1;
        }

        return left;
    }

    /**
     * 2. 两数相加
     * <p>
     * 给出两个非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点
     * 只能存储一位数字。如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0开头。
     * <p>
     * 思路：
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();

        int carry = 0;
        ListNode res = head;
        while (l1 != null || l2 != null) {
            int n1 = l1 == null ? 0 : l1.val;
            int n2 = l2 == null ? 0 : l2.val;
            int rem = (n1 + n2 + carry) % 10;
            carry = (n1 + n2 + carry) / 10;

            res.next = new ListNode(rem);
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            res = res.next;
        }
        if (carry != 0) res.next = new ListNode(carry);

        return head.next;
    }

    /**
     * 15. 三数之和
     * 使用双指针，注意数组可能存在重复数字，当两个指针处的数字与之前的相等则
     * 使用 continue 跳过这次遍历
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;

        for (int first = 0; first < len; first++) {
            if (first > 0 && nums[first] == nums[first - 1])
                continue;

            int third = len - 1;
            for (int second = first + 1; second < len; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1])
                    continue;

                int target = -(nums[first] + nums[second]);
                while (third > second && nums[third] > target) third--;

                // 该 second 值已经不可能找到对应的 third 值了，直接取下一个 first
                if (third == second) break;

                if (nums[third] == target) {
                    List<Integer> ans = new ArrayList<>();
                    ans.add(nums[first]);
                    ans.add(nums[second]);
                    ans.add(target);
                    res.add(ans);
                }
            }
        }

        return res;
    }

    /**
     * 25. K 个一组翻转链表
     * <p>
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * <p>
     * 思路：使用递归思路，
     * 1. 先从头节点开始数 k 个节点寻找下次递归的起始结点 b，若不足 k 个则返回头部；
     * 2. 翻转头部至 b 节点的前一个节点 [head, b) 区间内调用辅助函数，辅助函数返回翻转链表头部 newHead；
     * 3. 拼接原头部和子递归返回的头部：head.next = f(b, k)；
     * 4. 返回新头部 newHead；
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return null;
        ListNode a = head, b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) return head; // 若节点数小于 k，返回头部（在 next 操作前判定）
            b = b.next;
        }

        ListNode newHead = reverseBet(a, b); // 前 k 个结点翻转；
        a.next = reverseKGroup(b, k); // 与递归的下一串拼接；
        return newHead; // 返回头部；
    }

    /**
     * 翻转 [a, b) 左闭右开，即翻转 a 至 结点 b 的前一个结点，返回头节点；
     * 若 b 为 null，即为翻转整个链表；
     */
    ListNode reverseBet(ListNode a, ListNode b) {
        ListNode pre = null, curr = a, nxt;

        while (curr != b) {
            nxt = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nxt;
        }

        return pre;
    }

    /**
     * 33. 搜索旋转排序数组
     * 只需要关注每次二分后那部分为有序即可（通过比较 mid 与 0 索引处的值）
     * 然后根据有序部分的上下界确定下一次二分的边界
     */
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) return -1;
        if (n == 1) return target == nums[0] ? 0 : -1;

        int l = 0;
        int r = n - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target)
                return mid;

            if (nums[mid] >= nums[0]) {
                // 左部分有序
                if (nums[0] <= target && target <= nums[mid])
                    r = mid - 1;
                else l = mid + 1;
            } else {
                // 右部分有序
                if (nums[mid] <= target && target <= nums[n - 1])
                    l = mid + 1;
                else r = mid - 1;
            }
        }

        return -1;
    }

    /**
     * 92. 反转链表 II
     * <p>
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     * <p>
     * 思路：递归思维，
     * 递归 1：翻转 head 开始 m 至 n 的节点即为翻转 head.next 开始 m-1 至 n-1 的节点， 返回头部；
     * 如此递归至翻转 head 开始的前 n 个节点问题（m = 1 时）；
     * 递归 2：翻转 head 开始前 n 个节点递归为，先翻转除 head.next 开始的 n-1 节点
     * 接着翻转 head 至 head.next 部分：head.next.next = head；
     * 然后再与首个不翻转的节点相连（n =1 时被赋值），返回尾部（即最后一个被翻转的节点、首个不翻转的节点的前一个节点）；
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == 1) return reverseTopN(head, n);
        else head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }

    ListNode notRevHead;

    ListNode reverseTopN(ListNode head, int n) {
        if (n == 1) { // base case，同时记录首个不翻转的节点
            notRevHead = head.next;
            return head;
        }

        ListNode last = reverseTopN(head.next, n - 1);
        head.next.next = head;
        head.next = notRevHead;

        return last;
    }

    /**
     * 142. 环形链表 II
     * <p>
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * <p>
     * 思路：先用快慢指针遍历，两指针相遇时代表快指针比慢指针多走了总环的长度，慢指针走的
     * 步数也恰为总环长度，这时将快指针置于头部，然后两指针（距离为环的长度）同速前进，相
     * 遇的结点即为环的起始结点
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }

        if (fast == null || fast.next == null) return null;

        fast = head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * 217. 存在重复元素
     * <p>
     * 给定一个整数数组，判断是否存在重复元素。
     * <p>
     * 思路：排序再遍历，时间 nlogn 空间 n
     */
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; ++i) {
            if (nums[i] == nums[i + 1]) return true;
        }
        return false;
    }

    /**
     * 234. 回文链表
     * <p>
     * 请判断一个链表是否为回文链表。
     * <p>
     * 思路：找到链表中点后翻转后半部分（注意长为奇偶的不同），然后比较两头即可；
     * 若需还原翻转部分，只需再翻转一次后半部分然后拼接即可；
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) return true;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // ListNode p = slow;
        if (fast == null) slow = slow.next; // 若为奇数长，slow 须再往后一个节点；

        ListNode q = reverseBet(slow, null), right = q;
        ListNode left = head;

        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }

        // p.next = reverseBet(q, null);

        return true;
    }

    /**
     * 560. 和为K的子数组
     * <p>
     * 使用一个前缀和（以 i 为结尾的累计和）映射 preSum，则 [i,j] 的连续子数组和可
     * 用 preSum[j] - preSum[i-1] 表示；
     */
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> sumMap = new HashMap<>() {{
            put(0, 1);
        }};

        int res = 0, sum0_i = 0;
        for (int i = 0; i < n; i++) {
            sum0_i += nums[i]; // 得到至 i 为止的元素之和
            int sum0_j = sum0_i - k;
            if (sumMap.containsKey(sum0_j))
                res += sumMap.get(sum0_j); // 应该加上出现的次数
            sumMap.merge(sum0_i, 1, Integer::sum);
        }

        return res;
    }

    /**
     * 674. 最长连续递增序列
     * <p>
     * 给定一个未经排序的整数数组，找到最长且连续递增的子序列，并返回该序列的长度。
     * <p>
     * 思路：滑动窗口
     * 窗口定义：anchor 至 i 为严格递增的数组子序列；
     * 窗口初始化，长度为 1，仅包含 nums[0]
     * 窗口移动：
     * 1. 若 nums[i] <= nums[i-1] anchor = i，即窗口左边界变为 i;
     * 2. 若 nums[i] > nums[i-1] anchor 不变；
     * 更新窗口后，更新最大长度 len = max(len, i-anchor+1)；
     */
    public int findLengthOfLCIS(int[] nums) {
        int anchor = 0, maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] <= nums[i - 1]) anchor = i;
            maxLen = Math.max(maxLen, i - anchor + 1);
        }

        return maxLen;
    }

    /**
     * 830. 较大分组的位置
     * <p>
     * 在一个由小写字母构成的字符串 s 中，包含由一些连续的相同字符所构成的分组。
     * 例如，在字符串 s = "abbxxxxzyy" 中，就含有 "a", "bb", "xxxx", "z" 和 "yy" 这样的一些分组。
     * 分组可以用区间 [start, end] 表示，其中 start 和 end 分别表示该分组的起始和终止位置的下标。
     * 上例中的 "xxxx" 分组用区间表示为 [3,6] 。
     * 我们称所有包含大于或等于三个连续字符的分组为 较大分组 。
     * 找到每一个 较大分组 的区间，按起始位置下标递增顺序排序后，返回结果。
     * <p>
     * 思路：一次线扫描。
     */
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> res = new LinkedList<>();

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            int l = i, r = i;
            while (r < s.length() && s.charAt(r) == s.charAt(l)) {
                ++r;
            }
            if (r - l >= 3) {
                List<Integer> tmp = new LinkedList<>();
                tmp.add(l);
                tmp.add(r - 1);
                res.add(tmp);
            }
            i = r - 1;
        }

        return res;
    }

    public static void main(String[] args) {
        new Solution().largeGroupPositions("abbxxxxzzy");
    }
}

