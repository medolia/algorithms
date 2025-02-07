package problems.leetcode;

import libs.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/find-bottom-left-tree-value/?envType=company&envId=microsoft&favoriteSlug=microsoft-thirty-days">513. 找树左下角的值</a>
 * <p>给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
 * <p>假设二叉树中至少有一个节点。
 * <p>思路：层序遍历，优先向队列置入右子节点，遍历到最后即为结果。
 * <p>时空复杂度：O(N)
 */
class Leetcode513FindBottomLeftTreeValue {

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildTreeFromLevelOrder(
                1, 2, 3, 4, null, 5, 6, null, null, 7);
        int res = new Leetcode513FindBottomLeftTreeValue().findBottomLeftValue(root);
        System.out.println(res);
    }

    int findBottomLeftValue(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int res = 0;

        while (!deque.isEmpty()) {
            TreeNode node = deque.poll();
            res = node.val;

            if (node.right != null) {
                deque.offer(node.right);
            }
            if (node.left != null) {
                deque.offer(node.left);
            }
        }

        return res;
    }

}
