package game.bomman.map;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Cell extends Entity {
    private int[] pos = new int[2];
    private char rawConfig;
    private Image sprite;

    public Cell(int x, int y, char rawConfig) {
        pos[0] = x;
        pos[1] = y;
        this.rawConfig = rawConfig;
        hitBox = new HitBox(SIDE * x, SIDE * y, SIDE, SIDE);
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

    public int[] getPostitionInMap() {
        return pos;
    }

    public void getSpriteFrom(String filePath) throws FileNotFoundException {
        sprite = loadImage(filePath);
    }
}

