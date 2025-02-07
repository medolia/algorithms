package problems.leetcode;

/**
 * 给你一个整数数组 nums。
 * <p>
 * 返回两个（不一定不同的）质数在 nums 中 下标 的 最大距离。
 * <p>
 * 思路：双指针即可。如果数值有范围可以考虑存储质数判断
 */
public class Leetcode3115MaximumPrimeDifference {

    public static void main(String[] args) {
        int[] nums = {4, 2, 9, 5, 3};
        int res = new Leetcode3115MaximumPrimeDifference().maximumPrimeDifference(nums);
        System.out.println(res);
    }

    public int maximumPrimeDifference(int[] nums) {
        int first = -1;
        int res = 0;

        for (int i = 0; i < nums.length; i++) {
            if (isPrime(nums[i])) {

                if (first == -1) {
                    first = i;
                } else {
                    res = Math.max(res, i - first);
                }
            }
        }

        return res;
    }

    private boolean isPrime(int num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}
