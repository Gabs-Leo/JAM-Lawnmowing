package com.gabs.rpggame.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.Main;

public class HUD {
	public void render(Graphics g) {
		//g.setColor(new Color(50, 50, 50));
		//g.fillRect(0, 0, Main.GameProperties.ScreenWidth, 40);
		
		g.setColor(Color.red);
		g.fillRect(10, 10, Main.player.getMaxLife()*4, 40);
		
		g.setColor(new Color(203671));
		g.fillRect(10, 10, Main.player.getLife()*4, 40);

		g.setColor(new Color(0));
		g.fillRect(Main.GameProperties.ScreenWidth - 70, 10, 60, 40);
		g.fillRect(Main.GameProperties.ScreenWidth /2 - 100, 10, 200, 40);

		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Javanese Text", Font.PLAIN, 30));
		g.drawString("Wave "+Main.enemySpawner.getWave(), Main.GameProperties.ScreenWidth /2 - 40, 40);

		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Javanese Text", Font.PLAIN, 30));
		g.drawString(""+Main.player.getMowedAmount(), Main.GameProperties.ScreenWidth - 50, 35);
	}
}
