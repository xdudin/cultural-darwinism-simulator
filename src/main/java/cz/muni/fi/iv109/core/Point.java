package cz.muni.fi.iv109.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {

    private float x;
    private float y;

    /**
     * distance from other point with infinite field of the given size
     */
    public float distance(Point point, int playgroundSize) {
        float delta_x = Math.abs(this.x - point.x);
        float delta_y = Math.abs(this.y - point.y);

        if (delta_x > playgroundSize / 2.0f) delta_x -= playgroundSize;
        if (delta_y > playgroundSize / 2.0f) delta_y -= playgroundSize;

        return (float) Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
    }
}
