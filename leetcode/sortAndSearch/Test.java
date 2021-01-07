package sortAndSearch;

import design.NSum;
import edu.princeton.cs.algs4.Merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int[] nums = new int[] {-1,5,-78,3155,14,52,4,8,78,63,1};
        List<Integer> nums1 = new ArrayList<>();
        for (int num : nums)
            nums1.add(num);

        Integer[] a = nums1.toArray(new Integer[0]);

        BasicSortingMachine machine = new BasicSortingMachine();
        machine.HeapSort(a);
        System.out.println("complete! ");
    }
}
