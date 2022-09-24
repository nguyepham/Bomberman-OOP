package game.object;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class Bomber extends Character {
    protected static final Image[] images;
    private static final String IMAGE_PATH = ".\\assets\\BM\\";
    // Initialises images
    static {
        try {
            images = loadImages(IMAGE_PATH, STATES);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a Bomber character with his initial position.
     *
     * @param x initial horizontal coordinate.
     * @param y initial vertical coordinate.
     */
    public Bomber(int x, int y) {
        super(x, y);
        view = new ImageView(images[State.STAND_FRONT.ordinal()]);
        view.setX(x);
        view.setY(y);
    }
}
