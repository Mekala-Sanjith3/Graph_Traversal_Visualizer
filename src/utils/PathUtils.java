package utils;

import graph.Node;
import java.util.*;

public class PathUtils {
    public static List<Node> reconstructPath(Map<Node, Node> parentMap, Node end) {
        List<Node> path = new ArrayList<>();
        for (Node at = end; at != null; at = parentMap.get(at)) path.add(at);
        Collections.reverse(path);
        return path.isEmpty() || path.get(0) != parentMap.get(end) ? Collections.emptyList() : path;
    }

    public static List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        for (Node at = end; at != null; at = at.getPrevious()) path.add(at);
        Collections.reverse(path);
        return path;
    }
}