package game.bomman.entity.stuff;

import game.bomman.entity.Blocking;
import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class Stuff extends Entity implements Blocking {
    public static final double side = 48;
    public Stuff(double positionX, double positionY) {
        this.positionX = positionX * side;
        this.positionY = positionY * side;
    }

    public abstract void render(GraphicsContext gc);
}
