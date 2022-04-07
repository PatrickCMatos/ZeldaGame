package com.patrickcaminhas.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.patrickcaminhas.entities.Arrow;
import com.patrickcaminhas.entities.Entity;
import com.patrickcaminhas.entities.Player;
import com.patrickcaminhas.main.Game;
import com.patrickcaminhas.world.Camera;

public class UI {
	
	
	
	public void render(Graphics g) {
		//g.drawImage(Entity.HEART, 120, 120, null);
		g.setColor(Color.BLACK);
		g.fillRect(4, 4, 81, 12);
		g.setColor(Color.RED);
		g.fillRect(5, 5, 79, 10);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, (int)((Game.player.life/Game.player.maxLife)*79), 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.ITALIC,11));
		g.drawString("Vida: "+ ((int)Game.player.life + " / " + (int)Game.player.maxLife), 5, 14);
		
		g.setColor(Color.BLACK);
		g.fillRect(4, 17, 81, 12);
		g.setColor(Color.GRAY);
		g.fillRect(5, 18, 79, 10);
		g.setColor(Color.ORANGE);
		g.fillRect(5, 18, (int)((Game.player.arrow/Game.player.maxArrow)*79), 10);
		g.setColor(Color.BLACK);
		g.fillRect(5+7, 18, 1, 10);
		g.fillRect(5+15, 18, 1, 10);
		g.fillRect(5+23, 18, 1, 10);
		g.fillRect(5+31, 18, 1, 10);
		g.fillRect(5+39, 18, 1, 10);
		g.fillRect(5+47, 18, 1, 10);
		g.fillRect(5+55, 18, 1, 10);
		g.fillRect(5+63, 18, 1, 10);
		g.fillRect(5+71, 18, 1, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.ITALIC,11));
		//g.drawString("Flechas: "+ (int)Game.player.arrow+" / "+ (int)Game.player.maxArrow, 5, 27);
	}
	
}
