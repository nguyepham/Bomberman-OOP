package game.bomman.entity.stuff;

import game.bomman.entity.Blocking;
import game.bomman.entity.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Stuff extends Entity implements Blocking {
    public static final double side = 48;

    public Stuff(int row, int col) {
        this.positionX = col * side;
        this.positionY = row * side;

    }

    public abstract void render(GraphicsContext gc);

    public void renderBoundingBox(GraphicsContext gc) {
        Color fillColor = Color.ALICEBLUE;
        if (this instanceof Grass) {
            fillColor = Color.GREEN;
        }
        gc.setFill(fillColor);
        gc.fillRect(positionX, positionY, side, side);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, side, side);
    }
}
