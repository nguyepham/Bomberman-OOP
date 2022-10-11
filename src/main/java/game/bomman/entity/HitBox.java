package game.bomman.entity;

public class HitBox {
    private double minX;
    private double minY;
    private double width;
    private double height;

    public HitBox(double v1, double v2, double v3, double v4) {
        minX = v1;
        minY = v2;
        width = v3;
        height = v4;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }
    
    public double getMinY() {
        return minY;
    }
    
    public void setMinY(double minY) {
        this.minY = minY;
    }
    
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }

    public double getCenterX() {
        return minX + width / 2;
    }

    public double getCenterY() {
        return minY + height / 2;
    }

    public boolean contains(double x, double y) {
        return (minX <= x && x <= minX + width && minY <= y && y <= minY + height);
    }
}
