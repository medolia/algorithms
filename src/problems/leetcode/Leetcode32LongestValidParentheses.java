package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/longest-valid-parentheses/description/?envType=study-plan-v2&envId=top-100-liked">32. 最长有效括号</a>
 * <p>
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * <p>
 * 思路：动态规划，状态：dp[i] 以第 i 个字符结尾的最长有效括号子串长度
 * <p>时：O(N)，空：O(N)
 *
 * @author lbli
 */
class Leetcode32LongestValidParentheses {

    public static void main(String[] args) {
        int result = new Leetcode32LongestValidParentheses().longestValidParentheses(")()())");
        System.out.println(result);
    }

    public int longestValidParentheses(String s) {

        // dp[i] 以字符串第 i 个字符结尾的最长连续子串长度
        int[] dp = new int[s.length() + 1];

        int result = 0;
        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);

            if (c != ')') {
                continue;
            }

            int possiblePairI = i - dp[i - 1] - 1;
            if (possiblePairI > 0 && s.charAt(possiblePairI - 1) == '(') {
                // 三部分，此次的括号对 + 被两个索引隔开的两部分
                dp[i] = 2 + dp[i - 1] + dp[possiblePairI - 1];
                result = Math.max(result, dp[i]);
            }
        }

        return result;
    }

}
