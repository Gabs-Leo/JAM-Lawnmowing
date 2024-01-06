package com.gabs.rpggame.entities;

import com.gabs.rpggame.world.CollisionType;

import java.awt.image.BufferedImage;

public class Prop extends Entity {
    private CollisionType collisionType = CollisionType.BLOCK;
    private boolean front = false;

    public Prop(){}
    public Prop(Prop prop){
        this.setSprite(prop.getSprite());
        this.setCollisionType(prop.getCollisionType());
        this.setFront(prop.isFront());
    }
    public Prop(BufferedImage sprite){
        this.setSprite(sprite);
    }

    public Prop(BufferedImage sprite, boolean front){
        this.setSprite(sprite);
        this.front = front;
    }
    public Prop(BufferedImage sprite, CollisionType collisionType){
        this.setSprite(sprite);
        this.collisionType = collisionType;
    }
    public Prop(BufferedImage sprite, boolean front, CollisionType collisionType){
        this.setSprite(sprite);
        this.collisionType = collisionType;
        this.front = front;
    }
    public Prop(BufferedImage sprite, int x, int y, CollisionType collisionType){
        this.setSprite(sprite);
        this.setX(x);
        this.setY(y);
        this.collisionType = collisionType;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }
}
