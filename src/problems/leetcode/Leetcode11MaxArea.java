package problems.leetcode;

/**
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * <a href="https://leetcode.cn/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-100-liked">11. 盛最多水的容器</a>
 * <p>
 * 思路：双指针，证明每次挪动较小的指针总是较优解：每次挪动较大值时，不论下一个值多大都不会大于当前值。
 *
 * @author lbli
 */
class Leetcode11MaxArea {

    public static void main(String[] args) {
        int maxArea = new Leetcode11MaxArea().maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7});
        System.out.println(maxArea);
    }

    public int maxArea(int[] height) {
        int result = -1;

        int l = 0, r = height.length - 1;
        while (l < r) {
            int currV = (r - l) * Math.min(height[l], height[r]);
            result = Math.max(result, currV);

            if (height[l] <= height[r]) {
                l++;
            } else {
                r--;
            }
        }

        return result;
    }

}
