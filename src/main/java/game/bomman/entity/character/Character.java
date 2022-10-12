package game.bomman.entity.character;

import game.bomman.entity.Blockable;
import game.bomman.entity.Entity;
import game.bomman.map.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends Entity implements Blockable {
    protected double newLoadingX;
    protected double newLoadingY;
    protected int positionOnMapX;
    protected int positionOnMapY;
    protected double speed;
    protected Map map;
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

    public abstract void draw();
}
