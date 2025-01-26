package treeAndGraph;

import struc.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a href="https://leetcode.cn/problems/check-completeness-of-a-binary-tree/?envType=company&envId=meituan&favoriteSlug=meituan-thirty-days">958. 二叉树的完全性检验</a>
 * <br> 给你一棵二叉树的根节点 root ，请你判断这棵树是否是一棵 完全二叉树 。
 * <p>在一棵 完全二叉树 中，除了最后一层外，所有层都被完全填满，并且最后一层中的所有节点都尽可能靠左。最后一层（第 h 层）中可以包含 1 到 2h 个节点。
 * <p>思路：层序遍历，如果还没有遍历完就遇到 null 值则非完全二叉树。
 */
class LeetCode958CheckCompletenessOfABinaryTree {

    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        boolean flag = false;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                flag = true;
            }

            if (node != null) {
                if (flag) {
                    return false;
                }

                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return true;
    }

}
