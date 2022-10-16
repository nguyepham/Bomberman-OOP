package game.bomman.component;

import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.enemy.Enemy;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    protected static Canvas characterCanvas;
    protected static Bomber bomber;
    protected static List<Enemy> enemyList = new ArrayList<>();

    public static void init(Canvas canvas, Bomber bomber_) {
        characterCanvas = canvas;
        bomber = bomber_;
    }
}
