package game.bomman.entity.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Bomber extends Character {
    // Number of sprites for each direction in the Image
    // describing the walking motion of the character.
    private static final int nWalkingSpritesPerDirection = 4;
    private static final double duration = 0.1;
    // UP RIGHT DOWN LEFT
    public static final Image walkingImage;
    public static final Image idleImage;
    static {
        try {
            walkingImage = loadImage(IMAGES_PATH + "/player/walking@16.png");
            idleImage = loadImage(IMAGES_PATH + "/player/idle@4.png");
            imageWidth = walkingImage.getWidth() / 16;
            imageHeight = walkingImage.getHeight();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final double imageWidth;
    private static final double imageHeight;

    // In the beginning
    // the character stands face to face with the user.
    private String currentDirection = "DOWN";
    private int index = 8;

    /**
     * Updates the position of the character.
     *
     * @param elapsedTime the time passed between the current
     *                    game iteration with the previous one.
     * @param timeSinceStart the time passed since the game started.
     * @param direction the direction in which the character moves.
     */
    public void update(double elapsedTime, double timeSinceStart, String direction) {
        setPositionX(positionX + elapsedTime * velocityX);
        setPositionY(positionY + elapsedTime * velocityY);

        // set the index of the sprite to be drawn to screen
        if (!direction.equals(NOT_MOVING)) {
            // the index of the frame of the direction in which we are moving at this moment in time.
            int frameIndex = (int) ((timeSinceStart % (nWalkingSpritesPerDirection * duration)) / duration);
            // the padding which we add to frame index to get the correct index
            // of the sprite that we need to draw in the overall walking image
            int padding = 0;
            switch (direction) {
                case "UP" -> padding += 0;
                case "RIGHT" -> padding += nWalkingSpritesPerDirection;
                case "DOWN" -> padding += nWalkingSpritesPerDirection * 2;
                case "LEFT" -> padding += nWalkingSpritesPerDirection * 3;
            }
            index = padding + frameIndex;

            currentDirection = direction;
        }
    }

    // Renders the character to scene scaled to the dimension
    // specified by the arguments width and height.
    public void render(GraphicsContext gc) {
        // if currently not moving then draw the correct idle sprite
        if (velocityX == 0 && velocityY == 0) {
            switch (currentDirection) {
                case "UP" -> gc.drawImage(idleImage, 0, 0, imageWidth, imageHeight,
                        positionX, positionY, imageWidth, imageHeight);
                case "RIGHT" -> gc.drawImage(idleImage, imageWidth, 0, imageWidth, imageHeight,
                        positionX, positionY, imageWidth, imageHeight);
                case "DOWN" -> gc.drawImage(idleImage, imageWidth * 2, 0, imageWidth, imageHeight,
                        positionX, positionY, imageWidth, imageHeight);
                case "LEFT" -> gc.drawImage(idleImage, imageWidth * 3, 0, imageWidth, imageHeight,
                        positionX, positionY, imageWidth, imageHeight);
                default -> throw new RuntimeException();
            }
        }
        // else draw the correct walking spite corresponding to the current moving direction
        else {
            gc.drawImage(walkingImage, index * imageWidth, 0, imageWidth, imageHeight, positionX, positionY, imageWidth, imageHeight);
        }
    }

    // Updates the X's coordinate of the character.
    // The condition for valid position will change soon
    // when I add some code handling collision.
    public void setPositionX(double positionX) {
        if (positionX >= 0) {
            this.positionX = positionX;
        }
    }
    // the same goes for the Y's coordinate.
    public void setPositionY(double positionY) {
        if (positionY >= 0) {
            this.positionY = positionY;
        }
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void addVelocity(double dX, double dY) {
        velocityX += dX;
        velocityY += dY;
    }
}
