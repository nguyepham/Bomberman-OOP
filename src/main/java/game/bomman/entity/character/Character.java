package game.bomman.entity.character;

import game.bomman.entity.Entity;

public abstract class Character extends Entity {
    protected double velocityX;
    protected double velocityY;

    public static final String NOT_MOVING = "nm";
}
