package game.object;

public abstract class Character extends Entity {
    protected enum State {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STAND_BACK,
        STAND_FRONT,
        STAND_LEFT,
        STAND_RIGHT,
        DEAD
    }

    protected static final String[] STATES = {
            "up",
            "down",
            "left",
            "right",
            "stand_back",
            "stand_front",
            "stand_left",
            "stand_right"
    };

    /**
     * Creates a character with initial position.
     *
     * @param x initial horizontal coordinate.
     * @param y initial vertical coordinate.
     */
    public Character(int x, int y) {
        super(x, y);
    }
}
