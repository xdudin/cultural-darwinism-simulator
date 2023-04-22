package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.setup.SimulationFactory;

public class AnalyseStarter {

    public static void main(String[] args) {

        Simulation simulation = SimulationFactory.thousandRandom();

        // 1500 iteration - O(n^2) - 6300 ms
        SimulationRunner simulationRunner = new SimulationRunner(simulation, 1500);

        long start;
        start = System.currentTimeMillis();
        simulation.setStepCounter(0);
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        simulation.setStepCounter(0);
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        simulation.setStepCounter(0);
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);
    }
}
