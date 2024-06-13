package greedy;

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

    /**
     * 45. 跳跃游戏 II
     * <p>
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     * 假设你总是可以到达数组的最后一个位置。
     * <p>
     * 思路：贪心算法；
     * 常规的动态规划时间复杂度为 n^2 ，可用贪心算法优化为线性速度；
     * 作下列思考：
     * 假设当前起点为 start，可以到达的终点为 end，当前已跳跃次数为 jump；
     * 可以发现，从 start 至 end 的所有值都对应跳跃次数 jump （因为要取最小次数）；
     * 记录从 start 至 end 能到达的最远地方 farthest，则 end + 1 至 farthes 对应跳跃次数 jump + 1；
     * <p>
     * 优化思路：可以使用一个 end 变量记录当前跳跃次数能到达的最远距离；
     * farthest 对应当前跳跃次数后再跳一次能到达的最远距离；jump 记录跳跃次数；
     * 那么遍历过程中，i 在 end 之前一直对应的是当前跳跃次数，直至到达当前跳跃次数所能到达的最远距离 end，这时候需要
     * 进入下一次跳跃：++jump 然后更新 end 边界 end = farthest 即可；
     */
    public int jump(int[] nums) {
        int farthest = 0, n = nums.length;
        int end = 0, jump = 0;
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (end == i) {
                ++jump;
                end = farthest;
            }
        }

        return jump;
    }

    /**
     * 55. 跳跃游戏
     *
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个位置。
     *
     * 思路：类似 45 的贪心算法；
     */
    public boolean canJump(int[] nums) {
        int farthest = 0;
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            // 更新当前能到达的最远位置；
            farthest = Math.max(i + nums[i], farthest);
            // 当前到达最远的地方就是当前位置，说明遇到 0 了；
            if (farthest <= i) return false;
        }

        return farthest >= n - 1;
    }

}
