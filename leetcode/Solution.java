import struc.TreeNode;

import java.util.*;

class Solution {

    public static void main(String[] args) {

        Solution solution = new Solution();

        TreeNode root = TreeNode.buildTreeFromLevelOrder(new Integer[]{1, 2, 3, 4, null, 5, 6, null, null, 7});

        System.out.println(solution.findBottomLeftValue(root));;

    }

    public int findBottomLeftValue(TreeNode root) {

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int level = 1;
        int levelCount = 1;
        int nextLevelCount = 0;
        boolean levelFirstElement = false;
        int resV = root.val;

        while (!deque.isEmpty()) {
            TreeNode node = deque.poll();
            if (levelFirstElement) {
                levelFirstElement = false;
                resV = node.val;
            }

            if (node.left != null) {
                deque.offer(node.left);
                nextLevelCount++;

            }

            if (node.right != null) {
                deque.offer(node.right);
                nextLevelCount++;
            }

            if (--levelCount <= 0) {
                level++;
                levelCount = nextLevelCount;
                nextLevelCount = 0;
                levelFirstElement = true;
            }
        }

        return resV;
    }

    /**
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * <p>
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 思路：双指针，因为始终优先靠前匹配是局部最优解。
     * <p>
     * 延伸：如果不规定相对位置，那么需要使用计数器。如果是多个待匹配子串，考虑使用类似 KMP 的思路生成一个状态机。
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == s.length();
    }
}
