package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/jump-game-iii/description/">1306. 跳跃游戏 III</a>
 * <p>
 * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
 * <p>
 * 请你判断自己是否能够跳到对应元素值为 0 的 任一 下标处。
 * <p>
 * 注意，不管是什么情况下，你都无法跳到数组之外。
 * <p>
 * 思路：BFS
 * <p>时：O(N)，空：O(N)
 *
 * @author lbli
 */
class Leetcode1306CanReach {

    public static void main(String[] args) {
        boolean canReach = new Leetcode1306CanReach().canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 5);
        System.out.println(canReach);

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

}
