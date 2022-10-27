package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public class Fire extends SecondTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.2f;
    private static final int N_MOVING_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.142f;
    private static final int N_DYING_SPRITES = 7;
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

    public Fire(Map map, double loadingPosX, double loadingPosY) {
        super(fireWalking, fireDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY);
        brickPassing = true;
        steelPassing = true;
        speed = 80;
    }

    @Override
    public void layingBomb() {

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
