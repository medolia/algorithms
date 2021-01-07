package sortAndSearch;

import design.NSum;
import struc.Node;

public class BasicSortingMachine {
    /**
     * 插入排序
     * 遍历数组，将当前元素插入到左边已排序数组中的合适位置。
     */
    public void InsertSort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[i], a[j - 1]); j--)
                // 依次比较，前一位元素较大时往右挪一位，否则保持原位
                swap(a, j, j - 1);
        }
    }

    /**
     * 选择排序
     * 每遍历一次得到一个最小值置入左部分已排序数组的最右边
     */
    public void SelectionSort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min])) min = j;
            swap(a, i, min);
        }
    }

    /**
     * 归并排序
     * 递归至两段有序的数组合并
     */
    public void MergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        MergeSort(a, 0, a.length - 1);
    }

    private void MergeSort(Comparable[] a, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;

        MergeSort(a, left, mid);
        MergeSort(a, mid + 1, right);
        Merge(a, left, mid, right);
    }

    Comparable[] aux;
    private void Merge(Comparable[] a, int left, int mid, int right) {
        for (int k = left; k <= right; k++)
            aux[k] = a[k];

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > right) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }

    /**
     * 快速排序
     * 核心是取一基线值并分开小于基线值和大于等于基线值的部分，然后再将基线左右两部分递归排序；
     */
    public void QuickSort(Comparable[] a) {
        QuickSort(a, 0, a.length - 1);
    }

    private void QuickSort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi);
        QuickSort(a, lo, p - 1);
        QuickSort(a, p + 1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int j = lo;
        Comparable pivot = a[lo];

        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], pivot)) {
                j++;
                swap(a, j, i);
            }
        }

        swap(a, lo, j);
        return j;
    }

    /**
     * 堆排序
     * 1. 从右（N/2 开始）到左构造子堆使数组堆有序；
     * 2. 不断交换堆顶(最大值)与堆底并缩小最大堆容量直至 N == 1；
     * 如果不能使用辅助数组，需要改写 less、swap，使实际访问的索引比传入的小 1；
     */
    public void HeapSort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N+1];
        System.arraycopy(a,0,aux,1,N);

        for (int k = N /2; k>=1; k--)
            sink(aux, k, N);

        while (N > 1) {
            swap(aux, 1, N--);
            sink(aux, 1, N);
        }

        System.arraycopy(aux,1,a,0,a.length);
    }

    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private void swap(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private void sink(Comparable[]a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a[j], a[j+1])) j++;
            if (!less(a[k], a[j])) break;
            swap(a, k, j);
            k = j;
        }
    }
}
