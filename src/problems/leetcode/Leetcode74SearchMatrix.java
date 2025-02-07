package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/search-a-2d-matrix/solutions/688117/sou-suo-er-wei-ju-zhen-by-leetcode-solut-vxui/?envType=study-plan-v2&envId=top-100-liked">搜索二维矩阵</a>
 * 思路：二分，实质矩阵展开（一行一行地拼接）就是一个有序数组
 */
class Leetcode74SearchMatrix {

    public static void main(String[] args) {
//        boolean res = new Leetcode74SearchMatrix().searchMatrix(
//                new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3);

//        boolean res = new Leetcode74SearchMatrix().searchMatrix(
//                new int[][]{{-1, 1}, {2, 3}}, 0);

        boolean res = new Leetcode74SearchMatrix().searchMatrix(
                new int[][]{{-1, 1}}, 0);
        System.out.println(res);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;

        int l = -1, r = row * col;

        // 两边开区间的遍历
        while (l + 1 < r) {
            int mid = l + (r - l) / 2;
            int midV = matrix[mid / col][mid % col];

            if (target == midV) {
                return true;
            } else if (target < midV) {
                r = mid;
            } else {
                l = mid;
            }
        }

        return false;
    }

}
