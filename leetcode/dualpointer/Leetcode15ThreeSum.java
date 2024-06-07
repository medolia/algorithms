package dualpointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lbli
 */
public class Leetcode15ThreeSum {

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
            if (nums[l] > 0) break;
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
