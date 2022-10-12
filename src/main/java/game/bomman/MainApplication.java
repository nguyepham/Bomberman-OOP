package game.bomman;

import game.bomman.gameState.PlayingState;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Bomberman");
        stage.setResizable(false);

        PlayingState playingState = new PlayingState();
        playingState.setUp();
        playingState.run();

        stage.setScene(playingState.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}