package arrayAndLinkedList;

/**
 * 给你一个下标从 0 开始的整数数组 nums 。如果下标对 i、j 满足 0 ≤ i < j < nums.length ，如果 nums[i] 的 第一个数字 和 nums[j] 的 最后一个数字 互质 ，则认为 nums[i] 和 nums[j] 是一组 美丽下标对 。
 * <p>
 * 返回 nums 中 美丽下标对 的总数目。
 * <p>
 * 对于两个整数 x 和 y ，如果不存在大于 1 的整数可以整除它们，则认为 x 和 y 互质 。换而言之，如果 gcd(x, y) == 1 ，则认为 x 和 y 互质，其中 gcd(x, y) 是 x 和 y 的 最大公因数 。
 * <p>
 * 思路：维护一个统计第一个数出现频率的计数数组 arr[]，一次遍历数组计算元素最后一个数与 k(k - [1,9]) 的最大公因数
 * <p>如果互质则结果数增加 arr[k] 个
 * <p>时：O(N * (10 + logU)，U为最大数，logU对应计算gcd的时间；空：O(10)
 *
 * @author lbli
 */
class Leetcode2748CountBeautifulPairs {

    public static void main(String[] args) {
        int result = new Leetcode2748CountBeautifulPairs().countBeautifulPairs(new int[]{68, 8, 84, 14, 88, 42, 53});
        System.out.println(result);
    }

    private static int firstNumOfANum(int num) {
        while (num >= 10) {
            num /= 10;
        }

        return num;
    }

    private static int lastNumOfANum(int num) {
        return num % 10;
    }

    public int countBeautifulPairs(int[] nums) {
        int res = 0;
        int[] firstNumCountArr = new int[10];

        for (int num : nums) {
            for (int y = 1; y <= 9; y++) {
                if (y == 1 || Utils.gcd(lastNumOfANum(num), y) == 1) {
                    res += firstNumCountArr[y];
                }
            }

            firstNumCountArr[firstNumOfANum(num)]++;
        }

        return res;
    }


}
