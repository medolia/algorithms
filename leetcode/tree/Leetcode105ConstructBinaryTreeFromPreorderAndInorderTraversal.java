package tree;

/**
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 */
public class Leetcode105ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public static void main(String[] args) {
        TreeNode result = new Leetcode105ConstructBinaryTreeFromPreorderAndInorderTraversal()
                .buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        System.out.println(result);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        return buildTreeInternal(
                new ValueRange(preorder, 0, preorder.length - 1),
                new ValueRange(inorder, 0, inorder.length - 1));

    }

    private TreeNode buildTreeInternal(ValueRange preValueRange, ValueRange inValueRange) {
        if (preValueRange.invalid() || inValueRange.invalid()) {
            return null;
        }

        int rootVal = preValueRange.src[preValueRange.l];

        int inorderRootI;
        for (inorderRootI = inValueRange.l; inorderRootI < inValueRange.r; inorderRootI++) {
            if (inValueRange.src[inorderRootI] == rootVal) {
                break;
            }
        }

        // left tree length: inorderRootI - inValueRange.l
        int preLEnd = preValueRange.l + (inorderRootI - inValueRange.l);
        TreeNode left = buildTreeInternal(
                new ValueRange(preValueRange.src, preValueRange.l + 1, preLEnd),
                new ValueRange(inValueRange.src, inValueRange.l, inorderRootI - 1));
        TreeNode right = buildTreeInternal(
                new ValueRange(preValueRange.src, preLEnd + 1, preValueRange.r),
                new ValueRange(inValueRange.src, inorderRootI + 1, inValueRange.r));

        return new TreeNode(rootVal, left, right);
    }

    static class ValueRange {

        int[] src;
        int l, r;

        public ValueRange(int[] src, int l, int r) {
            this.src = src;
            this.l = l;
            this.r = r;
        }

        boolean invalid() {
            return r < l;
        }
    }


}
