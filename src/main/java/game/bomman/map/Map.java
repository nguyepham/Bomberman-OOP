package game.bomman.map;

import game.bomman.entity.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private double width;
    private double height;
    private Cell[][] cells;

    public void initCells(int width, int height) {
        cells = new Cell[height][];
        for (int i = 0; i < height; ++i) {
            cells[i] = new Cell[width];
        }
        this.height = Entity.SIDE * height;
        this.width = Entity.SIDE * width;
    }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public Cell getCell(int i, int j) {
        return cells[j][i];
    }

    public Canvas setUp() throws FileNotFoundException {
        FileInputStream maps = new FileInputStream(
                "src/main/resources/game/bomman/assets/map1.txt");
        Scanner mapScanner = new Scanner(maps);

        int height = mapScanner.nextInt();
        int width = mapScanner.nextInt();

        mapScanner.useDelimiter("\n");
        mapScanner.next();
        this.initCells(width, height);

        Canvas canvas = new Canvas(Entity.SIDE * width, Entity.SIDE * height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int j = 0; j < height; ++j) {
            String thisRow = mapScanner.next();
            for (int i = 0; i < width; ++i) {
                char rawConfig = thisRow.charAt(i);
                cells[j][i] = new Cell(i, j, rawConfig);
                double[] pos = cells[j][i].getLoadingPosition();

                if (j == height - 1) {
                    cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
                    gc.drawImage(cells[j][i].getSprite(), Entity.SIDE * 5, 0, Entity.SIDE, Entity.SIDE,
                            pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                    continue;
                }

                if (j == 0) {
                    cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
                    if (i == 0) {
                        gc.drawImage(cells[j][i].getSprite(), Entity.SIDE, 0, Entity.SIDE, Entity.SIDE,
                                pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                        continue;
                    }
                    if (i == width - 1) {
                        gc.drawImage(cells[j][i].getSprite(), Entity.SIDE * 3, 0, Entity.SIDE, Entity.SIDE,
                                pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                        continue;
                    }
                    gc.drawImage(cells[j][i].getSprite(), Entity.SIDE * 2, 0, Entity.SIDE, Entity.SIDE,
                            pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                    continue;
                }

                if (i == 0 || i == width - 1) {
                    cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/walls@10.png");
                    if (i == 0) {
                        gc.drawImage(cells[j][i].getSprite(), 0, 0, Entity.SIDE, Entity.SIDE,
                                pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                    } else {
                        gc.drawImage(cells[j][i].getSprite(), Entity.SIDE * 4, 0, Entity.SIDE, Entity.SIDE,
                                pos[0], pos[1], Entity.SIDE, Entity.SIDE);
                    }
                    continue;
                }

                switch (rawConfig) {
                    case '#' -> {
                        cells[j][i].getSpriteFrom(
                                Entity.IMAGES_PATH + "/map/steel.png");
                    }
                    case '*' -> {
                        cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/brick.png");
                    }
                    default -> {
                        if (j > 0
                                && (cells[j - 1][i].getRawConfig() == '#' || cells[j - 1][i].getRawConfig() == '*')) {
                            cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/shaderGrass.png");
                        } else {
                            cells[j][i].getSpriteFrom(Entity.IMAGES_PATH + "/map/grass.png");
                        }
                    }
                }
                gc.drawImage(cells[j][i].getSprite(), pos[0], pos[1], Entity.SIDE, Entity.SIDE);
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
