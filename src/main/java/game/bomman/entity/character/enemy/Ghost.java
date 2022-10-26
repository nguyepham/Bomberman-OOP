package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

/**
 * Moves like balloon but faster. Has two lives.
 */
public class Ghost extends FirstTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.3;
    private static final int N_MOVING_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.2;
    private static final int N_DYING_SPRITES = 7;
    private static final Image ghostWalking;
    private static final Image ghostDying;

    static {
        try {
            ghostWalking = loadImage(IMAGES_PATH + "/enemy/ghost@4.png") ;
            ghostDying = loadImage(IMAGES_PATH + "/enemy/ghost_die@7.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Ghost(Map map, double loadingPosX, double loadingPosY) {
        super(ghostWalking, ghostDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY);
        speed = 150;
        setNumOfLives(2);
    }
}
