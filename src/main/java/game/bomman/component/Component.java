package game.bomman.component;

import game.bomman.entity.character.Bomber;
import game.bomman.entity.character.Character;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.map.Cell;
import game.bomman.map.Map;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    protected static Canvas characterCanvas;
    protected static Map gameMap;
    protected static Bomber bomber;
    protected static List<Enemy> enemyList = new ArrayList<>();

    public static void init(Canvas canvas, Map gameMap_) {
        characterCanvas = canvas;
        gameMap = gameMap_;
        Character.setCanvas(characterCanvas.getGraphicsContext2D());

        bomber = new Bomber(gameMap);
        gameMap.getCell(1, 1).addEntity(bomber);
        System.out.println("Bomber constructed.");
    }
}