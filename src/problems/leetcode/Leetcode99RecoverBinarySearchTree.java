package problems.leetcode;

import libs.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索树恰好两节点交换，尝试还原
 * 中序获得序列 -> 标记 -> 中序还原
 * <a href="https://leetcode.cn/problems/recover-binary-search-tree/submissions/535563480/">还原二叉树</a>
 */
class Leetcode99RecoverBinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(3, null, new TreeNode(2)), null);

        new Leetcode99RecoverBinarySearchTree().recoverTree(root);
    }

    public void recoverTree(TreeNode root) {
        ArrayList<Integer> numsSorted = new ArrayList<>();
        inorder(root, numsSorted);
        int[] exNumPair = findAndMarkSwapped(numsSorted);
        recoverInternal(root, 2, exNumPair[0], exNumPair[1]);
    }

    private int[] findAndMarkSwapped(List<Integer> nums) {
        int n = nums.size();
        int index1 = -1, index2 = -1;
        for (int i = 0; i < n - 1; ++i) {
            if (nums.get(i + 1) < nums.get(i)) {
                index2 = i + 1;
                if (index1 == -1) {
                    index1 = i;
                } else {
                    break;
                }
            }
        }
        int x = nums.get(index1), y = nums.get(index2);
        return new int[]{x, y};
    }

    private void recoverInternal(TreeNode root, int count, int num1, int num2) {
        if (null == root) {
            return;
        } else if (root.val == num1) {
            root.val = num2;
            --count;
        } else if (root.val == num2) {
            root.val = num1;
            --count;
        } else if (count == 0) {
            return;
        }

        recoverInternal(root.left, count, num1, num2);
        recoverInternal(root.right, count, num1, num2);
    }

    private void inorder(TreeNode root, ArrayList<Integer> integers) {
        if (null == root) {
            return;
        }

        inorder(root.left, integers);
        integers.add(root.val);
        inorder(root.right, integers);
    }


}
