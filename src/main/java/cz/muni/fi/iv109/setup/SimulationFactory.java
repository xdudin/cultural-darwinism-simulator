package cz.muni.fi.iv109.setup;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.core.util.Point;

public class SimulationFactory {

    public SimulationParameters test() {
        return new SimulationParameters(
                0.08f,
                0f,
                5f,
                0.001f,
                3.3f,
                1.5f,
                4.5f
        );
    }

    public Simulation fourDiamond() {
        SimulationParameters parameters = test();

        Agent agent1 = new Agent(parameters, new Point(40, 50), 100, (short) 0);
        Agent agent2 = new Agent(parameters, new Point(60, 50), 100, (short) 40);
        Agent agent3 = new Agent(parameters, new Point(50, 60), -100, (short) 80);
        Agent agent4 = new Agent(parameters, new Point(50, 40), -100, (short) 120);

        return new Simulation(parameters, agent1, agent2, agent3, agent4);
    }

    public Simulation thousandRandom() {
        SimulationParameters parameters = test();

        return new Simulation(parameters, 1000);
    }
}
