package com.gabs.rpggame.world;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.Entity;
import com.gabs.rpggame.entities.Prop;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends Prop {
    private boolean mowed;

    public Floor(BufferedImage sprite){
        this.setSprite(sprite);
        this.setCollisionType(CollisionType.NO_COLLISION);
    }

    @Override
    public void eventTick() {
        super.eventTick();
        if(isColliding(this, Main.player)){
            if(!isMowed()){
                Main.player.setMowedAmount(Main.player.getMowedAmount()+1);
                System.out.println(Main.player.getMowedAmount());
            }
            this.setMowed(true);
        }
    }

    @Override
    public void render(Graphics g) {
        if(isMowed())
            g.drawImage(Main.spritesheet.getSpriteRelative(4, 3), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
        else
            super.render(g);
    }

    public boolean isMowed() {
        return mowed;
    }

    public Floor setMowed(boolean mowed) {
        this.mowed = mowed;
        return this;
    }
}
