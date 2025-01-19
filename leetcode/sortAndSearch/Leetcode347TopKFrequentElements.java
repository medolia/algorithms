package sortAndSearch;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/top-k-frequent-elements/description/?envType=study-plan-v2&envId=top-100-liked">347. 前 K 个高频元素</a>
 * <br>给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 */
class Leetcode347TopKFrequentElements {

    public static void main(String[] args) {
        int[] res = new Leetcode347TopKFrequentElements().topKFrequent(
                new int[]{1, 1, 1, 2, 2, 3}, 2
        );
        System.out.println(Arrays.toString(res));
    }


    /**
     * 思路：桶排序，得到频率计数map后将频率作为下标落库空壳数组，从右向左遍历k个元素得到答案。
     * <br>时间：N 空间：N</>
     */
    int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }


        List<Integer>[] buckets = new List[nums.length + 1];
        map.forEach((num, count) -> {

            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(num);
        });

        List<Integer> res = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && res.size() < k; i--) {
            List<Integer> curr = buckets[i];
            if (curr != null) {
                res.addAll(curr);
            }
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

}
