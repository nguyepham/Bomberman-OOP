package game.bomman.entity;

import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Flame extends Entity {
    private static final Image centralImage;
    private static final Image horizonImage;
    private static final Image verticalImage;
    private static final Image upImage;
    private static final Image downImage;
    private static final Image leftImage;
    private static final Image rightImage;

    static {
        try {
            centralImage = loadImage(IMAGES_PATH + "/flame/central.png");
            horizonImage = loadImage(IMAGES_PATH + "/flame/horizon.png");
            verticalImage = loadImage(IMAGES_PATH + "/flame/vertical.png");
            upImage = loadImage(IMAGES_PATH + "/flame/up.png");
            downImage = loadImage(IMAGES_PATH + "/flame/down.png");
            leftImage = loadImage(IMAGES_PATH + "/flame/left.png");
            rightImage = loadImage(IMAGES_PATH + "/flame/right.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void interactWith(Entity other) {

    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {

    }
}
