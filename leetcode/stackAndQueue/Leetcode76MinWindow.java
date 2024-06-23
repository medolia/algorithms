package stackAndQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/minimum-window-substring/description/">76. 最小覆盖子串</a>
 * <p>
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * <p>
 * 注意：
 * <p>对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * <p>如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 思路：滑动窗口，注意初始化窗口需要将右边界滑动至满足要求为止
 * <p>时：O(N)；空：O(N)
 */
class Leetcode76MinWindow {

    public static void main(String[] args) {
        String result = new Leetcode76MinWindow().minWindow("ADOBECODEBANC", "ABC");
        System.out.println(result);
    }

    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.merge(c, 1, Integer::sum);
        }

        // 当前窗口覆盖子串剩余字符树，为0表明满足要求
        int remaining = t.length();
        int l = 0, r = 0, minLen = Integer.MAX_VALUE, minStart = -1;

        while (r < s.length()) {
            char rc = s.charAt(r);
            if (need.getOrDefault(rc, 0) > 0) {
                remaining--;
            }
            need.merge(rc, -1, Integer::sum);

            if (remaining == 0) {
                while (l < r && need.getOrDefault(s.charAt(l), -1) < 0) {
                    need.merge(s.charAt(l), 1, Integer::sum);
                    l++;
                }

                // 左边界收缩完毕后择优
                if (r - l + 1 < minLen) {
                    minStart = l;
                    minLen = r - l + 1;
                }

                // 优化点：这里收缩完毕后可以左边界前进一格，对应更新 l 和 remaining 变量
            }

            r++;
        }

        return minLen > s.length() ? "" : s.substring(minStart, minStart + minLen);
    }

}
