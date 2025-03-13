package algorithms;

import graph.*;
import java.util.*;

public class Kruskal {
    public static List<Edge> kruskal(Graph graph) {
        List<Edge> edges = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            edges.addAll(graph.getEdgesFrom(node));
        }
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        DisjointSet disjointSet = new DisjointSet(graph.getNodes());
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            Node source = edge.getSource();
            Node target = edge.getTarget();
            if (!disjointSet.isConnected(source, target)) {
                mst.add(edge);
                disjointSet.union(source, target);
            }
        }
        return mst;
    }

    private static class DisjointSet {
        private Map<Node, Node> parent = new HashMap<>();

        public DisjointSet(List<Node> nodes) {
            for (Node node : nodes) {
                parent.put(node, node);
            }
        }

        public Node find(Node node) {
            if (parent.get(node) != node) {
                parent.put(node, find(parent.get(node)));
            }
            return parent.get(node);
        }

        public boolean isConnected(Node x, Node y) {
            return find(x) == find(y);
        }

        public void union(Node x, Node y) {
            Node rootX = find(x);
            Node rootY = find(y);
            if (rootX != rootY) {
                parent.put(rootX, rootY);
            }
        }
    }
}