package tree;

import struc.TreeNode;

/**
 * 算出树中最大值的路径
 * https://leetcode.cn/problems/binary-tree-maximum-path-sum/
 */
class Leetcode124BinaryTreeMaximumPathSum {

    int result = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        oneSideMax(root);

        return result;
    }

    private int oneSideMax(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int left = Math.max(0, oneSideMax(root.left));
        int right = Math.max(0, oneSideMax(root.right));

        result = Math.max(result, left + root.val + right);

        return Math.max(left, right) + root.val;
    }
}
