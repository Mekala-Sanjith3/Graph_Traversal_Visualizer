package gui;

import graph.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {
    private Graph graph;
    private List<Edge> mst = new ArrayList<>();
    private List<Node> path = new ArrayList<>();
    private List<Node> visitedNodes = new ArrayList<>();

    public void setGraph(Graph graph) { this.graph = graph; }
    public void setPath(List<Node> path) { this.path = path; }
    public void setVisitedNodes(List<Node> visitedNodes) { this.visitedNodes = visitedNodes; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEdges(g);
        drawMST(g);
        drawNodes(g);
    }
    public void setMST(List<Edge> mst) {
        this.mst = mst;
    }
    private void drawMST(Graphics g) {
        g.setColor(Color.GREEN);
        for (Edge edge : mst) {
            Node source = edge.getSource();
            Node target = edge.getTarget();
            g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
        }
    }


    private void drawEdges(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (Node node : graph.getNodes()) {
            for (Edge edge : graph.getEdgesFrom(node)) {
                Node source = edge.getSource();
                Node target = edge.getTarget();
                g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
                
                // Draw weight
                int midX = (source.getX() + target.getX()) / 2;
                int midY = (source.getY() + target.getY()) / 2;
                g.drawString(String.valueOf(edge.getWeight()), midX, midY);
            }
        }
    }

    private void drawVisitedNodes(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Node node : visitedNodes) {
            g.fillOval(node.getX() - 10, node.getY() - 10, 20, 20);
        }
    }

    private void drawPath(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            g.drawLine(current.getX(), current.getY(), next.getX(), next.getY());
        }
    }

    private void drawNodes(Graphics g) {
        for (Node node : graph.getNodes()) {
            // Color coding
            if (node.isStart()) g.setColor(Color.GREEN);
            else if (node.isEnd()) g.setColor(Color.RED);
            else g.setColor(Color.BLUE);

            // Draw node
            g.fillOval(node.getX() - 10, node.getY() - 10, 20, 20);
            
            // Label
            g.setColor(Color.WHITE);
            g.drawString(node.getId(), node.getX() - 5, node.getY() + 5);
        }
    }
}