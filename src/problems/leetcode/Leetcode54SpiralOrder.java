package problems.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/spiral-matrix/description/?envType=study-plan-v2&envId=top-100-liked">54. 螺旋矩阵</a>
 * <p>
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 * <p>
 * 思路：递归思维，M * N 的矩阵等于一个外圈加上 (M-2)*(N-2) 的结果，注意只有一行或一竖的边界情况
 *
 * 时：O(MN)；空：O(1)，没有使用额外空间
 * @author lbli
 */
@SuppressWarnings("all")
class Leetcode54SpiralOrder {

    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
//        int[][] matrix = {{1, 2, 3, 4}};
//        int[][] matrix = {{1}, {2}, {3}};
        List<Integer> res = new Leetcode54SpiralOrder().spiralOrder(matrix);
        System.out.println(res);
    }

    int colM, rowM;
    int[][] matrix;

    public List<Integer> spiralOrder(int[][] matrix) {
        rowM = matrix.length - 1;
        colM = matrix[0].length - 1;
        this.matrix = matrix;

        return spiralOrder(0, 0, rowM, colM);
    }

    private List<Integer> spiralOrder(int initRow, int initCol, int endRow, int endCol) {
        if (initRow > endRow || initCol > endCol) {
            return new ArrayList<>();
        }

        // circle
        List<Integer> circle = new ArrayList<>();
        for (int i = initRow; i <= endCol; i++) {
            circle.add(matrix[initRow][i]);
        }
        for (int i = initRow + 1; i <= endRow; i++) {
            circle.add(matrix[i][endCol]);
        }
        for (int i = endCol - 1; i >= initCol && endRow > initRow; i--) {
            circle.add(matrix[endRow][i]);
        }
        for (int i = endRow - 1; i > initRow && endCol > initCol; i--) {
            circle.add(matrix[i][initCol]);
        }

        List<Integer> next = spiralOrder(initRow + 1, initCol + 1, endRow - 1, endCol - 1);

        List<Integer> res = new ArrayList<>();
        res.addAll(circle);
        res.addAll(next);
        return res;
    }
}
