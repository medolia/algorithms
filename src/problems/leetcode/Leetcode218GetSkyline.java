package problems.leetcode;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/the-skyline-problem/description/">218. 天际线问题</a>
 * <p>
 * 城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。给你所有建筑物的位置和高度，请返回 由这些建筑物形成的 天际线 。
 * <p>
 * 每个建筑物的几何信息由数组 buildings 表示，其中三元组 buildings[i] = [lefti, righti, heighti] 表示：
 * <p>
 * lefti 是第 i 座建筑物左边缘的 x 坐标。
 * righti 是第 i 座建筑物右边缘的 x 坐标。
 * heighti 是第 i 座建筑物的高度。
 * 你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
 * <p>
 * 天际线 应该表示为由 “关键点” 组成的列表，格式 [[x1,y1],[x2,y2],...] ，并按 x 坐标 进行 排序 。关键点是水平线段的左端点。列表中最后一个点是最右侧建筑物的终点，y 坐标始终为 0 ，仅用于标记天际线的终点。此外，任何两个相邻建筑物之间的地面都应被视为天际线轮廓的一部分。
 * <p>
 * 注意：输出天际线中不得有连续的相同高度的水平线。例如 [...[2 3], [4 5], [7 5], [11 5], [12 7]...] 是不正确的答案；三条高度为 5 的线应该在最终输出中合并为一个：[...[2 3], [4 5], [12 7], ...]
 * <p>
 * 思路：高度优先队列 + 等x线从左至右扫描
 * <p>edge case 关键在于排序：{@link Leetcode218GetSkyline#getNodeComparator()}
 * <p>时：O(NlogN)，排序，维护高度优先序列；空：O(N)，存储节点列表，高度优先序列
 *
 * @author lbli
 */
@SuppressWarnings("all")
class Leetcode218GetSkyline {

    public static void main(String[] args) {
//        int[][] buildings = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        int[][] buildings = {{0, 2, 3}, {2, 5, 3}};
        List<List<Integer>> result = new Leetcode218GetSkyline().getSkyline(
                buildings);
        System.out.println(result);

    }

    private static Comparator<Node> getNodeComparator() {
        return (a, b) -> {
            // 1. x 升序
            if (a.x != b.x) return a.x - b.x;
            // 2. x 相等都是左边则高度高的优先
            if (a.left && b.left) return b.h - a.h;
            // 3. x 相等 都是右边则高度低的优先
            if (!a.left && !b.left) return a.h - b.h;
            // 4. x 相等但不同边，左边优先右边。由此可解决 edge case：{{0, 2, 3}, {2, 5, 3}}
            return a.left ? -1 : 1;
        };
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        PriorityQueue<Integer> heightHeap = new PriorityQueue<>(buildings.length, Comparator.reverseOrder());
        heightHeap.add(0);
        List<List<Integer>> result = new ArrayList<>();

        List<Node> nodeList = new ArrayList<>();
        for (int[] building : buildings) {
            nodeList.add(new Node(building[0], building[2], true));
            nodeList.add(new Node(building[1], building[2], false));
        }

        nodeList.sort(getNodeComparator());

        int prevH = 0;
        for (Node node : nodeList) {
            if (node.left) {
                heightHeap.add(node.h);
            } else {
                heightHeap.remove(node.h);
            }

            int currH = heightHeap.peek();
            if (currH != prevH) {
                result.add(Arrays.asList(node.x, currH));
                prevH = currH;
            }
        }

        return result;
    }

    private static class Node {
        int x, h;
        boolean left;

        public Node(int x, int h, boolean left) {
            this.x = x;
            this.h = h;
            this.left = left;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, h);
        }
    }
}
