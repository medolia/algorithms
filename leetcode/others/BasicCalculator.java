package others;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/basic-calculator/description/">224. 基本计算器</a>
 *
 * @author lbli
 */
public class BasicCalculator {

    public static void main(String[] args) {
        new BasicCalculator().calculate("(1+(4+5+2)-3)+(6+8)");
//        new BasicCalculator().calculate("(1+(4+5+2)-3)+(6+8)");
    }

    /**
     * (: 下一个元素入栈
     * ): 出栈
     */
    public int calculate(String s) {
        Stack<Stack<Integer>> mainStack = new Stack<>();
        mainStack.push(new Stack<>());

        int reg = 0;
        int res = 0;
        boolean minusFlag = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (c == '-') {
                minusFlag = true;
                continue;
            }

            if (Character.isDigit(c)) {
                reg = reg * 10 + (c - '0');
            } else {
                int validV = minusFlag ? -reg : reg;
                minusFlag = false;
                Stack<Integer> curr = mainStack.peek();
                curr.push(validV);
                reg = 0;
            }

            if (c == '+') {

            } else if (c == '(') {
                Stack<Integer> newStack = new Stack<>();
                mainStack.push(newStack);
            } else if (c == ')') {
                Stack<Integer> finished = mainStack.pop();
                int finishedRes = 0;
                while (!finished.isEmpty()) {
                    finishedRes += finished.pop();
                }
                mainStack.peek().push(finishedRes);
            }
        }
        mainStack.peek().push(reg);


        while (!mainStack.isEmpty()) {
            Stack<Integer> curr = mainStack.pop();
            while (!curr.isEmpty()) {
                res += curr.pop();
            }
        }

        return res;
    }

}
