package game.bomman.entity.immobileEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Brick extends ImmobileEntity {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/map/brick.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Brick(double loadingPosX, double loadingPosY) {
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
        gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
    }

    @Override
    public void update(double elapsedTime, double timeSinceStart) {

    }
}
