package problems.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/longest-palindrome/solutions/156931/zui-chang-hui-wen-chuan-by-leetcode-solution/">409. 最长回文串</a>
 * @author lbli
 */
public class Leetcode409LongestPalindrome {

    public static void main(String[] args) {
        int res = new Leetcode409LongestPalindrome().longestPalindrome("abccccdd");
    }

    public int longestPalindrome(String s) {

        Map<Character, Integer> counter = new HashMap<>();

        for (char c : s.toCharArray()) {
            counter.merge(c, 1, Integer::sum);
        }

        int res = 0;
        boolean singleFlag = true;
        for (Map.Entry<Character, Integer> kv : counter.entrySet()) {
            res += (kv.getValue() / 2) * 2;
            if (singleFlag && kv.getValue() % 2 > 0) {
                res++;
                singleFlag = false;
            }
        }

        return res;
    }

}
