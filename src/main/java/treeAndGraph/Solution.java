package treeAndGraph;

import struc.Node;
import struc.TreeNode;

import java.util.*;

public class Solution {
    /**
     * 剑指 Offer 08. 寻找二叉树的下一个节点
     * 给定一个节点，求按中序遍历顺序该节点的下一个节点
     * 1. 节点有右子节点，则下一个节点为右子数的最左节点
     * 2. 节点无右子节点
     * 2.1. 节点为父节点的左结点，则下一个节点为父节点
     * 2.2. 节点为父节点的右节点，则不断向当前的父节点遍历，直至找到一个节点为其父节点的左节点
     * 返回该节点的父节点
     */
    public TreeNode findInorderNext(TreeNode node) {
        if (node.right != null) {
            TreeNode temp = node.right;
            while (temp.left != null)
                temp = temp.left;
            return temp;
        } else if (node == node.parent.left)
            return node.parent;
        else {
            while (node != node.parent.left)
                node = node.parent;
            return node == null ? null : node.parent;
        }
    }

    /**
     * 剑指 Offer 26. 树的子结构
     * <p>
     * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
     * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
     * <p>
     * 思路：分三种情况，B 子树头与 A 树头重合、在 A 树左子树中、在 A 树右子树中
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return (A != null && B != null) && (isFound(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B));
    }

    boolean isFound(TreeNode A, TreeNode B) {
        if (B == null) return true;

        if (A == null || A.val != B.val) return false;

        return isFound(A.left, B.left) && isFound(A.right, B.right);
    }

    /**
     * 剑指 Offer 27. 二叉树的镜像
     * <p>
     * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) return null;
        // 递归
        /*TreeNode tmp = root.right;
        root.right = mirrorTree(root.left);
        root.left = mirrorTree(tmp);*/

