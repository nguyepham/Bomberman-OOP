package game.bomman.entity.character.enemy;

import game.bomman.command.Command;
import game.bomman.component.CharacterController;
import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public class Balloon extends Enemy {
    private static final double MOVING_SPRITE_DURATION = 0.3f;
    private static final int N_MOVING_SPRITES = 3;
    private static final double DYING_SPRITE_DURATION = 0.15f;
    private static final int N_DYING_SPRITES = 5;
    private double dyingTimer = 0;
    private int dyingFrameIndex = 0;
    private static final Image balloonWalking;
    private static final Image balloonDying;

    static {
        try {
            balloonWalking = loadImage(IMAGES_PATH + "/enemy/balloon.png") ;
            balloonDying = loadImage(IMAGES_PATH + "/enemy/balloonDying.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Balloon(Map map, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        movingCommand = CharacterController.moveDown;
        timer = new Random().nextDouble(MOVING_SPRITE_DURATION);
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        newLoadingX = loadingPosX;
        newLoadingY = loadingPosY;
        speed = 90;
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
    public void update(double elapsedTime) {
        if (isAlive == false) {
            dyingTimer += elapsedTime;
            dying();
            return;
        }

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
            gc.drawImage(balloonDying,
                    SIDE * dyingFrameIndex, 0, SIDE, SIDE,
                    hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
            return;
        }

        hitBox.setMinX(newLoadingX);
        hitBox.setMinY(newLoadingY);

        gc.drawImage(balloonWalking,
                SIDE * frameIndex, 0, SIDE, SIDE,
                newLoadingX, newLoadingY, SIDE, SIDE);
    }

    @Override
    public void interactWith(Entity other) {

    }

    @Override
    public void layingBomb() {

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
