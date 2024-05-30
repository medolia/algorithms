package sortAndSearch;

import java.util.Arrays;

@SuppressWarnings("all")
public class BasicSortingMachine {

    public static void main(String[] args) {
        int[] arr = {1, 5, -10, 9, 20, 31};
        new QuickSort().sort(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, -5, 10, 99, -100};
        new MergeSort().sort(arr);

        System.out.println(Arrays.toString(arr));
    }

    private static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    interface ISort {
        void sort(int... arr);
    }

    /**
     * 插入排序
     * 遍历数组，将当前元素插入到左边已排序数组中的合适位置。
     */
    static class InsertSort implements ISort {

        @Override
        public void sort(int... a) {
            int N = a.length;
            for (int i = 0; i < N; i++) {
                for (int j = i; j > 0 && a[i] < a[j - 1]; j--)
                    // 依次比较，前一位元素较大时往右挪一位，否则保持原位
                    swap(a, j, j - 1);
            }
        }
    }

    /**
     * 选择排序
     * 每遍历一次得到一个最小值置入左部分已排序数组的最右边
     */
    static class SelectionSort implements ISort {
        @Override
        public void sort(int... a) {
            int N = a.length;
            for (int i = 0; i < N; i++) {
                int min = i;
                for (int j = i + 1; j < N; j++)
                    if (a[j] < a[min]) min = j;
                swap(a, i, min);
            }
        }
    }

    /**
     * 堆排序
     * 1. 从右（N/2 开始）到左构造子堆使数组堆有序；
     * 2. 不断交换堆顶(最大值)与堆底并缩小最大堆容量直至 N == 1；
     * 如果不能使用辅助数组，需要改写 less、swap，使实际访问的索引比传入的小 1；
     */
    static class HeapSort implements ISort {

        @Override
        public void sort(int... a) {
            int N = a.length;
            int[] aux = new int[N + 1];
            System.arraycopy(a, 0, aux, 1, N);

            for (int k = N / 2; k >= 1; k--)
                sink(aux, k, N);

            while (N > 1) {
                swap(aux, 1, N--);
                sink(aux, 1, N);
            }

            System.arraycopy(aux, 1, a, 0, a.length);
        }

        private void sink(int[] a, int k, int N) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && a[j] < a[j + 1]) j++;
                if (!(a[k] < a[j])) break;
                swap(a, k, j);
                k = j;
            }
        }
    }

    /**
     * 快速排序
     */
    static class QuickSort implements ISort {

        @Override
        public void sort(int[] arr) {
            sort(arr, 0, arr.length - 1);
        }

        private void sort(int[] arr, int l, int r) {
            if (l >= r) {
                return;
            }

            int newPivotIdx = partition(arr, l, r);

            sort(arr, l, newPivotIdx);
            sort(arr, newPivotIdx + 1, r);
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

    }

    /**
     * 归并排序
     */
    static class MergeSort implements ISort {

        @Override
        public void sort(int... arr) {
            sort(arr, 0, arr.length - 1);
        }

        private void sort(int[] arr, int l, int r) {
            if (l >= r) return;

            int mid = l + (r - l) / 2;
            sort(arr, l, mid);
            sort(arr, mid + 1, r);

            mergeInternal(arr, l, mid, r);
        }

        private void mergeInternal(int[] arr, int l, int mid, int r) {
            int lPtr = l, rPtr = mid + 1;

            int[] temp = new int[r - l + 1];
            int tempPtr = 0;

            // 双指针
            while (lPtr <= mid && rPtr <= r) {
                if (arr[lPtr] < arr[rPtr]) {
                    temp[tempPtr++] = arr[lPtr++];
                } else {
                    temp[tempPtr++] = arr[rPtr++];
                }
            }

            while (lPtr <= mid) {
                temp[tempPtr++] = arr[lPtr++];
            }

            while (rPtr <= r) {
                temp[tempPtr++] = arr[rPtr++];
            }

            System.arraycopy(temp, 0, arr, l, r - l + 1);
        }
    }
}
