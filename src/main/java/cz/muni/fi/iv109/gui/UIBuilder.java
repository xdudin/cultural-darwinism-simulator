package cz.muni.fi.iv109.gui;

import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.gui.control_panel.ControlPanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;

public class UIBuilder {

    public static MainWindow buildMainWindow(Simulation simulation) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int simulationPlaneSize = (int) (screenSize.getHeight() * 0.8);
        AtomicBoolean suspendFlag = new AtomicBoolean(true);

        SimulationPanel simulationPanel = new SimulationPanel(simulationPlaneSize, simulation, suspendFlag);
        ControlPanel controlPanel = new ControlPanel(simulationPlaneSize, suspendFlag);

        MainWindow mainWindow = new MainWindow(simulationPanel, controlPanel);

        Point cornerLocation = calculateCornerLocation(screenSize, mainWindow.getSize());
        mainWindow.setLocation(cornerLocation);

        return mainWindow;
    }

    private static Point calculateCornerLocation(Dimension screenSize, Dimension mainWindowSize) {
        int center_x = (int) (screenSize.getWidth() / 2);
        int center_y = (int) (screenSize.getHeight() / 2);

        int corner_x = center_x - ((int) mainWindowSize.getWidth() / 2);
        int corner_y = center_y - ((int) mainWindowSize.getHeight() / 2) - 20; // minus title bar height

        return new Point(corner_x, corner_y);
    }
}
