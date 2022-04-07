package com.patrickcaminhas.entities;

import java.awt.image.BufferedImage;

import com.patrickcaminhas.main.Game;

public class Bow extends Entity {

	public static BufferedImage BOW_EN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
	public static BufferedImage BOW_LEFT = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage BOW_RIGHT = Game.spritesheet.getSprite(112, 0, 16, 16);
	public static BufferedImage BOW_DOWN = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage BOW_UP = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage BOW_LEFT_DAMAGED = Game.spritesheet.getSprite(112, 32, 16, 16);
	public static BufferedImage BOW_RIGHT_DAMAGED = Game.spritesheet.getSprite(96, 32, 16, 16);
	public static BufferedImage BOW_DOWN_DAMAGED = Game.spritesheet.getSprite(128, 32, 16, 16);
	public static BufferedImage BOW_UP_DAMAGED = Game.spritesheet.getSprite(144, 32, 16, 16);

	public Bow(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		super(x, y, WIDTH, HEIGHT, sprite);
	}

}
