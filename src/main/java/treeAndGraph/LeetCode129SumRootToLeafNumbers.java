package treeAndGraph;

import struc.TreeNode;

/**
 * 129. 求根节点到叶节点数字之和
 * <p>
 * https://leetcode.cn/problems/sum-root-to-leaf-numbers/submissions/593541078/?envType=company&envId=meituan&favoriteSlug=meituan-six-months
 * <p>
 * 前序遍历，时空复杂度均为 O(N)
 *
 * @author lilongbin
 */
class LeetCode129SumRootToLeafNumbers {

    public int sumNumbers(TreeNode root) {

        return sumInternal(root, 0);

    }

    public int sumInternal(TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }

        int sum = prevSum * 10 + root.val;

        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return sumInternal(root.left, sum) + sumInternal(root.right, sum);
        }
    }

}
