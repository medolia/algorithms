package problems.leetcode;

import libs.TreeNode;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * <p>
 * 思路：定义辅助递归函数-以当前根节点是否可以找到节点 p 或 q，使用后续遍历第一个满足祖先条件的即为结果
 */
@SuppressWarnings("all")
class Leetcode236LowestCommonAncestor {

    TreeNode ans = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        findNode(root, p, q);
        return ans;
    }

    // 辅助函数定义：以 root 为根（包含），是否可以找到 p 或 q
    private boolean findNode(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }

        boolean lFound = findNode(root.left, p, q);
        boolean rFound = findNode(root.right, p, q);

        boolean pqOneIsCurrNodeAnotherFoundInChild =
                (root == p || root == q) && (lFound || rFound);
        if (lFound && rFound || pqOneIsCurrNodeAnotherFoundInChild) {
            ans = root;
        }

        return lFound || rFound || root == p || root == q;
    }

}
