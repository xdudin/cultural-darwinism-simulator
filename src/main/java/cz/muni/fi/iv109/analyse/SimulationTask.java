package cz.muni.fi.iv109.analyse;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.setup.Disposition;
import cz.muni.fi.iv109.setup.SimulationFactory;

import java.util.Random;
import java.util.concurrent.Callable;

class SimulationTask implements Callable<ResultEntry[]> {

    public static final int NUMBER_OF_ROUNDS = 4;
    private static final int NUMBER_OF_STEPS = 1500;
    private static final int NUMBER_OF_AGENTS = 1000;

    private final Disposition disposition;
    private final float assimilationFactor;
    private final float fertilityFactor;
    private final int assimilationFactor_x;
    private final int fertilityFactor_y;

    public SimulationTask(
            Disposition disposition,
            float assimilationFactor,
            float fertilityFactor,
            int assimilationFactor_x,
            int fertilityFactor_y
    ) {
        this.disposition = disposition;
        this.assimilationFactor = assimilationFactor;
        this.fertilityFactor = fertilityFactor;
        this.assimilationFactor_x = assimilationFactor_x;
        this.fertilityFactor_y = fertilityFactor_y;
    }

    @Override
    public ResultEntry[] call() {
        Random random = new Random();
        ResultEntry[] results = new ResultEntry[NUMBER_OF_ROUNDS];

        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            long seed = random.nextLong();
            results[i] = simulateOneRound(seed);
        }

        return results;
    }

    private ResultEntry simulateOneRound(long seed) {
        Simulation simulation = simulation(seed);

        while (simulation.getStepCounter() < NUMBER_OF_STEPS) {
            simulation.doStep();
        }

        float averageCulture = simulation.getAverageCulture();

        return new ResultEntry(
                seed,
                assimilationFactor,
                fertilityFactor,
                averageCulture,
                assimilationFactor_x,
                fertilityFactor_y
        );
    }

    private Simulation simulation(long seed) {
        SimulationParameters parameters = SimulationFactory.analysisParameters(
                seed,
                assimilationFactor,
                fertilityFactor
        );
        Agent[] agents = SimulationFactory.agents(parameters, NUMBER_OF_AGENTS, disposition);
        return new Simulation(parameters, agents);
    }
}
