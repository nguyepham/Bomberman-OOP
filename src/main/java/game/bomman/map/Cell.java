package game.bomman.map;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cell extends Entity {
    private int[] pos = new int[2];
    private boolean blocking;
    private char rawConfig;
    private Image staticSprite;
    private List<Entity> entityList = new ArrayList<>();

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

    public void setBlocking(boolean value) {
        blocking = value;
    }

    public char getRawConfig() {
        return rawConfig;
    }

    public Image getSprite() { return staticSprite; }

    public void getSpriteFrom(String filePath) throws FileNotFoundException {
        staticSprite = loadImage(filePath);
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity) {
        for (int i = 0; i < entityList.size(); ++i) {
            if (entity.equals(entityList.get(i))) {
                entityList.remove(i);
                break;
            }
        }
    }

    @Override
    public void update(double elapsedTime, double timeSinceStart) {

    }
}

