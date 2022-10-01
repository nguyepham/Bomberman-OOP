package game.bomman.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Entity {
   public static final String IMAGES_PATH = "src/main/resources/game/bomman/assets/sprites";

   // Notes that X's coordinate here is the horizontal one
   // while Y's coordinate is the vertical one.
   protected double positionX;
   protected double positionY;

   // create a load Image method to encapsulate
   // the functionality of loading images so that
   // we can change the particular implementation
   // later on if necessary
   public static Image loadImage(String path) throws FileNotFoundException {
      FileInputStream inputStream = new FileInputStream(path);
      return new Image(inputStream);
   }
}
