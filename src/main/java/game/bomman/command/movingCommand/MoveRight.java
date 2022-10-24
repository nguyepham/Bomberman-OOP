package game.bomman.command.movingCommand;

import game.bomman.command.Command;
import game.bomman.entity.character.Character;

public class MoveRight extends Command {
    public MoveRight() { label = "r"; }

    @Override
    public void executeOn(Character obj) {
        if (obj.isAlive()) {
            obj.moveRight();
        }
    }
}
