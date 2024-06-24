package stackAndQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/sliding-window-maximum/description/">239. 滑动窗口最大值</a>
 * <p>
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * <p>
 * 返回 滑动窗口中的最大值 。
 * <p>
 * 思路：经典滑动窗口，维护一个单调队列
 * <p>时：使用双端队列保持有序相较优先队列可优化时间复杂度 O(NlogN) -> O(N)；空：O(N)
 *
 * @author lbli
 */
class Leetcode239MaxSlidingWindow {

    public static void main(String[] args) {
        int[] result1 = new Leetcode239MaxSlidingWindow().maxSlidingWindow(new int[]{1, -1}, 1);
        System.out.println(Arrays.toString(result1));

        int[] result = new Leetcode239MaxSlidingWindow().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        System.out.println(Arrays.toString(result));
    }

    @SuppressWarnings("all")
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        // rightBoundRes[i]=滑动窗口右边界为 i 时的最大值
        int[] rightBoundRes = new int[nums.length];

        // 初始化窗口
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            rightBoundRes[i] = nums[deque.peekFirst()];
        }

        // 移动窗口
        for (int i = k; i < nums.length; i++) {
            // 1. 缩短窗口至增加当前元素后窗口尺寸正好为 k
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 2. 移除肯定没用的较小尾部元素
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            rightBoundRes[i] = nums[deque.peekFirst()];
        }

        int[] res = new int[nums.length - k + 1];
        System.arraycopy(rightBoundRes, k - 1, res, 0, nums.length - k + 1);
        return res;
    }

}
