package stackAndQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/next-greater-element-ii/description/">503. 下一个更大元素 II</a>
 * <p>
 * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
 * <p>
 * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
 * <p>
 * 思路：单调栈，循环数组则多遍历 n 的长度即可，栈保证栈顶为当前最大值
 * <p>时：O(N)，空：O（N）
 *
 * @author lbli
 */
class Leetcode503NextGreaterElements {

    public static void main(String[] args) {
        int[] result = new Leetcode503NextGreaterElements().nextGreaterElements(new int[]{1, 2, 1});
        System.out.println(Arrays.toString(result));
    }

    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < nums.length * 2; i++) {
            int norI = i % nums.length;
            while (!stack.isEmpty() && nums[norI] > nums[stack.peekFirst()]) {
                res[stack.pollFirst()] = nums[norI];
            }

            stack.addFirst(norI);
        }

        return res;
    }


}
