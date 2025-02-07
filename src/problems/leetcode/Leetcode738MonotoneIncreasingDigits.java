package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/monotone-increasing-digits/description/?envType=company&envId=huawei&favoriteSlug=huawei-six-months">738. 单调递增的数字</a>
 * <p>
 * 题目描述: <br>
 * 当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。
 * <p>
 * 给定一个整数 n ，返回 小于或等于 n 的最大数字，且数字呈 单调递增 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: n = 10
 * 输出: 9
 * 示例 2:
 * <p>
 * 输入: n = 1234
 * 输出: 1234
 * 示例 3:
 * <p>
 * 输入: n = 332
 * 输出: 299
 * <p>
 * <p>思路: 贪心原则，首先拿到各位数组成的数组，先向右找第一个不满足递增的位置，再向左找位数最小自减一的位置，自减位之后的均为9。
 * <p>例1：23492 -> 第一个不满足的为十位 '9'，最小自减也是这位，结果为 23489；
 * <p>例2：19990 -> 第一个不满足的为十位 '9'，最小自减为千位，结果为 18999；
 *
 * <p>
 * 时间复杂度:O(log N) 将数转变为数组再遍历，所以为指数复杂度。
 * <p>
 * 空间复杂度:O(1)
 */

class Leetcode738MonotoneIncreasingDigits {

    public static void main(String[] args) {
        int[] tests = new int[]{120, 1234, 10, 19990, 23492};

        for (int test : tests) {
            System.out.println(new Leetcode738MonotoneIncreasingDigits().monotoneIncreasingDigits(test));
        }
    }

    public int monotoneIncreasingDigits(int n) {
        // 实际为方便左右多了保险数，10 -> [-1, 1, 0, 10]
        int[] numList = convert(n);
        int i = 1;
        // 寻找第一个不满足递增的位置
        while (i < numList.length - 1 && numList[i] <= numList[i + 1]) {
            i++;
        }
        if (i >= numList.length - 2) {
            return n;
        }

        // 寻找最小位数的自减位数
        while (i > 1 && numList[i] - 1 < numList[i - 1]) {
            i--;
        }
        numList[i]--;

        int res = 0;
        for (int j = 1; j < numList.length - 1; j++) {
            int num = j > i ? 9 : numList[j];
            res = res * 10 + num;
        }

        return res;
    }

    private int[] convert(int n) {
        char[] charArray = String.valueOf(n).toCharArray();
        int[] res = new int[charArray.length + 2];
        for (int i = 1; i <= charArray.length; i++) {
            res[i] = charArray[i - 1] - '0';
        }
        res[0] = -1;
        res[charArray.length + 1] = 10;
        return res;
    }

}
