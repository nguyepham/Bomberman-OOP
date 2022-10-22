package game.bomman.entity.character.enemy;

import game.bomman.component.CharacterController;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.Flame;
import game.bomman.entity.character.Bomber;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public class Fire extends Enemy {
    private static final double MOVING_SPRITE_DURATION = 0.2f;
    private static final int N_MOVING_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.16f;
    private static final int N_DYING_SPRITES = 7;
    private double dyingTimer = 0;
    private int dyingFrameIndex = 0;
    private static final Image fireWalking;
    private static final Image fireDying;

    static {
        try {
            fireWalking = loadImage(IMAGES_PATH + "/enemy/fire@4.png") ;
            fireDying = loadImage(IMAGES_PATH + "/enemy/fire_die@7.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Fire(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        movingCommand = CharacterController.moveDown;
        timer = new Random().nextDouble(MOVING_SPRITE_DURATION);
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        newLoadingX = loadingPosX;
        newLoadingY = loadingPosY;
        speed = 120;
        this.map = map;
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    private void dying() {
        if (dyingTimer >= DYING_SPRITE_DURATION) {
            dyingTimer = 0;
            ++dyingFrameIndex;
            if (dyingFrameIndex == N_DYING_SPRITES) {
                this.removeFromCell(positionOnMapX, positionOnMapY);
                InteractionHandler.removeEnemy(this);
            }
        }
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Flame) {
            this.die();
        }
    }

    @Override
    public void update(double elapsedTime) {
        if (isAlive == false) {
            dyingTimer += elapsedTime;
            dying();
            return;
        }

        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        /// Handle interaction between Bomber and other entities.
        InteractionHandler.handleInteraction(this, thisCell);

        timer += elapsedTime;
        if (timer >= MOVING_SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_MOVING_SPRITES) {
                frameIndex = 0;
            }
        }
        updatePosition(elapsedTime);

    }

    @Override
    public void draw() {
        if (isAlive == false) {
            gc.drawImage(fireDying,
                    SIDE * dyingFrameIndex, 0, SIDE, SIDE,
                    hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            return;
        }

        hitBox.setMinX(newLoadingX);
        hitBox.setMinY(newLoadingY);

        gc.drawImage(fireWalking,
                SIDE * frameIndex, 0, SIDE, SIDE,
                newLoadingX, newLoadingY, SIDE, SIDE);
    }

    @Override
    public void layingBomb() {

    }

    @Override
    public boolean isBlocked() {
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        return reachedMapEege();
    }

    @Override
    public void moveDown() {
        facingDirectionIndex = 2;
    }

    @Override
    public void moveLeft() {
        facingDirectionIndex = 3;
    }

    @Override
    public void moveRight() {
        facingDirectionIndex = 1;
    }

    @Override
    public void moveUp() {
        facingDirectionIndex = 0;
    }

    @Override
    public void removeDown() {

    }

    @Override
    public void removeLeft() {

    }

    @Override
    public void removeRight() {

    }

    @Override
    public void removeUp() {

    }
}
