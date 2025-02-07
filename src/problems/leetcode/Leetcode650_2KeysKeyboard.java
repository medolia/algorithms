package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/2-keys-keyboard/description/?envType=company&envId=microsoft&favoriteSlug=microsoft-thirty-days">650. 两个键的键盘</a>
 * <p>最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
 * <p>Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
 * <p>Paste（粘贴）：粘贴 上一次 复制的字符。
 * <p>给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。返回能够打印出 n 个 'A' 的最少操作次数。
 * <p>
 * <p>动态规划，状态转移公式：
 * <p><img src="./leetcode650_formula.png" alt="状态转移公式"></img>
 * <p>复杂度，时：O(N√N)，空：O(N)
 */
class Leetcode650_2KeysKeyboard {

    public static void main(String[] args) {
        int minSteps = new Leetcode650_2KeysKeyboard().minSteps(18);
        System.out.println(minSteps);
    }

    public int minSteps(int n) {
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                if (i % j == 0) {
                    // 从 j 开始，每次黏贴 j 个字符，共操作 i/j 次
                    dp[i] = Math.min(dp[i], dp[j] + i / j);
                    // 从 i/j 开始，每次黏贴 i/j 个字符，共操作 j 次
                    dp[i] = Math.min(dp[i], dp[i / j] + j);
                }
            }
        }

        return dp[n];
    }

}
