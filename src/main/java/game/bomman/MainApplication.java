package game.bomman;

import game.bomman.component.SoundPlayer;
import game.bomman.gameState.InstructionScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    public static Stage instructionStage;
    public static InstructionScene instructionScene;

    @Override
    public void start(Stage stage) throws IOException {
        instructionStage = new Stage();
        instructionScene = new InstructionScene();
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.setScene(scene);
        SoundPlayer.playStageStartSound();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
