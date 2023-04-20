package cz.muni.fi.iv109.core;

import cz.muni.fi.iv109.core.util.Point;
import cz.muni.fi.iv109.core.util.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

@Getter
@AllArgsConstructor
public class Agent {

    private Point position;
    private float culture; // number from -100 to 100
    private float direction;
    private short stepsRemaining = 0;
    private short stepsOfLifeRemaining = 3000; // one minute on 50 fps
    private final SimulationParameters parameters;

    public Agent(SimulationParameters parameters, Point position, float culture) {
        if (culture < -100 || culture > 100)
            throw new IllegalArgumentException("not within [-100, 100]");

        this.parameters = parameters;
        this.position = position;
        this.culture = culture;
    }

    public Agent(SimulationParameters parameters) {
        float x = PrngHolder.randomFloat(0f, PLAYGROUND_SIZE);
        float y = PrngHolder.randomFloat(0f, PLAYGROUND_SIZE);

        this.parameters = parameters;
        this.position = new Point(x, y);
        this.culture = PrngHolder.randomFloat(-100f, 100f);
    }

    public void move() {
        if (stepsRemaining == 0) resetTarget();

        move((float) (Math.cos(direction) * parameters.distancePerStep()),
             (float) (Math.sin(direction) * parameters.distancePerStep()));

        stepsRemaining--;
    }

    public void receiveMessage(Point positionOfSender, float cultureOfSender) {
        culture += cultureOfSender * parameters.assimilationFactor();
//        float mid = (culture + cultureOfSender) / 2;
//        culture += (mid - culture) * parameters.assimilationFactor();
        if (culture < -100) culture = -100;
        if (culture > 100) culture = 100;

        // shift(positionOfSender, cultureOfSender); // TODO: fine-tune shift parameters
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

    @Override
    public String toString() {
        return position.getX() + "," + position.getY() + "," + culture;
    }
}
