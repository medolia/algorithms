package problems.leetcode;

/**
 * 为什么适用动态规划：对于一个终点，其较优解为上面、左边两个格子中取其优，满足局部最优推导至全局最优
 *
 * @author lbli
 */
class Leetcode64MinPathSum {

    public static void main(String[] args) {
        int result = new Leetcode64MinPathSum().minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}});
        System.out.println(result);
    }

    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == 0 && c == 0) {
                    dp[r][c] = grid[r][c];
                    continue;
                }

                int left = c == 0 ? Integer.MAX_VALUE : dp[r][c - 1];
                int up = r == 0 ? Integer.MAX_VALUE : dp[r - 1][c];
                dp[r][c] = Math.min(left, up) + grid[r][c];
            }
        }

        return dp[rows - 1][cols - 1];
    }

}
