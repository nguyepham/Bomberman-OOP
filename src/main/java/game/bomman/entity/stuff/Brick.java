package game.bomman.entity.stuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Brick extends Stuff {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/map/brick.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Brick(double positionX, double positionY) {
        super(positionX, positionY);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, 0, 0, side, side, positionX, positionY, side, side);
    }
}
