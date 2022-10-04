package game.bomman;

import game.bomman.entity.stuff.Brick;
import game.bomman.entity.stuff.Grass;
import game.bomman.entity.stuff.Stuff;
import game.bomman.entity.stuff.Wall;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication extends Application {
    public Scene loadStaticMapSample() throws FileNotFoundException {
        FileInputStream maps = new FileInputStream("src/main/resources/game/bomman/assets/maps/map1.txt");
        Scanner mapScanner = new Scanner(maps);

        int height = mapScanner.nextInt();
        int width = mapScanner.nextInt();

        mapScanner.useDelimiter("\n");
        mapScanner.next();

        ArrayList<Stuff> entities = new ArrayList<>();

        for (int row = 0; row < height; ++row) {
            String thisRow = mapScanner.next();
            for (int col = 0; col < width; ++col) {
                switch (thisRow.charAt(col)) {
                    case '#' -> {
                        Wall w = new Wall(col, row);
                        entities.add(w);
                    }
                    case ' ' -> {
                        Grass g = new Grass(col, row);
                        entities.add(g);
                    }
                    case '*' -> {
                        Brick b = new Brick(col, row);
                        entities.add(b);
                    }
                }
            }
        }

        Canvas canvas = new Canvas(width * Stuff.side, height * Stuff.side);
        Group root = new Group(canvas);
        Scene scene = new Scene(root, canvas.getWidth(), canvas.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (Stuff entity: entities) {
            entity.render(gc);
        }

        return scene;
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Bomberman");
        stage.setResizable(false);

        stage.setScene(loadStaticMapSample());
//        loadBomberSample(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}