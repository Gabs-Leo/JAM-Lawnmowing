package com.gabs.rpggame.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.Enemy;
import com.gabs.rpggame.entities.Prop;
import com.gabs.rpggame.entities.collectables.Collectable;
import com.gabs.rpggame.entities.collectables.Equipment;

public class World {
	
	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;

	//Rocks
	private Prop rock1 = new Prop(Main.spritesheet.getSpriteRelative(3, 4));
	private Prop rock2 = new Prop(Main.spritesheet.getSpriteRelative(3, 5));

	//Streets
	private Prop streetLeft = new Prop(Main.spritesheet.getSpriteRelative(3, 6), CollisionType.NO_COLLISION);
	private Prop streetRight = new Prop(Main.spritesheet.getSpriteRelative(4, 6), CollisionType.NO_COLLISION);
	private Prop streetTop = new Prop(Main.spritesheet.getSpriteRelative(3, 7), CollisionType.NO_COLLISION);
	private Prop streetDown = new Prop(Main.spritesheet.getSpriteRelative(4, 7), CollisionType.NO_COLLISION);

	//Tree
	private Prop tree1 = new Prop(Main.spritesheet.getSpriteRelative(4, 0), true, CollisionType.NO_COLLISION);
	private Prop tree2 = new Prop(Main.spritesheet.getSpriteRelative(5, 0), true, CollisionType.NO_COLLISION);
	private Prop tree3 = new Prop(Main.spritesheet.getSpriteRelative(4, 1), true, CollisionType.NO_COLLISION);
	private Prop tree4 = new Prop(Main.spritesheet.getSpriteRelative(5, 1), true, CollisionType.NO_COLLISION);
	private Prop tree5 = new Prop(Main.spritesheet.getSpriteRelative(4, 2));
	private Prop tree6 = new Prop(Main.spritesheet.getSpriteRelative(5, 2));

