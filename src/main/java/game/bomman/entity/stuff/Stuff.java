package game.bomman.entity.stuff;

import game.bomman.entity.Blocking;
import game.bomman.entity.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Stuff extends Entity implements Blocking {
    public static final double side = 48;

    public Stuff(int row, int col) {
        this.positionX = col * side;
        this.positionY = row * side;
    }

    public abstract void render(GraphicsContext gc);

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, side - 1, side - 1);
    }
}
