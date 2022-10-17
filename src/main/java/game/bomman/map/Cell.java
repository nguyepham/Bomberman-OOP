package game.bomman.map;

import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cell extends Entity {
    private static GraphicsContext gc;
    private int[] pos = new int[2];
    private boolean blocking;
    private char rawConfig;
    private Image staticSprite;
    private List<Entity> entityList = new ArrayList<>();

    public Cell(GraphicsContext gc_, int x, int y, char rawConfig_) {
        gc = gc_;
        pos[0] = x;
        pos[1] = y;
        rawConfig = rawConfig_;
        blocking = false;
        initHitBox(SIDE * x, SIDE * y, SIDE, SIDE);
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

    public void reloadGrass() {
        gc.drawImage(
                staticSprite,
                0, 0, Entity.SIDE, Entity.SIDE,
                getLoadingPositionX(), getLoadingPositionY(), Entity.SIDE, Entity.SIDE
        );
    }

    public void setGrass() throws FileNotFoundException {
        staticSprite = loadImage(IMAGES_PATH + "/map/grass@2.png");
    }

    public void setSteel() throws FileNotFoundException {
        staticSprite = loadImage(IMAGES_PATH + "/map/steel.png");
        setBlocking(true);
    }

    public void setWall() throws FileNotFoundException {
        staticSprite = loadImage(IMAGES_PATH + "/map/walls@10.png");
        setBlocking(true);
    }

    public Entity getEntity(int index) {
        return entityList.get(index);
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
    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {

    }

    @Override
    public int getPosOnMapX() {
        return pos[0];
    }

    @Override
    public int getPosOnMapY() {
        return pos[1];
    }
}

