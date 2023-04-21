package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Simulation;

public class SimulationRunner implements Runnable {

    private final Simulation simulation;
    private final int numberOfSteps;

    public SimulationRunner(Simulation simulation, int numberOfSteps) {
        this.simulation = simulation;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (simulation.getStepCounter() < numberOfSteps) {
            simulation.doStep();
        }
    }
}
