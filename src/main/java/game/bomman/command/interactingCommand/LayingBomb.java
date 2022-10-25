package game.bomman.command.interactingCommand;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;

public class LayingBomb extends Command {
    @Override
    public void executeOn(Character obj) {
        if (obj.isAlive()) {
            obj.layingBomb();
        }
    }
}
