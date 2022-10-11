package game.bomman.entity.character;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Bomber extends Character {
    public static final double WIDTH = 42;
    public static final double HEIGHT = 48;
    private static Image bomberWalking;
    private static Image bomberStanding;
    private int numOfLife;
    private Stack<String> commandStack = new Stack<>();

    public Bomber(Map map, double targetMinX, double targetMinY, GraphicsContext gc) {
        this.map = map;
        this.newLoadingX = Entity.SIDE;
        this.newLoadingY = Entity.SIDE;
        this.positionOnMapX = 1;
        this.positionOnMapY = 1;
        this.speed = 200;
        this.numOfLife =3;
        this.gc = gc;
        this.gc.drawImage(bomberStanding, 0, 0, WIDTH, HEIGHT, targetMinX, targetMinY, WIDTH, HEIGHT);
        this.hitBox = new HitBox(targetMinX, targetMinY, WIDTH, HEIGHT);
    }

    public Image getWalkingImage() { return bomberWalking; }

    public Image getStandingImage() { return bomberStanding; }

    static {
        try {
            bomberWalking = loadImage(IMAGES_PATH + "/player/walkingDown.png");
            bomberStanding = loadImage(IMAGES_PATH + "/player/standingDown.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double elapsedTime) {
        Cell currentCell = map.getCell(positionOnMapX, positionOnMapY);
        double cellMinX = currentCell.getHitBox().getMinX();
        double cellMinY = currentCell.getHitBox().getMinY();

        /// Remember to check this after handling interaction between entities.
        if (commandStack.empty()) {
//            System.out.println("Empty.");
            return;
        }
        System.out.println("Position on map:");
        System.out.println(positionOnMapX + " " +  positionOnMapY +  "\n");

        double currentX = hitBox.getMinX();
        double currentY = hitBox.getMinY();

        System.out.println("\n");
        for (int i = 0; i < commandStack.size(); ++i) {
            System.out.println(commandStack.get(i));
        }

        /** Update the position of bomber **/
        /// Handle the input commands.
        String command = commandStack.peek();
        boolean isBuffering = (command.charAt(0) == '1');

        switch (command.charAt(1)) {

            case 'u' -> {
                Cell aheadCell = map.getCell(positionOnMapX, positionOnMapY - 1);

                if (this.gotInto(currentCell) == false && this.gotInto(aheadCell)) {
                    --positionOnMapY;
                    break;
                }
//                if (isBuffering && currentY <= cellMinY) {
//                    isBuffering = false;
//                    commandStack.pop();
//                    break;
//                }

                if (aheadCell.isBlocking() == false && isBuffering == false) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY - 1).isBlocking()
                            && currentX > cellMinX + 3) {
                        commandStack.add("1left");
                        break;
                    }

                    if (map.getCell(positionOnMapX - 1, positionOnMapY - 1).isBlocking()
                            && currentX < cellMinX + 3) {
                        commandStack.add("1right");
                        break;
                    }
                }

                newLoadingX = currentX;
                newLoadingY = currentY - speed * elapsedTime;

                /// Character blocked.
                if (aheadCell.isBlocking() || isBuffering) {
                    if (newLoadingY < cellMinY) {
                        newLoadingY = cellMinY;
                    }
                }
                if (isBuffering && currentY <= cellMinY) {
                    System.out.println("Up buffer removed.");
                    commandStack.pop();
                }
            }
            case 'd' -> {
                Cell aheadCell = map.getCell(positionOnMapX, positionOnMapY + 1);

                if (this.gotInto(currentCell) == false && this.gotInto(aheadCell)) {
                    ++positionOnMapY;
                    break;
                }
//                if (isBuffering && currentY >= cellMinY) {
//                    isBuffering = false;
//                    commandStack.pop();
//                    break;
//                }

                if (aheadCell.isBlocking() == false && isBuffering == false) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY + 1).isBlocking()
                            && currentX > cellMinX + 3) {
                        commandStack.add("1left");
                        break;
                    }
                    if (map.getCell(positionOnMapX - 1, positionOnMapY + 1).isBlocking()
                            && currentX < cellMinX + 3) {
                        commandStack.add("1right");
                        break;
                    }
                }

                newLoadingX = currentX;
                newLoadingY = currentY + speed * elapsedTime;
                /// Character blocked.
                if (aheadCell.isBlocking() || isBuffering) {
                    if (newLoadingY > cellMinY) {
                        newLoadingY = cellMinY;
                    }
                }
                if (isBuffering && currentY >= cellMinY) {
                    System.out.println("Down buffer removed.");
                    commandStack.pop();
                }
            }
            case 'l' -> {
                Cell aheadCell = map.getCell(positionOnMapX - 1, positionOnMapY);

                if (this.gotInto(currentCell) == false && this.gotInto(aheadCell)) {
                    --positionOnMapX;
                    break;
                }
//                if (isBuffering && currentX <= cellMinX + 3) {
//                    isBuffering = false;
//                    commandStack.pop();
//                    break;
//                }

                if (aheadCell.isBlocking() == false && isBuffering == false) {
                    if (map.getCell(positionOnMapX - 1, positionOnMapY - 1).isBlocking()
                            && currentY < cellMinY) {
                        commandStack.add("1down");
                        break;
                    }
                    if (map.getCell(positionOnMapX - 1, positionOnMapY + 1).isBlocking()
                            && currentY > cellMinY) {
                        commandStack.add("1up");
                        break;
                    }
                }

                newLoadingX = currentX - speed * elapsedTime;
                newLoadingY = currentY;

                /// Character blocked.
                if (aheadCell.isBlocking() || isBuffering) {
                    System.out.println("Left blocked.");
                    if (newLoadingX < cellMinX + 3) {
                        newLoadingX = cellMinX + 3;
                    }
                }
                if (isBuffering && currentX <= cellMinX + 3) {
                    System.out.println("Left buffer removed.");
                    commandStack.pop();
                }
            }
            case 'r' -> {
                Cell aheadCell = map.getCell(positionOnMapX + 1, positionOnMapY);

                if (this.gotInto(currentCell) == false && this.gotInto(aheadCell)) {
                    ++positionOnMapX;
                    break;
                }
//                if (isBuffering && currentX >= cellMinX + 3) {
//                    isBuffering = false;
//                    commandStack.pop();
//                    break;
//                }

                if (aheadCell.isBlocking() == false && isBuffering == false) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY - 1).isBlocking()
                            && currentY < cellMinY) {
                        commandStack.add("1down");
                        break;
                    }
                    if (map.getCell(positionOnMapX + 1, positionOnMapY + 1).isBlocking()
                            && currentY > cellMinY) {
                        commandStack.add("1up");
                        break;
                    }
                }

                newLoadingX = currentX + speed * elapsedTime;
                newLoadingY = currentY;

                /// Character blocked.
                if (aheadCell.isBlocking() || isBuffering) {
                    if (newLoadingX > cellMinX + 3) {
                        newLoadingX = cellMinX + 3;
                    }
                }
                if (isBuffering && currentX >= cellMinX + 3) {
                    System.out.println("Right buffer removed.");
                    commandStack.pop();
                }
            }
        }
        /// Reload character position.
        moveTo(newLoadingX, newLoadingY);
    }

    @Override
    public void moveTo(double newX, double newY) {
        gc.clearRect(SIDE, SIDE, SIDE * 30, SIDE * 12);
        hitBox.setMinX(newX);
        hitBox.setMinY(newY);
        gc.drawImage(bomberStanding, newX, newY, WIDTH, HEIGHT);
    }

