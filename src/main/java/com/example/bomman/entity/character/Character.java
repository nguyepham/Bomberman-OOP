package com.example.bomman.entity.character;

import com.example.bomman.entity.Entity;

public abstract class Character extends Entity {
    // Notes that X's coordinate here is the horizontal one
    // while Y's coordinate is the vertical one.
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;

    public static final String NOT_MOVING = "nm";
}