        // 辅助栈，自顶向下
        Stack<TreeNode> stack = new Stack<>() {{
            push(root);
        }};
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
            TreeNode tmp = node.right;
            node.right = node.left;
            node.left = tmp;
        }

        return root;
    }

    /**
     * 剑指 Offer 28. 对称的二叉树
     * <p>
     * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像
     * 一样，那么它是对称的。
     * <p>
     * 思路：考虑先左后右和先右后左两种前序遍历顺序，对称二叉树两种情况下均为同一序列
     */
    public boolean isSymmetric(TreeNode root) {
        return root == null || isSymmetric(root.left, root.right);
    }

    boolean isSymmetric(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null || node1.val != node2.val) return false;
        return isSymmetric(node1.left, node2.right) && isSymmetric(node1.right, node2.left);
    }

    /**
     * 剑指 Offer 32 - I. 从上到下打印二叉树
     * <p>
     * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
     * <p>
     * 思路：层次遍历，每次外循环读取一次队列尺寸以从左到右添加一层元素
     */
    public int[] levelOrder1(TreeNode root) {
        if (root == null) return new int[0];
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<Integer> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode curr = queue.poll();
                res.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }

        // List 转 int[] 需要先转化为 InputStream，明显比循环慢
        // return res.stream().mapToInt(Integer::valueOf).toArray();
        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++)
            ans[i] = res.get(i);

        return ans;
    }

    /**
     * 剑指 Offer 32 - II. 从上到下打印二叉树 II
     * <p>
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode curr = queue.poll();
                tmp.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            res.add(tmp);
        }

        return res;
    }

    /**
     * 剑指 Offer 32 - III. 从上到下打印二叉树 III
     * <p>
     * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按
     * 照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
     * <p>
     * 思路：双端队列 + flag 奇偶标记
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) deque.offer(root);
        boolean isReversed = false;

        while (!deque.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            if (isReversed) {
                for (int i = deque.size(); i > 0; i--) {
                    TreeNode node = deque.pollLast();
                    tmp.add(node.val);
                    if (node.right != null) deque.offerFirst(node.right);
                    if (node.left != null) deque.offerFirst(node.left);
                }
                isReversed = false;
            } else {
                for (int i = deque.size(); i > 0; i--) {
                    TreeNode node = deque.pollFirst();
                    tmp.add(node.val);
                    if (node.left != null) deque.offerLast(node.left);
                    if (node.right != null) deque.offerLast(node.right);
                }
                isReversed = true;
            }
            res.add(tmp);
        }

        return res;
    }

    /**
     * 剑指 Offer 33. 二叉搜索树的后序遍历序列
     * <p>
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假
     * 设输入的数组的任意两个数字都互不相同。
     * <p>
     * 思路：后序遍历根节点在最后，对于二叉搜索树，可以通过比较确定左右子树的后序遍历边界
     * 递归为：左子树、右子树分别比根节点小、大，且左子树、右子树也为二叉搜索树
     * <p>
     * 启发：给定一个后序遍历，根据左右子树的后序遍历、根节点的位置关系确定递归
     */
    public boolean verifyPostorder(int[] postorder) {
        return verifyPostorder(postorder, 0, postorder.length - 1);
    }

    boolean verifyPostorder(int[] postorder, int l, int r) {
        if (l >= r) return true;

        int p = l;
        while (postorder[p] < postorder[r]) ++p; // 扫描左子树，均须比根节点小
        int mid = p;
        while (postorder[p] > postorder[r]) ++p; // 扫描右子树，均须比根节点大

        // 确认扫描完成，且左右子树也是二叉搜索树
        return p == r && verifyPostorder(postorder, l, mid - 1) && verifyPostorder(postorder, mid, r - 1);
    }

    /**
     * 剑指 Offer 34. 二叉树中和为某一值的路径
     * <p>
     * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往
     * 下一直到叶节点所经过的节点形成一条路径。
     * <p>
     * 思路：使用回溯，返回条件为节点为 null，检测选择在作出选择（加入列表、目标值变更新）后进行
     * 检测条件为路径已到叶节点且目标值为0。
     * <p>
     * 启发1：回溯特别需要注意返回和检测选择的时机和条件，若在节点为null时再检测，则会把所有答案
     * 均添加两次入结果（叶子结点的左、右均判断一次），使用链表在撤销选择时更方便（removeLast）
     * 启发2：涉及累计值时，使用一个目标值变量（tgt）一般优于当前值（curr）与给定值（sum）比较
     */
    LinkedList<Integer> tmp; // 使用链表方便撤销选择
    LinkedList<List<Integer>> res;

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        tmp = new LinkedList<>();
        res = new LinkedList<>();
        backTrack(root, sum);

        return res;
    }

    void backTrack(TreeNode root, int tgt) {
        if (root == null) return;

        tmp.add(root.val);
        tgt -= root.val;
        // 已对叶节点作出选择（结点可能为正数或负数）且目标值为0
        if (tgt == 0 && root.left == null && root.right == null)
            res.add(new LinkedList<>(tmp));

        backTrack(root.left, tgt);
        backTrack(root.right, tgt);

        tmp.removeLast();
    }

    /**
     * 剑指 Offer 36. 二叉搜索树与双向链表
     * <p>
     * 将二叉搜索树转化为双向循环链表（除头尾外升序）。链表中的每个节点都有一个前驱和后继指针。对
     * 于双向循环链表，第一个节点的前驱是最后一个节点，最后一个节点的后继是第一个节点。
     * <p>
     * 思路：框架是中序遍历，维护两个全局变量 pre 和 head，每次 dfs 的任务：将前已完成转化的链
     * 表尾 pre 与当前结点 curr 相连，结束后再将 pre 和 head 连起来形成环。
     * <p>
     * 启发：树相关的递归框架中最关键的是对根节点的操作部分定界限，此题中为将先前已转化的链表尾与当
     * 前结点相连，而并没有越界对右结点的头部进行连接使问题更复杂。
     */
    TreeNode pre, head;

    public TreeNode treeToDoublyList(TreeNode root) {
        if (root == null) return null;
        dfs(root);
        head.left = pre;
        pre.right = head;

        return head;
    }

    void dfs(TreeNode curr) {
        if (curr == null) return;

        dfs(curr.left);

        if (pre == null) head = curr; // 先前无链表说明当前结点为头部
        else pre.right = curr;
        curr.left = pre;
        pre = curr;

        dfs(curr.right);
    }

    /**
     * 剑指 Offer 54. 二叉搜索树的第k大节点
     * <p>
     * 给定一棵二叉搜索树，请找出其中第k大的节点。
     * <p>
     * 思路：反向中序遍历，使用成员变量操作，递减 k 可以节省变量使用
     */
    int k, resVal;

    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        recur(root);
        return resVal;
    }

    void recur(TreeNode root) {
        if (root == null) return;

        recur(root.right);
        if (--k == 0) {
            resVal = root.val;
            return;
        }
        recur(root.left);
    }

    /**
     * 剑指 Offer 55 - I. 二叉树的深度
     * <p>
     * 输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含
     * 根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
     * <p>
     * 思路：后序遍历，若 root 不为 null，最大深度为 max(左子树深度，右子树深度）+ 1
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 剑指 Offer 55 - II. 平衡二叉树
     * <p>
     * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度
     * 相差不超过1，那么它就是一棵平衡二叉树。
     * <p>
     * 思路：后序遍历，剪枝
     * 由底向顶的过程中，如果已经发现存在不平衡的部分，提前 return 一个错误值退出递归
     */
    public boolean isBalanced(TreeNode root) {
        return recur55(root) != -1;
    }

    int recur55(TreeNode node) {
        if (node == null) return 0;

        int left = recur55(node.left);
        if (left == -1) return -1;

        int right = recur55(node.right);
        if (right == -1) return -1;
        // 注意 abs 返回的是一个 double 类变量，应该使用 < 2 而不是 <= 1（double 不应该使用 == 判断相等）
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }

    /**
     * 剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
     * <p>
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * 最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结
     * 点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。
     * <p>
     * 思路：由于是已排好序的搜索树，自顶向下遍历，找到一个节点在两值之间即可。
     * 启发：搜索数由于其特性，直接通过比较大小就能确定 p、q 在根节点的左侧、右侧还是异侧；
     */
    public TreeNode lowestCommonAncestor68(TreeNode root, TreeNode p, TreeNode q) {
        int curr = root.val;
        int hi = Math.max(p.val, q.val);
        int lo = Math.max(p.val, q.val);

        if (curr > hi) return lowestCommonAncestor68(root.left, p, q);
        else if (curr < lo) return lowestCommonAncestor68(root.right, p, q);
        else return root;
    }

    /**
     * 98. 验证二叉搜索树
     * <p>
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 思路：中序遍历，使用一个成员变量记录遍历的最近一个数；
     */
    Long preVal = Long.MIN_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (!isValidBST(root.left)) return false;
        if (root.val <= preVal) return false;
        preVal = (long) root.val;

        return isValidBST(root.right);
    }

    /**
     * 111. 二叉树的最小深度
     * <p>
     * 给定一个二叉树，找出其最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * <p>
     * 思路：BFS 层级遍历，遇到叶子节点时退出循环；
     */
    public int minDepth(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) queue.offer(root);

        int depth = 0;
        boolean isFound = false;
        while (!queue.isEmpty() && !isFound) {
            ++depth;
            for (int i = queue.size(); i > 0 && !isFound; i--) {
                TreeNode curr = queue.poll();
                if (curr.left == null && curr.right == null)
                    isFound = true;
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }

        return depth;
    }

    /**
     * 114. 二叉树展开为链表
     * <p>
     * 给定一个二叉树，原地将它展开为一个单链表，即全部节点的 left 为 null。
     * <p>
     * 思路：后序遍历，先展开左子树和右子树，然后再拼接；
     */
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.left);
        flatten(root.right);

        /*TreeNode lLast = root.left;
        if (lLast == null) return;

        while (lLast.right != null) lLast = lLast.right;

        TreeNode temp = root.right;
        root.right = root.left;
        lLast.right = temp;
        root.left = null;*/

        TreeNode left = root.left;
        TreeNode right = root.right;
        // 左边放空，右边防止原左子树；
        root.left = null;
        root.right = left;

        // 找到原左子树尾端；
        TreeNode p = root;
        while (p.right != null)
            p = p.right;

        // 拼接
        p.right = right;
    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针
     * <p>
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则
     * 将 next 指针设置为 NULL。
     * 初始状态下，所有 next 指针都被设置为 NULL。
     * <p>
     * 思路：前序遍历，从该层的最左端开始向右（next 方向）遍历处理下一层的连接问题；
     * 然后递归至下一层的最左端，直至倒数第 2 层；
     */
    public Node connect(Node root) {
        if (root == null || root.left == null) return root;
        Node p = root;
        while (p != null) {
            p.left.next = p.right;
            if (p.next != null) p.right.next = p.next.left;
            p = p.next;
        }

        connect(root.left);

        return root;
    }

    /**
     * 124. 二叉树中的最大路径和
     * <p>
     * 给定一个非空二叉树，返回其最大路径和。
     * 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
     * <p>
     * 思路：后序遍历框架，定义一个递归函数 transverse，返回值为经过当前节点且可以继续延伸的最大路径和，为 null 时返回 0；
     * 设 left、right 分别为左右子树的返回值，设当 left、right 均大于 0 时：
     * 最大路径可能有两种情况：
     * 1. 可能是不会继续往上一级延伸了，这时候得到可能的最大值为 left + root.val + right，
     * 2. 可能需要继续延伸，则目前的最大路径和为 max(left, right) + root.val；
     * 而当 left、right 为负时，说明子树产生负贡献，直接舍弃将起点设为根即可；
     */
    int pathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        transverse(root);

        return pathSum;
    }

    int transverse(TreeNode root) {
        if (root == null) return 0;
        int left = transverse(root.left);
        int right = transverse(root.right);

        // 如果为负贡献，则舍弃这部分；
        if (left < 0) left = 0;
        if (right < 0) right = 0;

        int max1 = left + root.val + right;
        pathSum = Math.max(pathSum, max1);

        return root.val + Math.max(left, right);
    }

    /**
     * 222. 完全二叉树的节点个数
     * <p>
     * 给出一个完全二叉树，求出该树的节点个数。
     * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最
     * 下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1 ~ 2 ^ h 个节点。
     * <p>
     * 思路：结合数普通二叉树和满二叉树的两种方法优化时间复杂度至 logN * logN；
     * 由于元素会优先出现在该层的左边，可以对比一直向左和一直向右的深度 hl 和 hr；
     * 1. hl == hr，说明为满二叉树，直接返回公式即可；
     * 2. hl > hr，则使用普通的二叉树结点数法，这里要注意左右子树必有一个直接返回公式的满二叉树；
     * 总的来说，while 循环时间为 logN，递归深度也为 logN，时间复杂度为 logN * logN；
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;

        TreeNode p = root;
        int hl = 0, hr = 0;
        while (p != null) {
            ++hl;
            p = p.left;
        }

        p = root;
        while (p != null) {
            ++hr;
            p = p.right;
        }

        if (hl == hr) return (int) Math.pow(2, hl) - 1;

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * 230. 二叉搜索树中第K小的元素
     * <p>
     * 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。
     * <p>
     * 思路：（重点）借助数据结构栈模拟中序遍历；
     */
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if (--k == 0) return root.val;

            root = root.right;
        }
    }

    /**
     * 236. 二叉树的最近公共祖先
     * <p>
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * <p>
     * 思路：转化为：寻找一个目标节点，两个指定节点分别在其左右子树中；
     * 后序遍历所有节点，当找到目标节点 p 或 q 或者为 null 时（即已遍历完）返回；
     * 若左子树没找到，目标节点必在当前节点的右子树中；
     * 若右子树没找到，...左子树中；
     * 若左、右子树均找到了（返回节点均不为 null），当前节点即为目标节点；
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    /**
     * 337. 打家劫舍 III
     * <p>
     * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之
     * 为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方
     * 的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
     * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
     * <p>
     * 思路：每个节点只有两个状态：选中和未选中，可用一个数组（返回值）保存两个状态对应的当前最优解；
     * 使用后序遍历自底向顶遍历，设 0 未选中 1 为选中：f(root.left) = int[] l ; f(root.right) = int[] r;
     * 对于当前节点，选中的较优解为：curr[1] = root.val + l[0] + r[0]；即左、右子节点必须同时未选中；
     * 而未选中的较优解为 curr[0] = max(l(0) + l(1)) + max(r(0) + r(1))；即左右节点无限制，可选中也可不选中；
     */
    public int rob(TreeNode root) {
        int[] result = dfs337(root);
        return Math.max(result[0], result[1]);
    }

    int[] dfs337(TreeNode root) {
        if (root == null) return new int[]{0, 0};

        int[] l = dfs337(root.left);
        int[] r = dfs337(root.right);
        int selected = root.val + l[0] + r[0];
        int notSelected = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
        return new int[]{notSelected, selected};
    }

    /**
     * 450. 删除二叉搜索树中的节点
     * <p>
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并
     * 保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
     * 要求算法时间复杂度为 O(h)，h 为树的高度。
     * <p>
     * 思路：类似插入操作的二分递归思维，比大小确定哪部分子树会发生改变，然后拼接递归后返回的根节点；
     * 当找到目标值对应的节点时：
     * 1. 节点左右均为 null，则直接删除即可，即返回 null；
     * 2. 节点仅有一边不为 null，则直接与不为 null 的那边拼接即可；
     * 3. 节点左右均不为 null，这时为不破坏树结构有两个选择：
     * 3.1. 节点值替换为左子树的最大值，即将节点值赋值后再删掉左子树最大值节点；
     * 3.2. 节点值替换为右子树的最小值，类似 3.1；
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key > root.val)
            root.right = deleteNode(root.right, key);
        else if (key < root.val)
            root.left = deleteNode(root.left, key);
        else {
            // 1. 若 root 两端有一端为 null，直接拼接另一端即可；
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // 2. 若两端均不为 null，为维持结构可以选择替换为右子树的最小值
            // 或者左子树的最大值，这里选择前者；
            TreeNode p = root.right;
            while (p.left != null)
                p = p.left;

            root.val = p.val;
            root.right = deleteNode(root.right, p.val);
        }

        return root;
    }

    /**
     * 538. 把二叉搜索树转换为累加树
     * <p>
     * 给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree）
     * 使每个节点 node 的新值等于原树中大于或等于 node.val 的值之和。
     * <p>
     * 思路：右 - 根 - 前 中序遍历，使用一个成员变量 sum 记录当前比 root 值大的所有值的和；
     * 注意对根赋值不要使用累加 "+=" 操作，会因为递归出错；
     */
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null) return null;

        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);

        return root;
    }

    /**
     * 652. 寻找重复的子树
     * <p>
     * 给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只
     * 需要返回其中任意一棵的根结点即可。
     * 两棵树重复是指它们具有相同的结构以及相同的结点值。
     * <p>
     * 思路：后序遍历，使用一个字符串记录当前节点的结构（左 + "，" + 右 + "，" + 根， null 使用 "#" 标记）；
     * 得到结构字符串后在已存储字符串（键）和出现次数（值）的哈希表中出现次数是否恰好为 1，若为真则为一个结果节点；
     */
    Map<String, Integer> memo;
    List<TreeNode> ans;

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        memo = new HashMap<>();
        ans = new LinkedList<>();
        getStruAndFindDuplicate(root);

        return ans;
    }

    String getStruAndFindDuplicate(TreeNode root) {
        if (root == null) return "#";

        String left = getStruAndFindDuplicate(root.left);
        String right = getStruAndFindDuplicate(root.right);

        String stru = left + "," + right + "," + root.val;
        int count = memo.getOrDefault(stru, 0);
        if (count == 1)
            ans.add(root);
        memo.put(stru, count + 1);

        return stru;
    }

    /**
     * 654. 最大二叉树
     * <p>
     * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
     * 二叉树的根是数组中的最大元素。
     * 左子树是通过数组中最大值左边部分构造出的最大二叉树。
     * 右子树是通过数组中最大值右边部分构造出的最大二叉树。
     * 通过给定的数组构建最大二叉树，并且输出这个树的根节点。
     * <p>
     * 思路：前序遍历，确定根位置后再拼接迭代的左右根节点；
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree(nums, 0, nums.length - 1);
    }

    TreeNode constructMaximumBinaryTree(int[] nums, int l, int r) {
        if (l > r) return null;
        int p = -1, max = Integer.MIN_VALUE;

        for (int i = l; i <= r; i++) {
            if (nums[i] > max) {
                p = i;
                max = nums[i];
            }
        }

        TreeNode root = new TreeNode(nums[p]);
        root.left = constructMaximumBinaryTree(nums, l, p - 1);
        root.right = constructMaximumBinaryTree(nums, p + 1, r);

        return root;
    }

    /**
     * 701. 二叉搜索树中的插入操作
     * <p>
     * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节
     * 点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
     * <p>
     * 思路：类似二分查找，根据搜索树的特性确定下个区间；
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val)
            root.left = insertIntoBST(root.left, val);
        else root.right = insertIntoBST(root.right, val);

        return root;
    }

    /**
     * 752. 打开转盘锁
     * <p>
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     * 字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。
     * <p>
     * 思路：BFS 框架，使用两个散列集分别记录死亡数字和已遍历过的数字；
     * 决策池：四个位置上或下共 8 个选择中没有被遍历过的数字；
     * 特殊：1. 如果找到了目标，直接返回当前步数；2. 如果为死亡数字，则不能进入下一层决策树（continue）；
     */
    public int openLock(String[] deadends, String target) {
        Set<String> ends = new HashSet<>(Arrays.asList(deadends));
        Set<String> considered = new HashSet<>() {{
            add("0000");
        }};
        Queue<String> queue = new LinkedList<>() {{
            offer("0000");
        }};
        int step = 0;

        while (!queue.isEmpty()) {

            for (int j = queue.size(); j > 0; j--) {
                String curr = queue.poll();
                if (curr.equals(target)) return step;
                if (ends.contains(curr)) continue;

                for (int i = 0; i < 4; i++) { // 下一层决策树共 8 个选择；
                    String up = upRoll(curr, i);
                    if (!considered.contains(up)) {
                        queue.offer(up);
                        considered.add(up);
                    }

                    String down = downRoll(curr, i);
                    if (!considered.contains(down)) {
                        queue.offer(down);
                        considered.add(down);
                    }
                }
            }

            ++step;
        }

        return -1;
    }

    String upRoll(String s, int idx) {
        char[] chars = s.toCharArray();
        int tmp = (chars[idx] - '0' + 1) % 10;
        chars[idx] = (char) (tmp + '0');
        return String.valueOf(chars);
    }

    String downRoll(String s, int idx) {
        char[] chars = s.toCharArray();
        int tmp = (chars[idx] - '0' + 9) % 10;
        chars[idx] = (char) (tmp + '0');
        return String.valueOf(chars);
    }

    /**
     * 773. 滑动谜题
     * <p>
     * 在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示.
     * 一次移动定义为选择 0 与一个相邻的数字（上下左右）进行交换.
     * 最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
     * 给出一个谜板的初始状态，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
     * <p>
     * 思路：考虑使用一个字符串表示谜板当前的状态，例 "324150" 表示 board = [[3,2,4],[1,5,0]]；
     * 如果谜板之前进入过这个状态，则跳过这个选择；
     * 为了体现二维的相对位置，使用一个映射保存当前位置与邻近位置的映射，比如 0 -> [1,3] 说明索引 0 处与索引
     * 1 和索引 3 处邻近，交换体现在交换字符串两个索引上的字符；
     * <p>
     * 启发：BFS 无法记录路径信息，要想排除回头路的选择只能记录状态（类似转盘锁目前各个位置上的数字）；
     */
    public int slidingPuzzle(int[][] board) {
        StringBuilder start = new StringBuilder();
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 3; c++)
                start.append(board[r][c]);

        Queue<String> queue = new LinkedList<>();
        queue.offer(start.toString());
        Set<String> visited = new HashSet<>();
        visited.add(start.toString());
        String target = "123450";
        int step = 0;

        Map<Integer, int[]> neighborsMap = new HashMap<>() {{
            put(0, new int[]{1, 3});
            put(1, new int[]{0, 2, 4});
            put(2, new int[]{1, 5});
            put(3, new int[]{0, 4});
            put(4, new int[]{1, 3, 5});
            put(5, new int[]{2, 4});
        }};

        while (!queue.isEmpty()) {

            for (int i = queue.size(); i > 0; i--) {
                String curr = queue.poll();
                if (curr.equals(target)) return step;

                int idx = 0;
                while (curr.charAt(idx) != '0') idx++;

                for (int neighbor : neighborsMap.get(idx)) {
                    String next = swap(curr, idx, neighbor);
                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }

            ++step;
        }

        return -1;
    }

    String swap(String s, int i, int j) {
        char[] chars = s.toCharArray();
        char tmp = s.charAt(i);
        chars[i] = chars[j];
        chars[j] = tmp;
        return String.valueOf(chars);
    }

    boolean board_is_finished(int board[][]) {
        return board[0][0] == 1 && board[0][1] == 2 && board[0][2] == 3
                && board[1][0] == 4 && board[1][1] == 5 && board[1][2] == 0;
    }

    public static void main(String[] args) {
        new Solution().slidingPuzzle(new int[][]{{3, 2, 4}, {1, 5, 0}});
    }

}
