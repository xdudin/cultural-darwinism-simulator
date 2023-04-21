package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.setup.SimulationFactory;

public class AnalyseStarter {

    public static void main(String[] args) {

        SimulationFactory simulationFactory = new SimulationFactory();
        Simulation simulation = simulationFactory.fourDiamond();

        SimulationRunner simulationRunner = new SimulationRunner(simulation, 30000);

        long start = System.currentTimeMillis();
        simulationRunner.run();
        System.out.println(System.currentTimeMillis() - start);
    }
}