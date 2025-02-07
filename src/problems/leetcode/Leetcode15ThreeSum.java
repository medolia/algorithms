package problems.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/3sum/solutions/284681/san-shu-zhi-he-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked">三数之和</a>
 * <p>给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 * <p>你返回所有和为 0 且不重复的三元组。
 * <p>注意：答案中不可以包含重复的三元组。
 * <p>
 * 思路：排序后双指针
 * <p>时：O(N^2)，数据有序所以指针移动是有固定方向的；空：O(logN)，用于排序
 * @author lbli
 */
class Leetcode15ThreeSum {

    public static void main(String[] args) {
        List<List<Integer>> result = new Leetcode15ThreeSum().threeSum(new int[]{1,-1,-1,0});
        System.out.println(result);

        List<List<Integer>> result1 = new Leetcode15ThreeSum().threeSum(new int[]{-1,0,1,2,-1,-4});
        System.out.println(result1);
    }

    @SuppressWarnings("all")
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int l = 0; l < nums.length - 2; l++) {
            // 最左指针小于 0 才可能有解
            if (nums[l] > 0) break;
            // 去重
            if (l > 0 && nums[l] == nums[l - 1]) continue;

            int mid = l + 1, r = nums.length - 1;

            while (mid < r) {
                int sum = nums[l] + nums[mid] + nums[r];
                // 最左指针不动的前提下，其余两个指针都要移动才可能找到下一个解
                if (sum == 0) {
                    res.add(Arrays.asList(nums[l], nums[mid], nums[r]));
                    while (mid < r && nums[mid] == nums[++mid]);
                    while (mid < r && nums[r] == nums[--r]);
                } else if (sum < 0) {
                    while (mid < r && nums[mid] == nums[++mid]);
                } else {
                    while (mid < r && nums[r] == nums[--r]) ;
                }
            }
        }

        return res;
    }

}
