package game.bomman.entity.immobileEntity;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Brick extends ImmobileEntity {
    private static final Image image;
    private static final Image breakingImage;
    private static final Image sparkyImage;
    private boolean isBreaking = false;
    private boolean broken = false;
    private static final double SPRITE_DURATION = 0.08;
    private static final int N_SPRITES = 7;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/map/brick.png");
            breakingImage = loadImage(IMAGES_PATH + "/map/brick_explosion@7.png");
            sparkyImage = loadImage(IMAGES_PATH + "/map/brick_sparky@2.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Brick(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
        gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
    }

    public void explode() {
        isBreaking = true;
    }

    private void beingBroken(double elapsedTime) {
        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITES) {
                isBreaking = false;
                broken = true;
            }
        }
    }

    @Override
    public void update(double elapsedTime) throws FileNotFoundException {
        if (isBreaking == true) {
            beingBroken(elapsedTime);
        }
        if (broken == true) {
            removeFromCell(positionOnMapX, positionOnMapY);
            InteractionHandler.removeImmobileEntity(this);
            map.getCell(positionOnMapX, positionOnMapY).setBlocking(false);

            Cell belowCell = map.getCell(positionOnMapX, positionOnMapY + 1);
            if (belowCell.getRawConfig() != '#') {
                belowCell.setGrass();
            }
        }
    }

    @Override
    public void draw() {
        double loadingPosX = hitBox.getMinX();
        double loadingPosY = hitBox.getMinY();

        if (isBreaking == false && broken == false) {
            gc.drawImage(image, loadingPosX, loadingPosY, SIDE, SIDE);
            return;
        }
        if (isBreaking == true && broken == false) {
            gc.drawImage(breakingImage, SIDE * frameIndex, 0, SIDE, SIDE, loadingPosX, loadingPosY, SIDE, SIDE);
        }
    }
}
