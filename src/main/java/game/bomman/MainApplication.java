package game.bomman;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Game.init(stage);
        Game.run();
    }

    public static void main(String[] args) {
        launch();
    }
}
