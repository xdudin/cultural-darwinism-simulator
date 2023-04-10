package cz.muni.fi.iv109.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Simulation {

    final int playgroundSize;
    final int communicationRadius;
    final List<Agent> agents = new ArrayList<>();
    int stepCounter = 0;

    public Simulation(int playgroundSize, int numberOfAgents) {
        this.playgroundSize = playgroundSize;
        this.communicationRadius = playgroundSize / 1;

        for (int i = 0; i < numberOfAgents; i++) {
            agents.add(new Agent(playgroundSize));
        }
    }

    public void doStep() {
        agents.forEach(Agent::move);

        // Due to O(n) neighbor lookup, overall complexity is O(n^2), this can be
        // improved if agents will be stored in a QuadTree. TODO: use QuadTree
        for (Agent agent : agents) {
            for (Agent candidate : agents) {
                if (agent != candidate &&
                    candidate.getPosition().distance(agent.getPosition(), playgroundSize) < communicationRadius)
                {
                    candidate.receiveMessage(agent.getPosition(), agent.getCulture());
                }
            }
        }

        System.out.println(agents.get(0).getCulture() + " " + agents.get(1).getCulture());

        stepCounter++;
    }

    public int getNumberOfAgents() {
        return agents.size();
    }
}
