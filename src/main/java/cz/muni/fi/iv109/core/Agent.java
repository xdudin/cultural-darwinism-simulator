package cz.muni.fi.iv109.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Agent {

    private static final float ASSIMILATION_FACTOR = 0.01f;

    Point position;

    /**
     * number from -100 to 100
     */
    float culture;

    float direction = 0;
    short stepsRemaining = 0;
    final float distancePerStep;
    final float playgroundSize;

    public Agent(int playgroundSize) {
        this.playgroundSize = playgroundSize;

        int x = PrngHolder.randomInteger(0, playgroundSize);
        int y = PrngHolder.randomInteger(0, playgroundSize);
//        distancePerStep = playgroundSize / 500.0f;
        distancePerStep = 0;

        position = new Point(x, y);
        culture = PrngHolder.randomFloat(-100, 100);
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        float x = position.getX();
        float y = position.getY();

        float new_x = (float) (x + Math.cos(direction) * distancePerStep);
        float new_y = (float) (y + Math.sin(direction) * distancePerStep);

        if (new_x < 0) new_x += playgroundSize;
        if (new_x > playgroundSize) new_x -= playgroundSize;
        if (new_y < 0) new_y += playgroundSize;
        if (new_y > playgroundSize) new_y -= playgroundSize;

        position.setX(new_x);
        position.setY(new_y);

        stepsRemaining--;
    }

    public void receiveMessage(Point positionOfSender, float cultureOfSender) {
        culture += cultureOfSender * ASSIMILATION_FACTOR;
        if (culture < -100) culture = -100;
        if (culture > 100) culture = 100;

        shift(positionOfSender, cultureOfSender);
    }

    private void shift(Point positionOfSender, float cultureOfSender) {
        if (Math.signum(culture) == Math.signum(cultureOfSender)) {

        } else {

        }
    }

    private void resetTarget() {
        direction = PrngHolder.randomDirection();
        stepsRemaining = (short) PrngHolder.randomInteger(30, 60);
    }
}
