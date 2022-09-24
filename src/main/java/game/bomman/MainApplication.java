package game.bomman;

import game.object.Bomber;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        Bomber b = new Bomber(50, 50);

        Group root = new Group(b.view);

        Scene scene = new Scene(root, 620, 260);

        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}