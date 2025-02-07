package problems.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 * <p>
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 * <p>
 * MedianFinder() 初始化 MedianFinder 对象。
 * <p>
 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 * <p>
 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
 * <p>
 * 思路：对于一个有序队列如果使用两个堆分别存储左右两半，左边为最大堆，右边为最小堆，根据两个堆顶即可算出中位数
 * addNum: 将数与当前中位数比较，小则入左最大堆，反之入右堆。
 * 入堆后通过调整保证两堆元素数不超过1即可。
 * 时：O(N)；空：addNum-O(logN),findMedian-O(1)
 *
 * @author lbli
 */
class Leetcode295MedianFinder {

    public static void main(String[] args) {
        Leetcode295MedianFinder leetcode295MedianFinder = new Leetcode295MedianFinder();
        leetcode295MedianFinder.addNum(-1);
        leetcode295MedianFinder.addNum(-2);
        leetcode295MedianFinder.addNum(-3);
//        leetcode295MedianFinder.addNum(9);
//        leetcode295MedianFinder.addNum(-1);
//        leetcode295MedianFinder.addNum(-99);
        double median = leetcode295MedianFinder.findMedian();
        System.out.println(median);
    }

    private final PriorityQueue<Integer> rMin, lMax;

    public Leetcode295MedianFinder() {
        rMin = new PriorityQueue<>(Comparator.naturalOrder());
        lMax = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
    }

    public void addNum(int num) {
        if (rMin.isEmpty() && lMax.isEmpty()) {
            lMax.add(num);
            return;
        }

        if (num <= findMedian()) {
            lMax.add(num);
            if (lMax.size() - rMin.size() > 1) {
                rMin.add(lMax.poll());
            }
        } else {
            rMin.add(num);
            if (rMin.size() > lMax.size()) {
                lMax.add(rMin.poll());
            }
        }

    }

    @SuppressWarnings("all")
    public double findMedian() {
        if (rMin.size() != lMax.size()) {
            return lMax.peek() * 1.0;
        } else {
            return (rMin.peek() + lMax.peek()) / 2.0;
        }

    }

}
