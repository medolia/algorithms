package sortAndSearch;

import java.util.Arrays;

/**
 * @author lbli
 */
class QuickSort {
    public static void main(String[] args) {
        int[] arr = {1, 5, -10, 9, 20, 31};
        new QuickSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] arr) {
        sortInternal(arr, 0, arr.length - 1);
    }

    private void sortInternal(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int newPivotIdx = partition(arr, l, r);

        sortInternal(arr, l, newPivotIdx);
        sortInternal(arr, newPivotIdx + 1, r);
    }

    private int partition(int[] arr, int l, int r) {
        int pivotIdx = l;
        int fixedPivotValue = arr[pivotIdx];

        // 将除 pivot 以外的位置，较小值置于左边
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] < fixedPivotValue) {
                pivotIdx++;
                swap(arr, i, pivotIdx);
            }
        }

        // 交换此时pivot 指针和最初的 pivot 指针的元素，这样 pivotIdx 左侧都比 pivot 小，右侧都大于等于 pivot
        swap(arr, pivotIdx, l);
        return pivotIdx;
    }

    private void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

}
