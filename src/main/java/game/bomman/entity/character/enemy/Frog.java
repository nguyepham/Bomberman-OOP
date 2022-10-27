package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Di chuyển như Balloon và có thể thay đổi tốc độ.
 */
public class Frog extends FirstTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.3f;
    private static final int N_MOVING_SPRITES = 3;
    private static final double DYING_SPRITE_DURATION = 0.2f;
    private static final int N_DYING_SPRITES = 7;
    private static final Image frogWalking;
    private static final Image frogDying;
    private final Random random = new Random();
    private static final int MAX_SPEED = 200;
    private static final int MIN_SPEED = 100;

    static {
        try {
            frogWalking = loadImage(IMAGES_PATH + "/enemy/frog@3.png") ;
            frogDying = loadImage(IMAGES_PATH + "/enemy/frog_die@7.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Frog(Map map, double loadingPosX, double loadingPosY) {
        super(frogWalking, frogDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY);
        setNumOfLives(1);
    }

    @Override
    public void update(double elapsedTime) {
        if (timer == 0) {
            speed = random.nextDouble(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
        }
        super.update(elapsedTime);
    }
}
