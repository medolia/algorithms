package problems.leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class GreedySolution {
    /**
     * 435. 无重叠区间
     * <p>
     * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
     * <p>
     * 思路：贪心 - 算法区间调度，先按右边界升序排序，将当前右边界定义为首个区间的右边界；
     * 遍历剩余的区间，若：
     * 1. 该区间左边界小于当前右边界，产生重叠需要删除；
     * 2. 该区间左边界大于等于当前右边界，不产生重叠，更新右边界为此区间右边界；
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1) return 0;
        Arrays.sort(intervals, Comparator.comparingInt((int[] inter) -> inter[1]));

        int currEnd = intervals[0][1];
        int count = -1;
        for (int[] interval : intervals) {
            int start = interval[0];
            if (start < currEnd) {
                count++;
            } else currEnd = interval[1];
        }

        return count;
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * <p>
     * 在二维空间中有许多球形的气球。对于每个气球，提供的输入是水平方向上，气球直径的开始和结束坐标。由于它是水
     * 平的，所以纵坐标并不重要，因此只要知道开始和结束的横坐标就足够了。开始坐标总是小于结束坐标。
     * 一支弓箭可以沿着 x 轴从不同点完全垂直地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标
     * 为 xstart，xend， 且满足 xstart ≤ x ≤ xend，则该气球会被引爆。可以射出的弓箭的数量没有限制。 弓箭
     * 一旦被射出之后，可以无限地前进。我们想找到使得所有气球全部被引爆，所需的弓箭的最小数量。
     * <p>
     * 思路：贪心算法 - 区间调度问题变体；
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length <= 1) return points.length;
        Arrays.sort(points, Comparator.comparingInt((int[] inter) -> inter[1]));

        int count = points.length + 1;
        int currEnd = points[0][1];
        for (int[] point : points) {
            int start = point[0];
            if (start <= currEnd) {
                --count;
            } else currEnd = point[1];
        }

        return count;
    }

}
