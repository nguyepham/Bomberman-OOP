package game.bomman.entity.character.enemy;

import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Random;

public class Balloon extends Enemy {
    private static final Image balloonImage;
    private static final double FIXED_SPEED = 100;
    private static final int N_SPRITES = 3;
    private static final double DURATION_PER_SPRITE = .2;
    private static final char[] DIRECTIONS = {'u', 'd', 'l', 'r'};

    private GraphicsContext gc;
    private double timer = 0;
    private char currentDirection = 'u';
    private static final Random rand = new Random();

    static {
        try {
            balloonImage = loadImage(IMAGES_PATH + "/enemy/priest@3.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Balloon(int positionOnMapX, int positionOnMapY, GraphicsContext gc, Map map) {
        this.positionOnMapX = positionOnMapX;
        this.positionOnMapY = positionOnMapY;
        this.gc = gc;
        this.map = map;
        newLoadingX = positionOnMapX * SIDE;
        newLoadingY = positionOnMapY * SIDE;
        initHitBox(newLoadingX, newLoadingY, SIDE, SIDE);
    }

    @Override
    public void update(double elapsedTime) {
        updateSprite(elapsedTime);
        updatePosition(elapsedTime);
    }

    private void updateSprite(double elapsedTime) {
        timer += elapsedTime;
        if (timer >= DURATION_PER_SPRITE) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITES) {
                frameIndex = 0;
            }
        }
    }

    /**
     * Cách di chuyển của Balloon:
     * 1. Ban đầu xác định một hướng đi
     * 2. Mỗi lần cập nhật vị trí:
     *     2.a. nếu đi được tiếp theo hướng cũ thì đi tiếp
     *     2.b. nếu không thì tìm một hướng đi bất kỳ không bị chặn
     *          và đi tiếp theo hướng ấy.
     * 3. Vận tốc di chuyển theo mỗi hướng là cố định
     * @param elapsedTime thời gian giữa 2 nhịp đồng hồ
     */
    private void updatePosition(double elapsedTime) {
        Cell aheadCell = null;
        for (int i = 0; i < 10; ++i) {
            switch (currentDirection) {
                case 'u' -> aheadCell = map.getCell(positionOnMapX, positionOnMapY - 1);
                case 'd' -> aheadCell = map.getCell(positionOnMapX, positionOnMapY + 1);
                case 'l' -> aheadCell = map.getCell(positionOnMapX - 1, positionOnMapY);
                case 'r' -> aheadCell = map.getCell(positionOnMapX + 1, positionOnMapY);
                default -> throw invalidCurrentDirectionError();
            }
            if (aheadCell.isBlocking()) {
                currentDirection = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
            }
            else {
                break;
            }
        }

        if (aheadCell.isBlocking()) return;

        switch (currentDirection) {
            case 'u' -> newLoadingY -= elapsedTime * FIXED_SPEED;
            case 'd' -> newLoadingY += elapsedTime * FIXED_SPEED;
            case 'l' -> newLoadingX -= elapsedTime * FIXED_SPEED;
            case 'r' -> newLoadingX += elapsedTime * FIXED_SPEED;
            default -> throw invalidCurrentDirectionError();
        }

        hitBox.setMinX(newLoadingX);
        hitBox.setMinY(newLoadingY);
        if (this.gotInto(aheadCell)) {
            positionOnMapX = aheadCell.getPosOnMapX();
            positionOnMapY = aheadCell.getPosOnMapY();
            System.out.println(positionOnMapX + " " + positionOnMapY);
        }
    }

    private RuntimeException invalidCurrentDirectionError() {
        return new RuntimeException("currentDirection is set to an invalid value: " + currentDirection);
    }

    @Override
    public void draw() {
        gc.drawImage(balloonImage, frameIndex * SIDE, 0, SIDE, SIDE,
                newLoadingX, newLoadingY, SIDE, SIDE);
    }

    @Override
    public void layingBomb() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void removeDown() {

    }

    @Override
    public void removeLeft() {

    }

    @Override
    public void removeRight() {

    }

    @Override
    public void removeUp() {

    }
}
