package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/">600. 不含连续1的非负整数</a>
 * <p>给定一个正整数 n ，请你统计在 [0, n] 范围的非负整数中，有多少个整数的二进制表示中不存在 连续的 1 。
 * <p>
 * 思路：动态规划，本质是 01 字典树的路径问题
 */
class Leetcode600NonNegativeIntegersWithoutConsecutiveOnes {
    public int findIntegers(int n) {
        int[] dp = new int[31];
        dp[0] = dp[1] = 1;
        for (int i = 2; i < 31; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        int pre = 0, res = 0;
        for (int i = 29; i >= 0; --i) {
            int val = 1 << i;
            if ((n & val) != 0) {
                res += dp[i + 1];
                if (pre == 1) {
                    break;
                }
                pre = 1;
            } else {
                pre = 0;
            }

            if (i == 0) {
                ++res;
            }
        }

        return res;
    }
}