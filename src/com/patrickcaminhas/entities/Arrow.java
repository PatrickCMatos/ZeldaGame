package com.patrickcaminhas.entities;

import java.awt.image.BufferedImage;

import com.patrickcaminhas.main.Game;

public class Arrow extends Entity {

	public static BufferedImage ARROW_SHOOTED_RIGHT = Game.spritesheet.getSprite(112, 48, 16, 16);
	public static BufferedImage ARROW_SHOOTED_LEFT = Game.spritesheet.getSprite(96, 48, 16, 16);
	public static BufferedImage ARROW_SHOOTED_UP = Game.spritesheet.getSprite(128, 48, 16, 16);
	public static BufferedImage ARROW_SHOOTED_DOWN = Game.spritesheet.getSprite(144, 48, 16, 16);
	public static BufferedImage ARROW_SHOOTED_DIAGONAL_LEFT_UP = Game.spritesheet.getSprite(96, 64, 16, 16);
	public static BufferedImage ARROW_SHOOTED_DIAGONAL_RIGHT_UP = Game.spritesheet.getSprite(112, 64, 16, 16);
	public static BufferedImage ARROW_SHOOTED_DIAGONAL_LEFT_DOWN = Game.spritesheet.getSprite(128, 64, 16, 16);
	public static BufferedImage ARROW_SHOOTED_DIAGONAL_RIGHT_DOWN = Game.spritesheet.getSprite(144, 64, 16, 16);

	public static BufferedImage ARROW_EN = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);

	public Arrow(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		super(x, y, WIDTH, HEIGHT, sprite);
	}

}
