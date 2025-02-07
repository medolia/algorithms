package problems.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * <p>
 * 思路：暴力回溯
 *
 * @author lbli
 */
class Leetcode51SolveNQueens {

    public static void main(String[] args) {
        List<List<String>> result = new Leetcode51SolveNQueens().solveNQueens(9);
        System.out.println(result.stream()
                .map(e -> String.join("\n", e))
                .collect(Collectors.joining("\n---------------------\n")));
    }

    List<List<String>> result;
    int n;
    Marker marker;

    public List<List<String>> solveNQueens(int n) {
        result = new ArrayList<>();
        this.n = n;
        marker = new Marker(n);

        backTrack(0, new ArrayList<>());

        return result;
    }

    private void backTrack(int count, List<String> curr) {
        if (count == n) {
            result.add(new ArrayList<>(curr));
            return;
        }

        for (int col = 0; col < this.n; col++) {
            int row = curr.size();
            String next = getNext(col);

            if (!marker.existJudge(col, row)) {
                continue;
            }

            curr.add(next);
            marker.mark(col, row);
            backTrack(count + 1, curr);

            curr.remove(curr.size() - 1);
            marker.unmark(col, row);
        }
    }

    private String getNext(int col) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(i == col ? "Q" : ".");
        }

        return res.toString();
    }

    static class Marker {
        boolean[] cols, rows, rightDownDiagonals, rightUpDiagonals;
        int n;

        Marker(int n) {
            cols = new boolean[n + 1];
            rows = new boolean[n + 1];
            rightDownDiagonals = new boolean[n * 2 + 1];
            rightUpDiagonals = new boolean[n * 2 + 1];
            this.n = n;
        }

        void mark(int col, int row) {
            cols[col] = true;
            rows[row] = true;
            rightDownDiagonals[col + row] = true;
            rightUpDiagonals[col - row + n] = true;
        }

        void unmark(int col, int row) {
            cols[col] = false;
            rows[row] = false;
            rightDownDiagonals[col + row] = false;
            rightUpDiagonals[col - row + n] = false;
        }

        boolean existJudge(int col, int row) {
            return !cols[col] && !rows[row]
                    && !rightDownDiagonals[col + row]
                    && !rightUpDiagonals[col - row + n];
        }
    }

}
