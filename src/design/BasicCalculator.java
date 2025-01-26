package design;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/basic-calculator/description/">224. 基本计算器</a>
 * <p>
 * 实际结算应该与输入顺序相反，即使用栈来处理，
 * <p>数字和符号正常压入栈
 * <p>当遇到 ”）“ 触发一次括号内结算
 *
 *
 * // TODO: 递归思维，括号内的可以看作一个子集
 * @author lbli
 */
public class BasicCalculator {

    public static void main(String[] args) {
//        new BasicCalculator().calculate("-(1+(46+5+2)-3)+(6+8)");
        int calculate = new BasicCalculator().calculate("2-(5-6)");
//        new BasicCalculator().calculate("(1+(4+5+2)-3)+(6+8)");
    }

    /**
     * (: 下一个元素入栈
     * ): 出栈
     */
    public int calculate(String s) {
        s = s.replaceAll(" ", "");
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();

        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                nums.push(num);
            } else if (c == ' ') {
                i++;
                continue;
            } else if (c == '+' || c == '-' || c == '(') {

                // (-2) -> (0-2)
                if (c == '-' && i > 0 && s.charAt(i - 1) == '(') {
                    nums.push(0);
                }
                ops.push(c);
                i++;
            } else if (c == ')') { // 触发一次结算
                calc(nums, ops);
                i++;
            }

        }

        calc(nums, ops);

        return nums.pop();
    }

    private void calc(Stack<Integer> nums, Stack<Character> ops) {
        int tempRes = 0;
        while (!nums.isEmpty() && !ops.isEmpty()) {


            int num = nums.pop();
            Character op = ops.pop();

            int validV = op == '-' ? -num : num;
            tempRes += validV;

            if (op == '(') {
                break;
            }
        }

        if (nums.size() == 1 && ops.isEmpty()) {
            tempRes += nums.pop();
        }
        nums.push(tempRes);
    }
}
