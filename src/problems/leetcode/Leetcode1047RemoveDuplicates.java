package problems.leetcode;

import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/description/?envType=company&envId=huawei&favoriteSlug=huawei-three-months">1047. 删除字符串中的所有相邻重复项</a> <br>
 * 题目描述:
 * 给出由小写字母组成的字符串 s，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * <p>
 * 在 s 上反复执行重复项删除操作，直到无法继续删除。
 * <p>
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 * <p>
 * 示例：
 * <p>
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 * <p>
 * 思路:
 * 滑动窗口，每次将当前元素与窗口边界元素比较，一致则抵消，否则加入窗口。
 * <p>时间复杂度:O(N)
 * <p>空间复杂度:O(N)
 */

class Leetcode1047RemoveDuplicates {

    public static void main(String[] args) {
        String[] testCases = new String[]{"abbaca", "aaaaa"};

        for (String testCase : testCases) {
            String res = new Leetcode1047RemoveDuplicates().removeDuplicates(testCase);
            System.out.printf("%s -> %s\n", testCase, res);
        }
    }

    public String removeDuplicates(String s) {
        LinkedList<Character> window = new LinkedList<>();
        window.add(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {

            if (!window.isEmpty() && window.peek() == s.charAt(i)) {
                window.poll();
                continue;
            }

            window.push(s.charAt(i));
        }

        StringBuilder res = new StringBuilder();
        while (!window.isEmpty()) {
            res.append(window.removeLast());
        }
        return res.toString();
    }

}
