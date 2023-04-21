package cz.muni.fi.iv109.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@Data
@AllArgsConstructor
public class Point {

    private float x;
    private float y;

    public float distance(Point point) {
        float delta_x = Math.abs(this.x - point.x);
        float delta_y = Math.abs(this.y - point.y);

        if (delta_x > PLAYGROUND_SIZE / 2.0f) delta_x -= PLAYGROUND_SIZE;
        if (delta_y > PLAYGROUND_SIZE / 2.0f) delta_y -= PLAYGROUND_SIZE;

        return (float) Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }
}
