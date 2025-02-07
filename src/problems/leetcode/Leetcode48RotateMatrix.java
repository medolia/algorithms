package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/rotate-image/solutions/526980/xuan-zhuan-tu-xiang-by-leetcode-solution-vu3m/?envType=study-plan-v2&envId=top-100-liked">48. 旋转图像</a>
 * <p>
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * <p>
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 * <p>
 * 思路：发现旋转前后的坐标关系：m[n-1-j,i] -> m[i,j]，从外向内一圈一圈迭代
 *
 * @author lbli
 */
class Leetcode48RotateMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        new Leetcode48RotateMatrix().rotate(matrix);
    }

    public void rotate(int[][] matrix) {

        int n = matrix.length;
        for (int depth = 0; depth < n / 2; depth++) {
            for (int col = depth; col < n - depth - 1; col++) {
                int temp = matrix[depth][col];
                matrix[depth][col] = matrix[n - col - 1][depth];
                matrix[n - col - 1][depth] = matrix[n - depth - 1][n - col - 1];
                matrix[n - depth - 1][n - col - 1] = matrix[col][n - depth - 1];
                matrix[col][n - depth - 1] = temp;
            }
        }

    }
}
