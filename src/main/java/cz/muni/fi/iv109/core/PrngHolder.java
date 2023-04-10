package cz.muni.fi.iv109.core;

import java.util.Random;

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

    /**
     * random direction within [0, 2pi] radians
     */
    public static float randomDirection() {
        return randomFloat(0, (float) (Math.PI * 2));
    }
}
