package problems.sword2offer;

import java.util.*;

public class Solution {
    /**
     * 剑指 Offer 15. 二进制中1的个数
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            ++count;
            n &= n - 1;
        }

        return count;
    }

    /**
     * 剑指 Offer 16. 数值的整数次方
     * <p>
     * 思路：将指数二进制分解，取位迭代
     * 使用 long 防止 int 指数上界溢出
     */
    public double myPow(double x, int n) {
        if (x == 0) return 0;

        long b = n;
        if (b < 0) {
            x = 1.0 / x;
            b = -b;
        }

        double res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res *= x;
            x *= x;
            b >>= 1;
        }

        return res;
    }

    /**
     * 剑指 Offer 43. 1～n 整数中 1 出现的次数
     * <p>
     * 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
     * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
     * 要求时间复杂度为 logN。
     * <p>
     * 3个例子看十位的 1 出现次数
     * 1. 2304：十位为 0，固定其为 1 时，千百位可取 00，01，...，22，个位可取 0 - 9，共 23 * 10 次；
     * 2. 2314：十位为 1，固定其为 1 时，2.1. 千百位取 00，01，...，22，个位取 0 - 9，同 1
     * 2.2. 千百位取 23，个位数只能取 0 - 4
     * 共 23 * 10 + 4 + 1 次；
     * 3. 2334：十位为 2-9，固定其为 1 时，千百位可取 00，01，...，22，23，个位可取 0 - 9，共 （23 + 1）*10 次；
     * <p>
     * 解析：https://leetcode-cn.com/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof/solution/mian-shi-ti-43-1n-zheng-shu-zhong-1-chu-xian-de-2/
     */
    public int countDigitOne(int n) {
        int digit = 1, res = 0;
        int high = n / 10, curr = n % 10, low = 0;

        while (high != 0 || curr != 0) {
            if (curr == 0) res += high * digit;
            else if (curr == 1) res += high * digit + low + 1;
            else res += (high + 1) * digit;

            low += curr * digit;
            curr = high % 10;
            high /= 10;
            digit *= 10;
        }

        return res;
    }

    /**
     * 剑指 Offer 44. 数字序列中某一位的数字
     * <p>
     * 数字以0123456789101112131415…的格式序列化到一个字符序列中。在这个序列中，第5位（从下标
     * 0开始计数）是5，第13位是1，第19位是4，等等。
     * 请写一个函数，求任意第n位对应的数字。
     * <p>
     * <p>
     * 思路：n 位数对应 共 9 * (10 ^ n) * n 位，由于给定索引，使用 (n-1) 操作可得到正确索引数和位数
     * 1. 找到索引数为几位数
     * 2. 找到索引数
     * 3. 找到索引的位
     */
    public int findNthDigit(int n) {
        int digit = 1;
        long start = 1;
        long count = 9;
        while (n > count) { // 1.
            n -= count;
            digit += 1;
            start *= 10;
            count = digit * start * 9;
        }
        long num = start + (n - 1) / digit; // 2.
        return Long.toString(num).charAt((n - 1) % digit) - '0'; // 3.
    }

    /**
     * 剑指 Offer 49. 丑数
     * <p>
     * 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
     * <p>
     * 思路：动态规划
     * 状态定义：dp[i] 为第 i+1 个丑数；
     * 状态初始化：dp[0] = 1；
     * 状态转移：dp[i] = min(dp[a] * 2, dp[b] * 3, dp[c] * 5)，a、b、c 分别为三个指向前一个状态小于
     * dp[i-1] 且 *2、*3、*5 后会大于 dp[i-1] 的数的索引位的指针，当取用了某个索引对应的值后，其对
     * 应索引需要自增；
     */
    public int nthUglyNumber(int n) {
        int[] list = new int[n];
        list[0] = 1;
        int n2 = 0, n3 = 0, n5 = 0;
        for (int i = 1; i < n; i++) {
            int x2 = list[n2] * 2, x3 = list[n3] * 3, x5 = list[n5] * 5;
            list[i] = Math.min(x2, Math.min(x3, x5));
            // 自增取得最小值的索引
            if (list[i] == x2) ++n2;
            if (list[i] == x3) ++n3;
            if (list[i] == x5) ++n5;
        }

        return list[n - 1];
    }

    /**
     * 剑指 Offer 53 - II. 0～n-1中缺失的数字
     * <p>
     * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范
     * 围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
     * <p>
     * 思路：位运算，异或运算，其满足交换律和结合律。
     */
    public int missingNumber(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; ++i)
            res ^= nums[i];

        for (int i = 0; i <= nums.length; ++i)
            res ^= i;

        return res;
    }

    /**
     * 剑指 Offer 56 - I. 数组中数字出现的次数
     * <p>
     * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一
     * 次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
     * <p>
     * 全数进行异或运算得到两个只出现一次的数的异或 tmp，然后可随意选一个对应异或位上不为0的数 x 将数组分组
     * 数组元素均与 x 异或运算会得到 0 或 非 0 两种解，两个只出现一次的数必定不在同一组，由此可区分，将 tmp
     * 分别与两组数异或得到两个目标数。
     */
    public int[] singleNumbers(int[] nums) {
        int twoNum = 0;
        for (int num : nums)
            twoNum ^= num;

        int digit = 1;
        while (true) {
            if ((twoNum & digit) != 0) break;
            digit <<= 1;
        }

        int num1 = 0, num2 = 0;
        for (int num : nums) {
            if ((num & digit) == 0)
                num1 ^= num;
            else num2 ^= num;
        }

        return new int[]{num1, num2};
    }

    /**
     * 剑指 Offer 56 - II. 数组中数字出现的次数 II
     * <p>
     * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
     * <p>
     * 思路：统计所有数的二进制位，除所求数字对应的位之外，其余所有位均能被 3 整除，使用一个数组保存
     * 位计数，之后还原即得所求数。
     */
    public int singleNumber(int[] nums) {
        int[] bitSum = new int[32];


        for (int num : nums) {
            int bit = 1;
            // 为方便还原，数组中索引 31 （最后一位）对应二进制的首位
            for (int i = 31; i >= 0; i--) {
                bitSum[i] += (bit & num) != 0 ? 1 : 0;
                bit <<= 1;
                if (num < bit) break;
            }
        }

        int res = 0;
        for (int i = 0; i <= 31; i++) {
            res <<= 1;
            res += bitSum[i] % 3;
        }

        return res;
    }

    /**
     * 剑指 Offer 57. 和为s的两个数字
     * <p>
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好
     * 是s。如果有多对数字的和等于s，则输出任意一对即可。
     * <p>
     * 思路：双指针法，最左最右两个指针，小于目标值左指针前进，大于目标值右指针后退
     * 时间复杂度 N 空间复杂度 1
     */
    public int[] twoSum(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int curr = nums[left] + nums[right];
            if (curr == target)
                return new int[]{nums[left], nums[right]};
            else if (curr < target) left++;
            else right--;
        }

        return new int[0];
    }

    /**
     * 剑指 Offer 57 - II. 和为s的连续正数序列
     * <p>
     * 思路：滑动窗口
     * 初始窗口：l = 1，r = 2，初始窗口内和 curr = l + r；
     * 窗口转移：
     * 1. curr > tar 需要舍弃值，左窗口右移一格（curr -= l，l++）；
     * 2. curr = tar 符合条件，输出数组，然后左窗口右移一格；
     * 3. curr < tar 需要增加值，右窗口右移一格（r++，curr += r）；
     * 退出循环条件：右窗口越界（l > （target + 1）/ 2）
     */
    public int[][] findContinuousSequence(int target) {
        List<int[]> res = new ArrayList<>();
        int lim = (target + 1) / 2;
        int l = 1, r = 2, curr = l + r;
        while (r <= lim) {
            if (curr == target) {
                int[] tmp = new int[r - l + 1];
                for (int i = l; i <= r; i++)
                    tmp[i - l] = i;
                res.add(tmp);
                curr -= l;
                l++;
            } else if (curr > target) {
                curr -= l;
                l++;
            } else {
                r++;
                curr += r;
            }
        }

        return res.toArray(new int[0][]);
    }

    /**
     * 剑指 Offer 61. 扑克牌中的顺子
     * <p>
     * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为
     * 1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
     * <p>
     * 思路：排序 + 遍历，上下界之差 < 5 则为顺子，也可用集合、遍历得到大小值。
     */
    public boolean isStraight(int[] nums) {
        int joker = 0;
        Arrays.sort(nums);
        for (int i = 0; i < 4; i++) {
            if (nums[i] == 0) ++joker;
            else if (nums[i] == nums[i + 1]) return false;
        }

        return nums[4] - nums[joker] < 5;
    }

    /**
     * 剑指 Offer 62. 圆圈中最后剩下的数字
     * <p>
     * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
     * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，
     * 因此最后剩下的数字是3。
     * <p>
     * 思路：有名的约瑟夫环问题，根据递推公式 f(n,m) = (f(n-1, m) + m) % n, n = 1 时 f (1, m) = 0;
     * <p>
     * 证明：设 f(n ,m) 为环长为 n、步长为 m 的最后存活数，f 与 起始索引无关，设 f(n-1, m) = k;
     * 这意味着，环长为 n 时从任意索引出发数 k+1 个数就是环长为 n-1 的存活解
     * 则有 f
     */
    public int lastRemaining(int n, int m) {
        if (n < 1 || m < 1)
            return -1;
        int last = 0;
        for (int i = 2; i <= n; i++)
            last = (last + m) % i;

        return last;
    }

    /**
     * 剑指 Offer 64. 求1+2+…+n
     * <p>
     * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C)
     * <p>
     * 诸多限制之后，考虑用递归，利用 1. && 的短路特性 或者 2. try catch 语句特性 作为递归退出的边界判定。
     */
    int res;

    public int sumNums(int n) {
        boolean x = n > 1 && sumNums(n - 1) > 0;
        res += n;
        return res;
    }

    int tmp[] = new int[]{0};

    public int sumNums1(int n) {
        try {
            return tmp[n];
        } catch (Exception e) {
            return n + sumNums1(n - 1);
        }
    }

    /**
     * 剑指 Offer 65. 不用加减乘除做加法
     * <p>
     * 思路：与操作得到进位，异或操作得到无进位
     */
    public int add(int a, int b) {
        while (b != 0) {
            int c = (a & b) << 1; // 计算进位
            a ^= b; // 计算无进位和
            b = c; // 将进位赋值给 b
        }

        return a;
    }

    /**
     * 剑指 Offer 66. 构建乘积数组
     * <p>
     * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]:
     * 其中 B 中的元素 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
     * <p>
     * 思路：为避开使用除法，使用两个数组分别保存 i 之前和之后的的乘法因子；
     * 之前因子数组：before[0] = 1，before[i] = before[i-1] * a[i-1]，使用顺序遍历；
     * 之后因子数组：after[len-1] = 1, after[i] = after[i+1] * a[i+1]，使用逆序遍历；
     */
    public int[] constructArr(int[] a) {
        int len = a.length;
        if (len == 0) return new int[0];
        int[] before = new int[len], after = new int[len], res = new int[len];
        before[0] = 1;
        after[a.length - 1] = 1;

        for (int i = 1; i < len; i++) // 生成之前因子数组
            before[i] = a[i - 1] * before[i - 1];
        for (int i = len - 2; i >= 0; i--) // 生成之后因子数组
            after[i] = a[i + 1] * after[i + 1];
        for (int i = 0; i < len; i++) // 生成结果数组，可与上一步合并
            res[i] = before[i] * after[i];

        return res;
    }

    /**
     * 13. 罗马数字转整数
     *
     * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
     *
     * 思路：考虑 'IV' 'XL' 'CD' 特殊情况，逆序遍历，存储之前遍历的数比较
     * 决定是否返回相反数；
     */
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int res = 0, pre = Integer.MIN_VALUE;
        for (int i = s.length() - 1; i >= 0; i--) {
            int curr = map.get(s.charAt(i));
            res += pre > curr ? -curr : curr;
            pre = curr;
        }

        return res;
    }

    /**
     * 172. 阶乘后的零
     *
     * 给定一个整数 n，返回 n! 结果尾数中零的数量。
     *
     * 结尾 0 的个数 -> 存在乘法因子为 10 的个数 -> 存在因子 5 的个数（因为 5 肯定比 2 多）
     */
    public long trailingZeroes(long n) {
        long res = 0;
        long divisor = 5;

        while (divisor <= n) {
            res += n / divisor;
            divisor *= 5;
        }

        return res;
    }

    /**
     * 204. 计数质数
     *
     * 统计所有小于非负整数 n 的质数的数量。
     *
     * 思路：从乘法因子来考虑，任意一个非质数均为若干个质数因子的乘积；
     * 乘法因子的平方必小于 n；
     * 以最小质数 2 开始遍历，2 是质数 那么 2 * 2 2 * 3 2 * 4... 必不是质数；
     * 下一个质数是 3：3 * 3、3 * 4...；(3 * 2 已在 2 时被标记)
     * 下一个质数是 5：标记 5 * 5...；（5 * 2 5 * 3 5 5 * 4（2 * 10）已被标记）
     *
     */
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);

        for (int i = 2; i*i<n; i++) {
            if (isPrime[i]) {
                for (int j = i*i; j < n; j+= i)
                    isPrime[j] = false;
            }
        }

        int count = 0;
        for (int i = 2; i<n; i++)
            count += isPrime[i] ? 1 : 0;

        return count;
    }

    /**
     * 372. 超级次方
     *
     * 你的任务是计算 ab 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
     *
     * 思路：乘积求模等于求模之后乘积，递归思维；
     * 1. 递归：b = [4,5,1,7] 相当于计算f1 = b[4,5,1] * 10，f2 = b[7]，然后返回 (f1 * f2) % base；
     * 2. 递归化问题为求 (a ^ k) % base，在循环中每自乘一次再求余即可；
     */
    int base = 1337;
    public int superPow(int a, int[] b) {
        a %= base;
        return superPow(a, b,  b.length-1);
    }

    public int superPow(int a, int[] b, int r) {
        if (r < 0) return 1;

        int curr = b[r];

        int part1 = customPow(superPow(a, b, r-1), 10);
        int part2 = customPow(a, curr);

        return (part1 * part2) % base;
    }

    int customPow(int a, int k) {
        a %= base;
        int res = 1;

        for (int i = 0; i<k; i++) {
            res *= a;
            res %= base;
        }

        return res;
    }

    /**
     * 793. 阶乘函数后K个零
     *
     * f(x) 是 x! 末尾是0的数量。（回想一下 x! = 1 * 2 * 3 * ... * x，且0! = 1）；
     * 例如， f(3) = 0 ，因为3! = 6的末尾没有0；而 f(11) = 2 ，因为11!= 39916800末端有2个0。给定 K，找出多少个非负整
     * 数x ，有 f(x) = K 的性质。
     *
     * 已知 x！末尾 0 的数量通过数乘法因子 5^n 的个数，接着使用二分查找（左闭右开）定位左、右边界即可；
     */
    public int preimageSizeFZF(int K) {
        int res = 0;
        long l = 0, r = Long.MAX_VALUE;

        while (l < r) {
            long mid = l + (r-l) / 2;
            if (trailingZeroes(mid) < K)
                l = mid + 1;
            else if (trailingZeroes(mid) > K)
                r = mid;
            else r = mid;
        }

        long left_bound = l;

        l = 0;
        r = Long.MAX_VALUE;
        while (l < r) {
            long mid = l + (r-l) / 2;
            if (trailingZeroes(mid) < K)
                l = mid + 1;
            else if (trailingZeroes(mid) > K)
                r = mid;
            else l = mid + 1;
        }

        long right_bound = l - 1;

        return (int) (right_bound - left_bound + 1);
    }

    public static void main(String[] args) {
        new Solution().preimageSizeFZF(100000000);
    }
}
