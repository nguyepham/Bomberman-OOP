package game.bomman.map;

import game.bomman.component.InteractionHandler;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.*;
import game.bomman.entity.immobileEntity.Brick;
import game.bomman.entity.immobileEntity.Portal;
import game.bomman.entity.item.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private int width;
    private int height;
    private Cell[][] cells;
    private Scanner mapScanner;

    public void readFromFile(String filePath) throws FileNotFoundException {
        FileInputStream mapFile = new FileInputStream(filePath);
        mapScanner = new Scanner(mapFile);

        int height = mapScanner.nextInt();
        int width = mapScanner.nextInt();

        mapScanner.useDelimiter("\n");
        mapScanner.next();
        initCells(width, height);
    }

    private void initCells(int width, int height) {
        cells = new Cell[height][];
        for (int i = 0; i < height; ++i) {
            cells[i] = new Cell[width];
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public Cell getCell(int i, int j) {
        return cells[j][i];
    }

    public void setUp(Canvas canvas) throws FileNotFoundException {
        canvas.setWidth(Entity.SIDE * width);
        canvas.setHeight(Entity.SIDE * height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int j = 0; j < height; ++j) {
            String thisRow = mapScanner.next();
            for (int i = 0; i < width; ++i) {
                char rawConfig = thisRow.charAt(i);
                cells[j][i] = new Cell(gc, i, j, rawConfig);
                Cell thisCell = cells[j][i];

                double posX = thisCell.getLoadingPositionX();
                double posY = thisCell.getLoadingPositionY();

                if (j == height - 1) {
                    thisCell.setWall();
                    gc.drawImage(thisCell.getSprite(), Entity.SIDE * 5, 0, Entity.SIDE, Entity.SIDE,
                            posX, posY, Entity.SIDE, Entity.SIDE);
                    continue;
                }

                if (j == 0) {
                    thisCell.setWall();
                    if (i == 0) {
                        gc.drawImage(thisCell.getSprite(), Entity.SIDE, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                        continue;
                    }
                    if (i == width - 1) {
                        gc.drawImage(thisCell.getSprite(), Entity.SIDE * 3, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                        continue;
                    }
                    gc.drawImage(thisCell.getSprite(), Entity.SIDE * 2, 0, Entity.SIDE, Entity.SIDE,
                            posX, posY, Entity.SIDE, Entity.SIDE);
                    continue;
                }

                if (i == 0 || i == width - 1) {
                    thisCell.setWall();
                    if (i == 0) {
                        gc.drawImage(thisCell.getSprite(), 0, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                    } else {
                        gc.drawImage(thisCell.getSprite(), Entity.SIDE * 4, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                    }
                    continue;
                }

                if (rawConfig == '#') {
                    thisCell.setSteel();
                    gc.drawImage(thisCell.getSprite(), posX, posY, Entity.SIDE, Entity.SIDE);
                } else {
                    thisCell.setGrass();
                    char bellowRawConfig = cells[j - 1][i].getRawConfig();

                    if (j > 0 && (bellowRawConfig == '#'
                            || bellowRawConfig == '*')
                            || bellowRawConfig == 'x'
                            || bellowRawConfig == 'f'
                            || bellowRawConfig == 's'
                            || bellowRawConfig == 'b'
                            || bellowRawConfig == 'l'
                            || bellowRawConfig == 'i'
                            || bellowRawConfig == 'j'
                            || bellowRawConfig == 'd') {
                        gc.drawImage(
                                thisCell.getSprite(),
                                Entity.SIDE, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE
                        );
                    } else {
                        gc.drawImage(
                                thisCell.getSprite(),
                                0, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE
                        );
                    }
                }
            }
        }

        mapScanner.close();
    }

    public void loadEntities() {
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                Cell thisCell = cells[j][i];
                char rawConfig = thisCell.getRawConfig();
                double posX = thisCell.getLoadingPositionX();
                double posY = thisCell.getLoadingPositionY();

                if (rawConfig == '*'
                        || rawConfig == 'x'
                        || rawConfig == 'f'
                        || rawConfig == 's'
                        || rawConfig == 'b'
                        || rawConfig == 'l'
                        || rawConfig == 'i'
                        || rawConfig == 'j'
                        || rawConfig == 'd') {
                    Brick newBrick = new Brick(this, posX, posY);
                    InteractionHandler.addImmobileEntity(newBrick);

                    switch (rawConfig) {
                        case 'x' -> {
                            /// Initialize the portal but not actually put it into the game yet.
                            Portal portal = new Portal(this, posX, posY);
                            InteractionHandler.addPortal(portal);
                        }
                        case 'f' -> {
                            FlameItem flame = new FlameItem(this, posX, posY);
                            InteractionHandler.addItem(flame);
                        }
                        case 'b' -> {
                            BombItem bomb = new BombItem(this, posX, posY);
                            InteractionHandler.addItem(bomb);
                        }
                        case 's' -> {
                            SpeedItem speed = new SpeedItem(this, posX, posY);
                            InteractionHandler.addItem(speed);
                        }
                        case 'l' -> {
                            LifeItem life = new LifeItem(this, posX, posY);
                            InteractionHandler.addItem(life);
                        }
                        case 'i' -> {
                            BombPassingItem bombPassing = new BombPassingItem(this, posX, posY);
                            InteractionHandler.addItem(bombPassing);
                        }
                        case 'j' -> {
                            BrickPassingItem brickPassing = new BrickPassingItem(this, posX, posY);
                            InteractionHandler.addItem(brickPassing);
                        }
                        case 'd' -> {
                            DetonatorItem detonator = new DetonatorItem(this, posX, posY);
                            InteractionHandler.addItem(detonator);
                        }
                    }
                    continue;
                }
                if (rawConfig == '1') {
                    Balloon balloon = new Balloon(this, posX, posY);
                    InteractionHandler.addEnemy(balloon);
                    continue;
                }
                if (rawConfig == '3') {
                    Fire fire = new Fire(this, posX, posY);
                    InteractionHandler.addEnemy(fire);
                    continue;
                }
                if (rawConfig == '2') {
                    Oneal oneal = new Oneal(this, posX, posY);
                    InteractionHandler.addEnemy(oneal);
                }
                else if (rawConfig == '4') {
                    Ghost ghost = new Ghost(this, posX, posY);
                    InteractionHandler.addEnemy(ghost);
                }
                else if (rawConfig == '5') {
                    Frog frog = new Frog(this, posX, posY);
                    InteractionHandler.addEnemy(frog);
                }
                else if (rawConfig == '6') {
                    Bear bear = new Bear(this, posX, posY);
                    InteractionHandler.addEnemy(bear);
                }
            }
        }
    }
}
