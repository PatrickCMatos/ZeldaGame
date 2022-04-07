package com.patrickcaminhas.entities;

import java.awt.image.BufferedImage;

import com.patrickcaminhas.main.Game;

public class LifePotion extends Entity {

	public static BufferedImage LIFEPOTION_EN = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);

	public LifePotion(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		super(x, y, WIDTH, HEIGHT, sprite);
	}

}
