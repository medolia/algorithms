package problems.sword2offer;

import java.util.*;

public class Solution4 {
    /**
     * 剑指 Offer 31. 栈的压入、弹出序列
     * <p>
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺
     * 序。假设压入栈的所有数字均不相等。例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序
     * 列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该
     * 压栈序列的弹出序列。
     * <p>
     * 思路：使用一个push辅助栈和pop指针模拟实际栈压入弹出过程
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length != popped.length) return false;

        Stack<Integer> stack = new Stack<>();
        int ptr = 0;

        for (int num : pushed) {
            stack.push(num);
            while (!stack.isEmpty() && stack.peek() == popped[ptr]) {
                stack.pop();
                ++ptr;
            }
        }

        return stack.isEmpty();
    }

    /**
     * 76. 最小覆盖子串
     *
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
     *
     * 思路：滑动窗口：
     * 收缩条件：已包含子字符串 t 中的全部字符，可使用一个计数量从 t 的长度开始计数；
     * 移动操作：指针++，对应被跳过/包括的字符在计数数组中++/--；
     *
     * 启发：该题无法从局部最优得到全局最优，不适用于贪心算法；
     *
     */
    public String minWindow(String s, String t) {
        int[] needed = new int[128];
        for (int i = 0; i<t.length(); i++)
            needed[t.charAt(i)]++;

        int l = 0, r= 0, count = t.length(), minLen = Integer.MAX_VALUE, start = 0;
        while (r < s.length()) {
            char c = s.charAt(r);
            // 模拟窗口右边界移动
            if (needed[c] > 0) count--;
            needed[c]--;

            // 若已完全包含 t，开始尝试收缩窗口左边界
            if (count == 0) {
                // 注意条件：数组计数须小于 0，窗口左边界才能移动；
                while (l < r && needed[s.charAt(l)] < 0) {
                    needed[s.charAt(l)]++;
                    ++l;
                }
                if (r - l + 1 < minLen) {
                    start = l;
                    minLen = r - l + 1;
                }
                // 此时左边界必定在 needed[i] == 0 处，必是 t 中的一个字符，模拟左边界移动一格；
                needed[s.charAt(l)]++;
                l++;
                count++;
            }
            r++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    /**
     * 84. 柱状图中最大的矩形
     * <p>
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     * <p>
     * 思路：单调栈：暴力法（时间复杂度为 N^2）遍历时发现，高度为 n 的最大矩形面积其宽度对应左右两边扫描时第一次严格小于 n 时的位置宽度；
     * 改进时间复杂度为 N：
     * 1. 借助栈，遍历至数组位置 i 时，如果当前高度小于栈顶元素（假设栈在遍历前已有一个元素 0），则弹出栈顶元素得到高度 h，接着得到宽度
     * w = i - peek() - 1 （peek()、i 分别为左、右扫描时第一次高度小于 n 的位置），然后再将 i 压入栈，继续遍历；
     * 2. 对于连续个相等高度，可以正常压入栈，在结算时（设数组最后加了一个元素 0 作为收尾）会在最后一次结算时得到正常最大值；
     */
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;

        int n = heights.length;
        int[] newHeights = new int[n + 2];
        System.arraycopy(heights, 0, newHeights, 1, n);
        newHeights[0] = newHeights[n + 1] = 0;

        Deque<Integer> deque = new LinkedList<>();
        deque.offerLast(0);
        for (int i = 1; i <= n + 1; i++) {

            while (newHeights[i] < newHeights[deque.peekLast()]) {
                int height = newHeights[deque.pollLast()];
                int width = i - deque.peekLast() - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            deque.offerLast(i);
        }

        return maxArea;
    }

    /**
     * 85. 最大矩形
     * <p>
     * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     * <p>
     * 思路：把每列 1 的个数看作高度即可把问题转化为 84. 柱状图中最大的矩形；
     * 从第一行开始，先遍历一行，如果当前元素为 1 则高度累加 1（从 0 开始），若为 0 则高度为 0；
     * 按这种顺序遍历完最后一行后得到的最大矩形面积即为答案；
     */
    public int maximalRectangle(char[][] matrix) {
        int R = matrix.length;
        if (R == 0) return 0;
        int C = matrix[0].length;
        int[] height = new int[C];
        int maxArea = 0;

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++)
                height[c] = matrix[r][c] == '1' ? height[c] + 1 : 0;
            maxArea = Math.max(maxArea, largestRectangleArea(height));
        }

        return maxArea;
    }

    /**
     * 239. 滑动窗口最大值
     * <p>
     * 给你一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口
     * 内的 k 个数字。滑动窗口每次只向右移动一位。返回滑动窗口中的最大值。
     * <p>
     * 思路：单调栈，顺序遍历，核心原理是如果新增的值比尾部元素更大时弹出尾部；
     * 而删去的值如果恰好为最大值则弹出头部；
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];

        for (int i = 0; i < k; i++) { // 形成窗口
            int curr = nums[i];
            while (!deque.isEmpty() && curr > deque.peekLast())
                deque.pollLast();
            deque.offerLast(curr);
        }
        res[0] = deque.peekFirst();

        for (int i = k; i < nums.length; i++) { // 窗口移动
            int next = nums[i];
            int pre = nums[i - k];
            if (pre == deque.peekFirst()) deque.pollFirst();
            while (!deque.isEmpty() && next > deque.peekLast())
                deque.pollLast();
            deque.offerLast(next);
            res[i - k + 1] = deque.peekFirst();
        }

        return res;
    }

    /**
     * 316. 去除重复字母
     * <p>
     * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证：
     * 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     * <p>
     * 思路：贪心队列，
     * 贪心原则：若队列尾端元素比当前元素更大（字典序更大）且在该元素之后还会存在（计数大于1），则弹出队列尾；
     * 关键：使用一个布尔辅助数组判定当前元素是否已经在队列中存在，若为真则跳过；每次遍历完一个元素后，更新计数数组；
     */
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        boolean[] exists = new boolean[26];
        Deque<Character> deque = new LinkedList<>();

