package cz.muni.fi.iv109.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PointTest {

    @Test
    void normalDistance() {
        float distance = new Point(1, 2).distance(new Point(3, 4));

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }

    @Test
    void distanceOverBorder() {
        float distance = new Point(1, 1).distance(new Point(99, 99));

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }
}
