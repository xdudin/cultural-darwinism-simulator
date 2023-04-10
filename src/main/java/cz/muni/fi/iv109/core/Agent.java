package cz.muni.fi.iv109.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Agent {

    Point position;

    /**
     * number from -100 to 100
     */
    byte culture;

    float direction = 0;
    short stepsRemaining = 0;
    final float distancePerStep;

    public Agent(int playgroundSize) {
        int x = PrngHolder.randomInteger(0, playgroundSize);
        int y = PrngHolder.randomInteger(0, playgroundSize);
        distancePerStep = playgroundSize / 100.0f;

        position = new Point(x, y);
        culture = PrngHolder.randomByte(-100, 100);
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        float x = position.getX();
        float y = position.getY();

        position.setX((float) (x + Math.cos(direction) * distancePerStep));
        position.setY((float) (y + Math.sin(direction) * distancePerStep));

        stepsRemaining--;
    }

    private void resetTarget() {
        direction = PrngHolder.randomDirection();
        stepsRemaining = (short) PrngHolder.randomInteger(3, 6);
    }
}
