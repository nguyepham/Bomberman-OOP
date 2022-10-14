package game.bomman.entity.immobileEntity;

import game.bomman.component.InteractionHandler;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class ActivatedBomb extends ImmobileEntity {
    private static final Image image;
    private double explodingTimer = 2.0f;
    private boolean isExploding = false;
    private boolean exploded = false;
    private static final double SPRITE_DURATION = 0.15;
    private static final int N_SPRITES = 4;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/bomb@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ActivatedBomb(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        this.map = map;
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    private void countDown(double elapsedTime) {
        explodingTimer -= elapsedTime;
        if (explodingTimer <= 0) {
            explode();
        }
    }

    private void explode() {
        isExploding = true;
        removeFromCell(positionOnMapX, positionOnMapY);
        InteractionHandler.removeImmobileEntity(this);
    }

    @Override
    public void update(double elapsedTime) {

        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITES) {
                frameIndex = 0;
            }
        }

        countDown(elapsedTime);
    }

    @Override
    public void draw() {
        double loadingPosX = hitBox.getMinX();
        double loadingPosY = hitBox.getMinY();

        if (isExploding == false) {
            gc.drawImage(image, SIDE * frameIndex, 0, SIDE, SIDE, loadingPosX, loadingPosY, SIDE, SIDE);
        }
    }
}
