package game.bomman.map;

import game.bomman.entity.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private int height;
    private int width;
    private Cell[][] cells;

    public void setSize(int width, int height) {
        cells = new Cell[height][];
        for (int i = 0; i < height; ++i) {
            cells[i] = new Cell[width];
        }
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Canvas load() throws FileNotFoundException {
        FileInputStream maps = new FileInputStream(
                "src/main/resources/game/bomman/assets/map1.txt");
        Scanner mapScanner = new Scanner(maps);

        int height = mapScanner.nextInt();
        int width = mapScanner.nextInt();

        mapScanner.useDelimiter("\n");
        mapScanner.next();
        this.setSize(width, height);

        Canvas canvas = new Canvas(Entity.SIDE * width, Entity.SIDE * height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int j = 0; j < height; ++j) {
            String thisRow = mapScanner.next();
            for (int i = 0; i < width; ++i) {
                char rawConfig = thisRow.charAt(i);
                int debug = 0;
                if (i == 12) {
                    debug = 1;
                }
                cells[j][i] = new Cell(i, j, rawConfig);

                switch (rawConfig) {
                    case '#' -> {
                        cells[j][i].getSpriteFrom("src/main/resources/game/bomman/assets/sprites/map/steel.png");
                    }
                    case '*' -> {
                        cells[j][i].getSpriteFrom("src/main/resources/game/bomman/assets/sprites/map/brick.png");
                    }
                    default -> {
                        if (j > 0 && (cells[j - 1][i].getRawConfig() == '#' || cells[j - 1][i].getRawConfig() == '*')) {
                            cells[j][i].getSpriteFrom(
                                    "src/main/resources/game/bomman/assets/sprites/map/shaderGrass.png");
                        } else {
                            cells[j][i].getSpriteFrom(
                                    "src/main/resources/game/bomman/assets/sprites/map/grass.png");
                        }
                    }
                }

                double[] pos = cells[j][i].getLoadingPosition();
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
