package gui;

import algorithms.*;
import graph.*;
import utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private Graph graph = new Graph();
    private GraphPanel graphPanel = new GraphPanel();
    private ControlPanel controlPanel;
    private Node startNode, endNode;
    private Node selectedNode;
    private int totalNodes = 0;
    private int placedNodes = 0;

    public MainFrame() {
        initializeUI();
        setupListeners();
        askForNodeCount();
    }

    private void initializeUI() {
        setTitle("Graph Traversal Visualizer");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        controlPanel = new ControlPanel(new ButtonListener());
        graphPanel.setGraph(graph);
        add(controlPanel, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        graphPanel.addMouseListener(new NodePlacementListener());
        graphPanel.addMouseListener(new NodeClickListener());
        graphPanel.addMouseListener(new EdgeDragListener());
    }

    private void askForNodeCount() {
        try {
            String input = JOptionPane.showInputDialog("Enter number of nodes:");
            totalNodes = Integer.parseInt(input);
            JOptionPane.showMessageDialog(this, "Click " + totalNodes + " times to place nodes");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number entered!");
        }
    }

    private class NodePlacementListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (placedNodes < totalNodes && SwingUtilities.isLeftMouseButton(e)) {
                Node node = new Node(e.getX(), e.getY(), "N" + (placedNodes + 1));
                graph.addNode(node);
                placedNodes++;
                graphPanel.repaint();
            }
        }
    }

    private class NodeClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                Node clickedNode = GraphUtils.findNodeAt(e.getPoint(), graph.getNodes(), 15);
                if (clickedNode != null) {
                    if (startNode == null) {
                        startNode = clickedNode;
                        startNode.setStart(true);
                    } else if (endNode == null) {
                        endNode = clickedNode;
                        endNode.setEnd(true);
                    }
                    graphPanel.repaint();
                }
            }
        }
    }

    private class EdgeDragListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            selectedNode = GraphUtils.findNodeAt(e.getPoint(), graph.getNodes(), 15);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Node targetNode = GraphUtils.findNodeAt(e.getPoint(), graph.getNodes(), 15);
            if (selectedNode != null && targetNode != null && !selectedNode.equals(targetNode)) {
                createEdge(selectedNode, targetNode);
            }
            selectedNode = null;
        }
    }

    private void createEdge(Node source, Node target) {
        String weight = JOptionPane.showInputDialog("Enter weight for " + 
                          source.getId() + " to " + target.getId() + ":");
        try {
            int weightValue = Integer.parseInt(weight);
            graph.addEdge(source, target, weightValue);
            graphPanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid weight value!");
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Start":
                    runAlgorithm();
                    break;
                case "Reset":
                    resetGraph();
                    break;
                case "AddEdge":
                    showEdgeDialog();
                    break;
            }
        }
    }

    private void runAlgorithm() {
        if (startNode == null || endNode == null) {
            JOptionPane.showMessageDialog(this, "Set start and end nodes first!");
            return;
        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                switch (controlPanel.getSelectedAlgorithm()) {
                    case "BFS":
                        List<Node> bfsPath = BFS.bfs(graph, startNode, endNode);
                        graphPanel.setPath(bfsPath);
                        showPathResult(bfsPath);
                        break;
                    case "DFS":
                        List<Node> dfsPath = DFS.dfs(graph, startNode, endNode);
                        graphPanel.setPath(dfsPath);
                        showPathResult(dfsPath);
                        break;
                    case "Dijkstra":
                        List<Node> dijkstraPath = Dijkstra.dijkstra(graph, startNode, endNode);
                        graphPanel.setPath(dijkstraPath);
                        showPathResult(dijkstraPath);
                        break;
                    case "Kruskal":
                        List<Edge> mst = Kruskal.kruskal(graph);
                        graphPanel.setMST(mst);
                        showMSTResult(mst);
                        break;
                }
                return null;
            }

            @Override
            protected void done() {
                graphPanel.repaint();
            }
        }.execute();
    }
    
    private void showMSTResult(List<Edge> mst) {
        if (mst.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No MST found!");
        } else {
            StringBuilder mstString = new StringBuilder("Minimum Spanning Tree:\n");
            for (Edge edge : mst) {
                mstString.append(edge.getSource().getId())
                         .append(" → ")
                         .append(edge.getTarget().getId())
                         .append(" (Weight: ")
                         .append(edge.getWeight())
                         .append(")\n");
            }
            JOptionPane.showMessageDialog(this, mstString.toString());
        }
    }

    private void showPathResult(List<Node> path) {
        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No path found!");
        } else {
            StringBuilder pathString = new StringBuilder("Path: ");
            for (Node node : path) {
                pathString.append(node.getId()).append(" → ");
            }
            pathString.setLength(pathString.length() - 3); // Remove last arrow
            JOptionPane.showMessageDialog(this, pathString.toString());
        }
    }

    private void showEdgeDialog() {
        List<Node> nodes = graph.getNodes();
        if (nodes.size() < 2) {
            JOptionPane.showMessageDialog(this, "Need at least 2 nodes to create edge!");
            return;
        }

        JComboBox<String> sourceCombo = new JComboBox<>();
        JComboBox<String> targetCombo = new JComboBox<>();
        for (Node node : nodes) {
            sourceCombo.addItem(node.getId());
            targetCombo.addItem(node.getId());
        }

        JTextField weightField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("From Node:"));
        panel.add(sourceCombo);
        panel.add(new JLabel("To Node:"));
        panel.add(targetCombo);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Create Edge", 
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            Node source = graph.getNodes().get(sourceCombo.getSelectedIndex());
            Node target = graph.getNodes().get(targetCombo.getSelectedIndex());
            createEdge(source, target);
        }
    }

    private void resetGraph() {
        graph = new Graph();
        startNode = null;
        endNode = null;
        placedNodes = 0;
        totalNodes = 0;
        graphPanel.setGraph(graph);
        graphPanel.repaint();
        askForNodeCount();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}