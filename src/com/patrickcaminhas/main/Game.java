package com.patrickcaminhas.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import com.patrickcaminhas.entities.*;
import com.patrickcaminhas.graficos.Spritesheet;
import com.patrickcaminhas.graficos.UI;
import com.patrickcaminhas.world.Camera;
import com.patrickcaminhas.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public final static int WIDTH = 240;
	public final static int HEIGHT = 135;
	public static int SCALE = 5;

	private int CURRENT_LEVEL = 1, MAX_LEVEL = 3;
	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<WolfEnemy> wolfs;

	public static List<ShootedArrow> arrows;
	public static Spritesheet spritesheet;
	public static Player player;

	public static World world;

	public static Random random;

	public UI ui;

	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;

	private boolean restartGame = false;

	public Menu menu;

	public boolean saveGame = false;

	public Game() {
		Sound.music.loop();
		random = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		initialFrame();
		// iniciando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		wolfs = new ArrayList<WolfEnemy>();
		arrows = new ArrayList<ShootedArrow>();

		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png");

		menu = new Menu();

	}

	public void initialFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeScale() {
		SCALE++;
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		if (SCALE > 6) {
			SCALE = 2;
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {

		if (gameState == "NORMAL") {
			if (this.saveGame) {
				this.saveGame = false;
				String[] opt1 = { "level", "vida" };
				int[] opt2 = { this.CURRENT_LEVEL, (int) player.life };
				Menu.saveGame(opt1, opt2, 0);
				System.out.println("Jogo foi salvo com sucesso!");
			}

			this.restartGame = false;
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				if (e instanceof Player) {
					// Estou dando tick no player
				}
				e.tick();
			}
			for (int i = 0; i < arrows.size(); i++) {
				arrows.get(i).tick();
			}

			if (enemies.size() == 0 && wolfs.size() == 0) {
				// PROXIMO NIVEL
				CURRENT_LEVEL++;
				if (CURRENT_LEVEL > MAX_LEVEL) {
					CURRENT_LEVEL = 1;
					gameState = "MENU";
				}
				String newWorld = "level" + CURRENT_LEVEL + ".png";
				World.restartGame(newWorld);
			}
		} else if (gameState == "GAME_OVER") {
			this.framesGameOver++;
			if (this.framesGameOver > 25) {
				this.framesGameOver = 0;
				if (this.showMessageGameOver) {
					this.showMessageGameOver = false;
				} else {
					this.showMessageGameOver = true;
				}

			}
			if (restartGame) {
				this.restartGame = false;
				this.gameState = "NORMAL";
				CURRENT_LEVEL = 1;
				String newWorld = "level" + CURRENT_LEVEL + ".png";
				World.restartGame(newWorld);
			}
		} else if (gameState == "MENU") {
			// MENU
			menu.tick();

		}

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		/* RENDERIZAÇÃO DO JOGO */
		// Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).render(g);
			;
		}
		ui.render(g);
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 30));
		// g.setColor(Color.WHITE);
		// g.drawString("Flechas: "+ player.arrow, 600, 50);
		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g2.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 29));
			g2.drawString("GAME OVER", (int) (WIDTH + 2), (int) (HEIGHT * 1.5));
			g.setFont(new Font("arial", Font.BOLD, 12));
			if (showMessageGameOver) {
				g2.drawString("Pressione ENTER para reiniciar!", (int) (WIDTH + 2) - 1, (int) (HEIGHT * 1.5) + 30);
			}

		} else if (gameState == "MENU") {
			menu.render(g);
		}
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				// System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;

			}
		}
		stop();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			player.jump = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// Andar para direita
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// Andar para esquerda
			player.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// Andar para cima
			player.up = true;
			if (gameState == "MENU") {
				menu.up = true;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// Andar para baixo
			player.down = true;
			if (gameState == "MENU") {
				menu.down = true;
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if (this.gameState == "MENU") {
				this.menu.enter = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (gameState != "MENU") {
				gameState = "MENU";
				menu.pause = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (gameState == "NORMAL") {
				this.saveGame = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// Andar para direita
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// Andar para esquerda
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// Andar para cima
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// Andar para baixo
			player.down = false;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX() / SCALE);
		player.my = (e.getY() / SCALE);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
