package game.bomman.entity.item;

import game.bomman.component.InteractionHandler;
import game.bomman.component.SoundPlayer;
import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.Flame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public abstract class Item extends Entity {
    protected static GraphicsContext gc;
    protected static final Image explodingImage;
    protected static final int N_EXPLODING_SPRITE = 7;
    protected static final double SPRITE_DURATION = 0.3f;
    protected static final double EXISTING_TIME = 15.0f;
    protected static final double EXPLODING_SPRITE_DURATION = 0.15f;
    protected static final int N_SPRITE = 2;
    protected double countdownTimer = EXISTING_TIME;
    protected double explodingTimer = 0;
    protected int explodingFrameIndex = 0;
    protected boolean countdownStarted = false;
    protected boolean isExploding = false;

    static {
        try {
            explodingImage = loadImage(IMAGES_PATH + "/item/bonus_explosion@7.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }

    protected void exploding() {
        if (explodingTimer >= EXPLODING_SPRITE_DURATION) {
            explodingTimer = 0;
            ++explodingFrameIndex;
            if (explodingFrameIndex == N_EXPLODING_SPRITE) {
                disappear(false);
            }
        }
    }

    protected void disappear(boolean byBomber) {
        if (byBomber == true) {
            SoundPlayer.playBonusSound();
        }
        InteractionHandler.removeItem(this);
    }

    public static void startCountdownTimer(Item item) {
        item.countdownStarted = true;
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Flame) {
            isExploding = true;
        }
    }

    @Override
    public void update(double elapsedTime) {
        InteractionHandler.handleInteraction(this, map.getCell(getPosOnMapX(), getPosOnMapY()));

        if (isExploding == true) {
            explodingTimer += elapsedTime;
            exploding();
            return;
        }

        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITE) {
                frameIndex = 0;
            }
        }
        if (countdownStarted == false) {
            return;
        }

        countdownTimer -= elapsedTime;
        if (countdownTimer <= 0) {
            disappear(false);
        }
    }

    @Override
    public void draw() {
        gc.drawImage(explodingImage,
                SIDE * explodingFrameIndex, 0, SIDE, SIDE,
                hitBox.getMinX(), hitBox.getMinY(), SIDE, SIDE);
    }
}
