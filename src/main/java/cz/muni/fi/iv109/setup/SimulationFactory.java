package cz.muni.fi.iv109.setup;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.PrngHolder;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.core.playground.Point;

import java.util.Random;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@SuppressWarnings("unused")
public class SimulationFactory {

    public static SimulationParameters analysisParameters(
            long seed,
            float assimilationFactor,
            float fertilityFactor
    ) {
        if (assimilationFactor < 1 || fertilityFactor < 1)
            throw new IllegalArgumentException("factors must be > 1");

        float k_fertilityMultiplier = 1.5f;

        return new SimulationParameters(
                new PrngHolder(seed),
                0.08f,
                5f,
                0.001f,
                assimilationFactor,
                k_fertilityMultiplier,
                k_fertilityMultiplier * fertilityFactor
        );
    }

    public static SimulationParameters referenceParameters() {
        return new SimulationParameters(
                new PrngHolder(new Random().nextLong()),
                0.08f,
                5f,
                0.001f,
                3f,
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
        Agent[] agents = agents(parameters, numberOfAgents, disposition);

        return new Simulation(parameters, agents);
    }

    public static Agent[] agents(
            SimulationParameters parameters,
            int numberOfAgents,
            Disposition disposition
    ) {
        return switch (disposition) {
            case RANDOM -> randomAgents(parameters, numberOfAgents);
            case HALF -> halfAgents(parameters, numberOfAgents);
            case CIRCLE -> circleAgents(parameters, numberOfAgents);
            case QUARTER -> quarterAgents(parameters, numberOfAgents);
            case FOUR_LINES -> fourLinesAgents(parameters, numberOfAgents);
            case CELLS -> cellsLinesAgents(parameters, numberOfAgents);
        };
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

    public static Agent[] quarterAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = randomAgents(parameters, numberOfAgents);

        for (Agent agent : agents) {

            float x = agent.getPosition().getX();
            float y = agent.getPosition().getY();

            agent.setCulture(
                    (x < PLAYGROUND_SIZE / 2f && y < PLAYGROUND_SIZE / 2f) ||
                    (x > PLAYGROUND_SIZE / 2f && y > PLAYGROUND_SIZE / 2f)
                            ? 100
                            : -100
            );
        }

        return agents;
    }

    public static Agent[] fourLinesAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = randomAgents(parameters, numberOfAgents);

        for (Agent agent : agents) {

            float x = agent.getPosition().getX();
            float y = agent.getPosition().getY();

            agent.setCulture(
                    x < PLAYGROUND_SIZE / 4f ||
                    (x > PLAYGROUND_SIZE / 4f * 2f && x < PLAYGROUND_SIZE / 4f * 3f)
                            ? -100
                            : 100
            );
        }

        return agents;
    }

    private static Agent[] cellsLinesAgents(SimulationParameters parameters, int numberOfAgents) {
        Agent[] agents = randomAgents(parameters, numberOfAgents);

        for (Agent agent : agents) {

            float x = agent.getPosition().getX();
            float y = agent.getPosition().getY();

            float cellSize = PLAYGROUND_SIZE / 4f;

            agent.setCulture(
                    (Math.floor(x / cellSize) % 2 == 0) && (Math.floor(y / cellSize) % 2 == 0) ||
                    (Math.floor(x / cellSize) % 2 == 1) && (Math.floor(y / cellSize) % 2 == 1)
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
