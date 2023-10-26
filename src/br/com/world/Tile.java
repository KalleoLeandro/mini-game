/**
 * 
 */
package br.com.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.graficos.Camera;


/**
 * 
 */
public class Tile {
	
	
	private BufferedImage sprite;
	
	private int x,y;
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
	public Tile(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.sprite = image;
	}
}
