package cz.muni.fi.iv109.setup;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.core.playground.Point;

@SuppressWarnings("unused")
public class SimulationFactory {

    public static SimulationParameters testParameters() {
        return new SimulationParameters(
                0.08f,
                5f,
                0.001f,
                3f,
                1.5f,
                4.5f
        );
    }

    public static Simulation twoRow() {
        SimulationParameters parameters = testParameters();

        Agent agent1 = new Agent(parameters, new Point(50, 50), 100, 0);
        Agent agent2 = new Agent(parameters, new Point(38, 38), -100, 50);

        return new Simulation(parameters, agent1, agent2);
    }

    public static Simulation fourDiamond() {
        SimulationParameters parameters = testParameters();

        Agent agent1 = new Agent(parameters, new Point(40, 50), 100, 0);
        Agent agent2 = new Agent(parameters, new Point(60, 50), 100, 40);
        Agent agent3 = new Agent(parameters, new Point(50, 60), -100, 80);
        Agent agent4 = new Agent(parameters, new Point(50, 40), -100, 120);

        return new Simulation(parameters, agent1, agent2, agent3, agent4);
    }

    public static Simulation thousandRandom() {
        SimulationParameters parameters = testParameters();

        return new Simulation(parameters, 1000);
    }

    public static Simulation hundredRandom() {
        SimulationParameters parameters = testParameters();

        return new Simulation(parameters, 100);
    }
}
