/**
 * 
 */
package br.com.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.graficos.Camera;
import br.com.main.Game;
import br.com.world.World;

/**
 * 
 */
public class Enemy extends Entity {

	private double speed = 0.6;

	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

	public BufferedImage[] enemyMove;

	private int life = 3;
	

	// private int maskx = 8, masky = 8, maskw = 10, maskh = 10;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		enemyMove = new BufferedImage[2];
		enemyMove[0] = Game.enemySheet.getSprite(168, 22, 16, 23);
		enemyMove[1] = Game.enemySheet.getSprite(188, 22, 16, 23);
	}

	public void tick() {
		if (Game.rand.nextInt(100) < 30) {
			if (isCollidingWithPlayer() == false) {
				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
						&& !isColliding((int) (x + speed), this.getY())) {
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
						&& !isColliding((int) (x - speed), this.getY())) {
					x -= speed;
				}
				if (y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
						&& !isColliding(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if (y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
						&& !isColliding(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			} else {
				if (Game.rand.nextInt(100) < 10) {
					Game.player.life--;
					Player.isDamaged = true;
					if (Game.player.life <= 0) {
						Game.player.life = 0;
					}
				}
			}
		}
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}

		collisionMagic();
		if (life <= 0) {
			destroySelf();
		}	
		
	}

	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.WIDTH, World.HEIGHT);
		Rectangle playerCurrent = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(playerCurrent);

	}

	public boolean isColliding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext, yNext, World.WIDTH, World.HEIGHT);
		for (Enemy e : Game.enemies) {
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		
	}

	public void collisionMagic() {
		for (Entity e : Game.spells) {
			if (Entity.isColliding(this, e)) {
				life--;
				Game.spells.remove(e);
				return;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(enemyMove[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

	/*
	 * public boolean isColliding(int xNext, int yNext) { Rectangle enemyCurrent =
	 * new Rectangle(xNext + maskx, yNext + masky, maskw, ); for (Enemy e :
	 * Game.enemies) { if (e == this) { continue; } Rectangle targetEnemy = new
	 * Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE); if
	 * (enemyCurrent.intersects(targetEnemy)) { return true; } }
	 * 
	 * return false; }
	 * 
	 * public void render(Graphics g) { super.render(g); g.setColor(Color.BLACK);
	 * g.fillRect(this.getX() +maskx - Camera.x, this.getY() + masky - Camera.y,
	 * maskw, maskh); }
	 */

}
