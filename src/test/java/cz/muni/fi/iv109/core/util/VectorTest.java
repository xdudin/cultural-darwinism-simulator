package cz.muni.fi.iv109.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VectorTest {

    @Test
    void creation() {
        Vector vector = new Vector(new Point(40, 50), new Point(60, 50));

        assertThat(vector.getX()).isCloseTo(20, within(0.001f));
        assertThat(vector.getY()).isCloseTo(0, within(0.001f));
    }

    @Test
    void creation2() {
        Vector vector = new Vector(new Point(41, 50), new Point(99, 50));

        assertThat(vector.getX()).isCloseTo(58, within(0.001f));
        assertThat(vector.getY()).isCloseTo(0, within(0.001f));
    }

    @Test
    void creationOverBoard() {
        Vector vector = new Vector(new Point(10, 50), new Point(90, 50));

        assertThat(vector.getX()).isCloseTo(-20, within(0.001f));
        assertThat(vector.getY()).isCloseTo(0, within(0.001f));
    }

    @Test
    void normalDistance() {
        float distance = Vector.distance(new Point(1, 2), new Point(3, 4));

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }

    @Test
    void distanceOverBorder() {
        float distance = Vector.distance(new Point(1, 1), new Point(99, 99));

        assertThat(distance).isCloseTo(2.828f, within(0.001f));
    }

    @Test
    void size() {
        float size = new Vector(new Point(0, 0), new Point(1, 1)).size();

        assertThat(size).isCloseTo(1.414f, within(0.001f));
    }

    @Test
    void normalUnify() {
        Vector vector = new Vector(new Point(0, 0), new Point(1, 2)).unify();

        assertThat(vector.size()).isCloseTo(1f, within(0.001f));
        assertThat(vector.getX()).isCloseTo(0.447f, within(0.001f));
        assertThat(vector.getY()).isCloseTo(0.894f, within(0.001f));
    }

    @Test
    void unifyOverBorder() {
        Vector vector = new Vector(new Point(1, 1), new Point(98, 99)).unify();

        assertThat(vector.size()).isCloseTo(1f, within(0.001f));
        assertThat(vector.getX()).isCloseTo(-0.832f, within(0.001f));
        assertThat(vector.getY()).isCloseTo(-0.554f, within(0.001f));
    }
}
