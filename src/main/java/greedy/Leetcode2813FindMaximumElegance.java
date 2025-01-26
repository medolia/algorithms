package greedy;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/maximum-elegance-of-a-k-length-subsequence/description/">2813. 子序列最大优雅度</a>
 * <p>
 * 给你一个长度为 n 的二维整数数组 items 和一个整数 k 。
 * <p>
 * items[i] = [profiti, categoryi]，其中 profiti 和 categoryi 分别表示第 i 个项目的利润和类别。
 * <p>
 * 现定义 items 的 子序列 的 优雅度 可以用 total_profit + distinct_categories ^ 2 计算，其中 total_profit 是子序列中所有项目的利润总和，distinct_categories 是所选子序列所含的所有类别中不同类别的数量。
 * <p>
 * 你的任务是从 items 所有长度为 k 的子序列中，找出 最大优雅度 。
 * <p>
 * 用整数形式表示并返回 items 中所有长度恰好为 k 的子序列的最大优雅度。
 * <p>
 * 注意：数组的子序列是经由原数组删除一些元素（可能不删除）而产生的新数组，且删除不改变其余元素相对顺序。
 * <p>
 * 思路：贪心。降序排序后先加入前 k 个元素，如果其类别均不一致则此 k 个为最佳答案。
 * <p>假如前 k 个存在重复类别，对于 k+1 及之后的新元素
 * <p>1. 新元素个为一个新类别，那么将他和 k 个元素中已有重复类别且 profit 最小的元素替换可能会是最优解（total_profit变小但distinct_categories变大）
 * <p>2. 新元素为已有类型，那么根据公式替换现有元素的任一个 total_profit 和 distinct_categories 都会变小，不考虑。
 * <p>
 * 时：O(NlogN)，初始排序；空：O(N)，辅助栈和辅助集合
 *
 * @author lbli
 */
class Leetcode2813FindMaximumElegance {

    public static void main(String[] args) {
        long maximumElegance = new Leetcode2813FindMaximumElegance()
                .findMaximumElegance(new int[][]{{3, 2}, {5, 1}, {10, 1}}, 2);
        System.out.println(maximumElegance);
    }

    public long findMaximumElegance(int[][] items, int k) {
        // 按 profit 倒序排列
        Arrays.sort(items, Comparator.comparingInt(e -> -e[0]));

        // 只存储重复 category 的 profit
        Stack<Integer> stack = new Stack<>();
        Set<Integer> categorySet = new HashSet<>();
        long totalProfit = 0;
        long res = 0;
        for (int i = 0; i < items.length; i++) {
            int currProfit = items[i][0];
            int category = items[i][1];

            // 先把前 k 个大元素算进去
            if (i < k) {
                totalProfit += currProfit;
                if (!categorySet.add(category)) {
                    stack.push(currProfit);
                }
            } else if (!stack.isEmpty()
                    // 只考虑元素为新 category 的情况
                    && categorySet.add(category)) {
                // 模拟选择：将 profit 最低且重复的元素替换为当前元素
                totalProfit += currProfit - stack.pop();
            }

            res = Math.max(res, totalProfit + (long) categorySet.size() * categorySet.size());
        }

        return res;
    }

}
