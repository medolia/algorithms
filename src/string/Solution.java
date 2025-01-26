package string;

import java.util.*;

public class Solution {
    /**
     * 剑指 Offer 05. 替换空格
     */
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < s.length(); ++i) {
            char curr = s.charAt(i);
            if (curr != ' ')
                res.append(curr);
            else
                res.append("%20");
        }

        return res.toString();
    }

    /**
     * 剑指 Offer 20. 表示数值的字符串
     * <p>
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字
     * 符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123"都表示
     * 数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
     * <p>
     * 思路：将字符串分为整数A，小数B，指数C三个部分，则数字的模式可以是 A[.[B]][E/eC] 或 .B[E/eC]
     * 其中 A、C 为有符号整数，B 为无符号整数，一开始去掉两边空格并添加自定义终止字符防止越界
     */
    int idx;

    public boolean isNumber(String s) {
        s = s.trim() + '|';
        idx = 0;
        if (s.length() == 0) return false;


        boolean isNumeric = scanInteger(s);
        if (s.charAt(idx) == '.') {
            ++idx;
            // 需要将扫描行为置于左边，否则可能会跳过扫描
            isNumeric = scanUnsignedInt(s) || isNumeric;
        }

        if (s.charAt(idx) == 'e' || s.charAt(idx) == 'E') {
            ++idx;
            isNumeric = scanInteger(s) && isNumeric;
        }

        return isNumeric && s.charAt(idx) == '|';
    }

    boolean scanInteger(String s) {
        if (s.charAt(idx) == '+' || s.charAt(idx) == '-')
            ++idx;

        return scanUnsignedInt(s);
    }

    boolean scanUnsignedInt(String s) {
        int start = idx;
        while (s.charAt(idx) >= '0' && s.charAt(idx) <= '9')
            ++idx;

        return idx > start;
    }

    /**
     * 剑指 Offer 48. 最长不含重复字符的子字符串
     * <p>
     * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
     * <p>
     * 思路：双指针，哈希映射
     * 遍历字符串时，如果出现重复字符时更新左指针（只能增大）
     * 然后记录每个字符出现的最新索引位置，更新结果
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> idxMap = new HashMap<>();
        int left = -1, res = 0;

        for (int i = 0; i < s.length(); ++i) {
            char curr = s.charAt(i);
            if (idxMap.containsKey(curr))
                left = Math.max(left, idxMap.get(curr));
            idxMap.put(curr, i);
            res = Math.max(res, i - left);
        }

        return res;
    }

    /**
     * 剑指 Offer 50. 第一个只出现一次的字符
     * <p>
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。
     * s 只包含小写字母。
     * <p>
     * 思路：计数器（类似哈希表），遍历两遍字符串，第一遍计数，第二遍找值；
     * <p>
     * 启发：检测字符在字符串中重复、出现次数等，可考虑用数组或者哈希表计数
     */
    public char firstUniqChar(String s) {
        int[] counter = new int[26]; // 也可以存 char - 'a' 的值缩小尺寸
        for (int i = 0; i < s.length(); i++)
            counter[s.charAt(i) - 'a']++;

        for (int i = 0; i < s.length(); i++)
            if (counter[s.charAt(i) - 'a'] == 1) return s.charAt(i);

        return ' ';
    }

    /**
     * 剑指 Offer 58 - I. 翻转单词顺序
     * <p>
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一
     * 样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
     * 此外，两端空格逆转后不输出，忽略掉两单词间多余的空格，不能使用 split()、reverse() 内建函数。
     * <p>
     * 思路：双指针，从右端开始扫描，左指针 l 指向最近的第一个空格，右指针 r 指向最近的第一个非空格
     * 由此一次扫描后字符串 s 的 [l+1,r] 为扫描得到的那个单词。
     */
    public String reverseWords(String s) {
        s = s.trim();
        StringBuilder res = new StringBuilder();
        int r = s.length() - 1, l = r;
        while (true) {
            // 更新左指针
            while (l >= 0 && s.charAt(l) != ' ')
                --l;
            res.append(s, l + 1, r + 1).append(" ");
            if (l < 0) break;
            r = l; // 添加后右指针需要从左指针处开始扫描
            while (s.charAt(r) == ' ')
                --r;
            l = r;
        }

        return res.toString().trim();
    }

    /**
     * 剑指 Offer 58 - II. 左旋转字符串
     * <p>
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的
     * 功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     * <p>
     * 思路：拼接，后半部分 s[n:] 和前半部分 s[0:n]
     * <p>
     * 启发：若为数组，则原索引 i 与新索引 i' 的关系为 i' = (i + s.len) % n
     */
    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    /**
     * 剑指 Offer 67. 把字符串转换成整数
     * <p>
     * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
     * 1. 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止;
     * 2. 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；
     * 3. 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     * 4. 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     * <p>
     * 思路：
     * 1. 开头的正负号需要跳过（默认跳过首位，检测头尾不为正负号则不跳过），使用一个变量保存符号
     * 2. 注意边界溢出，当 currRes > max / 10 || ( == max / 10 && curr > '7') 时判定溢出
     */
    public int strToInt(String str) {
        str = str.trim();
        if (str.length() == 0) return 0;

        int res = 0, sign = 1, boundary = Integer.MAX_VALUE / 10;
        int startI = 1;
        if (str.charAt(0) == '-') sign = -1;
        else if (str.charAt(0) != '+') startI = 0;

        for (int i = startI; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (curr < '0' || curr > '9') break;
            if (res > boundary || (res == boundary && curr > '7'))
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            res = res * 10 + curr - '0';
        }

        return res * sign;
    }

    /**
     * 5. 最长回文子串
     * <p>
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * <p>
     * 思路：回文中心、双指针扫描；
     * 考虑到最长回文可能是奇数串也可能是偶数串，所以对于当前位置 i，回文中心可能是
     * i 也可能是 i 和 i+1，两种可能都需要考虑；
     * <p>
     * 时间复杂度 N^2 空间复杂度 1
     */
    public String longestPalindrome(String s) {
        int maxLen = 0;
        String res = "";

        for (int i = 0; i < s.length(); i++) {
            int l = i, r = i;
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                --l;
                ++r;
            }
            if (r - l + 1 > maxLen) {
                res = s.substring(l + 1, r);
                maxLen = r - l + 1;
            }
            ;

            l = i;
            r = i + 1;
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                --l;
                ++r;
            }
            if (r - l + 1 > maxLen) {
                res = s.substring(l + 1, r);
                maxLen = r - l + 1;
            }
            ;
        }

        return res;
    }

    /**
     * 14. 最长公共前缀
     * <p>
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 思路：纵向扫描，由于答案一定是第一个字符串的子串，可以逐个将首个字符串的
     * 字符与之后的字符串对应位置进行比较；
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int charI = 0; charI < strs[0].length(); charI++) {
            char curr = strs[0].charAt(charI);

            for (int strI = 1; strI < strs.length; strI++) {
                if (charI == strs[strI].length() || strs[strI].charAt(charI) != curr)
                    return strs[0].substring(0, charI);
            }
        }

        return strs[0];
    }

    /**
     * 20. 有效的括号
     * <p>
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串，判断字符串是否有效。
     * 有效字符串需满足：
     * 1. 左括号必须用相同类型的右括号闭合。
     * 2. 左括号必须以正确的顺序闭合。
     * 3. 注意空字符串可被认为是有效字符串。
     * <p>
     * 思路：遇到左部分时入栈，遇到右部分时检查栈顶是否匹配；
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if (curr == '(' || curr == '{' || curr == '[')
                stack.push(curr);
            else {
                if (stack.isEmpty() || map.get(curr) != stack.peek())
                    return false;
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    /**
     * 43. 字符串乘法
     * 从个位开始相乘，num1 i 与 num2 j 对应结果数组中的 i+j 和 i+j+1 位
     */
    public String multiply(String num1, String num2) {
        int len1 = num1.length(), len2 = num2.length();
        int[] res = new int[len1 + len2];

        for (int i = len1 - 1; i >= 0; i--)
            for (int j = len2 - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');

                int p1 = i + j, p2 = i + j + 1;

                int sum = mul + res[p2];
                res[p2] = sum % 10;
                res[p1] += sum / 10;
            }

        int p = 0;
        while (p < res.length && res[p] == 0) ++p;

        StringBuilder ans = new StringBuilder();
        for (int i = p; i < res.length; i++)
            ans.append(res[i]);

        return ans.length() == 0 ? "0" : ans.toString();
    }

    /**
     * 71. 简化 Unix 文件路径
     */
    public String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        String[] split = path.split("/");
        for (String curr : split) {
            if (curr.equals("..")) {
                if (!stack.isEmpty())
                    stack.pop();
            } else if (curr.length() > 0 && !curr.equals("."))
                stack.push(curr);
        }

        if (stack.isEmpty())
            return "/";

        StringBuffer ans = new StringBuffer();
        while (!stack.isEmpty()) {
            ans.append("/").append(stack.pollLast());
        }
        return ans.toString();
    }

    /**
     * 205. 同构字符串
     * <p>
     * 给定两个字符串 s 和 t，判断它们是否是同构的。
     * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
     * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
     * <p>
     * 思路：需求s的字符与t的字符一一对应，映射必须唯一，考虑使用两个映射；
     */
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2t = new HashMap<>(), t2s = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i), tc = t.charAt(i);
            if (t2s.containsKey(tc) && t2s.get(tc) != sc || s2t.containsKey(sc) && s2t.get(sc) != tc) return false;
            t2s.put(tc, sc);
            s2t.put(sc, tc);
        }

        return true;
    }

    /**
     * 224. 基本计算器
     * <p>
     * 实现一个基本的计算器来计算一个简单的字符串表达式的值。
     * 字符串表达式可以包含左括号 ( ，右括号 )，基本运算符（"+"、"-"、"*"、"/"）非负整数和空格。
     * <p>
     * 思路：使用栈分解表达式；
     * 例："1 + 2 / 3 - 5 * 3" 转化为 "+1 +2/3 +(-5*3)；即左至右遍历，遇到一个运算符结算一次；
     * num -> 10 * preNum + num；"+" -> push(num); "-" -> push(-num);
     * "*" -> push(pop() * num); "/" -> push(pop / num);
     * 括号处理：找到对应右括号的位置，递归子串；
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        char sign = '+';
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if (Character.isDigit(curr))
                num = 10 * num + (curr - '0');

            if ((!Character.isDigit(curr) && curr != ' ') || i == s.length() - 1) {
                if (curr == '(') {
                    int pR = getPR(s, i);
                    num = calculate(s.substring(i + 1, pR));
                    i = pR - 1;
                } else {
                    if (sign == '+')
                        stack.push(num);
                    else if (sign == '-')
                        stack.push(-num);
                    else if (sign == '*') {
                        int pre = stack.pop();
                        stack.push(pre * num);
                    } else if (sign == '/') {
                        int pre = stack.pop();
                        stack.push(pre / num);
                    }
                    sign = curr;
                    num = 0;
                }
            }
        }

        int res = 0;
        while (!stack.isEmpty())
            res += stack.pop();

        return res;
    }

    private int getPR(String s, int pL) {
        int count = 1, pR = -1;
        for (int i = pL + 1; i < s.length(); i++) {
            if (s.charAt(i) == '(') ++count;
            else if (s.charAt(i) == ')') {
                if (--count == 0) {
                    pR = i;
                    break;
                }
            }
        }
        return pR;
    }

    /**
     * 391. 完美矩形
     * <p>
     * 我们有 N 个与坐标轴对齐的矩形, 其中 N > 0, 判断它们是否能精确地覆盖一个矩形区域。
     * 每个矩形用左下角的点和右上角的点的坐标来表示。例如，一个单位正方形可以表示为 [1,1,2,2]。
     * ( 左下角的点的坐标为 (1, 1) 以及右上角的点的坐标为 (2, 2) )。
     * <p>
     * 思路：假设遍历所有矩形后找到了 4 个最值定点，符合条件需要满足两个要求：
     * 1. 所有小矩形的面积和等于最终形成的大矩形面积；
     * 2. 小矩形的四个顶点两两抵消，最后剩下恰好剩下四个大矩形的顶点；
     */
    public boolean isRectangleCover(int[][] rectangles) {
        int areaSum = 0;
        int X1, Y1, X2, Y2;
        X1 = Y1 = Integer.MAX_VALUE;
        X2 = Y2 = Integer.MIN_VALUE;
        // 应以字符串为键，数组为键会出错；
        Set<String> points = new HashSet<>();

        for (int[] rec : rectangles) {
            int x1 = rec[0], y1 = rec[1], x2 = rec[2], y2 = rec[3];
            X1 = Math.min(x1, X1);
            X2 = Math.max(x2, X2);
            Y1 = Math.min(y1, Y1);
            Y2 = Math.max(y2, Y2);
            areaSum += (x2 - x1) * (y2 - y1);
            String[] currPoints = new String[]{x1 + " " + y1, x1 + " " + y2, x2 + " " + y1, x2 + " " + y2};
            for (String point : currPoints) { // 抵消顶点；
                if (points.contains(point))
                    points.remove(point);
                else points.add(point);
            }
        }

        // 面积需要相等
        int areaContained = (X2 - X1) * (Y2 - Y1);
        if (areaSum != areaContained) return false;

        // 点位需要对上
        if (points.size() != 4 || !points.contains(X1 + " " + Y1) || !points.contains(X1 + " " + Y2)
                || !points.contains(X2 + " " + Y1) || !points.contains(X2 + " " + Y2))
            return false;

        return true;
    }

    /**
     * 567. 字符串的排列
     * <p>
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
     * 注：输入的字符串只包含小写字母。
     * <p>
     * 思路：使用一个整数数组作计数器；
     */
    public boolean checkInclusion(String s1, String s2) {
        int[] map = new int[26];
        int count = 0;
        for (int i = 0; i < s1.length(); i++)
            if (map[s1.charAt(i) - 'a']++ == 0) count++;

        int l = 0;
        for (int i = 0; i < s2.length(); i++) {
            int ch = s2.charAt(i) - 'a';
            if (--map[ch] == 0) count--;

            while (map[ch] < 0)
                if (map[s2.charAt(l++) - 'a']++ == 0) count++;

            if (count == 0) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new Solution().isIsomorphic("foo", "bar");
    }
}
