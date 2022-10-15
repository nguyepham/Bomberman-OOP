package game.bomman.entity.immobileEntity;

import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class ImmobileEntity extends Entity {
    protected static GraphicsContext gc;

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }
}
