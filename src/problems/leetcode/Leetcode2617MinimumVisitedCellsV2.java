package problems.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <a href="https://leetcode.cn/problems/minimum-number-of-visited-cells-in-a-grid/description/?envType=company&envId=huawei&favoriteSlug=huawei-three-months">2617. 网格图中最少访问的格子数</a>
 * <p>给你一个下标从 0 开始的 m x n 整数矩阵 grid 。你一开始的位置在 左上角 格子 (0, 0) 。
 * <p>
 * 当你在格子 (i, j) 的时候，你可以移动到以下格子之一：
 * <p>
 * 满足 j < k <= grid[i][j] + j 的格子 (i, k) （向右移动），或者
 * 满足 i < k <= grid[i][j] + i 的格子 (k, j) （向下移动）。
 * 请你返回到达 右下角 格子 (m - 1, n - 1) 需要经过的最少移动格子数，如果无法到达右下角格子，请你返回 -1 。
 *
 */
class Leetcode2617MinimumVisitedCellsV2 {

    public static void main(String[] args) {
        Leetcode2617MinimumVisitedCellsV2 solution = new Leetcode2617MinimumVisitedCellsV2();
        int res = solution.minimumVisitedCellsV2(new int[][]{
                {3, 4, 2, 1},
                {4, 2, 1, 1},
                {2, 1, 1, 0},
                {3, 4, 1, 0}});
        System.out.println(res);
    }

    /**
     * 改良算法 使用优先队列
     */
    public int minimumVisitedCellsV2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int f = 0;
        PriorityQueue<int[]>[] colHeaps = new PriorityQueue[n]; // 每一列的最小堆
        Arrays.setAll(colHeaps, i -> new PriorityQueue<int[]>(Comparator.comparingInt(a -> a[0])));
        PriorityQueue<int[]> rowH = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // 行最小堆
        for (int i = 0; i < m; i++) {
            rowH.clear();
            for (int j = 0; j < n; j++) {
                while (!rowH.isEmpty() && rowH.peek()[1] < j) { // 无法到达第 j 列
                    rowH.poll(); // 弹出无用数据
                }
                PriorityQueue<int[]> colH = colHeaps[j];
                while (!colH.isEmpty() && colH.peek()[1] < i) { // 无法到达第 i 行
                    colH.poll(); // 弹出无用数据
                }

                f = i > 0 || j > 0 ? Integer.MAX_VALUE : 1; // 起点算 1 个格子
                if (!rowH.isEmpty()) {
                    f = rowH.peek()[0] + 1; // 从左边跳过来
                }
                if (!colH.isEmpty()) {
                    f = Math.min(f, colH.peek()[0] + 1); // 从上边跳过来
                }

                int g = grid[i][j];
                if (g > 0 && f < Integer.MAX_VALUE) {
                    rowH.offer(new int[]{f, g + j}); // 经过的格子数，向右最远能到达的列号
                    colH.offer(new int[]{f, g + i}); // 经过的格子数，向下最远能到达的行号
                }
            }
        }
        return f < Integer.MAX_VALUE ? f : -1; // 此时的 f 是在 (m-1, n-1) 处算出来的
    }

    /**
     * 思路1 正常遍历
     * 时空复杂度：O(MN(M+N)) O(MN)
     */
    public int minimumVisitedCells(int[][] grid) {
        int[][] stepGrid = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            Arrays.fill(stepGrid[row], -1);
        }
        stepGrid[0][0] = 1;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (stepGrid[row][col] == -1) {
                    continue;
                }

                for (int step = 1; step <= grid[row][col]; step++) {
                    if (row + step < grid.length) {
                        stepGrid[row + step][col] = stepGrid[row + step][col] == -1 ? stepGrid[row][col] + 1 :
                                Math.min(stepGrid[row + step][col], stepGrid[row][col] + 1);
                    }
                    if (col + step < grid[0].length) {
                        stepGrid[row][col + step] = stepGrid[row][col + step] == -1 ? stepGrid[row][col] + 1 :
                                Math.min(stepGrid[row][col + step], stepGrid[row][col] + 1);
                    }
                }
            }
        }

        return stepGrid[grid.length - 1][grid[0].length - 1];
    }

}
