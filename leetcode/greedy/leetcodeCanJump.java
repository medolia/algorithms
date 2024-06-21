package greedy;

import java.util.Arrays;

/**
 * 跳跃问题
 * 思路：使用贪心原则降低时间复杂度至线性，
 * 贪心原则：用尽可能小的步数到达当前索引
 * 假设索引 i 跳一步最远能到 索引 end，i 至 end 跳一步能到达的最远索引 farthest，那么区间 (end, farthest] 步数都是一样的
 */
public class leetcodeCanJump {

    public static void main(String[] args) {
        int maxResult = new leetcodeCanJump().maxResult(new int[]{1,-5,-20,4,-1,3,-6,-3}, 2);
        System.out.println(maxResult);

        boolean canReach = new leetcodeCanJump().canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 5);
        System.out.println(canReach);

        int jump = new leetcodeCanJump().jump(new int[]{2, 3, 1, 1, 4});
        System.out.println(jump);

        boolean canJump = new leetcodeCanJump().canJump(new int[]{3, 2, 1, 0, 4});
        System.out.println(canJump);
    }

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

    boolean[] memo;
    boolean found = false;

    public boolean canReach(int[] arr, int start) {

        memo = new boolean[arr.length];
        bfs(arr, start);

        return found;
    }

    private void bfs(int[] arr, int curr) {
        if (found || curr < 0 || curr >= arr.length) {
            return;
        }

        if (arr[curr] == 0) {
            found = true;
            return;
        }

        if (memo[curr]) {
            return;
        }
        memo[curr] = true;

        bfs(arr, curr - arr[curr]);
        bfs(arr, curr + arr[curr]);
    }

    public int maxResult(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int beforeMax = nums[0];

        int l = 1, r = k;

        while (nums.length > 1 && r < nums.length + k) {

            for (int i = l; i <= r; i++) {
                if (i >= nums.length) {
                    break;
                }

                max = Math.max(max, beforeMax + nums[i]);
            }
            beforeMax = max;

            l += k;
            r += k;
        }

        return beforeMax;
    }

}
