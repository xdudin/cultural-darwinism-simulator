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

    public Agent(int playgroundSize) {
        int x = PrngHolder.randomInteger(0, playgroundSize);
        int y = PrngHolder.randomInteger(0, playgroundSize);

        position = new Point(x, y);
        culture = PrngHolder.randomByte(-100, 100);
    }
}
