/**
 * 
 */
package br.com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 
 */

public class Menu {

	public String[] options = { "NOVO JOGO", "CARREGAR JOGO", "SAIR" };

	public int currentOption = 0, maxOption = options.length - 1;

	public boolean up, down, enter, pause;

	public void tick() {
		if (up) {
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		} else if (down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}

		if (enter) {
			enter = false;
			if (options[currentOption].equalsIgnoreCase("NOVO JOGO")) {
				Game.gameState = "NORMAL";
				pause = false;

			} else if (options[currentOption].equalsIgnoreCase("SAIR")) {
				System.exit(0);
			}
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 36));
		g.drawString("Meu Jogo", (Game.WIDTH * Game.SCALE) / 2 - 100, (Game.HEIGHT * Game.SCALE) / 2 - 160);

		// Opções do jogo
		g.setColor(Color.WHITE);

		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(pause ? "RESUMIR" : options[0], (Game.WIDTH * Game.SCALE) / 2 - 100, (Game.HEIGHT * Game.SCALE) / 2 - 110);		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(options[1], (Game.WIDTH * Game.SCALE) / 2 - 100, (Game.HEIGHT * Game.SCALE) / 2 - 80);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(options[2], (Game.WIDTH * Game.SCALE) / 2 - 100, (Game.HEIGHT * Game.SCALE) / 2 - 50);

		if (options[currentOption].equalsIgnoreCase("NOVO JOGO")) {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 130, (Game.HEIGHT * Game.SCALE) / 2 - 110);
		} else if (options[currentOption].equalsIgnoreCase("CARREGAR JOGO")) {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 130, (Game.HEIGHT * Game.SCALE) / 2 - 80);
		} else if (options[currentOption].equalsIgnoreCase("SAIR")) {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 130, (Game.HEIGHT * Game.SCALE) / 2 - 50);
		}
	}

}
