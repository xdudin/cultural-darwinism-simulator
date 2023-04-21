package cz.muni.fi.iv109.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PointTest {

    @Test
    void normalDistance() {
        Point A = new Point(1, 2);
        Point B = new Point(3, 4);

        float distance = A.distance(B);

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }

    @Test
    void distanceOverBorder() {
        Point A = new Point(1, 1);
        Point B = new Point(99, 99);

        float distance = A.distance(B);

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }
}
