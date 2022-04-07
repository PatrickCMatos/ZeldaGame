package com.patrickcaminhas.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.patrickcaminhas.main.Game;
import com.patrickcaminhas.main.Sound;
import com.patrickcaminhas.world.Camera;
import com.patrickcaminhas.world.World;

public class Enemy extends Entity {

	private double speed = 1.2;
	private int maskx = 8, masky = 8, maskw = 12, maskh = 10;
	private int frames = 0, maxFrames = 9, index = 0, maxIndex = 3;
	private BufferedImage[] sprite_right;
	private BufferedImage[] sprite_left;
	private BufferedImage[] sprite_up;
	private int dir_right = 0, dir_left = 1, dir_up = 2;
	private int dir = dir_right;
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(48, 64, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(8 * 16, 16, 16, 16);

	private int life = 5;

	private boolean isDamaged = false;
	private int damageFrames = 7, damageCurrent = 0;

	public Enemy(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		super(x, y, WIDTH, HEIGHT, null);
		sprite_right = new BufferedImage[4];
		sprite_left = new BufferedImage[4];
		sprite_up = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			sprite_right[i] = Game.spritesheet.getSprite(32, 64 + (i * 16), 16, 16);
			sprite_left[i] = Game.spritesheet.getSprite(48, 64 + (i * 16), 16, 16);
			sprite_up[i] = Game.spritesheet.getSprite(64, 64 + (i * 16), 16, 16);

		}
	}

	public void tick() {
		// if(Game.random.nextInt(100) < 30 ) {

		if (this.isCollidingWithPlayer() == false) {

			if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
					&& !isColliding((int) (x + speed), this.getY())) {
				x += speed;
				dir = dir_right;
			} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
					&& !isColliding((int) (x - speed), this.getY())) {
				x -= speed;
				dir = dir_left;
			}
			if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
					&& !isColliding(this.getX(), (int) (y + speed))) {
				y += speed;

			} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
					&& !isColliding(this.getX(), (int) (y - speed))) {
				y -= speed;
				dir = dir_up;
			}
		} else {
			if (Game.random.nextInt(100) < 10) {
				Sound.hurtEffect.play();
				Game.player.life -= Game.random.nextInt(3);
				Game.player.isDamaged = true;
				//System.out.println("Vida: " + Game.player.life);

			}

		}
		// }

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		collidingArrow();
		if (this.life <= 0) {
			Sound.explosionEffect.play();
			destroySelf();
			return;
		}
		if (isDamaged) {
			this.damageCurrent++;
			if (this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}
	}

	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}

	public void collidingArrow() {
		for (int i = 0; i < Game.arrows.size(); i++) {
			Entity e = Game.arrows.get(i);

			if (Entity.isColliding(this, e)) {
				isDamaged = true;
				life -= 2;
				Game.arrows.remove(i);
				return;

			}
		}

	}

	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}

	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);

		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == dir_right) {
				g.drawImage(sprite_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (dir == dir_left) {
				g.drawImage(sprite_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (dir == dir_up) {
				g.drawImage(sprite_up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		// g.setColor(Color.blue);
		// g.fillRect(this.getX()-Camera.x + maskx,this.getY()-Camera.y +
		// masky,maskw,maskh);
	}

}
