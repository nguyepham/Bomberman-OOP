package game.bomman.entity.immobileEntity;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.Bomber;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class Bomb extends ImmobileEntity {
    private static final Image image;
    private static Bomber bomber;
    private double countdownTimer = 2.0f;
    private static final double SPRITE_DURATION = 0.15;
    private static final int N_SPRITES = 4;

    static {
        try {
            image = loadImage(IMAGES_PATH + "/bomb@4.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Bomb(Map map_, Bomber bomber_, double loadingPosX, double loadingPosY, int posOnMapX, int posOnMapY) {
        map = map_;
        bomber = bomber_;
        positionOnMapX = posOnMapX;
        positionOnMapY = posOnMapY;
        map.getCell(posOnMapX, posOnMapY).setBlocking(true);
        initHitBox(loadingPosX, loadingPosY, SIDE, SIDE);
    }

    private void countDown(double elapsedTime) {
        countdownTimer -= elapsedTime;
        if (countdownTimer <= 0) {
            explode();
        }
    }

    private void explode() {
        Cell thisCell = map.getCell(positionOnMapX, positionOnMapY);
        int flameLength = bomber.getFlameLength();

        thisCell.setBlocking(false);
        bomber.retakeBomb();
        removeFromCell(positionOnMapX, positionOnMapY);
        InteractionHandler.removeImmobileEntity(this);

        createFlame('c', thisCell);

        for (int i = 1; i <= flameLength; ++i) {
            Cell nextCell = map.getCell(positionOnMapX, positionOnMapY - i);
            if (nextCell.isBlocking() == true) {
                if (nextCell.getBrick() != null) {
                    nextCell.getBrick().explode();
                } else if (nextCell.getBomb() != null) {
                    nextCell.getBomb().explode();
                }
                break;
            }
            if (i == flameLength) {
                createFlame('u', nextCell);
            } else {
                createFlame('v', nextCell);
            }
        }
        for (int i = 1; i <= flameLength; ++i) {
            Cell nextCell = map.getCell(positionOnMapX + i, positionOnMapY);
            if (nextCell.isBlocking() == true) {
                if (nextCell.getBrick() != null) {
                    nextCell.getBrick().explode();
                } else if (nextCell.getBomb() != null) {
                    nextCell.getBomb().explode();
                }
                break;
            }
            if (i == flameLength) {
                createFlame('r', nextCell);
            } else {
                createFlame('h', nextCell);
            }
        }

        for (int i = 1; i <= flameLength; ++i) {
            Cell nextCell = map.getCell(positionOnMapX, positionOnMapY + i);
            if (nextCell.isBlocking() == true) {
                if (nextCell.getBrick() != null) {
                    nextCell.getBrick().explode();
                } else if (nextCell.getBomb() != null) {
                    nextCell.getBomb().explode();
                }
                break;
            }
            if (i == flameLength) {
                createFlame('d', nextCell);
            } else {
                createFlame('v', nextCell);
            }
        }

        for (int i = 1; i <= flameLength; ++i) {
            Cell nextCell = map.getCell(positionOnMapX - i, positionOnMapY);
            if (nextCell.isBlocking() == true) {
                if (nextCell.getBrick() != null) {
                    nextCell.getBrick().explode();
                } else if (nextCell.getBomb() != null) {
                    nextCell.getBomb().explode();
                }
                break;
            }
            if (i == flameLength) {
                createFlame('l', nextCell);
            } else {
                createFlame('h', nextCell);
            }
        }
    }

    private void createFlame(char label, Cell thisCell) {
        Flame newFlame = new Flame(label, map,
                thisCell.getLoadingPositionX(), thisCell.getLoadingPositionY(),
                thisCell.getPosOnMapX(), thisCell.getPosOnMapY());
        thisCell.addEntity(newFlame);
        InteractionHandler.addImmobileEntity(newFlame);
    }

    @Override
    public void interactWith(Entity other) {

    }

    @Override
    public void update(double elapsedTime) {

        timer += elapsedTime;
        if (timer >= SPRITE_DURATION) {
            timer = 0;
            ++frameIndex;
            if (frameIndex == N_SPRITES) {
                frameIndex = 0;
            }
        }
        countDown(elapsedTime);
    }

    @Override
    public void draw() {
        double loadingPosX = hitBox.getMinX();
        double loadingPosY = hitBox.getMinY();

        gc.drawImage(image, SIDE * frameIndex, 0, SIDE, SIDE, loadingPosX, loadingPosY, SIDE, SIDE);
    }
}
