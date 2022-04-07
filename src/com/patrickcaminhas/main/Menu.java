package com.patrickcaminhas.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.patrickcaminhas.world.World;

public class Menu {

	public String[] options = { "NEW_GAME", "LOAD_GAME","CHANGE_SCALE", "HOW_TO_PLAY", "EXIT" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;
	public static boolean pause = false;
	public boolean howToPlay = false;
	public static  boolean saveExists = false, saveGame = false;

	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		} else {
			saveExists = false;
		}
		
		if (up) {
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if (down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if (enter) {
			enter = false;
			if (options[currentOption] == "NEW_GAME" || options[currentOption] == "CONTINUE") {
				Game.gameState = "NORMAL";
				pause = false;

			}else if(options[currentOption] == "LOAD_GAME"){
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
					System.out.println("TENTANDO CARREGAR 1");
				}
			}
			
			/*else if (options[currentOption] == "CHANGE_SCALE") {
				Game.SCALE++;
				if(Game.SCALE>6) {
					Game.SCALE=3;
				} 
			} */
			else if (options[currentOption] == "EXIT") {
				System.exit(1);
			}
			
			

		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String [] spl2  = spl[i].split(":");
			switch (spl2[0]) {
			case "level": {
				World.restartGame("level"+spl2[1]+".png");
				Game.gameState="NORMAL";
				pause=false;
				break;
			}
			case "vida": {
				Game.player.life = Integer.parseInt(spl2[1]); 
				break;
			}
			default:
				
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String [] trans = singleLine.split(":");
						char [] val = trans[1].toCharArray();
						trans[1] ="";
						for(int i = 0; i < val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
						System.out.println(line);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (FileNotFoundException e) {
				
			}
		}
		return line;
	}
	
	
	
	
	public static void saveGame(String [] val1, int [] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for( int i = 0; i < val1.length; i++) {
			String current = val1[i];
			String current2 = val1[1];
			current+=":";
			current2+=":";
			char[] value =  Integer.toString(val2[i]).toCharArray();
			char[] value2 =  Integer.toString(val2[i]).toCharArray();
			for( int j = 0; j < value.length; j++) {
				value[j]+=encode;
				current += value[j]; 
				if(j==1) {
					current2+=value[j];
				}
			}
			try {System.out.println(current2);
				write.write(current);
				write.newLine();
				write.write(current2);
				if(i < val1.length -1) {
					write.newLine();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				write.flush();
				write.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	

	public void render(Graphics g) {
		// BACKGROUND
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));

		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		// TITULO DO JOGO
		g.setColor(new Color(255, 38, 56));
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Legacy Reconquest", ((Game.WIDTH * Game.SCALE) / 2) - 150,
				((Game.HEIGHT * Game.SCALE) / 2) - 100);
		g.setFont(new Font("arial", Font.BOLD, 22));
		// OPÇÕES DO MENU
		g.setColor(Color.WHITE);
		if (pause == false) {
			g.drawString("Novo Jogo", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2));
		} else {
			g.drawString("Continuar", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2));

		}
		g.drawString("Carregar Jogo (Ainda em progresso)", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2) + 40);
		g.drawString("Aumentar resolução(Ainda em progresso)", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2) + 80);
		g.drawString("Como jogar", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2) + 120);
		g.drawString("Sair", ((Game.WIDTH * Game.SCALE) / 2) - 150, ((Game.HEIGHT * Game.SCALE) / 2) + 160);

		if (options[currentOption] == "NEW_GAME") {
			g.drawString("> ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2));
		} else if (options[currentOption] == "LOAD_GAME") {
			g.drawString("> ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2) + 40);
		}else if (options[currentOption] == "CHANGE_SCALE") {
			g.drawString("> ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2) + 80);
		} 
		else if (options[currentOption] == "HOW_TO_PLAY") {
			g.drawString("> ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2) + 120);
			g2.setColor(new Color(0,0,0,220));
			g2.fillRect(((Game.WIDTH * Game.SCALE) /2) - 220, ((Game.HEIGHT * Game.SCALE) / 4),  (int) ((int)Game.WIDTH*2),Game.HEIGHT*2 );
			g.setColor(Color.WHITE);
			g.drawString("CONTROLES", ((Game.WIDTH * Game.SCALE) / 2) - 50, ((Game.HEIGHT * Game.SCALE) / 2)-140);
			g.drawString("Movimento: Direcionais ou WASD.", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)-100);
			g.drawString("Atirar: Botão esquerdo do mouse.", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)-60);
			g.drawString("Pause: Botão ESC.", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)-20);
			g.drawString("----------------", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)+20);
			g.drawString("Dica: Só é possivel atirar quando se ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)+60);
			g.drawString("pega o arco.", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2)+80);


		} else if (options[currentOption] == "EXIT") {
			g.drawString("> ", ((Game.WIDTH * Game.SCALE) / 2) - 190, ((Game.HEIGHT * Game.SCALE) / 2) + 160);
		}
	}

}
