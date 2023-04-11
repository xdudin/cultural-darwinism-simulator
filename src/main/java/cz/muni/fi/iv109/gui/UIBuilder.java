package cz.muni.fi.iv109.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class UIBuilder {

    public static MainWindow buildMainWindow(int numberOfAgents) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int simulationPlaneSize = (int) (screenSize.getHeight() * 0.8);

        SimulationPanel simulationPanel = new SimulationPanel(simulationPlaneSize, numberOfAgents);

        return new MainWindow(simulationPanel, calculateCornerLocation(screenSize, simulationPlaneSize));
    }

    private static Point calculateCornerLocation(Dimension screenSize, int simulationPlaneSize) {
        int center_x = (int) (screenSize.getWidth() / 2);
        int center_y = (int) (screenSize.getHeight() / 2);

        int corner_x = center_x - (simulationPlaneSize / 2);
        int corner_y = center_y - (simulationPlaneSize / 2) - 20; // munis title bar height

        return new Point(corner_x, corner_y);
    }
}
