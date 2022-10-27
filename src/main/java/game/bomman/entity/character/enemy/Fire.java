package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

/**
 * Fire có thể đi qua Brick và Steel và thay đổi hướng mỗi 3s.
 */
public class Fire extends SecondTypeOfMovement {
    private static final double MOVING_SPRITE_DURATION = 0.2f;
    private static final int N_MOVING_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.142f;
    private static final int N_DYING_SPRITES = 7;
    private static final Image fireWalking;
    private static final Image fireDying;
    public static final double GO_AHEAD_TIME = 3;
    protected double goAheadTimer = 0;

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
        speed = 80;
    }

    public void resetGoAheadTimer() { goAheadTimer = 0; }

    public void addGoAheadTimer(double time) { goAheadTimer += time; }

    public boolean timerUp() {
        return goAheadTimer >= GO_AHEAD_TIME;
    }

    @Override
    public void runAI(double elapsedTime) {
        addGoAheadTimer(elapsedTime);
        if (this.isBlocked() || (timerUp() && fitInThatCell())) {
            super.runAI(elapsedTime);
            if (timerUp()) {
                resetGoAheadTimer();
            }
        }
    }
}
