package game.bomman.entity.immobileEntity;

import game.bomman.entity.Entity;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Portal extends ImmobileEntity {
    private static final Image image;
    private static final double SPRITE_DURATION = 0.5f;
    private boolean activated = false;
    private boolean appeared = false;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/map/exit@2.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void running(double elapsedTime) {
        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            frameIndex = 1 - frameIndex;
        }
    }

    public Portal(Map map, double loadingPosX, double loadingPosY) {
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    public void activate() { activated = true; }

    public boolean isActivated() {return activated; }

    public void appear() { appeared = true; }

    public boolean hasAppeared() { return appeared; }

    @Override
    public void interactWith(Entity other) {

    }

    @Override
    public void update(double elapsedTime) {
        if (activated == true) {
            this.running(elapsedTime);
        }
    }

    @Override
    public void draw() {
        gc.drawImage(image, SIDE * frameIndex, 0, SIDE, SIDE, hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
    }
}
