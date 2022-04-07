package com.patrickcaminhas.entities;

import java.awt.image.*;
import java.awt.*;
import com.patrickcaminhas.main.Game;
import com.patrickcaminhas.world.Camera;

public class Entity {

	protected double x;
	protected double y;
	protected double z;
	protected int WIDTH;
	protected int HEIGHT;
	private BufferedImage sprite;
	public static boolean isPlayer = false;

	private int maskx, masky, mwidth, mheight;

	public static BufferedImage HEART = Game.spritesheet.getSprite(96, 64, 16, 16);

	public Entity(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.sprite = sprite;
		this.isPlayer = false;
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = WIDTH;
		this.mheight = HEIGHT;

	}

	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public void tick() {
		isPlayer = false;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		// g.setColor(Color.RED);
		// g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y,
		// mwidth, mheight);

	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWIDTH() {
		return this.WIDTH;
	}

	public int getHEIGHT() {
		return this.HEIGHT;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWIDTH(int WIDTH) {
		this.WIDTH = WIDTH;
	}

	public void setHEIGHT(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e1.masky, e2.mwidth, e2.mheight);

		if (e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}
		return e1Mask.intersects(e2Mask);
	}

}
