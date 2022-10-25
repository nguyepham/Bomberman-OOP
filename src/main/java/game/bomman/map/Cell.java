package game.bomman.map;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.Bomb;
import game.bomman.entity.immobileEntity.Brick;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import game.bomman.entity.item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cell extends Entity {
    private static GraphicsContext gc;
    private boolean isSteel = false;
    private boolean isWall = false;
    private char rawConfig;
    private Image staticSprite;
    private List<Entity> entityList = new ArrayList<>();

    public Cell(GraphicsContext gc_, int x, int y, char rawConfig_) {
        gc = gc_;
        rawConfig = rawConfig_;
        initHitBox(SIDE * x, SIDE * y, SIDE, SIDE);
    }

    public boolean isSteel() { return isSteel; }

    public boolean isWall() { return isWall; }

    public boolean isBlocking(Character character) {
        boolean blocking = isWall || (isSteel && character.getSteelPassing() == false);
        ImmobileEntity entity = InteractionHandler.getImmobileEntity(this);
        if (entity != null) {
            blocking = blocking || (entity instanceof Brick && character.isBrickPassing() == false);
            blocking = blocking || (entity instanceof Bomb && character.isBombPassing() == false);
        }
        return blocking;
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
        isSteel = true;
    }

    public void setWall() throws FileNotFoundException {
        staticSprite = loadImage(IMAGES_PATH + "/map/walls@10.png");
        isWall = true;
    }

    public ImmobileEntity getImmobileEntity() {
        return InteractionHandler.getImmobileEntity(this);
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
    public void interactWith(Entity other) {

    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {

    }
}

