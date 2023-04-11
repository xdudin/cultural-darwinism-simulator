package cz.muni.fi.iv109.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@Getter
@AllArgsConstructor
public class Agent {

    private static final float ASSIMILATION_FACTOR = 0.01f;
    private static final float DISTANCE_PER_STEP = 0.5f;

    private Point position;

    /**
     * number from -100 to 100
     */
    private float culture;

    private float direction;
    private short stepsRemaining = 0;

    public Agent() {
        float x = PrngHolder.randomFloat(0f, PLAYGROUND_SIZE);
        float y = PrngHolder.randomFloat(0f, PLAYGROUND_SIZE);

        position = new Point(x, y);
        culture = PrngHolder.randomFloat(-100f, 100f);
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        float x = position.getX();
        float y = position.getY();

        float new_x = (float) (x + Math.cos(direction) * DISTANCE_PER_STEP);
        float new_y = (float) (y + Math.sin(direction) * DISTANCE_PER_STEP);

        if (new_x < 0) new_x += PLAYGROUND_SIZE;
        if (new_x > PLAYGROUND_SIZE) new_x -= PLAYGROUND_SIZE;
        if (new_y < 0) new_y += PLAYGROUND_SIZE;
        if (new_y > PLAYGROUND_SIZE) new_y -= PLAYGROUND_SIZE;

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

    @Override
    public String toString() {
        return position.getX() + "," + position.getY() + "," + culture;
    }
}
