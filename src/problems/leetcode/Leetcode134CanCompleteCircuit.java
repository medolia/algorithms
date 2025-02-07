package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/gas-station/description/">134. 加油站</a>
 * <p>在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * <p>你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 * <p>给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
 * <p>
 * 思路：使用贪心算法优化 N^2 遍历，如果从站点 i 可到达站点 j 但不可到达站点 j + 1，那么下一次遍历跳过已遍历站点直接从 j + 1 就可以。
 * <p>证明：i 对总油量的贡献为正且 i 到不了 j，那么 i+1 少了 i 贡献的油量也一定到不了 j
 *
 * @author lbli
 */
class Leetcode134CanCompleteCircuit {

    public static void main(String[] args) {
        int result = new Leetcode134CanCompleteCircuit()
                .canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2});
        System.out.println(result);
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;

        while (i < n) {
            int remaining = 0;
            int count = 0;

            while (count < n) {
                int next = (i + count) % n;
                remaining += gas[next] - cost[next];
                if (remaining < 0) {
                    break;
                }

                ++count;
            }

            if (count == n) {
                return i;
            } else {
                // 中间的所有站点都可跳过，从首个判定到达不了的站点继续出发
                i = i + count + 1;
            }
        }

        return -1;
    }

}
