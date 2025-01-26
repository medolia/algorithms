package sortAndSearch;

/**
 *
 * 左右边界区别：
 * @author lbli
 */
@SuppressWarnings("all")
class BinarySearch {

    public static void main(String[] args) {

        assert new BinarySearch().normal(new int[]{1, 2, 3, 4, 5}, 3) == 2;
        int[] arr = {1, 2, 3, 3, 3, 4, 5};
        assert new BinarySearch().leftBound(arr, 3) == 2;
        assert new BinarySearch().rightBound(arr, 3) == 4;
    }

    int normal(int[] nums, int num) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == num) {
                return mid;
            } else if (nums[mid] < num) {
                l = mid + 1;
            } else if (nums[mid] > num) {
                r = mid - 1;
            }
        }

        return -1;
    }

    /**
     * 左边界
     */
    int leftBound(int[] nums, int num) {
        // 左闭右开
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == num) {
                r = mid; // 区别1：相等时 r置于中间位，那么结束循环时l r 都在左边界
            } else if (nums[mid] < num) {
                l = mid + 1;
            } else if (nums[mid] > num) {
                r = mid;
            }
        }

        // 区别2：返回 l（或r，因为相等）
        return l;
    }

    /**
     * 右边界
     */
    int rightBound(int[] nums, int num) {
        // 左闭右开
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == num) {
                l = mid + 1; // 区别1：相等时 l+1，那么结束循环时前一个索引会是右边界
            } else if (nums[mid] < num) {
                l = mid + 1;
            } else if (nums[mid] > num) {
                r = mid;
            }
        }

        // 区别2：返回 l/r-1
        return l - 1;
    }

}
