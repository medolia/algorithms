package unionFind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    /**
     * 399. 除法求值
     * <p>
     * 给你一个变量对数组 equations 和一个实数值数组 values 作为已知条件，其中 equations[i] = [Ai, Bi] 和 values[i] 共同表示等式 Ai / Bi = values[i] 。每个 Ai 或 Bi 是一个表示单个变量的字符串。
     * 另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。
     * 返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。
     * 注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
     * <p>
     * 思路：并查集，定义如下：
     * int[] parent, int[] weight: parent[x] 表示 x 指向 parent[x]，权重为 weight[x]，即 x 结点代表数 / parent[x] 结点代表数 = weight[x]；
     * <p>
     * int find(int x): 找到 x 所对应的根，此方法会保证树的深度不会超过 3 层以维持较优查询性能；
     * void union(int x, int y, int value): 链接 x 和 y：先使用 find() 找到 x 和 y 对应的根 rootX 和 rootY，然后链接 rootX 和 rootY；
     * double isConnected(int x, int y): 先使用 find() 找到 x 和 y 对应的根 rootX 和 rootY，若两根为同一结点则返回正确结果值 weight[x] / weight[y]；
     * <p>
     * 补充：使用 map 记录每个字母对应的 id；
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int equationSize = equations.size();
        UnionFind399 unionFind = new UnionFind399(2 * equationSize);
        Map<String, Integer> map = new HashMap<>(2 * equationSize);
        int id = 0;

        for (int i = 0; i < equationSize; i++) {
            String var1 = equations.get(i).get(0);
            String var2 = equations.get(i).get(1);
            double value = values[i];
            if (!map.containsKey(var1))
                map.put(var1, id++);
            if (!map.containsKey(var2))
                map.put(var2, id++);

            int id1 = map.get(var1);
            int id2 = map.get(var2);

            unionFind.union(id1, id2, value);
        }

        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String var1 = queries.get(i).get(0);
            String var2 = queries.get(i).get(1);

            Integer id1 = map.get(var1);
            Integer id2 = map.get(var2);

            res[i] = id1 == null || id2 == null ? -1.0d : unionFind.isConnected(id1, id2);
        }

        return res;
    }

    class UnionFind399 {
        int[] parent;
        double[] weight;

        public UnionFind399(int n) {
            parent = new int[n];
            weight = new double[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1.0d;
            }
        }

        void union(int x, int y, double value) {
            int rootX = find(x);
            int rootY = find(y);

            parent[rootX] = rootY;
            weight[rootX] = value * weight[y] / weight[x];
        }

        int find(int x) {
            if (x != parent[x]) {
                int org = parent[x];
                parent[x] = find(parent[x]);
                weight[x] *= weight[org];
            }

            return parent[x];
        }

        double isConnected(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            return rootX == rootY ? weight[x] / weight[y] : -1.0d;
        }
    }

    /**
     * 684. 冗余连接
     *
     * 在本问题中, 树指的是一个连通且无环的无向图。
     * 输入一个图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
     * 结果图是一个以边组成的二维数组。每一个边的元素是一对[u, v] ，满足 u < v，表示连接顶点u 和v的无向图的边。
     * 返回一条可以删去的边，使得结果图是一个有着N个节点的树。如果有多个答案，则返回二维数组中最后出现的边。答案边 [u, v] 应满足相同的格式 u < v。
     *
     * 思路：并查集，注意因为一个数组只能表示一个链接方向，若需实现无向图需要保证 parent[x] 总是大于等于 x，即总是小索引指向大索引；
     */
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        UF684 uf = new UF684(n + 1);

        int[] res = new int[2];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (uf.connected(u, v)) res = edge;
            else uf.union(u, v);
        }

        return res;
    }

    class UF684 {
        int[] parent;

        public UF684(int n) {
            parent = new int[n];

            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x])
                parent[x] = find(parent[x]);

            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) parent[rootX] = rootY;
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    public int makeConnected(int n, int[][] connections) {
        UF1319 uf = new UF1319(n);

        int optCount = 0;
        for (int[] conn : connections) {
            int u = conn[0], v = conn[1];
            if (!uf.connected(u, v)) uf.union(u, v);
            else ++optCount;
        }

        if (uf.getCount() - 1 <= optCount) return uf.getCount() - 1;
        else return -1;
    }

    class UF1319 {
        int[] parent;
        int count;

        public UF1319(int n) {
            count = n;
            parent = new int[n];

            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x])
                parent[x] = find(parent[x]);

            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                parent[rootX] = rootY;
                count--;
            }
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        new Solution().findRedundantConnection(new int[][]{{1,2}, {2,3}, {1,3}});
    }
}
