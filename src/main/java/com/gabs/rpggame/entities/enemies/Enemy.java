package com.gabs.rpggame.entities.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.AliveEntity;
import com.gabs.rpggame.graphics.Animation;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.DamageType;
import com.gabs.rpggame.world.World;

public class Enemy extends AliveEntity {

	private int speed = 4;
	private int rightDir = 0,
				leftDir = 1,
				upDir = 2,
				downDir = 3;
	private boolean moving;
	private int direction = downDir;
	private EnemyType type;
	private int frames = 0,
			animDelay = 5,
			index = 1;

	private Animation
			downAnimation = new Animation(1,5),
			leftAnimation = new Animation(1,5),
			rightAnimation = new Animation(1,5),
			upAnimation = new Animation(1,5);

	public Enemy (EnemyType type) {
		this.setTargetable(true);
		int spriteIndex = 0;
		switch (type){
			case DEFAULT:
				this.setLife(60);
				this.setDamage(20);
				spriteIndex = 7;
				break;
			case RUNNER:
				this.setLife(80);
				this.setDamage(20);
				this.setSpeed(Main.player.getSpeed()+6);
				spriteIndex = 10;
				break;
			case TANK:
				this.setLife(120);
				this.setDamage(40);
				this.setSpeed(Main.player.getSpeed()+2);
				spriteIndex = 13;
				break;
		}
		for (int i = 0; i < 3; i++) {
			leftAnimation.getImages().add(Main.spritesheet.getSpriteRelative(spriteIndex, 0, this.getWidth(), this.getHeight()*2));
			rightAnimation.getImages().add(Main.spritesheet.getSpriteRelative(spriteIndex, 2, this.getWidth(), this.getHeight()*2));
			downAnimation.getImages().add(Main.spritesheet.getSpriteRelative(spriteIndex, 4, this.getWidth(), this.getHeight()*2));
			upAnimation.getImages().add(Main.spritesheet.getSpriteRelative(spriteIndex, 6, this.getWidth(), this.getHeight()*2));
		}

	}
	
	public Enemy(int life, int damage, DamageType damageType) {
		this.setLife(life);
		this.setDamage(damage);
		this.setDamageType(damageType);
	}
	
	@Override
	public void eventTick() {
		boolean increase;
		if(Main.random.nextInt(100) < 50) {
			increase = false;
			this.setSpeed(speed/2);
		} else {
			increase = true;
			this.setSpeed(speed/4);
		}
			
		this.setMoving(false);
		if(!this.isCollidingWithPlayer()) {
			if (this.getX() < Main.player.getX() && World.placeFree(this.getX() + this.getSpeed(), this.getY()) && !isColliding(this.getCollisionMask().getX() + this.getSpeed(), this.getCollisionMask().getY())) {
				this.setMoving(true);
				this.setX(this.getX() + this.getSpeed());
				this.setDirection(rightDir);
			}
			else if (this.getX() > Main.player.getX() && World.placeFree(this.getX() - this.getSpeed(), this.getY()) && !isColliding(this.getCollisionMask().getX() - this.getSpeed(), this.getCollisionMask().getY())) {
				this.setMoving(true);
				this.setX(this.getX() - this.getSpeed());
				this.setDirection(leftDir);
			}
			
			if(this.getY() < Main.player.getY() - 10 && World.placeFree(this.getX(), this.getY() + this.getSpeed()) && !isColliding(this.getCollisionMask().getX(), this.getCollisionMask().getY() + this.getSpeed())) {
				this.setMoving(true);
				this.setY(this.getY() + this.getSpeed());
				this.setDirection(downDir);
			}
			else if(this.getY() > Main.player.getY() + 10 && World.placeFree(this.getX(), this.getY() - this.getSpeed()) && !isColliding(this.getCollisionMask().getX(), this.getCollisionMask().getY() - this.getSpeed())) {
				this.setMoving(true);
				this.setY(this.getY() - this.getSpeed());
				this.setDirection(upDir);
			}	
		} else {
			//Contato com player
			this.inflictDamage(this.getDamage(), this.getDamageType(), Main.player);

			/*
			if(Main.player.isTargetable() && !Main.player.isTakingDamage()) {
				Main.player.setLife(Main.player.getLife()-this.getDamage());
				System.out.println("Dano!");
				System.out.println(Main.player.getLife());
				Main.player.setTakingDamage(true);
			}*/
		}
		
		if(this.isMoving()) {
			frames++;
			if(frames == animDelay) {
				frames = 0;
				index++;
				if(index > leftAnimation.getImages().size()-1)
					index = 0;
			}
		} else {
			index = 1;
			direction = downDir;
		}
		
		if(increase)
			this.setSpeed(speed*4);
		else
			this.setSpeed(speed*2);
		
		
		if(this.getLife() <= 0) {
			Main.enemies.remove(this);
		}
		
		super.eventTick();
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getCollisionMask().getX(), this.getCollisionMask().getY(), this.getCollisionMask().getWidth(), this.getCollisionMask().getHeight());
		Rectangle player = new Rectangle(Main.player.getCollisionMask().getX(), Main.player.getCollisionMask().getY(), Main.GameProperties.TileSize, Main.GameProperties.TileSize);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xNext, int yNext) {
		for(int i = 0; i < Main.enemies.size(); i++){
			Rectangle collisionBox = new Rectangle(xNext, yNext, this.getCollisionMask().getWidth(), this.getCollisionMask().getHeight());
			Rectangle targetEnemy = new Rectangle(Main.enemies.get(i).getCollisionMask().getX(), Main.enemies.get(i).getCollisionMask().getY(), Main.enemies.get(i).getCollisionMask().getWidth(), Main.enemies.get(i).getCollisionMask().getHeight());
			if(Main.enemies.get(i) != this) {
				return collisionBox.intersects(targetEnemy);
			}
				
		}
		return false;
	}
	
	@Override
	public void render(Graphics g) {
		if(this.getDirection() == downDir) {
			g.drawImage(downAnimation.getImages().get(downAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - Main.GameProperties.TileSize, null);
		}else if(this.getDirection() == upDir) {
			g.drawImage(upAnimation.getImages().get(upAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY()  - Main.GameProperties.TileSize, null);
		}
		
		if (this.getDirection() == rightDir) {
			g.drawImage(rightAnimation.getImages().get(rightAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - Main.GameProperties.TileSize, null);
		}else if(this.getDirection() == leftDir) {
			g.drawImage(leftAnimation.getImages().get(leftAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - Main.GameProperties.TileSize, null);
		}
		
		super.render(g);
	}

	public int getSpeed() {
		return speed;
	}

	public Enemy setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	public boolean isMoving() {
		return moving;
	}

	public Enemy setMoving(boolean moving) {
		this.moving = moving;
		return this;
	}

	public int getDirection() {
		return direction;
	}

	public Enemy setDirection(int direction) {
		this.direction = direction;
		return this;
	}

	public int getAnimDelay() {
		return animDelay;
	}

	public Enemy setAnimDelay(int animDelay) {
		this.animDelay = animDelay;
		return this;
	}

	public EnemyType getType() {
		return type;
	}

	public void setType(EnemyType type) {
		this.type = type;
	}
	public Enemy setX(int x){
		super.setX(x);
		return this;
	}
	public Enemy setY(int y){
		super.setY(y);
		return this;
	}
}
