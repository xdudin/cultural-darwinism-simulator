package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.setup.SimulationFactory;

public class AnalyseStarter {

    public static void main(String[] args) {

        Simulation simulation = SimulationFactory.thousandRandom();

        SimulationRunner simulationRunner = new SimulationRunner(simulation, 1500);

        long start;
        start = System.currentTimeMillis();
        simulation.setStepCounter(0);
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);
    }
}
