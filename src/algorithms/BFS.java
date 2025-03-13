package algorithms;

import graph.*;
import utils.PathUtils;
import java.util.*;

public class BFS {
    public static List<Node> bfs(Graph graph, Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == end) break;
            for (Edge edge : graph.getEdgesFrom(current)) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return PathUtils.reconstructPath(parentMap, end);
    }
}