package dynamicprogramming;

/**
 * <a href="https://labuladong.online/algo/dynamic-programming/stock-problem-summary/#%E4%B8%87%E6%B3%95%E5%BD%92%E4%B8%80">一个方法团灭 LeetCode 股票买卖问题</a>
 * <p>
 * 股票系列动态规划
 *
 * @author lbli
 */
class LeetcodeMaxProfit {

    public static void main(String[] args) {
        LeetcodeMaxProfit leetcodeMaxProfit = new LeetcodeMaxProfit();

        int result = leetcodeMaxProfit.maxProfitWith2Chances(new int[]{7, 6, 4, 3, 1});
        System.out.println(result);
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/description/">188. 买卖股票的最佳时机 IV</a>
     * <p>
     * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
     * <p>
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
     * <p>
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * 思路：状态定义 dp[i][j][0/1]=第 i 天剩余 j 次机会时 0-未持有/1-已持有 时的最大收益
     */
    public int maxProfitWithKChances(int k, int[] prices) {
        int[][][] dp = new int[prices.length + 1][k + 1][2];
        for (int day = 0; day <= prices.length; day++) {
            dp[day][k][1] = Integer.MIN_VALUE;
        }
        for (int remaining = k; remaining >= 0; remaining--) {
            dp[0][remaining][1] = Integer.MIN_VALUE;
        }


        for (int day = 1; day <= prices.length; day++) {

            for (int remaining = k - 1; remaining >= 0; remaining--) {

                int price = prices[day - 1];
                // 未持有：买入后卖出 OR 继续保持未持有
                dp[day][remaining][0] = Math.max(dp[day - 1][remaining][1] + price, dp[day - 1][remaining][0]);
                // 持有：昨天未买入今天买入 OR 保持持有
                dp[day][remaining][1] = Math.max(dp[day - 1][remaining + 1][0] - price, dp[day - 1][remaining][1]);

            }
        }

        return dp[prices.length][0][0];
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/?envType=study-plan-v2&envId=top-100-liked">121. 买卖股票的最佳时机</a>
     * <p>
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * <p>
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * <p>
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     */
    public int maxProfitWithOnlyOneChance(int[] prices) {
        // dp[i][0/1]：第 i ，0=未持有；1=持有；
        int[][] dp = new int[prices.length + 1][2];
        dp[0][1] = Integer.MIN_VALUE;

        for (int day = 1; day <= prices.length; day++) {
            int price = prices[day - 1];
            // 未持有：上一天持有今天卖掉 OR 上一天也没持有
            dp[day][0] = Math.max(dp[day - 1][1] + price, dp[day - 1][0]);
            // 持有：上一天持有今天继续持有 OR 之前没有持有今天买入
            dp[day][1] = Math.max(dp[day - 1][1], -price);

        }

        return dp[prices.length][0];
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/">122. 买卖股票的最佳时机 II</a>
     * <p>
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     * <p>
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     * <p>
     * 返回 你能获得的 最大 利润 。
     * <p>
     * 思路：不限制交易次数，那么不需要单独维度记录剩余交易次数状态
     */
    public int maxProfitWithoutLimit(int[] prices) {

        // dp[i][0/1]：第 i 天，0=未持有/1=已持有
        int[][] dp = new int[prices.length + 1][2];
        dp[0][1] = Integer.MIN_VALUE;

        for (int day = 1; day <= prices.length; day++) {
            int price = prices[day - 1];

            // 未持有：先前持有今天卖掉 OR 之前也没持有
            dp[day][0] = Math.max(dp[day - 1][1] + price, dp[day - 1][0]);
            // 持有：先前持有继续保持 OR 今天买入
            dp[day][1] = Math.max(dp[day - 1][1], dp[day - 1][0] - price);


        }

        return dp[prices.length][0];
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/">309. 买卖股票的最佳时机含冷冻期</a>
     * <p>
     * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。​
     * <p>
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * <p>
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * 思路：与 maxProfitWithoutLimit 无交易次数版本的区别在于持有态应该取前天而不是昨天的值
     */
    public int maxProfitWithGap(int[] prices) {
        // [day][0/1]: 第 day 天，0=未持有；1=已持有；
        int[][] dp = new int[prices.length + 1][2];
        dp[0][1] = Integer.MIN_VALUE;

        for (int day = 1; day <= prices.length; day++) {
            int price = prices[day - 1];

            // 未持有：先前持有今天卖掉 OR 之前也没持有
            dp[day][0] = Math.max(dp[day - 1][1] + price, dp[day - 1][0]);
            // 持有：先前持有继续保持 OR 今天买入（只能取前天未持有的值）
            dp[day][1] = day >= 2 ? Math.max(dp[day - 1][1], dp[day - 2][0] - price) : -price;

        }

        return dp[prices.length][0];
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/">714. 买卖股票的最佳时机含手续费</a>
     * <p>
     * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
     * <p>
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * <p>
     * 返回获得利润的最大值。
     * <p>
     * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     * <p>
     * 思路：买入的时候多减一个手续费即可
     */
    public int maxProfitWithFee(int[] prices, int fee) {

        // dp[i][0/1]：第 i 天，0=未持有/1=已持有
        int[][] dp = new int[prices.length + 1][2];
        dp[0][1] = Integer.MIN_VALUE;

        for (int day = 1; day <= prices.length; day++) {
            int price = prices[day - 1];

            // 未持有：先前持有今天卖掉 OR 之前也没持有
            dp[day][0] = Math.max(dp[day - 1][1] + price, dp[day - 1][0]);
            // 持有：先前持有继续保持 OR 今天买入
            dp[day][1] = Math.max(dp[day - 1][1], dp[day - 1][0] - price - fee);
        }

        return dp[prices.length][0];
    }

    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/description/">123. 买卖股票的最佳时机 III</a>
     * <p>
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * <p>
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * <p>
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    public int maxProfitWith2Chances(int[] prices) {
        return maxProfitWithKChances(2, prices);
    }


}
