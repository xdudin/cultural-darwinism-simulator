package cz.muni.fi.iv109.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Point;

public class MainWindow extends JFrame {

    private final SimulationPanel simulationPanel;

    public MainWindow(SimulationPanel simulationPanel, Point cornerLocation) {
        this.simulationPanel = simulationPanel;

        this.setTitle("Cultural darwinism simulator");

        this.setLocation(cornerLocation);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.add(simulationPanel);

        this.pack();
        this.setVisible(true);
    }

    public void startSimulation() {
        simulationPanel.startSimulationThread();
    }
}
