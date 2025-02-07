package problems.other.design;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 剑指 Offer 41. 数据流中的中位数
 * <p>
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于
 * 中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 * <p>
 * 思路：使用两个堆，一个小顶堆存放较大的一半，一个大顶堆存放较小的一半数
 * addNum(int num) 实现：
 * 1. 如果为偶数个数（两堆大小相同）向小顶堆（大数堆）存放一个数
 * 2. 如果为奇数个数，向大顶堆存放一个数
 * 注意1：为保证两堆顶始终对应数组的中间（即小顶堆的数始终均不小于大顶堆的数），添加数时应添加向另一个堆添加一个数再弹出的数。
 * 注意2：优先队列/二叉堆插入的数其排序并不与索引对应
 */
public class MedianFinder {
    Queue<Integer> rMin, lMax;

    public MedianFinder() {
        rMin = new PriorityQueue<>();
        lMax = new PriorityQueue<>((o1, o2) -> o2 - o1);
    }

    public void addNum(int num) {
        if (rMin.size() != lMax.size()) {
            rMin.add(num);
            lMax.add(rMin.poll());
        } else {
            lMax.add(num);
            rMin.add(lMax.poll());
        }
    }

    public double findMedian() {
        return rMin.size() == lMax.size() ? (rMin.peek() + lMax.peek()) / 2.0 : rMin.peek();
    }
}

