package arrayAndLinkedList;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <a href="https://leetcode.cn/problems/trapping-rain-water/">url</a>
 * 思路：双指针，每个格子的贡献其实只与左右边的最高高度的较小值有关
 * @author lbli
 */
class Leetcode42Trap {

    public static void main(String[] args) {
        int result = new Leetcode42Trap().trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        System.out.println(result);
    }

    public int trap(int[] height) {
        int lPtr = 0, rPtr = height.length - 1;
        int lMax = height[0], rMax = height[rPtr];

        int result = 0;

        while (lPtr <= rPtr) {
            lMax = Math.max(lMax, height[lPtr]);
            rMax = Math.max(rMax, height[rPtr]);

            if (lMax <= rMax) {
                result += lMax - height[lPtr++];
            } else {
                result += rMax - height[rPtr--];
            }
        }

        return result;
    }

}
