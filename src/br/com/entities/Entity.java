/**
 * 
 */
package br.com.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.graficos.Camera;
import br.com.main.Game;

/**
 * 
 */
public class Entity {
	
	public static BufferedImage TILE_FLOOR = Game.floorSheet.getSprite(0, 0, 60, 60);
	public static BufferedImage TILE_WALL = Game.wallSheet.getSprite(0, 50, 16, 16);
	public static BufferedImage TILE_LIFE = Game.itemsSheet.getSprite(84, 144, 16, 23);
	public static BufferedImage TILE_WEAPON = Game.itemsSheet.getSprite(148, 121, 16, 23);
	public static BufferedImage TILE_RECHARGE = Game.itemsSheet.getSprite(72, 96, 16, 23);
	public static BufferedImage TILE_ENEMY = Game.enemySheet.getSprite(168, 22, 16, 23);
	
	protected double x, y;
	protected int width, height;
	
	private BufferedImage sprite;
	
	private int maskx, masky, mwidth, mheight;

	public Entity(double x, double y, int width, int height, BufferedImage sprite) {		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;		
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		
		this.mwidth = width;
		this.mheight = height;
		
	}
	
	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}	

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public void setMasks(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	
	
	public int getMaskx() {
		return maskx;
	}

	public int getMasky() {
		return masky;
	}

	public int getMwidth() {
		return mwidth;
	}

	public int getMheight() {
		return mheight;
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky , e1.mwidth, e1.mheight);	
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e1.masky , e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y,  null);		
	}
}
