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
    final List<Agent> agents = new ArrayList<>();
    int stepCounter = 0;

    public Simulation(int playgroundSize, int numberOfAgents) {
        this.playgroundSize = playgroundSize;

        for (int i = 0; i < numberOfAgents; i++) {
            Agent agent = new Agent(playgroundSize);

            agents.add(agent);
        }
    }

    public void doStep() {
        agents.forEach(Agent::move);

        stepCounter++;
    }

    public int getNumberOfAgents() {
        return agents.size();
    }
}
