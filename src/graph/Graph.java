package graph;

import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(Node node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node target, int weight) {
        adjacencyList.get(source).add(new Edge(source, target, weight));
        adjacencyList.get(target).add(new Edge(target, source, weight));
    }

    public List<Edge> getEdgesFrom(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public List<Node> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }
}