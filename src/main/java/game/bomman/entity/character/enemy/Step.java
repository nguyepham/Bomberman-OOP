package game.bomman.entity.character.enemy;


public class Step {
    // target cell position
    private int posX;
    private int posY;
    // the direction led to that cell
    private int trace;

    public Step(int posX, int posY, int trace) {
        this.posX = posX;
        this.posY = posY;
        this.trace = trace;
    }

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public int getTrace() { return trace; }

    public void setTrace(int value) { trace = value; }
}
