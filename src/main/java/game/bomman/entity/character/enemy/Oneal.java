package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public class Oneal extends ThirdTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.3f;
    private static final int N_MOVING_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.2f;
    private static final int N_DYING_SPRITES = 5;
    private static final Image onealWalking;
    private static final Image onealDying;

    static {
        try {
            onealWalking = loadImage(IMAGES_PATH + "/enemy/oneal.png") ;
            onealDying = loadImage(IMAGES_PATH + "/enemy/onealDying.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Oneal(Map map, double loadingPosX, double loadingPosY) {
        super(onealWalking, onealDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY);
        speed = 120;
    }
}
