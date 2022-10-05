package game.bomman.entity.character;

import game.bomman.entity.Blockable;
import game.bomman.entity.Entity;

public abstract class Character extends Entity implements Blockable {
    protected double velocityX;
    protected double velocityY;

    public static final String NOT_MOVING = "nm";

    public void beTouched() {
        System.out.println("Character touched.");
    }
}
