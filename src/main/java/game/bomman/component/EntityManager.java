package game.bomman.component;

import game.bomman.command.Command;
import game.bomman.command.interactingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import game.bomman.entity.immobileEntity.Portal;
import game.bomman.map.Cell;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends Component {
    private static Command layingBomb = new LayingBomb();
    private static Canvas bombCanvas;
    private static Canvas itemCanvas;
    private static Portal portal;
    private static List<ImmobileEntity> immobileEntityList = new ArrayList<>();

    public static void init(Canvas bombCanvas_) {
        bombCanvas = bombCanvas_;
    }

    public static void addPortal(Portal portal_) {
        portal = portal_;
    }

    public static Portal getPortal() {
        return portal;
    }

    public static boolean portalAppeared() {
        return portal.hasAppeared();
    }

    public static void addEnemy(Enemy enemy) {
        enemyList.add(enemy);
    }

    public static void addImmobileEntity(ImmobileEntity entity) {
        immobileEntityList.add(entity);
    }

    public static void removeEnemy(Enemy enemy) {
        for (int i = 0; i < enemyList.size(); ++i) {
            if (enemyList.get(i).equals(enemy)) {
                enemyList.remove(i);
                break;
            }
        }
    }

    public static void removeImmobileEntity(ImmobileEntity entity) {
        for (int i = 0; i < immobileEntityList.size(); ++i) {
            if (immobileEntityList.get(i).equals(entity)) {
                immobileEntityList.remove(i);
                break;
            }
        }
    }

    public static void handleInteraction(Entity entity, Cell cell) {
        for (int i = 0; i < cell.numOfEntities(); ++i) {
            entity.interactWith(cell.getEntity(i));
        }
    }

    public static void activateInputReader() {
        EventHandler<KeyEvent> layingBombEvent = (event) -> {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case SPACE -> layingBomb.executeOn(bomber);
                }
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
//                switch (event.getCode()) {
//                    case DOWN -> removeDown.executeOn(bomber);
//                    case UP -> removeUp.executeOn(bomber);
//                    case RIGHT -> removeRight.executeOn(bomber);
//                    case LEFT -> removeLeft.executeOn(bomber);
//                }
            }
        };
        characterCanvas.addEventHandler(KeyEvent.KEY_PRESSED, layingBombEvent);
//        characterCanvas.addEventHandler(KeyEvent.KEY_RELEASED, layingBombEvent);
    }

    public static void update(double elapsedTime) {
        for (int i = 0; i < immobileEntityList.size(); ++i) {
            ImmobileEntity entity = immobileEntityList.get(i);
            entity.update(elapsedTime);
        }
    }

    public static void draw() {
        bombCanvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE, bombCanvas.getWidth(), bombCanvas.getHeight());

        for (ImmobileEntity entity : immobileEntityList) {
            entity.draw();
        }
    }
}
