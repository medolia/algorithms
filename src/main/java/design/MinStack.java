package design;

import java.util.Stack;

/**
 * 剑指 Offer 30. 包含min函数的栈
 * <p>
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈
 * 中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 * <p>
 * 思路：使用一个辅助栈辅助存储当前栈中的最小值
 */
public class MinStack {

    Stack<Integer> main;
    Stack<Integer> helper;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        main = new Stack<>();
        helper = new Stack<>();
    }

    public void push(int x) {
        main.push(x);
        helper.push(helper.isEmpty() ? x : Math.min(helper.peek(), x));
    }

    public void pop() {
        main.pop();
        helper.pop();
    }

    public int top() {
        return main.peek();
    }

    public int min() {
        return helper.peek();
    }
}