        for (int i = 0; i < s.length(); i++)
            count[s.charAt(i) - 'a']++;

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if (!exists[curr - 'a']) {
                while (!deque.isEmpty() && curr < deque.peekLast() && count[deque.peekLast() - 'a'] > 0)
                    exists[deque.pollLast() - 'a'] = false;
                deque.offerLast(curr);
                exists[curr - 'a'] = true;
            }
            count[curr - 'a']--; // 更新其后对应元素出现的次数；
        }

        StringBuilder res = new StringBuilder();
        while (!deque.isEmpty())
            res.append(deque.pollFirst());

        return res.toString();
    }

    /**
     * 455. 分发饼干
     *
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
     *
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有
     * 一个尺寸 s[j]。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满
     * 足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     *
     * 思路：贪心，满足胃口前提下给予最小的饼干，排序后使用双指针实现；
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int numOfCookies = s.length;
        int numOfChildren = g.length;
        for (int i = 0, j = 0; i< numOfChildren && j < numOfCookies; i++, j++) {
            while (j < numOfCookies && g[i] > s[j]) j++;
            if (j < numOfCookies) ++count;
        }

        return count;
    }

    /**
     * 496. 下一个更大元素 I
     * <p>
     * 给定两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
     * 找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
     * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个
     * 比 x 大的元素。如果不存在，对应位置输出 -1 。
     * <p>
     * 思路：从右向左遍历，使用栈存储已遍历的数字，当需要求当前靠右较大的第一值时，先
     * 当栈顶元素较小时弹出（相当于忽略较小值）
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> deque = new LinkedList<>();
        Map<Integer, Integer> idxMap = new HashMap<>();
        int n = nums2.length;
        int[] nextBig = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int curr = nums2[i];
            idxMap.put(curr, i);
            while (!deque.isEmpty() && curr >= deque.peekFirst())
                deque.pollFirst();
            nextBig[i] = deque.isEmpty() ? -1 : deque.peekFirst();
            deque.offerFirst(curr);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++)
            res[i] = nextBig[idxMap.get(nums1[i])];

        return res;
    }

    /**
     * 503. 下一个更大元素 II
     *
     * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。
     * 数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该
     * 循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
     *
     * 思路：逆向遍历的单调栈，不过由于是环形数组，需要先加长数组（尾接一个同样的数组），然后从 2n-1 开始；
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];
        for (int i = 2 * n - 1; i >= 0; i--) {
            int curr = nums[i % n];
            while (!stack.isEmpty() && curr >= stack.peek())
                stack.pop();
            res[i % n] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(curr);
        }

        return res;
    }

    /**
     * 649. Dota2 参议院
     * <p>
     * Dota2 的世界里有两个阵营：Radiant(天辉)和 Dire(夜魇)
     * Dota2 参议院由来自两派的参议员组成。现在参议院希望对一个 Dota2 游戏里的改变作出决定。他们以一个基
     * 于轮为过程的投票进行。在每一轮中，每一位参议员都可以行使两项权利中的一项：
     * 1. 禁止一名参议员的权利：参议员可以让另一位参议员在这一轮和随后的几轮中丧失所有的权利。
     * 2. 宣布胜利：如果参议员发现有权利投票的参议员都是同一个阵营的，他可以宣布胜利并决定在游戏中的有关变化。
     * 给定一个字符串代表每个参议员的阵营。字母 “R” 和 “D” 分别代表了Radiant（天辉）和Dire（夜魇）。然
     * 后，如果有 n 个参议员，给定字符串的大小将是n。以轮为基础的过程从给定顺序的第一个参议员开始到最后一个参议
     * 员结束。这一过程将持续到投票结束。所有失去权利的参议员将在过程中被跳过。假设每一位参议员都足够聪明，会为自己的
     * 政党做出最好的策略，你需要预测哪一方最终会宣布胜利并在 Dota2 游戏中决定改变。输出应该是Radiant或Dire。
     * <p>
     * 思路：循环队列，使用两个队列分别存放两阵营发言顺序，取出两个队列头 battle
     * 先手存活至下一轮（+len，加入队列尾），后手被"淘汰"
     */
    public String predictPartyVictory(String senate) {
        int len = senate.length();
        Queue<Integer> radiant = new LinkedList<>();
        Queue<Integer> dire = new LinkedList<>();
        for (int i = 0; i < len; ++i) {
            if (senate.charAt(i) == 'R')
                radiant.offer(i);
            else dire.offer(i);
        }

        while (!radiant.isEmpty() && !dire.isEmpty()) {
            int rIdx = radiant.poll(), dIdx = dire.poll();
            if (rIdx < dIdx)
                radiant.offer(rIdx + len);
            else dire.offer(dIdx + len);
        }

        return radiant.isEmpty() ? "Dire" : "Radiant";
    }

    /**
     * 739. 每日温度
     *
     * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要
     * 等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
     *
     * 思路：next Greater 问题，使用逆序单调栈；
     */
    public int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = T.length-1; i>= 0; i--) {
            while (!stack.isEmpty() && T[i] >= T[stack.peek()])
                stack.pop();
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }

        return res;
    }

    public static void main(String[] args) {
        new Solution4().minWindow("ab", "a");
    }
}
