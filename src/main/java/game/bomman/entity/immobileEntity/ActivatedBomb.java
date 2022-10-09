package game.bomman.entity.immobileEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class ActivatedBomb extends ImmobileEntity {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/bomb@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ActivatedBomb(double loadingPosX, double loadingPosY) {
        super(loadingPosX, loadingPosY);
        gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update(double elapsedTime) {

    }
}
