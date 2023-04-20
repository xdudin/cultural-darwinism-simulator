package cz.muni.fi.iv109.gui;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.setup.SimulationFactory;
import lombok.extern.slf4j.Slf4j;

import javax.swing.UIManager;

@Slf4j
public class Main {

    private static final int NUMBER_OF_AGENTS = 10;

    public static void main(String[] args) {

        SimulationFactory simulationFactory = new SimulationFactory();
        Simulation simulation = simulationFactory.fourDiamond();

        initNimbusLookAndFeel();
        UIBuilder.buildMainWindow(simulation).startSimulation();
    }

    private static void initNimbusLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            log.error("LookAndFeel initialization failed", ex);
        }
    }
}
