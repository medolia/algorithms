package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/unique-paths-ii/description/">63. 不同路径 II</a> <br>
 * 题目描述:
 * 给定一个 m x n 的整数数组 grid。一个机器人初始位于 左上角（即 grid[0][0]）。机器人尝试移动到 右下角（即 grid[m - 1][n - 1]）。机器人每次只能向下或者向右移动一步。
 * <p>
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。机器人的移动路径中不能包含 任何 有障碍物的方格。
 * <p>
 * 返回机器人能够到达右下角的不同路径数量。
 * <p>
 * 测试用例保证答案小于等于 2 * 10^9。
 * <p>
 * 思路: 动态规划，每个格子的可能路径数等于它左边和上面（如果存在）的格子路径数之和，
 * <p>转移公式：dp[i][j] = dp[i-1][j] + dp[i][j-1]
 *
 * <p>时间复杂度:O(M * N) 一次遍历即可
 * <p>空间复杂度:O(M * N)
 */
class Leetcode63UniquePathsWithObstacles {

    public static void main(String[] args) {
        int res = new Leetcode63UniquePathsWithObstacles().uniquePathsWithObstacles(new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}});
        System.out.println(res);

    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];

        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {

                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }

                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                    continue;
                }

                int fromL = 0, fromR = 0;
                if (i > 0) {
                    fromL = dp[i - 1][j];
                }
                if (j > 0) {
                    fromR = dp[i][j - 1];
                }
                dp[i][j] = fromL + fromR;
            }
        }

        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

}
