package arrayAndLinkedList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author lbli
 */
public class ArrayNesting {

    public static void main(String[] args) {
//        int[] nums = {5, 4, 0, 3, 1, 6, 2};
        int[] nums = {0,1,2};

        int res = new ArrayNesting().arrayNesting(nums);
    }

    /**
     * <a href='https://leetcode.cn/problems/array-nesting/'>565. 数组嵌套</a>
     */
    public int arrayNesting(int[] nums) {
        Set<Integer> memo = new HashSet<>();
        LinkedHashSet<Integer> resLink = new LinkedHashSet<>();

        for (int num : nums) {
            if (memo.contains(num)) {
                continue;
            }
            memo.add(num);

            LinkedHashSet<Integer> tmp = new LinkedHashSet<>();
            int i = num;
            while (!tmp.contains(i)) {
                tmp.add(i);
                memo.add(i);
                i = nums[i];
            }

            if (tmp.size() > resLink.size()) {
                resLink = tmp;
            }
        }

        return resLink.size();
    }

}
