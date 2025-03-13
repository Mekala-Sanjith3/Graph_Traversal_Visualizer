package algorithms;

import graph.*;
import utils.PathUtils;
import java.util.*;

public class DFS {
    public static List<Node> dfs(Graph graph, Node start, Node end) {
        Stack<Node> stack = new Stack<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current == end) break;
            for (Edge edge : graph.getEdgesFrom(current)) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }
        return PathUtils.reconstructPath(parentMap, end);
    }
}