//    public void update(double elapsedTime) {
//        handleCommands(elapsedTime);
//        moveTo(newLoadingX, newLoadingY);
//    }

    @Override
    public void moveDown() {
        if (commandStack.empty() || commandStack.peek().charAt(1) != 'd') {
            commandStack.push("0down");
            System.out.println("Moved down.");
        }
    }

    @Override
    public void moveLeft() {
        if (commandStack.empty() || commandStack.peek().charAt(1) != 'l') {
            commandStack.push("0left");
            System.out.println("Moved left.");
        }
    }

    @Override
    public void moveRight() {
        if (commandStack.empty() || commandStack.peek().charAt(1) != 'r') {
            commandStack.push("0right");
            System.out.println("Moved right.");
        }
    }

    @Override
    public void moveUp() {
        if (commandStack.empty() || commandStack.peek().charAt(1) != 'u') {
            commandStack.push("0up");
            System.out.println("Moved up.");
        }
    }

    @Override
    public void removeDown() {
        for (int i = commandStack.size() - 1; i >= 0; --i) {
            if (commandStack.get(i).equals("0down")) {
                commandStack.remove(i);
                break;
            }
        }
        System.out.println("reMoved down.");
    }

    @Override
    public void removeLeft() {
        for (int i = commandStack.size() - 1; i >= 0; --i) {
            if (commandStack.get(i).equals("0left")) {
                commandStack.remove(i);
                break;
            }
        }
        System.out.println("reMoved left.");
    }

    @Override
    public void removeRight() {
        for (int i = commandStack.size() - 1; i >= 0; --i) {
            if (commandStack.get(i).equals("0right")) {
                commandStack.remove(i);
                break;
            }
        }
        System.out.println("reMoved right.");
    }

    @Override
    public void removeUp() {
        for (int i = commandStack.size() - 1; i >= 0; --i) {
            if (commandStack.get(i).equals("0up")) {
                commandStack.remove(i);
                break;
            }
        }
        System.out.println("reMoved up.");

    }

    // Number of sprites for each direction in the Image
    // describing the walking motion of the character.
