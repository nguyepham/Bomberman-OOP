package game.bomman.entity.character;

import game.bomman.entity.Entity;
import game.bomman.entity.HitBox;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Bomber extends Character {
    public static final double WIDTH = 42;
    public static final double HEIGHT = 48;
    // Duration per sprite.
    private static final double DURATION = 0.1;
    private static final int N_SPRITES_PER_DIRECTION = 4;
    private int index = 8;
    private char lastDirection = 'd';

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
            bomberWalking = loadImage(IMAGES_PATH + "/player/walking.png");
            bomberStanding = loadImage(IMAGES_PATH + "/player/idle.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double elapsedTime, double timeSinceStart) {
        Cell currentCell = map.getCell(positionOnMapX, positionOnMapY);
        double cellMinX = currentCell.getHitBox().getMinX();
        double cellMinY = currentCell.getHitBox().getMinY();

        double currentX = hitBox.getMinX();
        double currentY = hitBox.getMinY();

        /// Remember to check this after handling interaction between entities.
        if (commandStack.empty()) {
            gc.clearRect(SIDE, SIDE, SIDE * 30, SIDE * 12);
            switch (lastDirection) {
                case 'u' -> gc.drawImage(bomberStanding,
                        0, 0, WIDTH, HEIGHT,
                        currentX, currentY, WIDTH, HEIGHT);
                case 'r' -> gc.drawImage(bomberStanding,
                        WIDTH, 0, WIDTH, HEIGHT,
                        currentX, currentY, WIDTH, HEIGHT);
                case 'd' -> gc.drawImage(bomberStanding,
                        WIDTH * 2, 0, WIDTH, HEIGHT,
                        currentX, currentY, WIDTH, HEIGHT);
                case 'l' -> gc.drawImage(bomberStanding,
                        WIDTH * 3, 0, WIDTH, HEIGHT,
                        currentX, currentY, WIDTH, HEIGHT);
            }

            return;
        }
        System.out.println("Position on map:");
        System.out.println(positionOnMapX + " " +  positionOnMapY +  "\n");

        System.out.println("\n");
        for (String s : commandStack) {
            System.out.println(s);
        }

        /** Update the position of bomber **/
        /// Handle the input commands.
        String command = commandStack.peek();
        boolean isBuffering = (command.charAt(0) == '1');

        int frameIndex = (int) (timeSinceStart % (DURATION * N_SPRITES_PER_DIRECTION) / DURATION);
        int padding = 0;
        //lastDirection = command.charAt(1);

        switch (command.charAt(1)) {
            case 'u' -> {
                Cell aheadCell = map.getCell(positionOnMapX, positionOnMapY - 1);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    --positionOnMapY;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
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
                padding = N_SPRITES_PER_DIRECTION * 2;

                Cell aheadCell = map.getCell(positionOnMapX, positionOnMapY + 1);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    ++positionOnMapY;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
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
                padding = N_SPRITES_PER_DIRECTION * 3;

                Cell aheadCell = map.getCell(positionOnMapX - 1, positionOnMapY);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    --positionOnMapX;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
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
                padding = N_SPRITES_PER_DIRECTION;

                Cell aheadCell = map.getCell(positionOnMapX + 1, positionOnMapY);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    ++positionOnMapX;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
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

        index = padding + frameIndex;

        /// Reload character position.
        moveTo(newLoadingX, newLoadingY);
    }

    @Override
    public void moveTo(double newX, double newY) {
        gc.clearRect(SIDE, SIDE, SIDE * 30, SIDE * 12);
        hitBox.setMinX(newX);
        hitBox.setMinY(newY);
        gc.drawImage(bomberWalking,
                index * WIDTH, 0, WIDTH, HEIGHT,
                newX, newY, WIDTH, HEIGHT);
    }

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
        lastDirection = 'd';
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
        lastDirection = 'l';
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
        lastDirection = 'r';
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
        lastDirection = 'u';
        System.out.println("reMoved up.");
    }
}
