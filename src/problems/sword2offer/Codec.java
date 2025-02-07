package problems.sword2offer;

import libs.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 剑指 Offer 37. 序列化二叉树
 *
 * 请实现两个函数，分别用来序列化和反序列化二叉树。
 *
 * 思路：可逆操作一般使用层次遍历而不考虑前、中、后遍历，使用允许 null 值的队列辅助
 * 完成，序列化时连同叶子结点的左右子 null 结点也一并打印出来以保证信息完整。
 * 反序列化时，除使用队列辅助外，还需要一个指针来指向当前结点的左右子树的索引处。
 *
 */
public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "[]";

        StringBuilder res = new StringBuilder("[");
        Queue<TreeNode> queue = new LinkedList<>() {{
            offer(root);
        }};

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null)
                res.append("null,");
            else {
                res.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        res.deleteCharAt(res.length() - 1).append("]");
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == "[]") return null;
        String[] values = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>() {{
            offer(root);
        }};

        int i = 1; // 子树指针，在每次检查非 null 后自增
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (!values[i].equals("null")) {
                curr.left = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(curr.left);
            }
            ++i;
            if (!values[i].equals("null")) {
                curr.right = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(curr.right);
            }
            ++i;
        }

        return root;
    }
}
