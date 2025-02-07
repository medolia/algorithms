package libs;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;

    @SuppressWarnings("unused")
    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("val:%s, left: %s, right: %s",
                this.val,
                left == null ? "null" : left.val,
                right == null ? "null" : right.val);
    }

    public static TreeNode buildTreeFromLevelOrder(Integer... levelOrder) {
        if (levelOrder == null || levelOrder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int index = 1;

        while (!queue.isEmpty() && index < levelOrder.length) {
            TreeNode current = queue.poll();

            // 左子节点
            if (index < levelOrder.length && levelOrder[index] != null) {
                current.left = new TreeNode(levelOrder[index]);
                queue.offer(current.left);
            }
            index++;

            // 右子节点
            if (index < levelOrder.length && levelOrder[index] != null) {
                current.right = new TreeNode(levelOrder[index]);
                queue.offer(current.right);
            }
            index++;
        }

        return root;
    }
}
