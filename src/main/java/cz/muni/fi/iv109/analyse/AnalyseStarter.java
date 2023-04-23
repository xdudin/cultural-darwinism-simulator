package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.setup.Disposition;
import cz.muni.fi.iv109.setup.SimulationFactory;

public class AnalyseStarter {

    public static void main(String[] args) {

        Simulation simulation;
        SimulationRunner simulationRunner;
        long start;

        simulation = SimulationFactory.referenceSimulation(1000, Disposition.RANDOM);
        simulationRunner = new SimulationRunner(simulation, 1500);
        start = System.currentTimeMillis();
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);

        simulation = SimulationFactory.referenceSimulation(1000, Disposition.RANDOM);
        simulationRunner = new SimulationRunner(simulation, 1500);
        start = System.currentTimeMillis();
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);

        simulation = SimulationFactory.referenceSimulation(1000, Disposition.RANDOM);
        simulationRunner = new SimulationRunner(simulation, 1500);
        start = System.currentTimeMillis();
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);

        simulation = SimulationFactory.referenceSimulation(1000, Disposition.RANDOM);
        simulationRunner = new SimulationRunner(simulation, 1500);
        start = System.currentTimeMillis();
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);
    }
}
