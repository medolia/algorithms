package treeAndGraph;

import struc.Graph;
import struc.Graph.GraphNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 无向连通图的深层拷贝
 * <p>思路：dfs + 备忘录复制节点</p>
 */
class GraphDeepCopy {

    // A--B
    // |  |
    // C--D
    public static void main(String[] args) {
        Graph orgGraph = new Graph();

        GraphNode a = new GraphNode("A");
        GraphNode b = new GraphNode("B");
        GraphNode c = new GraphNode("C");
        GraphNode d = new GraphNode("D");

        a.addNeighbor(b,c);
        b.addNeighbor(a,d);
        c.addNeighbor(a,d);
        d.addNeighbor(b,c);
        orgGraph.root = a;

        Graph copy = new GraphDeepCopy().deepCopy(orgGraph);
        System.out.println(copy);
    }

    Graph deepCopy(Graph orgGraph) {
        Graph res = new Graph();

        HashMap<GraphNode, GraphNode> old2NewMemo = new HashMap<>();
        res.root = dfsNodeCopy(orgGraph.root, old2NewMemo);
        return res;
    }

    private GraphNode dfsNodeCopy(GraphNode node, Map<GraphNode, GraphNode> memo) {
        if (node == null || memo.containsKey(node)) {
            return memo.get(node);
        }

        GraphNode newNode = new GraphNode(node.name);
        memo.put(node, newNode);
        for (GraphNode neighborNode : node.neighborList) {
            newNode.neighborList.add(dfsNodeCopy(neighborNode, memo));
        }

        return newNode;
    }


}
