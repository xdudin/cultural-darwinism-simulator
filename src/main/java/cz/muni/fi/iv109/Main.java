package cz.muni.fi.iv109;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.core.util.Point;
import cz.muni.fi.iv109.gui.UIBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.UIManager;

@Slf4j
public class Main {

    private static final int NUMBER_OF_AGENTS = 10;

    private Main() {
        throw new AssertionError("Not initializable");
    }

    public static void main(String[] args) {

        SimulationParameters parameters = new SimulationParameters(
                0.1f,
                0f,
                10f,
                0.01f
        );

        Agent agent1 = new Agent(parameters, new Point(40, 50), -100);
        Agent agent2 = new Agent(parameters, new Point(60, 50), 100);

        Simulation simulation = new Simulation(parameters, agent1, agent2);
//        Simulation simulation = new Simulation(NUMBER_OF_AGENTS);

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
