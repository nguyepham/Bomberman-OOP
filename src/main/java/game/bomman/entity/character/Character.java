package game.bomman.entity.character;

import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends Entity {
    protected double newLoadingX;
    protected double newLoadingY;
    protected double speed;
    protected static GraphicsContext gc;

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }

    public abstract void layingBomb();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void moveUp();

    public abstract void removeDown();

    public abstract void removeLeft();

    public abstract void removeRight();

    public abstract void removeUp();
}
