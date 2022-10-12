package game.bomman.entity.immobileEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class ActivatedBomb extends ImmobileEntity {
    private static final Image image;
    private double timer;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/bomb@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void runTimer() {

    }

    public ActivatedBomb(double loadingPosX, double loadingPosY) {
        timer = System.nanoTime() / 1000000000.0f;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
        gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
    }

    public void explode() {

    }

    @Override
    public void update(double elapsedTime, double timeSinceStart) {

    }
}
