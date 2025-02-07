package problems.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/daily-temperatures/description/?envType=study-plan-v2&envId=top-100-liked">739. 每日温度</a>
 * <p>
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 思路：相当于求距当前位置最近的下一个最大值，从右往左遍历，使用单调栈存储索引，
 *
 * @author lbli
 */
class Leetcode739DailyTemperature {

    public static void main(String[] args) {
        int[] result = new Leetcode739DailyTemperature().dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        System.out.println(Arrays.toString(result));
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }

        return result;
    }

}
