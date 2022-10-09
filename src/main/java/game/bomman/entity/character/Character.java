package game.bomman.entity.character;

import game.bomman.entity.Blockable;
import game.bomman.entity.Entity;
import game.bomman.inputHandler.MovingController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Character extends Entity implements Blockable {
    protected double velocityX;
    protected double velocityY;
    protected double speed;

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
