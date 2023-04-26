package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.setup.Disposition;
import cz.muni.fi.iv109.setup.SimulationFactory;

import java.util.Random;
import java.util.concurrent.Callable;

class SimulationTask implements Callable<ResultEntry> {

    private static final int NUMBER_OF_STEPS = 1500;

    private final long seed;
    private final float assimilationFactor;
    private final float fertilityMultiplier;
    private final int resultGrid_x;
    private final int resultGrid_y;

    private final Simulation simulation;

    public SimulationTask(
            float assimilationFactor,
            float fertilityMultiplier,
            int resultGrid_x,
            int resultGrid_y
    ) {
        this.seed = new Random().nextLong();

        this.assimilationFactor = assimilationFactor;
        this.fertilityMultiplier = fertilityMultiplier;
        this.resultGrid_x = resultGrid_x;
        this.resultGrid_y = resultGrid_y;

        this.simulation = init();
    }

    private Simulation init() {
        SimulationParameters parameters = SimulationFactory.analysisParameters(
                seed,
                assimilationFactor,
                fertilityMultiplier
        );
        Agent[] agents = SimulationFactory.agents(parameters, 1000, Disposition.RANDOM);
        return new Simulation(parameters, agents);
    }

    @Override
    public ResultEntry call() {
        while (simulation.getStepCounter() < NUMBER_OF_STEPS) {
            simulation.doStep();
        }

        float averageCulture = simulation.getAverageCulture();

        return new ResultEntry(
                seed,
                assimilationFactor,
                fertilityMultiplier,
                averageCulture,
                resultGrid_x,
                resultGrid_y
        );
    }
}
