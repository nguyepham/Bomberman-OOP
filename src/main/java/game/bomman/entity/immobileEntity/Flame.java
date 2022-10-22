package game.bomman.entity.immobileEntity;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
public class Flame extends ImmobileEntity {
    private static final Image centralImage;
    private static final Image horizonImage;
    private static final Image verticalImage;
    private static final Image upImage;
    private static final Image downImage;
    private static final Image leftImage;
    private static final Image rightImage;
    private static final double SPRITE_DURATION = 0.18f;
    private static final int N_CENTRAL_SPRITES = 5;
    private static final int N_OTHER_SPRITES = 4;
    private double otherTimer = 0;
    private int otherFrameIndex = 0;
    private char label;

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

    public Flame(char label, Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        this.label = label;
        this.map = map;
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    @Override
    public void interactWith(Entity other) {

    }

    private void disappear() {
        removeFromCell(positionOnMapX, positionOnMapY);
        InteractionHandler.removeImmobileEntity(this);
    }

    @Override
    public void update(double elapsedTime) {

        if (label == 'c') {
            timer += elapsedTime;
            if (timer >= SPRITE_DURATION) {
                timer = 0;
                ++frameIndex;
                if (frameIndex == N_CENTRAL_SPRITES) {
                    disappear();
                }
            }
        } else {
            otherTimer += elapsedTime;
            if (otherTimer >= SPRITE_DURATION) {
                otherTimer = 0;
                ++otherFrameIndex;
                if (otherFrameIndex == N_OTHER_SPRITES) {
                    disappear();
                }
            }
        }
    }

    @Override
    public void draw() {
        switch (label) {
            case 'c'-> {
                gc.drawImage(centralImage,
                        SIDE * frameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'h'-> {
                gc.drawImage(horizonImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'v'-> {
                gc.drawImage(verticalImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'u'-> {
                gc.drawImage(upImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'r'-> {
                gc.drawImage(rightImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'd'-> {
                gc.drawImage(downImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
            case 'l'-> {
                gc.drawImage(leftImage,
                        SIDE * otherFrameIndex, 0, SIDE, SIDE,
                        hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            }
        }
    }
}
