package stackAndQueue;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/jump-game-vi/description/">1696. 跳跃游戏 VI</a>
 * <p>
 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
 * <p>
 * 一开始你在下标 0 处。每一步，你最多可以往前跳 k 步，但你不能跳出数组的边界。也就是说，你可以从下标 i 跳到 [i + 1， min(n - 1, i + k)] 包含 两个端点的任意位置。
 * <p>
 * 你的目标是到达数组最后一个位置（下标为 n - 1 ），你的 得分 为经过的所有数字之和。
 * <p>
 * 请你返回你能得到的 最大得分 。
 * <p>
 * 思路：转化为求滑动窗口的最大值问题
 * 时：O(N)；空：O(N)
 *
 * @author lbli
 */
class Leetcode1696MaxResult {

    public static void main(String[] args) {
        int result = new Leetcode1696MaxResult().maxResult(new int[]{10, -5, -2, 4, 0, 3}, 3);
        System.out.println(result);
    }

    public int maxResult(int[] nums, int k) {

        int[] dp = new int[nums.length];
        // 单调双端队列存储窗口索引，头部为当前窗口最大值
        Deque<Integer> deque = new ArrayDeque<>();

        // 0. 初始化窗口
        deque.offer(0);
        // dp[i] 为索引 i 处可获得的最大得分
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {

            // 1. 压缩窗口
            while (deque.peekFirst() < i - k) {
                deque.pollFirst();
            }

            // 2. 状态转移
            dp[i] = nums[i] + dp[deque.peekFirst()];

            // 3. 当窗口滑动时，比较当前值与队列尾部值，尾部值较小则弹出
            while (!deque.isEmpty() && dp[i] >= dp[deque.peekLast()]) {
                deque.pollLast();
            }
            // 新元素入队，窗口滑动完成
            deque.offerLast(i);
        }


        return dp[nums.length - 1];
    }

}
