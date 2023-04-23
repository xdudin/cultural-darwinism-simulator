package cz.muni.fi.iv109.setup;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.core.playground.Point;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@SuppressWarnings("unused")
public class SimulationFactory {

    public static SimulationParameters analysisParameters(
            float assimilationFactor,
            float fertilityMultiplier
    ) {
        float k_fertilityFactor = 1.5f;

        return new SimulationParameters(
                0.08f,
                5f,
                0.001f,
                assimilationFactor,
                k_fertilityFactor,
                k_fertilityFactor * fertilityMultiplier
        );
    }

    public static SimulationParameters referenceParameters() {
        return new SimulationParameters(
                0.08f,
                5f,
                0.001f,
                5.5f,
                1.5f,
                4.5f
        );
    }

    public static Simulation twoRow() {
        SimulationParameters parameters = referenceParameters();

        Agent agent1 = new Agent(parameters, new Point(50, 50), 100, 0);
        Agent agent2 = new Agent(parameters, new Point(38, 38), -100, 50);

        return new Simulation(parameters, agent1, agent2);
    }

    public static Simulation fourDiamond() {
        SimulationParameters parameters = referenceParameters();

        Agent agent1 = new Agent(parameters, new Point(40, 50), 100, 0);
        Agent agent2 = new Agent(parameters, new Point(60, 50), 100, 40);
        Agent agent3 = new Agent(parameters, new Point(50, 60), -100, 80);
        Agent agent4 = new Agent(parameters, new Point(50, 40), -100, 120);

        return new Simulation(parameters, agent1, agent2, agent3, agent4);
    }

    public static Simulation referenceSimulation(int numberOfAgents, Disposition disposition) {
        SimulationParameters parameters = referenceParameters();

        Agent[] agents = switch (disposition) {
            case RANDOM -> randomAgents(parameters, numberOfAgents);
            case HALF -> halfAgents(parameters, numberOfAgents);
            case CIRCLE -> circleAgents(parameters, numberOfAgents);
        };

        return new Simulation(parameters, agents);
    }

    public static Agent[] randomAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = new Agent[numberOfAgents];

        for (int i = 0; i < numberOfAgents; i++) {
            agents[i] = new Agent(parameters);
        }

        return agents;
    }

    public static Agent[] halfAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = randomAgents(parameters, numberOfAgents);

        for (Agent agent : agents) {
            agent.setCulture(
                    agent.getPosition().getX() < PLAYGROUND_SIZE / 2f
                            ? -100
                            : 100
            );
        }

        return agents;
    }

    public static Agent[] circleAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = randomAgents(parameters, numberOfAgents);

        Point center = new Point(PLAYGROUND_SIZE / 2f, PLAYGROUND_SIZE / 2f);
        float radius = 40f;

        for (Agent agent : agents) {
            agent.setCulture(
                    agent.getPosition().distance(center) < radius
                            ? -100
                            : 100
            );
        }

        return agents;
    }
}
