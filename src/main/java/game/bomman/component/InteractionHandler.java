package game.bomman.component;

import game.bomman.command.Command;
import game.bomman.command.interactingCommand.*;
import game.bomman.entity.character.Character;
import game.bomman.entity.immobileEntity.ImmobileEntity;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class InteractionHandler {
    private static Command layingBomb = new LayingBomb();
    private static Canvas characterCanvas;
    private static Canvas bombCanvas;
    private static List<Character> characterList = new ArrayList<>();
    private static List<ImmobileEntity> immobileEntityList = new ArrayList<>();

    public static List<Character> getCharacterList() {
        return characterList;
    }

    public static List<ImmobileEntity> getImmobileEntityList() {
        return immobileEntityList;
    }
}
