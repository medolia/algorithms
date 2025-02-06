package backTrackAndDFSAndBFS;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/permutations-ii/description/">47. 全排列 II</a>
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 * [1,2,1],
 * [2,1,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * <p>思路：回溯，使用两个备忘录，一个存储当前层级已经选择过的数字，一个存储之前层级已经选择过的索引</p>
 * <p>时空复杂度：O(N*N!) O(N)</p>
 */
class Leetcode47Permutations_ii {

    public static void main(String[] args) {
        List<List<Integer>> res = new Leetcode47Permutations_ii().permuteUnique(new int[]{1, 2, 1});
        res.forEach(e -> System.out.println(Arrays.toString(e.toArray())));
    }

    List<List<Integer>> res = new ArrayList<>();
    int[] numList;

    public List<List<Integer>> permuteUnique(int[] nums) {

        res = new ArrayList<>();
        numList = nums;
        Arrays.sort(nums);
        LinkedList<Integer> list = new LinkedList<>();
        // 存储已经选择的索引
        Set<String> chosenIdx = new HashSet<>();
        dfs(chosenIdx, list);

        return res;
    }

    private void dfs(Set<String> chosenIdx, LinkedList<Integer> list) {
        if (list.size() == numList.length) {
            res.add(new ArrayList<>(list));
            return;
        }

        Set<Integer> memo = new HashSet<>();
        for (int i = 0; i < numList.length; i++) {
            int num = numList[i];
            String idxKey = String.valueOf(i);
            if (memo.contains(num) || chosenIdx.contains(idxKey)) {
                continue;
            }

            chosenIdx.add(idxKey);
            memo.add(num);
            list.add(num);
            dfs(chosenIdx, list);
            list.removeLast();
            chosenIdx.remove(idxKey);
        }
    }


}
