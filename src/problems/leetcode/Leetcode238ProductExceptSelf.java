package problems.leetcode;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/product-of-array-except-self/description/?envType=study-plan-v2&envId=top-100-liked">除自身以外数组的乘积</a>
 * <p>思路：正向逆向两次扫描前缀积</p>
 * <p>例子：数组[1,2,3,4] 正向扫描：[1,2,6,24]，逆向扫描：[24,24,12,4]
 * <p>时：O(N)两次扫描；空：O(N)存储前缀积
 *
 * @author lbli
 */
class Leetcode238ProductExceptSelf {

    public static void main(String[] args) {
        int[] res = new Leetcode238ProductExceptSelf().productExceptSelf(new int[]{0, 0});
        System.out.println(Arrays.toString(res));
    }

    public int[] productExceptSelf(int[] nums) {
        int[] lAcc = new int[nums.length], rAcc = new int[nums.length];

        int acc = 1;
        for (int i = 0; i < nums.length; i++) {
            acc *= nums[i];
            lAcc[i] = acc;
        }

        acc = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            acc *= nums[i];
            rAcc[i] = acc;
        }

        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int tmp = 1;
            if (i > 0) {
                tmp *= lAcc[i - 1];
            }
            if (i < nums.length - 1) {
                tmp *= rAcc[i + 1];
            }
            res[i] = tmp;
        }

        return res;
    }

}
