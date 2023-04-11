package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.util.Vector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Getter
public class Simulation {

    public static final float PLAYGROUND_SIZE = 100f;
    private static final float COMMUNICATION_RADIUS = 2f;

    private final List<Agent> agents = new ArrayList<>();
    private int stepCounter = 0;

    public Simulation(int numberOfAgents) {
        for (int i = 0; i < numberOfAgents; i++) {
            agents.add(new Agent());
        }
    }

    public Simulation(Agent... agents) {
        this.agents.addAll(Arrays.asList(agents));
    }

    public void doStep() {
        agents.forEach(Agent::move);

        // Due to O(n) neighbor lookup, overall complexity is O(n^2), this can be
        // improved if agents will be stored in a QuadTree. TODO: use QuadTree
        for (Agent agent : agents) {
            for (Agent candidate : agents) {
                if (agent != candidate &&
                    Vector.distance(candidate.getPosition(), agent.getPosition()) < COMMUNICATION_RADIUS)
                {
                    candidate.receiveMessage(agent.getPosition(), agent.getCulture());
                }
            }
        }

//        System.out.print(agents.get(0));
//        System.out.print("   ");
//        System.out.println(agents.get(1));

        stepCounter++;
    }

    public int getNumberOfAgents() {
        return agents.size();
    }
}
