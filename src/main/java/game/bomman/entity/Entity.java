package game.bomman.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Entity {
   public static final String IMAGES_PATH = "src/main/resources/game/bomman/assets/sprites";
   public static final double SIDE = 48;
   protected HitBox hitBox;

   protected void initHitBox(double loadingPosX, double loadingPosY, double width, double height) {
      hitBox = new HitBox(loadingPosX, loadingPosY, width, height);
   }

   // create a load Image method to encapsulate
   // the functionality of loading images so that
   // we can change the particular implementation
   // later on if necessary
   public static Image loadImage(String path) throws FileNotFoundException {
      FileInputStream inputStream = new FileInputStream(path);
      Image image = new Image(inputStream);
      try {
         inputStream.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return image;
   }

   public HitBox getHitBox() {
      return hitBox;
   }

   public double getLoadingPositionX() {
      return hitBox.getMinX();
   }

   public double getLoadingPositionY() {
      return hitBox.getMinY();
   }

   public boolean gotInto(Entity other) {
      return other.hitBox.contains(hitBox.getCenterX(), hitBox.getCenterY());
   }

   public abstract void update(double elapsedTime, double timeSinceStart);
}
