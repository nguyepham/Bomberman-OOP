package game.bomman.component;

import game.bomman.command.Command;
import game.bomman.command.interactingCommand.*;
import game.bomman.entity.Entity;
import game.bomman.entity.character.enemy.Enemy;
import game.bomman.entity.immobileEntity.Bomb;
import game.bomman.entity.immobileEntity.Brick;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import game.bomman.entity.immobileEntity.Portal;
import game.bomman.entity.item.Item;
import game.bomman.map.Cell;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class InteractionHandler extends Component {
    public static Command layingBomb = new LayingBomb();
    private static Canvas bombCanvas;
    private static Canvas itemCanvas;
    private static Portal portal;
    private static List<ImmobileEntity> immobileEntityList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();

    public static void init(Canvas bombCanvas_, Canvas itemCanvas_) {
        bombCanvas = bombCanvas_;
        itemCanvas = itemCanvas_;
        ImmobileEntity.setCanvas(bombCanvas.getGraphicsContext2D());
        Item.setCanvas(itemCanvas.getGraphicsContext2D());
    }

    public static void clearEntityList() {
        immobileEntityList.clear();
        itemList.clear();
    }

    public static void addPortal(Portal portal_) {
        portal = portal_;
    }

    public static Portal getPortal() {
        return portal;
    }

    public static void addEnemy(Enemy enemy) {
        enemyList.add(enemy);
    }

    public static void addItem(Item item) { itemList.add(item); }

    public static void addImmobileEntity(ImmobileEntity entity) {
        immobileEntityList.add(entity);
    }

    public static void removeEnemy(Enemy enemy) {
        if (enemyList.size() == 1) {
            if (portal.hasAppeared()) {
                portal.activate();
            } else {
                Cell portalCell = gameMap.getCell(portal.getPosOnMapX(), portal.getPosOnMapY());
                Brick brick = (Brick) portalCell.getImmobileEntity();
                brick.spark();
            }
            return;
        }

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

    public static void removeItem(Item item) {
        for (int i = 0; i < itemList.size(); ++i) {
            if (itemList.get(i).equals(item)) {
                itemList.remove(i);
                break;
            }
        }
    }

    public static ImmobileEntity getImmobileEntity(Cell cell) {
        for (ImmobileEntity entity : immobileEntityList) {
            if (entity.getPosOnMapX() == cell.getPosOnMapX() && entity.getPosOnMapY() == cell.getPosOnMapY()) {
                return entity;
            }
        }
        return null;
    }

    public static Item getItem(Cell cell) {
        for (Item item : itemList) {
            if (item.getPosOnMapX() == cell.getPosOnMapX() && item.getPosOnMapY() == cell.getPosOnMapY()) {
                return item;
            }
        }
        return null;
    }

    public static Bomb getBomb() {
        for (ImmobileEntity entity : immobileEntityList) {
            if (entity instanceof Bomb) {
                return (Bomb) entity;
            }
        }
        return null;
    }

    public static void handleInteraction(Entity entity, Cell cell) {
        int posX = cell.getPosOnMapX();
        int posY = cell.getPosOnMapY();

        if (bomber.getPosOnMapX() == posX && bomber.getPosOnMapY() == posY) {
            entity.interactWith(bomber);
        }

        for (Enemy other : enemyList) {
            if (other.getPosOnMapX() == posX && other.getPosOnMapY() == posY) {
                entity.interactWith(other);
            }
        }

        for (ImmobileEntity other : immobileEntityList) {
            if (other.getPosOnMapX() == posX && other.getPosOnMapY() == posY) {
                entity.interactWith(other);
            }
        }

        for (Item other : itemList) {
            if (other.getPosOnMapX() == posX && other.getPosOnMapY() == posY) {
                entity.interactWith(other);
            }
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

    public static void update(double elapsedTime) throws FileNotFoundException {
        for (int i = 0; i < immobileEntityList.size(); ++i) {
            ImmobileEntity entity = immobileEntityList.get(i);
            entity.update(elapsedTime);
        }
        for (int i = 0; i < itemList.size(); ++i) {
            Item item = itemList.get(i);
            item.update(elapsedTime);
        }
    }

    public static void draw() {
        bombCanvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE, bombCanvas.getWidth(), bombCanvas.getHeight());
        for (ImmobileEntity entity : immobileEntityList) {
            entity.draw();
        }
        itemCanvas.getGraphicsContext2D().clearRect(Entity.SIDE, Entity.SIDE, itemCanvas.getWidth(), itemCanvas.getHeight());
        for (Item item : itemList) {
            item.draw();
        }
    }
}