//    private static final int nWalkingSpritesPerDirection = 4;
//    private static final double duration = 0.1;
//    // UP RIGHT DOWN LEFT
//    public static final Image walkingImage;
//    public static final Image idleImage;
//    static {
//        try {
//            walkingImage = loadImage(IMAGES_PATH + "/player/walking@16.png");
//            idleImage = loadImage(IMAGES_PATH + "/player/idle@4.png");
//            imageWidth = walkingImage.getWidth() / 16;
//            imageHeight = walkingImage.getHeight();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static final double imageWidth;
//    private static final double imageHeight;
//
//    // In the beginning
//    // the character stands face to face with the user.
//    private String currentDirection = "DOWN";
//    private int index = 8;
//
//    /**
//     * Updates the Loading of the character.
//     *
//     * @param elapsedTime the time passed between the current
//     *                    game iteration with the previous one.
//     * @param timeSinceStart the time passed since the game started.
//     * @param direction the direction in which the character moves.
//     */
//    public void update(double elapsedTime, double timeSinceStart, String direction) {
//        setPositionX(positionX + elapsedTime * velocityX);
//        setPositionY(positionY + elapsedTime * velocityY);
//
//        // set the index of the sprite to be drawn to screen
//        if (!direction.equals(NOT_MOVING)) {
//            // the index of the frame of the direction in which we are moving at this moment in time.
//            int frameIndex = (int) ((timeSinceStart % (nWalkingSpritesPerDirection * duration)) / duration);
//            // the padding which we add to frame index to get the correct index
//            // of the sprite that we need to draw in the overall walking image
//            int padding = 0;
//            switch (direction) {
//                case "UP" -> padding += 0;
//                case "RIGHT" -> padding += nWalkingSpritesPerDirection;
//                case "DOWN" -> padding += nWalkingSpritesPerDirection * 2;
//                case "LEFT" -> padding += nWalkingSpritesPerDirection * 3;
//            }
//            index = padding + frameIndex;
//
//            currentDirection = direction;
//        }
//    }
//
//    // Renders the character to scene scaled to the dimension
//    // specified by the arguments width and height.
//    public void render(GraphicsContext gc) {
//        // if currently not moving then draw the correct idle sprite
//        if (velocityX == 0 && velocityY == 0) {
//            switch (currentDirection) {
//                case "UP" -> gc.drawImage(idleImage, 0, 0, imageWidth, imageHeight,
//                        positionX, positionY, imageWidth, imageHeight);
//                case "RIGHT" -> gc.drawImage(idleImage, imageWidth, 0, imageWidth, imageHeight,
//                        positionX, positionY, imageWidth, imageHeight);
//                case "DOWN" -> gc.drawImage(idleImage, imageWidth * 2, 0, imageWidth, imageHeight,
//                        positionX, positionY, imageWidth, imageHeight);
//                case "LEFT" -> gc.drawImage(idleImage, imageWidth * 3, 0, imageWidth, imageHeight,
//                        positionX, positionY, imageWidth, imageHeight);
//                default -> throw new RuntimeException();
//            }
//        }
//        // else draw the correct walking spite corresponding to the current moving direction
//        else {
//            gc.drawImage(walkingImage, index * imageWidth, 0, imageWidth, imageHeight, positionX, positionY, imageWidth, imageHeight);
//        }
//    }
//
//    // Updates the X's coordinate of the character.
//    // The condition for valid position will change soon
//    // when I add some code handling collision.
//    public void setPositionX(double positionX) {
//        if (positionX >= 0) {
//            this.positionX = positionX;
//        }
//    }
//    // the same goes for the Y's coordinate.
//    public void setPositionY(double positionY) {
//        if (positionY >= 0) {
//            this.positionY = positionY;
//        }
//    }
//
//    public void setVelocity(double velocityX, double velocityY) {
//        this.velocityX = velocityX;
//        this.velocityY = velocityY;
//    }
//
//    public void addVelocity(double dX, double dY) {
//        velocityX += dX;
//        velocityY += dY;
//    }
}
