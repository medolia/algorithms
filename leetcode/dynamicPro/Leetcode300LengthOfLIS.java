package dynamicPro;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/longest-increasing-subsequence/description/?envType=study-plan-v2&envId=top-100-liked">300. 最长递增子序列</a>
 * <p>
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的
 * 子序列
 * <p>
 * 思路：贪心算法 + 二分查找
 * <p>
 * 为什么适用贪心：尽可能地使前面的数小即可获得尽可能长的子序列，局部最优可以推导值全局最优
 * <p>
 * 二分查找：给定数找到最小的索引
 * <p>
 * 时：O(NlogN)；空：O(N)
 *
 * @author lbli
 */
class Leetcode300LengthOfLIS {

    public static void main(String[] args) {
        Leetcode300LengthOfLIS leetcode300LengthOfLIS = new Leetcode300LengthOfLIS();
//        leetcode300LengthOfLIS.binarySearch(List.of(1), 6);


        int result = leetcode300LengthOfLIS.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18});
        System.out.println(result);
    }

    public int lengthOfLIS(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int num : nums) {
            int optimizedIdx = binarySearchForMinIdx(result, num);
            if (optimizedIdx >= result.size()) {
                result.add(num);
            } else {
                result.set(optimizedIdx, num);
            }
        }

        return result.size();
    }

    /**
     * 尝试找插入的最低索引，特殊定制的二分查找
     */
    private int binarySearchForMinIdx(List<Integer> result, int num) {
        if (result.isEmpty()) {
            return 1;
        }

        // 左右闭区间，结束循环后，l 就是最小索引位置
        int l = 0, r = result.size() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (num < result.get(mid)) {
                r = mid - 1;
            } else if (num == result.get(mid)) {
                return mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }
}
