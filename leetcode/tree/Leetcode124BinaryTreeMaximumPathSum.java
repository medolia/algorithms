package tree;

/**
 * https://leetcode.cn/problems/binary-tree-maximum-path-sum/
 */
public class Leetcode124BinaryTreeMaximumPathSum {

    int result = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        onsideMax(root);

        return result;
    }

    public int onsideMax(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int left = Math.max(0, onsideMax(root.left));
        int right = Math.max(0, onsideMax(root.right));

        result = Math.max(result, left + root.val + right);

        return Math.max(left, right) + root.val;
    }
}
