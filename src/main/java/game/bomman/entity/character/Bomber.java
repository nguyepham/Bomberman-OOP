package game.bomman.entity.character;

import game.bomman.entity.stuff.Grass;
import game.bomman.entity.stuff.Stuff;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

// Todo:
// 1. Let position X, Y represent the position of the box
// 2. Render bounding box and try and error to find the correct dimension for the box

public class Bomber extends Character {
    // number of sprites for each direction in the Image
    // describing the walking motion of the character
    private static final int nWalkingSpritesPerDirection = 4;
    private static final double duration = 0.1;
    // UP RIGHT DOWN LEFT
    private static final Image walkingImage;
    private static final Image idleImage;
    static {
        try {
            walkingImage = loadImage(IMAGES_PATH + "/player/walking@16.png");
            idleImage = loadImage(IMAGES_PATH + "/player/idle@4.png");
            spriteWidth = walkingImage.getWidth() / 16;
            spriteHeight = walkingImage.getHeight();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static final double spriteWidth;
    private static final double spriteHeight;

    private static final double boundaryWidth = 25;
    private static final double boundaryHeight = 25;
    private static final double paddingBottom = 5;
    private static final double paddingTop = spriteHeight - boundaryHeight - paddingBottom;
    private static final double paddingLeft = (spriteWidth - boundaryWidth - 1) / 2;

    // In the beginning
    // the character stands face to face with the user.
    private String currentDirection = "DOWN";
    private int index = 8;

    private final double speed = 100;

    public Bomber(int row, int col) {
        positionX = col * Stuff.side + (Stuff.side - boundaryWidth) / 2;
        positionY = row * Stuff.side + (Stuff.side - boundaryHeight) / 2;
    }

    /**
     * Updates the position of the character.
     *
     * @param elapsedTime the time passed between the current
     *                    game iteration with the previous one.
     * @param timeSinceStart the time passed since the game started.
     * @param direction the direction in which the character moves.
     */
    public void update(double elapsedTime, double timeSinceStart, String direction, Stuff[][] entities) {
        // set the index of the sprite to be drawn to screen
        // the index of the frame of the direction in which we are moving at this moment in time.
        int frameIndex = (int) ((timeSinceStart % (nWalkingSpritesPerDirection * duration)) / duration);
        // the padding which we add to frame index to get the correct index
        // of the sprite that we need to draw in the overall walking image
        int padding = 0;
        switch (direction) {
            case "UP" -> {
                padding += 0;
                velocityY -= speed;
            }
            case "RIGHT" -> {
                padding += nWalkingSpritesPerDirection;
                velocityX += speed;
            }
            case "DOWN" -> {
                padding += nWalkingSpritesPerDirection * 2;
                velocityY += speed;
            }
            case "LEFT" -> {
                padding += nWalkingSpritesPerDirection * 3;
                velocityX -= speed;
            }
        }
        index = padding + frameIndex;

        currentDirection = direction;

        setPosition(positionX + elapsedTime * velocityX,
                positionY + elapsedTime * velocityY, entities);
    }

    private void setPosition(double positionX, double positionY, Stuff[][] entities) {
        int topLeftX = (int) (positionX / Stuff.side);
        int topLeftY = (int) (positionY / Stuff.side);
        int bottomRightX = (int) ((positionX + boundaryWidth) / Stuff.side);
        int bottomRightY = (int) ((positionY + boundaryHeight) / Stuff.side);

        for (int y = topLeftY; y <= bottomRightY; ++y) {
            for (int x = topLeftX; x <= bottomRightX; ++x) {
                if (!(entities[y][x] instanceof Grass) &&
                        new Rectangle2D(positionX, positionY, boundaryWidth, boundaryHeight).intersects(entities[y][x].getBoundary())) {
                    return;
                }
            }
        }

        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Renders the character to scene scaled to the dimension
    // specified by the arguments width and height.
    public void render(GraphicsContext gc) {
        double imageX = 0;
        Image outputImage;
        // if currently not moving then draw the correct idle sprite
        if (velocityX == 0 && velocityY == 0) {
            switch (currentDirection) {
                case "UP" -> imageX = 0;
                case "RIGHT" -> imageX = spriteWidth;
                case "DOWN" -> imageX = spriteWidth * 2;
                case "LEFT" -> imageX = spriteWidth * 3;
            }
            outputImage = idleImage;
        }
        // else draw the correct walking spite corresponding to the current moving direction
        else {
            imageX = spriteWidth * index;
            outputImage = walkingImage;
        }
        gc.drawImage(outputImage, imageX, 0, spriteWidth, spriteHeight,
                positionX - paddingLeft, positionY - paddingTop, spriteWidth, spriteHeight);
    }

    public void renderBoundingBox(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(positionX, positionY, boundaryWidth, boundaryHeight);
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, boundaryWidth, boundaryHeight);
    }

    public boolean intersects(Stuff stuff) {
        return this.getBoundary().intersects(stuff.getBoundary());
    }
}
