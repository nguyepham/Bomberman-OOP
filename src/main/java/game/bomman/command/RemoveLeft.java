package game.bomman.command;

import game.bomman.entity.character.Character;

public class RemoveLeft extends Command {
    @Override
    public void executeOn(Character obj) {
        obj.removeLeft();
    }
}
