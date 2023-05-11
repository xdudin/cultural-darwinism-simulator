package cz.muni.fi.iv109.core;

import java.util.Random;

@SuppressWarnings("unused")
public class PrngHolder {

    private final Random random;

    public PrngHolder (long seed) {
        random = new Random(seed);
    }

    /**
     * return next random integer within the interval [from, to]
     */
    public int randomInteger(int from, int to) {
        return random.nextInt(from, to + 1);
    }

    public float randomFloat(float from, float to) {
        return random.nextFloat(from, to);
    }

    public float randomCoordinate() {
        return randomFloat(0f, Simulation.PLAYGROUND_SIZE);
    }

    public float randomCulture() {
        return randomFloat(-100, 100);
    }

    public int randomAge() {
        return randomInteger(0, Simulation.TOTAL_STEPS_OF_LIFE);
    }

    /**
     * random direction within [0, 2pi] radians
     */
    public float randomDirection() {
        return randomFloat(0, (float) (Math.PI * 2));
    }

    public long gaussianLong(double mean, double stddev) {
        return Math.round(random.nextGaussian(mean, stddev));
    }
}
