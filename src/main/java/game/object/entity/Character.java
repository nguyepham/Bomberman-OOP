package game.object.entity;

import game.object.entity.Entity;

public abstract class Character extends Entity {
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

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

    public void move(Direction direction) {
        switch (direction) {
            case UP -> x--;
            case DOWN -> x++;
            case LEFT -> y--;
            case RIGHT -> y++;
        }
    }
}
