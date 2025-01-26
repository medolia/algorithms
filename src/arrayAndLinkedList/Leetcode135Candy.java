package arrayAndLinkedList;

/**
 * <a href="https://leetcode.cn/problems/candy/description/">135. 分发糖果</a>
 * <p>
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * <p>
 * 你需要按照以下要求，给这些孩子分发糖果：
 * <p>
 * 每个孩子至少分配到 1 个糖果。
 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
 * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
 * <p>
 * 思路：两次遍历，第一次满足规则 candy[i] > candy[i-1] 第二次满足规则 candy[i] > candy[i+1]
 * <p>时间复杂度：O(N)；空间复杂度：O(1)
 */
class Leetcode135Candy {

    public static void main(String[] args) {
//        int[] ratings = {1, 5, 4, 3, 2, 1, 1};
//        int[] ratings = {1, 5, 4, 3, 2, 1, 1};
        int[] ratings = {1, 0, 2};
        int candy = new Leetcode135Candy().candy(ratings);
        System.out.println(candy);
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }

}
