/**
 * 
 */
package br.com.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.graficos.Camera;
import br.com.main.Game;

/**
 * 
 */
public class Spell extends Entity {

	private int dx, dy;
	private double spd = 4;

	public int curLife = 0;

	public Spell(double x, double y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x += dx * spd;
		y += dy * spd;
		this.curLife++;
		if(this.curLife > 30) {
			Game.spells.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);

	}

}
