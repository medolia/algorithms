package problems.leetcode;

import libs.TreeNode;

/**
 * <a href="https://leetcode.cn/problems/longest-zigzag-path-in-a-binary-tree/description/?envType=company&envId=pinduoduo&favoriteSlug=pinduoduo-thirty-days">1372. 二叉树中的最长交错路径</a>
 * <p>给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
 * <p>
 * 选择二叉树中 任意 节点和一个方向（左或者右）。
 * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
 * 改变前进方向：左变右或者右变左。
 * 重复第二步和第三步，直到你在树中无法继续移动。
 * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
 * <p>
 * 请你返回给定树中最长 交错路径 的长度。
 *
 * <p>思路：dfs，使用一个变量标识上一次选择的路径，如果下次要走的方法须与上次一样，长度需要重置为 1</p>
 */
class Leetcode1372LongestZigzagPathInABinaryTree {

    private int res;

    public int longestZigZag(TreeNode root) {

        if (root == null) return 0;
        res = 0;

        // 上一层左和右都考虑一遍
        dfs(root, true, 0);
        dfs(root, false, 0);

        return res;
    }

    private void dfs(TreeNode root, boolean left, int len) {
        res = Math.max(res, len);

        if (left) {
            if (root.left != null) dfs(root.left, true, 1);
            if (root.right != null) dfs(root.right, false, len + 1);
        } else {
            if (root.left != null) dfs(root.left, true, len + 1);
            if (root.right != null) dfs(root.right, false, 1);
        }
    }

}
