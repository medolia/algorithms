package backTrackAndDFSAndBFS;

import java.util.*;

public class Solution {
    /**
     * 剑指 Offer 12. 矩阵中的路径
     */
    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, words, i, j, 0)) return true;
            }
        }
        return false;
    }

    boolean dfs(char[][] board, char[] words, int i, int j, int k) {
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != words[k]) return false;
        if (k == words.length - 1) return true;

        board[i][j] = '\0';

        boolean res = dfs(board, words, i + 1, j, k + 1) || dfs(board, words, i - 1, j, k + 1) ||
                dfs(board, words, i, j + 1, k + 1) || dfs(board, words, i, j - 1, k + 1);

        board[i][j] = words[k];

        return res;
    }

    /**
     * 剑指 Offer 17. 打印从1到最大的n位数
     * <p>
     * 防止大数溢出，使用分位 dfs 字符串拼接，指定左边界以去掉左边多余 0
     */
    StringBuilder tmpStr;
    int nine = 0, count = 0, start, n;
    char[] num, loop = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public String printNumbers(int n) {
        this.n = n;
        tmpStr = new StringBuilder();
        num = new char[n];
        start = n - 1;
        dfs17(0);
        tmpStr.deleteCharAt(tmpStr.length() - 1);
        return tmpStr.toString();
    }

    void dfs17(int x) {
        if (x == n) {
            String s = String.valueOf(num).substring(start);
            if (!s.equals("0")) tmpStr.append(s + ",");
            if (n - start == nine) start--;
            return;
        }
        for (char i : loop) {
            if (i == '9') nine++;
            num[x] = i;
            dfs17(x + 1);
        }
        nine--;
    }

    /**
     * 剑指 Offer 38. 字符串的排列
     * <p>
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     * <p>
     * 思路：dfs 遍历框架，设当前位置为 i
     * 退出条件：若已遍历到字符数组的最后一位则退出
     * 遍历操作：将位移 i 与其之后的所有字符逐个交换，若之后的字符未交换过（未存在与哈希集合中）则产生一个新结果
     * 选择定义：加入哈希集合，交换字符位置
     * 撤销选择定义：再次交换字符位置（还原）
     * <p>
     * 启发：返回的是一个定长结果，可以考虑将原字符串转化为数组进行换位操作
     */
    List<String> res;
    char[] c;

    public String[] permutation(String s) {
        res = new LinkedList<>();
        c = s.toCharArray();
        dfs(0);

        return res.toArray(new String[0]);
    }

    void dfs(int x) {
        if (x == c.length - 1) {
            res.add(String.valueOf(c));
            return;
        }

        Set<Character> set = new HashSet<>(); // 当前选择结点的哈什集
        for (int i = x; i < c.length; i++) {
            // 重复字符间的交换不会产生新组合
            if (set.contains(c[i])) continue;

            set.add(c[i]);
            swap(x, i);

            dfs(x + 1);

            swap(x, i);
        }
    }

    void swap(int i, int j) {
        char tmp = c[j];
        c[j] = c[i];
        c[i] = tmp;
    }

    /**
     * 22. 括号生成
     * <p>
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * <p>
     * 有效意味着需要左右闭合成对，比如 ")(" 就是无效的组合（可使用栈来判断）；
     * 回溯规划：结果、当前字符串、当前已添加的左括号数均使用全局变量；
     * 1. 递归参数：int pair，记录待配对的左括号数；int n，记录可添加的最大左括号数；
     * 2. 返回条件：若字符串长度已达到 2*n，说明已经得到一个完整结果；
     * 3. 决策判断：若已添加的左括号数 left 小于 n，则可以添加左括号，然后进入下一层决策树 dfs(pair+1)；
     * 若待配对的左括号数 pair 大于 0，则可以添加右括号，然后进入下一层决策树 dfs(pair-1)；
     */
    List<String> res22;
    StringBuilder tmp22;
    int left;

    public List<String> generateParenthesis(int n) {
        res22 = new LinkedList<>();
        tmp22 = new StringBuilder();
        left = 0;

        backTrack22(0, n);

        return res22;
    }

    void backTrack22(int pair, int n) {
        if (tmp22.length() == 2 * n) {
            res22.add(tmp22.toString());
            return;
        }

        if (left < n) {
            ++left;
            tmp22.append("(");
            backTrack22(pair + 1, n);
            --left;
            tmp22.deleteCharAt(tmp22.length() - 1);
        }

        if (pair > 0) {
            tmp22.append(")");
            backTrack22(pair - 1, n);
            tmp22.deleteCharAt(tmp22.length() - 1);
        }
    }

    /**
     * 37. 解数独
     * <p>
     * 编写一个程序，通过填充空格来解决数独问题。
     * <p>
     * 一个数独的解法需遵循如下规则：
     * <p>
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     * 空白格用 '.' 表示。
     * <p>
     * 回溯前：
     * 1. 定义全局（成员）变量：行、列、子九宫格 boolean 数组，row[r][n] col[c][n] box[r/3][c/3][n]
     * 分别代表 n 是否已出现在第 r 行、第 c 列、位置为 [r/3][c/3] 的九宫格处；记录空格处位置的列表，记录
     * 是否已完成的 boolean 变量；
     * 2. 遍历一次 9 * 9 方格，记录空格处的位置，更新已有数字处对应的 row col box 数组；
     * <p>
     * 回溯规划：
     * 递归参数：9*9 方格 char[][] board，当前空格索引 int curr；
     * 返回条件：当 curr == blocks.size() 时，所有空格都填写完毕，更新已完成的 boolean 变量，返回；
     * 选择：在当前空格填写 1-9，且更新对应的 row、col、boxes 为 true；
     * 撤销选择：注意因为要返回一个已填好的方格，不需要还原为 "."；只需要撤销对应的 row、col、boxes 为 false；
     * <p>
     * 启发：数字转字符使用 (char) (n + '0')
     */
    boolean[][] rows37;
    boolean[][] cols37;
    boolean[][][] boxes;
    LinkedList<int[]> blocks;
    boolean finished;

    public void solveSudoku(char[][] board) {
        rows37 = new boolean[9][10];
        cols37 = new boolean[9][10];
        boxes = new boolean[3][3][10];
        blocks = new LinkedList<>();
        finished = false;

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.')
                    blocks.add(new int[]{r, c});
                else {
                    int num = board[r][c] - '0';
                    rows37[r][num] = true;
                    cols37[c][num] = true;
                    boxes[r / 3][c / 3][num] = true;
                }
            }

        backTrack37(board, 0);
    }

    void backTrack37(char[][] board, int curr) {
        if (curr == blocks.size()) {
            finished = true;
            return;
        }

        int r = blocks.get(curr)[0], c = blocks.get(curr)[1];
        for (int n = 1; n <= 9 && !finished; n++) { // 当已完成时，不再进行决策；
            if (rows37[r][n] || cols37[c][n] || boxes[r / 3][c / 3][n])
                continue;

            board[r][c] = (char) (n + '0');
            rows37[r][n] = cols37[c][n] = boxes[r / 3][c / 3][n] = true;

            backTrack37(board, curr + 1);

            rows37[r][n] = cols37[c][n] = boxes[r / 3][c / 3][n] = false;
        }
    }

    /**
     * 46. 全排列
     * <p>
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * <p>
     * 思路：假设含重复元素（ex）；
     * 1. 终止条件：若 curr == nums.length-1，代表所有位已经固定完毕；
     * 2. 递推参数：当前固定位 curr；
     * 3. 递推工作：（ex）初始化一个 Set，用于排除重复元素；将位置 x 与分别与位置 i ∈ [x, len-1] 交换
     * 然后进入下一层递归 dfs(x+1)，即开始固定位置 x+1；
     * ex1. 若位置 i 已在 Set 中，代表不会产生新结果（即之前已经定位该位置为这个元素值了），剪枝；
     * ex2. 将 nums[i] 加入 Set 中；
     * 1. 将位置 x 与分别与位置 i 交换，意为将位置 x 的元素定为 nums[i];
     * 2. 进入下一层递归；
     * 3. 返回时需要再交换一次复原决策节点状态；
     */
    LinkedList<List<Integer>> res46;

    public List<List<Integer>> permute(int[] nums) {
        res46 = new LinkedList<>();
        backTrack46(0, nums);

        return res46;
    }

    void backTrack46(int curr, int[] nums) {
        if (curr == nums.length - 1) {
            List<Integer> tmp = new ArrayList<>();
            for (int num : nums)
                tmp.add(num);
            res46.add(tmp);
            return;
        }

        for (int j = curr; j < nums.length; j++) {
            swap(nums, curr, j);
            backTrack46(curr + 1, nums);
            swap(nums, curr, j);
        }
    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 51. N 皇后
     * <p>
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 即两两皇后不处于同一行、列或斜线上。
     * <p>
     * 思路：回溯
     * 关键：使用三个 boolean 数组判定是否该列、两斜线上已有皇后放置；
     */
    LinkedList<List<String>> res51;
    LinkedList<String> tmp51;
    boolean[] cols, cross1, cross2;

    public List<List<String>> solveNQueens(int n) {
        res51 = new LinkedList<>();
        tmp51 = new LinkedList<>();
        cols = new boolean[n];
        cross1 = new boolean[2 * n - 1];
        cross2 = new boolean[2 * n - 1];

        backTrack51(0, n);

        return res51;
    }

    void backTrack51(int currR, int n) {
        if (currR == n) {
            res51.add(new ArrayList<>(tmp51));
            return;
        }

        char[] opts = new char[n];
        Arrays.fill(opts, '.');

        for (int col = 0; col < n; col++) {
            if (cols[col] || cross1[currR - col + n - 1] || cross2[currR + col])
                continue;

            opts[col] = 'Q';
            tmp51.add(String.valueOf(opts));
            cols[col] = true;
            cross1[currR - col + n - 1] = true;
            cross2[currR + col] = true;

            backTrack51(currR + 1, n);

            opts[col] = '.';
            tmp51.removeLast();
            cols[col] = false;
            cross1[currR - col + n - 1] = false;
            cross2[currR + col] = false;
        }
    }

    /**
     * 77. 组合
     * <p>
     * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     * <p>
     * 递归规划：
     * 递归参数：当前数 curr，最大数 n， 需求数数量 k；
     * 返回条件：当 k== 0 时，说明已经取完给定数量的值了；
     * 选择/取消选择：加入/删除元素 i ∈ [curr, n-k+1]；
     * 进入下一层决策树：只能从选择的元素 i 之后的元素中选取，所以为 dfs(i + 1, k-1)；
     */
    LinkedList<List<Integer>> res77;
    LinkedList<Integer> tmp77;

    public List<List<Integer>> combine(int n, int k) {
        res77 = new LinkedList<>();
        tmp77 = new LinkedList<>();

        backTrack77(1, n, k);

        return res77;
    }

    void backTrack77(int curr, int n, int k) {
        if (k == 0) {
            res77.add(new ArrayList<>(tmp77));
            return;
        }

        for (int i = curr; i <= n - k + 1; i++) {
            tmp77.add(i);
            backTrack77(i + 1, n, k - 1);
            tmp77.removeLast();
        }
    }

    /**
     * 78. 子集
     * <p>
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     * <p>
     * 思路：结果需要的是所有结点，考虑在前序的时候添加结果；
     * 关键：下一层决策树应从当前位置的下一个位置开始；
     */
    LinkedList<List<Integer>> res78;
    LinkedList<Integer> tmp78;

    public List<List<Integer>> subsets(int[] nums) {
        res78 = new LinkedList<>();
        tmp78 = new LinkedList<>();
        backTrack78(0, nums);
        return res78;
    }

    void backTrack78(int start, int[] nums) {
        res78.add(new ArrayList<>(tmp78));
        for (int i = start; i < nums.length; i++) {
            tmp78.add(nums[i]);
            backTrack78(i + 1, nums); // 只能选择之后的元素
            tmp78.removeLast();
        }
    }

    /**
     * 79. 单词搜索
     * <p>
     * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用。
     * <p>
     * 思路：标准的 dfs 框架
     * 1. 不允许重复使用，则使用一个 visited 数组记录；
     * 2. 使用方向数组加循环来模拟位移
     * 3. 使用一个 result 变量记录当前结果
     */
    boolean[][] isVisited;
    char[][] board;
    String word;
    int R, C;

    public boolean exist79(char[][] board, String word) {
        this.board = board;
        this.word = word;
        this.R = board.length;
        this.C = board[0].length;
        this.isVisited = new boolean[R][C];

        for (int r = 0; r < R; ++r)
            for (int c = 0; c < C; ++c) {
                if (dfs79(0, r, c)) return true;
            }

        return false;
    }

    boolean dfs79(int idx, int r, int c) {
        if (board[r][c] != word.charAt(idx)) return false;
        else if (idx == word.length() - 1) return true;

        isVisited[r][c] = true;
        boolean result = false;
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            int nextR = r + dir[0];
            int nextC = c + dir[1];

            if (nextR >= 0 && nextR < R && nextC >= 0 && nextC < C)
                if (!isVisited[nextR][nextC])
                    if (dfs79(idx + 1, nextR, nextC)) {
                        result = true;
                        break;
                    }
        }
        isVisited[r][c] = false;

        return result;
    }

    /**
     * 93. 从字符串复原 ip 地址
     */
    static final int SEG_COUNT = 4;
    List<String> ans = new ArrayList<>();
    int[] segments = new int[SEG_COUNT];

    public List<String> restoreIpAddresses(String s) {
        segments = new int[SEG_COUNT];
        dfs(s, 0, 0);
        return ans;
    }

    void dfs(String s, int segId, int segStart) {
        // 4 段地址且已遍历完字符串
        if (segId == SEG_COUNT) {
            if (segStart == s.length()) {
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < SEG_COUNT; ++i) {
                    ipAddr.append(segments[i]);
                    if (i != SEG_COUNT - 1)
                        ipAddr.append(".");
                }
                ans.add(ipAddr.toString());
            }
            return;
        }

        // 还未找到 4 段就已经遍历完字符串
        if (segStart == s.length())
            return;

        // 遇到 0 值，必须为独立的一段（无前导0）
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); ++segEnd) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xFF) {
                segments[segId] = addr;
                dfs(s, segId + 1, segEnd + 1);
            } else break;
        }
    }

    /**
     * 200. 岛屿数量
     * <p>
     * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     * <p>
     * 思路：dfs 遍历框架，遍历二维网格，当遇到陆地时遍历四周；
     */
    public int maxAreaOfIsland(int[][] grid) {
        int islandNum = 0;
        int R = grid.length;
        if (R == 0) return islandNum;
        int C = grid[0].length;

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == 1) {
                    ++islandNum;
                    dfs(grid, r, c, R, C);
                }
            }
        }

        return islandNum;
    }

    void dfs(int[][] grid, int r, int c, int R, int C) {
        if (r < 0 || c < 0 || r >= R || c >= C || grid[r][c] == 0)
            return;

        grid[r][c] = 0;

        dfs(grid, r + 1, c, R, C);
        dfs(grid, r - 1, c, R, C);
        dfs(grid, r, c + 1, R, C);
        dfs(grid, r, c - 1, R, C);
    }

    /**
     * 301. 删除无效的括号
     *
     * 删除最小数量的无效括号，使得输入的字符串有效，返回所有可能的结果。
     *
     * 思路：BFS 框架，决策树每一层对应删除相同数量的括号；
     * BFS 要素总结：
     * 1. 队列 queue：通过队列尺寸限界模拟处理一层决策树的情形，特别要注意遍历须 (int i = queue.size(); i > 0; i--) 而
     * 不是 (int i = 0; i < queue.size(); i++) 原因是队列长度变更时可能会改变上界引起出错；
     * 2. 状态集合 set：记录已加入决策树中的状态，因为 bfs 只有当前状态的信息，而不是 dfs 记录的迄今经历的路径；
     * 3. （可选）标记变量 found：一般放置于进入下一层决策树的循环条件中，用于剪枝；
     * 4. （可选）决策层计数变量 step：记录决策树的总层数，比如此题中可记录删除的括号数；
     */
    public List<String> removeInvalidParentheses(String s) {
        Set<String> set = new HashSet<>() {{
            add(s);
        }};
        Queue<String> queue = new LinkedList<>() {{
            offer(s);
        }};
        boolean found = false;
        List<String> res = new LinkedList<>();

        while (!found) {
            for (int i = queue.size(); i > 0; i--) {
                String curr = queue.poll();
                if (isValid301(curr)) {
                    found = true;
                    res.add(curr);
                }

                for (int j = 0; j < curr.length() && !found; j++) {
                    if (curr.charAt(j) == '(' || curr.charAt(j) == ')') {
                        String next = curr.substring(0, j) + curr.substring(j + 1);
                        if (!set.contains(next)) {
                            set.add(next);
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        return res;
    }

    boolean isValid301(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                stack.push(c);
            else if (c == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    /**
     * 329. 矩阵中的最长递增路径
     * <p>
     * 给定一个整数矩阵，找出最长递增路径的长度。
     * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。
     * <p>
     * 思路：使用备忘录消除 dfs 重叠的子问题；
     */
    int[][] memo329;
    int[][] dirs;

    public int longestIncreasingPath(int[][] matrix) {
        int R = matrix.length;
        if (R == 0) return 0;
        int C = matrix[0].length;

        memo329 = new int[R][C];
        dirs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        int res = 0;

        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++)
                res = Math.max(res, dfs329(r, c, R, C, matrix));

        return res;
    }

    int dfs329(int r, int c, int R, int C, int[][] matrix) {
        if (memo329[r][c] > 0)
            return memo329[r][c];

        ++memo329[r][c];
        for (int[] dir : dirs) {
            int nextR = r + dir[0];
            int nextC = c + dir[1];

            if (nextR >= 0 && nextR < R && nextC >= 0 && nextC < C && matrix[r][c] > matrix[nextR][nextC])
                memo329[r][c] = Math.max(memo329[r][c], dfs329(nextR, nextC, R, C, matrix) + 1);
        }

        return memo329[r][c];
    }

    /**
     * 547. 朋友圈
     */
    boolean[] visited;

    public int findCircleNum(int[][] M) {
        visited = new boolean[M.length];
        Arrays.fill(visited, false);

        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                ++count;
                visited[i] = true;
                dfs1(M, i);
            }
        }

        return count;
    }

    void dfs1(int[][] M, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs1(M, j);
            }
        }
    }

    public static void main(String[] args) {
        new Solution().removeInvalidParentheses("()())()");
    }
}
