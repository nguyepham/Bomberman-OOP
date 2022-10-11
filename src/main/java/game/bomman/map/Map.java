package game.bomman.map;

import game.bomman.entity.Entity;
import game.bomman.entity.immobileEntity.Brick;
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

    public Map() throws FileNotFoundException {
        FileInputStream mapFile = new FileInputStream(
                "src/main/resources/game/bomman/assets/map1.txt");
        mapScanner = new Scanner(mapFile);

        int height = mapScanner.nextInt();
        int width = mapScanner.nextInt();

        mapScanner.useDelimiter("\n");
        mapScanner.next();
        this.initCells(width, height);
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

    public Canvas setUp(Canvas bombCanvas) throws FileNotFoundException {
        Canvas canvas = new Canvas(Entity.SIDE * width, Entity.SIDE * height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int j = 0; j < height; ++j) {
            String thisRow = mapScanner.next();
            for (int i = 0; i < width; ++i) {
                char rawConfig = thisRow.charAt(i);
                cells[j][i] = new Cell(i, j, rawConfig);
                Cell thisCell = cells[j][i];

                if (thisCell.getRawConfig() == '#' || thisCell.getRawConfig() == '*') {
                    thisCell.setBlocking();
                }
                double posX = thisCell.getLoadingPositionX();
                double posY = thisCell.getLoadingPositionY();

                if (j == height - 1) {
                    thisCell.getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
                    gc.drawImage(thisCell.getSprite(), Entity.SIDE * 5, 0, Entity.SIDE, Entity.SIDE,
                            posX, posY, Entity.SIDE, Entity.SIDE);
                    continue;
                }

                if (j == 0) {
                    thisCell.getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
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
                    thisCell.getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
                    if (i == 0) {
                        gc.drawImage(thisCell.getSprite(), 0, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                    } else {
                        gc.drawImage(thisCell.getSprite(), Entity.SIDE * 4, 0, Entity.SIDE, Entity.SIDE,
                                posX, posY, Entity.SIDE, Entity.SIDE);
                    }
                    continue;
                }

                switch (rawConfig) {
                    case '#' -> {
                        thisCell.getSpriteFrom(
                                Entity.IMAGES_PATH + "/map/steel.png");
                    }
                    case '*' -> {
                        thisCell.setEntity(new Brick(bombCanvas.getGraphicsContext2D(), posX, posY));
                    }
                    default -> {
                        if (j > 0 && (cells[j - 1][i].getRawConfig() == '#' || cells[j - 1][i].getRawConfig() == '*')) {
                            thisCell.getSpriteFrom(Entity.IMAGES_PATH + "/map/shaderGrass.png");
                        } else {
                            thisCell.getSpriteFrom(Entity.IMAGES_PATH + "/map/grass.png");
                        }
                    }
                }
                gc.drawImage(thisCell.getSprite(), posX, posY, Entity.SIDE, Entity.SIDE);
            }
        }

        mapScanner.close();
        return canvas;
    }

    public int[] getPositionOf(Entity entity) {
        int[] pos = new int[2];
        for (int i = 1; i < height - 1; ++i) {
            for (int j = 1; j < width; ++j) {
                Cell cell = cells[i][j];
                if (entity.gotInto(cell)) {
                    pos = new int[] {i, j};
                }
            }
        }
        return pos;
    }
}
