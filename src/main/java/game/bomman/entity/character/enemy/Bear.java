package game.bomman.entity.character.enemy;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.character.Bomber;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

/**
 * Có khá năng đuổi theo Bomber bằng BFS và tăng tốc khi đến gần Bomber.
 */
public class Bear extends FourthTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.3f;
    private static final int N_MOVING_SPRITES = 3;
    private static final double DYING_SPRITE_DURATION = 0.2f;
    private static final int N_DYING_SPRITES = 5;
    private static final Image bearWalking;
    private static final Image bearDying;
    private static final double MAX_SPEED = 180;
    private static final double MIN_SPEED = 100;
    private static double MAX_DISTANCE;

    static {
        try {
            bearWalking = loadImage(IMAGES_PATH + "/enemy/bear@3.png");
            bearDying = loadImage(IMAGES_PATH + "/enemy/bear_die@5.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Bear(Map map, double loadingPosX, double loadingPosY) {
        super(bearWalking, bearDying, N_MOVING_SPRITES, N_DYING_SPRITES,
                MOVING_SPRITE_DURATION, DYING_SPRITE_DURATION,
                map, loadingPosX, loadingPosY, 220);
        MAX_DISTANCE = map.getWidth() - 1 + map.getHeight() - 1;
    }

    @Override
    public void update(double elapsedTime) throws FileNotFoundException {
        Bomber bomber = InteractionHandler.getBomber();
        // khoảng cách đến Bomber tính theo Cell
        int distance = Math.abs(bomber.getPosOnMapX() - getPosOnMapX())
                + Math.abs(bomber.getPosOnMapY() - getPosOnMapY()) - 2;
        // tốc độ luôn trong [MIN_SPEED, MAX_SPEED]
        speed = MIN_SPEED + (MAX_DISTANCE - distance) / MAX_DISTANCE * (MAX_SPEED - MIN_SPEED);
        super.update(elapsedTime);
    }
}
