package greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <a href="https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/">452. 用最少数量的箭引爆气球</a>
 * <p>
 * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。
 * <p>
 * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
 * <p>
 * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
 * <p>
 * 思路：区间调度问题，贪心思路。end 升序排列，只要后续的区间与当前区间相交（正好区间相接也算）即可一条弓箭引爆。
 * <p>时：O(NlogN)，需要排序一次 空：O(1)
 */
class Leetcode452FindMinArrowShots {

    public static void main(String[] args) {
//        int[][] points = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
//        int[][] points = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};
        int[][] points = {{-2147483648, 2147483647}};
        int minArrowShots = new Leetcode452FindMinArrowShots().findMinArrowShots(points);
        System.out.println(minArrowShots);
    }

    public int findMinArrowShots(int[][] points) {

        Arrays.sort(points, Comparator.comparingInt(e -> e[1]));

        int result = 0;
        int currR = Integer.MIN_VALUE;
        boolean first = true;
        for (int[] point : points) {
            int start = point[0], end = point[1];

            if (!first && start <= currR) {
                continue;
            }

            first = false;
            result++;
            currR = end;
        }

        return result;
    }

}
