package com.gabs.rpggame.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.GameProperties;
import com.gabs.rpggame.GameState;
import com.gabs.rpggame.Main;

public class GameOverScreen {
	private int frame;
	private int duration = 30;
	private boolean showGameOverMessage;
	
	public void render(Graphics g) {
		if(Main.state == GameState.GAME_OVER) {
			g.setColor(new Color(0, 0, 0, .6f));
			g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
			
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Javanese Text", Font.PLAIN, 48));
			g.drawString("Game Over", Main.GameProperties.ScreenWidth/2 - 90, Main.GameProperties.ScreenHeight/2 - 20);

			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Javanese Text", Font.PLAIN, 24));
			g.drawString("Tiles Mowed: " + Main.player.getMowedAmount(), Main.GameProperties.ScreenWidth/2 - 50, Main.GameProperties.ScreenHeight/2 + 10);
			g.drawString("Waves survived: " + Main.enemySpawner.getWave(), Main.GameProperties.ScreenWidth/2 - 60, Main.GameProperties.ScreenHeight/2 + 35);

			frame++;
			if(frame >= duration) {
				frame = 0;
				if(showGameOverMessage)
					showGameOverMessage = false;
				else
					showGameOverMessage = true;
			}
			if(showGameOverMessage) {
				g.drawString("> Press [ Z ] to Restart <", Main.GameProperties.ScreenWidth/2 - 105, Main.GameProperties.ScreenHeight/2 + 70);
			}
		}
	}
}
