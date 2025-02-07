package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/first-missing-positive/description/?envType=study-plan-v2&envId=top-100-liked">41. 缺失的第一个正数</a>
 * <p>给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * <p>请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 思路：长度为 N 的数组对于结果必定落在区间 [1, N+1] 中，所以可以通过标记找出答案
 * <p>时：O(N)，扫描三遍；空：O(1)，但破坏了原数组。
 *
 * @author lbli
 */
class Leetcode41FirstMissingPositive {

    public static void main(String[] args) {
        int res = new Leetcode41FirstMissingPositive().firstMissingPositive(new int[]{3, 4, -1, 1});
        System.out.println(res);
    }

    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        // 1. 所有负数全部使用一个较大正数作为标记
        for (int i = 0; i < len; i++) {
            if (nums[i] <= 0) {
                nums[i] = len + 1;
            }
        }

        // 2. 通过数组下标标记对应已有的整数
        for (int i = 0; i < len; i++) {
            // 取绝对值，因为可能此位置被标记/翻转过
            int absNum = Math.abs(nums[i]);
            if (absNum <= len) {
                nums[absNum - 1] = -Math.abs(nums[absNum - 1]);
            }
        }

        // 3. 找到首个没有标记的位置，答案为下标 + 1
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }

        return len + 1;
    }

}
