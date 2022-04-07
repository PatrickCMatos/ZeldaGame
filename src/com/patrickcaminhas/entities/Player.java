package com.patrickcaminhas.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.patrickcaminhas.graficos.Spritesheet;
import com.patrickcaminhas.main.Game;
import com.patrickcaminhas.main.Sound;
import com.patrickcaminhas.world.Camera;
import com.patrickcaminhas.world.World;

public class Player extends Entity {

	public boolean right, left, up, down;
	public double speed = 1.5;
	public static int right_dir = 0, left_dir = 1, up_dir = 3, down_dir = 4;
	public static int dir = right_dir;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage playerDamage;

	public double life = 100;
	public double maxLife = 100;
	public double arrow = 0, maxArrow = 10;

	private boolean bow = false;
	public static boolean shoot = false, mouseShoot = false;
	public boolean isDamaged = false;
	private int damageFrames = 0;

	public static int mx;
	public static int my;
	public static double angleArrow;

	public boolean jump = false, isJumping = false, jumpUp = false, jumpDown = false;
	public static int z = 0;
	public int jumpFrames = 30, jumpCurrent = 0, jumpSpeed = 2;

	public Player(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
		super(x, y, WIDTH, HEIGHT, sprite);
		isPlayer = true;
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(96, 16, 16, 16);
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32, 0 + (i * 16), 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(48, 0 + (i * 16), 16, 16);
		}

		for (int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(64, 0 + (i * 16), 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(80, 0 + (i * 16), 16, 16);
		}
	}

	/*
	 * public void checkArrowDegrees() { this.angleArrow =
	 * Math.toDegrees(Math.atan2(my - (this.getY() - Camera.y + 8 ), mx -
	 * (this.getX() - Camera.x + 8 ) ) ); System.out.println(angleArrow); //Função
	 * que eu calculo angulo em graus, uso ela na função de atirar para medir o
	 * angulo //E o calculo é feito antes de renderizar a flecha. //Está dentro da
	 * função Player e a variavel angleArrow é Public Static Double }
	 */

	public void tick() {
		isPlayer = true;
		if (jump) {
			if (isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}

		if (isJumping) {

			if (jumpUp) {
				jumpCurrent += 2;
			} else if (jumpDown) {
				jumpCurrent -= 2;
				if (jumpCurrent <= 0) {
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			z = jumpCurrent;
			if (jumpCurrent >= jumpFrames) {
				jumpUp = false;
				jumpDown = true;
			}

		}

		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;

		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		checkCollisionBow();
		checkCollisionLifePotion();
		checkCollisionArrow();
		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}

		}
		/*
		 * if (shoot ) { shoot=false; if(bow && arrow > 0) { arrow--;
		 * 
		 * int dx = 0; int dy = 0; int px = 0; int py = 0; if(dir == right_dir || dir ==
		 * left_dir){ if (dir == right_dir) { px = 4; dx = 1; } if(dir==left_dir) { dx =
		 * -1; } ShootedArrow arrow = new ShootedArrow(this.getX()+px, this.getY()+py,
		 * 3, 3, null, dx, 0); Game.arrows.add(arrow); } if(dir == up_dir || dir ==
		 * down_dir){ if(dir==up_dir) { dy = -1; } if(dir==down_dir) { dy = 1; }
		 * 
		 * ShootedArrow arrow = new ShootedArrow(this.getX()+px, this.getY()+py, 3, 3,
		 * null, 0, dy); Game.arrows.add(arrow);}
		 * 
		 * } }
		 */
		//
		if (mouseShoot) {
			mouseShoot = false;
			double angle = (Math.atan2(my - (this.getY() + 8 - Camera.y), mx - (this.getX() + 8 - Camera.x)));

			if (bow && arrow > 0) {
				arrow--;

				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int px = 8;
				int py = 8;

				ShootedArrow arrow = new ShootedArrow(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				arrow.checkArrowDegrees();
				Sound.shootEffect.play();
				Game.arrows.add(arrow);

			}
		}

		if (life <= 0) {
			life = 0;
			Game.gameState = "GAME_OVER";
//game over
		}
		updateCamera();
	}

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	public void checkCollisionBow() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Bow) {
				if (Entity.isColliding(this, e)) {
					bow = true;
					Sound.bowEffect.play();
					Game.entities.remove(i);
					//System.out.println("Munição atual: " + arrow);
					return;
				}
			}
		}
	}

	public void checkCollisionArrow() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Arrow) {
				if (Entity.isColliding(this, e)) {
					arrow += 3;
					Sound.arrowPickUpEffect.play();
					if (arrow >= 10) {
						arrow = 10;
					}
					Game.entities.remove(i);
					System.out.println("Munição atual: " + arrow);
					return;
				}
			}
		}
	}

	public void checkCollisionLifePotion() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof LifePotion) {
				if (Entity.isColliding(this, atual)) {
					life += 8;
					if (life >= 100) {
						life = 100;
					}
					Game.entities.remove(atual);
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if (bow) {
					g.drawImage(Bow.BOW_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if (bow) {
					g.drawImage(Bow.BOW_LEFT, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			}
			if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if (bow) {
					g.drawImage(Bow.BOW_UP, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if (bow) {
					g.drawImage(Bow.BOW_DOWN, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				}
			}
		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
			if (dir == right_dir) {
				if (bow) {
					g.drawImage(Bow.BOW_RIGHT_DAMAGED, this.getX() - Camera.x, this.getY() - Camera.y - z, null);

				}
			} else if (dir == left_dir) {
				if (bow) {
					g.drawImage(Bow.BOW_LEFT_DAMAGED, this.getX() - Camera.x, this.getY() - Camera.y - z, null);

				}
			}
			if (dir == up_dir) {
				if (bow) {
					g.drawImage(Bow.BOW_UP_DAMAGED, this.getX() - Camera.x, this.getY() - Camera.y - z, null);

				}
			} else if (dir == down_dir) {
			}
			if (bow) {
				g.drawImage(Bow.BOW_DOWN_DAMAGED, this.getX() - Camera.x, this.getY() - Camera.y - z, null);

			}
		}
		if (isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval(this.getX() - Camera.x + 1, this.getY() - Camera.y + 13, 11, 5);
		}
	}

}
