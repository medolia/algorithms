package treeAndGraph;

import struc.TreeNode;

/**
 * 543. 二叉树的直径
 * <p>
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 * <p>
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
 * <p>
 * 两节点之间路径的 长度 由它们之间边数表示。
 * 思路：定义递归函数，以当前根节点最大的单边直径
 */
class Leetcode543DiameterOfBinaryTree {

    int res;

    public int diameterOfBinaryTree(TreeNode root) {
        res = Integer.MIN_VALUE;
        transverse(root);

        return res;
    }

    private int transverse(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftMax = root.left == null ? 0 : transverse(root.left) + 1;
        int rightMax = root.right == null ? 0 : transverse(root.right) + 1;

        res = Math.max(res, leftMax + rightMax);

        return Math.max(leftMax, rightMax);
    }

}
