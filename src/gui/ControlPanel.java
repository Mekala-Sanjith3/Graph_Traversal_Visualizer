package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JComboBox<String> algorithmCombo;
    private JButton startButton, resetButton, addEdgeButton;

    public ControlPanel(ActionListener listener) {
        setLayout(new GridLayout(0, 1));
        algorithmCombo = new JComboBox<>(new String[]{"BFS", "DFS", "Dijkstra", "Kruskal"});
        startButton = new JButton("Start");
        resetButton = new JButton("Reset");
        addEdgeButton = new JButton("Add Edge");

        startButton.setActionCommand("Start");
        resetButton.setActionCommand("Reset");
        addEdgeButton.setActionCommand("AddEdge");

        startButton.addActionListener(listener);
        resetButton.addActionListener(listener);
        addEdgeButton.addActionListener(listener);

        add(new JLabel("Algorithm:"));
        add(algorithmCombo);
        add(startButton);
        add(resetButton);
        add(addEdgeButton);
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmCombo.getSelectedItem();
    }
}