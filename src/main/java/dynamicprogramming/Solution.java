package dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    /**
     * 剑指 Offer 14- I. 剪绳子
     * 思路：创建一个 dp 数组，保存各长度至少切一刀后的最大乘积
     * 那么择优选择池为(长度为 j)：
     * 1. 只切一刀，即直接返回乘积i*（i-j）
     * 2. 保留 j，只拆分 i-j （已包含两端均拆分的情况）
     * 3. 使用先前较优的方案
     */
    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++)
            for (int j = 1; j < i; j++)
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));

        return dp[n];
    }

    /**
     * 剑指 Offer 46. 把数字翻译成字符串
     * <p>
     * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译
     * 成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有
     * 多少种不同的翻译方法。
     * <p>
     * 思路：动态规划框架
     * 状态定义：dp[i] 表明以 i 结尾的部分有多少种翻译方法；
     * 状态初始化：dp[0] = 1，dp[1] = 1，即有无数字均为 1 种翻译方法；
     * 状态转移：dp[i] = (10 =< x[i-1]x[i]组合数 <= 25) ? dp[i-1] + dp[i-2] : dp[i-1];
     * 状态压缩：由于状态只与前两个状态相关，使用两个同步更新临时变量存储即可；
     */
    public int translateNum(int num) {
        String val = String.valueOf(num);
        int dp_i_2 = 1, dp_i_1 = 1;
        for (int i = 2; i <= val.length(); i++) {
            String tmp = val.substring(i - 2, i);
            int dp_i = tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0 ? dp_i_1 + dp_i_2 : dp_i_1;
            dp_i_1 = dp_i;
            dp_i_2 = dp_i_1;
        }

        // 从后往前遍历，对 10 求余/作除法可省去转化字符串、求子字符串的开销
        /*int a = 1, b = 1, x, y = num % 10;
        while(num != 0) {
            num /= 10;
            x = num % 10;
            int tmp = 10 * x + y;
            int c = (tmp >= 10 && tmp <= 25) ? a + b : a;
            b = a;
            a = c;
            y = x;
        }
        return a;*/

        return dp_i_1;
    }

    /**
     * 剑指 Offer 47. 礼物的最大价值
     * <p>
     * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从
     * 棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一
     * 个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
     * <p>
     * 思路：动态规划
     * 状态定义：dp[i][j] 到达 (i,j) 时能拿到的最大价值
     * 状态初始化：dp[0][0] = grid[0][0]
     * 状态转移：dp[i][j] = grid[i][j] + max(dp[i-1][j], dp[i][j-1])
     * 因为只能向右或者向下运动，所以只能从左边和上面种择优
     */
    public int maxValue(int[][] grid) {
        int R = grid.length;
        if (R == 0) return 0;
        int C = grid[0].length;

        for (int c = 1; c < C; ++c) // 更新第一行（仅能从左到达）
            grid[0][c] += grid[0][c - 1];

        for (int r = 1; r < R; ++r) // 更新第一列（仅能从上面到达）
            grid[r][0] += grid[r - 1][0];

        for (int r = 1; r < R; ++r) // 更新其他值（左、上均能到达）
            for (int c = 1; c < C; ++c)
                grid[r][c] += Math.max(grid[r - 1][c], grid[r][c - 1]);

        return grid[R - 1][C - 1];
    }

    /**
     * 剑指 Offer 60. n个骰子的点数
     * <p>
     * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
     * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
     * <p>
     * 思路：状态规划：
     * 状态定义：dp[i][j] 投完第 i 个骰子后出现和为 j 的概率
     * 状态初始化：dp[1][1~6] = 1；
     * 状态转移：设当前骰子点数为 val， dp[i][j] = dp[i-1][j-val]，即之前骰子点数和恰好与当前骰子点数相加为 j 的次数
     * 边界条件：i <= n
     * <p>
     */
    public double[] dicesProbability(int n) {
        int[][] dp = new int[n + 1][6 * n + 1];
        double all = Math.pow(6, n);
        double[] ans = new double[5 * n + 1];

        for (int i = 1; i <= 6; i++)
            dp[1][i] = 1;

        for (int diceI = 2; diceI <= n; diceI++)
            for (int sum = diceI; sum <= 6 * n; sum++)
                for (int curr = 1; curr <= 6; curr++) {
                    if (sum - curr <= 0) break;
                    dp[diceI][sum] += dp[diceI - 1][sum - curr];
                }

        for (int i = n; i <= 6 * n; i++)
            ans[i - n] = dp[n][i] / all;

        return ans;
    }

    /**
     * 剑指 Offer 63. 股票的最大利润
     * <p>
     * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
     * <p>
     * 思路：动态规划：
     * 状态定义：第 i 天时， dp[0][i] 表示未持有股票时的最大利润，dp[1][i] 表示持有股票时的最大利润；
     * 状态初始化：dp[0][0] = 0，无股票无利润，dp[1][0] = -infinity，还没开始不可能持有股票；
     * 状态转移：dp[0][i] = max(dp[0][i], dp[1][i-1] + price[i]); 可选择不操作或者卖；
     * dp[1][i] = max(dp[1][i], -price[i]); 可选择不操作或者买，由于只能买 1 次，所以需要与 -price[i] 比较；
     * 如果可以交易多次，则与 dp[0][i-1] - price[i] 比较，状态压缩时需要提前保存更新前的前一天变量；
     */
    public int maxProfit(int[] prices) {
        int dp_0_i = 0, dp_1_i = Integer.MIN_VALUE;
        for (int price : prices) {
            dp_0_i = Math.max(dp_0_i, dp_1_i + price);
            dp_1_i = Math.max(dp_1_i, -price);
        }

        return dp_0_i;
    }

    /**
     * 5. 最长回文子串
     * <p>
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * <p>
     * 思路：动态规划；
     * 状态定义：dp[i][j] j - i 是否为回文串；
     * 状态转移：dp[i][j] 仅在下列条件中为真：
     * 1. j = i；
     * 2. s[j] = s[i] AND (j = i-1 OR dp[i-1][j+1] = true)；
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len + 1][len + 1];
        int maxLen = 0;
        StringBuilder res = new StringBuilder();

        for (int i = 1; i <= len; i++)
            for (int j = i; j >= 1; j--) {
                dp[i][j] = j == i || s.charAt(i - 1) == s.charAt(j - 1) && (j == i - 1 || dp[i - 1][j + 1]);
                if (dp[i][j] && i - j + 1 > maxLen) {
                    res = new StringBuilder(s.substring(j - 1, i));
                    maxLen = i - j + 1;
                }
            }

        return res.toString();
    }

    /**
     * 10. 正则表达式匹配
     * <p>
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * <p>
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖整个字符串s的，而不是部分字符串。
     * <p>
     * 状态规划：dp[i][j] 表明 s[:i] 与 p[:j] 是否匹配；
     * 状态转移：
     * 1. p[j] 不为 "*"：dp[i][j] = dp[i-1][j-1] && (s[i] = p[j] OR p[j] == ".")；
     * 2. p[j] 为 "*"；当且仅当下列三种情况(根据 * 前字符的出现次数)任意为真时 dp[i][j] 为真：
     * 2.1：* 前字符不出现：dp[i][j-2]
     * 2.2：* 前字符只出现 1 次：dp[i][j-1]
     * 2.3：* 前字符出现多次：(p[j-1] == s[i] OR p[j-1] == ".") AND dp[i-1][j]
     * 状态初始化（关键）：
     * 1. dp[0][0] = true，空串只能匹配空串；
     * 2. dp[0][j = 2~pLen] = dp[0][j-2] && p[j] == "*"；只需看偶数位是否为 * 即可；
     */
    public boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        dp[0][0] = true; // 初始化 1
        for (int j = 2; j <= pLen; j += 2)
            dp[0][j] = dp[0][j - 2] && p.charAt(j - 1) == '*'; // 初始化 2

        for (int i = 1; i <= sLen; i++)
            for (int j = 1; j <= pLen; j++)
                dp[i][j] = p.charAt(j - 1) != '*' ?
                        dp[i - 1][j - 1] && (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1)) :
                        // * 前字符：不出现 ｜｜ 出现一次 ｜｜ 出现多次
                        dp[i][j - 2] || dp[i][j - 1] || dp[i - 1][j] && (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.');

        return dp[sLen][pLen];
    }

    /**
     * 32. 最长有效括号
     * <p>
     * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     * <p>
     * 状态定义：dp[i] 为以位置 i 为结尾的字符串有效括号的最长长度，转化为求 max(dp[i])；
     * 状态转移：
     * 1. 若 i 为 "(" 显然 dp[i] = 0；当 i 为 ")" 时：
     * 2.1. 若 i-1 为 "("：dp[i] = dp[i-2] + 2；
     * 2.2. 若 i-1 为 ")"：需要考虑 i' = i-1-dp[i-1]，即 dp[i-1] 串的左边第一个字符；
     * i' 须为 "(" dp[i] 才不为 0，dp[i] = 2 + dp[i-1] + dp[i-1-dp[i-1]-1]；
     */
    public int longestValidParentheses(String s) {
        int maxans = 0;
        int[] dp = new int[s.length()];

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }

        return maxans;
    }

    /**
     * 53. 最大子序和
     * <p>
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 思路：状态规划
     * 状态定义：dp[0][i] 以 i 为结尾的最大连续子数组和；dp[1][i] 到 i 为止最大的连续子数组和；
     * 状态迁移：dp[0][i] = max(dp[0][i-1] + nums[i], nums[i])，即根据前面子数组纸鹤是否有贡献决定是否抛弃；
     * dp[1][i] = max(dp[1][i-1], dp[0][i])，即择优；
     * 状态初始化：dp[0][-1] = 0，dp[1][-1] = -infinity;
     */
    public int maxSubArray(int[] nums) {
        int dp_0_i = 0, dp_1_i = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            dp_0_i = Math.max(nums[i], dp_0_i + nums[i]);
            dp_1_i = Math.max(dp_1_i, dp_0_i);
        }

        return dp_1_i;
    }

    /**
     * 72. 编辑距离
     * <p>
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数。
     * 你可以对一个单词进行如下三种操作：1.插入一个字符 2.删除一个字符 3.替换一个字符
     * <p>
     * 思路：动态规划，使用自底向顶的迭代消除自顶向底带来的重叠子问题；
     * 状态定义：dp[i][j] word1 的前 i 个字符转换为 word2 的前 j 个字符需要的最小编辑距离（操作数）；
     * 状态初始化：dp[0][0] = 0; dp[0][1~len2] = j; dp[1-len1][0] = i;
     * 即当一方字符串为空时，另一方需要删除/插入所有字符；
     * 状态转移：
     * word1[i] == word2[j]，dp[i][j] = dp[i-1][j-1]，即直接跳过这一字符，两个指针均向前一步；
     * word1[i] != word2[j]，这时候有三种操作，取其较优解：
     * 1. word1 删除 word1[i]，则 dp[i][j] = dp[i-1][j] + 1，即 word1 指针前一步，word2 指针不动，操作数 + 1；
     * 2. word1 插入 word2[j]，则 dp[i][j] = dp[i][j-1] + 1，即 word1 指针不动，word2 指针前一步，操作数 + 1；
     * 3. word1[i] 替换 word2[j]，则 dp[i][j] = dp[i-1][j-1] + 1, 即两指针均向前一步，操作数 + 1；
     * <p>
     * 启发：1. 自底向顶的动态规划可以消除自顶向底带来的重叠子问题；
     * 2. 两字符串相互转化相关问题需要考虑两字符串从尾端逆序前进的两个指针，根据指针指向确定状态转移方程；
     * <p>
     * 状态压缩（可选）：注意到状态矩阵中，每个状态只与左、右、左上三个状态有关；
     */
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int j = 1; j <= len2; j++)
            dp[0][j] = j;

        for (int i = 1; i <= len1; i++)
            dp[i][0] = i;

        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++)
                dp[i][j] = word1.charAt(i - 1) == word2.charAt(j - 1) ?
                        dp[i - 1][j - 1] : Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;

        return dp[len1][len2];
    }

    /**
     * 122. 买卖股票的最佳时机 II
     * <p>
     * 思路见：剑指 Offer 63. 股票的最大利润
     */
    public int maxProfit2(int[] prices) {
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;

        for (int i = 0; i < prices.length; i++) {
            int temp = dp_i_0; // 更新今日收益前暂存上一天未持有时的收益
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i]);
        }

        return dp_i_0;
    }

    /**
     * 123. 买卖股票的最佳时机 III
     * <p>
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * 解析见 188，此题对应 k = 2；
     */
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][] dp_0 = new int[n + 1][3], dp_1 = new int[n + 1][3];

        for (int j = 0; j <= 2; j++)
            dp_1[0][j] = -100000;
        for (int i = 0; i <= n; i++)
            dp_1[i][2] = -100000;

        for (int i = 1; i <= n; i++)
            for (int j = 1; j >= 0; j--) {
                dp_0[i][j] = Math.max(dp_0[i - 1][j], dp_1[i - 1][j] + prices[i - 1]);
                dp_1[i][j] = Math.max(dp_1[i - 1][j], dp_0[i - 1][j + 1] - prices[i - 1]);
            }

        return dp_0[n][0];
    }

    /**
     * 188. 买卖股票的最佳时机 IV
     * <p>
     * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * 状态定义：dp_0[i][j]、dp_1[i][j] 分别为 第 i 天后还剩 j 次交易次数时未持有、持有股票时的最大利润；
     * 题所求变为 dp_0[n][0]；
     * 状态转移：第 i 天时需要作出选择来择优：使得 dp_0[i-1] 和 dp_1[i-1] 过渡到 dp_0[i] 和 dp_1[i-1]；
     * 1. 第 i 天后未持有股票，需要在选择 继续观望 和 卖掉股票 之间择优：
     * dp_0[i][j] = max(dp_0[i-1][j], dp_1[i-1][j] + prices[i])；
     * 2. 第 i 天后持有股票，需要在选择 继续持股 和进行一次股票 买进 交易之间择优，注意后者需要扣除一次交易次数：
     * dp_1[i][j] = max(dp_1[i-1][j], dp_0[i-1][j+1] - prices[i])；
     * 状态初始化：
     * 未开始时、交易次数没有扣除时，未持有状态收益一定为 0，而持有状态是非法的，收益为负无穷；
     * dp_0[0][0～k] = 0；dp[0~n][k] = 0；dp_1[0][0~k] = -infinity dp_1[0~n][k] = -infinity；
     */
    public int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        int[][] dp_0 = new int[n + 1][k + 1], dp_1 = new int[n + 1][k + 1];

        for (int j = 0; j <= k; j++)
            dp_1[0][j] = -100000;
        for (int i = 0; i <= n; i++)
            dp_1[i][k] = -100000;

        for (int i = 1; i <= n; i++)
            for (int j = k - 1; j >= 0; j--) {
                dp_0[i][j] = Math.max(dp_0[i - 1][j], dp_1[i - 1][j] + prices[i - 1]);
                dp_1[i][j] = Math.max(dp_1[i - 1][j], dp_0[i - 1][j + 1] - prices[i - 1]);
            }

        /*int res = 0;
        for (int j = 0; j<=k; j++)
            res = Math.max(res, dp_0[n][j]);

        return res;*/

        return dp_0[n][0];
    }

    /**
     * 198. 打家劫舍
     * <p>
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相
     * 邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 思路：动态规划；
     * 状态定义：dp[i] 表示经过第 i 个房屋后能得到的最大现金；
     * 状态转移：dp[i] = max(dp[i-1], dp[i-2] + nums[i]) 在前一天动手与不动手之间取较优解；
     * 状态初始化：dp[-2] =0，dp[-1] =0，即未开始时并没有获得任何价值；
     */
    public int rob(int[] nums) {
        int dp_i_1 = 0, dp_i_2 = 0;
        int dp_i = 0;

        for (int i = 0; i < nums.length; i++) {
            dp_i = Math.max(dp_i_1, dp_i_2 + nums[i]);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }

        return dp_i;
    }

    /**
     * 213. 打家劫舍 II
     * <p>
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一
     * 圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果
     * 两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，能够偷窃到的最高金额。
     * <p>
     * 思路：
     * 1. 如果房屋数小于等于 1，则是否成环不会影响结果。
     * 2. 如果房屋数大于 1，只能选择从 0 至 i-1 或者 1 至 i，两个取较优。
     * 即 i > 1 时，res = max(f(0, i-1), f(1, i))；状态相关条件、定义、初始化与无环时相同；
     */
    public int rob2(int[] nums) {
        if (nums.length == 1) return nums[0];
        int dp_i_1 = 0, dp_i_2 = 0;
        int dp_i = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            dp_i = Math.max(dp_i_1, dp_i_2 + nums[i]);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }

        int dp_head = dp_i;
        dp_i = 0;
        dp_i_1 = 0;
        dp_i_2 = 0;
        for (int i = 1; i < nums.length; i++) {
            dp_i = Math.max(dp_i_1, dp_i_2 + nums[i]);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }

        return Math.max(dp_head, dp_i);
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     * <p>
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格。
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * <p>
     * 思路：多了冷冻期这一设定，原有经典题目里的状态机不再适用，须修改为包含 持有、未持有冷冻、未持有未冷冻 三种状态；
     * 状态定义：dp_0_0[i] dp_0_1[i] dp_1[i] 分别为第 i 天后 未持有未冷冻、未持有冷冻、持有 三种状态；
     * 状态转移：
     * 未持有未冷冻 可以由未持有冷冻和未持有未冷冻两种状态过度而来：dp_0_0[i] = max(dp_0_0[i-1], dp_0_1[i-1])；
     * 未持有冷冻 仅能由前一天持有股票的状态过度而来：dp_0_1[i] = dp_1[i-1] + prices[i]；
     * 持有 可以由持有和未持有未冷冻两种状态过度而来：dp_1[i] = max(dp_1[i-1], dp_0_0[i-1] - prices[i])；
     * 状态初始化：未开始时：未持有无论是否冷冻收益均为0，而持有是非法的为负无穷；
     * 状态压缩：注意到全部状态仅与前一天的状态有关，可以先用临时变量保存前一天的状态再更新今天的状态；
     * <p>
     * 思路2：第 i 天持有股票状态只能从第 i-2 天的未持有状态转移而来，转移方程变为：
     * dp_1[i] = max(dp_1[i-1], dp_0[i-2] + prices[i])；dp_0[i] 与经典方程一致；
     */
    public int maxProfit5(int[] prices) {
        /*int dp_0_0 = 0, dp_0_1 = 0, dp_1 = -1000000;
        int pre_0_0, pre_0_1;
        for (int i = 0; i < prices.length; i++) {
            pre_0_0 = dp_0_0;
            pre_0_1 = dp_0_1;

            dp_0_1 = dp_1 + prices[i];
            dp_0_0 = Math.max(dp_0_0, pre_0_1);
            dp_1 = Math.max(dp_1, pre_0_0 - prices[i]);
        }

        return Math.max(dp_0_0, dp_0_1);*/

        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        int dp_0_pre_pre = 0; // 代表 dp[i-2][0]
        for (int i = 0; i < n; i++) {
            int dp_0_pre = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, dp_0_pre_pre - prices[i]);
            dp_0_pre_pre = dp_0_pre;
        }
        return dp_i_0;
    }

    /**
     * 312. 戳气球
     * <p>
     * 有 n 个气球，编号为0 到 n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
     * 现在要求你戳破所有的气球。如果你戳破气球 i ，就可以获得 nums[left] * nums[i] * nums[right] 个硬币。
     * 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right
     * 就变成了相邻的气球。
     * 你可以假设 nums[-1] = nums[n] = 1，但注意它们不是真实存在的所以并不能被戳破。
     * 求所能获得硬币的最大数量。
     * <p>
     * 思路：新建一个长度为 n + 1 的数组，结构为在原数组的左右新增一个值为 1 的元素作为虚拟气球；
     * 状态定义 dp[l][r] 为在新数组开区间 (l, r) 中能得到的最大硬币数量，则题所求变为 dp[0][n+1]；
     * 状态转移：为避免重叠子问题，定义一个 mid 处于开区间 (l, r) 中为 最后一个 被戳破的气球；
     * 则转移方程为 dp[l][r] = max(dp[l][r], dp[l][mid] + dp[mid][r] + tmp[l]*tmp[mid]*tmp[r])；
     * 遍历顺序：l 从 n+1 至 0（逆序），r 从 l + 1 至 n + 1（顺序），mid 从 l + 1 至 r - 1（顺序，其实逆序也可以）；
     * 状态初始化：由于 java 数组元素默认为 0，无需特意初始化；
     * <p>
     * 启发：可以从所求推导得到遍历顺序，所求为 dp[0][n+1]，即 l、r 的遍历终点分别为 0 和 n + 1；
     * 此题关键是状态定义两个状态是左右开区间，转移时以最后戳破的气球为遍历值；
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] tmp = new int[n + 2];
        tmp[0] = 1;
        tmp[n + 1] = 1;
        System.arraycopy(nums, 0, tmp, 1, n);

        int[][] dp = new int[n + 2][n + 2];

        for (int l = n + 1; l >= 0; l--)
            for (int r = l + 1; r <= n + 1; r++)
                for (int mid = l + 1; mid < r; mid++)
                    dp[l][r] = Math.max(dp[l][r], dp[l][mid] + dp[mid][r] + tmp[l] * tmp[mid] * tmp[r]);

        return dp[0][n + 1];
    }

    /**
     * 376. 摆动序列
     * <p>
     * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可
     * 能是正数或负数。少于两个元素的序列也是摆动序列。例如，[1,7,4,9,2,5] 是一个摆动序列，因为差值 (
     * 6,-3,5,-7,3)是正负交替出现的。相反, [1,4,7,2,5]和[1,7,4,5,5] 不是摆动序列，第一个序列是因
     * 为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。给定一个整数序列，返回作为摆动序列
     * 的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原
     * 始顺序。
     * <p>
     * 思路：受最长上升子序列启发，使用贪心算法，up_i、down_i 分别为到 i 为止最长的结尾为上升态或下降态
     * 的队列长度，当遇到峰时（[i] > [i-1]）更新up_i，遇到谷（[i] < [i-1]）时更新down_i，下列证明：
     * <p>
     * 推导1：记下降态的结尾元素为 j（j <= i），j < i 时，j - i 必定是递增的，反证：若为递减，则 j = i
     * 若为波动，则一定能找到一条更长的下降态序列，得证 [j] < [i]
     * 推导2：过程中 down、up 的差值不大于 1，自增的那个值必定比另一个大，依此省去比较过程
     * <p>
     * 启发：最长xx序列优先尝试使用动态规划和贪心算法，遍历过程中分遇到峰、谷两种情况对状态的影响
     */
    public int wiggleMaxLength(int[] nums) {
        int len = nums.length;
        if (len <= 1) return len;

        int up_i = 1, down_i = 1;
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i - 1])
                up_i = down_i + 1;
            else if (nums[i] < nums[i - 1])
                down_i = up_i + 1;
        }

        return Math.max(up_i, down_i);
    }

    /**
     * 416. 分割等和子集
     * <p>
     * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * <p>
     * 思路：转化为总容量为数组元素和的一半的 子集背包 问题；
     * 状态定义：dp[i][j] 至 i 个物品时是否存在总价值为 j 的组合；
     * 状态转移：1. j < nums[i] 容量不足，即第 i 个物品不可选，dp[i][j] = dp[i-1][j]；
     * 2. j >= nums[i]，可以装下，物品 i 可选可不选 dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i]]；
     * 状态初始化：dp[0~len][0] = true；一开始没有物品时及之后的所有选择都包含 不拿任何物品 这一选项；
     * <p>
     * 状态压缩：dp[i] 只与 dp[i-1] 有关，可以将 j 从最大值遍历，这样每次更新值后不会被覆盖；
     */
    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums)
            total += num;
        if (nums.length < 2 || total % 2 != 0) return false;
        total /= 2;

        /*int len = nums.length;
        boolean[][] dp = new boolean[len + 1][total + 1];
        for (int i = 0; i <= len; i++)
            dp[i][0] = true; // 可以不取数，即总价值为 0；

        // i 为第 i 个物品，j 为总容量；
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= total; j++) {
                if (j < nums[i - 1]) // 容量不足
                    dp[i][j] = dp[i - 1][j];
                else dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
            }
        }

        return dp[len][total];*/

        boolean[] dp = new boolean[total + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++)
            for (int j = total; j >= 0; j--) {
                if (j >= nums[i])
                    dp[j] = dp[j] || dp[j - nums[i]];
            }

        return dp[total];
    }

    /**
     * 516. 最长回文子序列
     * <p>
     * 给定一个字符串 s ，找到其中最长的回文子序列，并返回该序列的长度。可以假设 s 的最大长度为 1000 。
     * <p>
     * 思路：动态规划，状态定义 dp[i][j]：字符串第 j 至 第 i 个字符间最长的回文子序列
     * 即：dp[i][j] 在计算回文子序列时考虑到了第 j 至 第 i 个字符；
     * 状态转移：若第 i 个字符与第 j 个字符相等，那么他们一定出现在结果中：dp[i][j] = dp[j+1][i-1] + 2；
     * 若其不相等，则两字符必不可能同时出现在结果回文子序列中 (只需考虑一个)，dp[i][j] = max(dp[i-1][j], dp[i][j+1]);
     * 状态初始化：dp[1][1] dp[2][2] ... dp[len][len] 均为 1；
     */
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len + 1][len + 1];
        for (int i = 1; i <= len; i++)
            dp[i][i] = 1;

        for (int i = 1; i <= len; i++)
            for (int j = i - 1; j >= 1; j--)
                dp[i][j] = s.charAt(i - 1) == s.charAt(j - 1) ?
                        dp[i - 1][j + 1] + 2 : Math.max(dp[i - 1][j], dp[i][j + 1]);

        return dp[len][1];
    }

    /**
     * 518. 零钱兑换 II
     * <p>
     * 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
     * <p>
     * 思路：完全背包问题；
     * 状态定义：dp[i][j] 为至 i 个物品组合价值恰为 j 的组合数；
     * 状态转移 1. j < nums[i] 第 i 个物品不可选，dp[i][j] = dp[i-1][j]
     * 2. j >= nums[i] 第 i 个物品可选，当选用时有 dp[i][j-nums[i-1]] 种组合，不选用时有 dp[i-1][j] 种组合
     * 则 dp[i][j] = dp[i-1][j] + dp[i][j-nums[i-1]]；
     * 状态初始化：dp[0~len][0] = 1，目标价值为 0 时，什么都不拿就行，1 种组合。
     */
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for (int i = 0; i <= n; i++)
            dp[i][0] = 1;

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= amount; j++) {
                int coin = coins[i - 1];
                if (j < coin)
                    dp[i][j] = dp[i - 1][j];
                else dp[i][j] = dp[i - 1][j] + dp[i][j - coin];
            }

        return dp[n][amount];
    }

    /**
     * 583. 两个字符串的删除操作
     * <p>
     * 给定两个单词 word1 和 word2，找到使得 word1 和 word2 相同所需的最小步数;
     * 每步可以删除任意一个字符串中的一个字符。
     */
    public int minDistance583(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++)
            dp[i][0] = i;
        for (int j = 1; j <= len2; j++)
            dp[0][j] = j;

        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++)
                dp[i][j] = word1.charAt(i - 1) == word2.charAt(j - 1) ?
                        dp[i - 1][j - 1] : Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;

        return dp[len1][len2];
    }

    /**
     * 712. 两个字符串的最小ASCII删除和
     * <p>
     * 给定两个字符串s1, s2，找到使两个字符串相等所需删除字符的ASCII值的最小和。
     */
    public int minimumDeleteSum(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++)
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        for (int j = 1; j <= len2; j++)
            dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);

        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++)
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) ?
                        dp[i - 1][j - 1] : Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));

        return dp[len1][len2];
    }

    /**
     * 714. 买卖股票的最佳时机含手续费
     * <p>
     * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数fee 代表了交易股票的手续费用。
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * 返回获得利润的最大值。
     * <p>
     * 思路：动态规划，手续费用在卖出时支付；
     */
    public int maxProfit(int[] prices, int fee) {
        int dp_0_i = 0, dp_1_i = -100000;

        for (int i = 0; i < prices.length; i++) {
            int pre_0_i = dp_0_i;
            dp_0_i = Math.max(dp_0_i, dp_1_i + prices[i] - fee);
            dp_1_i = Math.max(dp_1_i, pre_0_i - prices[i]);
        }

        return dp_0_i;
    }

    /**
     * 877. 石子游戏
     * <p>
     * 亚历克斯和李用几堆石子在做游戏。偶数堆石子排成一行，每堆都有正整数颗石子 piles[i] 。
     * 游戏以谁手中的石子最多来决出胜负。石子的总数是奇数，所以没有平局。
     * 亚历克斯和李轮流进行，亚历克斯先开始。 每回合，玩家从行的开始或结束处取走整堆石头。 这种情况一直持续到没有
     * 更多的石子堆为止，此时手中石子最多的玩家获胜。
     * 假设亚历克斯和李都发挥出最佳水平，当亚历克斯赢得比赛时返回 true ，当李赢得比赛时返回 false 。
     * <p>
     * 状态定义：起始索引 i ，终止索引 j；dp_f[i][j]、dp_s[i][j] 分别为 i 至 j 先手、后手能获得的最高分；
     * 此时所求变为 dp_f[0][n - 1] - dp_s[0][n - 1] 是否大于 0；
     * 状态转移：先手面对 i 至 j 的石头堆只有两种选择：
     * 1. 取最右边的石头堆：即得到 i 至 j-1 时后手的最高分加上 j 石头的分数：dp_s[i][j-1] + piles[j]；
     * 2. 取最左边的石头堆：同理，dp_s[i+1][j] + piles[i]；
     * 在两种选择择优即可： dp_f[i][j] = max(1, 2);
     * 而后手的状态转移随先手变化，即先手取完较优解后，后手状态即为对应前一状态的先手：
     * 例 1 > 2 时，dp_f[i][j] = dp_s[i][j-1] + piles[j]，则 dp_s[i][j] = dp_f[i][j-1]；
     * 状态初始化：当 i = j = m 时，dp_f = piles[m]，dp_s = 0，即先手直接拿，后手没得拿；
     * 状态遍历顺序：设石子数为 4 即索引范围为 0 ～ 3：
     * 顺序为 0,0 1,1 2,2 3,3 --> 0,1 1,2 2,3 --> 0,2 1,3 --> 0,3
     * 即状态需要从状态矩阵的主对角线向右上方向遍历，遍历索引需要细心规划；
     * 注意到左右边界差由 0 递增为 3，故考虑以左右边界差作为外循环递增，左边界为内循环递增遍历；
     */
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp_f = new int[n][n], dp_s = new int[n][n];

        for (int i = 0; i < n; i++)
            dp_f[i][i] = piles[i];

        for (int l = 1; l < n; l++)
            for (int i = 0; i < n - l; i++) {
                int j = i + l;
                int left = piles[i] + dp_s[i + 1][j];
                int right = piles[j] + dp_s[i][j - 1];
                if (left > right) {
                    dp_f[i][j] = left;
                    dp_s[i][j] = dp_f[i + 1][j];
                } else {
                    dp_f[i][j] = right;
                    dp_s[i][j] = dp_f[i][j - 1];
                }
            }

        return dp_f[0][n - 1] - dp_s[0][n - 1] > 0;
    }

    /**
     * 887. 鸡蛋掉落
     * <p>
     * 你将获得K个鸡蛋，并可以使用一栋从1到N共有 N层楼的建筑。
     * 每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。
     * 你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F的楼层落下的鸡蛋都会碎，从F楼层或比它低的
     * 楼层落下的鸡蛋都不会破。
     * 每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层X扔下（满足1 <= X <= N）。
     * 你的目标是确切地知道 F 的值是多少。
     * 无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？
     * <p>
     * 状态定义：dp(K, N) 表示当前有 K 个鸡蛋，需要面对 N 层楼；
     * 状态转移：设当前楼层为 i，鸡蛋抛出后只有两种结果：
     * 1. 鸡蛋碎裂：这时候如果要寻找 F，其一定在 1 ～ i-1 中，对应 dp(K-1, i-1)；
     * 2. 鸡蛋完好：这时候 F 一定在 i + 1 至 N 中，对应 dp(K, N-i)；
     * dp(K, N) 需要在两种状态中取最大值（最坏情况）再 + 1（对应本次抛出）；
     * 即：dp(K, N) = min (max(dp(K-1, i-1), dp(K, N-i))) + 1， i 从 1 至 N 遍历；
     * 优化遍历：注意到随着 i 增大，碎裂对应 dp(i) 值一定递增，而完好对应 dp(i) 值一定递减；
     * 线性可转化为寻找山谷中的谷位置，即使用二分查找；
     * 状态初始化：K = 1 时 dp = N，即只能线性遍历；N = 0 时，dp = 0，由于 0 =< F <= N
     * 不用移动就能确定 F = 0；
     * <p>
     * 启发：使用备忘录可消除部分重复计算，当键涉及多个变量时考虑使用表达式；
     */
    public int superEggDrop(int K, int N) {
        return dp(K, N);
    }

    Map<Integer, Integer> memo = new HashMap();

    public int dp(int K, int N) {
        if (!memo.containsKey(N * 100 + K)) {
            int ans = Integer.MAX_VALUE;
            if (N == 0) {
                ans = 0;
            } else if (K == 1) {
                ans = N;
            } else {
                int lo = 1, hi = N;
                while (lo <= hi) {
                    int mid = (lo + hi) / 2;
                    int broken = dp(K - 1, mid - 1);
                    int not_broken = dp(K, N - mid);

                    if (broken > not_broken) {
                        hi = mid - 1;
                        ans = Math.min(ans, broken + 1);
                    } else {
                        lo = mid + 1;
                        ans = Math.min(ans, not_broken + 1);
                    }
                }
            }

            memo.put(N * 100 + K, ans);
        }

        return memo.get(N * 100 + K);
    }

    /**
     * 1143. 最长公共子序列
     * <p>
     * 给定两个字符串text1 和text2，返回这两个字符串的最长公共子序列的长度。
     * 一个字符串的子序列是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字
     * 符（也可以不删除任何字符）后组成的新字符串。例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是
     * "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
     * 若这两个字符串没有公共子序列，则返回 0。
     * <p>
     * 思路：经典的两字符串指针状态相关问题；
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length(), len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        dp[0][0] = 0;
        for (int i = 1; i <= len1; i++)
            dp[i][0] = 0;
        for (int j = 1; j <= len2; j++)
            dp[0][j] = 0;

        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++)
                dp[i][j] = text1.charAt(i - 1) == text2.charAt(j - 1) ?
                        dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);

        return dp[len1][len2];
    }

    public static void main(String[] args) {
        //new Solution().longestIncreasingPath(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}});
    }
}
