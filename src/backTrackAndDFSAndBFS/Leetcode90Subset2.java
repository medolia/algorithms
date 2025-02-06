package backTrackAndDFSAndBFS;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/subsets-ii/submissions/596874459/">90. 子集 II</a>
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的
 * 子集
 * （幂集）。
 * <p>
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 * <p>
 * 示例 2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * <p>思路：回溯，先计数再开始决策，选择：每个元素取的个数（从0 到最大计数）</p>
 * <p>时空复杂度：时O(2^N)空：O(N)</>
 */
class Leetcode90Subset2 {

    public static void main(String[] args) {
        List<List<Integer>> res = new Leetcode90Subset2().subsetsWithDup(new int[]{1, 2, 2, 3, 3});
        res.forEach(e -> System.out.println(Arrays.toString(e.toArray())));
    }

    List<List<Integer>> res;
    Map<Integer, Integer> counter;
    List<Integer> numsList;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        this.counter = new HashMap<>();
        for (int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }

        res = new ArrayList<>();
        this.numsList = new ArrayList<>(counter.keySet());
        dfs(0, new HashMap<>());

        return res;
    }

    private void dfs(int i, Map<Integer, Integer> currMap) {
        if (i == numsList.size()) {
            List<Integer> list = new ArrayList<>();
            currMap.forEach((k, v) -> {
                while (v-- > 0) {
                    list.add(k);
                }
            });

            res.add(list);
            return;
        }


        int integer = numsList.get(i);
        int totalCount = counter.getOrDefault(integer, 0);
        // 选择：每个元素取的个数（从0 到最大计数）
        for (int count = 0; count <= totalCount; count++) {
            currMap.put(integer, count);
            // 选下一个元素
            dfs(i + 1, currMap);
            currMap.remove(integer);
        }
    }


}
