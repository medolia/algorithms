package sortAndSearch;

/**
 * <a href="https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/?envType=study-plan-v2&envId=top-100-liked">153. 寻找旋转排序数组中的最小值</a>
 * <p>
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * <p>
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * <p>
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 思路：二分查找模板，由于数组数必定不相同，所以可以通过比较中间值和右边值确定下一步区间
 *
 * @author lbli
 */
class Leetcode153FindMin {

    public static void main(String[] args) {
        int result = new Leetcode153FindMin().findMin(new int[]{4, 5, 6, 7, 0, 1, 2});
        System.out.println(result);
    }

    public int findMin(int[] nums) {

        int l = 0, r = nums.length - 1;

        // l==r 时收敛
        while (l < r) {

            int mid = l + (r - l) / 2;
            // 中间值比右边值大，说明”谷底“在右边
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return nums[l];
    }

}
