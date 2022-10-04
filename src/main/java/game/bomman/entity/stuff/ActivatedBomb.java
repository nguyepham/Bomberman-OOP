package game.bomman.entity.stuff;

import game.bomman.entity.Blocking;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class ActivatedBomb extends Stuff implements Blocking {
    private static final Image image;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/bomb@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ActivatedBomb() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
