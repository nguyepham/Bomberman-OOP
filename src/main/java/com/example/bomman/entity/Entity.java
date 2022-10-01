package com.example.bomman.entity;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Entity {
   public static final String IMAGES_PATH = "src/main/resources/com/example/bomman/assets/sprites";


   // create a load Image method to encapsulate
   // how handling loading images so that
   // we can change the particular implementation
   // later on if necessary
   public static Image loadImage(String path) throws FileNotFoundException {
      FileInputStream inputStream = new FileInputStream(path);
      return new Image(inputStream);
   }
}
