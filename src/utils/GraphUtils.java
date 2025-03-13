package utils;

import graph.Node;
import java.awt.Point;
import java.util.List;

public class GraphUtils {
    public static Node findNodeAt(Point p, List<Node> nodes, int tolerance) {
        for (Node node : nodes) {
            if (Math.abs(node.getX() - p.x) < tolerance && Math.abs(node.getY() - p.y) < tolerance) {
                return node;
            }
        }
        return null;
    }
}