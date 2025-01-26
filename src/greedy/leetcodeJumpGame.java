package greedy;

/**
 * 跳跃问题
 * <p>
 * 思路：使用贪心原则降低时间复杂度至线性，
 * <p>
 * 贪心原则：用尽可能小的步数到达当前索引
 * <p>
 * 假设索引 i 跳一步最远能到 索引 end，i 至 end 跳一步能到达的最远索引 farthest，那么区间 (end, farthest] 步数都是一样的
 * <p>时：O(N)，遍历一次即可；空：O(1)，三个变量-i,end,farthest
 */
class leetcodeJumpGame {

    public static void main(String[] args) {

        int jump = new leetcodeJumpGame().jump(new int[]{2, 3, 1, 1, 4});
        System.out.println(jump);

        boolean canJump = new leetcodeJumpGame().canJump(new int[]{3, 2, 1, 0, 4});
        System.out.println(canJump);
    }

    /**
     * <a href="https://leetcode.cn/problems/jump-game/description/">55. 跳跃游戏</a>
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * <p>
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     */
    public boolean canJump(int[] nums) {
        // 从 i 最远能到 end
        int end = 0;
        // 从索引位置区间 [i,end] 最远能到索引 farthest
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // 下一次跳跃
            if (end == i) {
                end = farthest;
            }

            // 可达的最远处不超过当前索引，说明没可能到终点了
            if (farthest <= i) {
                return false;
            }
        }

        return farthest >= nums.length - 1;
    }

    /**
     * <a href="https://leetcode.cn/problems/jump-game-ii/description/">45. 跳跃游戏 II</a>
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     * <p>
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     * <p>
     * 0 <= j <= nums[i]
     * <p>
     * i + j < n
     * <p>
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     */
    public int jump(int[] nums) {
        int end = 0, jumpCnt = 0, farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {

            farthest = Math.max(farthest, i + nums[i]);

            if (end == i) {
                ++jumpCnt;
                end = farthest;
            }

            if (farthest <= i) {
                return -1;
            }

        }

        return jumpCnt;
    }

}
