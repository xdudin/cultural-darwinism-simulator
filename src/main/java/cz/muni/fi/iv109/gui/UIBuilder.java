package cz.muni.fi.iv109.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class UIBuilder {

    public static MainWindow buildMainWindow(int numberOfAgents) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int simulationAreaSize = (int) (screenSize.getHeight() * 0.8);

        SimulationPanel simulationPanel = new SimulationPanel(simulationAreaSize, numberOfAgents);

        return new MainWindow(simulationPanel, calculateCornerLocation(screenSize, simulationAreaSize));
    }

    private static Point calculateCornerLocation(Dimension screenSize, int simulationAreaSize) {
        int center_x = (int) (screenSize.getWidth() / 2);
        int center_y = (int) (screenSize.getHeight() / 2);

        int corner_x = center_x - (simulationAreaSize / 2);
        int corner_y = center_y - (simulationAreaSize / 2) - 20; // munis title bar height

        return new Point(corner_x, corner_y);
    }
}
