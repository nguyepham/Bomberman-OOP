package game.bomman.entity.character.enemy;

import game.bomman.entity.character.Direction;

public class Step {
    // target cell position
    private int posX;
    private int posY;
    // the direction led to that cell
    private Direction trace;

    public Step(int posX, int posY, Direction trace) {
        this.posX = posX;
        this.posY = posY;
        this.trace = trace;
    }

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public Direction getTrace() { return trace; }

    public void setTrace(Direction value) { trace = value; }
}
