package problems.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 递归计算 n 数之和
 *
 * 求数组 nums 中，n 个数和为 target 的所有 不重复 组合，起始索引为 start （若不指定则为 0）；
 * base case：n < 2 时无意义，返回空集；n == 2 时为两数之和问题，双指针解法，注意去重；
 * n > 2 时，假设目前位置为 i，先递归得到 n-1 个数和为 target - nums[i] 起始索引为 i+1 的解集，然后在所有
 * 解集尾部加上 nums[i]；
 *
 * 时间复杂度：max(N ^ (n-1), NlogN)，最底层的两数问题指针扫描 N，排序 NlogN，每次递归需要再以不同 target 扫描一次；
 * 空间复杂度：N，使用一个镜像数组用于排序；
 */
public class NSum {
    static int[] aux;

    private static List<List<Integer>> recurForResult(int[] nums, int n, int target, int start) {
        int sz = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        if (n < 2 || sz < n) return res;

        if (n == 2) {
            int lo = start, hi = sz - 1;
            while (lo < hi) {
                int left = aux[lo];
                int right = aux[hi];
                int sum = left + right;
                if (sum < target) {
                    while (aux[lo] == left) lo++;// 去重不满足条件的元素
                }
                else if (sum > target) {
                    while (aux[hi] == right) hi--;
                }
                else {
                    res.add(new ArrayList<>() {{
                        add(left);
                        add(right);
                    }});
                    while (aux[lo] == left) lo++;// 去重已添加的结果
                    while (aux[hi] == right) hi--;
                }
            }
        } else {
            for (int i = start; i < sz; i++) {
                int curr = aux[i];
                List<List<Integer>> preRes = recurForResult(aux, n - 1, target - curr, i + 1);
                for (List<Integer> eachRes : preRes) {
                    eachRes.add(aux[i]);
                    res.add(eachRes);
                }
                while (i > 0 && i < sz && aux[i] == aux[i-1]) i++;
            }
        }

        return res;
    }

    public static List<List<Integer>> recurForResult(int[] nums, int n, int target) {
        int sz = nums.length;
        aux = new int[sz];
        System.arraycopy(nums, 0, aux, 0, sz);
        Arrays.sort(aux);
        return recurForResult(nums, n, target, 0);
    }
}
