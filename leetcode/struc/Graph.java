package struc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    public GraphNode root;

    public static class GraphNode {
        public String name;
        public List<GraphNode> neighborList;

        public GraphNode(String name) {
            this.name = name;
            this.neighborList = new ArrayList<>();
        }

        public void addNeighbor(GraphNode... nodes) {
            this.neighborList.addAll(Arrays.asList(nodes));
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
