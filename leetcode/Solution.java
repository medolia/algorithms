import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public static void main(String[] args) {
        int[] nums = {3, -2, -1, 0, 1, 2, -3};
//        int[] nums = {0, 0, 0, 0,0};
//        int[] nums = {-3, -2, -1, 0, 1, 2, 3};
//        int[] nums = {-3, -2, -1, 0, 1, 2, 3};
        int[][] res = new Solution().threeSum(nums);
        System.out.println(Arrays.toString(res));
    }

    /**
     * 给定一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
     * 满足 nums[i] + nums[j] + nums[k] == 0 。请返回所有和为 0 且不重复的三元组.
     */
    int[][] threeSum(int[] nums) {
        Arrays.sort(nums);

        List<int[]> resList = new ArrayList<>();

        // 三指针，i左 j中间 k右
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int k = nums.length - 1; k >= 1; k--) {

                if (k < nums.length - 1 && nums[k] == nums[k + 1]) {
                    continue;
                }

                for (int j = i + 1; j < k; j++) {

                    if (j > 0 && nums[j] == nums[j - 1]) {
                        continue;
                    }

                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum == 0) {
                        resList.add(new int[]{nums[i], nums[j], nums[k]});
                    } else if (sum > 0) { // 指针移动只能增加 sum，sum 已经为正直接中断循环就好
                        break;
                    }
                }
            }
        }

        return resList.toArray(new int[][]{});
    }


}
