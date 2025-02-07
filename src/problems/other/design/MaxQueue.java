package problems.other.design;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指 Offer 59 - II. 队列的最大值
 * <p>
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 * 若队列为空，pop_front 和 max_value需要返回 -1
 * <p>
 * 思路：维护两个队列，一个存放最大值，一个存放原始数据（受 58.滑动窗口最大值 启发）
 */
public class MaxQueue {

    Deque<Integer> maxVal, origin;

    public MaxQueue() {
        maxVal = new LinkedList<>();
        origin = new LinkedList<>();
    }

    public int max_value() {
        return maxVal.isEmpty() ? -1 : maxVal.peekFirst();
    }

    public void push_back(int value) {
        while (!maxVal.isEmpty() && value > maxVal.peekLast())
            maxVal.removeLast();
        maxVal.offerLast(value);
        origin.offerLast(value);
    }

    public int pop_front() {
        if (!origin.isEmpty() && origin.peekFirst().equals(maxVal.peekFirst()))
            maxVal.removeFirst();
        return origin.isEmpty() ? -1 : origin.pollFirst();
    }
}
