package game.bomman.entity.character;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.Flame;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.entity.immobileEntity.Bomb;
import game.bomman.entity.immobileEntity.Brick;
import game.bomman.entity.immobileEntity.Portal;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Bomber extends Character {
    public static final double WIDTH = 42;
    public static final double HEIGHT = 48;
    private static final double WALKING_SPRITE_DURATION = 0.15f;
    private static final int N_SPRITES_PER_DIRECTION = 4;
    private static final double LEVEL_UP_SPRITE_DURATION = 0.4f;
    private static final int N_LEVEL_UP_SPRITES = 4;
    private static final double DYING_SPRITE_DURATION = 0.15;
    private static final int N_DYING_SPRITES = 11;
    private double levelUpTimer = 0;
    private int levelUpFrameIndex = 0;
    private double dyingTimer = 0;
    private int dyingFrameIndex = 0;
    private int padding = 0;
    private boolean isMoving = false;
    private boolean exited = false;
    private boolean gotIntoPortal = false;
    private static final Image bomberWalking;
    private static final Image bomberStanding;
    private static final Image bomberDying;
    private static final Image bomberLevelUp;
    private int numOfLives;
    private int numOfBombs;
    private Stack<String> commandStack = new Stack<>();

    public Bomber(Map map) {
        this.map = map;
        this.positionOnMapX = 1;
        this.positionOnMapY = 1;
        this.speed = 200;
        this.numOfLives = 3;
        this.numOfBombs = 2;
        gc.drawImage(bomberStanding, 0, 0, WIDTH, HEIGHT, SIDE + 3.0f, SIDE, WIDTH, HEIGHT);
        initHitBox(SIDE + 3.0f, SIDE, WIDTH, HEIGHT);
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

    public void getIntoPortal() { gotIntoPortal = true; }

    @Override
    public void update(double elapsedTime) {

        if (isAlive == false) {
            dyingTimer += elapsedTime;
            dying();
            return;
        }

        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        /// Handle interaction between Bomber and other entities.
        InteractionHandler.handleInteraction(this, thisCell);

        if (gotIntoPortal == true) {
            levelUpTimer += elapsedTime;
            levelUp();
            return;
        }

        if (commandStack.empty()) {
            isMoving = false;
            return;
        }
        isMoving = true;

        /// Update walking sprite.
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
        Cell aheadCell = currentCell;
        double cellMinX = currentCell.getHitBox().getMinX();
        double cellMinY = currentCell.getHitBox().getMinY();

        double currentX = hitBox.getMinX();
        double currentY = hitBox.getMinY();

        String command = commandStack.peek();
        boolean isBuffering = (command.charAt(0) == '1');

        switch (command.charAt(1)) {
            case 'u' -> {
                padding = N_SPRITES_PER_DIRECTION * 0;

                aheadCell = map.getCell(positionOnMapX, positionOnMapY - 1);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    currentCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    --positionOnMapY;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY - 1).isBlocking()
                            && currentX > cellMinX + 3) {
                        commandStack.add("1left");
                        System.out.println("Left buffered.");
                        break;
                    }

                    if (map.getCell(positionOnMapX - 1, positionOnMapY - 1).isBlocking()
                            && currentX < cellMinX + 3) {
                        commandStack.add("1right");
                        System.out.println("Right buffered.");
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

                /// Test the explosion of bricks.
                if (aheadCell.isBlocking() && currentY <= cellMinY) {
                    if (aheadCell.getRawConfig() == '*' || aheadCell.getRawConfig() == 'x') {
                        Brick brick = aheadCell.getBrick();
                        brick.explode();
                    }
                }
            }
            case 'd' -> {
                padding = N_SPRITES_PER_DIRECTION * 2;

                aheadCell = map.getCell(positionOnMapX, positionOnMapY + 1);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    currentCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    ++positionOnMapY;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY + 1).isBlocking()
                            && currentX > cellMinX + 3) {
                        commandStack.add("1left");
                        System.out.println("Left buffered.");
                        break;
                    }
                    if (map.getCell(positionOnMapX - 1, positionOnMapY + 1).isBlocking()
                            && currentX < cellMinX + 3) {
                        commandStack.add("1right");
                        System.out.println("Right buffered.");
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

                /// Test the explosion of bricks.
                if (aheadCell.isBlocking() && currentY >= cellMinY) {
                    if (aheadCell.getRawConfig() == '*' || aheadCell.getRawConfig() == 'x') {
                        Brick brick = aheadCell.getBrick();
                        brick.explode();
                    }
                }
            }
            case 'l' -> {
                padding = N_SPRITES_PER_DIRECTION * 3;

                aheadCell = map.getCell(positionOnMapX - 1, positionOnMapY);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    currentCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    --positionOnMapX;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
                    if (map.getCell(positionOnMapX - 1, positionOnMapY - 1).isBlocking()
                            && currentY < cellMinY) {
                        commandStack.add("1down");
                        System.out.println("Down buffered.");
                        break;
                    }
                    if (map.getCell(positionOnMapX - 1, positionOnMapY + 1).isBlocking()
                            && currentY > cellMinY) {
                        commandStack.add("1up");
                        System.out.println("Up buffered.");
                        break;
                    }
                }

                newLoadingX = currentX - speed * elapsedTime;
                newLoadingY = currentY;

                /// Character blocked.
                if (aheadCell.isBlocking() || isBuffering) {
                    if (newLoadingX < cellMinX + 3) {
                        newLoadingX = cellMinX + 3;
                    }
                }
                if (isBuffering && currentX <= cellMinX + 3) {
                    System.out.println("Left buffer removed.");
                    commandStack.pop();
                }

                /// Test the explosion of bricks.
                if (aheadCell.isBlocking() && currentX <= cellMinX + 3) {
                    if (aheadCell.getRawConfig() == '*' || aheadCell.getRawConfig() == 'x') {
                        Brick brick = aheadCell.getBrick();
                        brick.explode();
                    }
                }
            }
            case 'r' -> {
                padding = N_SPRITES_PER_DIRECTION;

                aheadCell = map.getCell(positionOnMapX + 1, positionOnMapY);

                if (!this.gotInto(currentCell) && this.gotInto(aheadCell)) {
                    currentCell.removeEntity(this);
                    aheadCell.addEntity(this);
                    ++positionOnMapX;
                    break;
                }

                if (!aheadCell.isBlocking() && !isBuffering) {
                    if (map.getCell(positionOnMapX + 1, positionOnMapY - 1).isBlocking()
                            && currentY < cellMinY) {
                        commandStack.add("1down");
                        System.out.println("Down buffered.");
                        break;
                    }
                    if (map.getCell(positionOnMapX + 1, positionOnMapY + 1).isBlocking()
                            && currentY > cellMinY) {
                        commandStack.add("1up");
                        System.out.println("Up buffered.");
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

                /// Test the explosion of bricks.
                if (aheadCell.isBlocking() && currentX >= cellMinX + 3) {
                    if (aheadCell.getRawConfig() == '*' || aheadCell.getRawConfig() == 'x') {
                        Brick brick = aheadCell.getBrick();
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

        if (isAlive == false) {
            gc.drawImage(bomberDying,
                    53 * dyingFrameIndex, 0, 53, HEIGHT,
                    hitBox.getMinX(), hitBox.getMinY(), 53, HEIGHT);
            return;
        }

        if (gotIntoPortal == true) {
            gc.drawImage(bomberLevelUp,
                    WIDTH * levelUpFrameIndex, 0, WIDTH, HEIGHT,
                    hitBox.getMinX(), hitBox.getMinY(), WIDTH, HEIGHT);
            return;
        }

        if (isMoving) {
            hitBox.setMinX(newLoadingX);
            hitBox.setMinY(newLoadingY);

            gc.drawImage(bomberWalking,
                    (frameIndex + padding) * WIDTH, 0, WIDTH, HEIGHT,
                    newLoadingX, newLoadingY, WIDTH, HEIGHT);
        } else {
            gc.drawImage(bomberStanding,
                    WIDTH * facingDirectionIndex, 0, WIDTH, HEIGHT,
                    hitBox.getMinX(), hitBox.getMinY(), WIDTH, HEIGHT);
        }
    }

    private void levelUp() {
        if (levelUpTimer >= LEVEL_UP_SPRITE_DURATION) {
            levelUpTimer = 0;
            ++levelUpFrameIndex;
            if (levelUpFrameIndex == N_LEVEL_UP_SPRITES) {
                exited = true;
            }
        }
    }

    private void dying() {
        if (dyingTimer >= DYING_SPRITE_DURATION) {
            dyingTimer = 0;
            ++dyingFrameIndex;
            if (dyingFrameIndex == N_DYING_SPRITES) {
                this.respawn();
            }
        }
    }

    @Override
    protected void die() {
        super.die();
        isMoving = false;
        while (commandStack.isEmpty() == false) { commandStack.pop(); }
    }

    private void respawn() {
        --numOfLives;
        isAlive = true;
        hitBox.setMinX(SIDE);
        hitBox.setMinY(SIDE);
        newLoadingX = SIDE;
        newLoadingY = SIDE;
        positionOnMapX = 1;
        positionOnMapY = 1;
        isMoving = false;
        facingDirectionIndex = 2;
        dyingFrameIndex = 0;
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Portal) {
            Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
            if (Math.abs(hitBox.getCenterX() - thisCell.getHitBox().getCenterX()) <= 0.3
                    && Math.abs(hitBox.getCenterY() - thisCell.getHitBox().getCenterY()) <= 0.3) {
                Portal portal = (Portal) other;
                if (portal.isActivated() == true) {
                    this.getIntoPortal();
                }
            }
            return;
        }
        if (other instanceof Enemy || other instanceof Flame) {
            this.die();
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
                Brick brick = portalCell.getBrick();
                brick.spark();
            }
            return;
        }

        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);

        Portal portal = InteractionHandler.getPortal();
        if (portal != null) {
            if (portal.getLoadingPositionX() == thisCell.getLoadingPositionX()
                    && portal.getLoadingPositionY() == thisCell.getLoadingPositionY()) {
                return;
            }
        }

        if (thisCell.isBlocking()) {
            return;
        }

        --numOfBombs;

        Bomb newBomb = new Bomb(
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
