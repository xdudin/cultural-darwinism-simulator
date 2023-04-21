package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.util.Point;
import cz.muni.fi.iv109.setup.SimulationFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static cz.muni.fi.iv109.core.Simulation.TOTAL_STEPS_OF_LIFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class AgentTest {

    @ParameterizedTest
    @CsvSource({"50,1.5", "-50,4.5"})
    void childCreationTest(float culture, float expectedMean) {
        int numberOfExperiments = 10000;

        SimulationParameters parameters = SimulationFactory.testParameters();
        Agent agent;
        int[] actualNumberOfChildren = new int[numberOfExperiments];

        for (int e = 0; e < numberOfExperiments; e++) {
            agent = new Agent(parameters, new Point(0, 0), culture, (short) 0);

            int numberOfChildren = 0;
            for (int i = 0; i < Simulation.TOTAL_STEPS_OF_LIFE; i++) {
                agent.increaseAge();
                if (agent.makeChildrenDecision()) {
                    numberOfChildren++;
                }
            }

            actualNumberOfChildren[e] = numberOfChildren;
        }

        float mean = (float) Arrays.stream(actualNumberOfChildren).summaryStatistics().getAverage();
        float std = standardDeviation(actualNumberOfChildren);

        System.out.println("mean=" + mean);
        System.out.println("std=" + std);

        assertThat(mean).isCloseTo(expectedMean, within(0.05f));
    }

    @Test
    @Disabled("for manual use")
    void computeChildrenDecisionCheckpoints() {
        int maxChildren = 7;

        short[] childrenCheckpoints = new short[maxChildren];

        short counter = TOTAL_STEPS_OF_LIFE / 3;
        float interval = (float) counter / maxChildren;
        for (int i = 0; i < childrenCheckpoints.length; i++) {
            childrenCheckpoints[i] = counter;
            counter += interval;
        }

        System.out.println(Arrays.toString(childrenCheckpoints));
    }

    private static float standardDeviation(int[] array) {

        float sum = Arrays.stream(array).sum();
        double mean = sum / array.length;

        double standardDeviation = 0.0;
        for (int num : array) {
            standardDeviation += (num - mean) * (num - mean);
        }

        return (float) Math.sqrt(standardDeviation / array.length);
    }
}
