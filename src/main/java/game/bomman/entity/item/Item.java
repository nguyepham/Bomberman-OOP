package game.bomman.entity.item;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class Item extends Entity {
    protected static GraphicsContext gc;
    protected static final double SPRITE_DURATION = 0.3f;;
    protected static final double EXISTING_TIME = 15.0f;
    protected static final int N_SPRITE = 2;
    protected double countdownTimer = EXISTING_TIME;
    protected boolean countdownStarted = false;

    public static void setCanvas(GraphicsContext value) {
        gc = value;
    }

    protected void disappear() {
        removeFromCell(positionOnMapX, positionOnMapY);
        InteractionHandler.removeItem(this);
    }

    public static void startCountdownTimer(Item item) {
        item.countdownStarted = true;
    }
}
