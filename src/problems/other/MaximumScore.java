package problems.other;

/**
 * <a href="https://leetcode.cn/problems/maximum-score-of-a-good-subarray/description/">1793. 好子数组的最大分数</a>
 *
 * @author lbli
 */
public class MaximumScore {

    public static void main(String[] args) {
//        new MaximumScore().maximumScore(new int[]{1, 4, 3, 7, 4, 5}, 3);
        new MaximumScore().maximumScore(new int[]{5, 5, 4, 5, 4, 1, 1, 1}, 0);
    }

    public int maximumScore(int[] nums, int k) {
        int l = k, r = k;
        int result = -1;
        int pivot = nums[k];
        while (pivot > 0) {
            while (l >= 0 && nums[l] >= pivot) {
                l--;
            }

            while (r < nums.length && nums[r] >= pivot) {
                r++;
            }

            result = Math.max(result, (r - l + 1 - 2) * pivot);

            if (l < 0 && r >= nums.length) {
                break;
            }

            pivot = Math.max(l < 0 ? -1 : nums[l], r >= nums.length ? -1 : nums[r]);
        }

        return result;
    }

}
