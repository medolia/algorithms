package sortAndSearch;

import java.util.Arrays;

/**
 * 215. 数组中的第K个最大元素 在未排序的数组中找到第 k 个最大的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而 不是第 k 个不同的元素。
 * 思路：引入快排框架，快排分区后得到的索引 p 即为 数组中第 p 小的数；
 * <br>
 * 归类：如何从未排序的 N 个数中找到 K 个最大的值
 * A1：建立一个 K 的最小堆，筛选掉 N - K 个最小元素，堆中留下的就是 K 个最大元素，时 O(Nlog(K)) 空 O(K)
 * A2（最佳）：借鉴快排，当发现正好有 K 个数大于 pivot 时中止。时 O(N)，空 O(N)
 *
 * @author lbli
 */
public class Leetcode215FindKMaxValuesInNNumbers {

    public static void main(String[] args) {
        int[] arr = {1, -2, 3, 49, 5};
        int k = 3;
        int result = new Leetcode215FindKMaxValuesInNNumbers()
                .findKthLargest(arr, k);
        System.out.printf("数组 %s 第 %s大的元素：%s%n", Arrays.toString(arr), k, result);
    }


    public int findKthLargest(int[] nums, int k) {
        int l = 0, r = nums.length - 1;
        k = nums.length - k; // 第 k 大，对应升序排序后的索引 n-k；

        while (l <= r) {
            int pivotIdx = partition(nums, l, r);
            if (pivotIdx < k) l = pivotIdx + 1;
            else if (pivotIdx > k) r = pivotIdx - 1;
            else return nums[pivotIdx];
        }

        return -1;
    }

    private int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        int pivotIdx = l;

        for (int i = l + 1; i <= r; i++) {
            if (nums[i] < pivot) {
                pivotIdx++;
                swap(nums, i, pivotIdx);
            }
        }

        swap(nums, pivotIdx, l);
        return pivotIdx;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
