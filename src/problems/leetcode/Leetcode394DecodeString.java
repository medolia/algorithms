package problems.leetcode;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/decode-string/description/?envType=study-plan-v2&envId=top-100-liked">394. 字符串解码</a>
 * <p>
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * <p>
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * <p>
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * <p>
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 * 思路：类似计算器，反向遍历结合辅助栈
 *
 * @author lbli
 */
class Leetcode394DecodeString {

    public static void main(String[] args) {
        String result = new Leetcode394DecodeString().decodeString("100[problems.leetcode]");
//        String result = new Leetcode394DecodeString().decodeString("3[a2[c]]");
        System.out.println(result);
    }

    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();

        int i = s.length() - 1;
        while (i >= 0) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                String part = getPartFromStack(stack);
                int count = 0;
                int tenPow = 0;
                while (i >= 0 && Character.isDigit(s.charAt(i))) {
                    count += (int) ((s.charAt(i) - '0') * Math.pow(10, tenPow));
                    tenPow++;
                    i--;
                }

                String tmp = "";
                while (count > 0) {
                    tmp = tmp.concat(part);
                    count--;
                }
                stack.push(tmp);
            } else {
                stack.push(String.valueOf(c));
                i--;
            }
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.toString();
    }

    @SuppressWarnings("all")
    private String getPartFromStack(Stack<String> stack) {
        StringBuilder result = new StringBuilder();
        while (true) {
            String curr = stack.pop();
            if (curr.equals("]")) {
                break;
            } else if (curr.equals("[")) {
                continue;
            } else {
                result.append(curr);
            }
        }

        return result.toString();
    }

}
