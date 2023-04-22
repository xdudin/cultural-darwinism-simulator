package cz.muni.fi.iv109.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Simulation {

    public static final float PLAYGROUND_SIZE = 100f;
    public static final int TOTAL_STEPS_OF_LIFE = 300;

    private final Agent[] agents;
    @Setter
    private int stepCounter = 0;
    private final SimulationParameters parameters;

    public Simulation(SimulationParameters parameters, int numberOfAgents) {
        this.parameters = parameters;
        agents = new Agent[numberOfAgents];

        for (int i = 0; i < numberOfAgents; i++) {
            agents[i] = new Agent(parameters);
        }
    }

    public Simulation(SimulationParameters parameters, Agent... agents) {
        this.parameters = parameters;
        this.agents = agents;
    }

    public void doStep() {
        for (Agent value : agents) value.move();

        // Due to O(n) neighbor lookup, overall complexity is O(n^2), this can be
        // improved if agents will be stored in a QuadTree. TODO: use QuadTree
        for (Agent agent : agents) {
            for (Agent candidate : agents) {
                if (agent != candidate &&
                    candidate.getPosition().distance(agent.getPosition()) < parameters.communicationRadius())
                {
                    candidate.receiveMessage(agent.getCulture());
                }
            }
        }

        for (Agent agent : agents) {
            agent.increaseAge();

            int age = agent.getAge();
            if (age >= TOTAL_STEPS_OF_LIFE) {
                agent.reborn(
                        PrngHolder.randomCoordinate(),
                        PrngHolder.randomCoordinate(),
                        PrngHolder.randomCulture(),
                        PrngHolder.randomAge()
                );
                continue;
            }

            if (agent.makeChildrenDecision()) {
                int toKillIndex = PrngHolder.randomInteger(0, agents.length - 1);

                agents[toKillIndex].reborn(
                        (float) Math.cos(PrngHolder.randomDirection()) + agent.getPosition().getX(),
                        (float) Math.sin(PrngHolder.randomDirection()) + agent.getPosition().getY(),
                        agent.getCulture(),
                        0
                );
            }
        }

        stepCounter++;
    }
}
