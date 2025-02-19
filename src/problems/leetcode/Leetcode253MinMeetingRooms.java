package problems.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <a href="https://leetcode.cn/problems/meeting-rooms-ii/solutions/2392308/hui-yi-shi-ii-by-leetcode-solution-fzxq/">253. 会议室 II</a> <br>
 * 题目描述:
 * intervals[i] = [starti, endi] ，返回 所需会议室的最小数量 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：intervals = [[0,30],[5,10],[15,20]]
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：intervals = [[7,10],[2,4]]
 * 输出：1
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= intervals.length <= 104
 * 0 <= starti < endi <= 106
 * <p>
 */

class Leetcode253MinMeetingRooms {

    public int minMeetingRooms(int[][] intervals) {

        // 检查基本情况。如果没有间隔，返回 0
        if (intervals.length == 0) {
            return 0;
        }

        // 最小堆
        PriorityQueue<Integer> allocator =
                new PriorityQueue<Integer>(
                        intervals.length,
                        new Comparator<Integer>() {
                            public int compare(Integer a, Integer b) {
                                return a - b;
                            }
                        });

        // 根据开始时间排序会议
        Arrays.sort(
                intervals,
                new Comparator<int[]>() {
                    public int compare(final int[] a, final int[] b) {
                        return a[0] - b[0];
                    }
                });

        // 添加第一场会议
        allocator.add(intervals[0][1]);

        // 遍历剩余会议
        for (int i = 1; i < intervals.length; i++) {

            // 如果最早应该腾出的房间是空闲的，则将该房间分配给本次会议。
            if (intervals[i][0] >= allocator.peek()) {
                allocator.poll();
            }

            // 如果要分配一个新房间，那么我们也要添加到堆中，
            // 如果分配了一个旧房间，那么我们还必须添加到具有更新的结束时间的堆中。
            allocator.add(intervals[i][1]);
        }

        // 堆的大小告诉我们所有会议所需的最小房间。
        return allocator.size();
    }

}
