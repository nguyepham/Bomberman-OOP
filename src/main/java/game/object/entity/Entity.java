package game.object.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Entity {
    protected int x;
    protected int y;
    public ImageView view;

    /**
     * Creates an entity with initial position.
     *
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected static Image[] loadImages(String imagePath, String[] states) throws FileNotFoundException {
        Image[] images = new Image[states.length];
        for (int i = 0; i < states.length; ++i) {
            images[i] = new Image(new FileInputStream(imagePath + states[i] + ".gif"));
        }
        return images;
    }
}
