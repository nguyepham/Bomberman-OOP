package game.bomman.entity.immobileEntity;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import javafx.scene.canvas.GraphicsContext;

public abstract class ImmobileEntity extends Entity {
    public static final double side = 48;

    public ImmobileEntity() {}

    public ImmobileEntity(double loadingPosX, double loadingPosY) {
        hitBox = new HitBox(loadingPosX, loadingPosX, SIDE, SIDE);
    }


    public abstract void render(GraphicsContext gc);
}
