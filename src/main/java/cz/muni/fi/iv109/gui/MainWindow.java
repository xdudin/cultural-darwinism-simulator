package cz.muni.fi.iv109.gui;

import cz.muni.fi.iv109.gui.control_panel.ControlPanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {

    private final SimulationPanel simulationPanel;
    private final ControlPanel controlPanel;

    public MainWindow(
            SimulationPanel simulationPanel,
            ControlPanel controlPanel
    ) {
        this.simulationPanel = simulationPanel;
        this.controlPanel = controlPanel;

        this.setTitle("Cultural darwinism simulator");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        this.add(simulationPanel);
        this.add(controlPanel);

        this.pack();
        this.setVisible(true);
    }

    public void startSimulation() {
        simulationPanel.startSimulationThread();
    }
}
