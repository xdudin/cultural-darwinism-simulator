package cz.muni.fi.iv109.core.util;


import lombok.Getter;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@Getter
public class Vector {

    private float x;
    private float y;

    public Vector (Point from, Point to) {
        float delta_x = to.getX() - from.getX();
        float delta_y = to.getY() - from.getY();

        if (Math.abs(delta_x) > PLAYGROUND_SIZE / 2.0f) delta_x -= PLAYGROUND_SIZE;
        if (Math.abs(delta_y) > PLAYGROUND_SIZE / 2.0f) delta_y -= PLAYGROUND_SIZE;

        this.x = delta_x;
        this.y = delta_y;
    }

    public float size() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector unify() {
        float s = size();
        x /= s;
        y /= s;

        return this;
    }

    public static float distance(Point from, Point to) {
        return new Vector(from, to).size();
    }
}
