package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.playground.Grid;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Simulation {

    public static final int PLAYGROUND_SIZE = 100;
    public static final int TOTAL_STEPS_OF_LIFE = 300;

    private final Grid grid;
    @Setter
    private int stepCounter = 0;
    private final SimulationParameters parameters;
    private final PrngHolder prngHolder;

    public Simulation(SimulationParameters parameters, Agent... agents) {
        this.prngHolder = parameters.prngHolder();
        this.parameters = parameters;
        this.grid = new Grid(parameters.communicationRadius(), agents);
    }

    public void doStep() {
        for (Agent value : grid.getAgents()) value.move();

        for (Agent agent: grid.getAgents()) {
            grid.applyWithinCommunicationRadius(
                    agent,
                    candidate -> candidate.receiveMessage(agent.getCulture())
            );
        }

        for (Agent agent : grid.getAgents()) {
            agent.increaseAge();

            int age = agent.getAge();
            if (age >= TOTAL_STEPS_OF_LIFE) {
                agent.reborn(
                        prngHolder.randomCoordinate(),
                        prngHolder.randomCoordinate(),
                        prngHolder.randomCulture(),
                        prngHolder.randomAge()
                );
                continue;
            }

            if (agent.makeChildrenDecision()) {
                int toKillIndex = prngHolder.randomInteger(0, grid.getAgents().length - 1);

                grid.getAgents()[toKillIndex].reborn(
                        (float) Math.cos(prngHolder.randomDirection()) + agent.getPosition().getX(),
                        (float) Math.sin(prngHolder.randomDirection()) + agent.getPosition().getY(),
                        agent.getCulture(),
                        0
                );
            }
        }

        stepCounter++;
    }

    public float getAverageCulture() {
        float total = 0;

        for (Agent agent : grid.getAgents()) {
            total += agent.getCulture();
        }

        return total / grid.getAgents().length;
    }
}
