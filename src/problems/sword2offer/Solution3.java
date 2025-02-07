package problems.sword2offer;

import java.util.*;

public class Solution3 {
    /**
     * 剑指 Offer 45. 把数组排成最小的数
     * <p>
     * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * <p>
     * 思路：本质是排序问题，排序规则为若字符串 x+y < y+x，则 x < y
     * 例：3 与 30，"303" < "330"，所以 30 < 3
     * 证明1. 排序规则满足自反、对称、传递性
     * 证明2. 反证证明以此法排序获得的数的确是最小值
     */
    public String minNumber(int[] nums) {
        String[] vals = new String[nums.length];

        for (int i = 0; i < nums.length; i++)
            vals[i] = String.valueOf(nums[i]);

        Arrays.sort(vals, (x, y) -> (x + y).compareTo(y + x));

        StringBuilder res = new StringBuilder();
        for (String val : vals)
            res.append(val);

        return res.toString();
    }

    /**
     * 剑指 Offer 51. 数组中的逆序对
     * <p>
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆
     * 序对。输入一个数组，求出这个数组中的逆序对的总数。
     * <p>
     * 思路：将计数更新部分插入到数组归并过程中
     */
    public int reversePairs(int[] nums) {
        tmp = new int[nums.length];
        return reversePairs(nums, 0, nums.length - 1);
    }

    int reversePairs(int[] nums, int left, int right) {
        if (left >= right) return 0;

        int mid = left + (right - left) / 2;

        int leftPairs = reversePairs(nums, left, mid);
        int rightPairs = reversePairs(nums, mid + 1, right);

        if (nums[mid] <= nums[mid + 1]) return leftPairs + rightPairs;

        int crossPairs = mergeAndCount(nums, left, mid, right);
        return leftPairs + rightPairs + crossPairs;
    }

    /**
     * 此时数组（nums）在 [left, mid] 和 [mid + 1, right] 两个区间均有
     * 序，使用三指针将两部分归并。
     */
    int[] tmp;

    int mergeAndCount(int[] nums, int left, int mid, int right) {
        for (int i = left; i <= right; i++)
            tmp[i] = nums[i];

        int i = left, j = mid + 1;
        int count = 0;
        /**
         * 左边区间 [left, mid]，右边区间 [mid+1, right]
         * i、j 分别为左半、右半部分的指针
         * 1. 当有一部分已无元素时（i == mid +1 或 j == right + 1），直接将另一部分的元素置入剩余空位
         * 2. 当两部分均有元素时
         * 2.1. 左半部分 大于 右半部分，逆序对计数更新，填入右半元素，右指针 + 1
         * 2.2. 右半部分 小于等于 左半部分，填入左半部分，左指针 + 1
         *
         */
        for (int k = left; k <= right; k++) {
            if (i > mid) nums[k] = tmp[j++];
            else if (j > right) nums[k] = tmp[i++];
            else if (tmp[i] <= tmp[j]) nums[k] = tmp[i++];
            else {
                nums[k] = tmp[j++];
                count += mid - i + 1; // 计数贡献：左半部分 i 及 i 右边的元素均比当前的 j 元素大
            }
        }
        return count;
    }

    /**
     * 241. 为运算表达式设计优先级
     *
     * 给定一个含有数字和运算符的字符串，为表达式添加括号，改变其运算优先级以求出不同的结果。你需要给出所有可能的组合的结果。
     * 有效的运算符号包含 +, - 以及 * 。
     *
     * 思路：加括号的本质即为以某一运算符为界先计算两遍再结算运算符，考虑使用分治递归，递归边界为任一运算符；
     * 例：若字符串长度为 9，位置索引 3 的运算符为 "-"，则 f(0,8) = [each in f(0,3)]（左结果列表） - [each in f(4, 8)]（右结果列表）；
     * base case：当遍历后结果列表为空时（即先前遍历没有遇到运算符），说明此时的字符串为不含运算符的单个数字，直接返回其值即可；
     */
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i<input.length(); i++) {
            char curr = input.charAt(i);
            if (curr == '+' || curr == '-' || curr == '*') {
                List<Integer> left = diffWaysToCompute(input.substring(0, i));
                List<Integer> right = diffWaysToCompute(input.substring(i+1));

                for (int eachL : left)
                    for (int eachR : right) {
                        if (curr == '+') res.add(eachL + eachR);
                        else if (curr == '-') res.add(eachL - eachR);
                        else res.add(eachL * eachR);
                    }
            }
        }

        if (res.isEmpty()) res.add(Integer.valueOf(input));

        return res;
    }

    /**
     * 300. 最长递增子序列
     * <p>
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
     * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * <p>
     * 思路：经典的耐心排序（patience sorting）问题，以扑克牌为例：
     * 遵守原则：1. 当前牌只能放置在牌堆顶点数比自己大的牌堆顶上，多个堆可选择时选择最左堆；
     * 2. 如果没有合适的牌堆顶，在新建一个牌堆顶放置之；
     * 这样放置完所有的牌后，牌堆数即为最长递增子序列的长度；
     */
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int res = 0;

        for (int num : nums) {
            int l = 0, r = res;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (tails[m] < num) l = m + 1;
                else r = m; // 要求左边界，需要将等号并入右边界变更条件
            }
            if (res == l) ++res;
            tails[l] = num;
        }

        return res;
    }

    /**
     * 354. 俄罗斯套娃信封问题
     * <p>
     * 给定一些标记了宽度和高度的信封，宽度和高度以整数对形式(w, h)现。当另一个信封的宽度和高度都比这个信封大
     * 的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
     * 请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
     * <p>
     * 思路：先按宽度升序、高度降序排序，然后寻找高度数组中最长递增子序列（不需要连续）；
     */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (int[] rec1, int[] rec2) ->
                rec1[0] == rec2[0] ? rec2[1] - rec1[1] : rec1[0] - rec2[0]);

        int len = envelopes.length;
        int[] height = new int[len];
        for (int i = 0; i < len; i++)
            height[i] = envelopes[i][1];

        return lengthOfLIS(height);
    }

    /**
     * 875. 爱吃香蕉的珂珂
     *
     * 珂珂喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
     * 珂珂可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有
     * 香蕉，然后这一小时内不会再吃更多的香蕉。珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
     *
     * 思路：二分查找，l 为 1，r 为 max[piles]；
     */
    public int minEatingSpeed(int[] piles, int H) {
        int max = 0;

        for (int num : piles)
            max = Math.max(max, num);

        int l = 1, r = max;

        while (l <= r) {
            int mid = (l+r) / 2;
            if (canFinish(mid, piles, H))
                r = mid - 1;
            else l = mid + 1;
        }

        return l;
    }

    boolean canFinish(int speed, int[] piles, int H) {
        int time = 0;

        for (int num : piles)
            time += (num / speed) + (num % speed > 0 ? 1 : 0);

        return time <= H;
    }

    void findBound(int[] nums, int tgt) {
        int l = 0, r = nums.length;

        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] > 7) r = mid;
            else if (nums[mid] < 7) l = mid + 1;
            else if (nums[mid] == 7) r = mid;
        }

        System.out.printf("left_bdr for 7 : %d \n", l);

        l = 0;
        r = nums.length;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] > 7) r = mid;
            else if (nums[mid] < 7) l = mid + 1;
            else if (nums[mid] == 7) l = mid + 1;
        }

        System.out.printf("right_bdr for 7 : %d", l - 1);
    }

    public static void main(String[] args) {
        new Solution3().findBound(new int[] {1,2,3,7,7,7,7,7,8,9,10}, 7);
    }

}
