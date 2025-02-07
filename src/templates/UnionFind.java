package templates;

import java.util.Arrays;

/**
 * 并查集，一种适合构建图类型的数据结构
 * <p>通过小树接大树以及路径压缩，保证 findRoot connect isConnected 的操作时间复杂度都是 O(logN)
 */
@SuppressWarnings("all")
public class UnionFind {
    int[] parent;
    int islandCount;
    int[] size;

    public UnionFind(int n) {
        this.islandCount = n;
        this.parent = new int[n];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        size = new int[n];
        Arrays.fill(size, 1);
    }

    public int findRoot(int x) {
        while (x != parent[x]) {
            // 路径压缩，保证树的高度不超过 3
            parent[x] = parent[parent[x]];

            x = parent[x];
        }

        return x;
    }

    public void connect(int x, int y) {
        int rootX = findRoot(x), rootY = findRoot(y);
        if (rootX == rootY) {
            return;
        }

        // 小链接到大链子
        if (size[rootX] > size[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        }
        --islandCount;
    }

    public boolean isConnected(int x, int y) {
        return findRoot(x) == findRoot(y);
    }

    public int getIslandCount() {
        return islandCount;
    }
}