	public World () {}
	public World(String path) {
		try {
			BufferedImage mapSprite = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
			WIDTH = mapSprite.getWidth();
			HEIGHT = mapSprite.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[mapSprite.getWidth() * mapSprite.getHeight()];
			
			mapSprite.getRGB(0, 0, 
							WIDTH, 
							HEIGHT,
							pixels,
							0,
							WIDTH);
			
			for (int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					int currentTile = pixels[xx + (yy * WIDTH)];
					
					Tile tile = new Tile();
					tile.setX(xx * Main.GameProperties.TileSize)
						.setY(yy * Main.GameProperties.TileSize)
						.setCollisionType(CollisionType.NO_COLLISION);
					int random = Main.generateRandomInt(1,20);
					if(random >= 4){
						tile.setSprite(
								Main.spritesheet.getSpriteRelative(
									3,
									0,
									Main.GameProperties.TileSize,
									Main.GameProperties.TileSize
								)
						);
					} else {
						tile.setSprite(
								Main.spritesheet.getSpriteRelative(
										3,
										random,
										Main.GameProperties.TileSize,
										Main.GameProperties.TileSize
								)
						);
					}
					/*
					if(currentTile == 0xFF303030) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(96, 32, Main.GameProperties.TileSize, Main.GameProperties.TileSize));	
					} else if(currentTile == 0xFF000000) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(0, 0, 1, 1));
					} else if(currentTile == 0xFF353535) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(96, 0, Main.GameProperties.TileSize, Main.GameProperties.TileSize));
					} 
					if (currentTile == 0xFFFF0000) {
						tile.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize)
							.setType(CollisionType.NO_COLLISION)
							.setSprite(Main.spritesheet.getSprite(0, 288, Main.GameProperties.TileSize, Main.GameProperties.TileSize));
					}


					else*/

					Prop prop = null;
					if(currentTile == 0xFF000000){
						prop = new Floor(tile.getSprite());
					}
					//Rocks
					else if (currentTile == 0xFF00D700) {
						prop = new Prop(rock1);
					}
					else if (currentTile == 0xFF00CD00) {
						prop = new Prop(rock2);
					}

					//Streets
					else if (currentTile == 0xFF00FF00) {
						prop = new Prop(streetLeft);
					}
					else if (currentTile == 0xFF00F500) {
						prop = new Prop(streetRight);
					}
					else if (currentTile == 0xFF00e100) {
						prop = new Prop(streetTop);
					}
					else if (currentTile == 0xFF00eb00) {
						prop = new Prop(streetDown);
					}
					//Tree
					else if (currentTile == 0xFF003200) {
						prop = new Prop(tree1);
					}else if (currentTile == 0xFF003300) {
						prop = new Prop(tree2);
					}else if (currentTile == 0xFF003400) {
						prop = new Prop(tree3);
					}else if (currentTile == 0xFF003500) {
						prop = new Prop(tree4);
					}else if (currentTile == 0xFF003600) {
						prop = new Prop(tree5);
					}else if (currentTile == 0xFF003700) {
						prop = new Prop(tree6);
					}

					//Player
					if(currentTile == 0xFF0000FF) {
						Main.player.setX(xx*Main.GameProperties.TileSize);
						Main.player.setY(yy*Main.GameProperties.TileSize);
						Main.eventTriggers.add(new EventTrigger().setAction(() -> System.out.println("xd")).setX(xx*Main.GameProperties.TileSize).setY(yy*Main.GameProperties.TileSize));
					}
					/*
					else if (currentTile == 0xFFFF00FF) {
						Collectable prop = new Collectable();
						prop
							.setSprite(Main.spritesheet.getSprite(544, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						prop.setMethod(() -> {Main.player.heal(15); Main.entities.remove(prop);});
						Main.entities.add(prop);
					} else if (currentTile == 0xFFfe00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						tile.setType(CollisionType.BLOCK);
						Main.entities.add(prop);
					} else if (currentTile == 0xFFfc00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 32, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						Main.frontEntities.add(prop);
					} else if (currentTile == 0xFFfb00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 0, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.frontEntities.add(prop);
					} else if (currentTile == 0xFFfd00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(608, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.entities.add(prop);
					} else if (currentTile == 0xFF0000FF) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(352, 160, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						tile.setType(CollisionType.BLOCK);
						Main.entities.add(prop);
						
					//Enemy
					} else if (currentTile == 0xFFFE0000) {
						Enemy enemy = new Enemy();
						enemy.getCollisionMask()
							.setWidth(16).setHeight(16);
						enemy
							.setDamage(20);
						enemy
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.enemies.add(enemy);
					}
					//Food
					else if(currentTile == 0xFF00ff85) {
						Collectable food = new Collectable();
						food.setSprite(Main.spritesheet.getSprite(64, 320, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						food.setMethod(() -> Main.player.heal(10));
						Main.entities.add(food);
					}
					//Ammo
					else if(currentTile == 0xFFfffb00) {
						Collectable ammo = new Collectable();
						ammo.setSprite(Main.spritesheet.getSprite(32, 320, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						ammo.setMethod(() -> Main.player.setAmmo(Main.player.getAmmo() + 10));
						Main.entities.add(ammo);
					}
					//Sword
					else if(currentTile == 0xFFfa9aff) {
						Equipment sword = Main.assets.findEquipmentByName("sword1").get();
						sword
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						sword.setMethod(() -> sword.equipTo(Main.player));
						Main.entities.add(sword);
					}*/

					if(prop != null){
						prop
								.setX(xx * Main.GameProperties.TileSize)
								.setY(yy * Main.GameProperties.TileSize);
						if(prop.isFront())
							Main.frontEntities.add(prop);
						else
							Main.entities.add(prop);

						tile.setCollisionType(prop.getCollisionType());
					}
					tiles[xx + (yy * WIDTH)] = tile;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xStart = Camera.getX() / Main.GameProperties.TileSize;
		int yStart = Camera.getY() / Main.GameProperties.TileSize;
		
		int xFinal = xStart + Main.GameProperties.ScreenWidth*Main.GameProperties.ScreenScale / Main.GameProperties.TileSize;
		int yFinal = yStart + Main.GameProperties.ScreenHeight*Main.GameProperties.ScreenScale / Main.GameProperties.TileSize*2;
		//int xFinal = xStart + Main.GameProperties.ScreenWidth / GameProperties.TILE_SIZE;
		//int yFinal = yStart + Main.GameProperties.ScreenHeight / GameProperties.TILE_SIZE;
		
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				
				if(xx < 0 || yy < 0 ||
				   xx >= WIDTH || yy >= HEIGHT) continue;
				tiles[xx + (yy * WIDTH)].render(g);
			}
		}
		/*
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				tiles[xx + (yy * WIDTH)].render(g);
			}
		}
		*/
	}
	
	public static boolean placeFree(int nextX, int nextY) {
		int x = Main.player.getWidth();
		int y = Main.player.getHeight();
		
		int x1 = nextX / x;
		int y1 = nextY / y;
		
		int x2 = (nextX + x - 1) / x;
		int y2 = nextY / y;
		
		int x3 = nextX / x;
		int y3 = (nextY + y - 1) / y;
		
		int x4 = (nextX + x - 1) / x;
		int y4 = (nextY + y - 1) / y;
		
		try {
			return 	tiles[x1 + y1*World.WIDTH].getCollisionType() == CollisionType.NO_COLLISION &&
					tiles[x2 + y2*World.WIDTH].getCollisionType() == CollisionType.NO_COLLISION &&
					tiles[x3 + y3*World.WIDTH].getCollisionType() == CollisionType.NO_COLLISION &&
					tiles[x4 + y4*World.WIDTH].getCollisionType() == CollisionType.NO_COLLISION;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static int calculatePostMitigationDamage(int damage, int resistance) {
		return damage / (1 + resistance / 100);
	};
	
	public Tile[] getTiles() {
		return tiles;
	}
	public World setTiles(Tile[] tiles) {
		World.tiles = tiles;
		return this;
	}
	
	public static int getWIDTH() {
		return WIDTH;
	}
	public static int getHEIGHT() {
		return HEIGHT;
	}
}
