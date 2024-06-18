package arrayAndLinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/QTMn0o/description/">LCR 010. 和为 K 的子数组</a>
 * <p>
 * 给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。
 * <p>
 * 思路：前缀和，使用哈希表维护前缀和计数，遍历到元素 num 时如果哈希表已有 num-k 的键则代表索引 num 至索引 num-k 为一个有效结果
 * <p>时：O(N)；空：O(N)
 * @author lbli
 */
class LCR010SubarraySum {

    public static void main(String[] args) {
        int result = new LCR010SubarraySum().subarraySum(new int[]{1, -1, 0}, 0);
        System.out.println(result);
    }

    public int subarraySum(int[] nums, int k) {
        int result = 0;
        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0, 1);

        int currSum = 0;
        for (int num : nums) {
            currSum += num;

            if (sumCount.containsKey(currSum - k)) {
                result += sumCount.get(currSum - k);
            }

            sumCount.merge(currSum, 1, Integer::sum);
        }

        return result;
    }

}
