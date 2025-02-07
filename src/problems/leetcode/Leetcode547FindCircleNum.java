package problems.leetcode;

import libs.UnionFind;

/**
 * <a href="https://leetcode.cn/problems/number-of-provinces/description/">547. 省份数量</a>
 * <p>
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * <p>
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * <p>
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 * <p>
 * 返回矩阵中 省份 的数量。
 * <p>
 * 思路：经典并查集，当然也可以使用 dfs
 * 时：O(N^2)；空：O(N)
 */
class Leetcode547FindCircleNum {

    public static void main(String[] args) {
        int circleNum = new Leetcode547FindCircleNum().findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}});
        System.out.println(circleNum);
    }

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;

        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.connect(i, j);
                }
            }
        }

        return unionFind.getIslandCount();
    }

}
