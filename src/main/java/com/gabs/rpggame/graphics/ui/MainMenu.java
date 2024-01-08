package com.gabs.rpggame.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.graphics.Spritesheet;
import com.gabs.rpggame.graphics.UI;
import com.gabs.rpggame.world.Direction;

import javax.imageio.ImageIO;

public class MainMenu implements UI {
	
	private int option = 0;

	private int textMarginLeft = 20;
	private int textMarginTop = 300;
	private int textSize = 50;
	private int textSpace = 10;

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
		try {
			g.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/home_bg.png"))), 0,0, null);
		} catch (IOException e) {
			System.out.println(e);
		}
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("arial", Font.PLAIN, textSize));
		g.drawString("Start", textMarginLeft, textMarginTop);
		g.drawString("Exit", textMarginLeft, textMarginTop+textSize+textSpace);
		switch(option) {
			case 0:
				g.setColor(Color.WHITE);
				g.drawString("Start", textMarginLeft, textMarginTop);
				break;
			case 1:
				g.setColor(Color.WHITE);
				g.drawString("Exit", textMarginLeft, textMarginTop+textSize+textSpace);
				break;
		}
	}
	
	public void changeOption( Direction direction ) {
		switch(direction) {
		case DOWN:
			option = option == 1 ? 0 : 1;
			break;
		case UP:
			option = option == 0 ? 1 : 0;
			break;
		default:
			break;
		}
	}

	public int getOption() {
		return option;
	}

	public MainMenu setOption(int option) {
		this.option = option;
		return this;
	}

	@Override
	public void trigger() {
		switch(option) {
			case 0:
				Main.startGame();
				break;
			case 1:
				Main.closeGame();
				break;
		}
	}
}
