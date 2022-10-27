package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Balloon extends FirstTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.3f;
    private static final int N_MOVING_SPRITES = 3;
    private static final double DYING_SPRITE_DURATION = 0.2f;
    private static final int N_DYING_SPRITES = 5;
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

    public Balloon(Map map, double loadingPosX, double loadingPosY) {
        super(balloonWalking, balloonDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY);
        speed = 100;
        setNumOfLives(1);
    }
}
