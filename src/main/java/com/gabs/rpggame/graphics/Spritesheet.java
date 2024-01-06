package com.gabs.rpggame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import com.gabs.rpggame.Main;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String path) {
		try {
			spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	public BufferedImage getSprite(int x, int y) {
		return spritesheet.getSubimage(x, y, Main.GameProperties.TileSize, Main.GameProperties.TileSize);
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	*/
	public BufferedImage getSpriteRelative(int x, int y){
		return spritesheet.getSubimage(
				x*Main.GameProperties.TileSize,
				y*Main.GameProperties.TileSize,
				Main.GameProperties.TileSize,
				Main.GameProperties.TileSize
		);
	}

	public BufferedImage getSpriteRelative(int x, int y, int width, int height){
		return spritesheet.getSubimage(
				x*Main.GameProperties.TileSize,
				y*Main.GameProperties.TileSize,
				width,
				height
		);
	}
}
