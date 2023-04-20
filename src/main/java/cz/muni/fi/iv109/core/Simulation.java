package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.util.Vector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Simulation {

    public static final float PLAYGROUND_SIZE = 100f;
    public static final short TOTAL_STEPS_OF_LIFE = 300;

    private final Agent[] agents;
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
                    Vector.distance(candidate.getPosition(), agent.getPosition()) < parameters.communicationRadius())
                {
                    candidate.receiveMessage(agent.getPosition(), agent.getCulture());
                }
            }
        }

        for (Agent agent : agents) {
            agent.increaseAge();

            short age = agent.getAge();
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
                        (short) 0
                );
            }
        }

//        System.out.print(agents[0]);
//        System.out.print("   ");
//        System.out.println(agents[1]);

        stepCounter++;
    }
}
