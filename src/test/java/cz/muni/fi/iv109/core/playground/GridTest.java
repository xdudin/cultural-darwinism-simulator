package cz.muni.fi.iv109.core.playground;

import cz.muni.fi.iv109.core.Agent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class GridTest {

    @Test
    void createWithExceptionTest() {
        assertThatThrownBy(() -> new Grid(6, new Agent[0]))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createNormalTest() {
        assertThatNoException().isThrownBy(() -> new Grid(5, new Agent[0]));
    }
}
