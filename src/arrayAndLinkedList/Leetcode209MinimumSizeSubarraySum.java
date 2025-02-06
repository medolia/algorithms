package arrayAndLinkedList;

/**
 * <a href="https://leetcode.cn/problems/minimum-size-subarray-sum/description/?envType=company&envId=pinduoduo&favoriteSlug=pinduoduo-six-months">209. 长度最小的子数组</a>
 * <p>给定一个含有 n 个正整数的数组和一个正整数 target 。
 * <p>
 * 找出该数组中满足其总和大于等于 target 的长度最小的
 * 子数组
 * [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
 * <p>思路：滑动窗口（假定解存在，那么对应的右边界一定会遍历到），时空复杂度：O(N),O(1)</p>
 */
class Leetcode209MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int res = new Leetcode209MinimumSizeSubarraySum().minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        System.out.println(res);
    }

    public int minSubArrayLen(int target, int[] nums) {
        int l = 0, r = 0;
        int sum = 0;
        int res = nums.length + 1;

        while (r < nums.length) {
            sum += nums[r];
            while (sum >= target) {
                res = Math.min(res, r - l + 1);
                sum -= nums[l];
                l++;
            }

            r++;
        }

        return res > nums.length ? 0 : res;
    }

}
