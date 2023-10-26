/**
 * 
 */
package br.com.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import br.com.entities.Player;

/**
 * 
 */

public class Ui {
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(8, 4, 100, 10);
		g.setColor(Color.GREEN);
		g.fillRect(8,4, (int)((Player.life/Player.maxLife)*100), 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.PLAIN, 8));
		g.drawString((int) Player.life + "/" + (int) Player.maxLife, 18, 11);
	}

}
