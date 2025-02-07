package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/string-compression-iii/">3163. 压缩字符串 III</a>
 * <p>思路：双指针 时：O(N) 空：O(1)</p>
 */
class Leetcode3163StringCompression3 {

    public static void main(String[] args) {
        Leetcode3163StringCompression3 solution = new Leetcode3163StringCompression3();
        String res1 = solution.compressedString("abcde");
        System.out.println(res1);
        String res2 = solution.compressedString("aaaaaaaaaaaaaabb");
        System.out.println(res2);
    }

    public String compressedString(String word) {
        int l = 0;
        StringBuilder res = new StringBuilder();
        while (l < word.length()) {

            int r = l;
            while (r < word.length() && word.charAt(r) == word.charAt(l) && r - l < 9) {
                r++;
            }
            res.append(r - l);
            res.append(word.charAt(l));

            l = r;
        }

        return res.toString();
    }

}
