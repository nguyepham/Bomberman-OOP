package game.bomman.entity.character;

import game.bomman.entity.Blockable;
import game.bomman.entity.Entity;
import game.bomman.map.Map;

public abstract class Character extends Entity implements Blockable {
    protected double velocityX;
    protected double velocityY;
    protected double newLoadingX;
    protected double newLoadingY;
    protected int positionOnMapX;
    protected int positionOnMapY;
    protected double speed;
    protected Map map;

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void moveUp();

    public abstract void removeDown();

    public abstract void removeLeft();

    public abstract void removeRight();

    public abstract void removeUp();

    public abstract void moveTo(double x, double y);

    public static final String NOT_MOVING = "nm";

    public void beTouched() {
        System.out.println("Character touched.");
    }
}
