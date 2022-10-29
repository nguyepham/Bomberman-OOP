package game.bomman.entity.character.enemy;

import game.bomman.map.Map;
import javafx.scene.image.Image;

public class FourthTypeOfMovement extends Enemy {
    public FourthTypeOfMovement(Image walkingImage, Image dyingImage, int nMovingSprites, int nDyingSprites, double movingSpriteDuration, double dyingSpriteDuration, Map map, double loadingPosX, double loadingPosY, int score) {
        super(walkingImage, dyingImage, nMovingSprites, nDyingSprites, movingSpriteDuration, dyingSpriteDuration, map, loadingPosX, loadingPosY, score);
    }

    @Override
    public void runAI(double elapsedTime) {
        if (movementController.markedCellReached() || isBlocked()) {
            movementController.clearPath();
            movementController.tracesThisCell();
            movementController.generatePathUsingDFS(getPosOnMapX(), getPosOnMapY());
        }
        movementController.moveByDFS();
    }
}
