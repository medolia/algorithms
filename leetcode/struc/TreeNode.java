package struc;


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
}
