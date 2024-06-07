package tree;

import struc.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 * <p>
 * <a href="https://leetcode.cn/problems/binary-tree-level-order-traversal/description/?envType=study-plan-v2&envId=top-100-liked">102. 二叉树的层序遍历</a>
 * @author lbli
 */
class Leetcode102LevelOrder {

    public static void main(String[] args) {
        TreeNode testRoot = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15), new TreeNode(7)));

        List<List<Integer>> result = new Leetcode102LevelOrder().levelOrder(testRoot);
        System.out.println(result);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        List<Integer> level = new ArrayList<>();
        int levelSize = 1, nextLevelSize = 0;
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            if (null != curr) {
                level.add(curr.val);
                queue.add(curr.left);
                queue.add(curr.right);
                nextLevelSize += 2;
            }

            --levelSize;
            if (levelSize == 0) {
                if (!level.isEmpty()) {
                    res.add(level);
                }
                level = new ArrayList<>();
                levelSize = nextLevelSize;
                nextLevelSize = 0;
            }

        }

        return res;
    }

}
