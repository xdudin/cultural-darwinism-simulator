package cz.muni.fi.iv109.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@Data
@AllArgsConstructor
public class Point {

    private float x;
    private float y;

    /**
     * distance from other point with infinite field of the given size
     */
    public float distance(Point point) {
        float delta_x = Math.abs(this.x - point.x);
        float delta_y = Math.abs(this.y - point.y);

        if (delta_x > PLAYGROUND_SIZE / 2.0f) delta_x -= PLAYGROUND_SIZE;
        if (delta_y > PLAYGROUND_SIZE / 2.0f) delta_y -= PLAYGROUND_SIZE;

        return (float) Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
    }
}
