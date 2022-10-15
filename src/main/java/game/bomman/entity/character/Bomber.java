package game.bomman.entity.character;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.ActivatedBomb;
import game.bomman.entity.immobileEntity.Brick;
import game.bomman.entity.immobileEntity.Portal;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Bomber extends Character {
    public static final double WIDTH = 42;
    public static final double HEIGHT = 48;
    private static final double WALKING_SPRITE_DURATION = 0.15f;
    private static final int N_SPRITES_PER_DIRECTION = 4;
    private static final double LEVEL_UP_SPRITE_DURATION = 0.2f;
    private static final int N_LEVEL_UP_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.15;
    private static final int N_DYING_SPRITES = 11;
    private double levelUpTimer = 0;
    private int levelUpFrameIndex = 0;
    private double dyingtimer = 0;
    private int dyingFrameIndex = 0;
    private int facingDirectionIndex = 2;
    private int padding = 0;
    private boolean isMoving = false;
    private boolean exited = false;
    private static Image bomberWalking;
    private static Image bomberStanding;
    private static Image bomberDying;
    private static Image bomberLevelUp;
    private int numOfLives;
    private int numOfBombs;
    private Stack<String> commandStack = new Stack<>();

    public Bomber(Map map, double targetMinX, double targetMinY) {
        this.map = map;
        this.newLoadingX = Entity.SIDE;
        this.newLoadingY = Entity.SIDE;
        this.positionOnMapX = 1;
        this.positionOnMapY = 1;
        this.speed = 200;
        this.numOfLives = 3;
        this.numOfBombs = 2;
        gc.drawImage(bomberStanding, 0, 0, WIDTH, HEIGHT, targetMinX, targetMinY, WIDTH, HEIGHT);
        initHitBox(targetMinX, targetMinY, WIDTH, HEIGHT);
    }

    static {
        try {
            bomberStanding = loadImage(IMAGES_PATH + "/player/idle.png") ;
            bomberWalking = loadImage(IMAGES_PATH + "/player/walking.png");
            bomberDying = loadImage(IMAGES_PATH + "/player/die@11.png");
            bomberLevelUp = loadImage(IMAGES_PATH + "/player/white@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void levelUp(double elapsedTime) {
        levelUpTimer += elapsedTime;
        if (levelUpTimer >= LEVEL_UP_SPRITE_DURATION) {
            levelUpTimer = 0;
            ++levelUpFrameIndex;
            if (levelUpFrameIndex == N_LEVEL_UP_SPRITES) {
                exited = true;
            }
        }
    }

    @Override
    public void update(double elapsedTime) {

        if (commandStack.empty()) {
            isMoving = false;
            return;
        }
        isMoving = true;

        /// Update sprite timer.
        timer += elapsedTime;
        if (timer >= WALKING_SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITES_PER_DIRECTION) {
                frameIndex = 0;
            }
        }

        /// Update bomber's position.
        updatePosition(elapsedTime);
    }

    private void updatePosition(double elapsedTime) {

        Cell currentCell = map.getCell(positionOnMapX, positionOnMapY);
        double cellMinX = currentCell.getHitBox().getMinX();
        double cellMinY = currentCell.getHitBox().getMinY();

        double currentX = hitBox.getMinX();
        double currentY = hitBox.getMinY();

        String command = commandStack.peek();
        boolean isBuffering = (command.charAt(0) == '1');

        switch (command.charAt(1)) {
            case 'u' -> {
                padding = N_SPRITES_PER_DIRECTION * 0;

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

                /// Test the breaking of bricks.
                if (aheadCell.isBlocking() && currentY <= cellMinY) {
                    if (aheadCell.getRawConfig() == '*') {
                        Brick brick = (Brick) aheadCell.getEntity(0);
                        brick.explode();
                    }
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

                /// Test the breaking of bricks.
                if (aheadCell.isBlocking() && currentY >= cellMinY) {
                    if (aheadCell.getRawConfig() == '*') {
                        Brick brick = (Brick) aheadCell.getEntity(0);
                        brick.explode();
                    }
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

                /// Test the breaking of bricks.
                if (aheadCell.isBlocking() && currentX <= cellMinX + 3) {
                    if (aheadCell.getRawConfig() == '*') {
                        Brick brick = (Brick) aheadCell.getEntity(0);
                        brick.explode();
                    }
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

                /// Test the sparking of bricks.
                if (aheadCell.isBlocking() && currentX >= cellMinX + 3) {
                    if (aheadCell.getRawConfig() == '*') {
                        Brick brick = (Brick) aheadCell.getEntity(0);
                        brick.explode();
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        if (exited == true) {
            return;
        }

        hitBox.setMinX(newLoadingX);
        hitBox.setMinY(newLoadingY);

        if (isMoving) {
            gc.drawImage(bomberWalking,
                    (frameIndex + padding) * WIDTH, 0, WIDTH, HEIGHT,
                    newLoadingX, newLoadingY, WIDTH, HEIGHT);
        } else {
            gc.drawImage(bomberStanding,
                    WIDTH * facingDirectionIndex, 0, WIDTH, HEIGHT,
                    hitBox.getMinX(), hitBox.getMinY(), WIDTH, HEIGHT);
        }
    }

    @Override
    public void layingBomb() {
        if (numOfBombs == 0) {
            /// The game is going to be over, all enemies killed.
            Portal portal = InteractionHandler.getPortal();
            if (InteractionHandler.portalAppeared()) {
                portal.activate();
            } else {
                Cell portalCell = map.getCell(portal.getPosOnMapX(), portal.getPosOnMapY());
                Brick brick = (Brick) portalCell.getEntity(0);
                brick.spark();
            }
            return;
        }
        --numOfBombs;

        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        ActivatedBomb newBomb = new ActivatedBomb(
                map, this,
                thisCell.getLoadingPositionX(),
                thisCell.getLoadingPositionY(),
                positionOnMapX,
                positionOnMapY
        );
        thisCell.addEntity(newBomb);
        InteractionHandler.addImmobileEntity(newBomb);
    }

    public void retakeBomb() {
        ++numOfBombs;
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
        facingDirectionIndex = 2;
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
        facingDirectionIndex = 3;
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
        facingDirectionIndex = 1;
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
        facingDirectionIndex = 0;
        System.out.println("reMoved up.");
    }
}
