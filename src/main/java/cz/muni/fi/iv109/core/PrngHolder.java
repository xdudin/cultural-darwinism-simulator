package cz.muni.fi.iv109.core;

import java.util.Random;

@SuppressWarnings("unused")
public class PrngHolder {

    private static final Random random = new Random(1000);

    /**
     * return next random integer within the interval [from, to]
     */
    public static int randomInteger(int from, int to) {
        return random.nextInt(from, to + 1);
    }

    public static byte randomByte(int from, int to) {
        if (from < -128 || to > 127) throw new IllegalArgumentException("not in byte range");
        return (byte) randomInteger(from, to);
    }

    public static float randomFloat(float from, float to) {
        return random.nextFloat(from, to);
    }

    public static float randomCoordinate() {
        return randomFloat(0f, Simulation.PLAYGROUND_SIZE);
    }

    public static float randomCulture() {
        return randomFloat(-100, 100);
    }

    public static short randomAge() {
        return (short) randomInteger(0, Simulation.TOTAL_STEPS_OF_LIFE);
    }

    /**
     * random direction within [0, 2pi] radians
     */
    public static float randomDirection() {
        return randomFloat(0, (float) (Math.PI * 2));
    }

    public static long gaussianLong(double mean, double stddev) {
        return Math.round(random.nextGaussian(mean, stddev));
    }
}
