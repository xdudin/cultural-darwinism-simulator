package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.playground.Point;
import cz.muni.fi.iv109.core.playground.PositionUpdatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;
import static cz.muni.fi.iv109.core.Simulation.TOTAL_STEPS_OF_LIFE;

@Getter @Setter
@AllArgsConstructor
public class Agent extends PositionUpdatable {

    public static final float MAX_CHILDREN = 7f;
    public static final int[] CHILDREN_CHECKPOINTS = {100, 114, 128, 142, 156, 170, 184};

    private Point position;
    private float culture; // number from -100 to 100
    private float direction;
    private int stepsRemaining = 0;
    private int age;
    private int numberOfChildren;
    private final SimulationParameters parameters;
    private final PrngHolder prngHolder;

    public Agent(SimulationParameters parameters, Point position, float culture, int age) {
        if (culture < -100 || culture > 100)
            throw new IllegalArgumentException("not within [-100, 100]");

        this.prngHolder = parameters.prngHolder();
        this.parameters = parameters;
        this.position = position;
        this.culture = culture;
        this.age = age;
    }

    public Agent(SimulationParameters parameters) {
        this(
            parameters,
            new Point(
                    parameters.prngHolder().randomCoordinate(),
                    parameters.prngHolder().randomCoordinate()),
            parameters.prngHolder().randomCulture(),
            parameters.prngHolder().randomAge()
        );
    }

    public void reborn(float x, float y, float culture, int age) {
        this.culture = culture;
        this.age = age;
        position.setX(x);
        position.setY(y);
        stepsRemaining = 0;
        numberOfChildren = 0;

        positionUpdate(this);
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        move((float) (Math.cos(direction) * parameters.distancePerStep()),
             (float) (Math.sin(direction) * parameters.distancePerStep()));

        stepsRemaining--;
    }

    public void receiveMessage(float cultureOfSender) {

        if (cultureOfSender > 0) { // sender is k
            culture += cultureOfSender * parameters.messageFactor() * parameters.assimilationFactor();
        }
        else { // sender is r
            culture += cultureOfSender * parameters.messageFactor();
        }

        if (culture < -100) culture = -100;
        if (culture > 100) culture = 100;
    }

    public void increaseAge() {
        age++;
    }

    public boolean makeChildrenDecision() {
        if (age < TOTAL_STEPS_OF_LIFE / 3 || age > TOTAL_STEPS_OF_LIFE / 3 * 2)
            return false;

        for (int checkpoint : CHILDREN_CHECKPOINTS) {
            if (age == checkpoint) {
                if (culture > 0) { // k-branch
                    return prngHolder.randomFloat(0, 1) <
                            parameters.k_fertilityFactor() / CHILDREN_CHECKPOINTS.length;
                }
                else { // r-branch
                    return prngHolder.randomFloat(0, 1) <
                            parameters.r_fertilityFactor() / CHILDREN_CHECKPOINTS.length;
                }
            }
        }

        return false;
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

        positionUpdate(this);
    }

    private void resetTarget() {
        direction = prngHolder.randomDirection();
        stepsRemaining = prngHolder.randomInteger(100, 200);
    }

    public void setCulture(float culture) {
        if (culture < -100 || culture > 100)
            throw new IllegalArgumentException("not within [-100, 100]");

        this.culture = culture;
    }

    @Override
    public String toString() {
        return position.getX() + "," + position.getY() + "," + culture;
    }
}
