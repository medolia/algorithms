package problems.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * <a href="https://leetcode.cn/problems/longest-consecutive-sequence/solutions/276931/zui-chang-lian-xu-xu-lie-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked">128. 最长连续序列</a>
 * <p>
 * 思路：先入哈希集合，再遍历查询，使用备忘录略过重复计算。比如 num=7 时，如果 num=6 已有数据直接跳过即可。
 * 时：O(n)，空：O(n)
 * @author lbli
 */
class Leetcode128LongestConsecutive {

    public static void main(String[] args) {
//        int[] nums = {100, 4, 200, 1, 3, 2};
        int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        int result = new Leetcode128LongestConsecutive().longestConsecutive(nums);
        System.out.println(result);
    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        Map<Integer, Integer> memo = new HashMap<>();

        int result = 0;
        for (Integer num : set) {
            if (memo.containsKey(num)) {
                continue;
            }

            int tmp = num;

            int currLength = 0;
            while (set.contains(num)) {
                if (memo.containsKey(num)) {
                    currLength += memo.get(num);
                    num -= memo.get(num);
                } else {
                    currLength++;
                    num -= 1;
                }
            }

            memo.put(tmp, currLength);

            result = Math.max(result, currLength);
        }

        return result;
    }

}
