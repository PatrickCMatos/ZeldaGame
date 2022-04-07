package com.patrickcaminhas.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.patrickcaminhas.main.Game;
import com.patrickcaminhas.world.Camera;
import com.patrickcaminhas.world.Tile;
import com.patrickcaminhas.world.WallTile;
import com.patrickcaminhas.world.World;

public class ShootedArrow extends Entity {

	private double dx;
	private double dy;
	private double speed = 3.5;
	private int life = 70, curLife = 0;
	public double angleArrow;
	public boolean isWall = false;

	public ShootedArrow(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite, double dx, double dy) {
		super(x, y, WIDTH, HEIGHT, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if (curLife == life) {
			Game.arrows.remove(this);
			return;
		}

	}

	public void checkArrowDegrees() {
		this.angleArrow = Math.toDegrees(
				Math.atan2(Player.my - (this.getY() - Camera.y + 8), Player.mx - (this.getX() - Camera.x + 8)));
		System.out.println(angleArrow);
		// Função que eu calculo angulo em graus, uso ela na função de atirar para medir
		// o angulo
		// E o calculo é feito antes de renderizar a flecha.
		// Está dentro da função Player e a variavel angleArrow é Public Static Double
	}
	/* 
	  */

	public void render(Graphics g) {
		// g.setColor(Color.YELLOW);
		// g.fillRect(this.getX() - Camera.x , this.getY() - Camera.y+6, 6, 2);

		if (this.angleArrow > -55.0 && this.angleArrow <= 35.0) {
			g.drawImage(Arrow.ARROW_SHOOTED_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if (this.angleArrow > 125.0 && this.angleArrow <= 180.0
				|| this.angleArrow > -180.0 && this.angleArrow <= -145.0) {
			g.drawImage(Arrow.ARROW_SHOOTED_LEFT, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if (this.angleArrow > -145.0 && this.angleArrow <= -55.0) {
			g.drawImage(Arrow.ARROW_SHOOTED_UP, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if (this.angleArrow > 35.0 && this.angleArrow <= 125.0) {
			g.drawImage(Arrow.ARROW_SHOOTED_DOWN, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

}
