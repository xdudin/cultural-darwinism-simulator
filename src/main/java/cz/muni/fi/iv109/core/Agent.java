package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.util.Point;
import cz.muni.fi.iv109.core.util.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;
import static cz.muni.fi.iv109.core.Simulation.TOTAL_STEPS_OF_LIFE;

@Getter
@AllArgsConstructor
public class Agent {

    public static final float MAX_CHILDREN = 7f;

    private Point position;
    private float culture; // number from -100 to 100
    private float direction;
    private short stepsRemaining = 0;
    private short age;
    private short[] childrenDecisionCheckpoint;
    private byte numberOfChildren;
    private final SimulationParameters parameters;

    public Agent(SimulationParameters parameters, Point position, float culture, short age) {
        if (culture < -100 || culture > 100)
            throw new IllegalArgumentException("not within [-100, 100]");

        this.parameters = parameters;
        this.position = position;
        this.culture = culture;
        this.age = age;
        this.childrenDecisionCheckpoint = computeChildrenDecisionCheckpoints();
    }

    public Agent(SimulationParameters parameters) {
        float x = PrngHolder.randomCoordinate();
        float y = PrngHolder.randomCoordinate();

        this.parameters = parameters;
        this.position = new Point(x, y);
        this.culture = PrngHolder.randomCulture();
        this.age = PrngHolder.randomAge();
        this.childrenDecisionCheckpoint = computeChildrenDecisionCheckpoints();
    }

    public void reborn(float x, float y, float culture, short age) {
        this.culture = culture;
        this.age = age;
        position.setX(x);
        position.setY(y);
        stepsRemaining = 0;
        childrenDecisionCheckpoint = computeChildrenDecisionCheckpoints();
        numberOfChildren = 0;
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        move((float) (Math.cos(direction) * parameters.distancePerStep()),
             (float) (Math.sin(direction) * parameters.distancePerStep()));

        stepsRemaining--;
    }

    public void receiveMessage(Point positionOfSender, float cultureOfSender) {
        culture += cultureOfSender * parameters.messageFactor();
//        float mid = (culture + cultureOfSender) / 2;
//        culture += (mid - culture) * parameters.messageFactor();
        if (culture < -100) culture = -100;
        if (culture > 100) culture = 100;

        // shift(positionOfSender, cultureOfSender); // TODO: fine-tune shift parameters
    }

    public void increaseAge() {
        age++;
    }

    public boolean makeChildrenDecision() {
        if (age < TOTAL_STEPS_OF_LIFE / 3 || age > TOTAL_STEPS_OF_LIFE / 3 * 2)
            return false;

        for (short checkpoint : childrenDecisionCheckpoint) {
            if (age == checkpoint) {
                if (culture > 0) { // k-branch
                    return PrngHolder.randomFloat(0, 1) <
                            parameters.k_childrenPerFamily() / childrenDecisionCheckpoint.length;
                }
                else { // r-branch
                    return PrngHolder.randomFloat(0, 1) <
                            parameters.r_childrenPerFamily() / childrenDecisionCheckpoint.length;
                }
            }
        }

        return false;
    }

    private void shift(Point positionOfSender, float cultureOfSender) {
        int multiplier = Math.signum(culture) == Math.signum(cultureOfSender) ? 1 : -1;
        Vector vector = new Vector(position, positionOfSender);
        float size = vector.size();
        if (size < 10f || Math.abs(size - 50f) < 0.1f) return;

        vector.unify();
        move(vector.getX() * parameters.shiftOnMessage() * multiplier,
             vector.getY() * parameters.shiftOnMessage() * multiplier);
    }

    private void move(float dx, float dy) {
        float x = position.getX();
        float y = position.getY();

        float new_x = x + dx;
        float new_y = y + dy;

        if (new_x < 0) new_x += PLAYGROUND_SIZE;
        if (new_x > PLAYGROUND_SIZE) new_x -= PLAYGROUND_SIZE;
        if (new_y < 0) new_y += PLAYGROUND_SIZE;
        if (new_y > PLAYGROUND_SIZE) new_y -= PLAYGROUND_SIZE;

        position.setX(new_x);
        position.setY(new_y);
    }

    private void resetTarget() {
        direction = PrngHolder.randomDirection();
        stepsRemaining = (short) PrngHolder.randomInteger(100, 200);
    }

    private short[] computeChildrenDecisionCheckpoints() {
        short[] childrenCheckpoints = new short[Math.round(MAX_CHILDREN)];

        short counter = TOTAL_STEPS_OF_LIFE / 3;
        float interval = counter / MAX_CHILDREN;
        for (int i = 0; i < childrenCheckpoints.length; i++) {
            childrenCheckpoints[i] = counter;
            counter += interval;
        }

        return childrenCheckpoints;
    }

    @Override
    public String toString() {
        return position.getX() + "," + position.getY() + "," + culture;
    }
}
