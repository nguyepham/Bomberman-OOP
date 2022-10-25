package game.bomman.entity.immobileEntity;

import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class ImmobileEntity extends Entity {
    public static double countdownTimer = 2.0f;
    protected static GraphicsContext gc;

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }

    public void explode() {}
}
