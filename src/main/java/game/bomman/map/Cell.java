package game.bomman.map;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Cell extends Entity {
    private int[] pos = new int[2];
    private boolean blocking;
    private char rawConfig;
    private Image sprite;
    private ImmobileEntity entity;

    public Cell(int x, int y, char rawConfig) {
        pos[0] = x;
        pos[1] = y;
        this.rawConfig = rawConfig;
        blocking = false;
        hitBox = new HitBox(SIDE * x, SIDE * y, SIDE, SIDE);
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking() {
        blocking = true;
    }

    public double getWidth() {
        return hitBox.getWidth();
    }

    public double getHeight() {
        return hitBox.getHeight();
    }

    public char getRawConfig() {
        return rawConfig;
    }

    public Image getSprite() { return sprite; }

    public void setEntity(ImmobileEntity value) {
        entity = value;
    }

    public int[] getPostitionInMap() {
        return pos;
    }

    public void getSpriteFrom(String filePath) throws FileNotFoundException {
        sprite = loadImage(filePath);
    }

    @Override
    public void update(double elapsedTime, double timeSinceStart) {

    }
}

