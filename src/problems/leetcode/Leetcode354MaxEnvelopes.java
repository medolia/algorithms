package problems.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <a href="https://leetcode.cn/problems/russian-doll-envelopes/">354. 俄罗斯套娃信封问题</a>
 * <p>
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * <p>
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * <p>
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * <p>
 * 注意：不允许旋转信封。
 * <p>
 * 思路：将信封按宽度升序->高度降序排序，转变成高度数组的一维LIS问题
 * 时：O(NlogN)；空：O(N)
 *
 * @author lbli
 * @see Leetcode300LengthOfLIS
 */
class Leetcode354MaxEnvelopes {

    public static void main(String[] args) {
        int result = new Leetcode354MaxEnvelopes().maxEnvelopes(new int[][]{{4, 5}, {4, 6}, {6, 7}, {2, 3}, {1, 1}});
        System.out.println(result);
    }

    public int maxEnvelopes(int[][] envelopes) {
        Comparator<int[]> weightAscThenHeightDesc = Comparator
                .<int[]>comparingInt(o -> o[0])
                // 宽度相同高度要降序防止宽度相同高度不同的两个信封也被视为可嵌套，反例： [[3,5] [3,6]] 结果会是2
                .thenComparing(o -> -o[1]);
        Arrays.sort(envelopes, weightAscThenHeightDesc);

        int[] heightArr = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            heightArr[i] = envelopes[i][1];
        }

        return new Leetcode300LengthOfLIS().lengthOfLIS(heightArr);
    }

}
