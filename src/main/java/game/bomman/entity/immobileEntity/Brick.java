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

    public Brick(GraphicsContext gc, double loadingPosX, double loadingPosY) {
        super(loadingPosX, loadingPosY);
        this.gc = gc;
        gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, 0, 0, side, side, positionX, positionY, side, side);
    }

    @Override
    public void update(double elapsedTime) {

    }
}
