package algorithms;

import graph.*;
import utils.PathUtils;
import java.util.*;

public class Dijkstra {
    public static List<Node> dijkstra(Graph graph, Node start, Node end) {
        if (start == null || end == null) return new ArrayList<>();
        
        // Reset distances
        for (Node node : graph.getNodes()) {
            node.setDistance(Integer.MAX_VALUE);
            node.setPrevious(null);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(
            Comparator.comparingInt(Node::getDistance)
        );
        start.setDistance(0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == end) break;

            for (Edge edge : graph.getEdgesFrom(current)) {
                Node neighbor = edge.getTarget();
                int newDist = current.getDistance() + edge.getWeight();
                if (newDist < neighbor.getDistance()) {
                    neighbor.setDistance(newDist);
                    neighbor.setPrevious(current);
                    queue.add(neighbor);
                }
            }
        }
        return PathUtils.reconstructPath(end);
    }
}